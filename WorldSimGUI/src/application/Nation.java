package application;

import java.io.Serializable;
import java.util.ArrayList;

public class Nation implements Serializable{

	private static final long serialVersionUID = 569741334000068828L;
	private int id;
	private double gnp;
	private double conventionalForces;
	private double nuclearForces;
	private String name;
	private ArrayList<Commodity> availableExports;
	private ArrayList<Commodity> exports;
	private ArrayList<Commodity> imports;
	private ArrayList<Player> team;

	public Nation(int num) {
		id = num;
		exports = new ArrayList<Commodity>();
		imports = new ArrayList<Commodity>();
		availableExports = new ArrayList<Commodity>();
		team = new ArrayList<Player>();
		gnp = 0;
		conventionalForces = 0;
		nuclearForces = 0;
	}
	
	@Override
	public String toString(){
		return name;
	}

	public void setGNP(double g) {
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
	
	public void addPlayer(Player p){
		team.add(p);
	}
	
	public void removePlayer(Player p){
		team.remove(p);
	}

	public int getTeamSize() {
		return team.size();
	}
	
	public boolean inUse(){
		return team.size() > 0;
	}

	public double getGnp() {
		return gnp;
	}
	
	public String getGnpString(){
		return Double.toString(gnp);
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public ArrayList<Player> getTeam(){
		return team;
	}
}
