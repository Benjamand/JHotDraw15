package org.jhotdraw.draw.figure.BDDRectangle;


import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import org.jhotdraw.draw.figure.RoundRectangleFigure;


import java.awt.geom.Point2D;

public class WhenSettingBounds extends Stage<WhenSettingBounds> {

    @ExpectedScenarioState
    RoundRectangleFigure roundRectangle;

    public WhenSettingBounds bounds_are_set(double x1, double y1, double x2, double y2) {
        roundRectangle.setBounds(new Point2D.Double(x1, y1), new Point2D.Double(x2, y2));
        return self();
    }

    public WhenSettingBounds arc_is_set(double width, double height) {
        roundRectangle.setArc(width, height);
        return self();
    }
}
