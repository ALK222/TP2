package simulator.view;

import java.util.List;

import javax.swing.SwingUtilities;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import simulator.control.Controller;
import simulator.misc.SortedArrayList;
import simulator.model.Event;
import simulator.model.Junction;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class JunctionsTableModel extends AbstractTableModel implements TrafficSimObserver {
	private static final long serialVersionUID = 1L;
	private List<Junction> _Junction;
	private String[] labels = { "Id", "Road Green", "Queues" };


		public JunctionsTableModel(Controller _ctrl) {
			this._Junction = new SortedArrayList<Junction>();
		}
	

	@Override
	public int getColumnCount() {
		return labels.length;
	}

	@Override
	public String getColumnName(int c) {
		return labels[c];
	}

	@Override
	public int getRowCount() {
		return _Junction.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		String o = "";
		Junction aux = this._Junction.get(col);
		switch (row) {
		case 0:
			o = aux.getId();
			break;
		case 1:
			int index =aux.getGreenLightIndex();
			if(index != -1)
			o=  aux.getInRoads().get(index).getId();
			else o="NONE";
			
			break;
		case 2:
			o = aux.getQueue().toString();
			break;
		}
		return o;
	}

	@Override
	public boolean isCellEditable(int arg0, int arg1) {
		return false;
	}


	


	

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		SwingUtilities.invokeLater(new Runnable(){
			
			@Override
			public void run() {
				_Junction = map.getJunctions();
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
				_Junction = map.getJunctions();
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
				_Junction = map.getJunctions();
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
				_Junction = map.getJunctions();
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
				_Junction = map.getJunctions();
				fireTableStructureChanged();
				fireTableDataChanged();
			}
		});
		
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub
		
	}

}
