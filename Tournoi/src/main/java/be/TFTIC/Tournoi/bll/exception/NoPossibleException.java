package be.TFTIC.Tournoi.bll.exception;

public class NoPossibleException extends TournamentException {

    public NoPossibleException(String message) {
        super(message,500);
    }
}
