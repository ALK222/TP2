package simulator.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import exceptions.RoadException;
import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;
import simulator.model.VehicleStatus;

public class MapByRoadComponent extends JComponent implements TrafficSimObserver {
	private static final long serialVersionUID = 1L;

	private static final int _JRADIUS = 10;
	private static final Color _Name_Color = Color.BLACK;
	private static final Color _BG_COLOR = Color.WHITE;
	private static final Color _JUNCTION_COLOR = Color.BLUE;
	private static final Color _JUNCTION_LABEL_COLOR = new Color(200, 100, 0);
	private static final Color _GREEN_LIGHT_COLOR = Color.GREEN;
	private static final Color _RED_LIGHT_COLOR = Color.RED;

	private RoadMap _map;

	private Image _car;

	public MapByRoadComponent(Controller _ctrl) {
		setPreferredSize(new Dimension(300, 200));
		initGUI();
		_ctrl.addObserver(this);

	}

	private void initGUI() {
		_car = loadImage("car.png");
	}

	private Image loadImage(String img) {
		Image i = null;
		try {
			return ImageIO.read(new File("src/resources/icons/" + img));
		} catch (IOException e) {
		}
		return i;
	}

	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		Graphics2D g = (Graphics2D) graphics;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		// clear with a background color
		g.setColor(_BG_COLOR);
		g.clearRect(0, 0, getWidth(), getHeight());

		if (_map == null || _map.getJunctions().size() == 0) {
			g.setColor(Color.red);
			g.drawString("No map yet!", getWidth() / 2 - 50, getHeight() / 2);
		} else {
			updatePrefferedSize();
			drawMap(g);
		}
	}

	private void drawMap(Graphics g) {
		drawRoads(g);
		drawVehicles(g);
	
	
	}

	private void drawRoads(Graphics g) {
		int i = 0;
		for (Road r : _map.getRoads()) {
			
			// the road goes from (x1,y) to (x2,y)
			int x1 = 50;
			int y = (i + 1) * 50;
			int x2 = getWidth() - 200;
			g.setColor(_Name_Color);
		//	g.drawString("Hola", 20, 50);
			g.drawString(r.getId(), x1-20, y);
			// choose a color for the circle depending on the traffic light of the road
			Color junctionColor = _RED_LIGHT_COLOR;
			int idx = r.getDest().getGreenLightIndex();
			if (idx != -1 && r.equals(r.getDest().getInRoads().get(idx))) {
				junctionColor = _GREEN_LIGHT_COLOR;
			}

			// choose a color for the road depending on the total contamination, the darker
			// the
			// more contaminated (wrt its co2 limit)
			//int roadColorValue = 200 - (int) (200.0 * Math.min(1.0, (double) r.getTotalCO2() / (1.0 + (double) r.getCO2Limit())));
		//	Color roadColor = new Color(roadColorValue, roadColorValue, roadColorValue);
			
			// draw line from (x1,y1) to (x2,y2) with arrow of color arrowColor and line of
			// color roadColor. The size of the arrow is 15px length and 5 px width
			g.setColor(_JUNCTION_COLOR);
			g.fillOval(x1, y-5, _JRADIUS, _JRADIUS);
			g.setColor(_JUNCTION_LABEL_COLOR);
			g.drawString(r.getSrc().getId(), x1, y -5);
			//En Map by road no hay triangulo de flecha
			g.setColor(Color.BLACK);
			g.drawLine(x1, y, x2, y);
			g.setColor(junctionColor);
			g.fillOval(x2, y-5, _JRADIUS, _JRADIUS);
			g.drawString(r.getDest().getId(), x2, y -5);
			drawWeather(g, x2+20,y-10);
			drawContamination(g,x2+60,y-10);
			++i;
		}

	}

	private void drawVehicles(Graphics g) {
		int i = 0;
		for (Vehicle v : _map.getVehicles()) {
			if (v.getStatus() != VehicleStatus.ARRIVED) {
				int y = (i + 1) * 50;
				// The calculation below compute the coordinate (vX,vY) of the vehicle on the
				// corresponding road. It is calculated relativly to the length of the road, and
				// the location on the vehicle.
				Road r = v.getCurrentRoad();
				int x1 = r.getSrc().getX();

				int x2 = r.getDest().getX();
				// double x = Math.sin(alpha) * relLoc;;
				double x = x1 + (int) ((x2 - x1) * ((double) v.getLocation() / (double) r.getLenght()));

				int xDir = x1 < x2 ? 1 : -1;

				int vX = x1 + xDir * ((int) x);

				// Choose a color for the vehcile's label and background, depending on its
				// contamination class
				int vLabelColor = (int) (25.0 * (10.0 - (double) v.getContamination()));
				g.setColor(new Color(0, vLabelColor, 0));

				// draw an image of a car (with circle as background) and it identifier

				g.drawImage(_car, vX, y - 6, 12, 12, this);
				g.drawString(v.getId(), vX, y - 6);
			}
			i++;
		}

	}

	private void drawWeather(Graphics g, int x, int y) {
		Image W;
		String name="";
		String aux="";
		for (Road r : _map.getRoads()) {
			name=r.getWeather();
		//	SUNNY, CLOUDY, RAINY, WINDY, STORM;
			switch(name) {
			case"SUNNY": aux="sun.png";
				break;
			case"CLOUDY":aux="cloud.png";
				break;
			case"RAINY":aux="rain.png";
				break;
			case"WINDY":aux="wind.png";
				break;
			case"STORM":aux="storm.png";
				break;
			}
			
			W= loadImage(aux);
			g.drawImage(W, x,y , 32, 32, this);//Falta ajustar la posici�n
		}
	}

	private void drawContamination(Graphics g, int x, int y) {
		Image W;
		String name="";
		for (Road r : _map.getRoads()) {
			int C = (int) Math.floor(Math.min((double) r.getTotalCO2() / (1.0 + (double) r.getCO2Limit()), 1.0) / 0.19);
			name="cont_"+C+".png";
			W= loadImage(name);
			
			g.drawImage(W, x,y , 32, 32, this); // Parametros: Nombre, x, y tamx, y, ni idea
		}
	}

	private void updatePrefferedSize() {
		setPreferredSize(new Dimension(300, 200));
	}

	public void update(RoadMap map) {
		_map = map;
		repaint();
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		SwingUtilities.invokeLater(new Runnable(){
		
			@Override
			public void run() {
				update(map);
			}
		});
		
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		SwingUtilities.invokeLater(new Runnable(){
		
			@Override
			public void run() {
				update(map);
			}
		});
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		SwingUtilities.invokeLater(new Runnable(){
		
			@Override
			public void run() {
				update(map);
			}
		});
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		SwingUtilities.invokeLater(new Runnable(){
		
			@Override
			public void run() {
				update(map);
			}
		});
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		SwingUtilities.invokeLater(new Runnable(){
		
			@Override
			public void run() {
				update(map);
			}
		});
	}

	@Override
	public void onError(String err) throws RoadException {
		throw new RoadException(err);

	}

}
