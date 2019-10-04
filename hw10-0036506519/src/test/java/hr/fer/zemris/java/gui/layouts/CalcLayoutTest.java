package hr.fer.zemris.java.gui.layouts;

import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;


public class CalcLayoutTest {


    @Test
    public void throwsForOutOfRange() {
        JPanel p = new JPanel(new CalcLayout(2));
        assertThrows(CalcLayoutException.class, () -> p.add(new JLabel("a"), new RCPosition(6, 3)));
        assertThrows(CalcLayoutException.class, () -> p.add(new JLabel("a"), new RCPosition(5, 8)));
        assertThrows(CalcLayoutException.class, () -> p.add(new JLabel("a"), new RCPosition(5, 0)));
        assertThrows(CalcLayoutException.class, () -> p.add(new JLabel("a"), new RCPosition(-1, 2)));
    }

    @Test
    public void throwsForFirstCellRange() {
        JPanel p = new JPanel(new CalcLayout(2));
        assertThrows(CalcLayoutException.class, () -> p.add(new JLabel("a"), new RCPosition(1, 2)));
        assertThrows(CalcLayoutException.class, () -> p.add(new JLabel("a"), new RCPosition(1, 5)));
    }

    @Test
    public void throwsForAlreadyFull() {
        JPanel p = new JPanel(new CalcLayout(2));
        assertDoesNotThrow(() -> p.add(new JLabel("a"), new RCPosition(3, 4)));
        assertThrows(CalcLayoutException.class, () -> p.add(new JLabel("a"), new RCPosition(3, 4)));
    }

    @Test
    public void testPrefferedSize() {
        JPanel p = new JPanel(new CalcLayout(2));
        JLabel l1 = new JLabel("");
        l1.setPreferredSize(new Dimension(10, 30));
        JLabel l2 = new JLabel("");
        l2.setPreferredSize(new Dimension(20, 15));
        p.add(l1, new RCPosition(2, 2));
        p.add(l2, new RCPosition(3, 3));
        Dimension dim = p.getPreferredSize();

        assertEquals(152, dim.width);
        assertEquals(158, dim.height);
    }

    @Test
    public void testPrefferedSize2() {
        JPanel p = new JPanel(new CalcLayout(2));
        JLabel l1 = new JLabel("");
        l1.setPreferredSize(new Dimension(108, 15));
        JLabel l2 = new JLabel("");
        l2.setPreferredSize(new Dimension(16, 30));
        p.add(l1, new RCPosition(1, 1));
        p.add(l2, new RCPosition(3, 3));
        Dimension dim = p.getPreferredSize();

        assertEquals(158, dim.height);
        assertEquals(152, dim.width);
    }
}