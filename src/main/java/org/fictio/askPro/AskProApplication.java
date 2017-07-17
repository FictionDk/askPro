package org.fictio.askPro;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan("org.fictio.askPro.dao")
public class AskProApplication {

	public static void main(String[] args) {
		SpringApplication.run(AskProApplication.class, args);
	}
}
