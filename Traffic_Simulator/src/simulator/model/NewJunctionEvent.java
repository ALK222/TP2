package simulator.model;

import exceptions.CoordException;
import exceptions.StrategyException;

public class NewJunctionEvent extends Event {
	
	private Junction junc;

	NewJunctionEvent(int time, String id, LightSwitchingStrategy lsStrategy, DequeuingStrategy dqStrategy, int xCoor, int yCoor) throws StrategyException, CoordException {
		super(time);
		this.junc = new Junction(id,lsStrategy, dqStrategy, xCoor, yCoor);
	}

	@Override
	void execute(RoadMap map) {
		map.juncMap.put(this.junc.getId(), junc);

	}

}
