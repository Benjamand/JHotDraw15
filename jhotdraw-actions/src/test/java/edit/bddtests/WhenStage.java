package edit.bddtests;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import org.jhotdraw.action.edit.PasteAction;
import org.jhotdraw.datatransfer.ClipboardUtil;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import javax.swing.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.Transferable;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;


public class WhenStage extends Stage<WhenStage> {
    @ExpectedScenarioState
    JTextArea textArea;

    @ExpectedScenarioState
    String clipBoardContent;

    public WhenStage the_paste_action_is_performed () {
        Clipboard mockClipboard  = Mockito.mock(Clipboard.class);
        TransferHandler mockTransferHandler = Mockito.mock(TransferHandler.class);
        Transferable mockTransferable = Mockito.mock(Transferable.class);

        // Code snippet: Transferable t = ClipboardUtil.getClipboard().getContents(c);
        // Becuase of above code snippet taken from PasteAction actionPerformed method, I have to mock the clipboardUtil, and make it return a mocked Transferable object.
        try(MockedStatic<ClipboardUtil> mockedStatic = mockStatic(ClipboardUtil.class)){
            mockedStatic.when(ClipboardUtil::getClipboard).thenReturn(mockClipboard);
            Mockito.when(mockClipboard.getContents(Mockito.any())).thenReturn(mockTransferable);

        }
        textArea.setTransferHandler(mockTransferHandler);
        ClipboardUtil.setClipboard(mockClipboard);

        PasteAction pasteAction = new PasteAction(textArea); //sets the target
        pasteAction.actionPerformed(null);

        verify(textArea.getTransferHandler()).importData(
                eq(textArea),
               any(Transferable.class)
        ); // This verifies that the actionPerformed method functions as expected.

        textArea.setText(clipBoardContent); //manually set the text after testing the actionPerformed logic of PasteAction worked
        return self();
    }
}
