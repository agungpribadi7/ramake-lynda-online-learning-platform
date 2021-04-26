package edu.stts;

public class courseForAdmin {
	protected int ban, id, free, like, viewer;
	protected String desc,instructor,judul,released,subtopic,topic,thumbnail,video;
	public courseForAdmin(int ban, int id, int free, int like, int viewer, String desc, String instructor, String judul,
			String released, String subtopic, String topic, String thumbnail, String video) {
		super();
		this.ban = ban;
		this.id = id;
		this.free = free;
		this.like = like;
		this.viewer = viewer;
		this.desc = desc;
		this.instructor = instructor;
		this.judul = judul;
		this.released = released;
		this.subtopic = subtopic;
		this.topic = topic;
		this.thumbnail = "https://storage.googleapis.com/lynda-310811.appspot.com/thumbnail/" + thumbnail;
		this.video = "https://storage.googleapis.com/lynda-310811.appspot.com/video/" + video;
	}
	public int getBan() {
		return ban;
	}
	public void setBan(int ban) {
		this.ban = ban;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getFree() {
		return free;
	}
	public void setFree(int free) {
		this.free = free;
	}
	public int getLike() {
		return like;
	}
	public void setLike(int like) {
		this.like = like;
	}
	public int getViewer() {
		return viewer;
	}
	public void setViewer(int viewer) {
		this.viewer = viewer;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getInstructor() {
		return instructor;
	}
	public void setInstructor(String instructor) {
		this.instructor = instructor;
	}
	public String getJudul() {
		return judul;
	}
	public void setJudul(String judul) {
		this.judul = judul;
	}
	public String getReleased() {
		return released;
	}
	public void setReleased(String released) {
		this.released = released;
	}
	public String getSubtopic() {
		return subtopic;
	}
	public void setSubtopic(String subtopic) {
		this.subtopic = subtopic;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	public String getVideo() {
		return video;
	}
	public void setVideo(String video) {
		this.video = video;
	}
	
}
