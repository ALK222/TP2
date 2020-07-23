package simulator.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Weather;

public class WeatherHistoryDialog extends JDialog implements TrafficSimObserver, ActionListener {

    private static final long serialVersionUID = 7625499667714901616L;
    /**
     *
     */

    private String _label = "Select a weather and press UPDATE to show the roads that have this weather in each tick";
    private HistoryDataTable dataTable;
    private JButton update;
    private JButton cancel;

    class HistoryDataTable extends AbstractTableModel {

        /**
         *
         */
        private static final long serialVersionUID = 1L;

        String[] _header = { "Tick", "Road" };

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
            Pair<Integer, List<Pair<String, String>>> e = _history.get(rowIndex);
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

        private String filter(List<Pair<String, String>> l) {
            List<String> roads = new ArrayList<>();
            String w = (String) weatherList.getSelectedItem();

            for (Pair<String, String> e : l) {
                if (e.getSecond().equals(w.toString())) {
                    roads.add(e.getFirst());
                }
            }
            return roads.toString();
        }

        private void update() {
            fireTableDataChanged();
            fireTableStructureChanged();
        }

    }

    private JComboBox<String> weatherList;
    private Weather[] w;
    private String[] wS;

    List<Pair<Integer, List<Pair<String, String>>>> _history;

    public WeatherHistoryDialog(Controller ctrl) {
        this.setModal(false);
        initGUI();
        ctrl.addObserver(this);
    }

    private void initGUI() {
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        _history = new ArrayList<>();
        dataTable = new HistoryDataTable();

        setTitle("Road Weather History");
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        setContentPane(mainPanel);

        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel(_label));

        mainPanel.add(topPanel);

        JPanel panelSup = new JPanel();
        panelSup.setLayout(new FlowLayout());

        w = Weather.values();
        wS = new String[w.length];
        for (int i = 0; i < w.length; ++i) {
            wS[i] = w[i].parse();
        }

        weatherList = new JComboBox<String>(wS);
        panelSup.add(weatherList);
        this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

        // BOTONES
        JPanel botones = new JPanel();
        botones.setLayout(new FlowLayout());

        // UPDATE
        update = new JButton("UPDATE");
        update.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                dataTable.update();

            }
        });

        JPanel tabla = new JPanel(new FlowLayout());

        JScrollPane dt = new JScrollPane(new JTable((dataTable)));
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

    public void open() {
        dataTable.update();
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.setVisible(false);

    }

    protected void updateHistory(RoadMap map, int time) {
        List<Pair<String, String>> l = new ArrayList<>();
        for (Road r : map.getRoads()) {
            l.add(new Pair<>(r.getId(), r.getWeather()));
        }
        _history.add(new Pair<>(time, l));
    }

    @Override
    public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
    }

    @Override
    public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                updateHistory(map, time);
            }
        });
    }

    @Override
    public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
    }

    @Override
    public void onReset(RoadMap map, List<Event> events, int time) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                _history.clear();
            }

        });
    }

    @Override
    public void onRegister(RoadMap map, List<Event> events, int time) {
    }

    @Override
    public void onError(String err) {
    }

}
