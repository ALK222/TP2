package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import exceptions.CoordException;
import exceptions.FactoryException;
import exceptions.JunctionException;
import exceptions.RoadException;
import exceptions.StrategyException;
import exceptions.VehicleException;
import simulator.model.Event;
import simulator.model.NewVehicleEvent;

public class NewVehicleEventBuilder extends Builder<Event> {

	public NewVehicleEventBuilder() {
		super("new_vehicle");
	}

	@Override
	protected Event createTheInstance(JSONObject data) throws JSONException, StrategyException, CoordException,
			FactoryException, RoadException, JunctionException, VehicleException {
		List<String> i = new ArrayList<String>();
		JSONArray k = data.getJSONArray("itinerary");
		for(int j = 0; j < k.length(); ++j) {
			i.add(k.getString(j));
		}
		return new NewVehicleEvent(data.getInt("time"), data.getString("id"),
				data.getInt("maxspeed"), data.getInt("class"), i);
	}

}
