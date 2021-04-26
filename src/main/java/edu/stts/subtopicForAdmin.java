package edu.stts;

public class subtopicForAdmin{
	protected int index_id,id;
	protected String idtopic,name;
	public subtopicForAdmin(int index_id, String idtopic, int id, String name) {
		// TODO Auto-generated constructor stub
		this.index_id = index_id;
		this.idtopic = idtopic;
		this.id = id;
		this.name = name;
	}
	public int getIndex_id() {
		return index_id;
	}
	public void setIndex_id(int index_id) {
		this.index_id = index_id;
	}
	public String getidtopic() {
		return idtopic;
	}
	public void setidtopic(String idtopic) {
		this.idtopic = idtopic;
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
	
	
}
