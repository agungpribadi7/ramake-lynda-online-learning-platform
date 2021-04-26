package edu.stts;

public class studentForAdmin {
	protected int banned,id,verification,wallet;
	protected String email,photo,name;
	public studentForAdmin(int banned, int id, int verification, int wallet, String email, String photo, String name) {
		super();
		this.banned = banned;
		this.id = id;
		this.verification = verification;
		this.wallet = wallet;
		this.email = email;
		this.photo = "https://storage.googleapis.com/proyek-cc-lynda.appspot.com/foto/" + photo;
		this.name = name;
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
	public int getWallet() {
		return wallet;
	}
	public void setWallet(int wallet) {
		this.wallet = wallet;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
