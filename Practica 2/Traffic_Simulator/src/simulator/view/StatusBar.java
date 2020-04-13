package simulator.view;

import javax.swing.JPanel;

import simulator.control.Controller;

public class StatusBar extends JPanel{

    private Controller _ctrl;

    public StatusBar(Controller ctrl){
        this._ctrl = ctrl;
    }
}