package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

	public static Game currGame;
	public static ArrayList<Game> allGames;

	@Override
	public void start(Stage primaryStage) {
		try {

			//primaryStage.initStyle(StageStyle.TRANSPARENT);
			
			
			System.out.println("Working Directory = "
					+ System.getProperty("user.dir"));

			FXMLLoader fxmlLoader = new FXMLLoader();
			URL location = Main.class.getResource("WorldSimFXML.fxml");
			fxmlLoader.setLocation(location);

			AnchorPane page = (AnchorPane) fxmlLoader.load(Main.class
					.getResource("WorldSimFXML.fxml").openStream());
			UIController UICont = fxmlLoader.getController();

			
			
			Scene scene = new Scene(page);
		//	Color c = Color.rgb((int)Color.BLACK.getRed(),(int)Color.BLACK.getGreen(), (int)Color.BLACK.getBlue(), .90);

		//	scene.setFill(c);
			primaryStage.setTitle("World Simulation");
			primaryStage.setScene(scene);
			primaryStage.show();

			populateNationsList(UICont);

			// ArrayList<Nation> nations = currGame.getNations();

			// ArrayList<String> UINations = UICont.nationsArray();
			// UICont.addNationToUI("#3 nation");
			// for (Nation nation : nations) {
			// UICont.addNationToUI(nation.name());
			// }
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public static void main(String[] args) {

		System.out.println("Working Directory = "
				+ System.getProperty("user.dir"));
		
		allGames = new ArrayList<Game>();

		loadGames();
		
		createNewGame();
		// initialize();
		launch(args);

	}
	
	public static void loadGames(){
		File[] gameFiles = null;
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		Game g;

		gameFiles = findFilesStartAndEndWith("Game",".wsm");
		
		for(File f: gameFiles){
			try{
				fis = new FileInputStream(f);
				ois = new ObjectInputStream(fis);
				g = (Game) ois.readObject();
				allGames.add(g);
				ois.close();
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public static File[] findFilesStartAndEndWith(final String beg, final String end){
		File workingDir = new File(System.getProperty("user.dir"));
		FilenameFilter filter = new FilenameFilter(){
			public boolean accept(File dir,String filename)
			{
				return (filename.startsWith(beg) && filename.endsWith(end));
			}
		};
		return workingDir.listFiles(filter);
	}
	

	public static void populateNationsList(UIController UICont) {
		UICont.setNations(currGame.getNations());
	}

	public static void initialize() {

		String alaf2 = System.getProperty("apple.laf.useScreenMenuBar");
		System.setProperty("apple.laf.useScreenMenuBar", "true");
		String alaf = System.getProperty("apple.laf.useScreenMenuBar");

	}

	public static void createNewGame() {
		currGame = new Game();
		importNations(currGame);

	}

	public static void importNations(Game g) {

		try {
			String line;
			String[] values;
			int id = 0;

			BufferedReader br = new BufferedReader(new FileReader(
					"NationsList.txt"));

			while ((line = br.readLine()) != null) {
				values = line.split(",");
				Nation newNation = new Nation(id);
				newNation.setName(values[0]);
				newNation.setGNP(Integer.parseInt(values[1]));
				g.addNation(newNation);
				id++;
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
