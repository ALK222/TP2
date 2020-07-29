package simulator.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import exceptions.StrategyException;

public class LessContStrategy implements DequeuingStrategy {

    private int _limit;
    private static String LIMITEXCEPTIONMSG = "LessContSTR: Limit must be a positive number";

    public static Comparator<Vehicle> cmp = new Comparator<Vehicle>() {

        @Override
        public int compare(Vehicle o1, Vehicle o2) {
            if (o1.getContamination() < o2.getContamination()) {
                return -1;
            } else if (o1.getContamination() > o2.getContamination()) {
                return 1;
            } else {
                return 0;
            }
        }
    };

    public LessContStrategy(int limit) throws StrategyException {
        if (limit > 0) {
            _limit = limit;
        } else {
            throw new StrategyException(LIMITEXCEPTIONMSG);
        }
    }

    @Override
    public List<Vehicle> dequeue(List<Vehicle> q) {
        List<Vehicle> l = new ArrayList<Vehicle>(q);
        l.sort(cmp);

        List<Vehicle> aux = new ArrayList<Vehicle>();

        int i = 0;

        if (l.size() > 0) {
            while (i < l.size() && i <= _limit) {
                aux.add(l.get(i));
            }
        }
        return aux;
    }
}
