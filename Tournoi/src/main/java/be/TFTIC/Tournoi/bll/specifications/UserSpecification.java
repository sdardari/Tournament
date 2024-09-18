package be.TFTIC.Tournoi.bll.specifications;

import be.TFTIC.Tournoi.dl.entities.User;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {

    public static Specification<User> hasUsername(String username) {
        return (root, _, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("username")),"%" + username+"%");
    }

    public static Specification<User> hasFirstname(String firstname) {
        return (root, _, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("firstname")),"%" + firstname+"%");
    }

    public static Specification<User> hasLastname(String lastname) {
        return (root, _, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("lastname")),"%" + lastname+"%");
    }

    public static Specification<User> hasEmail(String email) {
        return (root, _, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("email")),"%" + email+"%");
    }
}

