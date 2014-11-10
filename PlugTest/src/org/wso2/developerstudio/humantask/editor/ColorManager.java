/*
 * Copyright (c) 2014, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.wso2.developerstudio.humantask.editor;

import java.util.HashMap;
import java.util.Iterator;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

/**
 * This class has methods to retrieve color when the RGB values are provided
 * 
 */
public class ColorManager {

	protected HashMap<RGB, Color> colorTable = new HashMap<RGB, Color>(10);

	/**
	 * Disposes the colorTable
	 */
	public void dispose() {
		Iterator<Color> iterator = colorTable.values().iterator();
		while (iterator.hasNext()) {
			iterator.next().dispose();
		}
	}

	/**
	 * Retrieves the color when RGB values are provided
	 * 
	 * @param rgb
	 * @return
	 */
	public Color getColor(RGB rgb) {
		Color color = colorTable.get(rgb);
		if (color == null) {
			color = new Color(Display.getCurrent(), rgb);
			colorTable.put(rgb, color);
		}
		return color;
	}
}
