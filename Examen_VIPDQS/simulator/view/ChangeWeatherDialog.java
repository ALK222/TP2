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
import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import exceptions.WeatherException;
import simulator.control.Controller;
import simulator.misc.Pair;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.SetWeatherEvent;
import simulator.model.Weather;

class ChangeWeatherDialog extends JDialog implements ActionListener {
	/**
	 *
	 */
	private static final long serialVersionUID = -307371379788952772L;
	private List<Road> r = new ArrayList<Road>();
	private Weather[] w;
	private String[] wS;
	private int ticks = 1;;
	private String[] road;
	private JComboBox<String> listRoad;
	private JComboBox<String> listWeather;
	private JLabel tick_label;
	private Controller _ctrl;
	private JSpinner tick_field;
	private String _label = "Schedule an event to change the weather of a road after a given  number of simulation ticks from now.";
	private RoadMap _map;
	private int _time;;

	public ChangeWeatherDialog(Controller ctrl, RoadMap map, int time) {
		this._ctrl = ctrl;
		this._map = map;
		this._time = time;
		this.setModal(true);
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
		// Vehicle selector
		r = _map.getRoads();
		road = new String[r.size()];
		for (int i = 0; i < r.size(); ++i) {
			road[i] = r.get(i).getId();
		}


		// Coger el clima
		w = Weather.values();
		wS = new String[w.length];
		for (int i = 0; i < w.length; ++i) {
			wS[i] = w[i].parse();
		}

		listRoad = new JComboBox<String>(road);
		JLabel textV = new JLabel("Road: ");
		panelSup.add(textV);
		panelSup.add(listRoad);
		// Contamination Selector
		listWeather = new JComboBox<String>(wS);
		JLabel textClass = new JLabel("Weather: ");
		panelSup.add(textClass);
		panelSup.add(listWeather);

		// Tick selector
		tick_label = new JLabel();
		tick_label.setText(" Ticks: ");
		panelSup.add(tick_label);
		
		panelSup.add(createTickTextLabel());

		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

		// Botones
		JPanel botones = new JPanel();
		botones.setLayout(new FlowLayout());

		// CONFIRMAR
		JButton okB = new JButton("OK");
		okB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				List<Pair<String, Weather>> rd = new ArrayList<Pair<String, Weather>>();
				rd.add(new Pair<String, Weather>((String) listRoad.getSelectedItem(),
						Weather.valueOf((String) listWeather.getSelectedItem())));

				try {
					if (ticks != 0 && r.size() != 0) {
						SetWeatherEvent newWeatherClass = new SetWeatherEvent((ticks + _time), rd);
						_ctrl.addEvents(newWeatherClass);
					}
					
					dispose();
				} catch (WeatherException e1) {
					e1.printStackTrace();
				}

			}
		});
		botones.add(okB);

		// CANCELAR
		JButton cancelB = new JButton("cancel");
		cancelB.addActionListener(this);

		botones.add(cancelB);
		mainPanel.add(panelSup);
		mainPanel.add(botones);
		this.add(mainPanel);
		this.setVisible(false);
		this.pack();

	}

	public void open(RoadMap map) {
	}

	private JSpinner createTickTextLabel() {
		tick_field = new JSpinner();
		tick_field.setValue(ticks);
		tick_field.setMinimumSize(new Dimension(80, 30));
		tick_field.setMaximumSize(new Dimension(200, 30));
		tick_field.setPreferredSize(new Dimension(80, 30));
		tick_field.addChangeListener(new ChangeListener() {

			public void stateChanged(ChangeEvent e) {

				ticks = Integer.valueOf(tick_field.getValue().toString());

			}

		});

		return tick_field;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.dispose();
	}

}
