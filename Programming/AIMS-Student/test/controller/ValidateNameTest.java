package controller;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import controller.PlaceOrderController;

class ValidateNameTest {

	private PlaceOrderController placeOrderController;

	@BeforeEach
	void setUp() throws Exception {
		placeOrderController = new PlaceOrderController();
	}
	
	@ParameterizedTest
	@CsvSource({
		"phananh,true",
		"phananh1310,false",
		"$#phananh,false",
		",false"
	})

	public void test(String name, boolean expected) {
		boolean isValided = PlaceOrderController.validateName(name);
		assertEquals(expected,isValided);
	}

}
