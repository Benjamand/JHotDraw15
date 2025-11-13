package fontTests;

import org.jhotdraw.gui.JFontChooser;
import org.jhotdraw.gui.fontchooser.DefaultFontChooserModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static org.junit.Assert.*;

public class DefaultFontChooserModelTests {

     @BeforeEach
     void setUp() {
          MockitoAnnotations.openMocks(this);
     }


     @Test
     // Invariant test
     // Regardless of which fonts are set, the models root node has exactly 10 children.
     public void setFontsTest() {
          DefaultFontChooserModel model = new DefaultFontChooserModel();
          model.setFonts(new Font[] { new Font("Arial", Font.PLAIN, 12) });
          Assertions.assertEquals(10, model.getChildCount(model.getRoot()));

          model.setFonts(GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts());
          assertEquals(10, model.getChildCount(model.getRoot()));

          model.setFonts(new Font[] { new Font("Times New Roman", Font.BOLD, 14) });
          assertEquals(10, model.getChildCount(model.getRoot()));
     }



}
