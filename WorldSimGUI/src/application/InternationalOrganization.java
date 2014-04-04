package application;

import java.io.Serializable;
import java.util.ArrayList;

public class InternationalOrganization implements Serializable{
	
	private static final long serialVersionUID = -3689617648320276489L;
	private String name;
	private String password;
	private ArrayList<Nation> includedNations;

	public InternationalOrganization(String n, String pwd) {
		name = n;
		includedNations = new ArrayList<Nation>();
		password = pwd;
	}
	
	@Override
	public String toString(){
		return name;
	}
	
	public void setName(String n){
		name = n;
	}
	
	public String getName(){
		return name;
	}
	
	public String getPassword(){
		return password;
	}
	
	public void setPassword(String pwd){
		password = pwd;
	}
	
	public boolean validatePassword(String pwd){
		return pwd.equals(password);
	}
	
	public ArrayList<Nation> getNations(){
		return includedNations;
	}
	
	public void addNation(Nation n){
		includedNations.add(n);
	}
}
