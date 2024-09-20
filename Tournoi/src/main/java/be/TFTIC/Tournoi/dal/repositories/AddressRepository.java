package be.TFTIC.Tournoi.dal.repositories;

import be.TFTIC.Tournoi.dl.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
