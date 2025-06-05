package com.chinazes.chinazes;

import com.chinazes.chinazes.model.Players;
import com.chinazes.chinazes.repository.CommsRepository;
import com.chinazes.chinazes.repository.PlayersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@SpringBootApplication
public class ChinazesApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChinazesApplication.class, args);
	}


}
