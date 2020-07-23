package simulator.model;

import exceptions.CoordException;
import exceptions.JunctionException;
import exceptions.StrategyException;

public class NewJunctionEvent extends Event {

	private Junction junc;

	public NewJunctionEvent(int time, String id, LightSwitchingStrategy lsStrategy, DequeuingStrategy dqStrategy,
			int xCoor, int yCoor) throws StrategyException, CoordException {
		super(time);
		this.junc = new Junction(id, lsStrategy, dqStrategy, xCoor, yCoor);
	}

	@Override
	void execute(RoadMap map) throws JunctionException {
		map.addJunction(this.junc);

	}

	@Override
	public String toString(){
		return "New Junction '" + this.junc._id + "'";
	}

}
