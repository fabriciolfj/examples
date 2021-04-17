package com.coffeeandit.config;

import com.coffeeandit.example.db.hazelcast.HazelcastUserRepository;
import com.coffeeandit.example.usecase.port.PasswordEncoder;
import com.coffeeandit.example.usecase.port.UserRepository;
import com.coffeeandit.example.encoder.Sha256PasswordEncoder;
import com.coffeeandit.example.usecase.CreateUser;
import com.coffeeandit.example.usecase.FindUser;
import com.coffeeandit.example.usecase.LoginUser;
import com.coffeeandit.example.uuid.UuidGenerator;

public class SpringConfig {

	private final UserRepository userRepository = new HazelcastUserRepository();
	private final PasswordEncoder passwordEncoder = new Sha256PasswordEncoder();

	public CreateUser createUser() {
		return new CreateUser(userRepository, passwordEncoder, new UuidGenerator());
	}

	public FindUser findUser() {
		return new FindUser(userRepository);
	}

	public LoginUser loginUser() {
		return new LoginUser(userRepository, passwordEncoder);
	}
}
