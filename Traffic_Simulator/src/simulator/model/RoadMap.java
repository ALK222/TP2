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
		JSONArray junct = new JSONArray();
		for(Junction j : listJunc){
			junct.put(j.report());
		}
		inform.append("junctions", junct);
		JSONArray roads = new JSONArray();
		for(Road r : listRoad){
			roads.put(r.report());
		}
		inform.append("roads", roads);
		JSONArray vecs = new JSONArray();
		for(Vehicle v : listVec){
			vecs.put(v.report());
		}
		inform.append("vehicles", vecs);
		return inform;
		//ESTO NO VA A FUNCAR
	}
}
