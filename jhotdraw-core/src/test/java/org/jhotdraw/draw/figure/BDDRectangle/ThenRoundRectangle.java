package org.jhotdraw.draw.figure.BDDRectangle;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import org.jhotdraw.draw.figure.RoundRectangleFigure;

import static org.assertj.core.api.Assertions.assertThat;

public class ThenRoundRectangle extends Stage<ThenRoundRectangle> {

    @ExpectedScenarioState
    RoundRectangleFigure roundRectangle;

    public ThenRoundRectangle round_rectangle_should_have_size(double width, double height) {
        assertThat(roundRectangle.getBounds().width).isEqualTo(width);
        assertThat(roundRectangle.getBounds().height).isEqualTo(height);
        return self();
    }

    public ThenRoundRectangle round_rectangle_should_have_arc(double arcWidth, double arcHeight) {
        assertThat(roundRectangle.getArcWidth()).isEqualTo(arcWidth);
        assertThat(roundRectangle.getArcHeight()).isEqualTo(arcHeight);
        return self();
    }
}
