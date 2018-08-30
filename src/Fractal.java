import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.ForkJoinPool;

class Fractal extends JPanel{
    public static int HEIGHT;
    public static int WIDTH;
    public static ComplexNumber c;
    public static int maxiter;
    public static int blowup;
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
        System.out.println(Runtime.getRuntime().availableProcessors());

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

        ForkCalculate fork = new ForkCalculate(img, 0, WIDTH, HEIGHT);
        ForkJoinPool forkPool = new ForkJoinPool();
        forkPool.invoke(fork);
        return img;
    }

}
