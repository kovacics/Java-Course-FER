package hr.fer.zemris.java.fractals;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Program for displaying Newton-Raphson fractal.
 *
 * @author Stjepan Kovačić
 */
public class Newton {

    /**
     * Main method of the program.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {

        try (Scanner sc = new Scanner(System.in)) {
            System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
            System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done.");

            int i = 1;
            String input;
            List<Complex> roots = new ArrayList<>();

            while (true) {
                System.out.print("Root " + i + "> ");
                input = sc.nextLine();
                if (input.equals("done")) {
                    if (i >= 3) break;
                    else {
                        System.out.println("I need at least 2 roots.");
                        continue;
                    }
                }
                try {
                    roots.add(Complex.parse(input));
                } catch (RuntimeException e) {
                    System.out.println(e.getMessage());
                    continue;
                }
                i++;
            }

            System.out.println("Image of fractal will appear shortly. Thank you.");
            FractalViewer.show(new MyProducer(new ComplexRootedPolynomial(Complex.ONE, roots.toArray(new Complex[0]))));
        }
    }

    /**
     * Helping method for data calculation.
     */
    private static void calculate(double reMin, double reMax, double imMin, double imMax,
                                  int width, int height, int yMin, int yMax, short[] data, AtomicBoolean cancel,
                                  ComplexPolynomial polynomial, ComplexRootedPolynomial rootedPolynomial) {

        double convergenceTreshold = 1E-3;
        double rootTreshold = 2E-3;
        int maxIter = 64;
        int offset = yMin * width;

        for (int y = yMin; y <= yMax; y++) {
            for (int x = 0; x < width; x++) {

                double cre = x / (width - 1.0) * (reMax - reMin) + reMin;
                double cim = (height - 1.0 - y) / (height - 1) * (imMax - imMin) + imMin;

                Complex zn = new Complex(cre, cim);
                var derived = polynomial.derive();
                double module;

                int iter = 0;
                do {
                    Complex numerator = polynomial.apply(zn);
                    Complex denominator = derived.apply(zn);
                    Complex fraction = numerator.divide(denominator);
                    Complex znold = zn;

                    zn = zn.sub(fraction);
                    module = zn.sub(znold).module();

                    iter++;
                } while (module > convergenceTreshold && iter < maxIter);

                short index = (short) rootedPolynomial.indexOfClosestRootFor(zn, rootTreshold);
                data[offset++] = (short) (index + 1);
            }
        }
    }

    /**
     * Helping static class that represents calculation for the Newton-Raphson fractal.
     */
    public static class NewtonCalculation implements Callable<Void> {

        /**
         * Real part min.
         */
        double reMin;

        /**
         * Real part max.
         */
        double reMax;

        /**
         * Imaginary part min.
         */
        double imMin;

        /**
         * Imaginary part may.
         */
        double imMax;

        /**
         * Width of the window.
         */
        int width;

        /**
         * Height of the window.
         */
        int height;

        /**
         * Minimum y.
         */
        int yMin;

        /**
         * Maximum y.
         */
        int yMax;

        /**
         * Data of the pixels for the rendering.
         */
        short[] data;
        AtomicBoolean cancel;

        /**
         * Polynomial in {@code ComplexPolynomial} form
         */
        ComplexPolynomial polynomial;

        /**
         * Polynomial in {@code ComplexRootedPolynomial} form
         */
        ComplexRootedPolynomial rootedPolynomial;


        /**
         * Public constructor specifying all fields.
         */
        public NewtonCalculation(double reMin, double reMax, double imMin, double imMax,
                                 int width, int height, int yMin, int yMax, short[] data, AtomicBoolean cancel,
                                 ComplexPolynomial polynomial, ComplexRootedPolynomial rootedPolynomial) {
            this.reMin = reMin;
            this.reMax = reMax;
            this.imMin = imMin;
            this.imMax = imMax;
            this.width = width;
            this.height = height;
            this.yMin = yMin;
            this.yMax = yMax;
            this.data = data;
            this.cancel = cancel;
            this.polynomial = polynomial;
            this.rootedPolynomial = rootedPolynomial;
        }

        @Override
        public Void call() {
            Newton.calculate(reMin, reMax, imMin, imMax, width, height, yMin, yMax, data, cancel, polynomial, rootedPolynomial);
            return null;
        }
    }

    /**
     * Private static implementation of the {@code IFractalProducer} class.
     */
    public static class MyProducer implements IFractalProducer {

        /**
         * Pool of threads.
         */
        private ExecutorService pool;

        /**
         * Number of jobs.
         */
        private int jobs;

        /**
         * Polynomial in the rooted form.
         */
        private ComplexRootedPolynomial rootedPolynomial;

        /**
         * Polynomial for which Newton-Raphson iteration is done.
         */
        private ComplexPolynomial polynomial;

        /**
         * Constructs produces with specified polynomial.
         *
         * @param polynomial complex rooted polynomial
         */
        public MyProducer(ComplexRootedPolynomial polynomial) {
            int processors = Runtime.getRuntime().availableProcessors();
            jobs = 8 * processors;

            pool = Executors.newFixedThreadPool(processors, new DaemonicThreadFactory());

            this.rootedPolynomial = polynomial;
            this.polynomial = rootedPolynomial.toComplexPolynom();
        }

        @Override
        public void produce(double reMin, double reMax, double imMin, double imMax,
                            int width, int height, long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {

            short[] data = new short[width * height];
            int linesPerJob = height / jobs;

            List<Future<Void>> results = new ArrayList<>();

            for (int i = 0; i < jobs; i++) {
                int yMin = i * linesPerJob;
                int yMax = (i + 1) * linesPerJob - 1;
                if (i == jobs - 1) {
                    yMax = height - 1;
                }
                var job = new NewtonCalculation(reMin, reMax, imMin, imMax, width, height, yMin, yMax, data, cancel, polynomial, rootedPolynomial);
                results.add(pool.submit(job));
            }

            for (var result : results) {
                try {
                    result.get();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }

            observer.acceptResult(data, (short) (polynomial.order() + 1), requestNo);
        }


        /**
         * Thread factory that produces daemon threads.
         */
        private static class DaemonicThreadFactory implements ThreadFactory {

            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setDaemon(true);
                return t;
            }
        }
    }
}
