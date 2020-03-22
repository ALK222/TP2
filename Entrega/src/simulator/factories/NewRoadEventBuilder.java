package simulator.factories;

import org.json.JSONException;
import org.json.JSONObject;

import exceptions.CoordException;
import exceptions.FactoryException;
import exceptions.JunctionException;
import exceptions.RoadException;
import exceptions.StrategyException;
import simulator.model.Event;

public abstract class NewRoadEventBuilder extends Builder<Event> {

	NewRoadEventBuilder(String type) {
		super(type);
	}

	@Override
	protected Event createTheInstance(JSONObject data)
			throws JSONException, StrategyException, CoordException, FactoryException, RoadException, JunctionException {
		if(this._type.equals("new_city_road")) return new NewCityRoadEventBuilder().createTheRoad(data);
		else if(this._type.equals("new_inter_city_road")) return new NewCityRoadEventBuilder().createTheRoad(data);
		return null;
	}
	
	protected abstract Event createTheRoad(JSONObject data) throws JSONException, RoadException, JunctionException;

}
