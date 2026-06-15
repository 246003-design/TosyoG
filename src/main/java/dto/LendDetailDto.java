package dto;

public class LendDetailDto {
	private String title;
	private java.sql.Timestamp lendDate;
	private java.sql.Timestamp dueDate;
	private boolean overdue;
	
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public java.sql.Timestamp getLendDate() {
		return lendDate;
	}
	public void setLendDate(java.sql.Timestamp lendDate) {
		this.lendDate = lendDate;
	}
	public java.sql.Timestamp getDueDate() {
		return dueDate;
	}
	public void setDueDate(java.sql.Timestamp dueDate) {
		this.dueDate = dueDate;
	}
	public boolean isOverdue() {
		return overdue;
	}
	public void setOverdue(boolean overdue) {
		this.overdue = overdue;
	}

	
	


}
