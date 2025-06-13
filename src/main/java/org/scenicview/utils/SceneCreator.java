/*
 * Scenic View, 
 * Copyright (C) 2025 Jonathan Giles, Ander Ruiz, Amy Fowler 
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.scenicview.utils;

import javafx.scene.Parent;
import javafx.scene.Scene;

public final class SceneCreator {
  public static Scene newScene(Parent root) {
    return newScene(root, -1, -1);
  }
  public static Scene newScene(Parent root, double width, double height) {
    Scene scene = new Scene(root, width, height);
    scene.setUserAgentStylesheet("com/sun/javafx/scene/control/skin/modena/modena.css"); 
    return scene;
  }
}

