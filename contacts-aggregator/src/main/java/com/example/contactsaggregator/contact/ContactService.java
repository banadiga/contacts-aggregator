package com.example.contactsaggregator.contact;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

@AllArgsConstructor
@Service
public class ContactService {

  private ContactRepository contactRepository;

  @PostConstruct
  public void init() {
    Contact contact = Contact.builder()
        .firstName("Ihor")
        .secondName("Banadiga")
        .email(Email.builder().email("my@email.com").build())
        .build();
    contactRepository.save(contact);
  }

  public Optional<Contact> findBy(Collection<String> emails, Collection<String> phones) {
    return contactRepository.findAll().stream()
        .filter(contact ->
            !Collections.disjoint(
                contact.getEmails().stream().map(Email::getEmail).collect(Collectors.toList()),
                emails) ||
            !Collections.disjoint(
                contact.getPhones().stream().map(Phone::getPhone).collect(Collectors.toList()),
                phones))
        .findFirst();
  }

  public void saveAll(List<Contact> contacts) {
    contactRepository.saveAll(contacts);
  }
}
