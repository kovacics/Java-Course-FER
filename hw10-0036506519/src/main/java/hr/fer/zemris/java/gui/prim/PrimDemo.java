package hr.fer.zemris.java.gui.prim;

import javax.swing.*;
import java.awt.*;

/**
 * @author Stjepan Kovačić
 */
public class PrimDemo extends JFrame {

    /**
     * Constructs JFrame.
     */
    public PrimDemo() {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("PrimDemo");
        setLocation(20, 20);
        setSize(500, 200);
        initGUI();
    }

    /**
     * Private method that initializes gui.
     */
    private void initGUI() {
        var cp = getContentPane();
        cp.setLayout(new BorderLayout());

        var model = new PrimListModel();

        JList<Integer> list1 = new JList<>(model);
        JList<Integer> list2 = new JList<>(model);

        JPanel panel = new JPanel(new GridLayout(1, 2));

        panel.add(new JScrollPane(list1));
        panel.add(new JScrollPane(list2));

        cp.add(panel, BorderLayout.CENTER);

        JButton sljedeci = new JButton("sljedeći");
        sljedeci.addActionListener(e -> model.next());
        cp.add(sljedeci, BorderLayout.SOUTH);
    }

    /**
     * Main method of the program.
     *
     * @param args command-line args
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PrimDemo().setVisible(true));
    }
}
