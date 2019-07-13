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
import javafx.geometry.Dimension2D;
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
    private static final double                  MINIMUM_WIDTH    = 5;
    private static final double                  MINIMUM_HEIGHT   = 5;
    private static final double                  MAXIMUM_WIDTH    = 4096;
    private static final double                  MAXIMUM_HEIGHT   = 4096;
    private static       double                  aspectRatio;
    private              boolean                 keepAspect;
    private              boolean                 dirty;
    private              double                  size;
    private              double                  width;
    private              double                  height;
    private              Canvas                  canvas;
    private              GraphicsContext         ctx;
    private              ObservableList<SvgPath> shapes;
    private              double                  scaleX;
    private              double                  scaleY;
    private              ChangeListener<Boolean> dirtyListener;


    // ******************** Constructors **************************************
    public SvgNode() {
        this(new SvgPath[]{});
    }
    public SvgNode(final SvgPath... shapes) {
        this(true, Arrays.asList(shapes));
    }
    public SvgNode(final boolean keepAspect, final List<SvgPath> shapes) {
        this.shapes        = FXCollections.observableArrayList(shapes);
        this.scaleX        = 1.0;
        this.scaleY        = 1.0;
        this.keepAspect    = keepAspect;
        this.dirty         = true;
        this.dirtyListener = (o, ov, nv) -> redraw();

        initGraphics();
        registerListeners();
    }


    // ******************** Initialization ************************************
    private void initGraphics() {
        setPrefSize(PREFERRED_WIDTH, PREFERRED_HEIGHT);

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
    @Override protected double computeMinWidth(final double HEIGHT) { return MINIMUM_WIDTH; }
    @Override protected double computeMinHeight(final double WIDTH) { return MINIMUM_HEIGHT; }
    @Override protected double computePrefWidth(final double HEIGHT) { return super.computePrefWidth(HEIGHT); }
    @Override protected double computePrefHeight(final double WIDTH) { return super.computePrefHeight(WIDTH); }
    @Override protected double computeMaxWidth(final double HEIGHT) { return MAXIMUM_WIDTH; }
    @Override protected double computeMaxHeight(final double WIDTH) { return MAXIMUM_HEIGHT; }

    public ObservableList<SvgPath> getShapes() { return shapes; }

    public Dimension2D getCanvasDimension() { return new Dimension2D(canvas.getWidth(), canvas.getHeight()); }
    public void setCanvasDimension(final double width, final double height) {
        canvas.setWidth(width);
        canvas.setHeight(height);
    }

    public boolean getKeepAspect() { return keepAspect; }
    public void setKeepAspect(final boolean keepAspect) {
        this.keepAspect = keepAspect;
        resize();
    }


    // ******************** Resizing ******************************************
    private void resize() {
        width  = getWidth() - getInsets().getLeft() - getInsets().getRight();
        height = getHeight() - getInsets().getTop() - getInsets().getBottom();
        size   = width < height ? width : height;

        if (width > 0 && height > 0) {
            if (canvas.getWidth() == Region.USE_PREF_SIZE) { canvas.setWidth(width); }
            if (canvas.getHeight() == Region.USE_PREF_SIZE) { canvas.setHeight(height); }
            if (canvas.getWidth() != Region.USE_PREF_SIZE && canvas.getHeight() != Region.USE_PREF_SIZE) {
                aspectRatio = canvas.getHeight() / canvas.getWidth();
            }

            if (keepAspect) {
                if (aspectRatio * width > height) {
                    width = 1 / (aspectRatio / height);
                } else if (1 / (aspectRatio / height) > width) {
                    height = aspectRatio * width;
                }
            }

            scaleX = width / canvas.getWidth();
            scaleY = height / canvas.getHeight();

            canvas.relocate((getWidth() - width) * 0.5, (getHeight() - height) * 0.5);

            canvas.setScaleX(scaleX);
            canvas.setScaleY(scaleY);
            canvas.setTranslateX((canvas.getWidth() * scaleX - canvas.getWidth()) / 2);
            canvas.setTranslateY((canvas.getHeight() * scaleY - canvas.getHeight()) / 2);

            if (dirty) { redraw(); }
        }
    }

    private void redraw() {
        ctx.clearRect(0, 0, width, height);
        shapes.forEach(svgPath -> {
            svgPath.draw(ctx);
            svgPath.dirtyReset();
        });
        dirty = false;
    }
}
