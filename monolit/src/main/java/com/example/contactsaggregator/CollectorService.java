package com.example.contactsaggregator;

import lombok.AllArgsConstructor;

import com.example.contactsaggregator.contact.Contact;
import com.example.contactsaggregator.contact.ContactService;
import com.example.contactsaggregator.contact.Email;
import com.example.contactsaggregator.rawdata.RawData;
import com.example.contactsaggregator.rawdata.RawDataService;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CollectorService {

  private ContactService contactService;
  private RawDataService rawDataService;

  private static Email toEmail(String email) {
    return Email.builder().email(email).build();
  }

  private static List<Email> toEmails(Collection<String> emails) {
    return emails.stream().map(CollectorService::toEmail).collect(Collectors.toList());
  }

  @Scheduled(fixedDelay = 1000)
  public void migrate() {
    List<RawData> data = rawDataService.getNewData();

    migrate(data);

    rawDataService.markAsMigrated(data);
  }

  private void migrate(List<RawData> data) {
    List<Contact> contacts = data.stream()
        .map(this::getContact)
        .collect(Collectors.toList());
    contactService.saveAll(contacts);
  }

  private Contact getContact(RawData data) {
    Optional<Contact> containing = contactService.findByEmailsContaining(data.getEmails());

    containing.ifPresent(contact -> {
      List<String> currentEmails = contact.getEmails().stream()
          .map(Email::getEmail)
          .collect(Collectors.toList());

      List<Email> newEmails = data.getEmails().stream()
          .filter(email -> !currentEmails.contains(email))
          .map(CollectorService::toEmail)
          .collect(Collectors.toList());

      contact.getEmails().addAll(newEmails);
    });

    return containing.orElseGet(() -> Contact.builder()
        .firstName(data.getFirstName())
        .secondName(data.getSecondName())
        .emails(toEmails(data.getEmails()))
        .build());
  }
}
