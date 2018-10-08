package MyTableView;

import java.io.File;
import java.io.IOException;

import MyTableView.Account;
import MyTableView.ExcelReadWriter;
import MyTableView.AccountTableView;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TouchEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;

//
//example of editable Table View <> Excel.xlsx file
//

public class MyTableView extends Application {

	// path to excel file
	public static File pfad_data = new File("c://");
	public static File pfad_data_notebook = new File("c://d/java/data/mytableview");
	
	// display width/height
	public int displayX = 800;
	public int displayY = 600;
	
	Insets insets = new Insets(10,10,10,10);

	// Account Cache
	private static ObservableList<Account> accounts = FXCollections.observableArrayList();
	private static TableView<Account> accountsTableView = new TableView<Account>();

	public static void addAccount(Account acc) {
		accounts.add(acc);
	}

	public static ObservableList<Account> getAccounts() {
		return accounts;
	}

	// ---
	// programstart
	// ---
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {

		// get display width/height
		displayX = (int) Screen.getPrimary().getVisualBounds().getWidth();
		displayY = (int) Screen.getPrimary().getVisualBounds().getHeight();

        // fill and show Account TableView
		accounts.clear();
		File file = new File(pfad_data_notebook + "/Accounts.xlsx");
		if (file != null) {
			try {
				ExcelReadWriter.readXLSXFileToAccountTable(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		TableView<Account> table = AccountTableView.getAccountTableView(accounts);
		table.setEditable(true);
		HBox tableBottomBox = getAddTableRowBox();  
		
		// fill mobile touchable vbox
		VBox vbox = getTableViewVBox();
		vbox.getChildren().addAll(table, tableBottomBox);
		
		// show TableView
		Scene scene = new Scene(vbox, displayX/2, displayY/2);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Table View");
		primaryStage.show();
		
		// store tableView into Excel-file before closing Stage
		primaryStage.setOnCloseRequest( event ->
	    {
	    	File accfile = new File(pfad_data_notebook + "/Accounts.xlsx");
			if (accfile != null) {
				try {
					ExcelReadWriter.writeAccountTableToXLSXFile(accfile);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	    });

	}
	
	private VBox getTableViewVBox() {
		VBox vbox = new VBox();
		vbox.setMinSize(displayX, displayY);
		vbox.setAlignment(Pos.CENTER);
		vbox.setPadding(insets);

		return vbox;
	}
	
	// bottom Box for adding new rows on TableView
	private HBox getAddTableRowBox() {
		HBox hbox = new HBox();
        final TextField addID = new TextField();
        addID.setPromptText("Account-ID");
        addID.setMaxWidth(120);
        addID.setMinWidth(30);
        final TextField addName = new TextField();
        addName.setPromptText("Name");
        addName.setMaxWidth(120);
        addName.setMinWidth(30);
        final TextField addWallet = new TextField();
        addWallet.setPromptText("Wallet-Address");
        addWallet.setMaxWidth(300);
        addWallet.setMinWidth(300);
 
        final Button addButton = new Button("add");
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                addAccount(new Account(
                		addID.getText(),
                		addName.getText(),
                		"default",
                		addWallet.getText(),
                		"0")
                );
                addID.clear();
                addName.clear();
                addWallet.clear();         
            }
        });
        hbox.setSpacing(5);
    	hbox.getChildren().addAll(addID, addName, addWallet, addButton);
		
		return hbox;
	}
}
