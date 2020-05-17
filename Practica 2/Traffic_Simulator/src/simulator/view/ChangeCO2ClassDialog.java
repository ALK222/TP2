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



import exceptions.VehicleException;
import simulator.control.Controller;
import simulator.misc.Pair;
import simulator.model.NewSetContClassEvent;
import simulator.model.RoadMap;
import simulator.model.Vehicle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

class ChangeCO2ClassDialog extends JDialog implements ActionListener {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private List<Vehicle> v = new ArrayList<Vehicle>();
    private Integer[] contClass = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
    private int ticks;
    private String[] vehicles;
    private JComboBox<String> listVehicles;
    private JComboBox<Integer> listCont;
    private JLabel tick_label;
    private Controller _ctrl;
    private JSpinner tick_field;
    private String _label = "Schedule an event to change the CO2 class of a vehicle after a given  number of simulation ticks from now.";
    public ChangeCO2ClassDialog(Controller ctrl) {
        this._ctrl = ctrl;
      
        initGUI();
    }

    private void initGUI() {
    	JPanel mainPanel = new JPanel();
    	JPanel topPanel= new JPanel();
    	topPanel.add(new JLabel(_label));
	     mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.PAGE_AXIS));
	     mainPanel.add(topPanel);
	     JPanel panelSup = new JPanel();
	     panelSup.setLayout(new FlowLayout());
        //Vehicle selector
	     v = _ctrl.getTS().getRoadMap().getVehicles();
         vehicles = new String[v.size()];
         for (int i = 0; i < v.size(); ++i) {
             vehicles[i] = v.get(i).getId();
         }
        /*
         *INSERCION DE VEHICULO MANUAL PARA VER SI FUNCIONA 
         * 
         * 
         * **/
     
        
        ///////////
        listVehicles = new JComboBox<String>(vehicles);
        JLabel textV = new JLabel("Vehicles: ");
        panelSup.add(textV);
        panelSup.add(listVehicles);
        //Contamination Selector
        listCont = new JComboBox<Integer>(contClass);
        JLabel textClass = new JLabel("CO2 Class: ");
        panelSup.add(textClass);
        panelSup.add(listCont);

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
            	  List<Pair<String, Integer>> vc = new ArrayList<Pair<String, Integer>>();
                  vc.add(new Pair<String, Integer>((String) listVehicles.getSelectedItem(),(Integer)listCont.getSelectedItem()));

                  try {
                      if(ticks != 0){
                        NewSetContClassEvent newContClass = new NewSetContClassEvent((ticks+_ctrl.getTS().getTime()), vc);
                      _ctrl.addEvents(newContClass);
                      dispose();
                      } 
                  } catch (VehicleException e1) {
                     
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
    public void open(RoadMap map) {
    	   v = map.getVehicles();
           vehicles = new String[v.size()];
           for (int i = 0; i < v.size(); ++i) {
               vehicles[i] = v.get(i).getId();
           }
           listVehicles = new JComboBox<String>(vehicles);
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


