package simulator.view;

import java.util.List;

import javax.swing.JPanel;

import simulator.control.Controller;
import simulator.model.TrafficSimObserver;



public class ControlPanel extends JPanel implements TrafficSimObserver{
    
    private Controller _ctrl;

    public ControlPanel(Controller ctrl){
        this._ctrl = ctrl;
    }

    
    
}