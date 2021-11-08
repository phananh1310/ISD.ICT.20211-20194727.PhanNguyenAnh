package cartScreen;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import cartItem.CartItem;
import cartItem.CartItemController;
import gridItem.Item;
import gridItem.ItemController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class cartController implements Initializable {

    @FXML
    private ScrollPane scroll;

    @FXML
    private VBox vbox;
    
    private List<CartItem> cartItems = new ArrayList<>();
	 
	 private List<CartItem> getData(){
		 List<CartItem> cartItems = new ArrayList<>();
		 CartItem cartItem;
		 
		 for (int i=0;i<10;i++) {
			 cartItem = new CartItem();
			 cartItem.setName("Book 1");
			 cartItem.setPrice(20);
			 cartItem.setImgSrc("/AIMSImage/Media/cambridge-13-final.png");
			 cartItems.add(cartItem);
		 }
		 
		 return cartItems;
	 }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		cartItems.addAll(getData());

		try {
		for (int i=0;i<cartItems.size();i++) {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource("/cartItem/CartItem.fxml"));
			
			AnchorPane anchorPane = fxmlLoader.load();
						
			CartItemController itemController = fxmlLoader.getController();
			
		
			itemController.setData(cartItems.get(i));
			vbox.getChildren().add(anchorPane);
		}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
