package simulator.model;

public enum Weather {
	SUNNY, CLOUDY, RAINY, WINDY, STORM;
	public String parse(){
		if(this.equals(SUNNY)) return "SUNNY";
		else if (this.equals(CLOUDY)) return "CLOUDY";
		else if(this.equals(RAINY)) return "RAINY";
		else if(this.equals(WINDY)) return "WINDY";
		else return "STORM";
	}
}
