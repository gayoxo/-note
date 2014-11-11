/**
 * 
 */
package lector.share;

import java.io.Serializable;

/**
 * @author Joaquin Gayoso-Cabada
 *
 */
public class RuntimeAtNoteException extends Exception implements Serializable {
	
	private String Message;

	public RuntimeAtNoteException() {

	}
	
	public RuntimeAtNoteException(String message) {
		Message=message;
	}

	@Override
	public String getMessage() {
		return Message;
	}
	
	public void setMessage(String message) {
		Message = message;
	}
}
