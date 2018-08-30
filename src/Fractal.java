import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

class Fractal extends JPanel{
    private int HEIGHT;
    private int WIDTH;
    private ComplexNumber c;
    private int maxiter;
    private int blowup;
    private int IMG_WIDTH;
    private int IMG_HEIGHT;
    private double real;
    private double imaginary;

    Fractal(){
        HEIGHT      =     400;
        WIDTH       =     640;
        IMG_WIDTH   =     WIDTH;
        IMG_HEIGHT  =     HEIGHT;
        real        =     -0.8;
        imaginary   =     0.156;
        c           =     new ComplexNumber(real, imaginary);
        maxiter     =     256;
        blowup      =     2;
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(WIDTH,HEIGHT));


    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g = (Graphics2D) graphics;
        long startTime = System.nanoTime();
        Image fractal =  drawFractal();
        long endTime  = System.nanoTime();
        System.out.println((endTime - startTime)/1000000);
        g.drawImage(fractal,0,0,this);

    }

    private Image drawFractal() {
        BufferedImage img = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        for (int x = 0; x < IMG_WIDTH; x++) {
            for (int y = 0; y < IMG_HEIGHT; y++) {
                double X = map(x,0,IMG_WIDTH,-2.0,2.0);
                double Y = map(y,0,IMG_HEIGHT,-1.0,1.0);
                int color = getPixelColor(X,Y);

                img.setRGB(x,y,color);
            }
        }

        return img;
    }

    private double map(double x, double in_min, double in_max, double out_min, double out_max) {
        return (x-in_min)*(out_max-out_min)/(in_max-in_min) + out_min;

    }

    private int getPixelColor(double x, double y) {
        float hue;
        float saturation = 1f;
        float brightness;

        ComplexNumber z = new ComplexNumber(x, y);
        int i;
        for (i = 0; i < maxiter; i++) {
            z.square();
            z.add(c);
            if (z.mod() > blowup) {
                break;
            }
        }

        brightness = (i < maxiter) ? 1f : 0;
        hue = (i%maxiter)/(float)maxiter;
        int rgb = Color.getHSBColor(hue*5,saturation,brightness).getRGB();
        return rgb;

    }
}
