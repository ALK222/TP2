package simulator.model;

import java.util.List;

public class RoundRobinStrategy implements LightSwitchingStrategy {
/*	List<Road> roads;
	List<List<Vehicle>> qs;
	int currGreen;
	int lastSwitchingTime;
	int currTime;

RoundRobinStrategy()
this.roads = roads;
this.qs = qs;
this.currGreen = currGreen;
this.lastSwitchingTime = lastSwitchingTime;
this.currTime = currTime;/*/
	int timeSlot;
	RoundRobinStrategy(int timeSlot){
		this.timeSlot = timeSlot;
	}
	@Override
	public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, int lastSwitchingTime,
			int currTime) {/*EN TEORIA HECHO*/
		if(roads.isEmpty())return -1;
		if(currGreen==-1) return 0;
		if(currTime-lastSwitchingTime < this.timeSlot)return currGreen;
		return currGreen+1%roads.size();
	}

}
