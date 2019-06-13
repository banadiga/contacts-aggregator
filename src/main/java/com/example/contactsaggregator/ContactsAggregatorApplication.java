package com.example.contactsaggregator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ContactsAggregatorApplication {

  public static void main(String[] args) {
    SpringApplication.run(ContactsAggregatorApplication.class, args);
  }

}
