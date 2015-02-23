package fr.iutinfo.beans;

public class FriendRelation {
	private int userId;
	private int friendId;
	
	public FriendRelation() {
		userId = 0;
		friendId = 0;
	}
	
	public FriendRelation(int userId, int friendId) {
		this.userId = userId;
		this.friendId = friendId;
	}
	
	public int getUserId() {
		return userId;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public int getFriendId() {
		return friendId;
	}
	
	public void setFriendId(int friendId) {
		this.friendId = friendId;
	}
}
