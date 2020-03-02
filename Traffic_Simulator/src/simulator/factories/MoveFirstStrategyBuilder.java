package simulator.factories;

import org.json.JSONObject;

import simulator.model.DequeuingStrategy;
import simulator.model.MoveFirstStrategy;

public class MoveFirstStrategyBuilder extends Builder<DequeuingStrategy> {

	MoveFirstStrategyBuilder() {
		super("move_first_dqs");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected DequeuingStrategy createTheInstance(JSONObject data) {
		return new MoveFirstStrategy();
	}

}
