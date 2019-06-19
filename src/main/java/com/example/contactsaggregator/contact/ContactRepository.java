package com.example.contactsaggregator.contact;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface ContactRepository extends PagingAndSortingRepository<Contact, Long> {
  List<Contact> findAll();
}
