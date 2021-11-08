package searchScreen;

import gridItem.Item;
import gridItem.ItemController;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class Controller implements Initializable {

	 @FXML
	    private GridPane grid;
	 @FXML
	    private ScrollPane scroll;

	 
	 private List<Item> items = new ArrayList<>();
	 
	 private List<Item> getData(){
		 List<Item> items = new ArrayList<>();
		 Item item;
		 
		 for (int i=0;i<10;i++) {
			 item = new Item();
			 item.setName("Book 1");
			 item.setPrice(20);
			 item.setImgSrc("/AIMSImage/Media/cambridge-13-final.png");
			 items.add(item);
		 }
		 
		 return items;
	 }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		items.addAll(getData());

		int column = 0, row =0;
		try {
		for (int i=0;i<items.size();i++) {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource("/gridItem/GridItem.fxml"));
			
			AnchorPane anchorPane = fxmlLoader.load();
						
			ItemController itemController = fxmlLoader.getController();
			
		
			itemController.setData(items.get(i));
			
			if (column == 3) {
				column = 0;
				row += 1;
			}
			
			grid.add(anchorPane, column, row); 
			column +=1; 
		}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	 

}
