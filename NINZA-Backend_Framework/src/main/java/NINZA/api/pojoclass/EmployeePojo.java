package NINZA.api.pojoclass;

public class EmployeePojo {

	String designation;
	String dob;
	String email;
	String empName;
	int experience;
	String mobileNo;
	String project;
	String role;
	String username;
	
	public EmployeePojo(String designation, String dob, String email, String empName,int experience,String mobileNo,String project,String role,String username) {
		super();
		this.designation = designation;
		this.dob = dob;
		this.email = email;
		this.empName = empName;
		this.experience = experience;
		this.mobileNo = mobileNo;
		this.project = project;
		this.role = role;
		this.username = username;
	}
	
	public EmployeePojo() {}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public int getExperience() {
		return experience;
	}

	public void setExperience(int experience) {
		this.experience = experience;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	
	
}
