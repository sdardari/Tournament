package be.TFTIC.Tournoi.bll.exception.member;

import be.TFTIC.Tournoi.bll.exception.TournamentException;

public class AlreadyMemberException extends TournamentException {

    public AlreadyMemberException(String message) {
        super(message);
    }
}
