package simulator.view;

import java.util.Map;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;

public class HistoryDataTable extends AbstractTableModel {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String[] _colNames = { "Tick", "Road/s" };

    private Map<Integer, String[]> _dataMap;

    public HistoryDataTable() {
        _dataMap = null;
    }

    public HistoryDataTable(Controller _ctrl) {
        _dataMap = null;
    }

    public void update() {
        // observar que si no refresco la tabla no se carga
        // La tabla es la represantación visual de una estructura de datos,
        // en este caso de un ArrayList, hay que notificar los cambios.

        // We need to notify changes, otherwise the table does not refresh.
        fireTableDataChanged();
    }

    public void setDataTable(Map<Integer, String[]> data) {
        _dataMap = data;
        update();
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    @Override
    public String getColumnName(int col) {
        return _colNames[col];
    }

    public int getColumnCount() {
        return _colNames.length;
    }

    public int getRowCount() {
        return _dataMap == null ? 0 : _dataMap.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        String s = null;
        return s;
    }

}