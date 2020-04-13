package simulator.view;

import java.awt.BorderLayout;

import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

import simulator.control.Controller;
import simulator.model.TrafficSimObserver;



public class ControlPanel extends JPanel implements TrafficSimObserver{
    
    private Controller _ctrl;

    private boolean _stoped;

    private JToolBar toolbar;

    private JFileChooser chooser;

    private JButton loadButton;

    public ControlPanel(Controller ctrl){

        this._ctrl = ctrl;
        _stoped = false;
        initGui();
        _ctrl.addObserver(this);
    }

    private void run_sim(int n){
        if(n > 0 && !_stoped){
            try{
                _ctrl.run(1);
            }
            catch (Exception e){
                onError(e);
                _stoped = true;
                return;
            }
            SwingUtilities.invokeLater(new Runnable(){
            
                @Override
                public void run() {
                    run_sim(n - 1);
                }
            });
        }
        else{
            enableToolBar(true);
            _stoped = true;
        }
    }

    private void enableToolBar(boolean b){
        toolbar.setEnabled(b);
    }

    private void stop(){
        _stoped = true;
    }

    private void initGui(){
        this.toolbar = new JToolBar();
        setLayout(new BorderLayout());
        add(toolbar, BorderLayout.PAGE_START);

        //Load
        createLoadButton();
        toolbar.addSeparator();

        //Change contamination class button
        createSetContClassButton();
        toolbar.addSeparator();

        //Change weather button
        createChangeWeatherButton();
        toolbar.addSeparator();

        //Run button (https://www.youtube.com/watch?v=K09_5IsgGe8)
        createRunButton();
        toolbar.addSeparator();

        //Stop button
        createStopButton();
        toolbar.addSeparator();

        //Tick counter
        createTickCounter();
        toolbar.addSeparator();

        //Exit button
        createExitButton();
        toolbar.addSeparator();
    }

    private void createLoadButton() {

        chooser = new JFileChooser();
        chooser.setDialogTitle("Choose a file to load the objects to the simulation");
        loadButton = new JButton();
        loadButton.setToolTipText("Loads roads, vehicles, junctions and events into the simulator");
        loadButton.setIcon(new ImageIcon(this.getClass().getResource("/icons/open.png")));
    }
    
}