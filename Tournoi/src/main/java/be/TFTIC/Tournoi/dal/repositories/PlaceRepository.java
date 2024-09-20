package be.TFTIC.Tournoi.dal.repositories;

import be.TFTIC.Tournoi.dl.entities.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Integer> {
}
