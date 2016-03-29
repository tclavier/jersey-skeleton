package fr.dashingames.ludicode_android.tests;

public class ComplexArrayObject {

	private long binarySize;
	private SimpleObject[] array;
	
	public ComplexArrayObject(long size, SimpleObject[] array) {
		this.binarySize = size;
		this.array = array;
	}
	
	public long getBinarySize() {
		return binarySize;
	}
	
	public void setBinarySize(long size) {
		this.binarySize = size;
	}
	
	public SimpleObject[] getArray() {
		return array;
	}
	
	public void setArray(SimpleObject[] array) {
		this.array = array;
	}
	
}
