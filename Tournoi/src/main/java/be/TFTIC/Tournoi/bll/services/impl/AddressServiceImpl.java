package be.TFTIC.Tournoi.bll.services.impl;

import be.TFTIC.Tournoi.bll.exception.exist.DoNotExistException;
import be.TFTIC.Tournoi.bll.services.AddressService;
import be.TFTIC.Tournoi.dal.repositories.AddressRepository;
import be.TFTIC.Tournoi.dl.entities.Address;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    @Override
    public Address findById(Long addressId) {
        return addressRepository.findById(addressId)
                .orElseThrow(() -> new DoNotExistException("this address do no exist!"));
    }
}
