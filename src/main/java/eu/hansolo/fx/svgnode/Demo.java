/*
 * Copyright (c) 2019 by Gerrit Grunwald
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package eu.hansolo.fx.svgnode;

import javafx.application.Application;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;
import javafx.scene.Scene;


/**
 * User: hansolo
 * Date: 2019-07-10
 * Time: 11:23
 */
public class Demo extends Application {
    private SvgPath svgPath1;
    private SvgPath svgPath2;
    private SvgNode svgNode;

    @Override public void init() {
        svgPath1 = SvgPathBuilder.create()
                                 .path("M196.747,130.336L270.902,68.691L318.236,115.781L320.56,129.189L294.173,209.808L237.584,211.497L212.062,194.375L246.325,174.396L268.535,131.65L246.622,133.574L213.248,158.554L196.747,130.336Z")
                                 .fill(Color.RED)
                                 .stroke(Color.BLUE)
                                 .strokeWidth(2)
                                 .effect(new DropShadow(BlurType.TWO_PASS_BOX, Color.rgb(0, 0, 0, 0.65), 2, 0.0, 0, 0))
                                 .build();

        svgPath2 = SvgPathBuilder.create()
                                 .path("M140.739,72.898L60.696,48.355L83.388,132.422L167.038,157.744L140.739,72.898Z")
                                 .fill(Color.YELLOW)
                                 .stroke(Color.PURPLE)
                                 .strokeWidth(1)
                                 //.effect(new DropShadow(BlurType.TWO_PASS_BOX, Color.rgb(0, 0, 0, 0.65), 2, 0.0, 0, 0))
                                 .build();

        svgNode = new SvgNode(svgPath1, svgPath2);
        svgNode.setPrefSize(400, 400);
    }

    @Override public void start(Stage stage) {
        StackPane pane = new StackPane(svgNode);

        Scene scene = new Scene(pane);

        stage.setTitle("SvgNode");
        stage.setScene(scene);
        stage.show();
    }

    @Override public void stop() {
        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
