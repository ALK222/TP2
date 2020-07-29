package simulator.factories;

import org.json.JSONObject;

import simulator.model.LightSwitchingStrategy;
import simulator.model.RoundRobinStrategy;

public class RoundRobinStrategyBuilder extends Builder<LightSwitchingStrategy> {
	

	RoundRobinStrategyBuilder() {
		super("round_robin_lss");
	}

	@Override
	protected LightSwitchingStrategy createTheInstance(JSONObject data) {
		Integer time = (int) data.get("timeslot");
		if(time.equals(null)) time = 1;
		LightSwitchingStrategy ls = new RoundRobinStrategy(time);
		return ls;
	}

}
