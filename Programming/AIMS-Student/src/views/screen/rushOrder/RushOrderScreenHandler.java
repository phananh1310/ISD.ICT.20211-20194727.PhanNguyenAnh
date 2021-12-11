package views.screen.rushOrder;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;

import common.exception.InvalidDeliveryInfoException;
import common.exception.InvalidRushOrderInfoException;
import controller.PlaceOrderController;
import controller.PlaceRushOrderController;
import entity.invoice.Invoice;
import entity.order.Order;
import entity.order.RushOrder;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import utils.Configs;
import views.screen.BaseScreenHandler;
import views.screen.invoice.InvoiceScreenHandler;
import views.screen.popup.PopupScreen;

public class RushOrderScreenHandler extends BaseScreenHandler implements Initializable {

    @FXML
    private TextField instructions;

    @FXML
    private Label screenTitle;

    @FXML
    private Button btnConfirmRushOrder;

    @FXML
    private TextField time;
    
    private Order rushOrder;
    
    public RushOrderScreenHandler(Stage stage, String screenPath, Order order) throws IOException {
		super(stage, screenPath);
		this.rushOrder = order;
	}
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		final BooleanProperty firstTime = new SimpleBooleanProperty(true); // Variable to store the focus on stage load
		time.focusedProperty().addListener((observable,  oldValue,  newValue) -> {
            if(newValue && firstTime.get()){
                content.requestFocus(); // Delegate the focus to container
                firstTime.setValue(false); // Variable value changed for future references
            }
        });
	}
    
    @FXML
    void submitRushOrder(MouseEvent event) throws IOException, InterruptedException, SQLException {
    	// add info to messages

    	HashMap messages = new HashMap<>();
    	messages.put("time", time.getText());
    	messages.put("instructions", instructions.getText());
    	try {
    		// process and validate rush order info
    		getBController().processRushOrderInfo(messages);
    	} catch (InvalidRushOrderInfoException e) {
    		PopupScreen.error(e.getMessage());
    		return;
    	}
    	
    	
    	// create invoice screen
    	rushOrder.getDeliveryInfo().put("time",time.getText());
    	rushOrder.getDeliveryInfo().put("delivery_instructions", instructions.getText());
    	
    	Invoice invoice = getBController().createInvoice(rushOrder);
    	
    	BaseScreenHandler InvoiceScreenHandler = new InvoiceScreenHandler(this.stage, Configs.INVOICE_SCREEN_PATH, invoice);
    	InvoiceScreenHandler.setPreviousScreen(this);
    	InvoiceScreenHandler.setHomeScreenHandler(homeScreenHandler);
    	InvoiceScreenHandler.setScreenTitle("Invoice Screen");
    	InvoiceScreenHandler.setBController(getBController());
    	InvoiceScreenHandler.show();
    }
    
    public PlaceRushOrderController getBController(){
		return (PlaceRushOrderController) super.getBController();
	}

	public void notifyError(){
		// TODO: implement later on if we need
	}
}
