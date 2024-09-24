package be.TFTIC.Tournoi.bll.services;

import be.TFTIC.Tournoi.dl.entities.Place;
import java.util.List;

public interface PlaceService {
    Place getPlaceById(Long id);
    List<Place> getAllPlaces();
    Place createPlace(Place place);
    Place updatePlace(Long id, Place place);
    void deletePlace(Long id);
}