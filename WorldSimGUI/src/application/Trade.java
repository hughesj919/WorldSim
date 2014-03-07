package application;


public class Trade {

	private Commodity c;
	private Nation exporter;
	private Nation importer;
	double amount;
	
	public Trade(){

		amount = 0;
	}
	
	public Trade(Commodity com, Nation e, Nation i, double amt){
		c = com;
		exporter = e;
		importer = i;
		amount = amt;
	}
	
	public void setCommodity(Commodity com){
		c = com;
	}
	public void setExporter(Nation e){
		exporter = e;
	}
	public void setImporter(Nation i){
		importer = i;
	}
	public void setAmount(double amt){
		amount = amt;
	}
	public Commodity getCommodity(){
		return c;
	}
	public String getCommodityType(){
		return c.getTypeString();
	}
	public Nation getExporter(){
		return exporter;
	}
	public Nation getImporter(){
		return importer;
	}
	public String getImporterString(){
		return importer.getName();
	}
	public double getAmount(){
		return amount;
	}
}
