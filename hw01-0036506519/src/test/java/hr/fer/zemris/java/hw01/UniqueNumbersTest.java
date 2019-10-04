package hr.fer.zemris.java.hw01;

import hr.fer.zemris.java.hw01.UniqueNumbers.TreeNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UniqueNumbersTest {

    @Test
    public void addNodeTest() {

        TreeNode root = null;
        root = UniqueNumbers.addNode(root, 5);
        assertNotNull(root);

        TreeNode root2 = UniqueNumbers.addNode(root, -10);
        assertEquals(root, root2);
    }

    @Test
    public void containsValueTest() {

        TreeNode root = null;
        assertFalse(UniqueNumbers.containsValue(root, 0));

        root = UniqueNumbers.addNode(root, 5);
        assertTrue(UniqueNumbers.containsValue(root, 5));

        root = UniqueNumbers.addNode(root, 20);
        assertTrue(UniqueNumbers.containsValue(root, 20));
        assertTrue(UniqueNumbers.containsValue(root, 5));

        assertFalse(UniqueNumbers.containsValue(root, 17));
    }

    @Test
    public void velicinaTest() {

        TreeNode root = null;
        assertEquals(0, UniqueNumbers.size(root));

        root = UniqueNumbers.addNode(root, 5);
        UniqueNumbers.addNode(root, 20);
        assertEquals(2, UniqueNumbers.size(root));
    }
}
