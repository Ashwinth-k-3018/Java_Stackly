package model;

public class Doctor {

    private int doc_Id;
    private String doc_Name;
    private String doc_Specialization;

    public Doctor(int doc_Id, String doc_Name, String doc_Specialization) {
        this.doc_Id = doc_Id;
        this.doc_Name = doc_Name;
        this.doc_Specialization = doc_Specialization;
    }

    public int getDoc_Id() {
        return doc_Id;
    }

    public String getDoc_Name() {
        return doc_Name;
    }

    public String getDoc_Specialization() {
        return doc_Specialization;
    }

    public void setDoc_Id(int doc_Id) {
		this.doc_Id = doc_Id;
	}

	public void setDoc_Name(String doc_Name) {
		this.doc_Name = doc_Name;
	}

	public void setDoc_Specialization(String doc_Specialization) {
		this.doc_Specialization = doc_Specialization;
	}

	@Override
    public String toString() {
        return "Doctor ID: " + doc_Id
                + ", Name: " + doc_Name
                + ", Specialization: " + doc_Specialization;
    }

	
}