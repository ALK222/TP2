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
	List<Road> listRoad;// Carreteras entrantes
	Map<Junction, Road> mapRoad;// Mapa de carreteras salientes
	List<List<Vehicle>> listVehicle;
	Map<Road, List<Vehicle>> mapaColas; // mapa carretera-cola
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
		this.ultSem=1;
	}

	@Override
	void advance(int time) throws RoadException, VehicleException {
		if (!this.listVehicle.isEmpty()) {
			List<Vehicle> aux = this.deqEst.dequeue(this.listVehicle.get(greenLight));
			for (Vehicle i : aux) {
				i.moveToNextRoad();
				if (i.getStatus().equals(VehicleStatus.TRAVELING) || i.getStatus().equals(VehicleStatus.ARRIVED)) {
					this.listVehicle.get(greenLight).remove(i);
				}
			}
			// this.listVehicle.removeAll(aux);
		}

		// for(int i = 0; i <= aux.size()-1;i++) {
		// aux.get(i).moveToNextRoad();
		// }

		int green = this.greenLight;
		this.greenLight = this.est.chooseNextGreen(listRoad, listVehicle, greenLight, ultSem, time);
		if (this.greenLight != green)
			this.ultSem=time;
		
	}

	void addIncommingRoad(Road r) throws JunctionException {
		if (!r.destination.equals(this))
			throw new JunctionException("This road isn't a entry road");
		this.listRoad.add(r);
		List<Vehicle> queue = new ArrayList<Vehicle>();
		this.listVehicle.add(queue);
		this.mapaColas.put(r, queue);

		// this.mapRoad.put(this, r);
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
		/*
		 * a�ade el par (j,r) al mapa de carreteras salientes, donde j es el cruce
		 * destino de la carretera r. Tienes que comprobar que ninguna otra carretera va
		 * desde this al cruce j y, que la carretera r, es realmente una carretera
		 * saliente al cruce actual. En otro caso debes lanzar una excepci�n
		 */
		if (!r.origin.equals(this) && r.equals(exRoad()))
			throw new JunctionException("Bad road exit");
		// if(!this.mapRoad.get(this).equals(null))throw new JunctionException("Bad road
		// exit");
		// this.listRoad.add(r);
		this.mapRoad.put(this, r);
		// List<Vehicle> queue = r.vehicles;
		// this.listVehicle.add(queue);
		// this.mapaColas.put(r, r.vehicles);
		// HACIDO
	}

	void enter(Vehicle v) throws RoadException {// Hechito
		// completar
		this.mapaColas.get(v.getCurrentRoad()).add(v);
	}

	Road roadTo(Junction j) {

		return this.mapRoad.get(j);
	}

	@Override
	public JSONObject report() {
		JSONObject information = new JSONObject();
		information.put("id", (String)this._id);

		if (this.greenLight != -1)
			information.put("green", (String) this.listRoad.get(greenLight)._id);
		else
			information.put("green", "none");

		information.put("queues", listReport());// COMPLETAR CON EL JSON DE LAS COLAS

		return information;
	}

	private JSONArray listReport() {
	JSONArray aux = new JSONArray();

		for (Road r : listRoad) {
			JSONObject aux2 = new JSONObject();
			JSONArray auxVec = new JSONArray();
			String rId = r._id;
			aux2.put("road",(String) rId);
			for (Vehicle v : mapaColas.get(r)) {
				auxVec.put((String)v._id);
			}
			aux2.put("vehicles", auxVec);
			aux.put(aux2);
		}
		
		return aux;
	}

}
