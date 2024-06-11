import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GraphicsPanel extends JPanel {
    public GraphicsPanel() {
        Pacman pacman = new Pacman(); // Create a Pacman instance
        setPreferredSize(new Dimension(400, 400)); // Set the size of the panel

        setBackground(Color.BLACK); // Set the background color of the panel
        add(pacman); // Add the Pacman instance to the panel
        setFocusable(true); // Set the panel focusable to receive key events
        requestFocus(); // Request focus for the panel
        addKeyListener(pacman); // Add key listener to the Pacman instance
    }
}