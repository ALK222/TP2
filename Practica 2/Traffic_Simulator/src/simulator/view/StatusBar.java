package simulator.view;

import java.awt.FlowLayout;

import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar.Separator;

import exceptions.EventException;
import exceptions.JunctionException;
import exceptions.RoadException;
import exceptions.VehicleException;
import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class StatusBar extends JPanel implements TrafficSimObserver {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private JLabel _currTime;

    private JLabel time;

    private JLabel _event;

    public StatusBar(Controller ctrl) {

        initGUI();

        ctrl.addObserver(this);
        

    }

    private void initGUI(){
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.time = new JLabel("Time: ");
        this.add(time);
        this._currTime = new JLabel("0");
        this.add(_currTime);
        this.add(new Separator());
        this._event = new JLabel("Welcome!");
        this.add(_event);
    }

    @Override
    public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
        

    }

    @Override
    public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
        this._currTime.setText(String.valueOf(time));
    }

    @Override
    public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
        this._event.setText(e.toString());

    }

    @Override
    public void onReset(RoadMap map, List<Event> events, int time) {
        this._currTime.setText("0");
        this._event.setText("Welcome!");

    }

    @Override
    public void onRegister(RoadMap map, List<Event> events, int time) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onError(String err) throws VehicleException, EventException, JunctionException, RoadException {
        // TODO Auto-generated method stub

    }
}