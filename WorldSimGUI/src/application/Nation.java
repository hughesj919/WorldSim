package application;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

public class Nation implements Serializable {

	private static final long serialVersionUID = 569741334000068828L;
	private String countryCode;
	private BigDecimal gnp;
	private BigDecimal conventionalForces;
	private BigDecimal nuclearForces;
	private BigDecimal researchAndDevelopment;
	private BigDecimal contingencyFund;
	private BigDecimal capitalGoods;
	private BigDecimal rdAppreciationRate;
	private String name;
	private String password;
	private ArrayList<Commodity> availableExports;
	private ArrayList<Commodity> requiredImports;
	private ArrayList<Player> team;
	private ArrayList<Relationship> currentRelationships;
	private ArrayList<InternationalOrganization> currentOrgs;
	private boolean maxExportSet;
	private boolean basicGoodsSet;
	private boolean conForcesSet;
	private boolean nuclearForcesSet;
	private boolean importsSet;
	private boolean rdSet;
	private boolean contingencySet;
	private boolean capitalGoodsSet;
	

	public Nation() {
		availableExports = new ArrayList<Commodity>();
		requiredImports = new ArrayList<Commodity>();
		team = new ArrayList<Player>();
		currentRelationships = new ArrayList<Relationship>();
		currentOrgs = new ArrayList<InternationalOrganization>();
		gnp = BigDecimal.ZERO;
		conventionalForces = BigDecimal.ZERO;
		nuclearForces = BigDecimal.ZERO;
		researchAndDevelopment = BigDecimal.ZERO;
		contingencyFund = BigDecimal.ZERO;
		capitalGoods = BigDecimal.ZERO;
		rdAppreciationRate = BigDecimal.ZERO;
		maxExportSet = false;
		basicGoodsSet = false;
		conForcesSet = false;
		nuclearForcesSet = false;
		importsSet = false;
		rdSet = false;
		contingencySet = false;
		capitalGoodsSet = false;
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
		newCopy.setResearchAndDevelopment(new BigDecimal(n.researchAndDevelopment.toPlainString()));
		newCopy.setContingencyFund(new BigDecimal(n.contingencyFund.toPlainString()));
		newCopy.setCapitalGoods(new BigDecimal(n.capitalGoods.toPlainString()));
		
		newCopy.setMaxExportSet(n.getMaxExportSet());
		newCopy.setbasicGoodsSet(n.getBasicGoodsSet());
		newCopy.setConForcesSet(n.getConventionalForcesSet());
		newCopy.setNuclearForcesSet(n.getNuclearForcesSet());
		newCopy.setImportsSet(n.getImportsSet());
		newCopy.setRDSet(n.getRDSet());
		newCopy.setContingencySet(n.getContingencySet());
		newCopy.setCapitalGoodsSet(n.getCapitalGoodsSet());		

		for(Commodity c:n.getAvailableExports()){
			newCopy.getAvailableExports().add(c);	
		}
		for(Commodity c:n.getRequiredImports()){
			newCopy.getRequiredImports().add(c);
		}
		for(Player p:n.getTeam()){
			newCopy.getTeam().add(p);
		}		
		for(Relationship r:n.getRelationships()){
			newCopy.getRelationships().add(r);
		}
		for(InternationalOrganization o:n.getOrganizations()){
			newCopy.getOrganizations().add(o);
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
	
	private void setResearchAndDevelopment(BigDecimal b){
		researchAndDevelopment = b;
	}
	
	private void setContingencyFund(BigDecimal b){
		contingencyFund = b;
	}
	
	private void setCapitalGoods(BigDecimal b){
		capitalGoods = b;
	}
	
	public void setName(String n) {
		name = n;
	}
	
	public void setPassword(String n) {
		password = n;
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
	
	public String getPassword() {
		return password;
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
		if(b.compareTo(getBasicGoodsAllocation())==0 && checkLastAllocation(b))
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
		
		Nation prev = null;
		if(Main.currGame.currentRound()>0)
			prev = Main.currGame.getRounds().get(Main.currGame.currentRound()-1).getNation(countryCode);
		if(((prev!=null && b.compareTo(prev.getConventionalForcesAllocation())>=0)||prev==null) && b.compareTo(BigDecimal.valueOf(100))>=0 && b.add(getBudgetCommitments()).compareTo(getGnp())<=0 && checkLastAllocation(b)){
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
	
	private Nation getPreviousNation(){
		Nation prev = null;
		if(Main.currGame.currentRound()>0)
			prev = Main.currGame.getRounds().get(Main.currGame.currentRound()-1).getNation(countryCode);
		return prev;
	}
	
	public boolean setNuclearForcesAllocation(BigDecimal b){
		Nation prev = getPreviousNation();
		if(((prev!=null && b.compareTo(prev.getNuclearForcesAllocation())>=0)||prev==null) && ((gnp.compareTo(BigDecimal.valueOf(25000))>=0 && b.compareTo(BigDecimal.valueOf(500)) >=0) || (gnp.compareTo(BigDecimal.valueOf(25000))<0 && b.compareTo(BigDecimal.ZERO)==0)) && b.add(getBudgetCommitments()).compareTo(getGnp())<=0 && checkLastAllocation(b)){
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
		if(b!=null && b.compareTo(getImportsAllocation())==0 && checkLastAllocation(b))
			importsSet = true;
		return importsSet;
	}
	
	public BigDecimal getImportsAllocation(){		
		if(gnp.compareTo(new BigDecimal("5000")) < 0)
			return gnp.multiply(new BigDecimal(".20"));	
		else if(gnp.compareTo(new BigDecimal("12000")) < 0)
			return gnp.multiply(new BigDecimal(".20"));					
		else if(gnp.compareTo(new BigDecimal("25000")) < 0)
			return gnp.multiply(new BigDecimal(".15"));				
		else if(gnp.compareTo(new BigDecimal("50000")) < 0)
			return gnp.multiply(new BigDecimal(".10"));					
		else if(gnp.compareTo(new BigDecimal("50000")) >= 0)
			return gnp.multiply(new BigDecimal(".05"));
		return null;
	}	
	
	private void setRDSet(boolean t){
		rdSet = t;
	}
	
	public boolean getRDSet(){
		return rdSet;
	}
	
	public BigDecimal getRDAllocation(){
		return researchAndDevelopment;
	}
	
	public boolean setRDAllocation(BigDecimal b){
		Nation prev = getPreviousNation();
		if(((prev!=null && b!=null && b.compareTo(prev.getRDAllocation())>=0)||prev==null) && (b.compareTo(BigDecimal.valueOf(3000)) >=0 || b.compareTo(BigDecimal.ZERO) == 0) && b.add(getBudgetCommitments()).compareTo(getGnp())<=0 && checkLastAllocation(b)){
			researchAndDevelopment = b;			
			if(prev==null || (prev.getRDAllocation().compareTo(BigDecimal.ZERO)==0 || prev.getRDAllocation().toBigInteger().mod(new BigInteger("3000")).compareTo(b.toBigInteger().mod(new BigInteger("3000")))!=0)){
				Random generator = new Random();
				Integer rand = generator.nextInt(5);
				if(rand ==0)
					this.rdAppreciationRate = BigDecimal.ZERO;	
				else if(rand ==1)
					this.rdAppreciationRate = new BigDecimal(".10");	
				else if(rand ==2)
					this.rdAppreciationRate = new BigDecimal(".20");	
				else if(rand ==3)
					this.rdAppreciationRate = new BigDecimal(".30");	
				else if(rand ==4)
					this.rdAppreciationRate = new BigDecimal(".40");	
				else if(rand ==5)
					this.rdAppreciationRate = new BigDecimal(".50");					
			}			
			setRDSet(true);
		}
		return rdSet;
	}
	
	private void setContingencySet(boolean t){
		contingencySet = t;
	}
	
	public boolean getContingencySet(){
		return contingencySet;
	}
	
	public BigDecimal getContingencyAllocation(){
		return contingencyFund;
	}
	
	public boolean setContingencyAllocation(BigDecimal b){
		if(b!=null && b.compareTo(getGnp().multiply(BigDecimal.valueOf(.10)))<=0 && b.add(getBudgetCommitments()).compareTo(getGnp())<=0 && checkLastAllocation(b)){
			contingencyFund = b;
			setContingencySet(true);
		}
		return contingencySet;
	}
	
	private void setCapitalGoodsSet(boolean t){
		capitalGoodsSet = true;
	}
	
	public boolean getCapitalGoodsSet(){
		return capitalGoodsSet;
	}
	
	public BigDecimal getCapitalGoodsAllocation(){
		return capitalGoods;
	}
	
	public boolean setCapitalGoodsAllocation(BigDecimal b){
		if(b!=null && b.add(getBudgetCommitments()).compareTo(getGnp())<=0 && checkLastAllocation(b)){
			capitalGoods = b;
			setCapitalGoodsSet(true);
		}
		return capitalGoodsSet;
	}
	
	private BigDecimal getBudgetCommitments(){
		BigDecimal preAllocated = BigDecimal.ZERO;
		Nation prev = this.getPreviousNation();

		preAllocated = preAllocated.add(getBasicGoodsAllocation());
		preAllocated = preAllocated.add(getImportsAllocation());
		if(this.conForcesSet)
			preAllocated = preAllocated.add(this.conventionalForces);
		else if(prev!=null)
			preAllocated = preAllocated.add(prev.getConventionalForcesAllocation());
		if(this.nuclearForcesSet)
			preAllocated = preAllocated.add(this.nuclearForces);
		else if(prev!=null)
			preAllocated = preAllocated.add(prev.getNuclearForcesAllocation());
		
		if(this.rdSet)
			preAllocated = preAllocated.add(this.researchAndDevelopment);
		else if(prev!=null)
			preAllocated = preAllocated.add(prev.getRDAllocation());
		
		if(this.contingencySet)
			preAllocated = preAllocated.add(this.contingencyFund);
			
		if(this.capitalGoodsSet)
			preAllocated = preAllocated.add(this.capitalGoods);
		
		return preAllocated;		
	}
	
	public BigDecimal getBasicGoodsDepreciation(){
		BigDecimal basicGoods = this.getBasicGoodsAllocation();
		if(gnp.compareTo(new BigDecimal("5000")) < 0)
			return basicGoods.multiply(new BigDecimal(".70"));	
		else if(gnp.compareTo(new BigDecimal("12000")) < 0)
			return basicGoods.multiply(new BigDecimal(".65"));					
		else if(gnp.compareTo(new BigDecimal("25000")) < 0)
			return basicGoods.multiply(new BigDecimal(".60"));				
		else if(gnp.compareTo(new BigDecimal("50000")) < 0)
			return basicGoods.multiply(new BigDecimal(".50"));					
		else if(gnp.compareTo(new BigDecimal("50000")) >= 0)
			return basicGoods.multiply(new BigDecimal(".40"));
		return null;
	}
	
	public boolean fullyAllocated(){		
		return basicGoodsSet && conForcesSet && nuclearForcesSet && importsSet && rdSet && contingencySet && capitalGoodsSet && maxExportSet;
	}
	
	public BigDecimal getConventionalForcesDepreciation(){
		BigDecimal conForces = this.getConventionalForcesAllocation();
		if(gnp.compareTo(new BigDecimal("5000")) < 0)
			return conForces.multiply(new BigDecimal(".10"));	
		else if(gnp.compareTo(new BigDecimal("12000")) < 0)
			return conForces.multiply(new BigDecimal(".12"));					
		else if(gnp.compareTo(new BigDecimal("25000")) < 0)
			return conForces.multiply(new BigDecimal(".15"));				
		else if(gnp.compareTo(new BigDecimal("50000")) < 0)
			return conForces.multiply(new BigDecimal(".20"));					
		else if(gnp.compareTo(new BigDecimal("50000")) >= 0)
			return conForces.multiply(new BigDecimal(".30"));
		return null;
	}
	
	public BigDecimal getNuclearForcesDepreciation(){
		BigDecimal nucForces = this.getNuclearForcesAllocation();
		if(gnp.compareTo(new BigDecimal("5000")) < 0)
			return BigDecimal.ZERO;
		else if(gnp.compareTo(new BigDecimal("12000")) < 0)
			return nucForces.multiply(new BigDecimal(".30"));					
		else if(gnp.compareTo(new BigDecimal("25000")) < 0)
			return nucForces.multiply(new BigDecimal(".40"));				
		else if(gnp.compareTo(new BigDecimal("50000")) < 0)
			return nucForces.multiply(new BigDecimal(".50"));					
		else if(gnp.compareTo(new BigDecimal("50000")) >= 0)
			return nucForces.multiply(new BigDecimal(".60"));
		return null;
	}
	
	public BigDecimal getCapitalGoodsAppreciation(){
		BigDecimal capGoods = this.getCapitalGoodsAllocation();
		capGoods = capGoods.add(this.getContingencyTotal());
		if(gnp.compareTo(new BigDecimal("5000")) < 0)
			return capGoods.multiply(new BigDecimal(".25"));
		else if(gnp.compareTo(new BigDecimal("12000")) < 0)
			return capGoods.multiply(new BigDecimal(".20"));					
		else if(gnp.compareTo(new BigDecimal("25000")) < 0)
			return capGoods.multiply(new BigDecimal(".15"));				
		else if(gnp.compareTo(new BigDecimal("50000")) < 0)
			return capGoods.multiply(new BigDecimal(".10"));					
		else if(gnp.compareTo(new BigDecimal("50000")) >= 0)
			return capGoods.multiply(new BigDecimal(".05"));
		return null;
	}
	
	public BigDecimal getTotalCurrentImports(){
		BigDecimal totalImports = BigDecimal.ZERO;
		for(TradeData t:Main.currGame.getTrades()){
			if(t.getImporter() == this){
				totalImports = totalImports.add(t.getAmount());
			}
		}
		return totalImports;
	}
	
	public BigDecimal getTotalCurrentExports(){
		BigDecimal totalExports = BigDecimal.ZERO;
		for(TradeData t:Main.currGame.getTrades()){
			if(t.getExporter() == this){
				totalExports = totalExports.add(t.getAmount());
			}
		}
		return totalExports;
	}
	
	/*private void setAidReceivedSet(boolean t){
		this.aidReceivedSet = t;
	}
	
	private void setAidGivenSet(boolean t){
		this.aidGivenSet = t;
	}
	
	private void setLoanReceivedSet(boolean t){
		this.loanReceivedSet = t;
	}
	
	private void setLoanGivenSet(boolean t){
		this.loanGivenSet = t;
	}
	
	private void setIMFReceivedSet(boolean t){
		this.imfReceivedSet = t;
	}
	
	private void setIMFGivenSet(boolean t){
		this.imfGivenSet = t;
	}
	
	public boolean getAidReceivedSet(){
		return this.aidReceivedSet;
	}
	
	public boolean getAidGivenSet(){
		return this.aidGivenSet;
	}
	
	public boolean getLoanReceivedSet(){
		return this.loanReceivedSet;
	}
	
	public boolean getLoanGivenSet(){
		return this.loanGivenSet;
	}
	
	public boolean getIMFReceivedSet(){
		return this.imfReceivedSet;
	}
	
	public boolean getIMFGivenSet(){
		return this.imfGivenSet;
	}
	
	public BigDecimal getAidReceived(){
		return this.aidReceived;
	}
	
	public BigDecimal getAidGiven(){
		return this.aidGiven;
	}
	
	public BigDecimal getLoanReceived(){
		return this.loanReceived;
	}
	
	public BigDecimal getLoanGiven(){
		return this.loanGiven;
	}
	
	public BigDecimal getIMFReceived(){
		return this.imfReceived;
	}
	
	public BigDecimal getIMFGiven(){
		return this.imfGiven;
	}
	
	private BigDecimal getContingencyCommitment(){
		BigDecimal totalCommitted = BigDecimal.ZERO;
		if(this.aidGivenSet)
			totalCommitted.add(this.aidGiven);
		if(this.loanGivenSet)
			totalCommitted.add(this.loanGiven);
		if(this.imfGivenSet)
			totalCommitted.add(this.imfGiven);
		return totalCommitted;
	}
	
	public boolean setAidReceived(BigDecimal b){
		this.aidReceived = b;
		this.setAidReceivedSet(true);
		return this.aidReceivedSet;
	}
	
	public boolean setAidGiven(BigDecimal b){
		if(b.add(this.getContingencyCommitment()).compareTo(this.getContingencyAllocation())<=0){
			this.aidGiven = b;
			this.setAidGivenSet(true);
		}
		return this.getAidGivenSet();
	}
	
	public boolean setLoanReceived(BigDecimal b){
		this.loanReceived = b;
		this.setLoanReceivedSet(true);
		return this.loanReceivedSet;
	}
	
	public boolean setLoanGiven(BigDecimal b){
		if(b.add(this.getContingencyCommitment()).compareTo(this.getContingencyAllocation())<=0){
			this.loanGiven = b;
			this.setLoanGivenSet(true);
		}
		return this.getLoanGivenSet();		
	}
	
	public boolean setIMFReceived(BigDecimal b){
		this.imfReceived = b;
		this.setIMFReceivedSet(true);
		return this.imfReceivedSet;
	}
	
	public boolean setIMFGiven(BigDecimal b){
		if(b.add(this.getContingencyCommitment()).compareTo(this.getContingencyAllocation())<=0){
			this.imfGiven = b;
			this.setIMFGivenSet(true);
		}
		return this.getIMFGivenSet();		
	}
	
	public boolean contingencyAllocated(){
		return this.getAidGivenSet() && this.getAidReceivedSet() && this.getLoanGivenSet() && this.getLoanReceivedSet() && this.getIMFGivenSet() && this.getIMFReceivedSet();
	}*/
	
	public BigDecimal getAidReceived(){
		BigDecimal aidReceived = BigDecimal.ZERO;
		for(ContingencyTransaction t:Main.currGame.getContingencyTransactions()){
			if(t.getReceiver() == this && t.getType() == ContingencyType.Aid){
				aidReceived = aidReceived.add(t.getAmount());
			}
		}
		return aidReceived;		
	}
	
	public BigDecimal getAidGiven(){
		BigDecimal aidGiven = BigDecimal.ZERO;
		for(ContingencyTransaction t:Main.currGame.getContingencyTransactions()){
			if(t.getGiver() == this && t.getType() == ContingencyType.Aid){
				aidGiven = aidGiven.add(t.getAmount());
			}
		}
		return aidGiven;		
	}
	
	public BigDecimal getLoanReceived(){
		BigDecimal loanReceived = BigDecimal.ZERO;
		for(ContingencyTransaction t:Main.currGame.getContingencyTransactions()){
			if(t.getReceiver() == this && t.getType() == ContingencyType.Loan){
				loanReceived = loanReceived.add(t.getAmount());
			}
		}
		return loanReceived;		
	}
	
	public BigDecimal getLoanGiven(){
		BigDecimal loanGiven = BigDecimal.ZERO;
		for(ContingencyTransaction t:Main.currGame.getContingencyTransactions()){
			if(t.getGiver() == this && t.getType() == ContingencyType.Loan){
				loanGiven = loanGiven.add(t.getAmount());
			}
		}
		return loanGiven;		
	}
	
	public BigDecimal getIMFReceived(){
		BigDecimal imfReceived = BigDecimal.ZERO;
		for(ContingencyTransaction t:Main.currGame.getContingencyTransactions()){
			if(t.getReceiver() == this && t.getType() == ContingencyType.IMF){
				imfReceived = imfReceived.add(t.getAmount());
			}
		}
		return imfReceived;		
	}
	
	public BigDecimal getIMFGiven(){
		BigDecimal imfGiven = BigDecimal.ZERO;
		for(ContingencyTransaction t:Main.currGame.getContingencyTransactions()){
			if(t.getGiver() == this && t.getType() == ContingencyType.IMF){
				imfGiven = imfGiven.add(t.getAmount());
			}
		}
		return imfGiven;		
	}
	
	public BigDecimal getContingencyTotal(){
		BigDecimal totalContGiven = BigDecimal.ZERO;
		BigDecimal totalContReceived = BigDecimal.ZERO;
		
		totalContReceived = totalContReceived.add(this.getAidReceived());
		totalContReceived = totalContReceived.add(this.getLoanReceived());
		totalContReceived = totalContReceived.add(this.getIMFReceived());		
		totalContReceived = totalContReceived.add(this.getContingencyAllocation());
		
		totalContGiven = totalContGiven.add(this.getAidGiven());
		totalContGiven = totalContGiven.add(this.getLoanGiven());
		totalContGiven = totalContGiven.add(this.getIMFGiven());
		return totalContReceived.subtract(totalContGiven);
	}
	
	public ArrayList<Relationship> getRelationships(){
		return currentRelationships;
	}
	
	public ArrayList<InternationalOrganization> getOrganizations(){
		return currentOrgs;
	}
	
	public BigDecimal getNewGNPSubTotal(){
		BigDecimal total = BigDecimal.ZERO;
		total = total.add(getBasicGoodsSubTotal());
		total = total.add(getConventionalForcesSubTotal());
		total = total.add(getNuclearForcesSubTotal());
		total = total.add(getImportsSubTotal());
		total = total.add(getTotalCurrentExports());
		total = total.add(getRDSubTotal());
		total = total.add(getContingencyTotal());
		total = total.add(getCapitalGoodsSubTotal());
		
		return total;
	}
	
	public BigDecimal getBasicGoodsSubTotal(){
		return getBasicGoodsAllocation().subtract(getBasicGoodsDepreciation());
	}
	
	public BigDecimal getConventionalForcesSubTotal(){
		return getConventionalForcesAllocation().subtract(getConventionalForcesDepreciation());
	}
	
	public BigDecimal getNuclearForcesSubTotal(){
		return getNuclearForcesAllocation().subtract(getNuclearForcesDepreciation());
	}
	
	public BigDecimal getImportsSubTotal(){
		return getImportsAllocation().subtract(getTotalCurrentImports());
	}
	
	public BigDecimal getExportsSubTotal(){
		return getTotalCurrentExports();
	}
	
	public BigDecimal getRDAppreciation(){
		return rdAppreciationRate.multiply(getRDAllocation());
	}
	
	public BigDecimal getRDSubTotal(){
		return getRDAppreciation().add(getRDAllocation());
	}
	
	public BigDecimal getCapitalGoodsSubTotal(){
		return getCapitalGoodsAllocation().add(getCapitalGoodsAppreciation());
	}	
	
	public BigDecimal getIncomeTaxPercent(BigDecimal subTotal){
		if(subTotal.compareTo(new BigDecimal("5000")) < 0)
			return new BigDecimal(".10");	
		else if(subTotal.compareTo(new BigDecimal("12000")) < 0)
			return new BigDecimal(".12");					
		else if(subTotal.compareTo(new BigDecimal("25000")) < 0)
			return new BigDecimal(".15");				
		else if(subTotal.compareTo(new BigDecimal("50000")) < 0)
			return new BigDecimal(".20");					
		else if(subTotal.compareTo(new BigDecimal("50000")) >= 0)
			return new BigDecimal(".30");
		return null;
	}
	
	public Integer getPoliticalPoints(){
		Integer points = 0;
		for(TradeData t:Main.currGame.getTrades()){
			if(t.getImporter() == this || t.getExporter() == this){
				if(gnp.compareTo(new BigDecimal("50000"))>=0)
					points = points+4;
				else
					points = points+3;
			}
		}
		
		for(Relationship r:this.getRelationships()){
			if(r instanceof Alliance){
				if(gnp.compareTo(new BigDecimal("5000")) < 0)
					points = points+2;
				else if(gnp.compareTo(new BigDecimal("12000")) < 0)
					points = points+4;				
				else if(gnp.compareTo(new BigDecimal("25000")) < 0)
					points = points+4;			
				else if(gnp.compareTo(new BigDecimal("50000")) < 0)
					points = points+5;					
				else if(gnp.compareTo(new BigDecimal("50000")) >= 0)
					points = points+4;				
			}else if(r instanceof Neutrality){
				if(gnp.compareTo(new BigDecimal("5000")) < 0)
					points = points+5;
				else if(gnp.compareTo(new BigDecimal("12000")) < 0)
					points = points+4;				
				else if(gnp.compareTo(new BigDecimal("25000")) < 0)
					points = points+2;		
			}else if(r instanceof NonAggressionPact){
				points = points+1;
			}
		}
		
		if(gnp.compareTo(new BigDecimal("5000")) < 0)
			points = points + (currentOrgs.size()*5);
		else if(gnp.compareTo(new BigDecimal("12000")) < 0)
			points = points + (currentOrgs.size()*4);				
		else if(gnp.compareTo(new BigDecimal("25000")) < 0)
			points = points + (currentOrgs.size()*3);			
		else if(gnp.compareTo(new BigDecimal("50000")) < 0)
			points = points + (currentOrgs.size()*2);					
		else if(gnp.compareTo(new BigDecimal("50000")) >= 0)
			points = points + (currentOrgs.size()*4);		
		
		for(ContingencyTransaction t:Main.currGame.getContingencyTransactions()){
			if(t.getGiver() == this && t.getType()!=ContingencyType.IMF){
				if(gnp.compareTo(new BigDecimal("5000")) < 0)
					points = points+1;
				else if(gnp.compareTo(new BigDecimal("12000")) < 0)
					points = points+2;				
				else if(gnp.compareTo(new BigDecimal("25000")) < 0)
					points = points+3;			
				else if(gnp.compareTo(new BigDecimal("50000")) < 0)
					points = points+4;					
				else if(gnp.compareTo(new BigDecimal("50000")) >= 0)
					points = points+5;	
			}else if(t.getReceiver() == this && t.getType()!=ContingencyType.IMF){
				if(gnp.compareTo(new BigDecimal("5000")) < 0)
					points = points+4;
				else if(gnp.compareTo(new BigDecimal("12000")) < 0)
					points = points+3;				
				else if(gnp.compareTo(new BigDecimal("25000")) < 0)
					points = points+2;			
				else if(gnp.compareTo(new BigDecimal("50000")) < 0)
					points = points+1;					
				else if(gnp.compareTo(new BigDecimal("50000")) >= 0)
					points = points+1;	
			}
		}
		
		return points;		
	}
	
	public BigDecimal getPoliticalTaxPercent(){
		Integer points = this.getPoliticalPoints();
		if(points >=0 && points <=15)
			return BigDecimal.ZERO;
		else if(points >=16 && points <=30)
			return new BigDecimal(".05");
		else if(points >=31 && points <=45)
			return new BigDecimal(".10");
		else if(points >=46 && points <=60)
			return new BigDecimal(".20");
		else if(points >=61 && points <=75)
			return new BigDecimal(".25");
		else if(points >=76)
			return new BigDecimal(".30");
		return BigDecimal.ZERO;		
	}
	
	public boolean getOilRequired(){
		boolean oilRequired = false;
		for(Commodity c:this.requiredImports){
			if(c.getType() == CommodityType.Oil)
				oilRequired = true;
		}
		return oilRequired;
	}
	
	public boolean getFoodRequired(){
		boolean foodRequired = false;
		for(Commodity c:this.requiredImports){
			if(c.getType() == CommodityType.Food)
				foodRequired = true;
		}
		return foodRequired;
	}
	
	public boolean getOtherRequired(){
		boolean otherRequired = false;
		for(Commodity c:this.requiredImports){
			if(c.getType() == CommodityType.Other)
				otherRequired = true;
		}
		return otherRequired;
	}
	
	public boolean getOilImported(){
		boolean oilImported = false;
		for(TradeData t:Main.currGame.getTrades()){
			if(t.getImporter() == this && t.getCommodity().getType() == CommodityType.Oil){
				oilImported = true;
			}
		}
		return oilImported;
	}
	
	public boolean getFoodImported(){
		boolean foodImported = false;
		for(TradeData t:Main.currGame.getTrades()){
			if(t.getImporter() == this && t.getCommodity().getType() == CommodityType.Food){
				foodImported = true;
			}
		}
		return foodImported;		
	}
	
	public boolean getOtherImported(){
		boolean otherImported = false;
		for(TradeData t:Main.currGame.getTrades()){
			if(t.getImporter() == this && t.getCommodity().getType() == CommodityType.Other){
				otherImported = true;
			}
		}
		return otherImported;		
	}

	
	public BigDecimal getTradeTaxPercent(){
		BigDecimal tradeTaxPercent = BigDecimal.ZERO;
		if(getOilRequired()){
			if(getOilImported()){
				tradeTaxPercent = tradeTaxPercent.add(new BigDecimal(".05"));
			}
			else{
				tradeTaxPercent = tradeTaxPercent.subtract(new BigDecimal(".05"));
			}
		}
		if(getFoodRequired()){
			if(getFoodImported()){
				tradeTaxPercent = tradeTaxPercent.add(new BigDecimal(".05"));
			}
			else{
				tradeTaxPercent = tradeTaxPercent.subtract(new BigDecimal(".05"));
			}
		}
		if(getOtherRequired()){
			if(getOtherImported()){
				tradeTaxPercent = tradeTaxPercent.add(new BigDecimal(".05"));
			}
			else{
				tradeTaxPercent = tradeTaxPercent.subtract(new BigDecimal(".05"));
			}
		}
		
		if(getTotalCurrentImports().compareTo(this.getImportsAllocation()) !=0){
			tradeTaxPercent = tradeTaxPercent.subtract(new BigDecimal(".10"));
		}		
		
		return tradeTaxPercent;		
	}	
	
	public BigDecimal getTotalIMFContributions(){
		BigDecimal total = BigDecimal.ZERO;
		for(ContingencyTransaction t:Main.currGame.getContingencyTransactions()){
			if(t.getGiver() == this && t.getType() == ContingencyType.IMF)
				total = total.add(t.getAmount());
		}
		return total;
	}
	
	public BigDecimal getIMFTaxPercent(){
		BigDecimal total = getTotalIMFContributions();
		if(total.compareTo(new BigDecimal("500")) <= 0)
			return new BigDecimal(".03");
		else if(total.compareTo(new BigDecimal("1000")) <= 0)
			return new BigDecimal(".05");				
		else if(total.compareTo(new BigDecimal("5000")) <= 0)
			return new BigDecimal(".08");			
		else if(total.compareTo(new BigDecimal("10000")) <= 0)
			return new BigDecimal(".12");					
		else if(total.compareTo(new BigDecimal("10000")) > 0)
			return new BigDecimal(".15");	
		return null;
	}
	
	public BigDecimal getCurrentAllocations(){
		
		BigDecimal total = BigDecimal.ZERO;
		if(basicGoodsSet)
			total = total.add(this.getBasicGoodsAllocation());
		if(conForcesSet)
			total = total.add(this.getConventionalForcesAllocation());
		if(nuclearForcesSet)
			total = total.add(this.getNuclearForcesAllocation());
		if(importsSet)
			total = total.add(this.getImportsAllocation());
		if(rdSet)
			total = total.add(this.getRDAllocation());
		if(contingencySet)
			total = total.add(this.getContingencyAllocation());
		if(capitalGoodsSet)
			total = total.add(this.getCapitalGoodsAllocation());
		return total;
	}
	
	public boolean checkLastAllocation(BigDecimal b){
		Integer numSet = 0;
		if(basicGoodsSet)
			numSet++;
		if(conForcesSet)
			numSet++;
		if(nuclearForcesSet)
			numSet++;
		if(importsSet)
			numSet++;
		if(rdSet)
			numSet++;
		if(contingencySet)
			numSet++;
		if(capitalGoodsSet)
			numSet++;
		BigDecimal total = getCurrentAllocations();
		if(numSet == 6 && gnp.compareTo(total.add(b))!=0) //last allocation
			return false;
		else
			return true;
	}
	public BigDecimal getNewGNP(){
		return getNewGNP(getNewGNPSubTotal());
	}
	
	public BigDecimal getNewGNP(BigDecimal subTotal){
		BigDecimal newGNP = subTotal.add(subTotal.multiply(getIncomeTaxPercent(subTotal)));
		newGNP = newGNP.add(subTotal.multiply(getPoliticalTaxPercent()));
		newGNP = newGNP.add(subTotal.multiply(getTradeTaxPercent()));
		newGNP = newGNP.add(subTotal.multiply(getIMFTaxPercent()));
		return newGNP;
	}
	
	public void advanceRound(){
		if(this.fullyAllocated()){
			gnp = this.getNewGNP(this.getNewGNPSubTotal());
		}		
		currentOrgs.clear();
		currentRelationships.clear();
		conventionalForces = BigDecimal.ZERO;
		nuclearForces = BigDecimal.ZERO;
		researchAndDevelopment = BigDecimal.ZERO;
		contingencyFund = BigDecimal.ZERO;
		capitalGoods = BigDecimal.ZERO;
		rdAppreciationRate = BigDecimal.ZERO;
		maxExportSet = false;
		basicGoodsSet = false;
		conForcesSet = false;
		nuclearForcesSet = false;
		importsSet = false;
		rdSet = false;
		contingencySet = false;
		capitalGoodsSet = false;		
	}
}
