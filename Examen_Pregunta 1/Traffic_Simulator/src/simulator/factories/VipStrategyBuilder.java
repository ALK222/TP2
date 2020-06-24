package simulator.factories;

import org.json.JSONObject;

import exceptions.StrategyException;
import simulator.model.DequeuingStrategy;
import simulator.model.VipStrategy;

public class VipStrategyBuilder extends Builder<DequeuingStrategy> {

    VipStrategyBuilder() {
        super("vip_dqs");
    }

    @Override
    protected DequeuingStrategy createTheInstance(JSONObject data) throws StrategyException {
        Integer limit = (int) data.get("limit");
        if (limit.equals(null))
            limit = 1;

        String viptag = (String) data.get("viptag");
        if (viptag.equals(null)) {
            throw new StrategyException("viptag not found for VipStrategy");
        }
        DequeuingStrategy dq = new VipStrategy(viptag, limit);
        return dq;
    }

}