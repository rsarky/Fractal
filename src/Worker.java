import java.awt.*;
import java.awt.image.BufferedImage;

public class Worker implements Runnable {
    BufferedImage img;
    int minWidth;
    int maxWidth;
    int minHeight;
    int maxHeight;
    Worker(BufferedImage img, int minW, int maxW, int minH, int maxH) {
        this.img  = img;
        minWidth = minW;
        maxWidth = maxW;
        minHeight = minH;
        maxHeight = maxH;
    }
    @Override
    public void run() {
        for (int x = minWidth; x < maxWidth; x++) {
            for (int y = minHeight; y < maxHeight; y++) {
                double X = map(x,0,Fractal.IMG_WIDTH,-2.0,2.0);
                double Y = map(y,0,Fractal.IMG_HEIGHT,-1.0,1.0);
                int color = getPixelColor(X,Y);

                img.setRGB(x,y,color);
            }
        }
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
