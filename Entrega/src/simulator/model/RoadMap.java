package simulator.model;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONObject;

import exceptions.JunctionException;
import exceptions.RoadException;
import exceptions.VehicleException;

public class RoadMap{
	
	//ATRIBUTTES
	
	protected List<Junction> listJunc;
	protected List<Road> listRoad;
	protected List<Vehicle> listVec;
	protected Map<String, Junction> juncMap;
	protected Map<String, Road> roadMap;
	protected Map<String, Vehicle> vecMap;
	
	protected RoadMap() {
		this.listJunc = new ArrayList<Junction>();
		this.listRoad = new ArrayList<Road>();
		this.listVec = new ArrayList<Vehicle>();
		this.juncMap = new TreeMap<String, Junction>();
		this.roadMap = new TreeMap<String, Road>();
		this.vecMap = new TreeMap<String, Vehicle>();
	}
	
	protected void addJunction(Junction j) throws JunctionException {
		if(this.juncMap.containsKey(j.getId())) throw new JunctionException("This junction already exists");
		this.listJunc.add(j);
		this.juncMap.put(j.getId(), j);
	}
	
	protected void addRoad(Road r) throws RoadException {
		if(this.roadMap.containsKey(r.getId())) throw new RoadException("This road already exists");
		this.listRoad.add(r);
		this.roadMap.put(r.getId(), r);
	}
	
	protected void addVehicle(Vehicle v) throws VehicleException {
		if(this.vecMap.containsKey(v.getId())) throw new VehicleException("This vehicle already exists");
		this.listVec.add(v);
		this.vecMap.put(v.getId(), v);
	}
	
	
	public Junction getJunction(String id) {
		return juncMap.get(id);
	}
	
	public Road getRoad(String id) {
		return roadMap.get(id);
	}
	
	public Vehicle getVehicle(String id) {
		return vecMap.get(id);
	}
	
	public List<Junction> getJunctions() {
		return Collections.unmodifiableList(listJunc);
	}
	
	public List<Road> getRoads() {
		return Collections.unmodifiableList(listRoad);
	}
	
	public List<Vehicle> getVehicles() {
		return Collections.unmodifiableList(listVec);
	}
	
	protected void reset() {
		this.listJunc = new ArrayList<Junction>();
		this.listRoad = new ArrayList<Road>();
		this.listVec = new ArrayList<Vehicle>();
		this.juncMap = new TreeMap<String, Junction>();
		this.roadMap = new TreeMap<String, Road>();
		this.vecMap = new TreeMap<String, Vehicle>();
	}
	
	public JSONObject report() {
		JSONObject inform = new JSONObject();

		JSONArray roadRep = new JSONArray();
		JSONArray vecRep = new JSONArray();
		JSONArray juncRep = new JSONArray();

		auxReport(listRoad, roadRep);
		auxReport(listVec, vecRep);
		auxReport(listJunc, juncRep);

		inform.put("roads", roadRep);
		inform.put("vehicles", vecRep);
		inform.put("junctions", juncRep);
	
		return inform;
	}

	private <T extends SimulatedObject> void auxReport(List<T> auxList, JSONArray aux) {
		
		for(T o : auxList){
			aux.put(o.report());
		}
	}
}
