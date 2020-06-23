package simulator.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.json.JSONException;

import exceptions.CoordException;
import exceptions.FactoryException;
import exceptions.JunctionException;
import exceptions.RoadException;
import exceptions.StrategyException;
import exceptions.VehicleException;
import exceptions.WeatherException;
import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class ControlPanel extends JPanel implements TrafficSimObserver {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private Controller _ctrl;

	private boolean _stoped;

	private JToolBar toolbar;

	private JFileChooser chooser;

	private JButton loadButton;

	private JButton setContButton;

	private JButton setWeatherButton;

	private JButton setRunButton;

	private JButton setStopButton;

	private JSpinner setTicksArea;

	private JButton setExitButton;

	protected ChangeCO2ClassDialog conClassDialog;

	protected ChangeWeatherDialog weatherClassDialog;

	private RoadMap _map;

	private int _time;

	// protected int ticks = 10;//Valor por defecto
	protected int ticks = 1;// Valor por defecto

	public ControlPanel(Controller ctrl) {

		this._ctrl = ctrl;
		_stoped = false;
		_ctrl.addObserver(this);
		initGui();

	}

	private void run_sim(int n) {
		if (n > 0 && !_stoped) {
			try {
				_ctrl.run(1);
			} catch (Exception e) {
				onError(e.getMessage());
				enableButtons();
				_stoped = true;
				return;
			}
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					run_sim(n - 1);

				}
			});
		} else {
			enableToolBar(true);
			_stoped = true;
			enableButtons();
		}
	}

	private void enableToolBar(boolean b) {
		toolbar.setEnabled(b);
	}

	private void stop() {
		_stoped = true;
	}

	private void initGui() {
		this.toolbar = new JToolBar();
		setLayout(new BorderLayout());
		add(toolbar, BorderLayout.PAGE_START);
		conClassDialog = new ChangeCO2ClassDialog(_ctrl, _map, _time);
		weatherClassDialog = new ChangeWeatherDialog(_ctrl, _map, _time);
		JToolBar aux = new JToolBar();
		// Load
		createLoadButton();
		toolbar.addSeparator();

		// Change contamination class button
		createSetContClassButton();

		// Change weather button
		createChangeWeatherButton();
		toolbar.addSeparator();

		// Run button (https://www.youtube.com/watch?v=K09_5IsgGe8)
		createRunButton();

		// Stop button
		createStopButton();

		// Tick counter
		createTickCounter();
		toolbar.addSeparator();

		stackExitReset(aux);

	}

	private void stackExitReset(JToolBar aux) {
		// Exit button
		createExitButton(aux);
		aux.setAlignmentX(Component.RIGHT_ALIGNMENT);
		toolbar.add(Box.createHorizontalGlue());
		toolbar.add(aux);
	}

	// USAR COMO PLANTILLA PARA EL RESTO DE BOTONES
	private void createLoadButton() {

		chooser = new JFileChooser();
		chooser.setDialogTitle("Choose a file to load the objects to the simulation");
		chooser.setCurrentDirectory(new File("src/resources/examples/"));// Se abre directamente en el dir de los
																			// ejemplos
		loadButton = new JButton();
		loadButton.setToolTipText("Loads roads, vehicles, junctions and events into the simulator");
		loadButton.setIcon(new ImageIcon("src/resources/icons/open.png"));
		loadButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int ret = chooser.showOpenDialog(chooser);
				if (ret == JFileChooser.APPROVE_OPTION)
					JOptionPane.showMessageDialog(chooser, "You chose: " + chooser.getSelectedFile());
				else {
					if (ret == JFileChooser.CANCEL_OPTION)
						JOptionPane.showMessageDialog(chooser, "Selection canceled");
					else
						JOptionPane.showMessageDialog(chooser, "An error has ocurred");
				}

				File f = chooser.getSelectedFile();
				InputStream inputStream;

				try {
					inputStream = new FileInputStream(f);
					_ctrl.reset();
					_ctrl.loadEvents(inputStream); // En teproa se llama a loadEvents no addEvents
					// _ctrl.addEvents(inputStream);
				} catch (FileNotFoundException | JSONException | StrategyException | CoordException | FactoryException
						| RoadException | JunctionException | VehicleException | WeatherException e1) {
					JOptionPane.showMessageDialog(null, "An error has ocurred");
				}
			}

		});

		toolbar.add(loadButton);
	}

	private void createSetContClassButton() {

		setContButton = new JButton();
		setContButton.setToolTipText("Changes the CO2 class of a vehicle");
		setContButton.setIcon(new ImageIcon("src/resources/icons/co2class.png"));
		setContButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				changeCO2Class();

			}
		});
		toolbar.add(setContButton);
	}

	private void changeCO2Class() {
		ChangeCO2ClassDialog dial = new ChangeCO2ClassDialog(_ctrl, _map, _time);
		dial.setVisible(true);

	}

	private void createChangeWeatherButton() {
		setWeatherButton = new JButton();
		setWeatherButton.setToolTipText("Changes Road Weather");
		setWeatherButton.setIcon(new ImageIcon("src/resources/icons/weather.png"));
		setWeatherButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				changeWeather();
			}
		});
		toolbar.add(setWeatherButton);
	}

	private void changeWeather() {
		ChangeWeatherDialog dial = new ChangeWeatherDialog(_ctrl, _map, _time);
		dial.setVisible(true);

	}

	private void createRunButton() {
		setRunButton = new JButton();
		setRunButton.setToolTipText("Run the simulation N ticks");
		setRunButton.setIcon(new ImageIcon("src/resources/icons/run.png"));
		setRunButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				_stoped = false;
				disableButtons();
				run_sim(ticks);
			}
		});
		toolbar.add(setRunButton);
	}

	private void createStopButton() {
		setStopButton = new JButton();
		setStopButton.setToolTipText("Changes Road Weather");
		setStopButton.setIcon(new ImageIcon("src/resources/icons/stop.png"));
		setStopButton.setEnabled(false);
		setStopButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				stop();
			}
		});
		toolbar.add(setStopButton);
	}

	private void createTickCounter() {

		setTicksArea = new JSpinner();
		setTicksArea.setValue(ticks);
		setTicksArea.setToolTipText("Set ticks to execute in simulation");
		setTicksArea.setMinimumSize(new Dimension(80, 30));
		setTicksArea.setMaximumSize(new Dimension(200, 30));
		setTicksArea.setPreferredSize(new Dimension(80, 30));
		setTicksArea.addChangeListener(new ChangeListener() {

			public void stateChanged(ChangeEvent e) {

				ticks = Integer.valueOf(setTicksArea.getValue().toString());

			}

		});
		toolbar.add(new JLabel("Ticks:"));
		toolbar.add(setTicksArea);

	}

	private void createExitButton(JToolBar aux) {
		setExitButton = new JButton();
		setExitButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
		setExitButton.setToolTipText("Exit the aplication");
		setExitButton.setIcon(new ImageIcon("src/resources/icons/exit.png"));
		setExitButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int n = JOptionPane.showOptionDialog(new JFrame(), "Are sure you want to quit?", "Quit",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
				if (n == 0) {
					System.exit(0);
				}
			}
		});
		// toolbar.add(Box.createHorizontalGlue());
		// toolbar.add(setExitButton);
		aux.add(setExitButton);
	}

	private void disableButtons() {
		loadButton.setEnabled(false);

		setContButton.setEnabled(false);

		setWeatherButton.setEnabled(false);

		setRunButton.setEnabled(false);

		setStopButton.setEnabled(true);
		;

		setTicksArea.setEnabled(false);

		setExitButton.setEnabled(false);

	}

	private void enableButtons() {

		loadButton.setEnabled(true);

		setContButton.setEnabled(true);

		setWeatherButton.setEnabled(true);

		setRunButton.setEnabled(true);

		setStopButton.setEnabled(false);
		;

		setTicksArea.setEnabled(true);

		setExitButton.setEnabled(true);

	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		this._map = map;
		this._time = time;

	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		this._map = map;
		this._time = time;

	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		this._map = map;
		this._time = time;

	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		this._map = map;
		this._time = time;

	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		this._map = map;
		this._time = time;

	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub

	}

}
