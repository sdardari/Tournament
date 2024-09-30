package be.TFTIC.Tournoi.bll.exception.exist;

import be.TFTIC.Tournoi.bll.exception.TournamentException;

public class DoNotExistException extends TournamentException {

  public DoNotExistException(String message) {
    super(message, 404);
  }

  public DoNotExistException(String message, int status) {
    super(message, status);
  }

  public DoNotExistException(String message, Throwable cause, int status) {
    super(message, cause, status);
  }
}
