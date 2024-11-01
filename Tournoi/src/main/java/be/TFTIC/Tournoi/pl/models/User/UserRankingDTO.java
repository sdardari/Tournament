package be.TFTIC.Tournoi.pl.models.User;

import be.TFTIC.Tournoi.dl.entities.Ranking;
import be.TFTIC.Tournoi.dl.entities.User;

public record UserRankingDTO(Long id, String username, String firstname, String lastname, String email, Ranking ranking) {

  public static UserRankingDTO fromEntity(User user) {
    return new UserRankingDTO(
      user.getId(),
      user.getUsername(),
      user.getFirstname(),
      user.getLastname(),
      user.getEmail(),
      user.getRanking()
    );
  }
}
