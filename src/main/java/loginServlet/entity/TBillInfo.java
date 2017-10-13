package loginServlet.entity;

public class TBillInfo {
	private static final long serialVersionUID = 1L;
private String imgName;//图片票据的名称
  private String dt;//日期
  private String CompanyName;//公司名称
  private String type;//图片的类型
  private String size;//图片的大小
  private String upload_period;
  private String image_url;
  private String abstract_msg;
  private String total_price;
  private String customer_id;
  private String id;
  private String user_loginname;//上传系统登录用户名
  
  
public String getUser_loginname() {
	return user_loginname;
}
public void setUser_loginname(String user_loginname) {
	this.user_loginname = user_loginname;
}
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
private String remarks;
  public String getUpload_period() {
	return upload_period;
}
public void setUpload_period(String upload_period) {
	this.upload_period = upload_period;
}
public String getImage_url() {
	return image_url;
}
public void setImage_url(String image_url) {
	this.image_url = image_url;
}
public String getAbstract_msg() {
	return abstract_msg;
}
public void setAbstract_msg(String abstract_msg) {
	this.abstract_msg = abstract_msg;
}
public String getTotal_price() {
	return total_price;
}
public void setTotal_price(String total_price) {
	this.total_price = total_price;
}
public String getCustomer_id() {
	return customer_id;
}
public void setCustomer_id(String customer_id) {
	this.customer_id = customer_id;
}
public String getRemarks() {
	return remarks;
}
public void setRemarks(String remarks) {
	this.remarks = remarks;
}
public String getImgName() {
		return imgName;
	}
	public void setImgName(String imgName) {
		this.imgName = imgName;
	}
public String getDt() {
	return dt;
}
public void setDt(String dt) {
	this.dt = dt;
}
public String getCompanyName() {
	return CompanyName;
}
public void setCompanyName(String companyName) {
	CompanyName = companyName;
}
public String getType() {
	return type;
}
public void setType(String type) {
	this.type = type;
}
public String getSize() {
	return size;
}
public void setSize(String size) {
	this.size = size;
}
  
  
  
}
