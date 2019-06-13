package com.example.contactsaggregator.rawdata;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface RawDataRepository extends PagingAndSortingRepository<RawData, Long> {
  List<RawData> findAll();
}
