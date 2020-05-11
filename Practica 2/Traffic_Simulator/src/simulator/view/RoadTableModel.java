package simulator.view;

import java.util.List;

import javax.swing.SwingUtilities;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import simulator.control.Controller;
import simulator.misc.SortedArrayList;
import simulator.model.Event;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class RoadTableModel extends AbstractTableModel implements TrafficSimObserver{

	private static final long serialVersionUID = 1L;
	private List<Road> _Roads;
	private String[] labels = { "id", "Lenght", "Weather Conditions", "Max speed", "Actual speed", "CO2 Counter",
			"CO2 Limit" };

	public RoadTableModel(Controller _ctrl) {
	_Roads = new SortedArrayList<Road>();
		
		_ctrl.addObserver(this);

	}

	public String getColumnName(int i) {
		return labels[i];
	}

	public int getColumnCount() {
		return labels.length;
	}

	@Override
	public int getRowCount() {
		return this._Roads.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		String o = "";
		Road aux = this._Roads.get(col);
		switch (row) {
		case 0:
			o = aux.getId();
			break;
		case 1:
			o = aux.getLenght().toString();
			break;
		case 2:
			o = aux.getWeather();
			break;
		case 3:
			o = aux.getMaxSpeed().toString();
			break;
		case 4:
			o = aux.getCurrSpeed().toString();
			break;
		case 5:
			o = aux.getTotalCO2().toString();
			break;
		case 6:
			o = aux.getCO2Limit().toString();
			break;

		}
		return o;
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		SwingUtilities.invokeLater(new Runnable(){
			
			@Override
			public void run() {
				_Roads = map.getRoads();
				fireTableStructureChanged();
				fireTableDataChanged();
			}
		});

	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
SwingUtilities.invokeLater(new Runnable(){
			
			@Override
			public void run() {
				_Roads = map.getRoads();
				fireTableStructureChanged();
				fireTableDataChanged();
			}
		});


	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
SwingUtilities.invokeLater(new Runnable(){
			
			@Override
			public void run() {
				_Roads = map.getRoads();
				fireTableStructureChanged();
				fireTableDataChanged();
			}
		});


	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
SwingUtilities.invokeLater(new Runnable(){
			
			@Override
			public void run() {
				_Roads = map.getRoads();
				fireTableStructureChanged();
				fireTableDataChanged();
			}
		});


	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
SwingUtilities.invokeLater(new Runnable(){
			
			@Override
			public void run() {
				_Roads = map.getRoads();
				fireTableStructureChanged();
				fireTableDataChanged();
			}
		});


	}

	@Override
	public void onError(String err) {
		
	}


}
