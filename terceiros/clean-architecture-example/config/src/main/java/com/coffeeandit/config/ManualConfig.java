package com.coffeeandit.config;

import com.coffeeandit.example.db.InMemoryUserRepository;
import com.coffeeandit.example.usecase.port.IdGenerator;
import com.coffeeandit.example.usecase.port.PasswordEncoder;
import com.coffeeandit.example.usecase.port.UserRepository;
import com.coffeeandit.example.encoder.Sha256PasswordEncoder;
import com.coffeeandit.example.jug.JugIdGenerator;
import com.coffeeandit.example.usecase.CreateUser;
import com.coffeeandit.example.usecase.FindUser;
import com.coffeeandit.example.usecase.LoginUser;

public class ManualConfig {
	private final UserRepository userRepository = new InMemoryUserRepository();
	private final IdGenerator idGenerator = new JugIdGenerator();
	private final PasswordEncoder passwordEncoder = new Sha256PasswordEncoder();

	public CreateUser createUser() {
		return new CreateUser(userRepository, passwordEncoder, idGenerator);
	}

	public FindUser findUser() {
		return new FindUser(userRepository);
	}

	public LoginUser loginUser() {
		return new LoginUser(userRepository, passwordEncoder);
	}
}
