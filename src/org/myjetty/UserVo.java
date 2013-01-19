package org.myjetty;

public class UserVo {

	String name;
	Boolean Sex;
	int age;
	String PhoneNumber;
	
	public UserVo(String name, Boolean sex, int age, String phoneNumber) {
		super();
		this.name = name;
		Sex = sex;
		this.age = age;
		PhoneNumber = phoneNumber;
	}
	
	public UserVo() {
		super();
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Boolean getSex() {
		return Sex;
	}
	public void setSex(Boolean sex) {
		Sex = sex;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getPhoneNumber() {
		return PhoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		PhoneNumber = phoneNumber;
	}
	
	@Override
	public String toString() {
		return "UserVo [name=" + name + ", Sex=" + Sex + ", age=" + age
				+ ", PhoneNumber=" + PhoneNumber + "]";
	}
}
