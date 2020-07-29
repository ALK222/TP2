package simulator.model;

import java.util.List;
import java.util.ListIterator;

public class MostCrowdedStrategy implements LightSwitchingStrategy {
	int timeSlot;

	public MostCrowdedStrategy(int timeSlot) {
		this.timeSlot = timeSlot;
	}

	@Override
	public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, int lastSwitchingTime,
			int currTime) { /* PRESUPONGAMOS QUE FUNCIONA */

		if (roads.isEmpty())
			return -1;

		List<Vehicle> aux = qs.get(0);

		if (currGreen == -1) {

			for (int i = 0; i < qs.size(); ++i) {

				if (aux.size() < qs.get(i).size())
					aux = qs.get(i);

			}

			return qs.indexOf(aux);
		}

		if (currTime - lastSwitchingTime < this.timeSlot)
			return currGreen;

		ListIterator<List<Vehicle>> it = qs.listIterator();
		List<Vehicle> aux1 = qs.get((currGreen + 1) % roads.size());
		it.set(aux1);
		int j = currGreen + 1;
		while (j != currGreen) {
			List<Vehicle> aux2 = null;
			if (it.hasNext())
				aux2 = it.next();
			else {
				it.set(qs.get(0));
			}
			if (aux1.size() < it.next().size())
				aux1 = aux2;
		}
		return qs.indexOf(aux1);
	}

}
