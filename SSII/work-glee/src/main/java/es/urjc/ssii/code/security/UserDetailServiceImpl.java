package es.urjc.ssii.code.security;

import es.urjc.ssii.code.entidades.Usuario;
import es.urjc.ssii.code.repositorios.RepositorioAdmin;
import es.urjc.ssii.code.repositorios.RepositorioDemandante;
import es.urjc.ssii.code.repositorios.RepositorioEmpresa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
	@Autowired
	private RepositorioDemandante users;
	@Autowired
	private RepositorioEmpresa companies;
	@Autowired
	private RepositorioAdmin admins;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario user;
		user = admins.findByEmail(username);
		if(user == null) user = companies.findByEmail(username);
		if(user == null) user = users.findByEmail(username);
		if(user == null) throw new UsernameNotFoundException("User not found "+username+"!");
		return new UserDetailsImpl(user);
	}
}
