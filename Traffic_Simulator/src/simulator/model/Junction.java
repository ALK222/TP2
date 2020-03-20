package simulator.model;

import java.util.ArrayList;
import java.util.LinkedList;
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
		if (!this.listVehicle.isEmpty()) {
			List<Vehicle> aux = this.deqEst.dequeue(this.listVehicle.get(greenLight));
			if (!aux.isEmpty()) {
				for (Vehicle i : aux) {
					if(!i.getStatus().equals(VehicleStatus.ARRIVED))
					i.moveToNextRoad();
				}
			}
		}
		int aux=  this.greenLight;
		this.greenLight = this.est.chooseNextGreen(listRoad, listVehicle, greenLight, ultSem, time);
		if(this.greenLight == aux)this.ultSem++;
		else this.ultSem=0;
	}

	void addIncommingRoad(Road r) throws JunctionException {
		if (!r.destination.equals(this))
			throw new JunctionException("This road isn't a entry road");
		this.listRoad.add(r);
		LinkedList<Vehicle> queue = new LinkedList<Vehicle>();
		this.listVehicle.add(queue);

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
		if (!r.origin.equals(this) && r.equals(exRoad()))
			throw new JunctionException("Bad road exit");
		this.listRoad.add(r);
		this.mapRoad.put(this, r);
		// HACIDO
	}

	void enter(Vehicle v) throws RoadException {// Hechito
		// completar
		int i = this.listRoad.indexOf(v.getCurrentRoad());
		this.listVehicle.get(i).add(v);
	}

	Road roadTo(Junction j) {
		Road aux = null;
		aux = this.mapRoad.get(j);
		return aux;
	}

	@Override
	public JSONObject report() {
		JSONObject information = new JSONObject();
		information.append("id", this._id);

		if (this.greenLight != -1)
			information.append("green", this.listRoad.get(greenLight));
		else
			information.append("green", "none");

		information.append("queues", listReport(listVehicle));// COMPLETAR CON EL JSON DE LAS COLAS

		return information;
	}

	private JSONObject listReport(List<List<Vehicle>> list) {
		JSONObject rep = new JSONObject();
		int i = 1;
		List<Vehicle> auxQ = list.iterator().next();
		Vehicle it2 = auxQ.iterator().next();
		while (auxQ.iterator().hasNext()) {
			JSONArray vQ = new JSONArray();
			while (auxQ.iterator().hasNext()) {
				vQ.put(i, it2.report());
				it2 = auxQ.iterator().next();
			}
			rep.append("road", listRoad.get(i)._id);
			rep.append("vehicles", vQ);
			auxQ.iterator().next();
			i++;
		}

		return rep;
	}

}
