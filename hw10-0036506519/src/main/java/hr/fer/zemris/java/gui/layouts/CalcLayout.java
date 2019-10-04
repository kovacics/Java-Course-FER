package hr.fer.zemris.java.gui.layouts;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Class represents layout manager specific for the calculator.
 * Layout manager accepts maximum 31 components placed over
 * 5 x 7 grid.
 *
 * @author Stjepan Kovačić
 */
public class CalcLayout implements LayoutManager2 {

    /**
     * Minimum row.
     */
    private static final int ROW_MIN = 1;

    /**
     * Maximum row.
     */
    private static final int ROW_MAX = 5;

    /**
     * Minimum column.
     */
    private static final int COLUMN_MIN = 1;

    /**
     * Maximum column.
     */
    private static final int COLUMN_MAX = 7;

    /**
     * Width of the 'special' cell on row = 1 and column = 1.
     */
    private static final int SPECIAL_CELL_WIDTH = 5;

    /**
     * List of all components in the layout.
     */
    private Map<RCPosition, Component> components = new HashMap<>();

    /**
     * Pixel gap between components.
     */
    private int gap;
    /**
     * Map used for fixing specific pixel problems, for smoother
     * and more precise layout.
     * Maps width difference to the all cells that need to be
     * adjusted.
     */
    private static final Map<Integer, List<Integer>> columnsFixingMap;

    /**
     * Map used for fixing specific pixel problems, for smoother
     * and more precise layout.
     * Maps height difference to the all cells that need to be
     * adjusted.
     */
    private static final Map<Integer, List<Integer>> rowFixingMap;

    static {
        columnsFixingMap = new HashMap<>();
        for (int i = 1; i < COLUMN_MAX; i++) {
            columnsFixingMap.put(i, getListToFix(i, COLUMN_MAX));
        }

        rowFixingMap = new HashMap<>();
        for (int i = 1; i < ROW_MAX; i++) {
            rowFixingMap.put(i, getListToFix(i, ROW_MAX));
        }
    }

    /**
     * Constructs the layout with gap set to zero.
     */
    public CalcLayout() {
    }

    /**
     * Constructs the layout with given gap.
     *
     * @param gap pixel gap between components
     */
    public CalcLayout(int gap) {
        this.gap = gap;
    }

    @Override
    public void addLayoutComponent(Component comp, Object constraints) {

        RCPosition position;

        if (constraints instanceof RCPosition) {
            position = (RCPosition) constraints;
        } else if (constraints instanceof String) {

            String input = (String) constraints;
            input = input.replaceAll(" ", "");
            String[] parts = input.split(",");

            if (parts.length != 2) {
                throw new CalcLayoutException("Unable to parse position.");
            }

            try {
                position = new RCPosition(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
            } catch (NumberFormatException e) {
                throw new CalcLayoutException("Unable to parse position.");
            }
        } else {
            throw new CalcLayoutException("Constraints are not compatible with this layout manager");
        }

        checkIfValid(position.getRow(), position.getColumn());

        if (components.containsKey(position)) {
            throw new CalcLayoutException("Cannot add, there is component on that position already.");
        }

        components.put(position, comp);
    }


    @Override
    public void removeLayoutComponent(Component comp) {
        components.values().remove(comp);
    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        return someLayoutSize(Component::getPreferredSize);
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        return someLayoutSize(Component::getMinimumSize);
    }

    @Override
    public Dimension maximumLayoutSize(Container target) {
        return someLayoutSize(Component::getMaximumSize);
    }

    /**
     * Helping method for getting some layout size for the container.
     * Can be used to get preferred/maximum/minimum/... layout size.
     *
     * @param someSizeGetter function that for component returns some size of the component
     * @return layout size for the container
     */
    private Dimension someLayoutSize(Function<Component, Dimension> someSizeGetter) {
        Dimension resultSize = new Dimension();

        components.forEach((pos, comp) -> {
            Dimension currentCompSize = someSizeGetter.apply(comp);

            if (currentCompSize != null) {

                if (currentCompSize.height > resultSize.height) {
                    resultSize.height = currentCompSize.height;
                }
                if (currentCompSize.width > resultSize.width) {
                    if (pos.getRow() == 1 && pos.getColumn() == 1) {
                        int onePart = (currentCompSize.width - (SPECIAL_CELL_WIDTH - 1) * gap) / SPECIAL_CELL_WIDTH;
                        if (onePart > resultSize.width)
                            resultSize.width = onePart;
                    } else {
                        resultSize.width = currentCompSize.width;
                    }
                }
            }
        });

        resultSize.width *= COLUMN_MAX;
        resultSize.height *= ROW_MAX;

        resultSize.width += gap * (COLUMN_MAX - 1);
        resultSize.height += gap * (ROW_MAX - 1);

        return resultSize;
    }


    @Override
    public void layoutContainer(Container parent) {
        Dimension containerSize = parent.getSize();
        Insets insets = parent.getInsets();

        int maxWidth = containerSize.width - insets.left - insets.right - (gap * (COLUMN_MAX - 1));
        int maxHeight = containerSize.height - insets.bottom - insets.top - (gap * (ROW_MAX - 1));

        int eachCompWidth = maxWidth / COLUMN_MAX;
        int eachCompHeight = maxHeight / ROW_MAX;

        setBounds(eachCompHeight, eachCompWidth, insets);

        int widthDifference = (maxWidth - (eachCompWidth * COLUMN_MAX));
        int heightDifference = (maxHeight - (eachCompHeight * ROW_MAX));

        if (widthDifference != 0) {
            fixColumns(widthDifference);
        }
        if (heightDifference != 0) {
            fixRows(heightDifference);
        }
    }

    /**
     * Private method for 'fixing' rows.
     * Adjusts components so everything fits perfectly in the container.
     *
     * @param heightDifference height difference which needs to be removed
     */
    private void fixRows(int heightDifference) {
        List<Integer> rowsToFix = rowFixingMap.get(Math.abs(heightDifference));

        for (int row : rowsToFix) {
            components.forEach((pos, comp) -> {
                if (pos.getRow() == row) {
                    comp.setSize(comp.getWidth(), comp.getHeight() + 1); //make bigger
                } else if (pos.getRow() > row) {
                    comp.setLocation(comp.getX(), comp.getY() + 1); //move component
                }
            });
        }
    }

    /**
     * Private method for 'fixing' columns.
     * Adjusts components so everything fits perfectly in the container.
     *
     * @param widthDifference width difference which needs to be removed
     */
    private void fixColumns(int widthDifference) {
        List<Integer> columnsToFix = columnsFixingMap.get(Math.abs(widthDifference));

        for (int column : columnsToFix) {
            components.forEach((pos, comp) -> {

                //special comp at (1,1)
                if (pos.getRow() == 1 && pos.getColumn() == 1 && column < pos.getColumn() + SPECIAL_CELL_WIDTH) {
                    comp.setSize(comp.getWidth() + 1, comp.getHeight());
                } else if (pos.getColumn() == column) {
                    comp.setSize(comp.getWidth() + 1, comp.getHeight());
                } else if (pos.getColumn() > column) {
                    comp.setLocation(comp.getX() + 1, comp.getY());
                }
            });
        }
    }

    /**
     * Method sets bounds of the all components from the given map
     * by the general each component height and width.
     *
     * @param eachCompHeight height of all components
     * @param eachCompWidth  width of all components
     * @param insets         insets of the parent
     */
    private void setBounds(int eachCompHeight, int eachCompWidth, Insets insets) {
        components.forEach((pos, comp) -> {

            int gapX = gap * (pos.getColumn() - 1);
            int compBeforeX = eachCompWidth * (pos.getColumn() - 1);

            int gapY = gap * (pos.getRow() - 1);
            int compBeforeY = eachCompHeight * (pos.getRow() - 1);

            int startX = compBeforeX + gapX + insets.left;
            int startY = compBeforeY + gapY + insets.top;

            comp.setBounds(startX, startY,
                    (pos.getRow() == 1 && pos.getColumn() == 1) ?
                            SPECIAL_CELL_WIDTH * (eachCompWidth + gap) - gap : eachCompWidth, eachCompHeight);
        });
    }

    @Override
    public void invalidateLayout(Container target) {

    }

    @Override
    public void addLayoutComponent(String name, Component comp) {
        throw new UnsupportedOperationException("Unsupported operation.");
    }

    @Override
    public float getLayoutAlignmentX(Container target) {
        return 0;
    }

    @Override
    public float getLayoutAlignmentY(Container target) {
        return 0;
    }

    /**
     * Checks if arguments are in the range.
     *
     * @param row    row
     * @param column column
     */
    private void checkIfValid(int row, int column) {
        if (row < ROW_MIN || row > ROW_MAX) {
            throw new CalcLayoutException("Row must be in [" + ROW_MIN + "," + ROW_MAX + "] range");
        }

        if (column < COLUMN_MIN || column > COLUMN_MAX) {
            throw new CalcLayoutException("Column must be in [" + COLUMN_MIN + "," + COLUMN_MAX + "] range");
        }

        if (row == 1 && column > 1 && column < 6) {
            throw new CalcLayoutException("Specified position is forbidden.");
        }
    }

    /**
     * Helping static method for initializing all indexes(column or row)
     * that need to be adjusted based on height/width difference.
     *
     * @param toChoose how many indexes to choose
     * @param size     all indexes
     * @return list of chosen indexes
     */
    private static List<Integer> getListToFix(int toChoose, int size) {
        List<Integer> toFix = new ArrayList<>(toChoose);

        if (toChoose == 1) {
            toFix.add((size - 1) / 2);
        } else {
            int step = (size - 1) / (toChoose - 1);
            for (int i = 0; i < toChoose; i++) {
                toFix.add(i, Math.round(i * step) + 1);
            }
        }

        return toFix;
    }
}
