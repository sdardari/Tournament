package be.TFTIC.Tournoi.pl.models.placeDTO;

import be.TFTIC.Tournoi.dl.entities.Address;
import be.TFTIC.Tournoi.dl.entities.Place;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaceForm {

    private String nameClub;
    private Long addressId;

    public Place toEntity(Address address) {

        Place place = new Place();
        place.setNameClub(this.nameClub);
        place.setAddress(address);
        return place;
    }
}
