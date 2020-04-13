package simulator.model;

import java.util.ArrayList;
import java.util.List;

import exceptions.VehicleException;
import simulator.misc.Pair;

public class NewSetContClassEvent extends Event {
	
	private List<Pair<String, Integer>> p;

	public NewSetContClassEvent(int time, List<Pair<String, Integer>> cs) throws VehicleException {
		super(time);
		if(cs.equals(null)) {
			throw new VehicleException("La lista de vehiculos est� vacia");
		}
		this.p = new ArrayList<Pair<String, Integer>>();
		this.p = cs;
	}
	
	
	@Override
	void execute(RoadMap map) throws VehicleException {
		for(Pair<String, Integer> vehicle : this.p) {
			if(map.getVehicle(vehicle.getFirst()).equals(null)) throw new VehicleException("El vehiculo no se encuentra en el mapa");
			map.getVehicle(vehicle.getFirst()).setContamination(vehicle.getSecond());
		}
	}
	
	@Override
	public String toString(){
		return "Change CO2 class: " + this.p.toString();
	}
}