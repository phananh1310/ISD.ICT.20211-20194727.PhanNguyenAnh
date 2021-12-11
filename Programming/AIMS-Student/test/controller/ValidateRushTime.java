package controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ValidateRushTime {

	private PlaceRushOrderController placeRushOrderController;

	@BeforeEach
	void setUp() throws Exception {
		placeRushOrderController = new PlaceRushOrderController();
	}
	
	@ParameterizedTest
	@CsvSource({
		"2022/11/10 08:30,true",
		"2022/13/10 08:30,false",
		"2020/10/11 08:30,false",
		"13/10/2001 08:30,false",
		"2022/11/10 05:30,false",
		"2022/10/32 08:30,false",
		"13/10 08:30,false",
		"2022/11/10 08,false",

		
	})

	public void test(String time, boolean expected) {
		boolean isValided = PlaceRushOrderController.validateRushTime(time);
		assertEquals(expected,isValided);
	}
}
