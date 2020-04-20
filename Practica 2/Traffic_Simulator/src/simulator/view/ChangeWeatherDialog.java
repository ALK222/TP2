package simulator.view;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import exceptions.RoadException;
import exceptions.VehicleException;
import exceptions.WeatherException;
import simulator.control.Controller;
import simulator.misc.Pair;
import simulator.model.NewSetContClassEvent;
import simulator.model.Road;
import simulator.model.SetWeatherEvent;
import simulator.model.Weather;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

class ChangeWeatherDialog extends JDialog implements ActionListener {
    private List<Road> r = new ArrayList<Road>();
    private Weather[] w ;
    private String[] wS;
    private int ticks;
    private String[] road;
    private JComboBox<String> listRoad;
    private JComboBox<String> listWeather;
    private JLabel tick_label;
    private Controller _ctrl;
    private JSpinner tick_field;
    public ChangeWeatherDialog(Controller ctrl) {
        this._ctrl = ctrl;
        initGUI();
    }

    private void initGUI() {
    	JPanel mainPanel = new JPanel();
    	JPanel topPanel= new JPanel();
    	topPanel.add(new JLabel("Schedule an event to change the weather of a road after a given  number of simulation ticks from now."));
	     mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.PAGE_AXIS));
	     mainPanel.add(topPanel);
	     JPanel panelSup = new JPanel();
	     panelSup.setLayout(new FlowLayout());
        //Vehicle selector
        r = _ctrl.getTS().getRoadMap().getRoads();
        road = new String[r.size()];
        for (int i = 0; i < r.size(); ++i) {
            road[i] = r.get(i).getId();
        }
        /*
         *INSERCION DE VEHICULO MANUAL PARA VER SI FUNCIONA 
         * 
         * 
         * **/
        road = new String[2];
        road[0]= "r1";
        road[1] = "r2";
        
        ///////////
        
        //Coger el clima
        w= Weather.values();
        wS = new String[w.length];
        for (int i = 0; i < w.length; ++i) {
            wS[i] = w[i].parse();
        }
        
        listRoad = new JComboBox<String>(road);
        JLabel textV = new JLabel("Road: ");
        panelSup.add(textV);
        panelSup.add(listRoad);
        //Contamination Selector
        listWeather = new JComboBox<String>(wS);
        JLabel textClass = new JLabel("Weather: ");
        panelSup.add(textClass);
        panelSup.add(listWeather);

        //Tick selector
        tick_label = new JLabel();
        tick_label.setText(" Ticks: ");
        panelSup.add(tick_label);
        ticks = 0;
        panelSup.add(createTickTextLabel());

        this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

        //Botones
        JPanel botones = new JPanel();
        botones.setLayout(new FlowLayout());

        //CONFIRMAR
        JButton okB = new JButton("OK");
        okB.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
            	List<Pair<String, Weather>> rd = new ArrayList<Pair<String, Weather>>();
                rd.add(new Pair<String, Weather>((String) listRoad.getSelectedItem(),
                         (Weather)listWeather.getSelectedItem()));

                try {
                	SetWeatherEvent newWeatherClass = new SetWeatherEvent((ticks), rd);
                    newWeatherClass.execute(_ctrl.getTS().getRoadMap());
                   
                } catch (WeatherException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (RoadException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            
            
            }
            });
        botones.add(okB);

        //CANCELAR
        JButton cancelB = new JButton("cancel");
        cancelB.addActionListener(this);

        botones.add(cancelB);
        mainPanel.add(panelSup);
        mainPanel.add(botones);
        this.add(mainPanel);
        this.setVisible(false);
        this.pack();

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


