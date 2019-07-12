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
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
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
    private SvgPath svgPath3;
    private SvgPath svgPath4;
    private SvgPath svgPath5;
    private SvgPath svgPath6;
    private SvgPath svgPath7;
    private SvgNode svgNode;

    @Override public void init() {
        svgPath1 = SvgPathBuilder.create()
                                 .path("M235.135,235.135C159.925,310.345 135.231,275.075 26.004,422.225C17.921,433.114 20.496,448.544 31.694,456.194L33.637,457.521C38,460.501 43.224,461.88 48.503,461.642C58.83,461.176 80.859,464.825 116.106,492.951C167.374,533.862 226.035,500.058 252.374,473.719C281.232,450.167 320.774,395.21 285.236,340.081C273.056,321.188 266.597,306.174 263.368,294.601C258.079,275.643 275.644,258.078 294.602,263.367C306.175,266.596 321.188,273.055 340.082,285.235C395.21,320.773 450.168,281.233 473.72,252.373C500.06,226.033 533.863,167.373 492.952,116.105C464.826,80.858 461.176,58.829 461.643,48.502C461.881,43.223 460.503,37.999 457.522,33.636L456.195,31.693C448.545,20.495 433.115,17.921 422.226,26.003C275.075,135.231 310.344,159.925 235.135,235.135Z")
                                 .fill(Color.web("#FD6F71"))
                                 .stroke(Color.TRANSPARENT)
                                 .strokeWidth(0)
                                 .build();

        svgPath2 = SvgPathBuilder.create()
                                 .path("M85.968,403.053C106.035,377.514 146.121,331.223 206.362,303.757C214.498,300.047 224.098,301.902 230.29,308.353C252.48,331.471 295.699,389.535 234.658,451.558C177.284,509.854 117.679,460.332 87.834,431.163C80.132,423.636 79.314,411.522 85.968,403.053Z")
                                 .fill(Color.web("#85E7FF"))
                                 .stroke(Color.TRANSPARENT)
                                 .strokeWidth(0)
                                 .build();

        svgPath3 = SvgPathBuilder.create()
                                 .path("M109.686,409.31C101.985,401.784 101.167,389.668 107.82,381.2C113.481,373.995 120.737,365.137 129.588,355.518C110.289,373.237 95.743,390.609 85.966,403.054C79.313,411.521 80.131,423.637 87.832,431.164C117.677,460.334 177.283,509.855 234.656,451.559C238.431,447.724 241.803,443.904 244.81,440.106C191.186,481.359 137.507,436.5 109.686,409.31Z")
                                 .fill(Color.web("#57D0E6"))
                                 .stroke(Color.TRANSPARENT)
                                 .strokeWidth(0)
                                 .build();

        svgPath4 = SvgPathBuilder.create()
                                 .path("M61.163,438.023C64.067,435.193 62.465,430.262 58.452,429.679L45.283,427.766C43.689,427.534 42.312,426.534 41.599,425.09L35.71,413.157C33.915,409.52 28.729,409.52 26.935,413.157L21.046,425.09C20.333,426.534 18.956,427.535 17.362,427.766L4.193,429.679C0.18,430.262 -1.421,435.194 1.482,438.023L11.01,447.311C12.164,448.435 12.689,450.054 12.417,451.641L10.169,464.757C9.484,468.754 13.679,471.801 17.268,469.914L29.046,463.722C30.471,462.973 32.174,462.973 33.599,463.722L45.377,469.914C48.966,471.801 53.161,468.753 52.476,464.757L50.227,451.642C49.955,450.055 50.48,448.436 51.634,447.312L61.163,438.023Z")
                                 .fill(Color.web("#FADC60"))
                                 .stroke(Color.TRANSPARENT)
                                 .strokeWidth(0)
                                 .build();

        svgPath5 = SvgPathBuilder.create()
                                 .path("M403.053,85.968C377.514,106.035 331.223,146.121 303.757,206.362C300.047,214.498 301.902,224.098 308.353,230.29C331.471,252.48 389.535,295.699 451.558,234.658C509.854,177.284 460.332,117.679 431.163,87.834C423.636,80.132 411.52,79.315 403.053,85.968Z")
                                 .fill(Color.web("#85E7FF"))
                                 .stroke(Color.TRANSPARENT)
                                 .strokeWidth(0)
                                 .build();

        svgPath6 = SvgPathBuilder.create()
                                 .path("M330.206,208.437C323.755,202.245 321.902,192.645 325.61,184.509C339.693,153.62 358.724,128.045 377.37,107.736C352.891,130.259 323.473,163.116 303.756,206.362C300.046,214.498 301.9,224.098 308.352,230.29C331.47,252.48 389.534,295.699 451.556,234.658C455.511,230.765 458.954,226.863 461.957,222.959C404.341,268.579 351.895,229.255 330.206,208.437Z")
                                 .fill(Color.web("#57D0E6"))
                                 .stroke(Color.TRANSPARENT)
                                 .strokeWidth(0)
                                 .build();

        svgPath7 = SvgPathBuilder.create()
                                 .path("M463.722,33.601C462.972,32.175 462.972,30.473 463.722,29.048L469.914,17.27C471.8,13.681 468.753,9.486 464.757,10.171L451.642,12.421C450.055,12.693 448.436,12.167 447.311,11.014L438.023,1.485C435.192,-1.419 430.262,0.184 429.677,4.196L427.763,17.365C427.531,18.959 426.531,20.336 425.087,21.049L413.154,26.939C409.517,28.734 409.517,33.918 413.154,35.713L425.087,41.603C426.531,42.316 427.532,43.693 427.763,45.287L429.677,58.456C430.26,62.469 435.192,64.072 438.023,61.167L447.311,51.638C448.435,50.485 450.054,49.959 451.642,50.231L464.757,52.481C468.753,53.166 471.801,48.971 469.914,45.382L463.722,33.601Z")
                                 .fill(Color.web("#FADC60"))
                                 .stroke(Color.TRANSPARENT)
                                 .strokeWidth(0)
                                 .build();

        svgNode = new SvgNode(svgPath1, svgPath2, svgPath3, svgPath4, svgPath5, svgPath6, svgPath7);
        svgNode.setPrefSize(512, 512);
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
