package com.theironyard.charlotte;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AddressesRepo extends PagingAndSortingRepository<Addresses, Integer> {
    Page<Addresses> findByState(Pageable pageable, String state);
}