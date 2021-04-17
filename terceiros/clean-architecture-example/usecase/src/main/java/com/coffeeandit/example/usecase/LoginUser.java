package com.coffeeandit.example.usecase;

import com.coffeeandit.example.domain.entity.User;
import com.coffeeandit.example.usecase.exception.NotAllowedException;
import com.coffeeandit.example.usecase.port.PasswordEncoder;
import com.coffeeandit.example.usecase.port.UserRepository;

public final class LoginUser {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public LoginUser(final UserRepository userRepository, final PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public User login(final String email, final String password) {
		var user = userRepository.findByEmail(email).orElseThrow(() -> new NotAllowedException("Not allowed"));
		var hashedPassword = passwordEncoder.encode(email + password);
		if (!user.getPassword().equals(hashedPassword)) throw new NotAllowedException("Not allowed");
		return user;
	}
}
