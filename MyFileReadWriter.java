package javafxprogramme;
 
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Stream;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import allgemein.Hilfsmethoden;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


public class MyFileReadWriter extends Application {
	
	public static List<String> readFileInhalt = new ArrayList<String>();

	public static ObservableList<Stammdatensatz> stammdatenListe =
	        FXCollections.observableArrayList();
	
	private static TableView<Stammdatensatz> table = new TableView<Stammdatensatz>();
	
	final TextField sucheField = new TextField();
	
	public static void main(String[] args) {
        launch(args);
    }	

	
	 @Override
	    public void start(Stage primaryStage) {
	        Button btnLoad 		= new Button("Load");
	        Button btnProcess 	= new Button("Process");
	        Button btnSave 		= new Button("Save");
	        btnLoad.setOnAction		(btnLoadEventListener);
	        btnProcess.setOnAction	(btnProcessEventListener);
	        btnSave.setOnAction		(btnSaveEventListener);
	        
			// Suchstring Eingabefeld
	        Label sucheLabel = new Label("Suchfeld:");
	 
	        VBox rootBox = new VBox();
	        rootBox.getChildren().add(btnLoad);
	        rootBox.getChildren().add(btnProcess);
	        rootBox.getChildren().add(btnSave);
	        rootBox.getChildren().add(sucheLabel);
	        rootBox.getChildren().add(sucheField);
	        Scene scene = new Scene(rootBox, 300, 300);
	        primaryStage.setTitle("MyFileReadWriter");
	        primaryStage.setScene(scene);
	        primaryStage.show();
	    }
	 

	    @SuppressWarnings("deprecation")
		//  
	    // ProcessButton -> Datei verarbeiten   
	    //
	    EventHandler<ActionEvent> btnProcessEventListener
	    = (ActionEvent event) -> {
	        
	    	//Einzeldatensatzverarbeitung
	    	stammdatenListe.clear();
	    	table = new TableView<Stammdatensatz>();
	    	
	    	//default GueltigBis setzen
	    	Date gueltigBis = new Date(2000);
	    	gueltigBis.setYear(2000);
	    	
	    	String suchString = sucheField.getText();
	    	if (suchString.isEmpty()) {

	    		for (int i=0; i < readFileInhalt.size() && i < 10000 ; i++){
	    			// Stammdatenobjekt anlegen
	    			Stammdatensatz satzObjekt = new Stammdatensatz( i+1,
	    														readFileInhalt.get(i).substring(0, 4) + " " + readFileInhalt.get(i).substring(5, 11),
																"MOD",
		    													"DE",
		    													readFileInhalt.get(i),
		    													"",
		    													"ATLAS",
		    													new Date(System.currentTimeMillis()),
		    													gueltigBis,
		    													new Timestamp(System.currentTimeMillis())
		    													);
	    			stammdatenListe.add(satzObjekt);
	    		}
	    	}
	    	else
	    	{
	    		for (int i=0; i < readFileInhalt.size(); i++){
		    		
	    			// Stammdatenobjekt anlegen
	    			if (readFileInhalt.get(i).contains(suchString)) {
	    				Stammdatensatz satzObjekt = new Stammdatensatz( i+1,
	    														readFileInhalt.get(i).substring(0, 4) + " " + readFileInhalt.get(i).substring(5, 11),
																"MOD",
		    													"DE",
		    													readFileInhalt.get(i),
		    													"",
		    													"ATLAS",
		    													new Date(System.currentTimeMillis()),
		    													gueltigBis,
		    													new Timestamp(System.currentTimeMillis())
		    													);
				
	    				stammdatenListe.add(satzObjekt);
	    			}
	    		}
	    	}
	    	table.setItems(stammdatenListe);
  	
	    	Hilfsmethoden.setHinweisDialog("Verarbeitung abgeschlossen", null, 99);	
	    	
	    	table.setEditable(false);
	        table.autosize();
	        
	        TableColumn nrCol = new TableColumn("Nr");
	        nrCol.setMinWidth(60);
	        nrCol.setCellValueFactory(
	        		new PropertyValueFactory("StammNr"));
	        
	        TableColumn codeCol = new TableColumn("Code");
	        codeCol.setMinWidth(60);
	        codeCol.setCellValueFactory(
	        		new PropertyValueFactory("StammCode"));
	        
	        TableColumn typCol = new TableColumn("Typ");
	        typCol.setMinWidth(60);
	        typCol.setCellValueFactory(
	        		new PropertyValueFactory("StammTyp"));
	        
	        TableColumn inhaltCol = new TableColumn("Inhalt");
	        inhaltCol.setMinWidth(60);
	        inhaltCol.setCellValueFactory(
	        		new PropertyValueFactory("StammInhalt"));
	        
	        table.getColumns().addAll(nrCol, codeCol, typCol, inhaltCol);
	        
	        VBox vbox = new VBox();
	        vbox.setSpacing(10);
	        vbox.autosize();
	        VBox.setVgrow(table, Priority.ALWAYS);
	        vbox.getChildren().addAll(table);
	        
	        Scene scene = new Scene(vbox, 900, 900);
	        Stage primaryStage = new Stage();
	        primaryStage.setTitle("TableView");
	        primaryStage.setScene(scene);
	        primaryStage.show();
	    	
	    	
			// erstes.Stammdatenobjekt auslesen
			
	    	System.out.println("Code	: " + stammdatenListe.get(0).getStammCode());
	    	System.out.println("Typ     : " + stammdatenListe.get(0).getStammTyp());
	    	System.out.println("Sprache : " + stammdatenListe.get(0).getStammSprache());
	    	System.out.println("Inhalt  : " + stammdatenListe.get(0).getStammInhalt());
	    	System.out.println("Bem.    : " + stammdatenListe.get(0).getStammBemerkung());
	    	System.out.println("UserID  : " + stammdatenListe.get(0).getStammUserID());
	    	System.out.println("Von     : " + stammdatenListe.get(0).getStammGueltigVon());
	    	System.out.println("Bis     : " + stammdatenListe.get(0).getStammGueltigBis());
	    	System.out.println("AendTS  : " + stammdatenListe.get(0).getStammAenderungsTS());

	    	
	    };	 
	 
	    
	    //  
	    // LoadButton -> Datei einlesen   
	    //
	    EventHandler<ActionEvent> btnLoadEventListener
	    = (ActionEvent event) -> {
	        FileChooser fileChooser = new FileChooser();
	        fileChooser.getExtensionFilters()
	            .addAll(
	                new FileChooser.ExtensionFilter("TXT files (*.TXT)", "*.TXT"),
	                new FileChooser.ExtensionFilter("txt files (*.txt)", "*.txt"));
	         
	        File file = fileChooser.showOpenDialog(null);
	        
	        if (file != null) {
	        	readFileInhalt = Hilfsmethoden.readLineByLineJava8(file.getPath().toString());
	        	Hilfsmethoden.setHinweisDialog("Datei wurde eingelesen", null, 99);	
	        }
	      };
	       
	       
		    //  
		    // saveButton -> Datei ausgeben   
		    //
		    EventHandler<ActionEvent> btnSaveEventListener
		    = (ActionEvent event) -> {
		        FileChooser fileChooser = new FileChooser();
		        fileChooser.getExtensionFilters()
		            .addAll(
		                new FileChooser.ExtensionFilter("TXT files (*.TXT)", "*.TXT"),
		                new FileChooser.ExtensionFilter("txt files (*.txt)", "*.txt"));
		         
		        File file = fileChooser.showSaveDialog(null);
		        
		        if (file != null) {
		        	try {
	        			Files.write(Paths.get(file.getPath().toString()), readFileInhalt);
		        		Hilfsmethoden.setHinweisDialog("Datei wurde gespeichert", null, 99);	        	
		        	} catch (IOException e) {
		        	    e.printStackTrace();
		        	}
		        }
		       };
		       


}
