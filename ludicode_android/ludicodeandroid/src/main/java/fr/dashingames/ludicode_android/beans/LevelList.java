package fr.dashingames.ludicode_android.beans;

import android.os.Parcel;
import android.os.Parcelable;

public class LevelList implements Parcelable {
	private int id;
	private String name;
	private int levelCount;
	private LevelListAssociation[] levelsAssociation;

	public LevelList() { }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LevelListAssociation[] getLevelsAssociation() {
		return levelsAssociation;
	}

	public void setLevelsAssociation( LevelListAssociation[] levelsAssociation) {
		this.levelsAssociation = levelsAssociation;
	}

	public int getLevelCount() {
		return levelCount;
	}

	public void setLevelCount(int levelCount) {
		this.levelCount = levelCount;
	}

	public String toString() {
		String ch = getLevelCount() > 1 ? "x":"";
		return getName() + " (" + getLevelCount() + " niveau" + ch + ")";
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public LevelList(Parcel in) {
		id = in.readInt();
		name = in.readString();
		levelCount = in.readInt();
		levelsAssociation = (LevelListAssociation[]) in.readParcelableArray(LevelListAssociation.class.getClassLoader());
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeString(name);
		dest.writeInt(levelCount);
		dest.writeParcelableArray(levelsAssociation, flags);
	}

	public static final Parcelable.Creator<LevelList> CREATOR
	= new Parcelable.Creator<LevelList>() {
		public LevelList createFromParcel(Parcel in) {
			return new LevelList(in);
		}

		public LevelList[] newArray(int size) {
			return new LevelList[size];
		}
	};

}
