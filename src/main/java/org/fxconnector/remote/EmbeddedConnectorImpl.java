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
package org.fxconnector.remote;

import org.fxconnector.AppController;
import org.fxconnector.AppControllerImpl;
import org.fxconnector.StageController;
import org.fxconnector.StageControllerImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.concurrent.CountDownLatch;

import javafx.stage.Window;
import javafx.stage.Stage;
import javafx.application.Platform;

import org.fxconnector.AppController;

class EmbeddedConnectorImpl implements FXConnector {

    private AppController mainController = new AppControllerImpl(1, "Local");
      
    public List<AppController> connect() {
        updateStages();
        if (mainController.getStages().isEmpty()) {
            return List.of();
        }
        return List.of(mainController);
    }

    private void onNewStages(List<StageController> stages) {
        mainController = new AppControllerImpl(1, "Local");
        mainController.getStages().addAll(stages);
    }

    public void close() {
    }

    private List<StageController> previousStageControllers = List.of();

    void updateStages() {
        List<StageController> stageControllers = Window.getWindows().stream()
              .filter(Stage.class::isInstance)
              .map(Stage.class::cast)
              .filter(this::isNotScenicViewWindow)
              .map(this::createStageController)
              .collect(Collectors.toList());
        if (!isSame(stageControllers, previousStageControllers)) {
            onNewStages(stageControllers);
        }
    }

    boolean isNotScenicViewWindow(Stage stage) {
        return stage.getTitle() == null || !stage.getTitle().startsWith("Scenic View");
    }

    boolean isSame(List<StageController> controllers, List<StageController> previousControllers) {
        if (controllers.size() != previousControllers.size()) {
            return false;
        }

        for (int i = 0; i < controllers.size(); i++) {
            StageController left = controllers.get(i);
            StageController right = previousControllers.get(i);
            if (left.getID().getStageID() != right.getID().getStageID()) {
                return false;
            }
        }
        return true;
    }

    StageControllerImpl createStageController(Stage stage) {
        return new StageControllerImpl(stage, mainController);
    }
}
