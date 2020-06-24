package simulator.model;

import java.util.ArrayList;
import java.util.List;

public class VipStrategy implements DequeuingStrategy {

    private String _viptag;
    private int _limit;

    public VipStrategy(String viptag, int limit) {
        this._limit = limit;
        this._viptag = viptag;
    }

    @Override
    public List<Vehicle> dequeue(List<Vehicle> q) {
        List<Vehicle> aux = new ArrayList<Vehicle>();
        for (Vehicle v : q) {
            if (v._id.endsWith(_viptag) && aux.size() <= _limit) {
                aux.add(v);
            }
        }
        if (aux.size() <= _limit) {
            int i = 0;
            while (aux.size() <= _limit) {
                if (!aux.contains(q.get(i))) {
                    aux.add(q.get(i));
                }
                ++i;
            }
        }
        return aux;
    }

}