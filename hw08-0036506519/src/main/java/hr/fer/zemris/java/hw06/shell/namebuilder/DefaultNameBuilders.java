package hr.fer.zemris.java.hw06.shell.namebuilder;

/**
 * Class with some factory methods for some default name builders.
 *
 * @author Stjepan Kovačić
 */
public class DefaultNameBuilders {

    /**
     * Returns name builder for adding strings.
     *
     * @param t text to add
     * @return text name builder
     */
    public static NameBuilder text(String t) {
        return (res, sb) -> sb.append(t);
    }

    /**
     * Returns name builder for adding group part.
     *
     * @param index group index
     * @return group name builder
     */
    public static NameBuilder group(int index) {
        return (res, sb) -> sb.append(res.group(index));
    }

    /**
     * Returns name builder for adding group part with padding.
     *
     * @param index    group index
     * @param padding  padding character
     * @param minWidth minimal group width
     * @return padding group name builder
     */
    public static NameBuilder group(int index, char padding, int minWidth) {
        return (res, sb) -> {
            int toPad = minWidth - res.group(index).length();
            sb.append(String.valueOf(padding).repeat(toPad));
            sb.append(res.group(index));
        };
    }
}
