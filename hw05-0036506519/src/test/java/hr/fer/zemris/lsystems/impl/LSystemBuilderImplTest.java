package hr.fer.zemris.lsystems.impl;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class LSystemBuilderImplTest {


    @Test
    void generateTest() {
        LSystemBuilderImpl builder = new LSystemBuilderImpl();
        LSystemBuilderImpl.LSystemImpl lSystem = builder.new LSystemImpl();

        builder.setAxiom("F");
        builder.registerProduction('F', "F+F--F+F");

        assertEquals("F", lSystem.generate(0));
        assertEquals("F+F--F+F", lSystem.generate(1));
        assertEquals("F+F--F+F+F+F--F+F--F+F--F+F+F+F--F+F", lSystem.generate(2));
    }
}