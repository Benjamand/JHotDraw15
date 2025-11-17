package edit;
import org.jhotdraw.action.edit.CutAction;
import org.jhotdraw.datatransfer.ClipboardUtil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.sound.sampled.Clip;
import javax.swing.*;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

public class CutActionTest {
    JTextArea component;
    CutAction cutAction;
    Clipboard mockClipboard;
    TransferHandler mockTransferHandler;


    @Before
    public void setUp() {
        component = new JTextArea("This is a test.");
        cutAction = new CutAction(component);
        mockClipboard = Mockito.mock(Clipboard.class);
        ClipboardUtil.setClipboard(mockClipboard);
        mockTransferHandler = Mockito.mock(TransferHandler.class);
        component.setTransferHandler(mockTransferHandler);

        assertNotNull(cutAction);
    }
    @Test
    public void testActionPerformed() {
        component.selectAll();
        cutAction.actionPerformed(null);
        verify(component.getTransferHandler()).exportToClipboard(
                eq(component),
                eq(mockClipboard),
                eq(TransferHandler.MOVE)
        );
    }
    @Test
    public void TestActionSavesToClipboard() throws Exception {
        component.selectAll();
        cutAction.actionPerformed(null);
        verify(component.getTransferHandler()).exportToClipboard(
                eq(component),
                eq(mockClipboard),
                eq(TransferHandler.MOVE)
        );
    }
    @Test
    public void testCutActionTextAreaBoundaryOneChar() throws Exception {
        component.setText("A");
        component.selectAll();
        cutAction.actionPerformed(null);
        verify(component.getTransferHandler()).exportToClipboard(
                eq(component),
                eq(mockClipboard),
                eq(TransferHandler.MOVE)
        );
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

        verify(component.getTransferHandler()).exportToClipboard(
                eq(component),
                eq(mockClipboard),
                eq(TransferHandler.MOVE)
        );
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
        verify(component.getTransferHandler()).exportToClipboard(
                eq(component),
                eq(mockClipboard),
                eq(TransferHandler.MOVE)
        );
    }
}
