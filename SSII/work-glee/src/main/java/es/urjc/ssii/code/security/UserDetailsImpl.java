package es.urjc.ssii.code.security;

import es.urjc.ssii.code.entidades.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class UserDetailsImpl implements UserDetails {
	private Usuario user;

	public UserDetailsImpl() {}

	public UserDetailsImpl(Usuario user) {
		if(user != null){
			this.user = user;
		}
	}

	public Usuario getUser(){
		return user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List aux = new ArrayList<>();
		if(user.isAdmin()) aux.add(new SimpleGrantedAuthority("ADMIN"));
		else if(user.isCompany()) aux.add(new SimpleGrantedAuthority("COMPANY"));
		else aux.add(new SimpleGrantedAuthority("USER"));
		return aux;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}