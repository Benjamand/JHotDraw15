package fontTests;


import org.jhotdraw.gui.JFontChooser;
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

public class JFontChooserTests {

     @Mock
     private ActionListener listener;
     private Font font1;
     private Font font2;

     @BeforeEach
     void setUp() {
          MockitoAnnotations.openMocks(this);
     }


     @Test
     public void addAndRemoveActionListener() {
          JFontChooser fontChooser = new JFontChooser();

          fontChooser.addActionListener(listener);
          assertEquals(1, fontChooser.getListeners(ActionListener.class).length);
          fontChooser.removeActionListener(listener);
          assertEquals(0, fontChooser.getListeners(ActionListener.class).length);


     }

     // Test that font is always chosen. One font must always be chosen, that is the invariant.
     @Test
     public void chooseFont() {
          JFontChooser fontChooser = new JFontChooser();

          fontChooser.setSelectedFont(font1);
          assertEquals(font1, fontChooser.getSelectedFont());

          fontChooser.setSelectedFont(font2);
          assertEquals(font2, fontChooser.getSelectedFont());
     }

     @Test
     public void chooseFakeFont() {
          JFontChooser fontChooser = new JFontChooser();

          Font font3 = new Font("BOGUSA AND FAKE FONT, NOT REAL 1293129391239###!!!!", Font.ITALIC, 999999999);

          fontChooser.setSelectedFont(font1);
          assertEquals(font1, fontChooser.getSelectedFont());

          fontChooser.setSelectedFont(font3);
          assertEquals(font1, fontChooser.getSelectedFont());

     }


}
