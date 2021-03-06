package simulator.control;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import exceptions.CoordException;
import exceptions.FactoryException;
import exceptions.JunctionException;
import exceptions.RoadException;
import exceptions.SimulatorException;
import exceptions.StrategyException;
import exceptions.VehicleException;
import exceptions.WeatherException;
import simulator.factories.Factory;
import simulator.model.Event;
import simulator.model.Observable;
import simulator.model.TrafficSimObserver;
import simulator.model.TrafficSimulator;

public class Controller implements Observable<TrafficSimObserver>{

	private TrafficSimulator traffic_simulator;

	private Factory<Event> events_factory;

	public Controller(TrafficSimulator sim, Factory<Event> eventsFactory) throws SimulatorException {
		if (sim.equals(null) || eventsFactory.equals(null)) {
			throw new SimulatorException("los valores no son correctos");
		}
		this.traffic_simulator = sim;
		this.events_factory = eventsFactory;
	}

	public void loadEvents(InputStream in) throws JSONException, StrategyException, CoordException, FactoryException,
			RoadException, JunctionException, VehicleException, WeatherException {
		JSONObject jo = new JSONObject(new JSONTokener(in));
		JSONArray ja = jo.getJSONArray("events");
		for (int i = 0; i < ja.length(); ++i) {
			traffic_simulator.addEvent(events_factory.createInstance(ja.getJSONObject(i)));
		}
	}

	public void run(int n, OutputStream out) throws RoadException, VehicleException, JunctionException, IOException {
		PrintStream p = new PrintStream(out);
		p.println("{");
		p.println("  \"states\": [");
		for(int i = 1; i <= n; ++i){
			traffic_simulator.advance();
				p.print(traffic_simulator.report());
				if(i != n){
					p.println(",");
				}	
			
		}
		p.println("\n]");
		p.println("}");
	}

	public void run(int n) throws RoadException, VehicleException, JunctionException {

		for(int i = 0; i < n; i++){
			traffic_simulator.advance();
		}
	}

	public void reset(){
		traffic_simulator.reset();
	}

	@Override
	public void addObserver(TrafficSimObserver o) {
		
		this.traffic_simulator.addObserver(o);

	}

	@Override
	public void removeObserver(TrafficSimObserver o) {
		
		this.traffic_simulator.removeObserver(o);

	}

	public void addEvents(Event e){
		//Modifico esto porque le pasas Event e no InputStream i
		this.traffic_simulator.addEvent(e);

	}
	/*
	 * 
	 * 
	 *	GETTERS 
	 * 
	 *
	 */
	
}
