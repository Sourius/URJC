package es.urjc.ssii.code.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private UserDetailsService udservice;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		/*
		auth.inMemoryAuthentication()
				.withUser("admin")
				.password(passwordEncoder().encode("admin"))
				.authorities("ADMIN");
		auth.inMemoryAuthentication()
				.withUser("company")
				.password(passwordEncoder().encode("company"))
				.authorities("COMPANY");
		auth.inMemoryAuthentication()
				.withUser("user")
				.password(passwordEncoder().encode("user"))
				.authorities("USER");
		 */
		auth.authenticationProvider(getAuthenticationProvider()).userDetailsService(udservice);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.cors()
				.and()
				.csrf().disable()
				.authorizeRequests()
				//.antMatchers("/admins","/companies","/users", "/offers", "/inscriptions").denyAll()
				.antMatchers("/adminPage").hasAuthority("ADMIN")
				.antMatchers("/companyPage").hasAuthority("COMPANY")
				.antMatchers("/userPage").hasAuthority("USER")
				.anyRequest().authenticated()
				.and()
				.formLogin()
				//.loginPage("/login")
				.permitAll()
				.usernameParameter("username")
				.passwordParameter("password")
				//.loginProcessingUrl("/process_login")
				//.successForwardUrl("/login_success")
				.defaultSuccessUrl("/",true)
				//.failureForwardUrl("/login_failed")
				.and()
				.logout()
				.permitAll();
	}

	@Bean
	public DaoAuthenticationProvider getAuthenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(udservice);
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}

	@Bean
	public PasswordEncoder passwordEncoder(){
		return new PasswordEncoder() {
			@Override
			public String encode(CharSequence charSequence) {
				try {
					return hash256(charSequence.toString());
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
					throw new RuntimeException("Technical issues!");
				}
			}

			@Override
			public boolean matches(CharSequence charSequence, String s) {
				return encode(charSequence).equals(s);
			}

			private String hash256(String data) throws NoSuchAlgorithmException {
				MessageDigest digest = MessageDigest.getInstance("SHA-256");
				byte[] encoded = digest.digest(
						data.getBytes(StandardCharsets.UTF_8));
				return bytesToHex(encoded);
			}

			private String bytesToHex(byte[] bytes) {
				StringBuffer result = new StringBuffer();
				for (byte byt : bytes) result.append(Integer.toString((byt & 0xff) + 0x100, 16).substring(1));
				return result.toString();
			}
		};
	}
}
