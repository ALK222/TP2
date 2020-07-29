package simulator.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.model.Weather;

public class WeatherHistoryDialog extends JDialog implements ActionListener {
    /**
     *
     */
    private static final long serialVersionUID = 7625499667714901616L;
    /**
     *
     */

    private InputStream _data;
    private String _label = "Select a weather and press UPDATE to show the roads that have this weather in each tick";
    private HistoryDataTable dataTable;
    private JButton update;
    private JButton cancel;

    private JComboBox<String> weatherList;
    private Weather[] w;
    private String[] wS;

    private Map<Integer, String[]> _dataMap;

    public WeatherHistoryDialog() throws FileNotFoundException {
        _data = new FileInputStream(new File("src/WeatherHistory.json"));
        this.setModal(true);

        dataTable = new HistoryDataTable();

        initGUI();
    }

    private void initGUI() {
        JPanel mainPanel = new JPanel();
        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel(_label));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
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

                loadHistory(_data, (String) weatherList.getSelectedItem());
                dataTable.setDataTable(_dataMap);

            }

            private void loadHistory(InputStream _data, String weather) {
                JSONObject jo = new JSONObject(new JSONTokener(_data));
                JSONArray ja = jo.getJSONArray(weather);
                _dataMap = new TreeMap<Integer, String[]>();
                for (int i = 0; i < ja.length(); ++i) {
                    _dataMap.put(ja.getJSONObject(i).getInt("ticks"), (String[]) ja.getJSONObject(i).get("roads"));
                }

            }

        });

        JPanel tabla = new JPanel(new FlowLayout());

        JTable dt = new JTable(dataTable);
        dt.setPreferredSize(new Dimension(500, 200));
        tabla.add(dt);

        botones.add(update);

        cancel = new JButton("cancel");
        cancel.addActionListener(this);
        botones.add(cancel);

        mainPanel.add(panelSup);
        mainPanel.add(botones);
        mainPanel.add(tabla);
        this.add(mainPanel);
        this.pack();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.dispose();

    }

}