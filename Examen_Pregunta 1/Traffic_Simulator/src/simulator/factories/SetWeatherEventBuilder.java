package simulator.factories;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import exceptions.CoordException;
import exceptions.FactoryException;
import exceptions.JunctionException;
import exceptions.RoadException;
import exceptions.StrategyException;
import exceptions.VehicleException;
import exceptions.WeatherException;
import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.SetWeatherEvent;
import simulator.model.Weather;

public class SetWeatherEventBuilder extends Builder<Event> {

	public SetWeatherEventBuilder() {
		super("set_weather");
	}

	@Override
	protected Event createTheInstance(JSONObject data) throws JSONException, StrategyException, CoordException,
			FactoryException, RoadException, JunctionException, VehicleException, WeatherException {
			JSONArray a = data.getJSONArray("info");
			ArrayList<Pair<String, Weather>> ws = new ArrayList<Pair<String, Weather>>();
			for (int i = 0; i < a.length(); ++i) {
				JSONObject dummy = a.getJSONObject(i);
				ws.add(new Pair<String, Weather>(dummy.getString("road"), Weather.valueOf(dummy.getString("weather"))));
			}
			return new SetWeatherEvent(data.getInt("time"), ws);
	}

}
