package be.TFTIC.Tournoi.dl.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Entity
    public class Address {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String street;
        private String number;
        private String postalCode;
        private String city;
        private String country;

        @Override
        public String toString() {
            return street + " " + number + ", " + postalCode + " " + city + ", " + country;
        }

        public Address(String street, String number, String postalCode, String city, String country) {
            this.street = street;
            this.number = number;
            this.postalCode = postalCode;
            this.city = city;
            this.country = country;
        }

    }
