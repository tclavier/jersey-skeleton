package fr.dashingames.ludicode_android.beans;

import android.os.Parcel;
import android.os.Parcelable;

public class LevelListAssociation implements Parcelable {
	private int idLevel;
	private int idList;
	
	@Override
	public String toString() {
		return "I'm asso and " + idLevel + " level and " + idList + " list";
	}
	
	public LevelListAssociation() { }
	public LevelListAssociation(Parcel in) {
		idLevel = in.readInt();
		idList = in.readInt();
	}

	public int getIdLevel() {
		return idLevel;
	}
	
	public void setIdLevel(int idLevel) {
		this.idLevel = idLevel;
	}
	
	public int getIdList() {
		return idList;
	}
	
	public void setIdList(int idList) {
		this.idList = idList;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(idLevel);
		dest.writeInt(idList);
	}
	
	public static final Parcelable.Creator<LevelListAssociation> CREATOR
	= new Parcelable.Creator<LevelListAssociation>() {
		public LevelListAssociation createFromParcel(Parcel in) {
			return new LevelListAssociation(in);
		}

		public LevelListAssociation[] newArray(int size) {
			return new LevelListAssociation[size];
		}
	};
}
