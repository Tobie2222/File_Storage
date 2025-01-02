package com.example.filestorage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FilestorageApplication {

	public static void main(String[] args) {
		SpringApplication.run(FilestorageApplication.class, args);
		System.out.print("Ket noi database thanh cong");
	}

}
