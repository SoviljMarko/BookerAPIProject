package resources;

import pojo.BookingDates;
import pojo.SetBookData;

public class FistBookDataBuild {

	public SetBookData addBookPayload() {
		SetBookData book = new SetBookData();
		
		book.setFirstname("Brandon");
		book.setLastname("Sanderson");
		book.setTotalprice(999);
		book.setDepositpaid(true);
		book.setAdditionalneeds("First Book");
		
		BookingDates dates = new BookingDates();
		dates.setCheckin("2018-01-01");
		dates.setCheckout("2019-01-01");
		
		book.setBookingDates(dates);
		
		return book;
	}
}
