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
import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.NewSetContClassEvent;

public class SetContClassEventBuilder extends Builder<Event> {

	public SetContClassEventBuilder() {
		super("set_cont_class");
	}

	@Override
	protected Event createTheInstance(JSONObject data) throws JSONException, StrategyException, CoordException,
			FactoryException, RoadException, JunctionException, VehicleException {
		JSONArray a = data.getJSONArray("info");
		ArrayList<Pair<String, Integer>> ws = new ArrayList<Pair<String, Integer>>();
		for (int i = 0; i < a.length(); ++i) {
			JSONObject dummy = a.getJSONObject(i);
			ws.add(new Pair<String, Integer>(dummy.getString("vehicle"), dummy.getInt("class")));
		}
		return new NewSetContClassEvent(data.getInt("time"), ws);
	}

}
