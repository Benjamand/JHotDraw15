package edit.bddtests;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import org.fusesource.jansi.internal.CLibrary;
import org.jhotdraw.datatransfer.ClipboardUtil;

import javax.swing.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class GivenStage extends Stage<GivenStage> {
    @ProvidedScenarioState
    JTextArea textArea;
    @ProvidedScenarioState
    String clipBoardContent;
    public GivenStage a_text_area_with_clipboard_content(String text){
        textArea = new JTextArea();
        clipBoardContent = text;
        return self();
    }
}
