package application;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;

public class UIController {

	private ObservableList<Nation> obsNations;
	private ObservableList<Player> obsPlayers;
	private ObservableList<Game> obsGames;

	@FXML
	private TitledPane gamePane;

	@FXML
	private TitledPane budgetPane;

	@FXML
	private TitledPane teamPane;

	@FXML
	private TitledPane nationPane;

	@FXML
	private TitledPane leaderboardPane;

	@FXML
	private Button gameButton;

	@FXML
	private Button advanceButton;

	@FXML
	private Button nationButton;

	@FXML
	private Button budgetButton;

	@FXML
	private Button leaderboardButton;

	@FXML
	private Button teamButton;

	/**
	 * Player Tab
	 */

	@FXML
	private Button savePlayerButton;

	@FXML
	private Button deletePlayerButton;

	@FXML
	private TextField playerPeriod;

	@FXML
	private TextField playerName;

	@FXML
	private ComboBox<String> playerPosition;

	@FXML
	private ComboBox<Nation> playerNation;

	@FXML
	private ListView<Player> playersList;
	
	
	/**
	 * Game Tab
	 */

    @FXML
    private ComboBox<Game> gamesList;
	
	

	@FXML
	private ListView<Nation> nationsList;

	@FXML
	private ListView<Nation> inUseNationsList;
	
	

	@FXML
	public void gameButtonClick(ActionEvent event) {
		gamePane.setVisible(true);
		teamPane.setVisible(false);
		budgetPane.setVisible(false);
		leaderboardPane.setVisible(false);
		nationPane.setVisible(false);

	}

	@FXML
	public void budgetButtonClick(ActionEvent event) {
		gamePane.setVisible(false);
		teamPane.setVisible(false);
		budgetPane.setVisible(true);
		leaderboardPane.setVisible(false);
		nationPane.setVisible(false);

	}

	@FXML
	public void leaderboardButtonClick(ActionEvent event) {
		gamePane.setVisible(false);
		teamPane.setVisible(false);
		budgetPane.setVisible(false);
		leaderboardPane.setVisible(true);
		nationPane.setVisible(false);

	}

	public void teamButtonClick(ActionEvent event) {
		gamePane.setVisible(false);
		teamPane.setVisible(true);
		budgetPane.setVisible(false);
		leaderboardPane.setVisible(false);
		nationPane.setVisible(false);
	}

	@FXML
	public void nationButtonClick(ActionEvent event) {
		gamePane.setVisible(false);
		teamPane.setVisible(false);
		budgetPane.setVisible(false);
		leaderboardPane.setVisible(false);
		nationPane.setVisible(true);

	}

	@FXML
	public void playerSaveButtonClick(ActionEvent event) {
		String pName = playerName.getText();
		//String pType = playerPosition.getValue();
		Integer pPeriod = Integer.getInteger(playerPeriod.getText());
		Nation pNation = playerNation.getValue();
		
		Player newPlayer = new Player(pName);
		newPlayer.setPeriod(pPeriod);
		newPlayer.setNation(pNation);
		//newPlayer.setType(pType);
		
		obsPlayers.add(newPlayer);
		Main.currGame.addPlayer(newPlayer);


	}

	@FXML
	public void playerDeleteButtonClick(ActionEvent event) {

	}

	@FXML
	public void advanceButtonClick(ActionEvent event) {

	}

	/*
	 * public ArrayList<String> nationsArray() { return nationsArrList; }
	 * 
	 * public void setNationsArray(ArrayList<String> arr) { nationsArrList =
	 * arr; }
	 */

	public void addNationToUI(Nation nat) {
		obsNations.add(nat);
	}

	public void setNations(ArrayList<Nation> nats) {
		for (Nation n : nats) {
			obsNations.add(n);
			// obsNations.
		}
	}

	public void initialize() {
		System.out.println("Initializing UI...");

		obsNations = FXCollections.observableArrayList();
		obsPlayers = FXCollections.observableArrayList();
		obsGames = FXCollections.observableArrayList();
		
		gamesList.setItems(obsGames);

		// playerNation.setItems(obsNations);

		playerPosition.setItems(FXCollections.observableArrayList(
				"Chief of State", "Budget Officer"));
		// playerPosition.setItems();

		nationsList.setItems(obsNations);
		playersList.setItems(obsPlayers);

	}

}
