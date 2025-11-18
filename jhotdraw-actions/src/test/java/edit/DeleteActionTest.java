package edit;
import org.jhotdraw.action.edit.CutAction;
import org.jhotdraw.action.edit.DeleteAction;
import org.jhotdraw.datatransfer.ClipboardUtil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.swing.*;
import javax.swing.text.JTextComponent;

import java.awt.datatransfer.Clipboard;
import java.awt.event.ActionEvent;

import static org.junit.Assert.*;

public class DeleteActionTest {
    JTextComponent component;
    DeleteAction deleteAction;
    ActionEvent actionEvent;
    Clipboard mockClipboard;
    TransferHandler mockTransferHandler;

    /**
     * JTextComponent c = getTextComponent(e);
     * Inside deleteNextChar, otherwise it takes the selected component
     * The test creates an ActionEvent with the component as the source, for the deleteNextChar method to work correctly
     */

    @Before
    public void setUp() {
        component = new JTextArea("This is a test.");
        deleteAction = new DeleteAction(component);

        actionEvent = new ActionEvent(component, ActionEvent.ACTION_PERFORMED, null);
        assertNotNull(deleteAction);
    }
    @Test
    public void testActionPerformed() {
        assertNotNull(component); // Ensure component is not null, becuase of check in actionPerformed
        assertTrue(component.isEditable()); // Ensure component is editable, because of check in actionPerformed
        component.selectAll();
        deleteAction.actionPerformed(actionEvent);
        assertEquals("", component.getText());
    }

    @Test
    public void testDeleteNextChar() {
        component.selectAll();
        deleteAction.deleteNextChar(actionEvent);
        System.out.println(component.getText());
        assertEquals("", component.getText());
    }
}
