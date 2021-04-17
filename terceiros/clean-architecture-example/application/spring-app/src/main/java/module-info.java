module coffeeandit.example.spring {
	requires coffeeandit.example.config;
	requires coffeeandit.example.usecase;
	requires coffeeandit.example.controller;
	requires spring.web;
	requires spring.beans;

	requires spring.boot;
	requires spring.boot.autoconfigure;
	requires spring.context;
	requires com.fasterxml.jackson.databind;
	requires jackson.annotations;

	exports com.coffeeandit.example.spring;
	exports com.coffeeandit.example.spring.config;
	opens com.coffeeandit.example.spring.config to spring.core;
}
