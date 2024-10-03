package be.TFTIC.Tournoi.bll.exception.request;

import be.TFTIC.Tournoi.bll.exception.TournamentException;

public class BadRequestException extends TournamentException {
    public BadRequestException(String message) {
        super(message, 500);
    }

    public BadRequestException(String message, int status) {
        super(message, status);
    }

    public BadRequestException(String message, Throwable cause, int status) {
        super(message, cause, status);
    }
}
