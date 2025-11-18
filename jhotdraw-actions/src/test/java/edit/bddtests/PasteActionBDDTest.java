package edit.bddtests;
import com.tngtech.jgiven.junit.ScenarioTest;
import org.junit.Test;

public class PasteActionBDDTest extends ScenarioTest<GivenStage, WhenStage, ThenStage> {
    @Test
    public void should_past_clipboard_content_into_text_field(){
        given().a_text_area_with_clipboard_content("Test!"); //An empty textArea with text in clipboard
        when().the_paste_action_is_performed();
        then().the_text_area_should_contain("Test!");
    }
}
