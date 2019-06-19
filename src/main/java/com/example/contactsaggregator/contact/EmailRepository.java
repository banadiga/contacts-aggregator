package com.example.contactsaggregator.contact;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface EmailRepository extends PagingAndSortingRepository<Email, Long> {
}
