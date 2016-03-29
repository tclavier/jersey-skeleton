package fr.iutinfo.beans;

/**
 * Associate a boolean of a state with a String message.
 * Uses in multiples files to give a return status to the server
 *  about the use actions
 * @author Florent
 */
public class Feedback {
    private boolean success;
    private String message;
	
    /**
     * Constructor of Feedback
     * @param success
     * @param message 
     */
    public Feedback(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
    
    /**
     * No parameter constructor of Feedback
     *  default : boolean true, string null
     */
    public Feedback() {
        this.success = true;
        this.message = new String();
    }
	
    public boolean getSuccessState() {
	return success;
    }
    public void setSuccessState(boolean success) {
	this.success = success;
    }
    
    public String getMessage() {
	return message;
    }
    public void setMessage(String message) {
	this.message = message;
    }
}
