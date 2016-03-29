package fr.dashingames.ludicode_android.beans;


import android.os.Parcel;
import android.os.Parcelable;

public class Instruction implements Parcelable {

	private String name;
	private String code;
	private int block;
	private int color;
	private int id;
	
	private Instruction englobingBlock;

	public static final int IS_BLOCK_ELSE = 2;
	public static final int IS_BLOCK = 1;
	public static final int IS_NOT_BLOCK = 0;

	public Instruction() {	}

	public Instruction(String name, String code, int block, int color) {
		this.name = name;
		this.code = code;
		this.block = block;
		this.color = color;
	}

	public Instruction(Parcel in) {
		String[] data = new String[2];
		in.readStringArray(data);
		block = in.readInt();
		color = in.readInt();

		this.name = data[0];
		this.code = data[1];

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getBlock() {
		return block;
	}

	public void setBlock(int block) {
		this.block = block;
	}
	
	public boolean isBlock() {
		return block == IS_BLOCK || block == IS_BLOCK_ELSE;
	}
	
	public boolean isBlockWithElse() {
		return block == IS_BLOCK_ELSE;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
        this.color = color;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String toString() {
		return name;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		String[] data = new String[2];
		data[0] = name;
		data[1] = code;
		dest.writeStringArray(data);
		dest.writeInt(block);
		dest.writeInt(color);
	}

	public static final Parcelable.Creator<Instruction> CREATOR
	= new Parcelable.Creator<Instruction>() {
		public Instruction createFromParcel(Parcel in) {
			return new Instruction(in);
		}

		public Instruction[] newArray(int size) {
			return new Instruction[size];
		}
	};

	@Override
	public boolean equals(Object o) {
		Instruction instruction = (Instruction) o;
		return this.name.equals(instruction.getName());
	}

	public Instruction getEnglobingBlock() {
		return englobingBlock;
	}

	public void setEnglobingBlock(Instruction englobingBlock) {
		this.englobingBlock = englobingBlock;
	}
}
