package application;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Hashtable;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Separator;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Path;
import javafx.util.Callback;

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
	private ObservableList<Player> obsNationPlayers;
	private ObservableList<Commodity> obsRequiredImports;
	private ObservableList<Commodity> obsAvailableExports;
	public ObservableList<Trade> obsNationImports;
	public ObservableList<Trade> obsNationExports;

	@FXML
	private Button nationButton;
	
	 @FXML
	 private Button nationSaveButton;

	@FXML
	private TitledPane nationPane;

	@FXML
	private TitledPane nationsInUsePane;
	
	@FXML
	private TitledPane nationInfoPane;
	
	@FXML
	private TitledPane nationTeamMemberPane;
	 
	@FXML
	private TitledPane nationPoliticalPane;
	 
	@FXML
	private TitledPane nationTradePane;

	@FXML
	private Label titleLabel;
	
	@FXML
	private Accordion nationAccordian;

	@FXML
	private Label nationConForcesLabel;

	@FXML
	private Label nationCurrGDPLabel;

	@FXML
	private Label nationExportAmRemLabel;

	@FXML
	private Label nationRequiredImportsLabel;
	
	@FXML
	private Label nationTotalImportsLabel;
	
	@FXML
	private Label nationTotalExportsLabel;

	@FXML
	private Label nationMaxExportLabel;

	@FXML
    private TextField nationMaxExportField;

	@FXML
	private Label nationNucForcesLabel;

	@FXML
	private ListView<Nation> nationsAvailableList;

	@FXML
	private ListView<Nation> nationsInUseList;

	@FXML
	private TableView<Player> nationPlayerTable;

	@FXML
	private TableColumn<Player, String> nationPlayerNameColumn;

	@FXML
	private TableColumn<Player, String> nationPlayerPositionColumn;

	@FXML
	private ListView<Commodity> nationRequiredImports;

	@FXML
	private ListView<Commodity> nationAvailableExports;

	@FXML
	private TableView<Trade> nationImportTable;
	
	@FXML
	private TableView<Trade> nationExportTable;

	@FXML
	private TableColumn<Trade, BigDecimal> nationImportAmountColumn;

	@FXML
	private TableColumn<Trade, Commodity> nationImportNameColumn;

	@FXML
	private TableColumn<Trade, Nation> nationImportNationColumn;

	@FXML
	private TableColumn<Trade, String> nationImportTypeColumn;
	
	@FXML
	private TableColumn<Trade, BigDecimal> nationExportAmountColumn;

	@FXML
	private TableColumn<Trade, Commodity> nationExportNameColumn;
	
	@FXML
	private TableColumn<Trade, Nation> nationExportNationColumn;

	@FXML
	private TableColumn<Trade, String> nationExportTypeColumn;
	

	@FXML
	public void nationButtonClick(ActionEvent event) {
		gamePane.setVisible(false);
		teamPane.setVisible(false);
		budgetPane.setVisible(false);
		leaderboardPane.setVisible(false);
		nationPane.setVisible(true);
		
		if(Main.currNation == null){
			nationInfoPane.setDisable(true);
			nationTeamMemberPane.setDisable(true);
			nationPoliticalPane.setDisable(true);
			nationTradePane.setDisable(true);
		}
	}
	
	@FXML
    void nationSaveButtonClick(ActionEvent event) {
		
		for(Trade t:obsNationImports){
			if(t!=null){
				if(!Main.currGame.getTrades().contains(t.getTradeData())){
					Main.currGame.getTrades().add(t.getTradeData());
				}
			}
		}
		Main.currGame.saveGame();
    }

	@FXML
	void nationsInUseListMouseClick(MouseEvent event) {

		Nation n = nationsInUseList.getSelectionModel().getSelectedItem();
		if (n != null) {
			
			Main.currNation = n;
			if(n.getMaxExportSet()){
				nationTradePane.setDisable(false);
				nationMaxExportLabel.setText(NumberFormat.getCurrencyInstance().format(n.getMaxExports()));
				nationMaxExportLabel.setVisible(true);
				nationMaxExportField.setVisible(false);
			}else{
				nationTradePane.setDisable(true);
				nationMaxExportLabel.setVisible(false);
				nationMaxExportField.setText(null);
				nationMaxExportField.setVisible(true);	
			}
			
			nationInfoPane.setDisable(false);
			nationTeamMemberPane.setDisable(false);
			nationPoliticalPane.setDisable(false);
			
			obsNationImports.clear();
			obsNationExports.clear();
			
			BigDecimal totalImports = BigDecimal.ZERO;
			BigDecimal totalExports = BigDecimal.ZERO;
			if(n.getTeam()!=null)
				obsNationPlayers.setAll(n.getTeam());
			if(n.getAvailableExports()!=null)
				obsAvailableExports.setAll(n.getAvailableExports());
			if(n.getRequiredImports()!=null)
				obsRequiredImports.setAll(n.getRequiredImports());
			if(Main.currGame.getTrades()!=null){
					for(TradeData t:Main.currGame.getTrades()){
						if(t.getImporter() == n){
							Trade newTrade = new Trade(t);
							obsNationImports.add(newTrade);
							totalImports = totalImports.add(t.getAmount());
						}else if(t.getExporter() == n){
							Trade newTrade = new Trade(t);
							obsNationExports.add(newTrade);
							totalExports = totalExports.add(t.getAmount());
						}
					}
			}
			if(!obsNationImports.contains(null))
				obsNationImports.add(obsNationImports.size(), null);

			titleLabel.setText(n.getName().toLowerCase() + " | "
					+ NumberFormat.getCurrencyInstance().format(n.getGnp()));
			nationCurrGDPLabel.setText(NumberFormat.getCurrencyInstance()
					.format(n.getGnp()));
			nationTotalImportsLabel.setText(NumberFormat.getCurrencyInstance().format(totalImports));		
			nationTotalExportsLabel.setText(NumberFormat.getCurrencyInstance().format(totalExports));
			nationConForcesLabel.setText(NumberFormat.getCurrencyInstance().format(n.getConventionalForcesAllocation()));
			nationNucForcesLabel.setText(NumberFormat.getCurrencyInstance().format(n.getNuclearForcesAllocation()));
			
			clearAllocationChart();
			/*
			 * playerName.setText(p.getName());
			 * playerPeriod.setText(p.getPeriodString());
			 * playerNation.setValue(p.getNation()); if(p.getType() ==
			 * playerType.chiefOfState)
			 * playerPosition.setValue("Chief of State"); else
			 * playerPosition.setValue("Foreign Minister");
			 */
		}

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
	
	@FXML
    void nationMaxExportFieldAction(ActionEvent event) {
		
		BigDecimal b = new BigDecimal(nationMaxExportField.getText());
    	if(b!=null)
    		if(Main.currNation.setMaxExports(b)){
    			nationMaxExportField.setVisible(false);
    			nationMaxExportLabel.setText(NumberFormat.getCurrencyInstance().format(Main.currNation.getMaxExports()));
    			nationMaxExportLabel.setVisible(true);
    			nationTradePane.setDisable(false);
    			nationTradePane.requestFocus();
    	}	
    }

	/*@FXML
	void nationImportTradeNationEdit(CellEditEvent<Trade, Nation> event) {
		
		boolean test = true;
		Nation n = event.getNewValue();
		if (n != null) {
			obsCommodities.removeAll(obsCommodities);
			obsCommodities.setAll(n.getAvailableExports());
	
		}
	}*/
	
	/* @FXML
	 void nationImportNameColumnEditStart(CellEditEvent<Trade, Commodity> event) {
		 Trade t = nationImportTable.getSelectionModel().getSelectedItem();
	
		 if(n!=null){
			 obsCommodities.setAll(n.getAvailableExports());
		 }
	 }*/

	public void initializeNationTab() {
		
		nationAccordian.setExpandedPane(nationsInUsePane);
		
		
		obsNationPlayers = FXCollections.observableArrayList();
		obsNationImports = FXCollections.observableArrayList();
		obsNationExports = FXCollections.observableArrayList();
		obsInUseNations = FXCollections.observableArrayList();
		obsAvailableNations = FXCollections.observableArrayList();
		obsRequiredImports = FXCollections.observableArrayList();
		obsAvailableExports = FXCollections.observableArrayList();

		nationPlayerNameColumn
				.setCellValueFactory(new PropertyValueFactory<Player, String>(
						"name"));
		nationPlayerPositionColumn
				.setCellValueFactory(new PropertyValueFactory<Player, String>(
						"typeString"));
		
		/*
		 * Current Imports Tab
		 */
		
		nationImportNationColumn
				.setCellValueFactory(new PropertyValueFactory<Trade, Nation>(
						"exporter"));
		nationImportNationColumn.setCellFactory(new Callback<TableColumn<Trade,Nation>,TableCell<Trade,Nation>>(){
			@Override
			public TableCell<Trade, Nation> call(
					TableColumn<Trade, Nation> param) {				
				return new ComboBoxTableCell<Trade, Nation>(obsInUseNations){	
					@Override public void updateItem(Nation n, boolean empty){
						//send false because we really have no empty cells, blank cells should be changeable
						super.updateItem(n, false);
					}
					
					
					@Override public void commitEdit(Nation n){
						super.commitEdit(n);
						Trade t = null;
					
						if(this.getIndex() < this.getTableView().getItems().size()){
							t = this.getTableView().getItems().get(this.getIndex());
							if(t == null){
								
								TradeData newTradeData = new TradeData(n.getAvailableExports().get(0),Main.currNation,n,BigDecimal.ZERO);
								Trade newTrade = new Trade(newTradeData);
								obsNationImports.remove(null);
								obsNationImports.add(newTrade);
								obsNationImports.add(null);
								nationImportTable.getSelectionModel().select(this.getIndex());
							}else{  //if the nation changes, we need to reset the whole trade
								t.setExporter(n);
								t.setCommodity(n.getAvailableExports().get(0));
							}
						}						
					}
				};
			}
		});

		nationImportNameColumn
				.setCellValueFactory(new PropertyValueFactory<Trade, Commodity>(
						"commodity"));		
		nationImportNameColumn.setCellFactory(new Callback<TableColumn<Trade,Commodity>,TableCell<Trade,Commodity>>(){
			@Override
			public TableCell<Trade, Commodity> call(
					TableColumn<Trade, Commodity> param) {				
				return new ComboBoxTableCell<Trade, Commodity>(){				
					@Override public void startEdit(){
						super.startEdit();
						Trade t = null;
						if(this.getIndex() < this.getTableView().getItems().size()){
							t = this.getTableView().getItems().get(this.getIndex());
							if(t!=null){
								this.getItems().setAll(t.getExporter().getAvailableExports());
							}else{
								this.getItems().clear();			
							}
						}
					}
					
					//Don't commit the edit if we have a null value. Clicking in the center of the cell does this for some reason.
					@Override public void commitEdit(Commodity c){
						if(c!=null){
							super.commitEdit(c);								
						}
					}
				};
			}
		});
		nationImportTypeColumn
				.setCellValueFactory(new PropertyValueFactory<Trade, String>(
						"type"));
		nationImportAmountColumn
				.setCellValueFactory(new PropertyValueFactory<Trade, BigDecimal>(
						"amount"));	
		nationImportAmountColumn.setCellFactory(new Callback<TableColumn<Trade,BigDecimal>,TableCell<Trade,BigDecimal>>(){
			@Override
			public TableCell<Trade, BigDecimal> call(TableColumn<Trade, BigDecimal> arg0) {
				return new CurrencyFieldTableCell<Trade,BigDecimal>();
			}
		});
		
		
		/*
		 * Current Exports Tab
		 */
		nationExportNationColumn
			.setCellValueFactory(new PropertyValueFactory<Trade, Nation>(
				"importer"));
		nationExportNameColumn
			.setCellValueFactory(new PropertyValueFactory<Trade, Commodity>(
				"commodity"));	
		nationExportTypeColumn
			.setCellValueFactory(new PropertyValueFactory<Trade, String>(
				"type"));
		nationExportAmountColumn
			.setCellValueFactory(new PropertyValueFactory<Trade, BigDecimal>(
				"amount"));	
		nationExportAmountColumn.setCellFactory(new Callback<TableColumn<Trade,BigDecimal>,TableCell<Trade,BigDecimal>>(){
			@Override
			public TableCell<Trade, BigDecimal> call(TableColumn<Trade, BigDecimal> arg0) {
				return new CurrencyFieldTableCell<Trade,BigDecimal>();
				}
			});	
		
		nationsInUseList.setItems(obsInUseNations);
		nationsAvailableList.setItems(obsAvailableNations);
		nationPlayerTable.setItems(obsNationPlayers);
		nationAvailableExports.setItems(obsAvailableExports);
		nationRequiredImports.setItems(obsRequiredImports);
		
		nationImportTable.setItems(obsNationImports);
		nationImportTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		nationExportTable.setItems(obsNationExports);
		nationExportTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

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

		if (newPlayer == null) {

			newPlayer = new Player(pName);

			// add to nation list if newly in use
			if (!pNation.inUse()) {
				obsAvailableNations.remove(pNation);
				obsInUseNations.add(pNation);
			}

			// add player to nation
			pNation.addPlayer(newPlayer);

			// add player to player list and curr game
			obsPlayers.add(newPlayer);
			Main.currGame.addPlayer(newPlayer);
		} else {

			// player does exist, assume a nation change, so update as necessary
			Nation prevNation = newPlayer.getNation();

			if (prevNation != null) {
				prevNation.removePlayer(newPlayer);
				if (!prevNation.inUse()) {
					obsInUseNations.remove(prevNation);
					obsAvailableNations.add(prevNation);
				}
			}

			if (!pNation.inUse()) {
				obsAvailableNations.remove(pNation);
				obsInUseNations.add(pNation);
			}

			pNation.addPlayer(newPlayer);
		}

		// change values on the player
		newPlayer.setPeriod(pPeriod);
		newPlayer.setNation(pNation);
		if (pType.equals("Chief of State"))
			newPlayer.setType(playerType.chiefOfState);
		else if (pType.equals("Foreign Minister"))
			newPlayer.setType(playerType.foreignMinister);

		// save the state
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
		if (newPlayer != null) {
			Nation pNation = newPlayer.getNation();
			pNation.removePlayer(newPlayer);
			if (!pNation.inUse()) {
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
		if (p != null) {
			playerName.setText(p.getName());
			playerPeriod.setText(p.getPeriodString());
			playerNation.setValue(p.getNation());
			if (p.getType() == playerType.chiefOfState)
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

	private Player findPlayer(String playerName) {

		Player newPlayer;
		newPlayer = new Player(playerName);

		int pIndex = obsPlayers.indexOf(newPlayer);
		if (pIndex > -1) {
			newPlayer = obsPlayers.get(pIndex);
			return newPlayer;
		} else
			return null;
	}

	private void clearPlayerScreen() {
		playerName.setText(null);
		playerPeriod.setText(null);
		playerNation.setValue(null);
		playerPosition.setValue(null);
	}

	/***********************************************
	 * Budget Tab
	 ***********************************************/

	
	private ObservableList<PieChart.Data> obsPieChartData;
	private static final PieChart.Data unallocatedPiePiece = new PieChart.Data("Unallocated Funds", 0);
	private static final PieChart.Data basicGoodsPiePiece = new PieChart.Data("Basic Goods", 0);
	private static PieChart.Data conForcesPiePiece = new PieChart.Data("Conventional Forces", 0);
	private static PieChart.Data nuclearForcesPiePiece = new PieChart.Data("Nuclear Forces", 0);
	private static PieChart.Data importsPiePiece = new PieChart.Data("Imports", 0);
	private static PieChart.Data researchPiePiece = new PieChart.Data("Research and Development", 0);
	private static PieChart.Data contingencyPiePiece = new PieChart.Data("Contingency Funds", 0);
	private static PieChart.Data capitalGoodsPiePiece = new PieChart.Data("Capital Goods", 0);
	
	
	@FXML
	private TitledPane budgetPane;

	@FXML
	private Button budgetButton;

	@FXML
	private PieChart budgetPie;

	@FXML
	private Button budgetSaveButton;
	
	@FXML
	private GridPane budgetGrid;
	
	@FXML
	private TextField budgetCapitalGoodsField;

	@FXML
	private TextField budgetConForcesField;

	@FXML
	private TextField budgetContingencyField;
	
	@FXML
	private TextField budgetNucForcesField;
	
    @FXML
    private TextField budgetRDField;
    
    @FXML
    private TextField budgetImportsField;

    @FXML
    private TextField budgetBasicGoodsField;
    
    @FXML
    private Label budgetBasicGoodsLabel;
    
    @FXML
    private Label budgetBasicGoodsPercentLabel;
    
    @FXML
    private Label budgetConForcesLabel;
    
    @FXML
    private Label budgetConForcesPercentLabel;
    
    @FXML
    private Label budgetNucForcesLabel;
    
    @FXML
    private Label budgetNucForcesPercentLabel;
    
    @FXML
    private Label budgetImportsLabel;
    
    @FXML
    private Label budgetImportsPercentLabel;
    
	
	@FXML
	public void budgetButtonClick(ActionEvent event) {
		gamePane.setVisible(false);
		teamPane.setVisible(false);
		budgetPane.setVisible(true);
		leaderboardPane.setVisible(false);
		nationPane.setVisible(false);
		updateAllocationChart();
	}
	
	@FXML
	public void budgetSaveButtonClick(ActionEvent event) {
		/*obsPieChartData.add(new PieChart.Data("Allocated", 75));
		obsPieChartData.remove(0);
		obsPieChartData.add(new PieChart.Data("UnAllocated", 25));*/
	
	}	
	
    @FXML
    public void budgetBasicGoodsFieldAction(ActionEvent event) {
    	BigDecimal basicGoods = new BigDecimal(budgetBasicGoodsField.getText());
    	if(basicGoods!=null){
    		if(Main.currNation.setBasicGoodsAllocation(basicGoods)){
    			budgetBasicGoodsLabel.setText(NumberFormat.getCurrencyInstance().format(basicGoods));
    			budgetBasicGoodsField.setVisible(false);
    			budgetBasicGoodsLabel.setVisible(true);
    			budgetBasicGoodsPercentLabel.setText(NumberFormat.getPercentInstance().format(basicGoods.divide(Main.currNation.getGnp(), 2, RoundingMode.HALF_UP)));
    			budgetBasicGoodsPercentLabel.setVisible(true);
    			updateAllocationChart();			
    		}
    	}
    }
    
    @FXML
	void budgetConForcesAction(ActionEvent event) {
		BigDecimal conForces = new BigDecimal(budgetConForcesField.getText());
		if(conForces!=null){
			if(Main.currNation.setConventionalForcesAllocation(conForces)){
				budgetConForcesLabel.setText(NumberFormat.getCurrencyInstance().format(conForces));
				budgetConForcesField.setVisible(false);
				budgetConForcesLabel.setVisible(true);
				budgetConForcesPercentLabel.setText(NumberFormat.getPercentInstance().format(conForces.divide(Main.currNation.getGnp(), 2, RoundingMode.HALF_UP)));
				budgetConForcesPercentLabel.setVisible(true);
				updateAllocationChart();
			}
		}
	}
    
    @FXML
    public void budgetNucForcesFieldAction(ActionEvent event) {
    	BigDecimal nucForces = new BigDecimal(budgetNucForcesField.getText());
    	if(nucForces!=null){
    		if(Main.currNation.setNuclearForcesAllocation(nucForces)){
    			budgetNucForcesLabel.setText(NumberFormat.getCurrencyInstance().format(nucForces));
    			budgetNucForcesField.setVisible(false);
    			budgetNucForcesLabel.setVisible(true);
    			budgetNucForcesPercentLabel.setText(NumberFormat.getPercentInstance().format(nucForces.divide(Main.currNation.getGnp(), 2, RoundingMode.HALF_UP)));
    			budgetNucForcesPercentLabel.setVisible(true);
    			updateAllocationChart();
    		}
    	}
    	
    }
    
    @FXML
    public void budgetImportsFieldAction(ActionEvent event) {
    	BigDecimal imports = new BigDecimal(budgetImportsField.getText());
    	if(imports!=null){
    		if(Main.currNation.setImportsAllocation(imports)){
    			budgetImportsLabel.setText(NumberFormat.getCurrencyInstance().format(imports));
    			budgetImportsField.setVisible(false);
    			budgetImportsLabel.setVisible(true);
    			budgetImportsPercentLabel.setText(NumberFormat.getPercentInstance().format(imports.divide(Main.currNation.getGnp(), 2,RoundingMode.HALF_UP)));
    			budgetImportsPercentLabel.setVisible(true);
    			updateAllocationChart();
    		}
    	}
    }
    
    public void clearAllocationChart(){
    	obsPieChartData.clear();
    	unallocatedPiePiece.setPieValue(0);
    	basicGoodsPiePiece.setPieValue(0);
    	conForcesPiePiece.setPieValue(0);
    	nuclearForcesPiePiece.setPieValue(0);
    	importsPiePiece.setPieValue(0);
    	researchPiePiece.setPieValue(0);
    	contingencyPiePiece.setPieValue(0);
    	capitalGoodsPiePiece.setPieValue(0);
    	obsPieChartData.add(unallocatedPiePiece);	
    }
    
    
    public void updateAllocationChart(){
    	
    	
    	if(Main.currNation!=null){
    		
    		BigDecimal totalAllocation = BigDecimal.ZERO;    		
    		if(Main.currNation.getBasicGoodsSet()){
    			totalAllocation = totalAllocation.add(Main.currNation.getBasicGoodsAllocation());
   				basicGoodsPiePiece.setPieValue(Main.currNation.getBasicGoodsAllocation().doubleValue());
   				if(!obsPieChartData.contains(basicGoodsPiePiece))
   					obsPieChartData.add(basicGoodsPiePiece);
    		}
    		
    		if(Main.currNation.getConventionalForcesSet()){
    			totalAllocation = totalAllocation.add(Main.currNation.getConventionalForcesAllocation());
    			conForcesPiePiece.setPieValue(Main.currNation.getConventionalForcesAllocation().doubleValue());
    			if(!obsPieChartData.contains(conForcesPiePiece))
    				obsPieChartData.add(conForcesPiePiece);
    		}
    		
    		if(Main.currNation.getNuclearForcesSet()){
    			totalAllocation = totalAllocation.add(Main.currNation.getNuclearForcesAllocation());
    			nuclearForcesPiePiece.setPieValue(Main.currNation.getNuclearForcesAllocation().doubleValue());
    			if(!obsPieChartData.contains(nuclearForcesPiePiece))
    				obsPieChartData.add(nuclearForcesPiePiece);
    		}
    		
    		if(Main.currNation.getImportsSet()){
    			totalAllocation = totalAllocation.add(Main.currNation.getImportsAllocation());
    			importsPiePiece.setPieValue(Main.currNation.getImportsAllocation().doubleValue());
    			if(!obsPieChartData.contains(importsPiePiece))
    				obsPieChartData.add(importsPiePiece);
    		}
    		
    		if(totalAllocation.compareTo(Main.currNation.getGnp())<0){
    			unallocatedPiePiece.setPieValue(Main.currNation.getGnp().subtract(totalAllocation).doubleValue());
    		}		
    	}
    }
    
	
	public void initializeBudgetTab(){
	
		obsPieChartData = FXCollections.observableArrayList();
		obsPieChartData.add(unallocatedPiePiece);

		budgetPie.setData(obsPieChartData);
		budgetPie.setAnimated(true);
	}

	/***********************************************
	 * Leaderboard Tab
	 ***********************************************/
	private ObservableList<Series<Number, Number>> obsRoundData;
	private Hashtable <String, Series<Number,Number>> allSeries;

	@FXML
	private TitledPane leaderboardPane;

	@FXML
	private Button leaderboardButton;

	@FXML
	private LineChart<Number, Number> leaderboardChart;

	@FXML
	public void leaderboardButtonClick(ActionEvent event) {
		gamePane.setVisible(false);
		teamPane.setVisible(false);
		budgetPane.setVisible(false);
		leaderboardPane.setVisible(true);
		nationPane.setVisible(false);

	}
	
	public void changeLeaderboard(){
		
		if(Main.currGame!=null){
			
			for(Nation nat:Main.currGame.getRounds().get(0).getNations()){
				if(Main.currGame.getNation(nat.getCountryCode()).inUse()){
					final XYChart.Series<Number, Number> newSeries = new XYChart.Series<>();
					newSeries.setName(nat.getName());
					newSeries.getData().add(new Data<Number, Number>(0, nat.getGnp()));
					obsRoundData.add(newSeries);
				}
			}
		}
		
	}
	
	public void initializeLeaderBoardTab(){	

		leaderboardChart.setTitle("GNP Per Round");
		leaderboardChart.setData(obsRoundData);
		final XYChart.Series<Number, Number> series = new XYChart.Series<>();
		series.setName("Ghana");
		series.getData().add(new Data<Number, Number>(3, 15));
		series.getData().add(new Data<Number, Number>(4, 24));
		series.getData().add(new Data<Number, Number>(5, 34));
		series.getData().add(new Data<Number, Number>(6, 36));
		series.getData().add(new Data<Number, Number>(7, 22));
		series.getData().add(new Data<Number, Number>(8, 45));
		series.getData().add(new Data<Number, Number>(9, 43));
		series.getData().add(new Data<Number, Number>(10, 17));

		obsRoundData.add(series);

		// make the first series in the chart glow when you mouse over it.
		Node n = series.getNode();
		// Node n = leaderboardChart.lookup(".chart-series-line.series0");
		if (n != null && n instanceof Path) {
			final Path path = (Path) n;
			final Glow glow = new Glow(.8);
			path.setEffect(null);
			path.setOnMouseEntered(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent e) {
					path.setEffect(glow);
					Tooltip.install(path, new Tooltip(series.getName()));
				}
			});
			path.setOnMouseExited(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent e) {
					path.setEffect(null);
				}
			});
		}
		
		
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

		obsPlayers = FXCollections.observableArrayList();
		obsAllNations = FXCollections.observableArrayList();
		obsGames = FXCollections.observableArrayList();
		obsRoundData = FXCollections.observableArrayList();

		obsGames.setAll(Main.allGames);
		gamesList.setItems(obsGames);
		playerPosition.setItems(FXCollections.observableArrayList(
				"Chief of State", "Foreign Minister"));

		playersList.setItems(obsPlayers);
		playerNation.setItems(obsAllNations);
		initializeNationTab();
		initializeLeaderBoardTab();
		initializeBudgetTab();
		gameButtonClick(null);

	}

}
