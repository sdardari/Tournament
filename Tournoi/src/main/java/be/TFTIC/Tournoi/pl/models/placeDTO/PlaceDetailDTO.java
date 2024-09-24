package be.TFTIC.Tournoi.pl.models.placeDTO;

import be.TFTIC.Tournoi.dl.entities.Place;
import lombok.Data;

@Data
public class PlaceDetailDTO {
    private Long id;
    private String nameClub;
    private String address;

    public static PlaceDetailDTO fromPlace(Place place) {
        PlaceDetailDTO dto = new PlaceDetailDTO();
        dto.setId(place.getId());
        dto.setNameClub(place.getNameClub());
        dto.setAddress(place.getAddress().toString());
        return dto;
    }
}
