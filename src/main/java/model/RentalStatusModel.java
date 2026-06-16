package model;

import java.sql.Timestamp;

public class RentalStatusModel {
	
	public boolean isOverdue(Timestamp time) {
		Timestamp now = new Timestamp(System.currentTimeMillis());
		return now.after(time);
	}

}
