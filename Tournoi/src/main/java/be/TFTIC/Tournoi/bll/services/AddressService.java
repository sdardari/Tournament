package be.TFTIC.Tournoi.bll.services;

import be.TFTIC.Tournoi.dl.entities.Address;

public interface AddressService {

    Address findById (Long addressId);
}
