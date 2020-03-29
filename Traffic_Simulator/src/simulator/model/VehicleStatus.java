package simulator.model;

public enum VehicleStatus {
	PENDING, TRAVELING, WAITING, ARRIVED;
	public String parse(){
		if(this.equals(PENDING)) return "PENDING";
		else if (this.equals(TRAVELING)) return "TRAVELING";
		else if(this.equals(WAITING)) return "WAITING";
		else return "ARRIVED";
	}
}
