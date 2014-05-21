/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 1997-2010 Oracle and/or its affiliates. All rights reserved.
 *
 * Oracle and Java are registered trademarks of Oracle and/or its affiliates.
 * Other names may be trademarks of their respective owners.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common
 * Development and Distribution License("CDDL") (collectively, the
 * "License"). You may not use this file except in compliance with the
 * License. You can obtain a copy of the License at
 * http://www.netbeans.org/cddl-gplv2.html
 * or nbbuild/licenses/CDDL-GPL-2-CP. See the License for the
 * specific language governing permissions and limitations under the
 * License.  When distributing the software, include this License Header
 * Notice in each file and include the License file at
 * nbbuild/licenses/CDDL-GPL-2-CP.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the GPL Version 2 section of the License file that
 * accompanied this code. If applicable, add the following below the
 * License Header, with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Contributor(s):
 *
 * The Original Software is NetBeans. The Initial Developer of the Original
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2006 Sun
 * Microsystems, Inc. All Rights Reserved.
 *
 * If you wish your version of this file to be governed by only the CDDL
 * or only the GPL Version 2, indicate your decision by adding
 * "[Contributor] elects to include this software in this distribution
 * under the [CDDL or GPL Version 2] license." If you do not indicate a
 * single choice of license, a recipient has the option to distribute
 * your version of this file under either the CDDL, the GPL Version 2 or
 * to extend the choice of license to its licensees as provided above.
 * However, if you add GPL Version 2 code and therefore, elected the GPL
 * Version 2 license, then the option applies only if the new code is
 * made subject to such option by the copyright holder.
 */
package net.falappa.widgets.colorbox;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents one color with some text description.
 *
 * @author Administrator
 * @author S. Aubrecht
 * @author Alessandro Falappa
 */
class ColorValue {

    static final ColorValue CUSTOM_COLOR = new ColorValue("Custom...", null, false); //NOI18N
    static final ColorValue RANDOM_COLOR = new ColorValue("Random", null, false); //NOI18N

    private static final Map<Color, String> colorMap = new HashMap<>();

    static {
        colorMap.put(Color.BLACK, "Black");//NOI18N
        colorMap.put(Color.BLUE, "Blue");//NOI18N
        colorMap.put(Color.CYAN, "Cyan");//NOI18N
        colorMap.put(Color.DARK_GRAY, "Dark_Gray");//NOI18N
        colorMap.put(Color.GRAY, "Gray");//NOI18N
        colorMap.put(Color.GREEN, "Green");//NOI18N
        colorMap.put(Color.LIGHT_GRAY, "Light_Gray");//NOI18N
        colorMap.put(Color.MAGENTA, "Magenta");//NOI18N
        colorMap.put(Color.ORANGE, "Orange");//NOI18N
        colorMap.put(Color.PINK, "Pink");//NOI18N
        colorMap.put(Color.RED, "Red");//NOI18N
        colorMap.put(Color.WHITE, "White");//NOI18N
        colorMap.put(Color.YELLOW, "Yellow");//NOI18N
    }

    final String text;
    final Color color;
    final boolean isCustom;

    static String toText(Color color) {
        String text = colorMap.get(color);
        if (null == text && null != color) {
            StringBuilder sb = new StringBuilder();
            sb.append('[').append(color.getRed()).
                    append(',').append(color.getGreen()).
                    append(',').append(color.getBlue()).
                    append(']');
            text = sb.toString();
        }
        return text;
    }

    ColorValue(Color color, boolean custom) {
        this(toText(color), color, custom);
    }

    ColorValue(String text, Color color, boolean custom) {
        this.text = text;
        this.color = color;
        this.isCustom = custom;
    }

    @Override
    public String toString() {
        return text;
    }

}
