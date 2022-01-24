package classes;

public class Karyawan {
	
	private String code;
	private String name;
	private String gender;
	private String position;
	private int salary;
	
	public Karyawan(String code, String name, String gender, String position, int salary) {
		this.code = code;
		this.name = name;
		this.gender = gender;
		this.position = position;
		this.salary = salary;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

}
