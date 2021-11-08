package checkOutScreen;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import gridItem.ItemController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class checkOutController implements Initializable {

    @FXML
    private StackPane stackPane;

    @FXML
    private VBox orderSummaryItemVbox;
    
   Parent fxml;

    @FXML
    void customerInfoBtnClicked(ActionEvent event) {
		try {
			fxml = FXMLLoader.load(getClass().getResource("/checkOutSubScreen/CustomerInfo.fxml"));
			stackPane.getChildren().removeAll();
			stackPane.getChildren().setAll(fxml);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	
    }

    @FXML
    void invoiceBtnClicked(ActionEvent event) {
    	try {
			fxml = FXMLLoader.load(getClass().getResource("/checkOutSubScreen/Invoice.fxml"));
			stackPane.getChildren().removeAll();
			stackPane.getChildren().setAll(fxml);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void paymentMethodBtnClicked(ActionEvent event) {
    	try {
			fxml = FXMLLoader.load(getClass().getResource("/checkOutSubScreen/paymentMethod.fxml"));
			stackPane.getChildren().removeAll();
			stackPane.getChildren().setAll(fxml);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			fxml = FXMLLoader.load(getClass().getResource("/checkOutSubScreen/CustomerInfo.fxml"));
			stackPane.getChildren().removeAll();
			stackPane.getChildren().setAll(fxml);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
