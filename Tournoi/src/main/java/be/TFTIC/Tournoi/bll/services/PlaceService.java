package be.TFTIC.Tournoi.bll.services;

import be.TFTIC.Tournoi.dl.entities.Place;
import be.TFTIC.Tournoi.pl.models.placeDTO.PlaceForm;

import java.util.List;

public interface PlaceService {
    Place getPlaceById(Long id);
    List<Place> getAllPlaces();
    Place createPlace(PlaceForm placeForm);
    Place updatePlace(Long id, PlaceForm placeForm);
    void deletePlace(Long id);
}