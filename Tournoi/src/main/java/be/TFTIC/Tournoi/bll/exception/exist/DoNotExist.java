package be.TFTIC.Tournoi.bll.exception.exist;

import be.TFTIC.Tournoi.bll.exception.TournamentException;

public class DoNotExist extends TournamentException {

  public DoNotExist(String message) {
    super(message, 404);
  }

  public DoNotExist(String message, int status) {
    super(message, status);
  }

  public DoNotExist(String message, Throwable cause, int status) {
    super(message, cause, status);
  }
}
