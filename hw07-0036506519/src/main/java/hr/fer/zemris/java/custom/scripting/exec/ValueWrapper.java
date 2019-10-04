package hr.fer.zemris.java.custom.scripting.exec;

import java.util.function.BiFunction;

/**
 * Class represents wrapper for objects of any type.
 *
 * @author Stjepan Kovačić
 */
public class ValueWrapper {

    /**
     * Value of the wrapper.
     */
    private Object value;


    /**
     * Constructs wrapper with given value.
     *
     * @param value value of the wrapper
     */
    public ValueWrapper(Object value) {
        this.value = value;
    }


    /**
     * Adds given value to this wrapper value.
     *
     * @param incValue value to add
     */
    public void add(Object incValue) {
        operation(value, incValue, Double::sum);
    }

    /**
     * Subtracts given value from this wrapper value.
     *
     * @param decValue value to subtract
     */
    public void subtract(Object decValue) {
        operation(value, decValue, (v1, v2) -> v1 - v2);
    }

    /**
     * Multiplies this wrapper value with given value.
     *
     * @param mulValue value to multiply wrapper value by
     */
    public void multiply(Object mulValue) {
        operation(value, mulValue, (v1, v2) -> v1 * v2);
    }

    /**
     * Divides wrapper value with given value.
     *
     * @param divValue value to divide wrapper value by
     */
    public void divide(Object divValue) {
        operation(value, divValue, (v1, v2) -> v1 / v2);
    }

    /**
     * Compares this wrapper value and given value.
     *
     * @param withValue other value
     * @return result of the comparing
     */
    public int numCompare(Object withValue) {
        if (value == null && withValue == null) return 0;
        return Double.compare(getPair(value).value, getPair(withValue).value);
    }

    //****************************
    //      HELPING METHODS
    //****************************


    /**
     * Helping method that represents one operation on two value.
     *
     * @param first    first operation argument
     * @param second   second operation argument
     * @param function operation
     */
    private void operation(Object first, Object second, BiFunction<Double, Double, Double> function) {

        TypeValuePair firstPair = getPair(first);
        TypeValuePair secondPair = getPair(second);

        NumberType resultType = NumberType.INTEGER;

        if (firstPair.type == NumberType.DOUBLE || secondPair.type == NumberType.DOUBLE) {
            resultType = NumberType.DOUBLE;
        }

        double result = function.apply(firstPair.value, secondPair.value);

        if (resultType == NumberType.INTEGER) {
            value = (int) result;
        } else {
            value = result;
        }
    }

    /**
     * Private method that returns pair of the object type and value.
     *
     * @param value value for which to get pair class
     * @return pair of type and value
     */
    private TypeValuePair getPair(Object value) {
        if (value == null) {
            return new TypeValuePair(NumberType.INTEGER, 0);
        }

        if (value instanceof String) {
            try {
                if (isDecimalValue((String) value)) {
                    return new TypeValuePair(NumberType.DOUBLE, Double.parseDouble((String) value));
                } else {
                    return new TypeValuePair(NumberType.INTEGER, Integer.parseInt((String) value));
                }
            } catch (NumberFormatException e) {
                throw new RuntimeException("Given argument is not compatible with arithmetic operations");
            }
        } else if (value instanceof Double) {
            return new TypeValuePair(NumberType.DOUBLE, (double) value);
        } else if (value instanceof Integer) {
            return new TypeValuePair(NumberType.INTEGER, (int) value);
        } else {
            throw new RuntimeException("Arithmetic operations demand Integer, " +
                    "String or Double arguments, your input is of type: " + value.getClass().toString());
        }
    }

    /**
     * Helping method that checks if given string is decimal value.
     *
     * @param value value to check
     * @return true if decimal value, false otherwise
     */
    private boolean isDecimalValue(String value) {
        return value.contains(".") || value.contains("E");
    }

    //****************************
    //      GETTER/SETTER
    //****************************

    /**
     * Getter of the wrapper value.
     *
     * @return wrapper value
     */
    public Object getValue() {
        return value;
    }

    /**
     * Setter of the wrapper value.
     *
     * @param value value to set
     */
    public void setValue(Object value) {
        this.value = value;
    }

    //****************************
    //      HELPING CLASS
    //****************************

    /**
     * Static helping class that represents object as type and value.
     */
    private static class TypeValuePair {


        /**
         * Value of the object.
         */
        private double value;

        /**
         * Type of the object.
         */
        private NumberType type;

        /**
         * Constructs pair with given type and value.
         *
         * @param type  object type
         * @param value object value
         */
        public TypeValuePair(NumberType type, double value) {
            this.value = value;
            this.type = type;
        }
    }
}
