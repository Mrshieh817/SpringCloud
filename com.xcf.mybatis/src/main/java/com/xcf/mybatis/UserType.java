package com.xcf.mybatis;
/** 
* @author xcf 
* @Date 创建时间：2021年6月11日 上午10:13:55 
*/
public enum UserType {
	
	exstatus("1","失效","2021-6-11 10:17:40","2071-6-11 10:17:45","5"),
	restatus("2","真实","2021-6-11 10:17:40","2071-6-11 10:17:45","5"),
	destatus("3","删除","2021-6-11 10:17:40","2071-6-11 10:17:45","5");
	
	private String code;
	private String name;
	private String startTime;
	private String endTime;
	private String exprise;


	public String getCode() {return code;}
	public void setCode(String code) {this.code = code;}
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
	public String getStartTime() {return startTime;}
	public void setStartTime(String startTime) {this.startTime = startTime;}
	public String getEndTime() {return endTime;}
	public void setEndTime(String endTime) {this.endTime = endTime;}
	public String getExprise() {return exprise;}
	public void setExprise(String exprise) {this.exprise = exprise;}

	UserType(String code,String name,String startTime,String endTime,String exprise){
		this.code=code;
		this.name=name;
		this.startTime=startTime;
		this.endTime=endTime;
		this.exprise=exprise;
	}
	
	@SuppressWarnings("unused")
	public static String getString (String code) {
		UserType [] userType=UserType.values();
		for (UserType userType2 : userType) {
			if (userType2.getCode().equals(code)) {
				return userType2.getName();
			}
		}
		return "";
	}

}
