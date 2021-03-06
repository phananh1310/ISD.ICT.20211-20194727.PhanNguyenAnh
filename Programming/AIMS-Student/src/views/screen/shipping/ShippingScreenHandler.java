package views.screen.shipping;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import controller.PlaceOrderController;
import controller.PlaceRushOrderController;
import common.exception.InvalidDeliveryInfoException;
import entity.cart.Cart;
import entity.invoice.Invoice;
import entity.order.Order;
import entity.order.OrderMedia;
import entity.order.RushOrder;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import utils.Configs;
import views.screen.BaseScreenHandler;
import views.screen.invoice.InvoiceScreenHandler;
import views.screen.popup.PopupScreen;
import views.screen.rushOrder.RushOrderScreenHandler;

public class ShippingScreenHandler extends BaseScreenHandler implements Initializable {

	@FXML
	private Label screenTitle;

	@FXML
	private TextField name;

	@FXML
	private TextField phone;

	@FXML
	private TextField address;

	@FXML
	private TextField instructions;

	@FXML
	private ComboBox<String> province;

	private Order order;
	
	private RushOrder rushOrder = null; // only supported media
	private Order normalOrder; // only none supported media

	public ShippingScreenHandler(Stage stage, String screenPath, Order order) throws IOException {
		super(stage, screenPath);
		this.order = order;
	}

	public ShippingScreenHandler(Stage stage, String shippingScreenPath, Order normalOrder, RushOrder rushOrder) throws IOException {
		super(stage, shippingScreenPath);
		this.normalOrder = normalOrder;
		this.rushOrder = rushOrder;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		final BooleanProperty firstTime = new SimpleBooleanProperty(true); // Variable to store the focus on stage load
		name.focusedProperty().addListener((observable,  oldValue,  newValue) -> {
            if(newValue && firstTime.get()){
                content.requestFocus(); // Delegate the focus to container
                firstTime.setValue(false); // Variable value changed for future references
            }
        });
		this.province.getItems().addAll(Configs.PROVINCES);
	}

	@FXML
	void submitDeliveryInfo(MouseEvent event) throws IOException, InterruptedException, SQLException {

		// add info to messages
		HashMap messages = new HashMap<>();
		messages.put("name", name.getText());
		messages.put("phone", phone.getText());
		messages.put("address", address.getText());
		messages.put("instructions", instructions.getText());
		messages.put("province", province.getValue());
		try {
			// process and validate delivery info
			getBController().processDeliveryInfo(messages);
		} catch (InvalidDeliveryInfoException e) {
			PopupScreen.error(e.getMessage());
			return;
		}
	
		if (rushOrder==null) {
			// calculate shipping fees
			int shippingFees=0;
			shippingFees = getBController().calculateShippingFee(order);
			order.setShippingFees(shippingFees);
			order.setDeliveryInfo(messages);
		
		
			// create invoice screen
			Invoice invoice = getBController().createInvoice(order);
		
			BaseScreenHandler InvoiceScreenHandler = new InvoiceScreenHandler(this.stage, Configs.INVOICE_SCREEN_PATH, invoice);
			InvoiceScreenHandler.setPreviousScreen(this);
			InvoiceScreenHandler.setHomeScreenHandler(homeScreenHandler);
			InvoiceScreenHandler.setScreenTitle("Invoice Screen");
			InvoiceScreenHandler.setBController(getBController());
			InvoiceScreenHandler.show();
		}
		else {
			int shippingFees=0;
			shippingFees = getBController().calculateShippingFee(normalOrder);
			shippingFees += ((PlaceRushOrderController) getBController()).calculateRushOrderShippingFee(rushOrder);
			
			order = new Order();
			order.setDeliveryInfo(messages);
			order.setShippingFees(shippingFees);
			order.setRushOrder(true);
			
			List<OrderMedia> list = new ArrayList<OrderMedia>();
			list.addAll(normalOrder.getlstOrderMedia());
			list.addAll(rushOrder.getlstOrderMedia());
			order.setlstOrderMedia(list);
			
			
			BaseScreenHandler RushOrderScreenHandler = new RushOrderScreenHandler(this.stage, Configs.RUSH_ORDER_PATH, order);
			RushOrderScreenHandler.setPreviousScreen(this);
			RushOrderScreenHandler.setHomeScreenHandler(homeScreenHandler);
			RushOrderScreenHandler.setScreenTitle("Rush Order Screen");
			RushOrderScreenHandler.setBController(getBController());
			RushOrderScreenHandler.show();
		}
	}

	public PlaceOrderController getBController(){
		if (rushOrder==null)
			return (PlaceOrderController) super.getBController();
		else 
			return (PlaceRushOrderController) super.getBController();
	}

	public void notifyError(){
		// TODO: implement later on if we need
	}

}
