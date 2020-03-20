package simulator.model;

import java.util.List;


public class MoveFirstStrategy implements DequeuingStrategy {

	@Override
	public List<Vehicle> dequeue(List<Vehicle> q) {
		List<Vehicle> aux = q;
		aux.add(q.get(0));
		return aux;
	}

}
