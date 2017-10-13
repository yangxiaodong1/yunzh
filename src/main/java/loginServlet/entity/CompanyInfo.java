package loginServlet.entity;

public class CompanyInfo {
	public String id;//公司的编号
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String name;//公司的名字
	public String code;//公司的编码
	public String dtTime;//最后一次更改的时间
	public String num;//上传的总数
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDtTime() {
		return dtTime;
	}
	public void setDtTime(String dtTime) {
		this.dtTime = dtTime;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}

	
	
}
