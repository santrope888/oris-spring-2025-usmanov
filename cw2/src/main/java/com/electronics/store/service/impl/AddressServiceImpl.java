package com.electronics.store.service.impl;

import com.electronics.store.model.AddressEntity;
import com.electronics.store.repository.AddressRepository;
import com.electronics.store.service.AddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    @Override
    public List<AddressEntity> findAll() {
        return addressRepository.findAll();
    }
}
