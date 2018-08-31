import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

class Fractal extends JPanel{
    private int HEIGHT;
    private int WIDTH;
    private ComplexNumber c;
    private int maxiter;
    private int blowup;
    private double real;
    private double imaginary;

    Fractal(){
        HEIGHT      =     600;
        WIDTH       =     800;
        real        =     -0.8;
        imaginary   =     0.156;
        c           =     new ComplexNumber(real, imaginary);
        maxiter     =     400;
        blowup      =     4;
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
        BufferedImage img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                double X = map(x,0,WIDTH,-2.0,2.0);
                double Y = map(y,0,HEIGHT,-1.0,1.0);
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
