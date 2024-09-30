package be.TFTIC.Tournoi.bll.exception;

import lombok.Getter;

@Getter
public class TournamentException extends RuntimeException{

    private final int status;
    private final Object message;

    public TournamentException(String message){
        super(message);
        this.status = 500;
        this.message = message;
    }

    public TournamentException(String message, int status){
        super(message);
        this.status = status;
        this.message = message;
    }
    public TournamentException(String message, Throwable cause, int status) {
        super(message, cause);
        this.status = status;
        this.message = message;
    }

    public Object getCustomMessage(){
        return message;
    }
    @Override
    public String getMessage(){
        return message.toString();
    }

    @Override
    public String toString() {
        StackTraceElement element = this.getStackTrace()[0];
        return String.format("%s" + " thrown in " + "%s" + "() at " + "%s:%d"  + " with message: " + "%s",
                this.getClass().getSimpleName(),
                element.getMethodName(),
                element.getFileName(),
                element.getLineNumber(),
                this.getMessage());
    }
}
