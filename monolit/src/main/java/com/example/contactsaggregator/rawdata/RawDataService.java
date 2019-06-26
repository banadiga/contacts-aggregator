package com.example.contactsaggregator.rawdata;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

@AllArgsConstructor
@Service
public class RawDataService {

  private RawDataRepository rawDataRepository;

  @PostConstruct
  public void init() {
    RawData rawData1 = RawData.builder()
        .firstName("Igor")
        .secondName("Banadiga")
        .email1("test1@email.com")
        .email2("my@email.com")
        .build();
    rawDataRepository.save(rawData1);

    RawData rawData2 = RawData.builder()
        .firstName("Ivan")
        .secondName("Ivanov")
        .email3("test2@email.com")
        .build();
    rawDataRepository.save(rawData2);
  }

  public List<RawData> getNewData() {
    return rawDataRepository.findAll().stream()
        .filter(rawData -> !rawData.getMigrated())
        .collect(Collectors.toList());
  }

  public void markAsMigrated(List<RawData> data) {
    if (data.size() == 0) {
      return;
    }

    data.forEach(rawData -> rawData.setMigrated(true));
    rawDataRepository.saveAll(data);
  }
}
