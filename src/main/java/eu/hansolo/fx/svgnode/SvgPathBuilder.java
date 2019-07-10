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
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.effect.Effect;
import javafx.scene.paint.Paint;
import javafx.scene.shape.FillRule;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;

import java.util.HashMap;


public class SvgPathBuilder<B extends SvgPathBuilder<B>> {
    private HashMap<String, Property> properties = new HashMap<>();


    // ******************** Constructors **************************************
    protected SvgPathBuilder() {}


    // ******************** Methods *******************************************
    public static final SvgPathBuilder create() {
        return new SvgPathBuilder();
    }

    public final B path(final String path) {
        properties.put("path", new SimpleStringProperty(path));
        return (B)this;
    }

    public final B fill(final Paint fill) {
        properties.put("fill", new SimpleObjectProperty<>(fill));
        return (B)this;
    }

    public final B stroke(final Paint stroke) {
        properties.put("stroke", new SimpleObjectProperty<>(stroke));
        return (B)this;
    }

    public final B strokeWidth(final double strokeWidth) {
        properties.put("strokeWidth", new SimpleDoubleProperty(strokeWidth));
        return (B)this;
    }

    public final B fillRule(final FillRule fillRule) {
        properties.put("fillRule", new SimpleObjectProperty<>(fillRule));
        return (B)this;
    }

    public final B effect(final Effect effect) {
        properties.put("effect", new SimpleObjectProperty<>(effect));
        return (B)this;
    }

    public final B visible(final boolean visible) {
        properties.put("visible", new SimpleBooleanProperty(visible));
        return (B)this;
    }

    public final B lineJoin(final StrokeLineJoin lineJoin) {
        properties.put("lineJoin", new SimpleObjectProperty<>(lineJoin));
        return (B)this;
    }

    public final B lineCap(final StrokeLineCap lineCap) {
        properties.put("lineCap", new SimpleObjectProperty<>(lineCap));
        return (B)this;
    }

    public final SvgPath build() {
        final SvgPath svgPath = new SvgPath();
        for (String key : properties.keySet()) {
            if ("path".equals(key)) {
                svgPath.setPath(((StringProperty) properties.get(key)).get());
            } else if ("fill".equals(key)) {
                svgPath.setFill(((ObjectProperty<Paint>) properties.get(key)).get());
            } else if ("stroke".equals(key)) {
                svgPath.setStroke(((ObjectProperty<Paint>) properties.get(key)).get());
            } else if ("lineWidth".equals(key)) {
                svgPath.setStrokeWidth(((DoubleProperty) properties.get(key)).get());
            } else if ("fillRule".equals(key)) {
                svgPath.setFillRule(((ObjectProperty<FillRule>) properties.get(key)).get());
            } else if ("effect".equals(key)) {
                svgPath.setEffect(((ObjectProperty<Effect>) properties.get(key)).get());
            } else if("visible".equals(key)) {
                svgPath.setVisible(((BooleanProperty) properties.get(key)).get());
            } else if("lineJoin".equals(key)) {
                svgPath.setLineJoin(((ObjectProperty<StrokeLineJoin>) properties.get(key)).get());
            } else if("lineCap".equals(key)) {
                svgPath.setLineCap(((ObjectProperty<StrokeLineCap>) properties.get(key)).get());
            }
        }
        return svgPath;
    }
}
