package MyTableView;

import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Callback;
import MyTableView.Account;

//
// example of editable Table View 
//

public class AccountTableView {

	public static TableView getAccountTableView(ObservableList<Account> accountList) {

		final Label label = new Label("Accounts");
		label.setFont(new Font("Arial", 15));

		TableView table = new TableView<Account>();
		table.setItems(accountList);
		table.setEditable(true);
		table.autosize();

		TableColumn accIdCol = new TableColumn("Account-ID");
		accIdCol.setMinWidth(60);
		accIdCol.setCellValueFactory(new PropertyValueFactory("Id"));
		accIdCol.setCellFactory(TextFieldTableCell.forTableColumn());
		accIdCol.setOnEditCommit(new EventHandler<CellEditEvent<Account, String>>() {
			@Override
			public void handle(CellEditEvent<Account, String> t) {
				((Account) t.getTableView().getItems().get(t.getTablePosition().getRow())).setId(t.getNewValue());
				Account akt_t;
				akt_t = (Account) t.getTableView().getItems().get(t.getTablePosition().getRow());
			}
		});

		TableColumn nameCol = new TableColumn("Name");
		nameCol.setMinWidth(80);
		nameCol.setCellValueFactory(new PropertyValueFactory("Name"));
		nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
		nameCol.setOnEditCommit(new EventHandler<CellEditEvent<Account, String>>() {
			@Override
			public void handle(CellEditEvent<Account, String> t) {
				((Account) t.getTableView().getItems().get(t.getTablePosition().getRow())).setName(t.getNewValue());
				Account akt_t;
				akt_t = (Account) t.getTableView().getItems().get(t.getTablePosition().getRow());
			}
		});

		TableColumn walletCol = new TableColumn("Wallet-Adresse");
		walletCol.setMinWidth(60);
		walletCol.setCellValueFactory(new PropertyValueFactory("WalletAddress"));
		walletCol.setCellFactory(TextFieldTableCell.forTableColumn());
		walletCol.setOnEditCommit(new EventHandler<CellEditEvent<Account, String>>() {
			@Override
			public void handle(CellEditEvent<Account, String> t) {
				((Account) t.getTableView().getItems().get(t.getTablePosition().getRow()))
						.setWalletAddress(t.getNewValue());
				Account akt_t;
				akt_t = (Account) t.getTableView().getItems().get(t.getTablePosition().getRow());
			}
		});

		TableColumn tokenCol = new TableColumn("Token");
		tokenCol.setMinWidth(60);
		tokenCol.setCellValueFactory(new PropertyValueFactory("Balance"));

		table.getColumns().addAll(accIdCol, nameCol, walletCol, tokenCol);

		table.setRowFactory(new Callback<TableView<Account>, TableRow<Account>>() {
			@Override
			public TableRow<Account> call(TableView<Account> tableView) {
				final TableRow<Account> row = new TableRow<>();
				final ContextMenu contextMenu = new ContextMenu();
				final MenuItem removeMenuItem = new MenuItem("delete row");
				final MenuItem addMenuItem = new MenuItem("copy row");

				removeMenuItem.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						table.getItems().remove(row.getItem());
						// showGenericTable();
					}
				});

				addMenuItem.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						MyTableView.addAccount(new Account(row.getItem().getId(), row.getItem().getName(),
								row.getItem().getPw(), row.getItem().getWalletAddress(), "0"));
					}
				});

				contextMenu.getItems().addAll(removeMenuItem, addMenuItem);

				// Set context menu on row, but use a binding to make it only show for non-empty
				// rows:
				row.contextMenuProperty();
				row.contextMenuProperty()
						.bind(Bindings.when(row.emptyProperty()).then((ContextMenu) null).otherwise(contextMenu));

				return row;
			}
		});
		
		// mobile touch implementation

		return table;

	}

}
