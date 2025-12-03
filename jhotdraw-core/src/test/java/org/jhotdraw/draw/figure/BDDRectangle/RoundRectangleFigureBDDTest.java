package org.jhotdraw.draw.figure.BDDRectangle;

import com.tngtech.jgiven.junit5.ScenarioTest;
import org.junit.jupiter.api.Test;

public class RoundRectangleFigureBDDTest extends
        ScenarioTest<GivenRoundRectangle, WhenSettingBounds, ThenRoundRectangle> {

    @Test
    public void roundRectangle_resizes_when_bounds_are_set() {
        given().a_round_rectangle();
        when().bounds_are_set(0, 0, 120, 60);
        then().round_rectangle_should_have_size(120, 60);
    }

    @Test
    public void roundRectangle_arc_can_be_set() {
        given().a_round_rectangle();
        when().arc_is_set(15, 25);
        then().round_rectangle_should_have_arc(15, 25);
    }
}
