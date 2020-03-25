package simulator.factories;

import org.json.JSONException;
import org.json.JSONObject;

import exceptions.CoordException;
import exceptions.FactoryException;
import exceptions.JunctionException;
import exceptions.RoadException;
import exceptions.StrategyException;
import exceptions.VehicleException;
import exceptions.WeatherException;

public interface Factory<T> {
	public T createInstance(JSONObject info) throws JSONException, StrategyException, CoordException, FactoryException, RoadException, JunctionException, VehicleException, WeatherException;
}
