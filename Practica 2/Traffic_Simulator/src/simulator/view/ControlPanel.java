package simulator.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

import simulator.control.Controller;
import simulator.model.TrafficSimObserver;

public class ControlPanel extends JPanel implements TrafficSimObserver {

    private Controller _ctrl;

    private boolean _stoped;

    private JToolBar toolbar;

    private JFileChooser chooser;

    private JButton loadButton;

    private JButton setContButton;

    public ControlPanel(Controller ctrl) {

        this._ctrl = ctrl;
        _stoped = false;
        initGui();
        _ctrl.addObserver(this);
    }

    private void run_sim(int n) {
        if (n > 0 && !_stoped) {
            try {
                _ctrl.run(1);
            } catch (Exception e) {
                onError(e);
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

        // Load
        createLoadButton();
        toolbar.addSeparator();

        // Change contamination class button
        createSetContClassButton();
        toolbar.addSeparator();

        // Change weather button
        createChangeWeatherButton();
        toolbar.addSeparator();

        // Run button (https://www.youtube.com/watch?v=K09_5IsgGe8)
        createRunButton();
        toolbar.addSeparator();

        // Stop button
        createStopButton();
        toolbar.addSeparator();

        // Tick counter
        createTickCounter();
        toolbar.addSeparator();

        // Exit button
        createExitButton();
        toolbar.addSeparator();
    }

    // USAR COMO PLANTILLA PARA EL RESTO DE BOTONES
    private void createLoadButton() {

        chooser = new JFileChooser();
        chooser.setDialogTitle("Choose a file to load the objects to the simulation");
        loadButton = new JButton();
        loadButton.setToolTipText("Loads roads, vehicles, junctions and events into the simulator");
        loadButton.setIcon(new ImageIcon(this.getClass().getResource("/icons/open.png")));
        loadButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
               int ret = chooser.showOpenDialog(chooser);
               if(ret == JFileChooser.APPROVE_OPTION) JOptionPane.showMessageDialog(chooser, "You chose: " + chooser.getSelectedFile());
               else{
                   if(ret == JFileChooser.CANCEL_OPTION) JOptionPane.showMessageDialog(chooser, "Selection canceled");
                   else JOptionPane.showMessageDialog(chooser, "An error has ocurred");
               }

               File f = chooser.getSelectedFile();
               InputStream inputStream;

               try{
                   inputStream = new FileInputStream(f);
                   _ctrl.reset();
                   _ctrl.addEvents(inputStream);
               }
               catch (FileNotFoundException e1){
                   JOptionPane.showMessageDialog(null, "An error has ocurred");
               }
            }

        });

        toolbar.add(loadButton);
    }

    private void createSetContClassButton() {

        setContButton = new JButton();
        loadButton.setToolTipText("Changes the CO2 class of a vehicle");
        loadButton.setIcon(new ImageIcon(this.getClass().getResource("/Traffic_Simulator/src/resources/icons/co2class.png")));
        loadButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
               
            }

        });

        toolbar.add(setContButton);
    }
    
}