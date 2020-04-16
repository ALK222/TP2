package simulator.view;

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
import simulator.model.Vehicle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

class ChangeCO2ClassDialog extends JDialog implements ActionListener {
    private List<Vehicle> v = new ArrayList<Vehicle>();
    private Integer[] contClass = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
    private int ticks;
    private String[] vehicles;
    private JComboBox<String> listVehicles;
    private JComboBox<Integer> listCont;
    private JLabel tick_label;
    private Controller _ctrl;
    private JSpinner tick_field;

    public ChangeCO2ClassDialog(Controller ctrl) {
        this._ctrl = ctrl;
        initGUI();
    }

    private void initGUI() {

        //Vehicle selector
        v = _ctrl.getTS().getRoadMap().getVehicles();
        vehicles = new String[v.size()];
        for (int i = 0; i < v.size(); ++i) {
            vehicles[i] = v.get(i).getId();
        }
        listVehicles = new JComboBox<String>(vehicles);
        this.add(listVehicles);

        //Contamination Selector
        listCont = new JComboBox<Integer>(contClass);
        this.add(listCont);

        //Tick selector
        tick_label = new JLabel();
        tick_label.setText(" Ticks: ");
        this.add(tick_label);
        ticks = 0;
        createTickTextLabel();

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
                vc.add(new Pair<String, Integer>((String) listVehicles.getSelectedItem(),
                        (Integer) listCont.getSelectedItem()));

                try {
                    NewSetContClassEvent newContClass = new NewSetContClassEvent((ticks), vc);
                    newContClass.execute(_ctrl.getTS().getRoadMap());
                } catch (VehicleException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }

        });
        botones.add(okB);

        //CANCELAR
        JButton cancelB = new JButton("OK");
        cancelB.addActionListener(this);

        botones.add(cancelB);

        this.add(botones);

        this.pack();

    }
    
    private void createTickTextLabel() {
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
		
		this.add(tick_field);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.dispose();
    }

}


