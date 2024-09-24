package be.TFTIC.Tournoi.pl.controllers;

import be.TFTIC.Tournoi.bll.services.PlaceService;
import be.TFTIC.Tournoi.dl.entities.Place;
import be.TFTIC.Tournoi.pl.models.placeDTO.PlaceDetailDTO;
import be.TFTIC.Tournoi.pl.models.placeDTO.PlaceForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/places")
@RequiredArgsConstructor
public class PlaceController {

    private final PlaceService placeService;

    @GetMapping
    public ResponseEntity<List<PlaceDetailDTO>> getAllPlaces() {
        List<Place> places = placeService.getAllPlaces();
        List<PlaceDetailDTO> placeDTOs = places.stream()
                .map(PlaceDetailDTO::fromPlace)
                .toList();
        return ResponseEntity.ok(placeDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlaceDetailDTO> getPlaceById(@PathVariable Long id) {
        Place place = placeService.getPlaceById(id);
        PlaceDetailDTO placeDTO = PlaceDetailDTO.fromPlace(place);
        return ResponseEntity.ok(placeDTO);
    }

    @PostMapping("/create")
    public ResponseEntity<PlaceDetailDTO> createPlace(@RequestBody PlaceForm placeForm) {
        Place place = placeForm.toEntity();
        Place createdPlace = placeService.createPlace(place);
        return ResponseEntity.ok(PlaceDetailDTO.fromPlace(createdPlace));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlaceDetailDTO> updatePlace(@PathVariable Long id, @RequestBody PlaceForm placeForm) {
        Place updatedPlace = placeService.updatePlace(id, placeForm.toEntity());
        return ResponseEntity.ok(PlaceDetailDTO.fromPlace(updatedPlace));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlace(@PathVariable Long id) {
        placeService.deletePlace(id);
        return ResponseEntity.noContent().build();
    }
}