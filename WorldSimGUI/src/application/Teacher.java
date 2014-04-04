package application;

import java.io.Serializable;
import java.util.ArrayList;

public class Teacher implements Serializable{

	private static final long serialVersionUID = -1182178369466897524L;
	private String name;
	private ArrayList<Player> students;

	public Teacher(String n) {
		name = n;
	}
	
	public String toString(){
		return name;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String n){
		name = n;
	}
	
	public ArrayList<Player> getStudents(){
		return students;
	}

}
