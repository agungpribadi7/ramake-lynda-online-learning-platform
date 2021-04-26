package edu.stts;

public class instructorForAdmin {
	protected int accepted,banned,id,verification;
	protected String description,email,photo,intro,name;
	
	public instructorForAdmin(int accepted, int banned, int id, int verification, String description,
			String email, String photo, String intro, String name) {
		super();
		this.accepted = accepted;
		this.banned = banned;
		this.id = id;
		this.verification = verification;
		this.description = description;
		this.email = email;
		this.photo = "https://storage.googleapis.com/proyek-cc-lynda.appspot.com/foto/" + photo;
		this.intro = intro;
		this.name = name;
	}

	public int getAccepted() {
		return accepted;
	}

	public void setAccepted(int accepted) {
		this.accepted = accepted;
	}

	public int getBanned() {
		return banned;
	}

	public void setBanned(int banned) {
		this.banned = banned;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getVerification() {
		return verification;
	}

	public void setVerification(int verification) {
		this.verification = verification;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
