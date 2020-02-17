package simulator.model;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MostCrowdedStrategy implements LightSwitchingStrategy {
	int timeSlot;
	MostCrowdedStrategy(int timeSlot){
		this.timeSlot = timeSlot;
	}
	
	
		
	
	@Override
	public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, int lastSwitchingTime,
			int currTime) { /*PRESUPONGAMOS QUE FUNCIONA*/
		int size=0;
		int i=0;
		if(roads.isEmpty())return -1;
		if(currGreen==-1) {
			i = 0;
			for(List<Vehicle> aux : qs ) {
				if(aux.size()>size) {
					size = aux.size();
					i = qs.indexOf(aux);
				}
			}
			
			 return i;
		}
		if(currTime-lastSwitchingTime < this.timeSlot)return currGreen;
		
		ListIterator<List<Vehicle>> it = qs.listIterator();
		List<Vehicle> aux = qs.get(currGreen+1%roads.size());
		it.set(aux);
		
		i = currGreen+1;
		while(i != currGreen) {//Si el iterador llega al final de la lista, vuelve al inicio
			if(aux.size() > size) {
				size = aux.size();
			}
			if(!it.hasNext()) {
				aux = qs.get(0);
				it.set(aux);
				i=0;
			}else {
				aux=it.next();
				i++;
			}
		} 
		return size;
	}

}
