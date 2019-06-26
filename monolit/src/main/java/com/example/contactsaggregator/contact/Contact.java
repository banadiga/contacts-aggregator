package com.example.contactsaggregator.contact;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Contact {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "second_name")
  private String secondName;

  @Singular
  @OneToMany(
      fetch = FetchType.EAGER,
      cascade = CascadeType.ALL,
      orphanRemoval = true
  )
  private List<Email> emails = new ArrayList<>();

  @Singular
  @OneToMany(
      fetch = FetchType.EAGER,
      cascade = CascadeType.ALL,
      orphanRemoval = true
  )
  private List<Phone> phones = new ArrayList<>();
}
