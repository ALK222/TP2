package simulator.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import exceptions.JunctionException;
import exceptions.RoadException;
import exceptions.VehicleException;
import simulator.misc.SortedArrayList;

public class TrafficSimulator implements Observable<TrafficSimObserver>{

	// ATRIBUTTES

	private RoadMap mapa_carreteras;

	private List<Event> event_list;

	private int time;

	private List<TrafficSimObserver> observers;

	// COSNTRUCTOR

	public TrafficSimulator() {
		this.mapa_carreteras = new RoadMap();
		this.event_list = new SortedArrayList<Event>();
		this.time = 1;
		this.observers =  new SortedArrayList<TrafficSimObserver>();
	}

	// METHODS

	public void addEvent(Event e) {
		this.event_list.add(e);
		onEventAdded(mapa_carreteras, event_list, e, time);
	}

	private void onEventAdded(RoadMap mapa_carreteras2, List<Event> event_list2, Event e, int time2) {
		// TODO Auto-generated method stub
		
	}

	public void advance() throws RoadException, VehicleException, JunctionException {

		try{
			++time;

			this.onAdvanceStart(this.mapa_carreteras, this.event_list, this.time);
	
			List<Event> aux1 = new ArrayList<>();
			for (Event e : event_list) {
				if (e.getTime() == this.time) {
					aux1.add(e);
					e.execute(mapa_carreteras);
				}
			}
	
			event_list.removeAll(aux1);
	
			for (Junction j : mapa_carreteras.getJunctions()) {
				j.advance(time);
			}
	
			for (Road road : mapa_carreteras.getRoads()) {
				road.advance(time);
			}
	
			onAdvanceEnd(mapa_carreteras, event_list, time);
		}
		catch (Exception e){
			onError(e.getMessage());
		}
		
	}

	private void onError(String message) {
		// TODO Auto-generated method stub
		
	}

	private void onAdvanceEnd(RoadMap mapa_carreteras2, List<Event> event_list2, int time2) {
		// TODO Auto-generated method stub
		
	}

	private void onAdvanceStart(RoadMap mapa_carreteras2, List<Event> event_list2, int time2) {
		// TODO Auto-generated method stub
		
	}

	public void reset() {
		mapa_carreteras.reset();
		event_list.clear();
		time = 0;
		onReset(mapa_carreteras, event_list, time);
	}

	private void onReset(RoadMap mapa_carreteras2, List<Event> event_list2, int time2) {
		// TODO Auto-generated method stub
		
	}
	public int getTime() {
		return this.time;
	}
	public JSONObject report() {
		JSONObject r = new JSONObject();
		r.put("time", (int)time - 1);
		r.put("state", mapa_carreteras.report());
		return r;
	}

	/*
	*
	*	OBSERVER STUFF
	*
	*/

	@Override
	public void addObserver(TrafficSimObserver o) {

		this.observers.add(o);

	}

	@Override
	public void removeObserver(TrafficSimObserver o) {
		
		this.observers.remove(o);

	}
	/*
	 * 
	 * 
	 *	GETTERS 
	 * 
	 *
	 */
	public RoadMap getRoadMap() {
		return this.mapa_carreteras; 
	}
}
