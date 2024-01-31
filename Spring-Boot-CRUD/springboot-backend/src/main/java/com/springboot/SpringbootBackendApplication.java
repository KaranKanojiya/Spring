package com.springboot;

import com.springboot.repository.EmployeeRepository;
import com.springboot.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;

@SpringBootApplication
public class SpringbootBackendApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootBackendApplication.class, args);
	}

	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public void run(String... args) throws Exception {

 		Employee employee = new Employee();
		employee.setFirstName("Karan");
		employee.setLastName("Kanojiya");
		employee.setEmailId("kanojiyakaran@gmail.com");
		employeeRepository.save(employee);
	}
}
