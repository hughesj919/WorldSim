package application;

import java.io.Serializable;
import java.util.ArrayList;

public class Nation implements Serializable{

	private static final long serialVersionUID = 569741334000068828L;
	private int id;
	private float gnp;
	private String name;
	private ArrayList<Commodity> exports;
	private ArrayList<Commodity> imports;
	private ArrayList<Player> team;

	public Nation(int num) {
		id = num;
		exports = new ArrayList<Commodity>();
		imports = new ArrayList<Commodity>();
		gnp = 0;
	}
	
	@Override
	public String toString(){
		return name;
	}

	public void setGNP(float g) {
		gnp = g;
	}

	public void setID(int num) {
		id = num;
	}

	public void setName(String n) {
		name = n;
	}

	public void addExport(Commodity c) {
		exports.add(c);
	}

	public void addImport(Commodity c) {
		imports.add(c);
	}

	public int teamSize() {
		return team.size();
	}

	public float gnp() {
		return gnp;
	}

	public int id() {
		return id;
	}

	public String name() {
		return name;
	}

}
