package application;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;

public class Nation implements Serializable {

	private static final long serialVersionUID = 569741334000068828L;
	private String countryCode;
	private BigDecimal gnp;
	private BigDecimal conventionalForces;
	private BigDecimal nuclearForces;
	private String name;
	private ArrayList<Commodity> availableExports;
	private ArrayList<Commodity> requiredImports;
	private ArrayList<Player> team;
	private boolean maxExportSet;
	private boolean basicGoodsSet;
	private boolean conForcesSet;
	private boolean nuclearForcesSet;
	private boolean importsSet;

	public Nation() {
		availableExports = new ArrayList<Commodity>();
		requiredImports = new ArrayList<Commodity>();
		team = new ArrayList<Player>();
		gnp = BigDecimal.ZERO;
		conventionalForces = BigDecimal.ZERO;
		nuclearForces = BigDecimal.ZERO;
		maxExportSet = false;
		basicGoodsSet = false;
		conForcesSet = false;
		nuclearForcesSet = false;
		importsSet = false;
	}
	
	/**
	 * 
	 * Static method generates new nation from passed in nation
	 * @param n 
	 * @return
	 */
	
	public static Nation copyNation(final Nation n){
		Nation newCopy = new Nation();
		newCopy.setName(n.getName());
		newCopy.setCountryCode(n.getCountryCode());
		newCopy.setGNP(new String(n.getGnpString()));
		newCopy.setConventionalForces(new BigDecimal(n.conventionalForces.toPlainString()));
		newCopy.setNuclearForces(new BigDecimal(n.nuclearForces.toPlainString()));
		newCopy.setMaxExportSet(n.getMaxExportSet());
		newCopy.setbasicGoodsSet(n.getBasicGoodsSet());
		newCopy.setConForcesSet(n.getConventionalForcesSet());
		newCopy.setNuclearForcesSet(n.getNuclearForcesSet());
		newCopy.setImportsSet(n.getImportsSet());
		for(Commodity c:n.getAvailableExports()){
			newCopy.getAvailableExports().add(c);	
		}
		for(Commodity c:n.getRequiredImports()){
			newCopy.getRequiredImports().add(c);
		}
		for(Player p:n.getTeam()){
			newCopy.getTeam().add(p);
		}		
		return newCopy;
	}

	@Override
	public String toString() {
		return name;
	}

	public void setGNP(String g) {
		gnp = new BigDecimal(g);
	}
	
	private void setConventionalForces(BigDecimal b){
		conventionalForces = b;
	}
	
	private void setNuclearForces(BigDecimal b){
		nuclearForces = b;
	}
	
	public void setName(String n) {
		name = n;
	}
	
	public void setCountryCode(String c){
		countryCode = c;
	}

	public void addAvailableExport(Commodity c) {
		availableExports.add(c);
	}

	public void addRequiredImport(Commodity c) {
		requiredImports.add(c);
	}

	public void addPlayer(Player p) {
		team.add(p);
	}

	public void removePlayer(Player p) {
		team.remove(p);
	}

	public int getTeamSize() {
		return team.size();
	}

	public boolean inUse() {
		return team.size() > 0;
	}

	public BigDecimal getGnp() {
		return gnp;
	}

	public String getGnpString() {
		return gnp.toString();
	}

	public String getName() {
		return name;
	}
	
	public String getCountryCode(){
		return countryCode;
	}
	
	public BigDecimal getConventionalForcesAllocation(){
		return conventionalForces;
	}
	
	public BigDecimal getNuclearForcesAllocation(){
		return nuclearForces;
	}

	public ArrayList<Player> getTeam() {
		return team;
	}

	public ArrayList<Commodity> getRequiredImports() {
		return requiredImports;
	}

	public ArrayList<Commodity> getAvailableExports() {
		return availableExports;
	}
	
	public boolean getMaxExportSet(){
		return maxExportSet;
	}
	
	private void setMaxExportSet(boolean t){
		maxExportSet = t;
	}
	
	public BigDecimal getMaxExports(){
		
		if(gnp.compareTo(new BigDecimal("5000")) < 0)
			return gnp.multiply(new BigDecimal(".05"));
		else if(gnp.compareTo(new BigDecimal("12000")) < 0)
			return gnp.multiply(new BigDecimal(".10"));
		else if(gnp.compareTo(new BigDecimal("50000")) < 0)
			return gnp.multiply(new BigDecimal(".15"));
		else if(gnp.compareTo(new BigDecimal("50000")) >= 0)
			return gnp.multiply(new BigDecimal(".20"));
		else
			return null;
		
	}
	
	public boolean setMaxExports(BigDecimal b){
		if(b.compareTo(getMaxExports())==0)
			maxExportSet = true;
		return maxExportSet;
	}
	
	public boolean getBasicGoodsSet(){
		return basicGoodsSet;
	}
	
	private void setbasicGoodsSet(boolean t){
		basicGoodsSet = t;
	}
	
	public BigDecimal getBasicGoodsAllocation(){
		if(gnp.compareTo(new BigDecimal("5000")) < 0)
			return gnp.multiply(new BigDecimal(".60"));
		else if(gnp.compareTo(new BigDecimal("12000")) < 0)
			return gnp.multiply(new BigDecimal(".50"));
		else if(gnp.compareTo(new BigDecimal("25000")) < 0)
			return gnp.multiply(new BigDecimal(".35"));
		else if(gnp.compareTo(new BigDecimal("50000")) < 0)
			return gnp.multiply(new BigDecimal(".25"));
		else if(gnp.compareTo(new BigDecimal("50000")) >= 0)
			return gnp.multiply(new BigDecimal(".15"));
		else
			return null;
	}
	
	public boolean setBasicGoodsAllocation(BigDecimal b){
		if(b.compareTo(getBasicGoodsAllocation())==0)
			basicGoodsSet = true;
		return basicGoodsSet;
	}

	public boolean getConventionalForcesSet(){
		return conForcesSet;
	}
	
	private void setConForcesSet(boolean t){
		conForcesSet = t;
	}
	
	public boolean setConventionalForcesAllocation(BigDecimal b){
		Nation prev = Main.currGame.getRounds().get(Main.currGame.currentRound()-1).getNation(countryCode);
		if(prev!=null && b.compareTo(prev.getConventionalForcesAllocation())>=0 && b.compareTo(BigDecimal.valueOf(100))>=0){
			conventionalForces = b;
			setConForcesSet(true);
		}
		return conForcesSet;
	}
	
	private void setNuclearForcesSet(boolean t){
		nuclearForcesSet = t;
	}
	
	public boolean getNuclearForcesSet(){
		return nuclearForcesSet;
	}
	
	public boolean setNuclearForcesAllocation(BigDecimal b){
		Nation prev = Main.currGame.getRounds().get(Main.currGame.currentRound()-1).getNation(countryCode);
		if(prev!=null && b.compareTo(prev.getNuclearForcesAllocation())>=0 && gnp.compareTo(BigDecimal.valueOf(25000))>=0 && b.compareTo(BigDecimal.valueOf(500)) >=0){
			nuclearForces = b;
			setNuclearForcesSet(true);
		}
		return nuclearForcesSet;
	}
	
	private void setImportsSet(boolean t){
		importsSet = t;
	}
	
	public boolean getImportsSet(){
		return importsSet;
	}
	
	public boolean setImportsAllocation(BigDecimal b){		
		if(b!=null && b.compareTo(getImportsAllocation())==0)
			importsSet = true;
		return importsSet;
	}
	
	public BigDecimal getImportsAllocation(){		
		if(gnp.compareTo(new BigDecimal("5000")) < 0)
			return gnp.multiply(BigDecimal.valueOf(.20));	
		else if(gnp.compareTo(new BigDecimal("12000")) < 0)
			return gnp.multiply(BigDecimal.valueOf(.20));					
		else if(gnp.compareTo(new BigDecimal("25000")) < 0)
			return gnp.multiply(BigDecimal.valueOf(.15));				
		else if(gnp.compareTo(new BigDecimal("50000")) < 0)
			return gnp.multiply(BigDecimal.valueOf(.10));					
		else if(gnp.compareTo(new BigDecimal("50000")) >= 0)
			return gnp.multiply(BigDecimal.valueOf(.05));
		return null;
	}	
}
