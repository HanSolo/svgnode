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

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Region;

import java.util.Arrays;
import java.util.List;


/**
 * User: hansolo
 * Date: 2019-07-10
 * Time: 07:14
 */
public class SvgNode extends Region {
    private static final double                  PREFERRED_WIDTH  = -1;
    private static final double                  PREFERRED_HEIGHT = -1;
    private static final double                  MINIMUM_WIDTH    = 50;
    private static final double                  MINIMUM_HEIGHT   = 50;
    private static final double                  MAXIMUM_WIDTH    = 4096;
    private static final double                  MAXIMUM_HEIGHT   = 4096;
    private              double                  size;
    private              double                  width;
    private              double                  height;
    private              Canvas                  canvas;
    private              GraphicsContext         ctx;
    private              ObservableList<SvgPath> shapes;
    private              ChangeListener<Boolean> dirtyListener;


    // ******************** Constructors **************************************
    public SvgNode() {
        this(new SvgPath[]{});
    }
    public SvgNode(final SvgPath... shapes) {
        this(Arrays.asList(shapes));
    }
    public SvgNode(final List<SvgPath> shapes) {
        getStylesheets().add(SvgNode.class.getResource("svg-node.css").toExternalForm());

        this.shapes = FXCollections.observableArrayList(shapes);

        initGraphics();
        registerListeners();
    }


    // ******************** Initialization ************************************
    private void initGraphics() {
        if (Double.compare(getPrefWidth(), 0.0) <= 0 || Double.compare(getPrefHeight(), 0.0) <= 0 || Double.compare(getWidth(), 0.0) <= 0 ||
            Double.compare(getHeight(), 0.0) <= 0) {
            if (getPrefWidth() > 0 && getPrefHeight() > 0) {
                setPrefSize(getPrefWidth(), getPrefHeight());
            } else {
                setPrefSize(PREFERRED_WIDTH, PREFERRED_HEIGHT);
            }
        }

        getStyleClass().setAll("svg-node");

        canvas = new Canvas(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        ctx    = canvas.getGraphicsContext2D();

        getChildren().setAll(canvas);
    }

    private void registerListeners() {
        widthProperty().addListener(o -> resize());
        heightProperty().addListener(o -> resize());
        shapes.addListener((ListChangeListener<SvgPath>) c -> {
            while(c.next()) {
                if (c.wasAdded()) {
                    c.getAddedSubList().forEach(svgPath -> svgPath.dirtyProperty().addListener(dirtyListener));
                } else if (c.wasRemoved()) {
                    c.getRemoved().forEach(svgPath -> svgPath.dirtyProperty().removeListener(dirtyListener));
                }
            }
            redraw();
        });
        shapes.forEach(svgPath -> svgPath.dirtyProperty().addListener(dirtyListener));
    }


    // ******************** Methods *******************************************
    @Override public void layoutChildren() {
        super.layoutChildren();
    }

    @Override protected double computeMinWidth(final double HEIGHT) { return MINIMUM_WIDTH; }
    @Override protected double computeMinHeight(final double WIDTH) { return MINIMUM_HEIGHT; }
    @Override protected double computePrefWidth(final double HEIGHT) { return super.computePrefWidth(HEIGHT); }
    @Override protected double computePrefHeight(final double WIDTH) { return super.computePrefHeight(WIDTH); }
    @Override protected double computeMaxWidth(final double HEIGHT) { return MAXIMUM_WIDTH; }
    @Override protected double computeMaxHeight(final double WIDTH) { return MAXIMUM_HEIGHT; }


    // ******************** Resizing ******************************************
    private void resize() {
        width  = getWidth() - getInsets().getLeft() - getInsets().getRight();
        height = getHeight() - getInsets().getTop() - getInsets().getBottom();
        size   = width < height ? width : height;

        if (width > 0 && height > 0) {
            canvas.setWidth(width);
            canvas.setHeight(height);
            canvas.relocate((getWidth() - width) * 0.5, (getHeight() - height) * 0.5);

            redraw();
        }
    }

    private void redraw() {
        ctx.clearRect(0, 0, width, height);

        shapes.forEach(svgPath -> {
            svgPath.draw(ctx);
            svgPath.dirtyReset();
        });
    }
}
