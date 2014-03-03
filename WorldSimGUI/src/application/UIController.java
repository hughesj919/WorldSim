package application;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;

public class UIController {

	/***********************************************
	 * Game Tab
	 **********************************************/
	private ObservableList<Game> obsGames;
	

	@FXML
	private Button gameButton;

	@FXML
	private TitledPane gamePane;

	@FXML
	private ComboBox<Game> gamesList;

	@FXML
	private Button saveGameButton;

	@FXML
	private TextField newGameText;

	@FXML
	public void gameButtonClick(ActionEvent event) {
		gamePane.setVisible(true);
		teamPane.setVisible(false);
		budgetPane.setVisible(false);
		leaderboardPane.setVisible(false);
		nationPane.setVisible(false);

	}

	@FXML
	public void saveGameButtonClick(ActionEvent event) {

		String name = newGameText.getText();
		if (name.matches("\\s*\\w+(\\w|\\s)*")) {
			Game newGame = new Game(name);
			String saveName = "Game" + name.replaceAll("\\s+", "") + ".wsm";
			if (newGame.saveGame(saveName)) {
				obsGames.add(newGame);
				Main.changeGame(newGame, this);
				System.out.println("Game Saved");

			} else {
				System.out.println("Error saving game");
			}
		} else {
			System.out.println("Letters and numbers only please!");
		}
		newGameText.setText(null);

	}

	@FXML
	public void gamesListChange(ActionEvent event) {
		Game g = gamesList.getValue();
		Main.changeGame(g, this);

	}

	public void setGame(Game g) {
		gamesList.setValue(g);
	}

	public Game findGame(String gameName) {

		for (Game g : obsGames) {
			if (g.getName().equals(gameName)) {
				return g;
			}
		}
		return null;
	}

	/***********************************************
	 * Nation Tab
	 ***********************************************/
	private ObservableList<Nation> obsInUseNations;
	private ObservableList<Nation> obsAvailableNations;

	@FXML
	private Button nationButton;

	@FXML
	private TitledPane nationPane;

	@FXML
	private ListView<Nation> nationsAvailableList;

	@FXML
	private ListView<Nation> nationsInUseList;

	@FXML
	public void nationButtonClick(ActionEvent event) {
		gamePane.setVisible(false);
		teamPane.setVisible(false);
		budgetPane.setVisible(false);
		leaderboardPane.setVisible(false);
		nationPane.setVisible(true);

	}

	public void addInUseNation(Nation nat) {
		obsInUseNations.add(nat);
	}

	public void addAvailableNation(Nation nat) {
		obsAvailableNations.add(nat);
	}

	public void setInUseNations(ArrayList<Nation> nats) {
		for (Nation n : nats) {
			obsInUseNations.add(n);
			// obsNations.
		}
	}

	/***********************************************
	 * Team/Player Tab
	 ***********************************************/
	private ObservableList<Player> obsPlayers;
	private ObservableList<Nation> obsAllNations;

	@FXML
	private Button teamButton;

	@FXML
	private TitledPane teamPane;

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

	public void teamButtonClick(ActionEvent event) {
		gamePane.setVisible(false);
		teamPane.setVisible(true);
		budgetPane.setVisible(false);
		leaderboardPane.setVisible(false);
		nationPane.setVisible(false);
	}

	@FXML
	public void playerSaveButtonClick(ActionEvent event) {
		String pName = playerName.getText();
		String pType = playerPosition.getValue();
		int pPeriod = Integer.parseInt(playerPeriod.getText());
		Nation pNation = playerNation.getValue();

		Player newPlayer = findPlayer(pName);
		
		if(newPlayer == null){
			
			newPlayer = new Player(pName);
			
			//add to nation list if newly in use
			if(!pNation.inUse()){
				obsAvailableNations.remove(pNation);
				obsInUseNations.add(pNation);
			}
			
			//add player to nation
			pNation.addPlayer(newPlayer);
			
			//add player to player list and curr game
			obsPlayers.add(newPlayer);
			Main.currGame.addPlayer(newPlayer);			
		}
		else{
			
			//player does exist, assume a nation change, so update as necessary
			Nation prevNation = newPlayer.getNation();
			
			if(prevNation !=null){
				prevNation.removePlayer(newPlayer);
				if(!prevNation.inUse()){
					obsInUseNations.remove(prevNation);
					obsAvailableNations.add(prevNation);
				}
			}
			
			if(!pNation.inUse()){
				obsAvailableNations.remove(pNation);
				obsInUseNations.add(pNation);
			}
			
			pNation.addPlayer(newPlayer);
		}
		
		//change values on the player
		newPlayer.setPeriod(pPeriod);
		newPlayer.setNation(pNation);
		if (pType.equals("Chief of State"))
			newPlayer.setType(playerType.chiefOfState);
		else if (pType.equals("Foreign Minister"))
			newPlayer.setType(playerType.foreignMinister);

		//save the state
		Main.currGame.saveGame();
		clearPlayerScreen();

		/*
		 * Rectangle rect = new Rectangle (100, 40, 100, 100);
		 * rect.setArcHeight(50); rect.setArcWidth(50);
		 * rect.setFill(Color.VIOLET);
		 * 
		 * FadeTransition ft = new FadeTransition(Duration.millis(3000), rect);
		 * ft.setFromValue(1.0); ft.setToValue(0.3); ft.setCycleCount(4);
		 * ft.setAutoReverse(true);
		 * 
		 * ft.play();
		 */

	}
	
	@FXML
	public void playerDeleteButtonClick(ActionEvent event) {
		String pName = playerName.getText();
		Player newPlayer = findPlayer(pName);
		if(newPlayer!=null){
			Nation pNation = newPlayer.getNation();
			pNation.removePlayer(newPlayer);
			if(!pNation.inUse()){
				obsInUseNations.remove(pNation);
				obsAvailableNations.add(pNation);
			}
			obsPlayers.remove(newPlayer);
			Main.currGame.removePlayer(newPlayer);
			Main.currGame.saveGame();
			clearPlayerScreen();
		}
	}

	@FXML
	void playersListClick(MouseEvent event) {
		Player p = playersList.getSelectionModel().getSelectedItem();
		if(p!=null){
			playerName.setText(p.getName());
			playerPeriod.setText(p.getPeriodString());
			playerNation.setValue(p.getNation());
			if(p.getType() == playerType.chiefOfState)
				playerPosition.setValue("Chief of State");
			else
				playerPosition.setValue("Foreign Minister");
		}
	}

	public void setAllPlayers(ArrayList<Player> players) {
		obsPlayers.setAll(players);
	}

	public void setAllNations(ArrayList<Nation> nations) {
		obsAllNations.setAll(nations);
	}
	
	private Player findPlayer(String playerName){
		
		Player newPlayer;
		newPlayer = new Player(playerName);
		
		int pIndex = obsPlayers.indexOf(newPlayer);
		if(pIndex > -1){	
			newPlayer = obsPlayers.get(pIndex);
			return newPlayer;
		}
		else
			return null;
	}
	
	private void clearPlayerScreen(){
		playerName.setText(null);
		playerPeriod.setText(null);
		playerNation.setValue(null);
		playerPosition.setValue(null);		
	}

	/***********************************************
	 * Budget Tab
	 ***********************************************/

	@FXML
	private TitledPane budgetPane;

	@FXML
	private Button budgetButton;

	@FXML
	private PieChart budgetPie;
	
    @FXML
    private Button budgetSaveButton;

	ObservableList<PieChart.Data> obsPieChartData;

	@FXML
	public void budgetButtonClick(ActionEvent event) {
		gamePane.setVisible(false);
		teamPane.setVisible(false);
		budgetPane.setVisible(true);
		leaderboardPane.setVisible(false);
		nationPane.setVisible(false);

	}
	
	@FXML
	public void budgetSaveButtonClick(ActionEvent event) {
		obsPieChartData.add(new PieChart.Data("Allocated", 75));
		obsPieChartData.remove(0);
		obsPieChartData.add(new PieChart.Data("UnAllocated", 25));
	}

	/***********************************************
	 * Leaderboard Tab
	 ***********************************************/

	@FXML
	private TitledPane leaderboardPane;

	@FXML
	private Button leaderboardButton;

	@FXML
	public void leaderboardButtonClick(ActionEvent event) {
		gamePane.setVisible(false);
		teamPane.setVisible(false);
		budgetPane.setVisible(false);
		leaderboardPane.setVisible(true);
		nationPane.setVisible(false);

	}

	/***********************************************
	 * Initialization/Round Advancement
	 ***********************************************/
	@FXML
	private Button advanceButton;

	@FXML
	public void advanceButtonClick(ActionEvent event) {

	}

	public void initialize() {
		System.out.println("Initializing UI...");

		obsInUseNations = FXCollections.observableArrayList();
		obsAvailableNations = FXCollections.observableArrayList();
		obsPlayers = FXCollections.observableArrayList();
		obsAllNations = FXCollections.observableArrayList();
		obsGames = FXCollections.observableArrayList();
		obsPieChartData = FXCollections.observableArrayList(new PieChart.Data(
				"Unallocated", 100));

		
		obsGames.setAll(Main.allGames);
		gamesList.setItems(obsGames);

		// playerNation.setItems(obsNations);

		playerPosition.setItems(FXCollections.observableArrayList(
				"Chief of State", "Foreign Minister"));
		// playerPosition.setItems();

		nationsInUseList.setItems(obsInUseNations);
		nationsAvailableList.setItems(obsAvailableNations);
		playersList.setItems(obsPlayers);
		playerNation.setItems(obsAllNations);

		/*
		 * = FXCollections .observableArrayList(new PieChart.Data("Grapefruit",
		 * 13), new PieChart.Data("Oranges", 25), new PieChart.Data( "Plums",
		 * 10), new PieChart.Data("Pears", 22), new PieChart.Data("Apples",
		 * 30));
		 */

		budgetPie.setData(obsPieChartData);
		budgetPie.setAnimated(true);
		

		gameButtonClick(null);

	}

}
