package app.model;

public class NoteException extends Exception {

	private static final long serialVersionUID = -4972545290276741760L;
	
	public NoteException(String msg) {
		super(msg);
	}
	
	public NoteException(String message, Throwable cause) {
        super(message, cause);
    }

}
