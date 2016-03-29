package fr.dashingames.ludicode_android.tests;

public class ComplexObject {

	private SimpleObject obj;
	private int number;
	
	public ComplexObject(SimpleObject obj, int number) {
		super();
		this.obj = obj;
		this.number = number;
	}
	
	public SimpleObject getObj() {
		return obj;
	}
	
	public void setObj(SimpleObject obj) {
		this.obj = obj;
	}
	
	public int getNumber() {
		return number;
	}
	
	public void setNumber(int number) {
		this.number = number;
	}	
}
