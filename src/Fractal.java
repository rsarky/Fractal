import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

class Fractal extends JPanel{
    private int HEIGHT;
    private int WIDTH;
    public static ComplexNumber c;
    public static int maxiter;
    public static int blowup;
    public static int IMG_WIDTH;
    public static int IMG_HEIGHT;
    private double real;
    private double imaginary;

    Fractal(){
        HEIGHT      =     600;
        WIDTH       =     800;
        IMG_WIDTH   =     WIDTH;
        IMG_HEIGHT  =     HEIGHT;
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
        BufferedImage img = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Thread t[] = new Thread[4];
        t[0] = new Thread(new Worker(img,0,IMG_WIDTH/2,0,IMG_HEIGHT/2));
        t[1] = new Thread(new Worker(img,0,IMG_WIDTH/2,IMG_HEIGHT/2,IMG_HEIGHT));
        t[2] = new Thread(new Worker(img,IMG_WIDTH/2,IMG_WIDTH,IMG_HEIGHT/2,IMG_HEIGHT));
        t[3] = new Thread(new Worker(img,IMG_WIDTH/2,IMG_WIDTH,0,IMG_HEIGHT/2));

        for(int i=0;i<4;i++) {
            t[i].start();
        }

        try {
            for(int i=0;i<4;i++) {
                t[i].join();
            }
        } catch (Exception e) { }

        return img;
    }

}
