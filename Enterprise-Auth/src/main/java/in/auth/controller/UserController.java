package in.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import in.auth.entity.LoginUser;
import in.auth.entity.UserBody;
import in.auth.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService service;

	@PostMapping("/register")
	public LoginUser register(@RequestBody UserBody user) {
		LoginUser loginUser = new LoginUser();
		loginUser.setUsername(user.getUsername());
		loginUser.setPassword(user.getPassword());
		return service.register(loginUser);
	}

	@PostMapping("/login")
	public String login(@RequestBody UserBody user) {
		LoginUser loginUser = new LoginUser();
		loginUser.setUsername(user.getUsername());
		loginUser.setPassword(user.getPassword());
		return service.verify(loginUser);
	}
}
