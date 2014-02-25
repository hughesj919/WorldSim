package application;

enum playerType {
	chiefOfState, budgetOffice
};

public class Player {
	private int period;
	private String name;
	private Nation nation;
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

	public void setNation(Nation n) {
		nation = n;
	}

	public void setType(playerType p) {
		type = p;
	}

}
