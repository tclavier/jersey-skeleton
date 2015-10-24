package fr.iutinfo.skeleton.api;

public class User {
	private String name;
	private String alias;
	private int id = 0;

	public User(int id, String name) {
		this.id = id;
		this.name = name;
	}

    public User(int id, String name, String alias) {
        this.id = id;
        this.name = name;
        this.alias = alias;
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

	@Override
    public boolean equals(Object arg) {
        if (getClass() != arg.getClass())
            return false;
        User user = (User) arg;
        return name.equals(user.name) && alias.equals(user.alias);
	}

	@Override
    public String toString() {
		return id + ": " + alias + ", " +name;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}
}
