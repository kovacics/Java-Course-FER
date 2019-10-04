package hr.fer.zemris.java.raytracer;

import hr.fer.zemris.java.raytracer.model.*;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Program for rendering pictures of 3d space.
 *
 * @author Stjepan Kovačić
 */
public class RayCaster {

    /**
     * Numerical error.
     */
    private static final double DELTA = 1E-5;

    /**
     * Main method of the program.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        RayTracerViewer.show(getIRayTracerProducer(),
                new Point3D(10, 0, 0),
                new Point3D(0, 0, 0),
                new Point3D(0, 0, 10),
                20, 20);
    }

    /**
     * Returns new ray tracer producer.
     *
     * @return iray tracer producer
     */
    private static IRayTracerProducer getIRayTracerProducer() {
        return new IRayTracerProducer() {

            @Override
            public void produce(Point3D eye, Point3D view, Point3D viewUp,
                                double horizontal, double vertical, int width, int height,
                                long requestNo, IRayTracerResultObserver observer, AtomicBoolean cancel) {
                if (cancel.get()) return;
                System.out.println("Započinjem izračune...");

                short[] red = new short[width * height];
                short[] green = new short[width * height];
                short[] blue = new short[width * height];

                Point3D zAxis = view.sub(eye).normalize();
                Point3D yAxis = viewUp.normalize();
                Point3D xAxis = zAxis.vectorProduct(yAxis).normalize();
                Point3D screenCorner = view.sub(xAxis.scalarMultiply(horizontal / 2)).add(yAxis.scalarMultiply(vertical / 2));

                Scene scene = RayTracerViewer.createPredefinedScene();
                short[] rgb = new short[3];
                int offset = 0;
                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        Point3D screenPoint = screenCorner.add(xAxis.scalarMultiply(x * horizontal / (width - 1)))
                                .sub(yAxis.scalarMultiply(y * vertical / (height - 1)));

                        Ray ray = Ray.fromPoints(eye, screenPoint);
                        tracer(scene, ray, rgb);

                        red[offset] = rgb[0] > 255 ? 255 : rgb[0];
                        green[offset] = rgb[1] > 255 ? 255 : rgb[1];
                        blue[offset] = rgb[2] > 255 ? 255 : rgb[2];

                        offset++;
                    }
                }
                System.out.println("Izračuni gotovi...");
                observer.acceptResult(red, green, blue, requestNo);
                System.out.println("Dojava gotova...");
            }
        };
    }


    /**
     * Trace the ray and determines color of the closest intersection of the
     * ray with the objects in the scene.
     *
     * @param scene scene that contains object(s)
     * @param ray   ray to trace
     * @param rgb   array of colors
     */
    private static void tracer(Scene scene, Ray ray, short[] rgb) {
        rgb[0] = 0;
        rgb[1] = 0;
        rgb[2] = 0;

        RayIntersection closest = findClosestIntersection(scene, ray);

        if (closest == null) {
            return;
        }

        determineColorFor(scene, closest, rgb, ray);
    }

    /**
     * Helping method used for determination of the color for the intersection.
     *
     * @param scene  scene that contains object(s)
     * @param rayInt ray and object intersection
     * @param rgb    array of colors
     * @param ray    ray
     */
    private static void determineColorFor(Scene scene, RayIntersection rayInt, short[] rgb, Ray ray) {

        addAmbient(rgb);

        for (LightSource light : scene.getLights()) {

            Ray r = Ray.fromPoints(light.getPoint(), rayInt.getPoint());
            RayIntersection rayInt2 = findClosestIntersection(scene, r);

            if (inTheShadow(rayInt, rayInt2, light)) continue;

            addDiffuse(rgb, rayInt, light);
            addReflective(rgb, rayInt, light, ray);
        }
    }


    /**
     * Helping method that checks if intersection is in the shadow behind some other object.
     *
     * @param rayInt  intersection
     * @param rayInt2 intersection with other object
     * @param light   light source
     * @return true if in the shadow, false otherwise
     */
    private static boolean inTheShadow(RayIntersection rayInt, RayIntersection rayInt2, LightSource light) {
        if (rayInt2 != null) {
            double distanceToOtherObject = rayInt2.getPoint().sub(light.getPoint()).norm();
            double distanceToIntersection = rayInt.getPoint().sub(light.getPoint()).norm();

            return distanceToOtherObject - distanceToIntersection < -DELTA;
        }
        return false;
    }


    /**
     * Helping method that adds ambient component.
     *
     * @param rgb color array
     */
    private static void addAmbient(short[] rgb) {
        for (int i = 0; i < 3; i++) {
            rgb[i] = 15;
        }
    }

    /**
     * Helping method that adds diffuse component.
     *
     * @param rgb    array of colors
     * @param rayInt ray and object intersection
     * @param light  light source
     */
    private static void addDiffuse(short[] rgb, RayIntersection rayInt, LightSource light) {
        //Id = Ii · kd · cos(θ)
        Point3D rayVector = light.getPoint().sub(rayInt.getPoint()).normalize();
        double cosAngle = rayVector.scalarProduct(rayInt.getNormal());

        if (cosAngle < 0) return;

        rgb[0] += rayInt.getKdr() * light.getR() * cosAngle;
        rgb[1] += rayInt.getKdg() * light.getG() * cosAngle;
        rgb[2] += rayInt.getKdb() * light.getB() * cosAngle;
    }

    /**
     * Helping method that adds reflective component.
     *
     * @param rgb    array of colors
     * @param rayInt ray and object intersection
     * @param light  light source
     * @param ray    ray
     */
    private static void addReflective(short[] rgb, RayIntersection rayInt, LightSource light, Ray ray) {

        //Is = Ii · ks · cosα^n
        //r=d−2(d⋅n^)n^

        Point3D rayVector = rayInt.getPoint().sub(light.getPoint()).normalize();
        Point3D rayReflected = rayVector.sub(rayInt.getNormal().scalarMultiply(2).
                scalarMultiply(rayVector.scalarProduct(rayInt.getNormal()))).normalize();

        double cosAngle = ray.start.sub(rayInt.getPoint()).normalize().scalarProduct(rayReflected);
        double cosPowered = Math.pow(cosAngle, rayInt.getKrn());

        if (cosAngle < 0) return;

        rgb[0] += rayInt.getKrr() * light.getR() * cosPowered;
        rgb[1] += rayInt.getKrg() * light.getG() * cosPowered;
        rgb[2] += rayInt.getKrb() * light.getB() * cosPowered;
    }

    /**
     * Method finds closest intersection of the ray and object(s) in the scene.
     *
     * @param scene scene that contains various object(s)
     * @param ray   ray for which to find intersection
     * @return closest intersection of the ray and some object
     */
    private static RayIntersection findClosestIntersection(Scene scene, Ray ray) {
        var objects = scene.getObjects();
        RayIntersection intersection = null;

        for (GraphicalObject object : objects) {
            RayIntersection currentInt = object.findClosestRayIntersection(ray);
            if (currentInt != null) {
                if (intersection == null ||
                        currentInt.getDistance() < intersection.getDistance()) {
                    intersection = currentInt;
                }
            }
        }

        return intersection;
    }
}
