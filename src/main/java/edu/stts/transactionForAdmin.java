package edu.stts;

public class transactionForAdmin {
	protected int accepted, total, id, iduser,tipe;
	protected String date;
	public transactionForAdmin(int accepted, int total, int id, int iduser, String date, int tipe) {
		super();
		this.accepted = accepted;
		this.total = total;
		this.id = id;
		this.iduser = iduser;
		this.date = date;
		this.tipe = tipe;
	}
	public int getAccepted() {
		return accepted;
	}
	public void setAccepted(int accepted) {
		this.accepted = accepted;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIduser() {
		return iduser;
	}
	public void setIduser(int iduser) {
		this.iduser = iduser;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getTipe() {
		return tipe;
	}
	public void setTipe(int tipe) {
		this.tipe = tipe;
	}
	
}
