package model;

public class Patient {

    private int pat_ID;
    private String pat_Name;
    private int age;
    private String gender;
    private String phone;

    public Patient(int pat_ID, String pat_Name, int age, String gender, String phone) {
        this.pat_ID = pat_ID;
        this.pat_Name = pat_Name;
        this.age = age;
        this.gender = gender;
        this.phone = phone;
    }

    public int getPat_ID() {
        return pat_ID;
    }

    public String getPat_Name() {
        return pat_Name;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getPhone() {
        return phone;
    }
    
    public void setPat_ID(int pat_ID) {
		this.pat_ID = pat_ID;
	}

	public void setPat_Name(String pat_Name) {
		this.pat_Name = pat_Name;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	

    @Override
    public String toString() {
        return "Patient ID: " + pat_ID
                + ", Name: " + pat_Name
                + ", Age: " + age
                + ", Gender: " + gender
                + ", Phone: " + phone;
    }
}