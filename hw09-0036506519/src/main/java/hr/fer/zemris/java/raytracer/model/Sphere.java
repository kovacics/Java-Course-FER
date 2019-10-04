package hr.fer.zemris.java.raytracer.model;

/**
 * Represents sphere graphical object.
 *
 * @author Stjepan Kovačić
 */
public class Sphere extends GraphicalObject {

    /**
     * Sphere center.
     */
    private Point3D center;

    /**
     * Radius of the sphere.
     */
    private double radius;

    /**
     * Red diffuse component.
     */
    private double kdr;

    /**
     * Green diffuse component.
     */
    private double kdg;

    /**
     * Blue diffuse component.
     */
    private double kdb;

    /**
     * Red reflective component.
     */
    private double krr;

    /**
     * Green reflective component.
     */
    private double krg;

    /**
     * Blue reflective component.
     */
    private double krb;

    /**
     * Shininess factor.
     */
    private double krn;


    /**
     * Constructor specifying all fields.
     *
     * @param center sphere center
     * @param radius sphere radius
     * @param kdr    red diffuse component
     * @param kdg    green diffuse component
     * @param kdb    blue diffuse component
     * @param krr    red reflective component
     * @param krg    green reflective component
     * @param krb    blue reflective component
     * @param krn    shininess factor
     */
    public Sphere(Point3D center, double radius, double kdr, double kdg, double kdb, double krr, double krg, double krb, double krn) {
        this.center = center;
        this.radius = radius;
        this.kdr = kdr;
        this.kdg = kdg;
        this.kdb = kdb;
        this.krr = krr;
        this.krg = krg;
        this.krb = krb;
        this.krn = krn;
    }

    @Override
    public RayIntersection findClosestRayIntersection(Ray ray) {

        //a=dot(B,B)
        //b=2⋅dot(B,A−C)
        //c=dot(A−C,A−C)−r2

        Point3D A = ray.start;
        Point3D B = ray.direction;
        Point3D C = center;

        double a = B.scalarProduct(B);
        double b = 2 * B.scalarProduct(A.sub(C));
        double c = A.sub(C).scalarProduct(A.sub(C)) - radius * radius;

        double discriminant = b * b - 4 * a * c;

        if (discriminant < 0) {
            return null;
        }
        double p = (-b - Math.sqrt(discriminant)) / (2 * a);
        if (p < 0) return null;

        return new SphereRayIntersection(A.add(B.scalarMultiply(p)), p, true, this);
    }

    // GETTERS

    public Point3D getCenter() {
        return center;
    }

    public double getRadius() {
        return radius;
    }

    public double getKdr() {
        return kdr;
    }

    public double getKdg() {
        return kdg;
    }

    public double getKdb() {
        return kdb;
    }

    public double getKrr() {
        return krr;
    }

    public double getKrg() {
        return krg;
    }

    public double getKrb() {
        return krb;
    }

    public double getKrn() {
        return krn;
    }

    /**
     * Represents ray-sphere intersection.
     *
     * @author Stjepan Kovačić
     */
    public static class SphereRayIntersection extends RayIntersection {

        /**
         * Sphere.
         */
        private Sphere sphere;

        /**
         * Constructs intersection with specified attributes.
         *
         * @param point    point in the space
         * @param distance distance from the ray source
         * @param outer    if intersection is outer (ray comes from outside into sphere)
         * @param sphere   sphere
         */
        public SphereRayIntersection(Point3D point, double distance, boolean outer, Sphere sphere) {
            super(point, distance, outer);
            this.sphere = sphere;
        }

        @Override
        public Point3D getNormal() {
            return getPoint().sub(sphere.getCenter()).normalize();
        }

        @Override
        public double getKdr() {
            return sphere.getKdr();
        }

        @Override
        public double getKdg() {
            return sphere.getKdg();
        }

        @Override
        public double getKdb() {
            return sphere.getKdb();
        }

        @Override
        public double getKrr() {
            return sphere.getKrr();
        }

        @Override
        public double getKrg() {
            return sphere.getKrg();
        }

        @Override
        public double getKrb() {
            return sphere.getKrb();
        }

        @Override
        public double getKrn() {
            return sphere.getKrn();
        }
    }
}
