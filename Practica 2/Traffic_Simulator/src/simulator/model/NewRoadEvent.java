package simulator.model;

public abstract class NewRoadEvent extends Event {
	protected Road r;
	String id; 
	String srcJunc;
	String destJunc;
	int lenght; 
	int co2Limit; 
	int maxSpeed;
	Weather weather;

	NewRoadEvent(int time, String id, String srcJunc, String destJunc, int lenght, int co2Limit, int maxSpeed, Weather weather) {
		super(time);
		this.id = id;
		this.srcJunc = srcJunc;
		this.destJunc = destJunc;
		this.lenght = lenght;
		this.co2Limit = co2Limit;
		this.maxSpeed = maxSpeed;
		this.weather = weather;
	}
	
	
	public String toString(){
		return "New Road '" + this.r._id + "'";
	}



}
