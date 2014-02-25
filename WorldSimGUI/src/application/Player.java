package application;

enum playerType {
	chiefOfState, budgetOffice
};

public class Player {
	private int period;
	private String name;
	private int nationID;
	private playerType type;

	Player(String n) {
		name = n;
	}

	public void setName(String n) {
		name = n;
	}

	public void setPeriod(int p) {
		period = p;
	}

	public void setNation(int id) {
		nationID = id;
	}

	public void setType(playerType p) {
		type = p;
	}

}
