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
import simulator.model.DequeuingStrategy;
import simulator.model.Event;
import simulator.model.LightSwitchingStrategy;
import simulator.model.NewJunctionEvent;

public class NewJunctionEventBuilder extends Builder<Event> {

	public NewJunctionEventBuilder() {
		super("new_junction");

	}

	@Override
	protected Event createTheInstance(JSONObject data) throws JSONException, StrategyException, CoordException,
			FactoryException, RoadException, JunctionException, VehicleException, WeatherException {

		// ls comprovation
		String lss = data.getJSONObject("ls_strategy").getString("type");
		LightSwitchingStrategy ls;
		if (lss.equals("round_robin_lss"))
			ls = new RoundRobinStrategyBuilder().createInstance(data.getJSONObject("ls_strategy"));
		else if (lss.equals("most_crowded_lss"))
			ls = new MostCrowdedStrategyBuilder().createInstance(data.getJSONObject("ls_strategy"));
		else
			throw new FactoryException("Incorrect lss on new Junction");

		// ms comprovation
		String dq = data.getJSONObject("dq_strategy").getString("type");
		DequeuingStrategy dqq;
		if (dq.equals("move_first_dqs"))
			dqq = new MoveFirstStrategyBuilder().createInstance(data.getJSONObject("dq_strategy"));
		else if (dq.equals("most_all_dqs"))
			dqq = new MoveAllStrategyBuilder().createInstance(data.getJSONObject("dq_strategy"));
		else if (dq.equals("vip_dqs"))
			dqq = new VipStrategyBuilder().createInstance(data.getJSONObject("dq_strategy"));
		else
			throw new FactoryException("Incorrect dqs on new Junction");

		return new NewJunctionEvent((int) data.get("time"), data.getString("id"), ls, dqq,
				(int) data.getJSONArray("coor").getInt(0), (int) data.getJSONArray("coor").getInt(1));
	}

}
