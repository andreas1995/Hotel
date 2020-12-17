package Model;

import java.util.Date;


public class BillCalculator {
	/*this class calculates the final price*/
	private Room room;
	private int finalPrice;
	private int roomPrice;
	//this value indicates how many days before someone can cancel before getting fined. 
	private int daysBeforeCancel = -1;
	private Database database;
	private RoomHandler rm;
	
	
	public BillCalculator(Reservation res) {
		/*get room and reservation*/
		this.database = new Database();
		this.rm = new RoomHandler();
		try {
			this.room = rm.getTheRoom(res);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.roomPrice = room.getPrice();
		FeesCalculation(res);
		
	}
	
	private void FeesCalculation(Reservation res) {
		Date today = new Date();
		Date startDate = res.getCheckInDate();
		Date endDate = res.getCheckOutDate();
		
		
		
		if( 0 == database.getDateDiff(today, endDate)) {
			calculateFinalPrice(database.getDateDiff(startDate, endDate),roomPrice) ;
		}
		//if today is bigger than startdate that means they checked in
		//in this case they would pay the entire fee anyway because is to late
		else if(0 < database.getDateDiff(startDate, today)) {
			calculateFinalPrice(database.getDateDiff(startDate, endDate),roomPrice);
		}
		//if today is smaller than start date that means they did not check in yet
		//if they cancel in less than 1 day before
		//they would pay half of the price because is to late.
		else if(daysBeforeCancel <= database.getDateDiff(startDate, today) ) {
		calculateFinalPrice(database.getDateDiff(startDate, endDate),roomPrice/2) ;
		}
		//if they cancel before 1 day then there are no charges
		else if(daysBeforeCancel > database.getDateDiff(startDate, today) ) {
			this.finalPrice = 0;
		}
			
		
		
	}
	
    //calculate price
    private void calculateFinalPrice(int daysDifference,int roomPrice) {
    	this.finalPrice = daysDifference * roomPrice;
    }
    
    

	//get price
	public int getFinalPrice() {
		System.out.println(finalPrice);
    	return this.finalPrice;
     }
    
}
