package javafxprogramme;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.print.JobSettings;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MyPrinter extends Application {
	
		double randOben   = 50;
		double randUnten  = 50;
		double randLinks  = 100;
		double randRechts = 100;
	
	   // Create the TextArea
		final TextArea textArea = new TextArea();
		// Create the JobStatus Label	
		private final Label jobStatus = new Label();
		
		public static void main(String[] args) 
		{
			Application.launch(args);
		}
		
		@Override
		public void start(final Stage stage)
		{
			// Create the Text Label
			Label textLabel = new Label("Please insert your Text:");

			// Create the TextArea
			final TextArea textArea = new TextArea();
			textArea.setPrefHeight(300);  
			textArea.setPrefWidth(300);    
			
			// Create the Buttons
			Button printSetupButton = new Button("Print Setup and Print");
			
			// Create the Event-Handlers for the Button
			printSetupButton.setOnAction(new EventHandler <ActionEvent>() 
			{
	            public void handle(ActionEvent event) 
	            {
	            	Label label1 = new Label("Labeltext: 12345");
	            	HBox hbox = new HBox(label1);
	            	hbox.setAlignment(Pos.CENTER);
	            	VBox content = new VBox(10);
	            	content.getChildren().addAll(textArea, hbox);
	            	content.setAlignment(Pos.CENTER);
	            	printSetup(content, stage);
	            }
	        });
			
			// Create the Status Box
			HBox jobStatusBox = new HBox(5, new Label("Job Status: "), jobStatus);
			// Create the Button Box
			HBox buttonBox = new HBox(printSetupButton);
			
			// Create the VBox
			VBox root = new VBox(5);

			// Add the Children to the VBox		
			
			root.getChildren().addAll(textLabel, textArea, buttonBox, jobStatusBox);
			// Set the Size of the VBox
			root.setPrefSize(300, 300);		
			
			// Set the Style-properties of the VBox
			root.setStyle("-fx-padding: 10;" +
					"-fx-border-style: solid inside;" +
					"-fx-border-width: 2;" +
					"-fx-border-insets: 5;" +
					"-fx-border-radius: 5;" +
					"-fx-border-color: blue;");
			
			// Create the Scene
			Scene scene = new Scene(root);
			// Add the scene to the Stage
			stage.setScene(scene);
			// Set the title of the Stage
			stage.setTitle("(pdf) Druck Beispiel");
			// Display the Stage
			stage.show();		
		}
		
		private void printSetup(Node node, Stage owner) 
		{
			
			// Create the PrinterJob		
			PrinterJob job = PrinterJob.createPrinterJob();
			printAttributes(job);
		
			if (job == null) 
			{
				return;
			}

			// Show the print setup dialog
			job.showPageSetupDialog(owner);
			boolean proceed = job.showPrintDialog(owner);
			
			if (proceed) 
			{
				print(job, node);
			}
		}
		
		private void print(PrinterJob job, Node node) 
		{
			// Set the Job Status Message
			jobStatus.textProperty().bind(job.jobStatusProperty().asString());
			
			// Print the node
			boolean printed = job.printPage(node);
		
			if (printed) 
			{
				job.endJob();
			}
		}	
		
		private void printAttributes(PrinterJob printerJob)
		{
			// Create the Printer Job
			//PrinterJob printerJob = PrinterJob.createPrinterJob();
			
			// Get The Printer Job Settings
			JobSettings jobSettings = printerJob.getJobSettings();
			
			// Get the Page Layout
			PageLayout pageLayout = jobSettings.getPageLayout();
			
			// Get The Printer
			Printer printer = printerJob.getPrinter();
			
			// Create the Page Layout of the Printer
			
			//pageLayout = printer.createPageLayout(Paper.A6,
			//		PageOrientation.PORTRAIT,Printer.MarginType.EQUAL);
			
			pageLayout = printer.createPageLayout(Paper.A4,
					PageOrientation.PORTRAIT,randLinks,randRechts,randOben,randUnten);
			
			jobSettings.setPageLayout(pageLayout);
			
			/*
			// Get the Page Attributes
			double pgW = pageLayout.getPrintableWidth();
			double pgH = pageLayout.getPrintableHeight();
			double pgLM = pageLayout.getLeftMargin();
			double pgRM = pageLayout.getRightMargin();
			double pgTM = pageLayout.getTopMargin();
			double pgBM = pageLayout.getBottomMargin();
			
			// Show the Page Attributes
			textArea.appendText("Printable Width: " + pgW + "\n");				
			textArea.appendText("Printable Height: " + pgH + "\n");	
			textArea.appendText("Page Left Margin: " + pgLM + "\n");	
			textArea.appendText("Page Right Margin: " + pgRM + "\n");	
			textArea.appendText("Page Top Margin: " + pgTM + "\n");	
			textArea.appendText("Page Bottom Margin: " + pgBM + "\n");		
			
			// Create the Scene
			Scene scene = new Scene(textArea);
			// Add the scene to the Stage
			Stage stage1 = new Stage();
			stage1.setScene(scene);
			// Set the title of the Stage
			stage1.setTitle("Seiteneinstellungen");
			// Display the Stage
			stage1.show();		
			*/
		}
}
