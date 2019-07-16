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
import javafx.scene.transform.Affine;


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

            SVGParser p = new SVGParser(getPath());
            p.allowComma = false;
            boolean largeArcFlag      = false, sweepFlag = false;
            double  rx, ry, a;
            double  x, y, lastX       = -Double.MAX_VALUE, lastY = -Double.MAX_VALUE;
            double  c1x, c1y, lastC1X = -Double.MAX_VALUE, lastC1Y = -Double.MAX_VALUE;
            double  c2x, c2y, lastC2X = -Double.MAX_VALUE, lastC2Y = -Double.MAX_VALUE;
            long    elementCount      = 0;
            while (!p.isDone()) {
                p.allowComma = false;
                char cmd = p.getChar();
                switch (cmd) {
                    case 'M':
                        x = p.f();
                        y = p.f();
                        ctx.moveTo(x, y);
                        lastX = x;
                        lastY = y;
                        while (p.nextIsNumber()) {
                            x = p.f();
                            y = p.f();
                            ctx.lineTo(x, y);
                            lastX = x;
                            lastY = y;
                        }
                        elementCount++;
                        break;
                    case 'm':
                        if (elementCount > 0) {
                            x = p.f() + lastX;
                            y = p.f() + lastY;
                            ctx.moveTo(x, y); // move relative
                            lastX = x;
                            lastY = y;
                        } else {
                            x = p.f();
                            y = p.f();
                            ctx.moveTo(x, y);
                            lastX = x;
                            lastY = y;
                        }
                        while (p.nextIsNumber()) {
                            x = p.f() + lastX;
                            y = p.f() + lastY;
                            ctx.lineTo(x, y); // move relative
                            lastX = x;
                            lastY = y;
                        }
                        elementCount++;
                        break;
                    case 'L':
                        do {
                            x = p.f();
                            y = p.f();
                            ctx.lineTo(x, y);
                            lastX = x;
                            lastY = y;
                        } while (p.nextIsNumber());
                        elementCount++;
                        break;
                    case 'l':
                        do {
                            x = p.f() + lastX;
                            y = p.f() + lastY;
                            ctx.lineTo(x, y); // move relative
                            lastX = x;
                            lastY = y;
                        } while (p.nextIsNumber());
                        elementCount++;
                        break;
                    case 'H':
                        do {
                            x = p.f();
                            ctx.lineTo(x, lastY);
                            lastX = x;
                        } while (p.nextIsNumber());
                        elementCount++;
                        break;
                    case 'h':
                        do {
                            x = p.f() + lastX;
                            ctx.lineTo(x, lastY); // move relative
                            lastX = x;
                        } while (p.nextIsNumber());
                        elementCount++;
                        break;
                    case 'V':
                        do {
                            y = p.f();
                            ctx.lineTo(lastX, y);
                            lastY = y;
                        } while (p.nextIsNumber());
                        elementCount++;
                        break;
                    case 'v':
                        do {
                            y = p.f() + lastY;
                            ctx.lineTo(lastX, y); // move relative
                            lastY = y;
                        } while (p.nextIsNumber());
                        elementCount++;
                        break;
                    case 'Q':
                        do {
                            c1x = p.f();
                            c1y = p.f();
                            x = p.f();
                            y = p.f();
                            ctx.quadraticCurveTo(c1x, c1y, x, y);
                            lastC1X = c1x;
                            lastC1Y = c1y;
                            lastX = x;
                            lastY = y;
                        } while (p.nextIsNumber());
                        elementCount++;
                        break;
                    case 'q':
                        do {
                            c1x = p.f() + lastX;
                            c1y = p.f() + lastY;
                            x = p.f() + lastX;
                            y = p.f() + lastY;
                            ctx.quadraticCurveTo(c1x, c1y, x, y); // relative move
                            lastC1X = c1x;
                            lastC1Y = c1y;
                            lastX = x;
                            lastY = y;
                        } while (p.nextIsNumber());
                        elementCount++;
                        break;
                /*
                case 'T':
                    do {
                        ctx.quadraticCurveToSmooth(p.f(), p.f());
                    } while (p.nextIsNumber());
                    break;
                case 't':
                    do {
                        ctx.quadraticCurveToSmoothRel(p.f(), p.f());
                    } while (p.nextIsNumber());
                    break;
                    */
                    case 'C':
                        do {
                            c1x = p.f();
                            c1y = p.f();
                            c2x = p.f();
                            c2y = p.f();
                            x = p.f();
                            y = p.f();
                            ctx.bezierCurveTo(c1x, c1y, c2x, c2y, x, y);
                            lastC1X = c1x;
                            lastC1Y = c1y;
                            lastC2X = c2x;
                            lastC2Y = c2y;
                            lastX = x;
                            lastY = y;
                        } while (p.nextIsNumber());
                        elementCount++;
                        break;
                    case 'c':
                        do {
                            c1x = p.f() + lastX;
                            c1y = p.f() + lastY;
                            c2x = p.f() + lastX;
                            c2y = p.f() + lastY;
                            x = p.f() + lastX;
                            y = p.f() + lastY;
                            ctx.bezierCurveTo(c1x, c1y, c2x, c2y, x, y);
                            lastC1X = c1x;
                            lastC1Y = c1y;
                            lastC2X = c2x;
                            lastC2Y = c2y;
                            lastX = x;
                            lastY = y;
                        } while (p.nextIsNumber());
                        elementCount++;
                        break;
                    case 'S':
                        do {
                            c1x = lastX * 2.0d - lastC2X;
                            c1y = lastY * 2.0d - lastC2Y;
                            c2x = p.f();
                            c2y = p.f();
                            x = p.f();
                            y = p.f();
                            ctx.bezierCurveTo(c1x, c1y, c2x, c2y, x, y);
                            lastC1X = c1x;
                            lastC1Y = c1y;
                            lastC2X = c2x;
                            lastC2Y = c2y;
                            lastX = x;
                            lastY = y;
                        } while (p.nextIsNumber());
                        elementCount++;
                        break;
                    case 's':
                        do {
                            c1x = lastX * 2.0d - lastC2X;
                            c1y = lastY * 2.0d - lastC2Y;
                            c2x = p.f() + lastX;
                            c2y = p.f() + lastY;
                            x = p.f() + lastX;
                            y = p.f() + lastY;
                            ctx.bezierCurveTo(c1x, c1y, c2x, c2y, x, y);
                            lastC1X = c1x;
                            lastC1Y = c1y;
                            lastC2X = c2x;
                            lastC2Y = c2y;
                            lastX = x;
                            lastY = y;
                        } while (p.nextIsNumber());
                        elementCount++;
                        break;
                    case 'A':
                        do {
                            rx = p.f();
                            ry = p.f();
                            a = p.f();
                            largeArcFlag = p.b();
                            sweepFlag = p.b();
                            x = p.f();
                            y = p.f();
                            drawArcTo(ctx, lastX, lastY, rx, ry, a, x, y, largeArcFlag, sweepFlag);
                            lastX = x;
                            lastY = y;
                        } while (p.nextIsNumber());
                        elementCount++;
                        break;
                    case 'a':
                        do {
                            rx = p.f();
                            ry = p.f();
                            a = p.f();
                            largeArcFlag = p.b();
                            sweepFlag = p.b();
                            x = p.f() + lastX;
                            y = p.f() + lastY;
                            drawArcTo(ctx, lastX, lastY, rx, ry, a, x, y, largeArcFlag, sweepFlag);
                            lastX = x;
                            lastY = y;
                        } while (p.nextIsNumber());
                        elementCount++;
                        break;
                    case 'Z':
                    case 'z':
                        ctx.closePath();
                        elementCount++;
                        break;
                    default:
                        throw new IllegalArgumentException("invalid command (" + cmd + ") in SVG polygon at pos=" + p.pos);
                }
                p.allowComma = false;
            }

            ctx.fill();
            ctx.stroke();

            ctx.restore();
        }
    }

    static class SVGParser {
        final String svgpath;
        final int    length;
        int          pos;
        boolean      allowComma;
        double       lastX;
        double       lastY;


        public SVGParser(final String SVG_PATH) {
            svgpath = SVG_PATH;
            length  = SVG_PATH.length();
        }


        public boolean isDone() { return (toNextNonWsp() >= length); }

        public char getChar() { return svgpath.charAt(pos++); }

        public boolean nextIsNumber() {
            if (toNextNonWsp() < length) {
                switch (svgpath.charAt(pos)) {
                    case '-':
                    case '+':
                    case '0': case '1': case '2': case '3': case '4':
                    case '5': case '6': case '7': case '8': case '9':
                    case '.': return true;
                }
            }
            return false;
        }

        public double f() { return getDouble(); }

        public double getDouble() {
            int start  = toNextNonWsp();
            int end    = toNumberEnd();
            allowComma = true;

            if (start < end) {
                String flstr = svgpath.substring(start, end);
                try {
                    return Double.parseDouble(flstr);
                } catch (NumberFormatException e) { }
                throw new IllegalArgumentException("invalid double (" + flstr + ") in polygon at pos=" + start);
            }
            throw new IllegalArgumentException("end of polygon looking for double");
        }

        public boolean b() {
            toNextNonWsp();
            allowComma = true;
            if (pos < length) {
                char flag = svgpath.charAt(pos);
                switch (flag) {
                    case '0': pos++; return false;
                    case '1': pos++; return true;
                }
                throw new IllegalArgumentException("invalid boolean flag (" + flag + ") in polygon at pos=" + pos);
            }
            throw new IllegalArgumentException("end of polygon looking for boolean");
        }

        private int toNextNonWsp() {
            boolean canBeComma = allowComma;
            while (pos < length) {
                switch (svgpath.charAt(pos)) {
                    case ',':
                        if (!canBeComma) { return pos; }
                        canBeComma = false;
                        break;
                    case ' ':
                    case '\t':
                    case '\r':
                    case '\n':
                        break;
                    default:
                        return pos;
                }
                pos++;
            }
            return pos;
        }

        private int toNumberEnd() {
            boolean allowSign  = true;
            boolean hasExp     = false;
            boolean hasDecimal = false;
            while (pos < length) {
                switch (svgpath.charAt(pos)) {
                    case '-':
                    case '+':
                        if (!allowSign) return pos;
                        allowSign = false;
                        break;
                    case '0': case '1': case '2': case '3': case '4':
                    case '5': case '6': case '7': case '8': case '9':
                        allowSign = false;
                        break;
                    case 'E': case 'e':
                        if (hasExp) return pos;
                        hasExp = allowSign = true;
                        break;
                    case '.':
                        if (hasExp || hasDecimal) return pos;
                        hasDecimal = true;
                        allowSign  = false;
                        break;
                    default:
                        return pos;
                }
                pos++;
            }
            return pos;
        }
    }

    private static void drawArcTo(
            final GraphicsContext ctx,
            final double x0, final double y0,
            final double radiusX, final double radiusY,
            final double xAxisRotation,
            final double x, final double y,
            final boolean largeArcFlag, final boolean sweepFlag) {

        // Compute the half distance between the current and the final point
        final double dx2 = (x0 - x) / 2.0;
        final double dy2 = (y0 - y) / 2.0;
        // Convert angle from degrees to radians
        final double xAxisRotationR = Math.toRadians(xAxisRotation);
        final double cosAngle = Math.cos(xAxisRotationR);
        final double sinAngle = Math.sin(xAxisRotationR);

        //
        // Step 1 : Compute (x1, y1)
        //
        final double x1 = ( cosAngle * dx2 + sinAngle * dy2);
        final double y1 = (-sinAngle * dx2 + cosAngle * dy2);
        // Ensure radii are large enough
        double rx = Math.abs(radiusX);
        double ry = Math.abs(radiusY);
        double Prx = rx * rx;
        double Pry = ry * ry;
        final double Px1 = x1 * x1;
        final double Py1 = y1 * y1;
        // check that radii are large enough
        final double radiiCheck = Px1/Prx + Py1/Pry;
        if (radiiCheck > 1.0) {
            rx = Math.sqrt(radiiCheck) * rx;
            ry = Math.sqrt(radiiCheck) * ry;
            if (Double.isNaN(rx) || Double.isNaN(ry)) {
                ctx.lineTo(x, y);
                return;
            }
            Prx = rx * rx;
            Pry = ry * ry;
        }

        //
        // Step 2 : Compute (cx1, cy1)
        //
        double sign = ((largeArcFlag == sweepFlag) ? -1.0 : 1.0);
        double sq = ((Prx*Pry)-(Prx*Py1)-(Pry*Px1)) / ((Prx*Py1)+(Pry*Px1));
        sq = (sq < 0.0) ? 0.0 : sq;
        final double coef = (sign * Math.sqrt(sq));
        final double cx1 = coef * ((rx * y1) / ry);
        final double cy1 = coef * -((ry * x1) / rx);

        //
        // Step 3 : Compute (cx, cy) from (cx1, cy1)
        //
        final double sx2 = (x0 + x) / 2.0;
        final double sy2 = (y0 + y) / 2.0;
        final double cx = sx2 + (cosAngle * cx1 - sinAngle * cy1);
        final double cy = sy2 + (sinAngle * cx1 + cosAngle * cy1);

        //
        // Step 4 : Compute the angleStart (angle1) and the angleExtent (dangle)
        //
        final double ux = (x1 - cx1) / rx;
        final double uy = (y1 - cy1) / ry;
        final double vx = (-x1 - cx1) / rx;
        final double vy = (-y1 - cy1) / ry;
        // Compute the angle start
        double n = Math.sqrt((ux * ux) + (uy * uy));
        double p = ux; // (1 * ux) + (0 * uy)
        sign = ((uy < 0.0) ? -1.0 : 1.0);
        double angleStart = Math.toDegrees(sign * Math.acos(p / n));

        // Compute the angle extent
        n = Math.sqrt((ux * ux + uy * uy) * (vx * vx + vy * vy));
        p = ux * vx + uy * vy;
        sign = ((ux * vy - uy * vx < 0.0) ? -1.0 : 1.0);
        double angleExtent = Math.toDegrees(sign * Math.acos(p / n));
        if (!sweepFlag && (angleExtent > 0)) {
            angleExtent -= 360.0;
        } else if (sweepFlag && (angleExtent < 0)) {
            angleExtent += 360.0;
        }
        angleExtent = angleExtent % 360;
        angleStart = angleStart % 360;

        Affine a = new Affine();
        a.appendRotation(xAxisRotation, cx, cy);
        Affine ar = new Affine();
        ar.appendRotation(-xAxisRotation, cx, cy);

        ctx.transform(a);
        ctx.arc(cx, cy, rx, ry, -angleStart, -angleExtent);
        ctx.transform(ar);
    }
}
