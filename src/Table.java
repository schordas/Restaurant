
public class Table {
	int tableNum = 0;
	int numSeats = 0;
	int startX = 0;
	int	startY = 0;
	int occupiedSeats = 0;
	String shape = "";
	int radius = 0; //for round 
	int width = 0;	//for rectangle & square
	int height = 0; //for rectangle & square 
	String status = "";
	boolean isSqr = false;
	boolean isRect = false;
	boolean isRnd = false;
	boolean isOccupied = false;
	
	public void setTableNum(int tableNum){
		this.tableNum = tableNum;
	}
	public void setNumSeats(int numSeats){
		this.numSeats = numSeats;
	}
	public void setStartX(int startX){
		this.startX = startX;
	}
	public void setStartY(int startY){
		this.startY = startY;
	}
	public void setOccSeats(int occupiedSeats){
		this.occupiedSeats = occupiedSeats;
	}
	public void setShape(String shape){
		this.shape = shape;
		if (this.shape.equals("round")){
			this.isRnd = true;
			this.isRect = false;
			this.isSqr = false;
		}
		else if (this.shape.equals("square")){
			this.isRnd = false;
			this.isRect = false;
			this.isSqr = true;
		}
		else {
			this.isRnd = false;
			this.isRect = true;
			this.isSqr = false;
		}
	}
	
	public void setRadius(int radius){
		this.radius = radius;
		
	}
	
	public void setSides(int width, int height){
		this.width = width;
		this.height = height;
	}
	public void setStatus(String status){
		this.status = status;
		if(this.status.equals("open")){
			isOccupied = false;
		}
		else{
			isOccupied = true;
		}
	}
	public int getTableNum(){
		return this.tableNum;
	}
	
}
