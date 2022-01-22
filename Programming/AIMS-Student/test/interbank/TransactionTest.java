package interbank;

import entity.payment.CreditCard;
import entity.payment.PaymentTransaction;
import subsystem.interbank.InterbankSubsystemController;

public class TransactionTest {

	public static void main(String[] args) {
		CreditCard card = new CreditCard("ict_group5_2021","Group 5",523,"1125");
		InterbankSubsystemController inter= new InterbankSubsystemController();
		PaymentTransaction trans = inter.payOrder(card, 1, "no");
		//PaymentTransaction trans = inter.payOrder(card, 1, "test");
		System.out.println(trans.getErrorCode());

	}

}
