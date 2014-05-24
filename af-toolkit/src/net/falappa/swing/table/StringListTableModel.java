/*
 * Copyright 2014 Alessandro Falappa <alex.falappa@gmail.com>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.falappa.swing.table;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 * A table model managing a list of strings.
 * <p>
 * Can be made not editable.
 * <p>
 * @author Alessandro Falappa <alex.falappa@gmail.com>
 */
public class StringListTableModel extends AbstractTableModel {

    private boolean editable = true;
    private final ArrayList<String> colls = new ArrayList<String>();

    @Override
    public int getRowCount() {
        return colls.size();
    }

    @Override
    public int getColumnCount() {
        return 1;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (columnIndex != 0) {
            throw new IllegalArgumentException("Column must be 0");
        }
        return colls.get(rowIndex);
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (!(aValue instanceof String)) {
            throw new IllegalArgumentException("Value must be String");
        }
        if (columnIndex != 0) {
            throw new IllegalArgumentException("Column must be 0");
        }
        colls.set(rowIndex, (String) aValue);
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return editable;
    }

    public void setEditable(boolean flag) {
        editable = flag;
    }

    public boolean isEditable() {
        return editable;
    }

    public void addString(String coll) {
        colls.add(coll);
        fireTableDataChanged();
    }

    public void removeString(String coll) {
        colls.remove(coll);
        fireTableDataChanged();
    }

    public void removeString(int idx) {
        colls.remove(idx);
        fireTableDataChanged();
    }

    public void clear() {
        colls.clear();
        fireTableDataChanged();
    }
}
