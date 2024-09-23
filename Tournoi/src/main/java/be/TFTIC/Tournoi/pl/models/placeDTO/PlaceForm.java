package be.TFTIC.Tournoi.pl.models.placeDTO;

import be.TFTIC.Tournoi.dl.entities.Address;
import be.TFTIC.Tournoi.dl.entities.Place;

public class PlaceForm {

    private String nameClub;
    private Address address;

    public Place toEntity() {
        Place place = new Place();
                place.setNameClub(this.nameClub);
                place.setAddress(this.address);
                return place;
    }
}
