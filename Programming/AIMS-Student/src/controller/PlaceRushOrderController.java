package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Logger;

import common.exception.InvalidDeliveryInfoException;
import common.exception.InvalidRushOrderInfoException;
import common.exception.PlaceOrderException;
import entity.cart.Cart;
import entity.cart.CartMedia;
import entity.order.Order;
import entity.order.OrderMedia;
import entity.order.RushOrder;
import utils.Configs;

/**
 * This class controls the flow of place rush order usecase in this AIMS project
 * @author phananh
 */

public class PlaceRushOrderController extends PlaceOrderController {

	 /**
     * This method checks the supported of product when user click PlaceRushOrder button
     * @throws SQLException
     */
	
    public void placeRushOrder() throws SQLException{
        Cart.getCart().checkRushOrderMedia();
    }
	
	/**
	* This method creates the new RushOrder based on the Cart (Media which is supported for RushOrder)
	*
	* @return RushOrder
	* @throws SQLException
	*/
	public RushOrder createRushOrder() {
		RushOrder rushOrder = new RushOrder();
		for (Object object : Cart.getCart().getListMedia()) {
			CartMedia cartMedia = (CartMedia) object;
			if(cartMedia.getMedia().isSupportRushOrder()) {
				OrderMedia orderMedia = new OrderMedia(cartMedia.getMedia(), cartMedia.getQuantity(), cartMedia.getPrice());
				rushOrder.getlstOrderMedia().add(orderMedia);
			}
		}
		return rushOrder;
	}

	/**
	* This method creates the new Order based on the Cart (Media which is not supported for RushOrder)
	*
	* @return Order
	* @throws SQLException
	*/
	public Order createOrder() {
		Order order = new Order();
		for (Object object : Cart.getCart().getListMedia()) {
			CartMedia cartMedia = (CartMedia) object;
			if(!cartMedia.getMedia().isSupportRushOrder()) {
				OrderMedia orderMedia = new OrderMedia(cartMedia.getMedia(), cartMedia.getQuantity(), cartMedia.getPrice());
				order.getlstOrderMedia().add(orderMedia);
			}
		}
		return order;
	}

	/**
	* This method calculates the shipping fees of RushOrder
	* fee = fee + 10 * number of items for rush order
	*
	* @param rushOrder
	* @return shippingFee
	*/
	public int calculateRushOrderShippingFee(RushOrder rushOrder) {
	// + 10 for each product support rush order 
	Random rand = new Random();
	int fees = (int) (((rand.nextFloat() * 10) / 100) * rushOrder.getAmount()) + rushOrder.getlstOrderMedia().size() * 10;
	LOGGER.info("Order Amount: " + rushOrder.getAmount() + " -- Shipping Fees: " + fees);
	return fees;
	}



	/**
	* This method check if deliveryTime is valid
	*
	* @param time
	* @return boolean
	*/
	public static boolean validateRushTime(String time) {
		if (time=="") return false;
		if (time.isEmpty()) return true;
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
			LocalDateTime dateTime = LocalDateTime.parse(time, formatter);
			LocalDateTime now = LocalDateTime.now();
			boolean isAfter = dateTime.isAfter(now);
			boolean validHour = false;
			if (dateTime.getHour() <= 18 && dateTime.getHour() >= 7) {
				validHour = true;
			}
			return isAfter && validHour;
		} catch (NullPointerException e) {
			return true;
		} catch (DateTimeParseException e) {
			return false;
		}
	}


	/**
	* This method check if province in deliveryInfo is Hanoi
	*
	* @param province
	* @return boolean
	*/
	public boolean validateProvince(String province) {
		if (province != null) {
			return (province.equals("Hà Nội"));
		}
		return false;
	}
	
	
	public void processRushOrderInfo(HashMap info) throws IOException, InterruptedException {
		LOGGER.info("Process Rush Order Delivery Info");
		LOGGER.info(info.toString());
		validateRushOrderInfo(info);
	}

	public void validateRushOrderInfo(HashMap<String, String> info) throws InterruptedException, IOException {
		if (!validateRushTime(info.get("time")))
			throw new InvalidRushOrderInfoException("Invalid time");
	}
	
	/**
	* This method check DeliveryInfo of a Rush Order
	*
	* @param info
	* @return void
	*/
	public void validateDeliveryInfo(HashMap<String, String> info) throws InterruptedException, IOException{
		super.validateDeliveryInfo(info);
		if (!(validateProvince(info.get("province"))))
			throw new InvalidDeliveryInfoException("Only Ha noi Province is supported");
	}
}
