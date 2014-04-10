package application;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Hashtable;

import javafx.beans.binding.StringBinding;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableObjectValue;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.ObservableValueBase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.VPos;
import javafx.print.PrinterJob;
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
import javafx.scene.control.ListCell;
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
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Path;
import javafx.util.Callback;
import javafx.util.converter.BigDecimalStringConverter;

public class UIController {

	/***********************************************
	 * Game Tab
	 **********************************************/
	private ObservableList<Game> obsGames;
	private ObservableList<InternationalOrganization> obsOrgs;
	private ObservableList<Teacher> obsTeachers;
	private ObservableList<ContingencyTransaction> obsIMFTransactions;

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
	private TableView<Nation> gameNationTable;
	
    @FXML
    private TableColumn<Nation,String> gameNationColumn;

    @FXML
    private TableColumn<Nation,String> gamePasswordColumn;
    
    @FXML
    private TableView<InternationalOrganization> gameIntOrgTable;
    
    @FXML
    private TableColumn<InternationalOrganization,String> gameIntOrgNameColumn;

    @FXML
    private TableColumn<InternationalOrganization,String> gameIntOrgPasswordColumn;
   
    @FXML
    private Button gamePrintPasswordButton;
   
    @FXML
    private Button gameIntOrgAddButton;

    @FXML
    private Button gameIntOrgDeleteButton;

    @FXML
    private TextField gameIntOrgNameField;
    
    @FXML
    private ListView<Teacher> gameTeacherList;
    
    @FXML
    private TextField gameTeacherTextField;
    
    @FXML
    private Button gameTeacherAddButton;
    
    @FXML
    private Button gameTeacherDeleteButton;
    
    @FXML
    private Button gameIMFAddButton;
    
    @FXML
    private Button gameIMFDeleteButton;
    
    @FXML
    private TextField gameIMFAmountField;
    
    @FXML
    private ComboBox<Nation> gameIMFNationDropDown;
    
	@FXML
	private TableView<ContingencyTransaction> gameIMFTable;
	
	@FXML
	private TableColumn<ContingencyTransaction,Nation> gameIMFNationColumn;

	@FXML
	private TableColumn<ContingencyTransaction,BigDecimal> gameIMFAmountColumn;


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
	
	@FXML
    void gameIntOrgAddButtonAction(ActionEvent event) {
		String name = gameIntOrgNameField.getText();
		if(name!=null){
			InternationalOrganization newOrg = new InternationalOrganization(name, null);
			Main.currGame.addInternationalOrganization(newOrg);
			gameIntOrgNameField.setText(null);
			obsOrgs.add(newOrg);
		}
    }

    @FXML
    void gameIntOrgDeleteButtonAction(ActionEvent event) {
    	InternationalOrganization org = gameIntOrgTable.getSelectionModel().getSelectedItem();
    	if(org!=null){
    		Main.currGame.removeInternationalOrganization(org);
    		obsOrgs.remove(org);
    	}
    }
    
    @FXML
    void gamePrintPasswordButtonAction(ActionEvent event) {
    	PrinterJob job = PrinterJob.createPrinterJob();
    	if(job!=null){
    		 boolean success = job.printPage(gameNationTable);
    		    if (success) {
    		        job.endJob();
    		    }
    	}
    }
    
    @FXML
    void gameTeacherAddButtonAction(ActionEvent event) {
    	String name = gameTeacherTextField.getText();
    	if(name!=null){
    		Teacher newTeach = new Teacher(name);
    		Main.currGame.getTeachers().add(newTeach);
    		gameTeacherTextField.setText(null);
    		obsTeachers.add(newTeach);
    	}
    }
    
    @FXML
    void gameTeacherDeleteButtonAction(ActionEvent event) {
    	Teacher teach = gameTeacherList.getSelectionModel().getSelectedItem();
    	if(teach!=null){
    		Main.currGame.getTeachers().remove(teach);
    		obsTeachers.remove(teach);
    	}    
    }
    
    @FXML
    void gameIMFAddButtonAction(ActionEvent event) {
    	Nation nat = gameIMFNationDropDown.getValue();
    	if(nat!=null){
    		BigDecimal amt = new BigDecimal(gameIMFAmountField.getText());
    		if(amt!=null){
    			ContingencyTransaction cont = new ContingencyTransaction(ContingencyType.IMF,null,nat,amt);
    			Main.currGame.getContingencyTransactions().add(cont);
    			obsIMFTransactions.add(cont);
    			gameIMFAmountField.setText(null);
    			gameIMFNationDropDown.getSelectionModel().clearSelection();
    		}
    	}
    }
    
    @FXML
    void gameIMFDeleteButtonAction(ActionEvent event) {
    	ContingencyTransaction cont = gameIMFTable.getSelectionModel().getSelectedItem();
    	if(cont!=null){
    		Main.currGame.getContingencyTransactions().remove(cont);
    		obsIMFTransactions.remove(cont);
    	}
    }
    
    public void setAllTeachers(ArrayList<Teacher> tList){
    	obsTeachers.setAll(tList);
    }
    
    public void setAllInternationalOrgs(ArrayList<InternationalOrganization> orgsList){
    	obsOrgs.setAll(orgsList);
    }
    
    public void setAllIMFTransactions(ArrayList<ContingencyTransaction> imfList){
    	obsIMFTransactions.setAll(imfList);
    }
	
	private void initializeGameTab(){
		obsGames = FXCollections.observableArrayList();
		obsInUseNations = FXCollections.observableArrayList();
		obsInUseNationsMinusCurrent = FXCollections.observableArrayList();
		obsOrgs = FXCollections.observableArrayList();
		obsTeachers = FXCollections.observableArrayList();
		obsIMFTransactions = FXCollections.observableArrayList();
		
		obsGames.setAll(Main.allGames);
		gamesList.setItems(obsGames);
		gameTeacherList.setItems(obsTeachers);
		
		gameNationTable.setItems(obsInUseNations);
		gameNationColumn
			.setCellValueFactory(new PropertyValueFactory<Nation, String>(
				"name"));
		gamePasswordColumn
			.setCellValueFactory(new PropertyValueFactory<Nation, String>(
				"password"));		
		
		gameIntOrgTable.setItems(obsOrgs);
		gameIntOrgNameColumn
			.setCellValueFactory(new PropertyValueFactory<InternationalOrganization, String>(
			"name"));
		gameIntOrgPasswordColumn
		.setCellValueFactory(new PropertyValueFactory<InternationalOrganization, String>(
			"password"));	
		
		gameIMFNationDropDown.setItems(obsInUseNations);
		gameIMFTable.setItems(obsIMFTransactions);
		gameIMFNationColumn.setCellValueFactory(new PropertyValueFactory<ContingencyTransaction, Nation>("receiver"));
		gameIMFAmountColumn.setCellValueFactory(new PropertyValueFactory<ContingencyTransaction, BigDecimal>("amount"));
		gameIMFAmountColumn.setCellFactory(new Callback<TableColumn<ContingencyTransaction,BigDecimal>,TableCell<ContingencyTransaction,BigDecimal>>(){
			@Override
			public TableCell<ContingencyTransaction, BigDecimal> call(
					TableColumn<ContingencyTransaction, BigDecimal> param) {
				// TODO Auto-generated method stub
				return new CurrencyFieldTableCell<ContingencyTransaction,BigDecimal>();
			}
			
		});
		
		
		
	}

	/****************************************************************************************************************************
	 * Nation Tab
	 ****************************************************************************************************************************/
	private ObservableList<Nation> obsInUseNations;
	private ObservableList<Nation> obsInUseNationsMinusCurrent;
	private ObservableList<Nation> obsPossibleAlliances;
	private ObservableList<Nation> obsPossiblePacts;
	private ObservableList<Nation> obsPossibleNeutralities;
	private ObservableList<InternationalOrganization> obsPossibleOrgs;
	private ObservableList<Nation> obsAvailableNations;
	private ObservableList<Player> obsNationPlayers;
	private ObservableList<Commodity> obsRequiredImports;
	private ObservableList<Commodity> obsAvailableExports;
	private ObservableList<Trade> obsNationImports;
	private ObservableList<Trade> obsNationExports;
	private ObservableList<Nation> obsAlliances;
	private ObservableList<Nation> obsNeutrality;
	private ObservableList<Nation> obsPacts;
	private ObservableList<ContingencyType> obsContingencyTypes;
	private ObservableList<InternationalOrganization> obsNationOrgs;
	private SimpleObjectProperty<BigDecimal> maxImports;
	private SimpleObjectProperty<BigDecimal> maxExports;
	private SimpleObjectProperty<BigDecimal> totalCurrentImports;
	private SimpleObjectProperty<BigDecimal> totalCurrentExports;
	private ObservableList<ContingencyTransaction> obsContingencyGiven;
	private ObservableList<ContingencyTransaction> obsContingencyReceived;

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
	private TitledPane nationContingencyPane;

	@FXML
	private Label titleLabel;
	
	@FXML
	private Accordion nationAccordian;

	@FXML
	private Label nationConForcesLabel;

	@FXML
	private Label nationCurrGDPLabel;
	
	@FXML
	private Label nationStartGDPLabel;

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
	private TableColumn<Player, Teacher> nationPlayerTeacherColumn;

	@FXML
	private TableColumn<Player, Integer> nationPlayerPeriodColumn;

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
    private ListView<Nation> nationAlliancesList;
    
    @FXML
    private ListView<Nation> nationNeutralitiesList;
    
    @FXML
    private ListView<Nation> nationPactsList;
    
    @FXML
    private ListView<InternationalOrganization> nationOrgsList;
    
    @FXML
    private TableView<ContingencyTransaction> nationContingencyGivenTable;
    
    @FXML
    private TableColumn<ContingencyTransaction,BigDecimal> nationContingencyGivenAmountColumn;

    @FXML
    private TableColumn<ContingencyTransaction,Nation> nationContingencyGivenNationColumn;

    @FXML
    private TableColumn<ContingencyTransaction,ContingencyType> nationContingencyGivenTypeColumn;
    
    @FXML
    private TableView<ContingencyTransaction> nationContingencyReceivedTable;

    @FXML
    private TableColumn<ContingencyTransaction,BigDecimal> nationContingencyReceivedAmountColumn;

    @FXML
    private TableColumn<ContingencyTransaction,Nation> nationContingencyReceivedNationColumn;

    @FXML
    private TableColumn<ContingencyTransaction,ContingencyType> nationContingencyReceivedTypeColumn;
    
    @FXML
    private ListView<Nation> testListView;
    
   

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
			nationContingencyPane.setDisable(true);
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
			obsInUseNationsMinusCurrent.setAll(obsInUseNations);
			obsInUseNationsMinusCurrent.remove(Main.currNation);
			obsPossibleAlliances.setAll(obsInUseNationsMinusCurrent);
			obsPossibleNeutralities.setAll(obsInUseNationsMinusCurrent);
			obsPossiblePacts.setAll(obsInUseNationsMinusCurrent);
			obsPossibleOrgs.setAll(obsOrgs);
			
			if(n.fullyAllocated()){
				nationTradePane.setDisable(false);		
				nationContingencyPane.setDisable(false);
				nationInfoPane.setDisable(false);
				nationTeamMemberPane.setDisable(false);
				nationPoliticalPane.setDisable(false);
			}
			else{
				nationTradePane.setDisable(true);	
				nationContingencyPane.setDisable(true);
				nationInfoPane.setDisable(true);
				nationTeamMemberPane.setDisable(true);
				nationPoliticalPane.setDisable(true);
			}		
			
			obsNationImports.removeAll(obsNationImports);
			obsNationExports.removeAll(obsNationExports);
			
			
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
			nationStartGDPLabel.setText(NumberFormat.getCurrencyInstance().format(Main.currGame.getRounds().get(0).getNation(Main.currNation.getCountryCode()).getGnp()));
			maxImports.set(Main.currNation.getImportsAllocation());
			totalCurrentImports.set(totalImports);
			maxExports.set(Main.currNation.getMaxExports());
			totalCurrentExports.set(totalExports);
			nationConForcesLabel.setText(NumberFormat.getCurrencyInstance().format(n.getConventionalForcesAllocation()));
			nationNucForcesLabel.setText(NumberFormat.getCurrencyInstance().format(n.getNuclearForcesAllocation()));
			
			obsAlliances.clear();
			obsNeutrality.clear();
			obsPacts.clear();
			for(Relationship r:n.getRelationships()){
				if(r instanceof Alliance){
					obsAlliances.add(r.getOtherNation(n));
					obsPossibleAlliances.remove(r.getOtherNation(n));
				}else if(r instanceof Neutrality){
					obsNeutrality.add(r.getOtherNation(n));
					obsPossibleNeutralities.remove(r.getOtherNation(n));
				}else if(r instanceof NonAggressionPact){
					obsPacts.add(r.getOtherNation(n));
					obsPossiblePacts.remove(r.getOtherNation(n));
				}
			}
			obsAlliances.add(null);
			obsNeutrality.add(null);
			obsPacts.add(null);
			
			obsNationOrgs.clear();
			for(InternationalOrganization org:n.getOrganizations()){
				obsNationOrgs.add(org);		
				obsPossibleOrgs.remove(org);
			}
			obsNationOrgs.add(null);
			
			obsContingencyGiven.clear();
			obsContingencyReceived.clear();
			for(ContingencyTransaction t:Main.currGame.getContingencyTransactions()){
				if(t.getGiver() == n){
					obsContingencyGiven.add(t);
				}
				if(t.getReceiver() == n){
					obsContingencyReceived.add(t);
				}
			}
			obsContingencyGiven.add(null);
			
			
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
		obsRequiredImports = FXCollections.observableArrayList();
		obsAvailableExports = FXCollections.observableArrayList();
		obsAvailableNations = FXCollections.observableArrayList();
		obsAlliances = FXCollections.observableArrayList();
		obsPossibleAlliances = FXCollections.observableArrayList();
		obsPossibleOrgs = FXCollections.observableArrayList();
		obsPacts = FXCollections.observableArrayList();
		obsPossiblePacts = FXCollections.observableArrayList();
		obsNeutrality = FXCollections.observableArrayList();
		obsPossibleNeutralities = FXCollections.observableArrayList();
		obsNationOrgs = FXCollections.observableArrayList();	
		obsContingencyGiven = FXCollections.observableArrayList();
		obsContingencyReceived = FXCollections.observableArrayList();
		obsContingencyTypes = FXCollections.observableArrayList(ContingencyType.Aid,ContingencyType.Loan,ContingencyType.IMF);
		
		maxImports = new SimpleObjectProperty<BigDecimal>(BigDecimal.ZERO);		
		maxExports = new SimpleObjectProperty<BigDecimal>(BigDecimal.ZERO);		
		totalCurrentImports = new SimpleObjectProperty<BigDecimal>(BigDecimal.ZERO);
		totalCurrentExports = new SimpleObjectProperty<BigDecimal>(BigDecimal.ZERO);
		nationRequiredImportsLabel.textProperty().bindBidirectional(maxImports, NumberFormat.getCurrencyInstance());
		nationMaxExportLabel.textProperty().bindBidirectional(maxExports, NumberFormat.getCurrencyInstance());
		nationTotalImportsLabel.textProperty().bindBidirectional(totalCurrentImports, NumberFormat.getCurrencyInstance());
		nationTotalExportsLabel.textProperty().bindBidirectional(totalCurrentExports, NumberFormat.getCurrencyInstance());
		
		obsPacts.add(null);
		obsNeutrality.add(null);
		obsAlliances.add(null);
		obsNationOrgs.add(null);
		
		nationNeutralitiesList.setItems(obsNeutrality);
		nationPactsList.setItems(obsPacts);
		nationOrgsList.setItems(obsNationOrgs);
		nationAlliancesList.setItems(obsAlliances);	
		
		nationAlliancesList.setCellFactory(new Callback<ListView<Nation>,ListCell<Nation>>(){
			@Override
			public ListCell<Nation> call(ListView<Nation> arg0) {
				return new ComboBoxListCell<Nation>(obsPossibleAlliances){
					@Override
					public void updateItem(Nation n, boolean empty){
						super.updateItem(n, empty);
						if(n!=null)
							this.setEditable(false);
						else
							this.setEditable(true);
					}					
					@Override
					public void commitEdit(Nation n){							
						if(passwordDialog(n)){
							super.commitEdit(n);
							Alliance newAlliance = new Alliance(Main.currNation,n);
							Main.currNation.getRelationships().add(newAlliance);
							n.getRelationships().add(newAlliance);
							obsAlliances.add(null);
							obsPossibleAlliances.remove(n);
						
						}
						else{
							this.cancelEdit();
						}
					}
					
				};
			}			
		});
		
		nationNeutralitiesList.setCellFactory(new Callback<ListView<Nation>,ListCell<Nation>>(){
			@Override
			public ListCell<Nation> call(ListView<Nation> arg0) {
				// TODO Auto-generated method stub
				return new ComboBoxListCell<Nation>(obsPossibleNeutralities){
					@Override
					public void updateItem(Nation n, boolean empty){
						super.updateItem(n, empty);
						if(n!=null)
							this.setEditable(false);
						else
							this.setEditable(true);
					}					
					
					@Override
					public void commitEdit(Nation n){							
						if(passwordDialog(n)){
							super.commitEdit(n);
							Neutrality newNeutrality = new Neutrality(Main.currNation,n);
							Main.currNation.getRelationships().add(newNeutrality);
							n.getRelationships().add(newNeutrality);
							obsNeutrality.add(null);
							obsPossibleNeutralities.remove(n);
						}
						else{
							this.cancelEdit();
						}
					}
				};
			}			
		});
		
		nationPactsList.setCellFactory(new Callback<ListView<Nation>,ListCell<Nation>>(){
			@Override
			public ListCell<Nation> call(ListView<Nation> arg0) {
				// TODO Auto-generated method stub
				return new ComboBoxListCell<Nation>(obsPossiblePacts){
					@Override
					public void updateItem(Nation n, boolean empty){
						super.updateItem(n, empty);
						if(n!=null)
							this.setEditable(false);
						else
							this.setEditable(true);
					}					
					@Override
					public void commitEdit(Nation n){							
						if(passwordDialog(n)){
							super.commitEdit(n);
							NonAggressionPact newPact= new NonAggressionPact(Main.currNation,n);
							Main.currNation.getRelationships().add(newPact);
							n.getRelationships().add(newPact);
							obsPacts.add(null);
							obsPossiblePacts.remove(n);
						}
						else{
							this.cancelEdit();
						}
					}
				};
			}			
		});	
		
		nationOrgsList.setCellFactory(new Callback<ListView<InternationalOrganization>,ListCell<InternationalOrganization>>(){
			@Override
			public ListCell<InternationalOrganization> call(ListView<InternationalOrganization> arg0) {
				// TODO Auto-generated method stub
				return new ComboBoxListCell<InternationalOrganization>(obsPossibleOrgs){
					@Override
					public void updateItem(InternationalOrganization o, boolean empty){
						super.updateItem(o, empty);
						if(o!=null)
							this.setEditable(false);
						else
							this.setEditable(true);
					}					
					@Override
					public void commitEdit(InternationalOrganization o){							
						if(passwordDialog(o)){
							super.commitEdit(o);
							Main.currNation.getOrganizations().add(o);
							obsNationOrgs.add(null);
							obsPossibleOrgs.remove(o);
						}
						else{
							this.cancelEdit();
						}
					}
				};
			}			
		});	
		

		nationPlayerNameColumn
				.setCellValueFactory(new PropertyValueFactory<Player, String>(
						"name"));
		nationPlayerPositionColumn
				.setCellValueFactory(new PropertyValueFactory<Player, String>(
						"typeString"));
		nationPlayerTeacherColumn.setCellValueFactory(new PropertyValueFactory<Player, Teacher>("teacher"));
		nationPlayerPeriodColumn.setCellValueFactory(new PropertyValueFactory<Player,Integer>("period"));
		
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
				return new ComboBoxTableCell<Trade, Nation>(obsInUseNationsMinusCurrent){	
					@Override public void updateItem(Nation n, boolean empty){
						//send false because we really have no empty cells, blank cells should be changeable
						super.updateItem(n, empty);
					}
					
					
					@Override public void commitEdit(Nation n){
						Trade t = null;					
						if(this.getIndex() < this.getTableView().getItems().size()){
							t = this.getTableView().getItems().get(this.getIndex());
							if(t == null){
								if(passwordDialog(n)){
									super.commitEdit(n);
									TradeData newTradeData = new TradeData(n.getAvailableExports().get(0),Main.currNation,n,BigDecimal.ZERO);
									Trade newTrade = new Trade(newTradeData);
									obsNationImports.remove(null);
									obsNationImports.add(newTrade);
									obsNationImports.add(null);
									nationImportTable.getSelectionModel().select(this.getIndex());
								}else{
									this.cancelEdit();
								}
							}else{  //if the nation changes, we need to reset the whole trade
								if(passwordDialog(n)){
									super.commitEdit(n);
									t.setExporter(n);
									t.setCommodity(n.getAvailableExports().get(0));
								}else{
									this.cancelEdit();
								}					
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
				return new CurrencyFieldTableCell<Trade,BigDecimal>(){
					@Override 
					public void commitEdit(BigDecimal amt){
						Trade t = this.getTableView().getItems().get(this.getIndex());
						if(t!=null){
							//subtract prior trade amount from total
							//TODO							
							BigDecimal sumImport = totalCurrentImports.get().subtract(t.amountProperty().get());
							BigDecimal sumExport= t.getExporter().getTotalCurrentExports().subtract(t.amountProperty().get());
							if(sumImport.add(amt).compareTo(t.getImporter().getImportsAllocation())<=0 && sumExport.compareTo(t.getExporter().getMaxExports())<=0){
								totalCurrentImports.set(sumImport.add(amt));
								super.commitEdit(amt);
								
							}else{
								this.cancelEdit();
							}							
						}else{
							this.cancelEdit();
						}
						
					}
				};
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
		
		/*
		 * Contingency Given Tab
		 */
		
		nationContingencyGivenNationColumn.setCellValueFactory(new PropertyValueFactory<ContingencyTransaction,Nation>("receiver"));
		nationContingencyGivenNationColumn.setCellFactory(new Callback<TableColumn<ContingencyTransaction,Nation>,TableCell<ContingencyTransaction,Nation>>(){
			@Override
			public TableCell<ContingencyTransaction, Nation> call(
					TableColumn<ContingencyTransaction, Nation> param) {
				// TODO Auto-generated method stub
				return new ComboBoxTableCell<ContingencyTransaction, Nation>(obsInUseNationsMinusCurrent){	
					//TODO
					@Override
					public void commitEdit(Nation n){
						ContingencyTransaction t = this.getTableView().getItems().get(this.getIndex());
						if(t == null){
							if(passwordDialog(n)){
								super.commitEdit(n);
								ContingencyTransaction newCont = new ContingencyTransaction(ContingencyType.Aid,Main.currNation,n,BigDecimal.ZERO);
								obsContingencyGiven.remove(null);
								obsContingencyGiven.add(newCont);
								obsContingencyGiven.add(null);
								Main.currGame.getContingencyTransactions().add(newCont);
								nationContingencyGivenTable.getSelectionModel().select(this.getIndex());
							}else{
								this.cancelEdit();
							}
						}else{  //if the nation changes, we need to reset the whole trade
							if(t.getType()!=ContingencyType.IMF && passwordDialog(n)){
								super.commitEdit(n);
								t.setReceiver(n);
							}else{
								this.cancelEdit();
							}					
						}
					}
				};
			}			
		});
		nationContingencyGivenTypeColumn.setCellValueFactory(new PropertyValueFactory<ContingencyTransaction,ContingencyType>("type"));
		nationContingencyGivenTypeColumn.setCellFactory(new Callback<TableColumn<ContingencyTransaction,ContingencyType>,TableCell<ContingencyTransaction,ContingencyType>>(){
			@Override
			public TableCell<ContingencyTransaction, ContingencyType> call(
					TableColumn<ContingencyTransaction, ContingencyType> param) {
				// TODO Auto-generated method stub
				return new ComboBoxTableCell<ContingencyTransaction, ContingencyType>(obsContingencyTypes){
					@Override
					public void commitEdit(ContingencyType type){
						ContingencyTransaction trans = this.getTableView().getItems().get(this.getIndex());
						if(trans == null && type == ContingencyType.IMF){
							ContingencyTransaction newTrans = new ContingencyTransaction(ContingencyType.IMF,Main.currNation,null,BigDecimal.ZERO);
							obsContingencyGiven.remove(null);
							obsContingencyGiven.add(newTrans);
							obsContingencyGiven.add(null);
							Main.currGame.getContingencyTransactions().add(newTrans);
							nationContingencyGivenTable.getSelectionModel().select(this.getIndex());
						}else if(trans== null && type !=ContingencyType.IMF){
							this.cancelEdit();
						}else if(trans!=null){
							super.commitEdit(type);
							trans.setType(type);
						}
						
					}
				};
			}
		});
		nationContingencyGivenAmountColumn.setCellValueFactory(new PropertyValueFactory<ContingencyTransaction,BigDecimal>("amount"));
		nationContingencyGivenAmountColumn.setCellFactory(new Callback<TableColumn<ContingencyTransaction,BigDecimal>,TableCell<ContingencyTransaction,BigDecimal>>(){
			@Override
			public TableCell<ContingencyTransaction, BigDecimal> call(
					TableColumn<ContingencyTransaction, BigDecimal> param) {
				// TODO Auto-generated method stub
				return new CurrencyFieldTableCell<ContingencyTransaction,BigDecimal>(){
					@Override
					public void commitEdit(BigDecimal amt){
						super.commitEdit(amt);
						ContingencyTransaction trans = this.getTableView().getItems().get(this.getIndex());
						if(trans!=null){
							trans.setAmount(amt);
						}else{
							this.cancelEdit();
						}
					}
				};
			}
			
		});
		
		/*
		 * Contingency Received Tab
		 */
		
		nationContingencyReceivedNationColumn.setCellValueFactory(new PropertyValueFactory<ContingencyTransaction,Nation>("giver"));
		nationContingencyReceivedTypeColumn.setCellValueFactory(new PropertyValueFactory<ContingencyTransaction,ContingencyType>("type"));
		nationContingencyReceivedAmountColumn.setCellValueFactory(new PropertyValueFactory<ContingencyTransaction,BigDecimal>("amount"));
		nationContingencyReceivedAmountColumn.setCellFactory(new Callback<TableColumn<ContingencyTransaction,BigDecimal>,TableCell<ContingencyTransaction,BigDecimal>>(){
			@Override
			public TableCell<ContingencyTransaction, BigDecimal> call(
					TableColumn<ContingencyTransaction, BigDecimal> param) {
				// TODO Auto-generated method stub
				return new CurrencyFieldTableCell<ContingencyTransaction,BigDecimal>();
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
		
		nationContingencyGivenTable.setItems(obsContingencyGiven);
		nationContingencyReceivedTable.setItems(obsContingencyReceived);
		
		

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
	private ComboBox<Teacher> playerTeacher;

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
		Teacher pTeacher = playerTeacher.getValue();

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
		newPlayer.setTeacher(pTeacher);
		

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
			playerTeacher.setValue(p.getTeacher());
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
		playerTeacher.setValue(null);
	}
	
	private void initializePlayerTab(){
		obsPlayers = FXCollections.observableArrayList();
		obsAllNations = FXCollections.observableArrayList();
		
			
		playerPosition.setItems(FXCollections.observableArrayList(
				"Chief of State", "Foreign Minister"));

		playersList.setItems(obsPlayers);
		playerNation.setItems(obsAllNations);
		playerTeacher.setItems(obsTeachers);
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
    private TextField budgetExportsField;

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
    private Label budgetExportsLabel;
    
    @FXML
    private Label budgetImportsPercentLabel;
    
    @FXML
    private Label budgetRDLabel;
    
    @FXML
    private Label budgetRDPercentLabel;
    
    @FXML
    private Label budgetContingencyLabel;
    
    @FXML
    private Label budgetContingencyPercentLabel;
    
    @FXML
    private Label budgetCapitalGoodsLabel;
    
    @FXML
    private Label budgetCapitalGoodsPercentLabel;
    
    @FXML
	private TextField budgetPlusMinusCapitalGoodsField;

	@FXML
	private TextField budgetPlusMinusConForcesField;

	@FXML
	private TextField budgetPlusMinusContingencyField;
	
	@FXML
	private TextField budgetPlusMinusNucForcesField;
	
    @FXML
    private TextField budgetPlusMinusRDField;
    
    @FXML
    private TextField budgetPlusMinusImportsField;
    
    @FXML
    private TextField budgetPlusMinusExportsField;

    @FXML
    private TextField budgetPlusMinusBasicGoodsField;
    
    @FXML
    private Label budgetPlusMinusBasicGoodsLabel;
    
    @FXML
    private Label budgetPlusMinusConForcesLabel;
    
    @FXML
    private Label budgetPlusMinusNucForcesLabel;
    
    @FXML
    private Label budgetPlusMinusImportsLabel;
    
    @FXML
    private Label budgetPlusMinusExportsLabel;
    
    @FXML
    private Label budgetPlusMinusRDLabel;
    
    @FXML
    private Label budgetPlusMinusConFundLabel;
    
    @FXML
    private Label budgetPlusMinusCapitalGoodsLabel;
    
    @FXML
    private Label budgetPlusMinusBasicGoodsSubTotalLabel;
    
    @FXML
    private Label budgetPlusMinusConForcesSubTotalLabel;
    
    @FXML
    private Label budgetPlusMinusNucForcesSubTotalLabel;
    
    @FXML
    private Label budgetPlusMinusImportsSubTotalLabel;
    
    @FXML
    private Label budgetPlusMinusExportsSubTotalLabel;
    
    @FXML
    private Label budgetPlusMinusRDSubTotalLabel;
    
    @FXML
    private Label budgetPlusMinusConFundSubTotalLabel;
    
    @FXML
    private Label budgetPlusMinusCapitalGoodsSubTotalLabel;
    
    @FXML
    private TextField budgetAidReceivedField;
    
    @FXML
    private TextField budgetAidGivenField;
    
    @FXML
    private TextField budgetLoanReceivedField;
    
    @FXML
    private TextField budgetLoanGivenField;
    
    @FXML
    private TextField budgetIMFReceivedField;
    
    @FXML
    private TextField budgetIMFGivenField;
   
    @FXML
    private Label budgetAidGivenLabel;
    
    @FXML
    private Label budgetAidReceivedLabel;
    
    @FXML
    private Label budgetLoanGivenLabel;
    
    @FXML
    private Label budgetLoanReceivedLabel;
    
    @FXML
    private Label budgetIMFGivenLabel;
    
    @FXML
    private Label budgetIMFReceivedLabel;
    
    @FXML
    private Label budgetIncomeTaxSubTotalLabel;
    
    @FXML
    private Label budgetPoliticalTaxSubTotalLabel;
    
    @FXML
    private Label budgetTradeTaxSubTotalLabel;
    
    @FXML
    private Label budgetIMFTaxSubTotalLabel;
    
    @FXML
    private Label budgetIncomeTaxPercentLabel;
    
    @FXML
    private Label budgetPoliticalTaxPercentLabel;
    
    @FXML
    private Label budgetTradeTaxPercentLabel;
    
    @FXML
    private Label budgetIMFTaxPercentLabel;
    
    @FXML
    private Label budgetIncomeTaxNewSubTotalLabel;
    
    @FXML
    private Label budgetPoliticalTaxNewSubTotalLabel;
    
    @FXML
    private Label budgetTradeTaxNewSubTotalLabel;
    
    @FXML
    private Label budgetIMFTaxNewSubTotalLabel;
    
	@FXML
	public void budgetButtonClick(ActionEvent event) {
		gamePane.setVisible(false);
		teamPane.setVisible(false);
		budgetPane.setVisible(true);
		leaderboardPane.setVisible(false);
		nationPane.setVisible(false);
		setBudgetData();
	}
	
	@FXML
	public void budgetSaveButtonClick(ActionEvent event) {
		Main.currGame.saveGame();	
	}	
	
    @FXML
    public void budgetBasicGoodsFieldAction(ActionEvent event) {
    	BigDecimal basicGoods = new BigDecimal(budgetBasicGoodsField.getText());
    	if(basicGoods!=null){
    		if(Main.currNation.setBasicGoodsAllocation(basicGoods)){
    			budgetBasicGoodsLabel.setText(NumberFormat.getCurrencyInstance().format(basicGoods));
    			budgetBasicGoodsField.setVisible(false);
    			budgetBasicGoodsField.setText(null);
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
				budgetConForcesField.setText(null);
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
    			budgetNucForcesField.setText(null);
    			budgetNucForcesLabel.setVisible(true);
    			budgetNucForcesPercentLabel.setText(NumberFormat.getPercentInstance().format(nucForces.divide(Main.currNation.getGnp(),2,RoundingMode.HALF_UP)));
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
    			budgetImportsField.setText(null);
    			budgetImportsLabel.setVisible(true);
    			budgetImportsPercentLabel.setText(NumberFormat.getPercentInstance().format(imports.divide(Main.currNation.getGnp(),2,RoundingMode.HALF_UP)));
    			budgetImportsPercentLabel.setVisible(true);
    			updateAllocationChart();
    		}
    	}
    }
    
    @FXML
    public void budgetExportsFieldAction(ActionEvent event) {
    	BigDecimal b = new BigDecimal(budgetExportsField.getText());
    	if(b!=null)
    		if(Main.currNation.setMaxExports(b)){
    			budgetExportsField.setVisible(false);
    			budgetExportsField.setText(null);
    			budgetExportsLabel.setText(NumberFormat.getCurrencyInstance().format(Main.currNation.getMaxExports()));
    			budgetExportsLabel.setVisible(true);
    	}	
    	
    }
    
    @FXML
    public void budgetRDFieldAction(ActionEvent event) {
    	BigDecimal rd = new BigDecimal(budgetRDField.getText());
    	if(rd!=null){
    		if(Main.currNation.setRDAllocation(rd)){
    			budgetRDLabel.setText(NumberFormat.getCurrencyInstance().format(rd));
    			budgetRDField.setVisible(false);
    			budgetRDField.setText(null);
    			budgetRDLabel.setVisible(true);
    			budgetRDPercentLabel.setText(NumberFormat.getPercentInstance().format(rd.divide(Main.currNation.getGnp(),2,RoundingMode.HALF_UP)));
    			budgetRDPercentLabel.setVisible(true);
    			updateAllocationChart();
    		}
    	}    
    }
    
    @FXML
    public void budgetContingencyFieldAction(ActionEvent event) {
    	BigDecimal cont = new BigDecimal(budgetContingencyField.getText());
    	if(cont!=null){
    		if(Main.currNation.setContingencyAllocation(cont)){
    			budgetContingencyLabel.setText(NumberFormat.getCurrencyInstance().format(cont));
    			budgetContingencyField.setVisible(false);
    			budgetContingencyField.setText(null);
    			budgetContingencyLabel.setVisible(true);
    			budgetContingencyPercentLabel.setText(NumberFormat.getPercentInstance().format(cont.divide(Main.currNation.getGnp(), 2, RoundingMode.HALF_UP)));
    			budgetContingencyPercentLabel.setVisible(true);
    			updateAllocationChart();
    		}    		
    	}    
    }
    
    @FXML
    public void budgetCapitalGoodsFieldAction(ActionEvent event) {
    	BigDecimal capt = new BigDecimal(budgetCapitalGoodsField.getText());
    	if(capt!=null){
    		if(Main.currNation.setCapitalGoodsAllocation(capt)){
    			budgetCapitalGoodsLabel.setText(NumberFormat.getCurrencyInstance().format(capt));
    			budgetCapitalGoodsField.setVisible(false);
    			budgetCapitalGoodsField.setText(null);
    			budgetCapitalGoodsLabel.setVisible(true);
    			budgetCapitalGoodsPercentLabel.setText(NumberFormat.getPercentInstance().format(capt.divide(Main.currNation.getGnp(), 2, RoundingMode.HALF_UP)));
    			budgetCapitalGoodsPercentLabel.setVisible(true);
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
   				if(!obsPieChartData.contains(basicGoodsPiePiece)&& Main.currNation.getBasicGoodsAllocation().compareTo(BigDecimal.ZERO)!=0)
   					obsPieChartData.add(basicGoodsPiePiece);
    		}
    		
    		if(Main.currNation.getConventionalForcesSet()){
    			totalAllocation = totalAllocation.add(Main.currNation.getConventionalForcesAllocation());
    			conForcesPiePiece.setPieValue(Main.currNation.getConventionalForcesAllocation().doubleValue());
    			if(!obsPieChartData.contains(conForcesPiePiece)&& Main.currNation.getConventionalForcesAllocation().compareTo(BigDecimal.ZERO)!=0)
    				obsPieChartData.add(conForcesPiePiece);
    		}
    		
    		if(Main.currNation.getNuclearForcesSet()){
    			totalAllocation = totalAllocation.add(Main.currNation.getNuclearForcesAllocation());
    			nuclearForcesPiePiece.setPieValue(Main.currNation.getNuclearForcesAllocation().doubleValue());
    			if(!obsPieChartData.contains(nuclearForcesPiePiece) && Main.currNation.getNuclearForcesAllocation().compareTo(BigDecimal.ZERO)!=0)
    				obsPieChartData.add(nuclearForcesPiePiece);
    		}
    		
    		if(Main.currNation.getImportsSet()){
    			totalAllocation = totalAllocation.add(Main.currNation.getImportsAllocation());
    			importsPiePiece.setPieValue(Main.currNation.getImportsAllocation().doubleValue());
    			if(!obsPieChartData.contains(importsPiePiece)&& Main.currNation.getImportsAllocation().compareTo(BigDecimal.ZERO)!=0)
    				obsPieChartData.add(importsPiePiece);
    		}
    		
    		if(Main.currNation.getRDSet()){
    			totalAllocation = totalAllocation.add(Main.currNation.getRDAllocation());
    			researchPiePiece.setPieValue(Main.currNation.getRDAllocation().doubleValue());
    			if(!obsPieChartData.contains(researchPiePiece)&& Main.currNation.getRDAllocation().compareTo(BigDecimal.ZERO)!=0)
    				obsPieChartData.add(researchPiePiece);
    		}

    		
    		if(Main.currNation.getContingencySet()){
    			totalAllocation = totalAllocation.add(Main.currNation.getContingencyAllocation());
    			contingencyPiePiece.setPieValue(Main.currNation.getContingencyAllocation().doubleValue());
    			if(!obsPieChartData.contains(contingencyPiePiece)&& Main.currNation.getContingencyAllocation().compareTo(BigDecimal.ZERO)!=0)
    				obsPieChartData.add(contingencyPiePiece);
    		}
    		
    		if(Main.currNation.getCapitalGoodsSet()){
    			totalAllocation = totalAllocation.add(Main.currNation.getCapitalGoodsAllocation());
    			capitalGoodsPiePiece.setPieValue(Main.currNation.getCapitalGoodsAllocation().doubleValue());
    			if(!obsPieChartData.contains(capitalGoodsPiePiece)&& Main.currNation.getCapitalGoodsAllocation().compareTo(BigDecimal.ZERO)!=0)
    				obsPieChartData.add(capitalGoodsPiePiece);
    		}
    		
    		if(totalAllocation.compareTo(Main.currNation.getGnp())<0){
    			unallocatedPiePiece.setPieValue(Main.currNation.getGnp().subtract(totalAllocation).doubleValue());
    		}	
    		else if(totalAllocation.compareTo(Main.currNation.getGnp())==0){
    			unallocatedPiePiece.setPieValue(0);
    			obsPieChartData.remove(unallocatedPiePiece);
    		}
    		
    		for(PieChart.Data p:obsPieChartData){
    			final Node n = p.getNode();
    			if(n!=null){
  
    				final Glow glow = new Glow(.8);   
    				n.setOnMouseEntered(new EventHandler<MouseEvent>() {
    					@Override
    					public void handle(MouseEvent e) {
    						n.setEffect(glow);
    						//n.setTranslateX(10);
    						//Tooltip.install(path, new Tooltip(series.getName()));
    					}
    				});
    				n.setOnMouseExited(new EventHandler<MouseEvent>() {
    					@Override
    					public void handle(MouseEvent e) {
    						n.setEffect(null);
    						//n.setTranslateX(0);
    					}
    				});
    			}
    		}
    	}
    }
    
    private void setBudgetData(){
    	
    	if(Main.currNation!=null){
    		if(Main.currNation.getBasicGoodsSet()){
    			budgetBasicGoodsField.setVisible(false);
    			budgetBasicGoodsLabel.setText(NumberFormat.getCurrencyInstance().format(Main.currNation.getBasicGoodsAllocation()));
				budgetBasicGoodsField.setVisible(false);
				budgetBasicGoodsLabel.setVisible(true);
				budgetBasicGoodsPercentLabel.setText(NumberFormat.getPercentInstance().format(Main.currNation.getBasicGoodsAllocation().divide(Main.currNation.getGnp(), 2, RoundingMode.HALF_UP)));
				budgetBasicGoodsPercentLabel.setVisible(true);
    		}else{
    			budgetBasicGoodsField.setVisible(true);
    			budgetBasicGoodsLabel.setVisible(false);
    			budgetBasicGoodsPercentLabel.setVisible(false);    		
    		}
    		
    		if(Main.currNation.getConventionalForcesSet()){
    			budgetConForcesLabel.setText(NumberFormat.getCurrencyInstance().format(Main.currNation.getConventionalForcesAllocation()));
				budgetConForcesField.setVisible(false);
				budgetConForcesLabel.setVisible(true);
				budgetConForcesPercentLabel.setText(NumberFormat.getPercentInstance().format(Main.currNation.getConventionalForcesAllocation().divide(Main.currNation.getGnp(), 2, RoundingMode.HALF_UP)));
				budgetConForcesPercentLabel.setVisible(true);
    			
    		}else{
    			budgetConForcesField.setVisible(true);
    			budgetConForcesLabel.setVisible(false);
    			budgetConForcesPercentLabel.setVisible(false);
    		}
    		
    		if(Main.currNation.getNuclearForcesSet()){
    			budgetNucForcesLabel.setText(NumberFormat.getCurrencyInstance().format(Main.currNation.getNuclearForcesAllocation()));
    			budgetNucForcesField.setVisible(false);
    			budgetNucForcesLabel.setVisible(true);
    			budgetNucForcesPercentLabel.setText(NumberFormat.getPercentInstance().format(Main.currNation.getNuclearForcesAllocation().divide(Main.currNation.getGnp(),2,RoundingMode.HALF_UP)));
    			budgetNucForcesPercentLabel.setVisible(true);    			
    		}else{
    			budgetNucForcesField.setVisible(true);
    			budgetNucForcesLabel.setVisible(false);
    			budgetNucForcesPercentLabel.setVisible(false);    			
    		}
    		
    		if(Main.currNation.getImportsSet()){
    			budgetImportsLabel.setText(NumberFormat.getCurrencyInstance().format(Main.currNation.getImportsAllocation()));
    			budgetImportsField.setVisible(false);
    			budgetImportsLabel.setVisible(true);
    			budgetImportsPercentLabel.setText(NumberFormat.getPercentInstance().format(Main.currNation.getImportsAllocation().divide(Main.currNation.getGnp(),2,RoundingMode.HALF_UP)));
    			budgetImportsPercentLabel.setVisible(true);    			
    		}else{
    			budgetImportsField.setVisible(true);
    			budgetImportsLabel.setVisible(false);
    			budgetImportsPercentLabel.setVisible(false);        			
    		}
    		
    		if(Main.currNation.getMaxExportSet()){
    			budgetExportsLabel.setText(NumberFormat.getCurrencyInstance().format(Main.currNation.getMaxExports()));
    			budgetExportsField.setVisible(false);
    			budgetExportsLabel.setVisible(true);
    		}else{
    			budgetExportsField.setVisible(true);
    			budgetExportsLabel.setVisible(false);
    		}    		
    		
    		if(Main.currNation.getRDSet()){
    			budgetRDLabel.setText(NumberFormat.getCurrencyInstance().format(Main.currNation.getRDAllocation()));
    			budgetRDField.setVisible(false);
    			budgetRDLabel.setVisible(true);
    			budgetRDPercentLabel.setText(NumberFormat.getPercentInstance().format(Main.currNation.getRDAllocation().divide(Main.currNation.getGnp(),2,RoundingMode.HALF_UP)));
    			budgetRDPercentLabel.setVisible(true);    			
    		}else{
    			budgetRDField.setVisible(true);
    			budgetRDLabel.setVisible(false);
    			budgetRDPercentLabel.setVisible(false);    
    		}
    		
    		if(Main.currNation.getContingencySet()){
    			budgetContingencyLabel.setText(NumberFormat.getCurrencyInstance().format(Main.currNation.getContingencyAllocation()));
    			budgetContingencyField.setVisible(false);
    			budgetContingencyLabel.setVisible(true);
    			budgetContingencyPercentLabel.setText(NumberFormat.getPercentInstance().format(Main.currNation.getContingencyAllocation().divide(Main.currNation.getGnp(), 2, RoundingMode.HALF_UP)));
    			budgetContingencyPercentLabel.setVisible(true);    			
    		}else{
    			budgetContingencyField.setVisible(true);
    			budgetContingencyLabel.setVisible(false);
    			budgetContingencyPercentLabel.setVisible(false);    			
    		}
    		
    		if(Main.currNation.getCapitalGoodsSet()){
    			budgetCapitalGoodsLabel.setText(NumberFormat.getCurrencyInstance().format(Main.currNation.getCapitalGoodsAllocation()));
    			budgetCapitalGoodsField.setVisible(false);
    			budgetCapitalGoodsLabel.setVisible(true);
    			budgetCapitalGoodsPercentLabel.setText(NumberFormat.getPercentInstance().format(Main.currNation.getCapitalGoodsAllocation().divide(Main.currNation.getGnp(), 2, RoundingMode.HALF_UP)));
    			budgetCapitalGoodsPercentLabel.setVisible(true);    			
    		}else{
    			budgetCapitalGoodsField.setVisible(true);
    			budgetCapitalGoodsLabel.setVisible(false);
    			budgetCapitalGoodsPercentLabel.setVisible(false);  
    		}    		    		
    	}    	
    	updateAllocationChart();
    }
    
    @FXML
    public void budgetPlusMinusBasicGoodsFieldAction(ActionEvent event) {
    	BigDecimal bas = new BigDecimal(budgetPlusMinusBasicGoodsField.getText());
    	if(bas!=null){
    		if(bas.compareTo(Main.currNation.getBasicGoodsDepreciation())==0 || bas.negate().compareTo(Main.currNation.getBasicGoodsDepreciation())==0){
    			budgetPlusMinusBasicGoodsLabel.setText(NumberFormat.getCurrencyInstance().format(Main.currNation.getBasicGoodsDepreciation().negate()));
    			budgetPlusMinusBasicGoodsField.setVisible(false);
    			budgetPlusMinusBasicGoodsField.setText(null);
    			budgetPlusMinusBasicGoodsLabel.setVisible(true);
    			budgetPlusMinusBasicGoodsSubTotalLabel.setText(NumberFormat.getCurrencyInstance().format(Main.currNation.getBasicGoodsAllocation().subtract(Main.currNation.getBasicGoodsDepreciation())));
    			budgetPlusMinusBasicGoodsSubTotalLabel.setVisible(true);
    		}
    	}      	
    }
    
    @FXML
    public void budgetPlusMinusConForcesFieldAction(ActionEvent event) {
    	BigDecimal con = new BigDecimal(budgetPlusMinusConForcesField.getText());
    	if(con!=null){
    		if(con.compareTo(Main.currNation.getConventionalForcesDepreciation())==0 || con.negate().compareTo(Main.currNation.getConventionalForcesDepreciation())==0){
    			budgetPlusMinusConForcesLabel.setText(NumberFormat.getCurrencyInstance().format(Main.currNation.getConventionalForcesDepreciation().negate()));
    			budgetPlusMinusConForcesField.setVisible(false);
    			budgetPlusMinusConForcesField.setText(null);
    			budgetPlusMinusConForcesLabel.setVisible(true);
    			budgetPlusMinusConForcesSubTotalLabel.setText(NumberFormat.getCurrencyInstance().format(Main.currNation.getConventionalForcesAllocation().subtract(Main.currNation.getConventionalForcesDepreciation())));
    			budgetPlusMinusConForcesSubTotalLabel.setVisible(true);
    		}
    	}      	
    }
    
    @FXML
    public void budgetPlusMinusNucForcesFieldAction(ActionEvent event) {
    	BigDecimal nuc = new BigDecimal(budgetPlusMinusNucForcesField.getText());
    	if(nuc!=null){
    		if(nuc.compareTo(Main.currNation.getNuclearForcesDepreciation())==0 || nuc.negate().compareTo(Main.currNation.getNuclearForcesDepreciation())==0){
    			budgetPlusMinusNucForcesLabel.setText(NumberFormat.getCurrencyInstance().format(Main.currNation.getNuclearForcesDepreciation().negate()));
    			budgetPlusMinusNucForcesField.setVisible(false);
    			budgetPlusMinusNucForcesField.setText(null);
    			budgetPlusMinusNucForcesLabel.setVisible(true);
    			budgetPlusMinusNucForcesSubTotalLabel.setText(NumberFormat.getCurrencyInstance().format(Main.currNation.getNuclearForcesAllocation().subtract(Main.currNation.getNuclearForcesDepreciation())));
    			budgetPlusMinusNucForcesSubTotalLabel.setVisible(true);
    		}
    	}      	
    }
    
    @FXML
    public void budgetPlusMinusImportsFieldAction(ActionEvent event) {
    	BigDecimal imp = new BigDecimal(budgetPlusMinusImportsField.getText());
    	if(imp!=null){
    		BigDecimal currentImports = Main.currNation.getTotalCurrentImports();
    		if(imp.compareTo(currentImports)==0 || imp.negate().compareTo(currentImports)==0){
    			budgetPlusMinusImportsLabel.setText(NumberFormat.getCurrencyInstance().format(currentImports.negate()));
    			budgetPlusMinusImportsField.setVisible(false);
    			budgetPlusMinusImportsField.setText(null);
    			budgetPlusMinusImportsLabel.setVisible(true);
    			budgetPlusMinusImportsSubTotalLabel.setText(NumberFormat.getCurrencyInstance().format(Main.currNation.getImportsAllocation().subtract(currentImports)));
    			budgetPlusMinusImportsSubTotalLabel.setVisible(true);
    		}
    	}      	
    }
    
    @FXML
    public void budgetPlusMinusExportsFieldAction(ActionEvent event) {
    	BigDecimal exp = new BigDecimal(budgetPlusMinusExportsField.getText());
    	if(exp!=null){
    		BigDecimal currentExports = Main.currNation.getTotalCurrentExports();
    		if(exp.compareTo(currentExports)==0){
    			budgetPlusMinusExportsLabel.setText(NumberFormat.getCurrencyInstance().format(currentExports));
    			budgetPlusMinusExportsField.setVisible(false);
    			budgetPlusMinusExportsField.setText(null);
    			budgetPlusMinusExportsLabel.setVisible(true);
    			budgetPlusMinusExportsSubTotalLabel.setText(NumberFormat.getCurrencyInstance().format(currentExports));
    			budgetPlusMinusExportsSubTotalLabel.setVisible(true);
    		}
    	}      
    }
    
    @FXML
    public void budgetPlusMinusRDFieldAction(ActionEvent event) {
    	BigDecimal rd = new BigDecimal(budgetPlusMinusRDField.getText());
    	if(rd!=null){
    		if(rd.compareTo(BigDecimal.ZERO)>=0 && rd.compareTo(Main.currNation.getRDAllocation().multiply(new BigDecimal(".50")))<=0){
    			budgetPlusMinusRDLabel.setText(NumberFormat.getCurrencyInstance().format(rd));
    			budgetPlusMinusRDField.setVisible(false);
    			budgetPlusMinusRDField.setText(null);
    			budgetPlusMinusRDLabel.setVisible(true);
    			budgetPlusMinusRDSubTotalLabel.setText(NumberFormat.getCurrencyInstance().format(Main.currNation.getRDAllocation().add(rd)));
    			budgetPlusMinusRDSubTotalLabel.setVisible(true);
    		}
    	}      	
    }
    
    @FXML
    public void budgetPlusMinusCapitalGoodsFieldAction(ActionEvent event) {
    	BigDecimal cg = new BigDecimal(budgetPlusMinusCapitalGoodsField.getText());
    	if(cg!=null){
    		if(cg.compareTo(Main.currNation.getCapitalGoodsAppreciation())==0){
    			budgetPlusMinusCapitalGoodsLabel.setText(NumberFormat.getCurrencyInstance().format(Main.currNation.getCapitalGoodsAppreciation()));
    			budgetPlusMinusCapitalGoodsField.setVisible(false);
    			budgetPlusMinusCapitalGoodsField.setText(null);
    			budgetPlusMinusCapitalGoodsLabel.setVisible(true);
    			budgetPlusMinusCapitalGoodsSubTotalLabel.setText(NumberFormat.getCurrencyInstance().format(Main.currNation.getCapitalGoodsAllocation().add(Main.currNation.getCapitalGoodsAppreciation())));
    			budgetPlusMinusCapitalGoodsSubTotalLabel.setVisible(true);
    		}
    	}      	
    }
    
   /* private void enableContingencyFund(){
    	if(Main.currNation.contingencyAllocated()){
    		budgetPlusMinusCapitalGoodsField.setDisable(false);
    		budgetPlusMinusConFundLabel.setText(NumberFormat.getCurrencyInstance().format(Main.currNation.getContingencyTotal()));
    	}
    }
    
    @FXML
    public void budgetAidGivenFieldAction(ActionEvent event) {
    	BigDecimal b = new BigDecimal(budgetAidGivenField.getText());
    	if(b!=null){
    		if(Main.currNation.setAidGiven(b)){
    			budgetAidGivenLabel.setText(NumberFormat.getCurrencyInstance().format(Main.currNation.getAidGiven()));
    			budgetAidGivenField.setVisible(false);
    			budgetAidGivenField.setText(null);
    			budgetAidGivenLabel.setVisible(true);
    		}
    	}	
    	enableContingencyFund();
    }
    
    @FXML
    public void budgetAidReceivedFieldAction(ActionEvent event) {
    	BigDecimal b = new BigDecimal(budgetAidReceivedField.getText());
    	if(b!=null){
    		if(Main.currNation.setAidReceived(b)){
    			budgetAidReceivedLabel.setText(NumberFormat.getCurrencyInstance().format(Main.currNation.getAidReceived()));
    			budgetAidReceivedField.setVisible(false);
    			budgetAidReceivedField.setText(null);
    			budgetAidReceivedLabel.setVisible(true);
    		}
    	}
    	enableContingencyFund();
    }
    
    @FXML
    public void budgetLoanGivenFieldAction(ActionEvent event) {
    	BigDecimal b = new BigDecimal(budgetLoanGivenField.getText());
    	if(b!=null){
    		if(Main.currNation.setLoanGiven(b)){
    			budgetLoanGivenLabel.setText(NumberFormat.getCurrencyInstance().format(Main.currNation.getLoanGiven()));
    			budgetLoanGivenField.setVisible(false);
    			budgetLoanGivenField.setText(null);
    			budgetLoanGivenLabel.setVisible(true);
    		}
    	}   
    	enableContingencyFund();
    }
    
    @FXML
    public void budgetLoanReceivedFieldAction(ActionEvent event) {
    	BigDecimal b = new BigDecimal(budgetLoanReceivedField.getText());
    	if(b!=null){
    		if(Main.currNation.setLoanReceived(b)){
    			budgetLoanReceivedLabel.setText(NumberFormat.getCurrencyInstance().format(Main.currNation.getLoanReceived()));
    			budgetLoanReceivedField.setVisible(false);
    			budgetLoanReceivedField.setText(null);
    			budgetLoanReceivedLabel.setVisible(true);
    		}
    	}   
    	enableContingencyFund();        
    }
    
    @FXML
    public void budgetIMFGivenFieldAction(ActionEvent event) {
    	BigDecimal b = new BigDecimal(budgetIMFGivenField.getText());
    	if(b!=null){
    		if(Main.currNation.setIMFGiven(b)){
    			budgetIMFGivenLabel.setText(NumberFormat.getCurrencyInstance().format(Main.currNation.getIMFGiven()));
    			budgetIMFGivenField.setVisible(false);
    			budgetIMFGivenField.setText(null);
    			budgetIMFGivenLabel.setVisible(true);
    		}
    	}  
    	enableContingencyFund();
    }
    
    @FXML
    public void budgetIMFReceivedFieldAction(ActionEvent event) {
    	BigDecimal b = new BigDecimal(budgetIMFReceivedField.getText());
    	if(b!=null){
    		if(Main.currNation.setIMFReceived(b)){
    			budgetIMFReceivedLabel.setText(NumberFormat.getCurrencyInstance().format(Main.currNation.getIMFReceived()));
    			budgetIMFReceivedField.setVisible(false);
    			budgetIMFReceivedField.setText(null);
    			budgetIMFReceivedLabel.setVisible(true);
    		}
    	}   
    	enableContingencyFund();
    }*/
    
    private void showNewGDPCalculations(){
    	budgetPlusMinusBasicGoodsLabel.setText(NumberFormat.getCurrencyInstance().format(Main.currNation.getBasicGoodsDepreciation().negate()));
    	budgetPlusMinusBasicGoodsSubTotalLabel.setText(NumberFormat.getCurrencyInstance().format(Main.currNation.getBasicGoodsSubTotal()));
    	budgetPlusMinusConForcesLabel.setText(NumberFormat.getCurrencyInstance().format(Main.currNation.getConventionalForcesDepreciation().negate()));
		budgetPlusMinusConForcesSubTotalLabel.setText(NumberFormat.getCurrencyInstance().format(Main.currNation.getConventionalForcesSubTotal()));
		budgetPlusMinusNucForcesLabel.setText(NumberFormat.getCurrencyInstance().format(Main.currNation.getNuclearForcesDepreciation().negate()));
		budgetPlusMinusNucForcesSubTotalLabel.setText(NumberFormat.getCurrencyInstance().format(Main.currNation.getNuclearForcesSubTotal()));
		budgetPlusMinusImportsLabel.setText(NumberFormat.getCurrencyInstance().format(Main.currNation.getTotalCurrentImports().negate()));
		budgetPlusMinusImportsSubTotalLabel.setText(NumberFormat.getCurrencyInstance().format(Main.currNation.getImportsSubTotal()));
		budgetPlusMinusExportsLabel.setText(NumberFormat.getCurrencyInstance().format(Main.currNation.getTotalCurrentExports()));
		budgetPlusMinusExportsSubTotalLabel.setText(NumberFormat.getCurrencyInstance().format(Main.currNation.getExportsSubTotal()));
		budgetPlusMinusRDLabel.setText(NumberFormat.getCurrencyInstance().format(Main.currNation.getRDAppreciation()));
		budgetPlusMinusRDSubTotalLabel.setText(NumberFormat.getCurrencyInstance().format(Main.currNation.getRDSubTotal()));
		budgetPlusMinusConFundLabel.setText(NumberFormat.getCurrencyInstance().format(Main.currNation.getContingencyTotal()));		
		budgetPlusMinusCapitalGoodsLabel.setText(NumberFormat.getCurrencyInstance().format(Main.currNation.getCapitalGoodsAppreciation()));
		budgetPlusMinusCapitalGoodsSubTotalLabel.setText(NumberFormat.getCurrencyInstance().format(Main.currNation.getCapitalGoodsSubTotal()));
		
		budgetAidGivenLabel.setText(NumberFormat.getCurrencyInstance().format(Main.currNation.getAidGiven()));
		budgetAidReceivedLabel.setText(NumberFormat.getCurrencyInstance().format(Main.currNation.getAidReceived()));
		budgetLoanGivenLabel.setText(NumberFormat.getCurrencyInstance().format(Main.currNation.getLoanGiven()));
		budgetLoanReceivedLabel.setText(NumberFormat.getCurrencyInstance().format(Main.currNation.getLoanReceived()));
		budgetIMFGivenLabel.setText(NumberFormat.getCurrencyInstance().format(Main.currNation.getIMFGiven()));
		budgetIMFReceivedLabel.setText(NumberFormat.getCurrencyInstance().format(Main.currNation.getIMFReceived()));
		
		BigDecimal subTotal = Main.currNation.getNewGNPSubTotal();
		budgetIncomeTaxSubTotalLabel.setText(NumberFormat.getCurrencyInstance().format(subTotal));
		budgetPoliticalTaxSubTotalLabel.setText(NumberFormat.getCurrencyInstance().format(subTotal));
		budgetTradeTaxSubTotalLabel.setText(NumberFormat.getCurrencyInstance().format(subTotal));
		budgetIMFTaxSubTotalLabel.setText(NumberFormat.getCurrencyInstance().format(subTotal));
		
		budgetIncomeTaxSubTotalLabel.setText(NumberFormat.getCurrencyInstance().format(subTotal));
		budgetPoliticalTaxSubTotalLabel.setText(NumberFormat.getCurrencyInstance().format(subTotal));
		budgetTradeTaxSubTotalLabel.setText(NumberFormat.getCurrencyInstance().format(subTotal));
		budgetIMFTaxSubTotalLabel.setText(NumberFormat.getCurrencyInstance().format(subTotal));
		
		budgetIncomeTaxPercentLabel.setText(NumberFormat.getPercentInstance().format(Main.currNation.getIncomeTaxPercent(subTotal)));
		budgetPoliticalTaxPercentLabel.setText(NumberFormat.getPercentInstance().format(Main.currNation));

		
		
    }
    
    
    
	
	public void initializeBudgetTab(){
	
		obsPieChartData = FXCollections.observableArrayList();
		obsPieChartData.add(unallocatedPiePiece);

		budgetPie.setData(obsPieChartData);
		budgetPie.setAnimated(true);
	}

	/*****************************************************************************************************************************
	 * Leaderboard Tab
	 ****************************************************************************************************************************/
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
		obsRoundData = FXCollections.observableArrayList();	
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
	
	final ObservableList data = 
	        FXCollections.observableArrayList();

	public void initialize() {
		System.out.println("Initializing UI...");		
		initializeGameTab();
		initializeNationTab();
		initializeLeaderBoardTab();
		initializePlayerTab();
		initializeBudgetTab();
		gameButtonClick(null);
		

		testListView.setEditable(true);
		final ObservableList names = 
			        FXCollections.observableArrayList();
		
		names.addAll(
	             "Adam", "Alex", "Alfred", "Albert",
	             "Brenda", "Connie", "Derek", "Donny", 
	             "Lynne", "Myrtle", "Rose", "Rudolph", 
	             "Tony", "Trudy", "Williams", "Zach"
	        );
		
		
		 testListView.setItems(data);
		testListView.setCellFactory(ComboBoxListCell.forListView(obsAvailableNations));
	}
	
	private boolean passwordDialog(Nation n){
		Main.passwordNation = n;
		Main.passwordStage.setX(Main.pStage.getX() + Main.pStage.getWidth()/2 - Main.passwordController.passwordAnchorPane.getPrefWidth()/2);
		Main.passwordStage.setY(Main.pStage.getY() + Main.pStage.getHeight()/2 - Main.passwordController.passwordAnchorPane.getPrefHeight()/2);
		Main.passwordStage.showAndWait();
		return Main.passwordOk;		
	}
	
	private boolean passwordDialog(InternationalOrganization o){
		Main.passwordOrg = o;
		Main.passwordStage.setX(Main.pStage.getX() + Main.pStage.getWidth()/2 - Main.passwordController.passwordAnchorPane.getPrefWidth()/2);
		Main.passwordStage.setY(Main.pStage.getY() + Main.pStage.getHeight()/2 - Main.passwordController.passwordAnchorPane.getPrefHeight()/2);
		Main.passwordStage.showAndWait();
		return Main.passwordOk;		
	}
	

}
