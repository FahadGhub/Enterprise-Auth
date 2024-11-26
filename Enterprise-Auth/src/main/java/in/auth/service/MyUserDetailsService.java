package in.auth.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.auth.entity.LoginUser;
import in.auth.entity.UserPrincipal;
import in.auth.repository.LoginUserRepository;

@Service
@Transactional
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private LoginUserRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Map<String, Object> param = new HashMap<>();
		param.put("username", username);
		LoginUser user = userRepo.findEntityByMultipleIds(LoginUser.class, param);
		if (user == null) {
			System.out.println("User Not Found");
			throw new UsernameNotFoundException("user not found");
		}

		return new UserPrincipal(user);
	}
}
