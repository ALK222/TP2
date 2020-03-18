package simulator.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import exceptions.JunctionException;
import exceptions.RoadException;
import exceptions.VehicleException;
import simulator.misc.SortedArrayList;

public class TrafficSimulator {

	//ATRIBUTTES
	
	private RoadMap mapa_carreteras;
	
	private List<Event> event_list;
	
	private int time;
	
	//COSNTRUCTOR
	
	public TrafficSimulator() {
		this.reset();
	}
	
	//METHODS
	
	public void addEvent(Event e) {
		this.event_list.add(e);
	}
	
	public void advance() throws RoadException, VehicleException, JunctionException {
		++time;
		// for(Event e : event_list) {
		// 	if(e.getTime() == this.time) {
		// 		e.execute(mapa_carreteras);
		// 		//event_list.remove(e);
		// 	}
		// }
		List<Event> aux = new ArrayList<>();
		for(Event e: event_list){
			if(e.getTime() == this.time){
				aux.add(e);
			}
			for(Event d : aux){
				d.execute(mapa_carreteras);
				event_list.remove(d);
			}
		}
		
		
		for(Junction j : mapa_carreteras.getJunctions()) {
			j.advance(time);
		}
		
		for(Road road : mapa_carreteras.getRoads()) {
			road.advance(time);
		}
	}
	
	public void reset() {
		this.mapa_carreteras = new RoadMap();
		this.event_list = new SortedArrayList<Event>();
		this.time = 0;
	}
	
	public JSONObject report() {
		JSONObject r = new JSONObject();
		r.append("Time", time);
		r.append("State", mapa_carreteras.report());
		return r;
	}
}
