package be.TFTIC.Tournoi.bll.exception.noMatching;

import be.TFTIC.Tournoi.bll.exception.TournamentException;

public class NoMatchingException extends TournamentException {

  public NoMatchingException(String message) {
    super(message, 400);
  }

  public NoMatchingException(String message, int status) {
    super(message, status);
  }

  public NoMatchingException(String message, Throwable cause, int status) {
    super(message, cause, status);
  }

}

