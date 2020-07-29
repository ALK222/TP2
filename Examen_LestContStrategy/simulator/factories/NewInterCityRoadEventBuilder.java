package simulator.factories;

import org.json.JSONException;
import org.json.JSONObject;

import exceptions.JunctionException;
import exceptions.RoadException;
import simulator.model.Event;
import simulator.model.NewInterCityRoad;
import simulator.model.Weather;

public class NewInterCityRoadEventBuilder extends NewRoadEventBuilder {

	public NewInterCityRoadEventBuilder() {
		super("new_inter_city_road");
	}

	@Override
	protected Event createTheRoad(JSONObject data) throws JSONException, RoadException, JunctionException {
		return new NewInterCityRoad(data.getInt("time"),data.getString("id"),data.getString("src"), 
				data.getString("dest"), data.getInt("length"), data.getInt("co2limit"), data.getInt("maxspeed"), 
				Weather.valueOf(data.getString("weather")));
	}


}