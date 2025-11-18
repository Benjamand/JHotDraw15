package edit.bddtests;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;

import javax.swing.*;

import static org.junit.Assert.assertEquals;

public class ThenStage extends Stage<ThenStage> {
    @ExpectedScenarioState
    JTextArea textArea;

    public ThenStage the_text_area_should_contain(String expectedText){
        assertEquals(expectedText, textArea.getText());
        return self();
    }

}
