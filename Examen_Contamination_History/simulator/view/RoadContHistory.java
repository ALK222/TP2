package simulator.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import exceptions.EventException;
import exceptions.JunctionException;
import exceptions.RoadException;
import exceptions.VehicleException;
import simulator.control.Controller;
import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class RoadContHistory extends JDialog implements TrafficSimObserver, ActionListener {

    /**
     *
     */
    private static final long serialVersionUID = 8859420467504730999L;

    private List<Pair<Integer, List<Road>>> _history;

    private JButton update;
    private JButton cancel;
    private HistoryDataTable _dataTable;
    private JSpinner contLimit;
    private String _label = "Select a contamination limit and press UPDATE to show the roads that exceeded this total CO2 in each tick";

    private class HistoryDataTable extends AbstractTableModel {

        /**
         *
         */
        private static final long serialVersionUID = 1L;

        String[] _header = { "Tick", "Vehicles" };

        @Override
        public int getRowCount() {
            return _history.size();
        }

        @Override
        public int getColumnCount() {
            return _header.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Pair<Integer, List<Road>> e = _history.get(rowIndex);
            String v = "";

            switch (columnIndex) {
                case 0:
                    v = e.getFirst() + "";
                    break;
                case 1:
                    v = filter(e.getSecond());
                    break;
                default:
                    break;
            }
            return v;
        }

        private String filter(List<Road> l) {
            List<String> roads = new ArrayList<>();
            Integer w = (Integer) contLimit.getValue();

            for (Road e : l) {
                if (e.getTotalCO2() >= w) {
                    roads.add(e.toString());
                }
            }
            return roads.toString();
        }

        private void update() {
            fireTableDataChanged();
            fireTableStructureChanged();
        }

    }

    public RoadContHistory(Controller ctrl) {
        initGUI();
        setVisible(true);
        ctrl.addObserver(this);
    }

    private void initGUI() {
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        _history = new ArrayList<>();
        _dataTable = new HistoryDataTable();

        setTitle("Road Weather History");
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        setContentPane(mainPanel);

        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel(_label));

        mainPanel.add(topPanel);

        JPanel panelSup = new JPanel();
        panelSup.setLayout(new FlowLayout());

        contLimit = new JSpinner();
        contLimit.setMinimumSize(new Dimension(80, 30));
        contLimit.setMaximumSize(new Dimension(200, 30));
        contLimit.setPreferredSize(new Dimension(80, 30));
        panelSup.add(contLimit);
        this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

        // BOTONES
        JPanel botones = new JPanel();
        botones.setLayout(new FlowLayout());

        // UPDATE
        update = new JButton("UPDATE");
        update.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                _dataTable.update();

            }
        });

        JPanel tabla = new JPanel(new FlowLayout());

        JScrollPane dt = new JScrollPane(new JTable((_dataTable)));
        dt.setPreferredSize(new Dimension(500, 200));
        tabla.add(dt);

        botones.add(update);

        cancel = new JButton("cancel");
        cancel.addActionListener(this);
        botones.add(cancel);

        mainPanel.add(panelSup);
        mainPanel.add(botones);
        mainPanel.add(tabla);
        this.pack();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.setVisible(false);

    }

    public void open() {
        _dataTable.update();
        setVisible(true);
    }

    private void updateHistory(RoadMap rMap, int time) {
        _history.add(new Pair<Integer, List<Road>>(time, rMap.getRoads()));
    }

    @Override
    public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
    }

    @Override
    public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
        updateHistory(map, time);
    }

    @Override
    public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onReset(RoadMap map, List<Event> events, int time) {
        _history.clear();
    }

    @Override
    public void onRegister(RoadMap map, List<Event> events, int time) {
    }

    @Override
    public void onError(String err) throws VehicleException, EventException, JunctionException, RoadException {
    }

}
