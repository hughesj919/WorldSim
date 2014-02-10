package application;
import java.util.ArrayList;

public class Nation {

	private int id;
	private float gnp;
	private String name;
	private ArrayList<Commodity> exports;
	private ArrayList<Commodity> imports;

	public Nation(int num) {
		id = num;
		exports = new ArrayList<Commodity>();
		imports = new ArrayList<Commodity>();
		gnp = 0;
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
