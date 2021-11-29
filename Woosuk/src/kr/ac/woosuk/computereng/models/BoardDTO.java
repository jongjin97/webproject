package kr.ac.woosuk.computereng.models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class BoardDTO {
	private int id;
	private String writer;
	private Date createDate;
	private String title;
	private String subTitle;
	private String contents;
	private AttachFileDTO attachFile;
	private String getFormatDate;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSubTitle() {
		return subTitle;
	}
	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public AttachFileDTO getAttachFile() {
		return attachFile;
	}
	public void setAttachFile(AttachFileDTO attachFile) {
		this.attachFile = attachFile;
	}
	public String getGetFormatDate() {
		return getFormatDate;
	}
	public String getFormatDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY/MM/dd HH:mm:ss");
		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+9"));
		return dateFormat.format(this.createDate);
	}
	
}
