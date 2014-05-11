/*
 * Copyright 2013 Alessandro Falappa.
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
package net.falappa.utils;

import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JComponent;

/**
 * Class of static utility methods for GUI purposes.
 * <p/>
 * @author Alessandro Falappa <alex.falappa@gmail.com>
 */
public class GuiUtils {

    /**
     * Private constructor to prevent instantiation.
     */
    private GuiUtils() {
    }

    /**
     * Enable/disable a group of <tt>JComponent</tt>s widgets.
     * <p/>
     * @param flag the enable/disable flag
     * @param comps the components as variable arguments
     */
    public static void widgetsEnable(boolean flag, JComponent... comps) {
        for (JComponent cmp : comps) {
            cmp.setEnabled(flag);
        }
    }

    /**
     * Converts a <tt>DefaultListModel</tt> into an <tt>ArrayList</tt> object.
     * <p>
     * @param <T> the elements type
     * @param listModel a DefaultListModel to convert
     * @return an ArrayList containing the list model elements
     */
    public static <T> ArrayList<T> listModelAsList(DefaultListModel<T> listModel) {
        ArrayList<T> ret = new ArrayList<>();
        for (int i = 0; i < listModel.size(); i++) {
            ret.add(listModel.get(i));
        }
        return ret;
    }

    /**
     * Converts an <tt>ArrayList</tt> into a <tt>DefaultListModel</tt> object.
     * <p>
     * @param <T> the elements type
     * @param list an ArrayList to convert
     * @return a DefaultListModel containing the list elements
     */
    public static <T> DefaultListModel<T> listAsListModel(ArrayList<T> list) {
        DefaultListModel<T> ret = new DefaultListModel<>();
        for (T elem : list) {
            ret.addElement(elem);
        }
        return ret;
    }

}
