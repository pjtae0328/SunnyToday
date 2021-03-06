package xyz.sunnytoday.dto;

import java.util.Date;

public class Message {
	private int message_no;	
	private int too;
	private int fromm;
	private Date post_date;
	private String title;
	private String content;
	private int rnum;
	private String fromNick;
	private String tooNick;
	
	@Override
	public String toString() {
		return "Message [message_no=" + message_no + ", too=" + too + ", fromm=" + fromm + ", post_date=" + post_date
				+ ", title=" + title + ", content=" + content + "]";
	}
	
	public String getTooNick() {
		return tooNick;
	}
	public void setTooNick(String tooNick) {
		this.tooNick = tooNick;
	}
	public String getFromNick() {
		return fromNick;
	}
	public void setFromNick(String fromNick) {
		this.fromNick = fromNick;
	}
	public int getRnum() {
		return rnum;
	}
	public void setRnum(int rnum) {
		this.rnum = rnum;
	}
	public int getMessage_no() {
		return message_no;
	}
	public void setMessage_no(int message_no) {
		this.message_no = message_no;
	}
	public int getToo() {
		return too;
	}
	public void setToo(int too) {
		this.too = too;
	}
	public int getFromm() {
		return fromm;
	}
	public void setFromm(int fromm) {
		this.fromm = fromm;
	}
	public Date getPost_date() {
		return post_date;
	}
	public void setPost_date(Date post_date) {
		this.post_date = post_date;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
}
