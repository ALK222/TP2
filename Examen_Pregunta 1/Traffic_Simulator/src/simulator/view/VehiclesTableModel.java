package simulator.view;

import java.util.List;

import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;

import exceptions.VehicleException;
import simulator.control.Controller;
import simulator.misc.SortedArrayList;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;

public class VehiclesTableModel extends AbstractTableModel implements TrafficSimObserver {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String[] labels = { "id", "Location", "Itinerary", "CO2 class", "Max. Speed", "Speed", "Total CO2",
			"Distance" };
	private List<Vehicle> _vehicles;

	public VehiclesTableModel(Controller _ctrl) {

		_vehicles = new SortedArrayList<Vehicle>();
		_ctrl.addObserver(this);// Si a�ades esto como observador(que creo que no lo es) salta error al meter
								// tambien el RoadsTableModel,
		// porque no se pueden comparar, no son iguales.
	}

	public String getColumnName(int c) {

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
		switch (col) {
		case 0:
			rt = v.getId().toString();
			break;

		case 1:
			rt = v.getCurrentRoad().toString() + ": "+v.getLocation().toString();
			break;

		case 2:
			rt = v.getItinerary();

			break;

		case 3:
			rt = v.getContamination().toString();

			break;

		case 4:
			rt = v.getMaxSpeed().toString();

			break;
		case 5:
			rt= v.getSpeed().toString();
			break;

		case 6:
			rt = v.getTotalCo2().toString();

			break;
		case 7:
			rt = v.getDistance().toString();
			break;
		}

		return rt;
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				_vehicles = map.getVehicles();
				fireTableStructureChanged();
				fireTableDataChanged();
			}
		});

	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				_vehicles = map.getVehicles();
				fireTableStructureChanged();
				fireTableDataChanged();
			}
		});

	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				_vehicles = map.getVehicles();
				fireTableStructureChanged();
				fireTableDataChanged();
			}
		});

	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				_vehicles = map.getVehicles();
				fireTableStructureChanged();
				fireTableDataChanged();
			}
		});

	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				_vehicles = map.getVehicles();
				fireTableStructureChanged();
				fireTableDataChanged();
			}
		});

	}

	@Override
	public void onError(String err) throws VehicleException {
		throw new VehicleException(err);
	}

}
