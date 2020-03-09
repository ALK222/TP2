package simulator.factories;

import org.json.JSONException;
import org.json.JSONObject;

import exceptions.JunctionException;
import exceptions.RoadException;
import simulator.model.Event;
import simulator.model.NewCityRoad;
import simulator.model.Weather;

public class NewCityRoadEventBuilder extends NewRoadEventBuilder {

	NewCityRoadEventBuilder() {
		super("new_city_road");
	}

	@Override
	protected Event createTheRoad(JSONObject data) throws JSONException, RoadException, JunctionException {
		return new NewCityRoad(data.getInt("time"),data.getString("id"),data.getString("src"), 
				data.getString("dest"), data.getInt("length"), data.getInt("co2limit"), data.getInt("maxspeed"), 
				Weather.valueOf(data.getString("weather")));
	}


}
