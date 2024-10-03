package be.TFTIC.Tournoi.bll.services.impl;

import be.TFTIC.Tournoi.bll.exception.exist.DoNotExistException;
import be.TFTIC.Tournoi.bll.services.AddressService;
import be.TFTIC.Tournoi.bll.services.PlaceService;
import be.TFTIC.Tournoi.dal.repositories.PlaceRepository;
import be.TFTIC.Tournoi.dl.entities.Address;
import be.TFTIC.Tournoi.dl.entities.Place;
import be.TFTIC.Tournoi.pl.models.placeDTO.PlaceForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaceServiceImpl implements PlaceService {

    private final PlaceRepository placeRepository;
    private final AddressService addressService;

    @Override
    public Place getPlaceById(Long id) {
        return placeRepository.findById(id)
                .orElseThrow(() -> new DoNotExistException("Place not found"));
    }

    @Override
    public List<Place> getAllPlaces() {
        return placeRepository.findAll();
    }

    @Override
    public Place createPlace(PlaceForm placeForm) {
        Address address = addressService.findById(placeForm.getAddressId());
        Place place = placeForm.toEntity(address);
        return placeRepository.save(place);
    }

    @Override
    public Place updatePlace(Long id, PlaceForm placeForm) {
        Place existingPlace = getPlaceById(id);
        Address address = addressService.findById(placeForm.getAddressId());
        existingPlace.setNameClub(placeForm.getNameClub());
        existingPlace.setAddress(address);
        return placeRepository.save(existingPlace);
    }

    @Override
    public void deletePlace(Long id) {
        placeRepository.deleteById(id);
    }
}