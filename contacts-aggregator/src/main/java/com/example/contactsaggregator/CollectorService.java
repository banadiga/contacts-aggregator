package com.example.contactsaggregator;

import lombok.AllArgsConstructor;

import com.example.contactsaggregator.contact.Contact;
import com.example.contactsaggregator.contact.ContactService;
import com.example.contactsaggregator.contact.Email;
import com.example.contactsaggregator.contact.Phone;
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

  private static Phone toPhone(String phone) {
    return Phone.builder().phone(phone).build();
  }

  private static List<Email> toEmails(Collection<String> emails) {
    return emails.stream().map(CollectorService::toEmail).collect(Collectors.toList());
  }

  private static List<Phone> toPhone(Collection<String> emails) {
    return emails.stream().map(CollectorService::toPhone).collect(Collectors.toList());
  }

  @Scheduled(fixedDelay = 1000)
  public void migrate() {
    List<RawData> data = rawDataService.getNewData();

    List<Contact> contacts = data.stream()
        .map(this::getContact)
        .collect(Collectors.toList());
    contactService.saveAll(contacts);

    rawDataService.markAsMigrated(data);
  }

  private Contact getContact(RawData data) {
    Optional<Contact> containing = contactService.findBy(data.getEmails(), data.getPhones());

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
//        .phones(toPhone(data.getPhones()))
        .build());
  }
}
