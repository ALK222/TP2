package simulator.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONObject;

import exceptions.CoordException;
import exceptions.JunctionException;
import exceptions.RoadException;
import exceptions.StrategyException;
import exceptions.VehicleException;

public class Junction extends SimulatedObject {
	List<Road> listRoad;
	Map<Junction, Road> mapRoad;
	List<List<Vehicle>> listVehicle;
	Map<Road, List<Vehicle>> mapaColas; //mapa carretera-cola
	int greenLight; // Que semaforo est� en verde -1 todos en rojo
	int ultSem; // El paso en el cual el indice de semaforo en verde ha cambiado de valor
	LightSwitchingStrategy est;
	DequeuingStrategy deqEst;
	int _x;
	int _y;

	Junction(String id, LightSwitchingStrategy lsStrategy, DequeuingStrategy dqStrategy, int xCoord, int yCoord)
			throws StrategyException, CoordException {
		super(id);
		this.listRoad = new ArrayList<Road>();
		this.mapRoad = new TreeMap<Junction, Road>();
		this.listVehicle = new ArrayList<List<Vehicle>>();
		this.mapaColas = new TreeMap<Road, List<Vehicle>>();
		if (lsStrategy == null) {
			throw new StrategyException("LighSwitchinStrategy is NULL");
		}
		if (dqStrategy == null) {
			throw new StrategyException("DequeuingStrategy is NULL");
		}
		this.deqEst = dqStrategy;
		this.est = lsStrategy;
		if (xCoord < 0 || yCoord < 0)
			throw new CoordException("The coordenades are negatives");
		this._x = xCoord;
		this._y = yCoord;
	}

	@Override
	void advance(int time) throws RoadException, VehicleException {
		List<Vehicle> aux = this.deqEst.dequeue(this.listVehicle.get(greenLight));
		for (Vehicle i : aux) {
			i.moveToNextRoad();
		}
	
		int green=  this.greenLight;
		this.greenLight = this.est.chooseNextGreen(listRoad, listVehicle, greenLight, ultSem, time);
		if(this.greenLight == green) this.ultSem++;
		else this.ultSem=0;
	}

	void addIncommingRoad(Road r) throws JunctionException {
		if(!r.origin.equals(this)) throw new JunctionException("This road isn't a entry road");
		this.listRoad.add(r);
		List<Vehicle> queue = r.vehicles;
		this.listVehicle.add(queue);
		this.mapaColas.put(r, r.vehicles);
		
		this.mapRoad.put(this, r);
	}

	Road exRoad() {// Busca si hay una carretera saliente
		Road aux = null;
		int i = 0;
		for (Road r : listRoad) {
			r = listRoad.get(i);
			if (r.origin.equals(this))
				aux = r;
		}
		return aux;
	}

	void addOutGoingRoad(Road r) throws JunctionException {
		if(!r.destination.equals(this) && r.equals(exRoad()))throw new JunctionException("Bad road exit");
		this.listRoad.add(r);
		List<Vehicle> queue = r.vehicles;
		this.listVehicle.add(queue);
		this.mapaColas.put(r, r.vehicles);
		//HACIDO
	}
	void enter(Vehicle v) throws RoadException {//Hechito
		//completar
		this.mapaColas.get(v.getCurrentRoad()).add(v);
	}

	Road roadTo(Junction j) {
		
		for(Road aux : listRoad){
			if(j.equals(aux.destination)) return aux;
		}
		return null;
	}

	@Override
	public JSONObject report() {
		JSONObject information = new JSONObject();
		information.append("id", this._id);

		if (this.greenLight != -1)
			information.append("green", this.listRoad.get(greenLight));
		else
			information.append("green", "none");

		information.append("queues", listReport());// COMPLETAR CON EL JSON DE LAS COLAS

		return information;
	}

	private JSONArray listReport(){
		JSONArray aux = new JSONArray();
		JSONObject aux2 = new JSONObject();
		JSONArray auxVec = new JSONArray();
		for(Road r : listRoad){
			aux2.put("road", r._id);
			for(Vehicle v : r.vehicles){
				auxVec.put(v._id);
			}
			aux2.put("vehicles", auxVec);
			aux.put(aux2);
		}

		return aux;
	}

}
