package simulator.factories;

import org.json.JSONObject;

import exceptions.StrategyException;
import simulator.model.DequeuingStrategy;
import simulator.model.LessContStrategy;

public class LessContaminationStrategyBuilder extends Builder<DequeuingStrategy> {

    LessContaminationStrategyBuilder() {
        super("less_cont_dqs");
    }

    @Override
    protected DequeuingStrategy createTheInstance(JSONObject data) throws StrategyException {
        int limit = data.has("limit") ? data.getInt("limit") : 1;
        return new LessContStrategy(limit);
    }

}
