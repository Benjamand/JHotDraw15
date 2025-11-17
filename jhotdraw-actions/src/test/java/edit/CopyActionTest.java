package edit;
import org.jhotdraw.action.edit.CopyAction;
import org.jhotdraw.datatransfer.ClipboardUtil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import javax.swing.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class CopyActionTest {
    /**
     * Make Java assertions for invariants
     * Add boundary cases
     */

    JTextArea component;
    CopyAction copyAction;
    Clipboard mockClipboard;
    TransferHandler mockTransferHandler;

    @Before
    public void setUp() { //Mock of transferhandler and clipboard.
        component = new JTextArea("This is a test.");
        copyAction = new CopyAction(component);

        mockClipboard = Mockito.mock(Clipboard.class);
        mockTransferHandler = Mockito.mock(TransferHandler.class);
        component.setTransferHandler(mockTransferHandler);


        ClipboardUtil.setClipboard(mockClipboard);
        assertNotNull(copyAction);
    }

    // Verify is a function used to test behaviour of mocks.
    // eq checks that the arguments passed into the mock are equal to the expected values.
    // Becuase the components transferhandler was set to the mock. I can verify that exportToClipboard was called with the correct parameters.
    @Test
    public void testCopyTextAreaToClipboard() throws Exception {
        component.selectAll();
        copyAction.actionPerformed(null);
        verify(component.getTransferHandler()).exportToClipboard(
                eq(component),
                eq(mockClipboard),
                eq(TransferHandler.COPY)
        );
    }

    @Test
    public void testCopyTextAreaBoundaryOneChar() throws Exception {
        component.setText("A");
        component.selectAll();
        copyAction.actionPerformed(null);
        verify(component.getTransferHandler()).exportToClipboard(
                eq(component),
                eq(mockClipboard),
                eq(TransferHandler.COPY)
        );
    }
    @Test
    public void testCopyTextAreaBoundaryManyChars() throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            stringBuilder.append("A");
        }
        String str = stringBuilder.toString();
        component.setText(str);
        component.selectAll();
        copyAction.actionPerformed(null);
        verify(component.getTransferHandler()).exportToClipboard(
                eq(component),
                eq(mockClipboard),
                eq(TransferHandler.COPY)
        );
    }
    @Test
    public void testCopyTextAreaBoundaryUniqueSymbols() throws Exception {
        component.setText("½█ÖÇœÆ");
        component.selectAll();
        copyAction.actionPerformed(null);
        verify(component.getTransferHandler()).exportToClipboard(
                eq(component),
                eq(mockClipboard),
                eq(TransferHandler.COPY)
        );
    }

}