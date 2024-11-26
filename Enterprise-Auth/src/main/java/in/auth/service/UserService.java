package in.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.auth.entity.LoginUser;
import in.auth.repository.LoginUserRepository;

@Service
@Transactional
public class UserService {

	@Autowired
	private JWTService jwtService;

	@Autowired
	AuthenticationManager authManager;

	@Autowired
	private LoginUserRepository repo;

	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

	public LoginUser register(LoginUser user) {
		user.setPassword(encoder.encode(user.getPassword()));
		repo.save(user);
		return user;
	}

	public String verify(LoginUser user) {
		Authentication authentication = authManager
				.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		if (authentication.isAuthenticated()) {
			return jwtService.generateToken(user.getUsername());
		} else {
			return "fail";
		}
	}
}
