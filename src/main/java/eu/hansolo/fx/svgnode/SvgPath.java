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

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.BooleanPropertyBase;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.DoublePropertyBase;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ObjectPropertyBase;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.StringPropertyBase;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Effect;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.FillRule;


public class SvgPath {
    private String                   _svgPath;
    private StringProperty           svgPath;
    private Paint                    _fill;
    private ObjectProperty<Paint>    fill;
    private Paint                    _stroke;
    private ObjectProperty<Paint>    stroke;
    private double                   _strokeWidth;
    private DoubleProperty           strokeWidth;
    private FillRule                 _fillRule;
    private ObjectProperty<FillRule> fillRule;
    private Effect                   _effect;
    private ObjectProperty<Effect>   effect;
    private BooleanProperty          dirty;


    // ******************* Constructors ***************************************
    public SvgPath() {
        this("", Color.BLACK, Color.BLACK, 1.0, FillRule.NON_ZERO, null);
    }
    public SvgPath(final String svgPath, final Paint fill, final Paint stroke, final double strokeWidth, final FillRule fillRule, final Effect effect) {
        _svgPath     = svgPath;
        _fill        = fill;
        _stroke      = stroke;
        _strokeWidth = Helper.clamp(0, Double.MAX_VALUE, strokeWidth);
        _fillRule    = fillRule;
        _effect      = effect;
        dirty        = new BooleanPropertyBase(false) {
            @Override public Object getBean() { return SvgPath.this; }
            @Override public String getName() { return "dirty"; }
        };
    }


    // ******************* Methods ********************************************
    public String getSvgPath() { return null == svgPath ? _svgPath : svgPath.get(); }
    public void setSvgPath(final String svgPath) {
        if (null == this.svgPath) {
            _svgPath = svgPath;
            dirty.set(true);
        } else {
            this.svgPath.set(svgPath);
        }
    }
    public StringProperty svgPathProperty() {
        if (null == svgPath) {
            svgPath = new StringPropertyBase(_svgPath) {
                @Override protected void invalidated() { dirty.set(true); }
                @Override public Object getBean() { return SvgPath.this; }
                @Override public String getName() { return "svgPath"; }
            };
            _svgPath = null;
        }
        return svgPath;
    }

    public Paint getFill() { return null == fill ? _fill : fill.get(); }
    public void setFill(final Paint fill) {
        if (null == this.fill) {
            _fill = fill;
            dirty.set(true);
        } else {
            this.fill.set(fill);
        }
    }
    public ObjectProperty<Paint> fillProperty() {
        if (null == fill) {
            fill = new ObjectPropertyBase<>(_fill) {
                @Override protected void invalidated() { dirty.set(true); }
                @Override public Object getBean() { return SvgPath.this; }
                @Override public String getName() { return "fill"; }
            };
            _fill = null;
        }
        return fill;
    }

    public Paint getStroke() { return null == stroke ? _stroke : stroke.get(); }
    public void setStroke(final Paint stroke) {
        if (null == this.stroke) {
            _stroke = stroke;
            dirty.set(true);
        } else {
            this.stroke.set(stroke);
        }
    }
    public ObjectProperty<Paint> strokeProperty() {
        if (null == stroke) {
            stroke = new ObjectPropertyBase<>(_stroke) {
                @Override protected void invalidated() { dirty.set(true); }
                @Override public Object getBean() { return SvgPath.this; }
                @Override public String getName() { return "stroke"; }
            };
            _stroke = null;
        }
        return stroke;
    }

    public double getStrokeWidth() { return null == strokeWidth ? _strokeWidth : strokeWidth.get(); }
    public void setStrokeWidth(final double strokeWidth) {
        if (null == this.strokeWidth) {
            _strokeWidth = Helper.clamp(0, Double.MAX_VALUE, strokeWidth);
            dirty.set(true);
        } else {
            this.strokeWidth.set(strokeWidth);
        }
    }
    public DoubleProperty strokeWidthProperty() {
        if (null == strokeWidth) {
            strokeWidth = new DoublePropertyBase(_strokeWidth) {
                @Override protected void invalidated() {
                    set(Helper.clamp(0, Double.MAX_VALUE, get()));
                    dirty.set(true);
                }
                @Override public Object getBean() { return SvgPath.this; }
                @Override public String getName() { return "strokeWidth"; }
            };
        }
        return strokeWidth;
    }

    public FillRule getFillRule() { return null == fillRule ? _fillRule : fillRule.get(); }
    public void setFillRule(final FillRule fillRule) {
        if (null == this.fillRule) {
            _fillRule = fillRule;
            dirty.set(true);
        } else {
            this.fillRule.set(fillRule);
        }
    }
    public ObjectProperty<FillRule> fillRuleProperty() {
        if (null == fillRule) {
            fillRule = new ObjectPropertyBase<>(_fillRule) {
                @Override protected void invalidated() { dirty.set(true); }
                @Override public Object getBean() { return SvgPath.this; }
                @Override public String getName() { return "fillRule"; }
            };
            _fillRule = null;
        }
        return fillRule;
    }

    public Effect getEffect() { return null == effect ? _effect : effect.get(); }
    public void setEffect(final Effect effect) {
        if (null == this.effect) {
            _effect = effect;
            dirty.set(true);
        } else {
            this.effect.set(effect);
        }
    }
    public ObjectProperty<Effect> effectProperty() {
        if (null == effect) {
            effect = new ObjectPropertyBase<>(_effect) {
                @Override protected void invalidated() { dirty.set(true); }
                @Override public Object getBean() { return SvgPath.this; }
                @Override public String getName() { return "effect"; }
            };
            _effect = null;
        }
        return effect;
    }

    public ReadOnlyBooleanProperty dirtyProperty() { return dirty; }
    protected void dirtyReset() { dirty.set(false); }

    public void draw(final GraphicsContext ctx) {
        ctx.save();

        ctx.setEffect(getEffect());
        ctx.setFill(getFill());
        ctx.setStroke(getStroke());
        ctx.beginPath();

        ctx.closePath();

        ctx.fill();
        ctx.stroke();

        ctx.restore();
    }
}
