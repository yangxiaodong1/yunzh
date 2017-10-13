package loginServlet.entity;

import java.util.List;

public class CustomerInfo {
	
	public String userName;
	public String telephone;
	public String userPicUrl;
	public List<TCustomer> TCustomer;
	public List<TBillInfo> siList;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getUserPicUrl() {
		return userPicUrl;
	}
	public void setUserPicUrl(String userPicUrl) {
		this.userPicUrl = userPicUrl;
	}
	
	public List<TCustomer> getTCustomer() {
		return TCustomer;
	}
	public void setTCustomer(List<TCustomer> tCustomer) {
		TCustomer = tCustomer;
	}
	public List<TBillInfo> getSiList() {
		return siList;
	}
	public void setSiList(List<TBillInfo> siList) {
		this.siList = siList;
	}
	
	
	
	

}
