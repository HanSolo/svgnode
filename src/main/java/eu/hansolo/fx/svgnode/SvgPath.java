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
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;


public class SvgPath {
    private String                         _path;
    private StringProperty                 path;
    private Paint                          _fill;
    private ObjectProperty<Paint>          fill;
    private Paint                          _stroke;
    private ObjectProperty<Paint>          stroke;
    private double                         _lineWidth;
    private DoubleProperty                 lineWidth;
    private FillRule                       _fillRule;
    private ObjectProperty<FillRule>       fillRule;
    private Effect                         _effect;
    private ObjectProperty<Effect>         effect;
    private boolean                        _visible;
    private BooleanProperty                visible;
    private StrokeLineJoin                 _lineJoin;
    private ObjectProperty<StrokeLineJoin> lineJoin;
    private StrokeLineCap                  _lineCap;
    private ObjectProperty<StrokeLineCap>  lineCap;
    private BooleanProperty                dirty;


    // ******************* Constructors ***************************************
    public SvgPath() {
        this("", Color.BLACK, Color.BLACK, 1.0, FillRule.NON_ZERO, null, true);
    }
    public SvgPath(final String path, final Paint fill, final Paint stroke, final double lineWidth, final FillRule fillRule, final Effect effect, final boolean visible) {
        _path        = path;
        _fill        = fill;
        _stroke      = stroke;
        _lineWidth   = Helper.clamp(0, Double.MAX_VALUE, lineWidth);
        _fillRule    = fillRule;
        _effect      = effect;
        _visible     = visible;
        _lineJoin    = StrokeLineJoin.MITER;
        _lineCap     = StrokeLineCap.SQUARE;
        dirty        = new BooleanPropertyBase(false) {
            @Override public Object getBean() { return SvgPath.this; }
            @Override public String getName() { return "dirty"; }
        };
    }


    // ******************* Methods ********************************************
    public String getPath() { return null == path ? _path : path.get(); }
    public void setPath(final String path) {
        if (null == this.path) {
            _path = path;
            dirty.set(true);
        } else {
            this.path.set(path);
        }
    }
    public StringProperty pathProperty() {
        if (null == path) {
            path = new StringPropertyBase(_path) {
                @Override protected void invalidated() { dirty.set(true); }
                @Override public Object getBean() { return SvgPath.this; }
                @Override public String getName() { return "path"; }
            };
            _path = null;
        }
        return path;
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

    public double getLineWidth() { return null == lineWidth ? _lineWidth : lineWidth.get(); }
    public void setLineWidth(final double lineWidth) {
        if (null == this.lineWidth) {
            _lineWidth = Helper.clamp(0, Double.MAX_VALUE, lineWidth);
            dirty.set(true);
        } else {
            this.lineWidth.set(lineWidth);
        }
    }
    public DoubleProperty lineWidthProperty() {
        if (null == lineWidth) {
            lineWidth = new DoublePropertyBase(_lineWidth) {
                @Override protected void invalidated() {
                    set(Helper.clamp(0, Double.MAX_VALUE, get()));
                    dirty.set(true);
                }
                @Override public Object getBean() { return SvgPath.this; }
                @Override public String getName() { return "lineWidth"; }
            };
        }
        return lineWidth;
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

    public boolean isVisible() { return null == visible ? _visible : visible.get(); }
    public void setVisible(final boolean visible) {
        if (null == this.visible) {
            _visible = visible;
            dirty.set(true);
        } else {
            this.visible.set(visible);
        }
    }
    public BooleanProperty visibleProperty() {
        if (null == visible) {
            visible = new BooleanPropertyBase(_visible) {
                @Override protected void invalidated() { dirty.set(true); }
                @Override public Object getBean() { return SvgPath.this; }
                @Override public String getName() { return "visible"; }
            };
        }
        return visible;
    }

    public boolean isDirty() { return dirty.get(); }
    protected void dirtyReset() { dirty.set(false); }
    public ReadOnlyBooleanProperty dirtyProperty() { return dirty; }

    public StrokeLineJoin getLineJoin() { return null == lineJoin ? _lineJoin : lineJoin.get(); }
    public void setLineJoin(final StrokeLineJoin lineJoin) {
        if (null == this.lineJoin) {
            _lineJoin = lineJoin;
            dirty.set(true);
        } else {
            this.lineJoin.set(lineJoin);
        }
    }
    public ObjectProperty<StrokeLineJoin> lineJoinProperty() {
        if (null == lineJoin) {
            lineJoin = new ObjectPropertyBase<StrokeLineJoin>(_lineJoin) {
                @Override protected void invalidated() { dirty.set(true); }
                @Override public Object getBean() { return SvgPath.this; }
                @Override public String getName() { return "lineJoin"; }
            };
            _lineJoin = null;
        }
        return lineJoin;
    }

    public StrokeLineCap getLineCap() { return null == lineCap ? _lineCap : lineCap.get(); }
    public void setLineCap(final StrokeLineCap lineCap) {
        if (null == this.lineCap) {
            _lineCap = lineCap;
            dirty.set(true);
        } else {
            this.lineCap.set(lineCap);
        }
    }
    public ObjectProperty<StrokeLineCap> lineCapProperty() {
        if (null == lineCap) {
            lineCap = new ObjectPropertyBase<StrokeLineCap>(_lineCap) {
                @Override protected void invalidated() { dirty.set(true); }
                @Override public Object getBean() { return SvgPath.this; }
                @Override public String getName() { return "lineCap"; }
            };
            _lineCap = null;
        }
        return lineCap;
    }
    
    public void draw(final GraphicsContext ctx) {
        if (isVisible()) {

            ctx.save();

            ctx.setEffect(getEffect());
            ctx.setLineJoin(getLineJoin());
            ctx.setLineCap(getLineCap());
            ctx.setLineWidth(getLineWidth());
            ctx.setStroke(getStroke());
            ctx.setFill(getFill());
            
            ctx.beginPath();
            ctx.appendSVGPath(getPath());
            ctx.fill();
            ctx.stroke();
            
            ctx.restore();
        }
    }
}
