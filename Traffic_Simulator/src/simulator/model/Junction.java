package simulator.model;

import org.json.JSONObject;

import exceptions.RoadException;

public class Junction extends SimulatedObject{
	int semaforo;
	
	Junction(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	@Override
	void advance(int time) throws RoadException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public JSONObject report() {
		// TODO Auto-generated method stub
		return null;
	}

	public void addBegining(Road road) {
		// TODO Auto-generated method stub
		
	}

	public void addExit(Road road) {
		// TODO Auto-generated method stub
		
	}

}
