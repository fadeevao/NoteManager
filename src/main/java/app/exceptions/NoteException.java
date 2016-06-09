package app.exceptions;

public class NoteException extends Exception {
	
	public NoteException(String msg) {
		super(msg);
	}
	
	public NoteException(String message, Throwable cause) {
        super(message, cause);
    }

}
