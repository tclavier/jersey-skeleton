package fr.dashingames.ludicode_android.beans;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
	
	private int id = 0;
	private long facebookId;
	private String name;
	private String password;
	private String email;
	private String cookie;
	
	public User(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public User() {
		
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String toString() {
		return id + ": " + name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@Override
	public boolean equals(Object other){
		return this.id == ((User)other).id;
	}

	public long getFacebookId() {
		return facebookId;
	}

	public void setFacebookId(long facebookId) {
		this.facebookId = facebookId;
	}

	public String getCookie() {
		return cookie;
	}

	public void setCookie(String cookie) {
		this.cookie = cookie;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeString(name);
		dest.writeString(password);
		dest.writeString(email);
		dest.writeString(cookie);
	}
	
	public User(Parcel in) {
		id = in.readInt();
		name = in.readString();
		password = in.readString();
		email = in.readString();
		cookie = in.readString();
	}
	
	public static final Parcelable.Creator<User> CREATOR
	= new Parcelable.Creator<User>() {
		public User createFromParcel(Parcel in) {
			return new User(in);
		}

		public User[] newArray(int size) {
			return new User[size];
		}
	};

}
