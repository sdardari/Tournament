package be.TFTIC.Tournoi.bll.exception.member;

import be.TFTIC.Tournoi.bll.exception.TournamentException;

public class NotMemberException extends TournamentException {


    public NotMemberException(String message) {
        super(message,500);
    }

    public NotMemberException() {
        super("User is not a member of this chat.",500);
    }
}
