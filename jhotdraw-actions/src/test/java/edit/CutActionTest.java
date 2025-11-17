package edit;
import org.jhotdraw.action.edit.CutAction;
import org.jhotdraw.datatransfer.ClipboardUtil;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;

import java.awt.datatransfer.DataFlavor;

import static org.junit.Assert.*;

public class CutActionTest {
    JTextArea component;
    CutAction cutAction;
    @Before
    public void setUp() {
        component = new JTextArea("This is a test.");
        cutAction = new CutAction(component);
        assertNotNull(cutAction);
    }
    @Test
    public void testActionPerformed() {
        component.selectAll();
        cutAction.actionPerformed(null);
        assertEquals("", component.getText());
    }
    @Test
    public void TestActionSavesToClipboard() throws Exception {
        component.selectAll();
        cutAction.actionPerformed(null);
        String result = ClipboardUtil.getClipboard().getData(DataFlavor.stringFlavor).toString();
        assertEquals("This is a test.", result);
    }
    @Test
    public void testCutActionTextAreaBoundaryOneChar() throws Exception {
        component.setText("A");
        component.selectAll();
        cutAction.actionPerformed(null);
        String result = ClipboardUtil.getClipboard().getData(DataFlavor.stringFlavor).toString();
        assertEquals("A", result);
    }
    @Test
    public void testCutActionAreaBoundaryManyChars() throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            stringBuilder.append("A");
        }
        String str = stringBuilder.toString();
        component.setText(str);
        component.selectAll();
        int attempts = 0;
        while (true){
            try{
                cutAction.actionPerformed(null);
                break;
            }catch (IllegalStateException e){
                if (attempts++ > 5){
                    throw e;
                }
            }
        }

        String result = ClipboardUtil.getClipboard().getData(DataFlavor.stringFlavor).toString();
        assertEquals(str, result);
    }
    @Test
    public void testCutActionAreaBoundaryUniqueSymbols() throws Exception { // Fejler også noglegange her, java.lang.IllegalStateException: cannot open system clipboard
        component.setText("½█ÖÇœÆ");
        component.selectAll();
        int attempts = 0;
        while (true){
            try{
                cutAction.actionPerformed(null);
                break;
            }catch (IllegalStateException e){
                if(attempts++ > 5){
                    throw e;
                }
            }
        }
        String result = ClipboardUtil.getClipboard().getData(DataFlavor.stringFlavor).toString();
        assertEquals("½█ÖÇœÆ", result);
    }
}
