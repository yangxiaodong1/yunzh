package loginServlet.entity;

public class TCustomer {
  private String id;//公司id
  private String name;//公司名称
  private String code;//公司编码
  private String dtTime;//公司最近一次票据结算日期
  private String num;//已经上传的数量
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
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
