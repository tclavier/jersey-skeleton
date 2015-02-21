package fr.iutinfo.bins;

public class Feedback {
	private boolean success;
	private String message;
	
	public Feedback() {
		this.success = true;
		this.message = new String();
	}
	
	public Feedback(boolean success, String message) {
		this.success = success;
		this.message = message;
	}
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
