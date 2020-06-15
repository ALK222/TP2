package simulator.factories;

import org.json.JSONObject;

import simulator.model.LightSwitchingStrategy;
import simulator.model.MostCrowdedStrategy;

public class MostCrowdedStrategyBuilder extends Builder<LightSwitchingStrategy> {

	MostCrowdedStrategyBuilder() {
		super("most_crowded_lss");
		
	}

	@Override
	protected LightSwitchingStrategy createTheInstance(JSONObject data) {
		Integer time = (int) data.get("timeslot");
		if(time.equals(null)) time = 1;
		LightSwitchingStrategy ls = new MostCrowdedStrategy(time);
		return ls;
	}

}
