import javax.swing.*;
import java.awt.*;

class window {

    window(){
        JFrame frame = new JFrame("FRACTAL");
        Fractal frac = new Fractal(); //Content Pane

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(frac);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    public static void main(String args[]){
        SwingUtilities.invokeLater(() -> new window());
    }
}
