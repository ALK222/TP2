package simulator.model;

import java.util.ArrayList;
import java.util.List;

import exceptions.RoadException;
import exceptions.WeatherException;
import simulator.misc.Pair;

public class SetWeatherEvent extends Event {
	
	private List<Pair<String, Weather>> p;

	public SetWeatherEvent(int time, List<Pair<String,Weather>> ws) throws WeatherException {
		super(time);
		if(ws.equals(null)) {
			throw new WeatherException("La lista de weathers esta vacia");
		}
		this.p = new ArrayList<Pair<String, Weather>>();
		this.p = ws;
	}
	
	
	@Override
	void execute(RoadMap map) throws RoadException {
		for(Pair<String, Weather> road : this.p) {
			if(map.getRoad(road.getFirst()).equals(null)) throw new RoadException("La carretera no se encuentra en el mapa");
			map.getRoad(road.getFirst()).setWeather(road.getSecond());
		}
	}

}
