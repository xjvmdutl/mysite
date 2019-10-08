package kr.co.itcen.mysite.vo;

public class PictureVo {
	private Long no;
	private String url;
	private String filename;
	private Long board_no;
	public Long getNo() {
		return no;
	}
	public void setNo(Long no) {
		this.no = no;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public Long getBoard_no() {
		return board_no;
	}
	public void setBoard_no(Long board_no) {
		this.board_no = board_no;
	}
	@Override
	public String toString() {
		return "PictureVo [no=" + no + ", url=" + url + ", filename=" + filename + ", board_no=" + board_no + "]";
	}
}
