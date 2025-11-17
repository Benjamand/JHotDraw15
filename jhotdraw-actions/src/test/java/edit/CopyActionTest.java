package edit;
import org.jhotdraw.action.edit.CopyAction;
import org.jhotdraw.datatransfer.ClipboardUtil;
import org.junit.Before;
import org.junit.Test;
import javax.swing.*;
import java.awt.datatransfer.DataFlavor;
import static org.junit.Assert.*;
public class CopyActionTest {
    /**
     * Make Java assertions for invariants
     * Add boundary cases
     */

    JTextArea component;
    CopyAction copyAction;
    @Before
    public void setUp() {
        component = new JTextArea("This is a test.");
        copyAction = new CopyAction(component);
        assertNotNull(copyAction);
    }

    /*
    The test verifies that the CopyAction correctly copies the selected text from the JTextArea to the system clipboard.
     */
    @Test
    public void testCopyTextAreaToClipboard() throws Exception {
        component.selectAll();
        copyAction.actionPerformed(null);
        String result = ClipboardUtil.getClipboard().getData(DataFlavor.stringFlavor).toString();
        assertEquals("This is a test.", result);
    }
    @Test
    public void testCopyTextAreaBoundaryOneChar() throws Exception {
        component.setText("A");
        component.selectAll();
        copyAction.actionPerformed(null);
        String result = ClipboardUtil.getClipboard().getData(DataFlavor.stringFlavor).toString();
        assertEquals("A", result);
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
        String result = ClipboardUtil.getClipboard().getData(DataFlavor.stringFlavor).toString();
        assertEquals(str, result);
    }
    @Test
    public void testCopyTextAreaBoundaryUniqueSymbols() throws Exception {
        component.setText("½█ÖÇœÆ");
        component.selectAll();
        copyAction.actionPerformed(null);
        String result = ClipboardUtil.getClipboard().getData(DataFlavor.stringFlavor).toString();
        assertEquals("½█ÖÇœÆ", result);
    }

}