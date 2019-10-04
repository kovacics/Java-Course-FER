package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.java.custom.collections.SimpleHashtable;
import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.commands.*;
import hr.fer.zemris.math.Vector2D;

import java.awt.*;

/**
 * Implementation of the {@code LSystemBuilder} interface.
 */
public class LSystemBuilderImpl implements LSystemBuilder {

    /**
     * Map that maps characters to String productions.
     */
    private SimpleHashtable<Character, String> productions;

    /**
     * Map that maps characters to {@code Command}.
     */
    private SimpleHashtable<Character, Command> commands;

    /**
     * Current unit length.
     */
    private double unitLength = 0.1;

    /**
     * Scaler for {@code unitLength} applied on every new generation.
     */
    private double unitLengthDegreeScaler = 1;

    /**
     * Origin of the {@code LSystem}.
     */
    private Vector2D origin = new Vector2D(0, 0);

    /**
     * Angle of the {@code LSystem}.
     */
    private Vector2D angle = new Vector2D(0, 0);

    /**
     * Axiom of the {@code LSystem}.
     */
    private String axiom = "";

    /**
     * Default constructor. Constructs {@code Dictionary} for productions,
     * and {@code Dictionary} for commands.
     */
    public LSystemBuilderImpl() {
        productions = new SimpleHashtable<>();
        commands = new SimpleHashtable<>();
    }

    /**
     * Sets {@code unitLength} of the {@code LSystem}.
     *
     * @param v unitLength to be set
     * @return this {@code LSystem} with changed {@code unitLength}
     */
    @Override
    public LSystemBuilder setUnitLength(double v) {
        unitLength = v;
        return this;
    }

    /**
     * Sets {@code origin} of the {@code LSystem}.
     *
     * @param v  x coordinate of the {@code origin}
     * @param v1 y coordinate of the {@code origin}
     * @return this {@code LSystem} with changed {@code origin}
     */
    @Override
    public LSystemBuilder setOrigin(double v, double v1) {
        origin = new Vector2D(v, v1);
        return this;
    }

    /**
     * Sets {@code angle} of the {@code LSystem}.
     *
     * @param v angle(in degrees) to be set
     * @return this {@code LSystem} with changed {@code angle}
     */
    @Override
    public LSystemBuilder setAngle(double v) {
        angle = new Vector2D(Math.cos(Math.toRadians(v)), Math.sin(Math.toRadians(v)));
        return this;
    }

    /**
     * Sets {@code axiom} of the {@code LSystem}.
     *
     * @param s axiom of the {@code LSystem} to be set
     * @return this {@code LSystem} with changed {@code axiom}
     */
    @Override
    public LSystemBuilder setAxiom(String s) {
        axiom = s;
        return this;
    }

    /**
     * Sets {@code unitLengthDegreeScaler} of the {@code LSystem}.
     *
     * @param v value to be set as {@code unitLengthDegreeScaler}
     * @return this {@code LSystem} with changed {@code unitLengthDegreeScaler}
     */
    @Override
    public LSystemBuilder setUnitLengthDegreeScaler(double v) {
        unitLengthDegreeScaler = v;
        return this;
    }

    /**
     * Saves and maps given character to String which represents
     * (symbol, production) pair.
     *
     * @param c symbol which should be replaced by some character sequence {@code s} in the next generation
     * @param s character sequence which should replace given symbol {@code c}
     * @return this {@code LSystem} with registered given production
     */
    @Override
    public LSystemBuilder registerProduction(char c, String s) {
        productions.put(c, s);
        return this;
    }

    /**
     * Saves and maps given character to String which represents
     * (symbol, command) pair.
     *
     * @param c symbol which represent some command that should be executed when 'parsing' that symbol
     * @param s String representation of the command which should be mapped to the given symbol {@code c},
     *          and then executed when 'parsing' that symbol
     * @return this {@code LSystem} with registered given production
     */
    @Override
    public LSystemBuilder registerCommand(char c, String s) {
        commands.put(c, parseCommand(s));
        return this;
    }

    /**
     * Parses given {@code String} to {@code Command}.
     *
     * @param s String which should be parsed into some command
     * @return parsed {@code Command}
     */
    private Command parseCommand(String s) {
        String[] parts = s.split("\\s+");
        switch (parts.length) {
            case 1:
                return parseStackOp(parts[0]);

            case 2:
                return parseNonStackOp(parts);

            default:
                throw new IllegalArgumentException("Illegal command.");
        }
    }

    /**
     * Helping method that parses commands which are not stack oriented.
     *
     * @param parts parameters of the command
     * @return parsed {@code Command}
     */
    private Command parseNonStackOp(String[] parts) {
        try {
            double number = Double.parseDouble(parts[1]);
            switch (parts[0]) {
                case "draw":
                    return new DrawCommand(number);
                case "skip":
                    return new SkipCommand(number);
                case "scale":
                    return new ScaleCommand(number);
                case "rotate":
                    return new RotateCommand(number);
            }
        } catch (NumberFormatException ex) {
            try {
                return new ColorCommand(Color.decode("#" + parts[1]));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Illegal command, cannot interpreter " + parts[1] +
                        " as color");
            }
        }
        throw new IllegalArgumentException("Illegal command.");
    }

    /**
     * Helping method that parses commands which are not stack oriented.
     *
     * @param part parameter of the command
     * @return parsed {@code Command}
     */
    private Command parseStackOp(String part) {
        switch (part) {
            case "push":
                return new PushCommand();

            case "pop":
                return new PopCommand();

            default:
                throw new IllegalArgumentException("Illegal command.");
        }
    }

    /**
     * Method configures whole {@code LSystemBuilder} from input text.
     *
     * @param strings input text that should be parsed into {@code LSystemBuilder} variables.
     * @return this configured {@code LSystemBuilder}
     */
    @Override
    public LSystemBuilder configureFromText(String[] strings) {
        for (String s : strings) {
            if (s.equals("") || s.equals("\\n") || s.equals("\\t")) continue;

            String[] parts;
            s = s.replace("  ", " ");

            if (s.startsWith("unitLengthDegreeScaler")) {
                parts = s.split("( / |/ | /|/|\\s+)");
            } else {
                parts = s.split("\\s+");
            }

            switch (parts.length) {
                case 2:
                    parseTwoPartsDirective(parts);
                    break;
                case 3:
                    parseThreePartsDirective(parts);
                    break;
                case 4:
                    parseFourPartsDirective(parts);
                    break;

                default:
                    throw new IllegalArgumentException("Illegal command.");
            }
        }
        return this;
    }

    /**
     * Helping method for parsing directives that have 2 parameters.
     *
     * @param parts directive parts
     */
    private void parseTwoPartsDirective(String[] parts) {
        try {
            switch (parts[0]) {
                case "angle":
                    setAngle(Double.parseDouble(parts[1]));
                    break;

                case "axiom":
                    setAxiom(parts[1]);
                    break;

                case "unitLength":
                    setUnitLength(Double.parseDouble(parts[1]));
                    break;

                case "unitLengthDegreeScaler":
                    setUnitLengthDegreeScaler(Double.parseDouble(parts[1]));
                    break;

                default:
                    throw new IllegalArgumentException("Illegal command.");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Illegal command, cannot parse " + parts[1] + "to double");
        }
    }

    /**
     * Helping method for parsing directives that have 3 parameters.
     *
     * @param parts directive parts
     */
    private void parseThreePartsDirective(String[] parts) {
        try {
            switch (parts[0]) {
                case "origin":
                    setOrigin(Double.parseDouble(parts[1]), Double.parseDouble(parts[2]));
                    break;

                case "production":
                    if (parts[1].length() > 1) {
                        throw new IllegalArgumentException("Illegal command.");
                    }
                    registerProduction(parts[1].charAt(0), parts[2]);
                    break;

                case "unitLengthDegreeScaler":
                    setUnitLengthDegreeScaler(Double.parseDouble(parts[1]) / Double.parseDouble(parts[2]));
                    break;

                case "command":
                    if (parts[1].length() > 1) {
                        throw new IllegalArgumentException("Illegal command.");
                    }
                    registerCommand(parts[1].charAt(0), parts[2]);
                    break;

                default:
                    throw new IllegalArgumentException("Illegal command.");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Cannot parse given arguments : "
                    + parts[1] + "," + parts[2] + "to double values");
        }
    }

    /**
     * Helping method for parsing directives that have 4 parameters.
     *
     * @param parts directive parts
     */
    private void parseFourPartsDirective(String[] parts) {
        if (parts[0].equals("command")) {
            if (parts[1].length() > 1) {
                throw new IllegalArgumentException("Illegal command.");
            }
            registerCommand(parts[1].charAt(0), parts[2] + " " + parts[3]);
        } else {
            throw new IllegalArgumentException("Illegal command.");
        }
    }

    /**
     * Creates {@code LSystem} of type {@code LSystemBuilderImpl} which is inner class of this class.
     *
     * @return constructed {@code LSystem}
     */
    @Override
    public LSystem build() {
        return new LSystemImpl();
    }

    /**
     * Inner class that is implementation of the {@code LSystem} class.
     */
    public class LSystemImpl implements LSystem {

        /**
         * Generates i-th generation of the character sequence based on configuration from the outer class.
         *
         * @param i generation of the character sequence
         * @return generated character sequence
         */
        @Override
        public String generate(int i) {
            if (i == 0) return axiom;

            StringBuilder sb = new StringBuilder();
            String lastGen = generate(i - 1);
            for (char c : lastGen.toCharArray()) {
                String production = productions.get(c);
                if (production != null) {
                    sb.append(production);
                } else {
                    sb.append(c);
                }
            }
            return sb.toString();
        }

        /**
         * Generates i-th generation of the character sequence which gets parsed
         * into commands, which are then executed (and possibly drawn on screen),
         * so calling this method results in some drawing on the screen.
         *
         * @param i       depth(generation of the sequence)
         * @param painter instance of the class used for drawing
         */
        @Override
        public void draw(int i, Painter painter) {
            Context ctx = new Context();
            TurtleState state = new TurtleState(origin.copy(), angle.copy(), Color.BLACK,
                    unitLength * Math.pow(unitLengthDegreeScaler, i));
            ctx.pushState(state);

            String generated = generate(i);
            for (char c : generated.toCharArray()) {
                Command cmd = commands.get(c);
                if (cmd != null) cmd.execute(ctx, painter);
            }
        }
    }
}
