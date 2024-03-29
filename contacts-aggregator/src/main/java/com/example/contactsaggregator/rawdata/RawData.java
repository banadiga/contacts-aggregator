package com.example.contactsaggregator.rawdata;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class RawData {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "second_name")
  private String secondName;

  @Column(name = "username")
  private String username;

  @Column(name = "email1")
  private String email1;
  @Column(name = "email2")
  private String email2;
  @Column(name = "email3")
  private String email3;
  @Column(name = "email4")
  private String email4;

  @Builder.Default
  @Column(name = "migrate")
  private Boolean migrated = false;
  @Column(name = "phone1")
  private String phone1;
  @Column(name = "phone2")
  private String phone2;
  @Column(name = "phone3")
  private String phone3;
  @Column(name = "phone4")
  private String phone4;

  public Collection<String> getEmails() {
    return Stream.of(email1, email2, email3, email4)
        .filter(Objects::nonNull)
        .collect(Collectors.toList());
  }

  public Collection<String> getPhones() {
    return Stream.of(phone1, phone2, phone3, phone4)
        .filter(Objects::nonNull)
        .collect(Collectors.toList());
  }
}
