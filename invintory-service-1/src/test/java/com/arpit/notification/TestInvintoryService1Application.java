package com.arpit.notification;

import org.springframework.boot.SpringApplication;

public class TestInvintoryService1Application {

	public static void main(String[] args) {
		SpringApplication.from(InvintoryService1Application::main).with(TestcontainersConfiguration.class).run(args);
	}

}
