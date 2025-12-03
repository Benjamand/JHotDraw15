package org.jhotdraw.draw.figure.BDDRectangle;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import org.jhotdraw.draw.figure.RoundRectangleFigure;


public class GivenRoundRectangle extends Stage<GivenRoundRectangle> {

    @ProvidedScenarioState
    RoundRectangleFigure roundRectangle;

    public GivenRoundRectangle a_round_rectangle() {
        roundRectangle = new RoundRectangleFigure();
        return self();
    }
}
