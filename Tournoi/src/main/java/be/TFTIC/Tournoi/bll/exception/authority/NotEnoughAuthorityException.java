package be.TFTIC.Tournoi.bll.exception.authority;

import be.TFTIC.Tournoi.bll.exception.TournamentException;

public class NotEnoughAuthorityException extends TournamentException {

    public NotEnoughAuthorityException(String message) {
        super(message, 500);
    }
}
