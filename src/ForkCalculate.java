import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.RecursiveAction;

public class ForkCalculate extends RecursiveAction {
    BufferedImage img;
    int minWidth;
    int maxWidth;
    int height;
    int threshold;
    int numPixels;

    ForkCalculate(BufferedImage b, int minW, int maxW, int h) {
        img = b;
        minWidth = minW;
        maxWidth = maxW;
        height = h;
        threshold = 100000; //TODO : Experiment with this value.
        numPixels = (maxWidth - minWidth) * height;
    }

    void computeDirectly() {
        for (int x = minWidth; x < maxWidth; x++) {
            for (int y = 0; y < height; y++) {
                double X = map(x,0,Fractal.WIDTH,-2.0,2.0);
                double Y = map(y,0,Fractal.HEIGHT,-1.0,1.0);
                int color = getPixelColor(X,Y);

                img.setRGB(x,y,color);
            }
        }
    }

    @Override
    protected void compute() {
        if(numPixels < threshold) {
            computeDirectly();
            return;
        }

        int split = (minWidth + maxWidth)/2;

        invokeAll(new ForkCalculate(img, minWidth, split, height), new ForkCalculate(img, split, maxWidth, height));
    }

    private int getPixelColor(double x, double y) {
        float hue;
        float saturation = 1f;
        float brightness;

        ComplexNumber z = new ComplexNumber(x, y);
        int i;
        for (i = 0; i < Fractal.maxiter; i++) {
            z.square();
            z.add(Fractal.c);
            if (z.mod() > Fractal.blowup) {
                break;
            }
        }

        brightness = (i < Fractal.maxiter) ? 1f : 0;
        hue = (i%Fractal.maxiter)/(float)Fractal.maxiter;
        int rgb = Color.getHSBColor(hue*5,saturation,brightness).getRGB();
        return rgb;

    }

    private double map(double x, double in_min, double in_max, double out_min, double out_max) {
        return (x-in_min)*(out_max-out_min)/(in_max-in_min) + out_min;

    }
}
