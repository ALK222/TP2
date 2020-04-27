package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;

public class  VehiclesTableModel extends AbstractTableModel implements TrafficSimObserver{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String[] labels = {"id", "Location", "CO2 class", "Max. Speed", "Total CO2", "Distance"};
	private List<Vehicle> _vehicles;
	
	public VehiclesTableModel(Controller _ctrl) {
		
		_vehicles = new ArrayList<Vehicle>();
		_ctrl.addObserver(this);
	}

	public String getColumnName(int c){

		return labels[c];
	}
	
	@Override
	public int getColumnCount() {

		return labels.length;
	}

	@Override
	public int getRowCount() {
		
		return _vehicles.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		String rt = "";
		Vehicle v = _vehicles.get(row);
		switch(col){
			case 0:
				rt = v.getId().toString();
			break;

			case 1:
				rt = v.getLocation().toString();
			break;

			case 2:
				rt = v.getContamination().toString();
			break;

			case 3:
				rt = v.getMaxSpeed().toString();
			break;

			case 4:
				rt = v.getTotalCo2().toString();
			break;

			case 5:
				rt = v.getDistance().toString();
			break;			
		}

		return rt;
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		SwingUtilities.invokeLater(new Runnable(){
		
			@Override
			public void run() {
				_vehicles = map.getVehicles();
				fireTableStructureChanged();
			}
		});
		
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		SwingUtilities.invokeLater(new Runnable(){
		
			@Override
			public void run() {
				_vehicles = map.getVehicles();
				fireTableStructureChanged();
			}
		});
		
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {

		SwingUtilities.invokeLater(new Runnable(){
		
			@Override
			public void run() {
				_vehicles = map.getVehicles();
				fireTableStructureChanged();
			}
		});
		
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		
		SwingUtilities.invokeLater(new Runnable(){
		
			@Override
			public void run() {
				_vehicles = map.getVehicles();
				fireTableStructureChanged();
			}
		});
		
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		
		SwingUtilities.invokeLater(new Runnable(){
		
			@Override
			public void run() {
				_vehicles = map.getVehicles();
				fireTableStructureChanged();
			}
		});
		
	}

	@Override
	public void onError(String err) {		
	}


}
