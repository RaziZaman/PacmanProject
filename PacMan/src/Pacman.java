import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
public class Pacman extends JPanel implements KeyListener, ActionListener {
    private Image blueDown, blueLeft, blueRight, blueUp, pinkDown, pinkLeft, pinkRight, pinkUp, redDown, redLeft, redRight, redUp, yellowDown, yellowLeft, yellowRight, yellowUp, powerPelletGhost;
    private int blockSize = 40;
    private int score = 0;
    private static final int POWER_PELLET_DURATION = 10000;
    private boolean powerPelletConsumed = false;
    private double powerPelletStartTime;
    private int lives = 3;
    private int count2 = 0;
    private int currentMapIndex = 0;
    private int[][] map1 = {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 2, 2, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 2, 2, 1}, // 2 represents a dot
            {1, 3, 1, 1, 2, 1, 1, 1, 2, 1, 2, 1, 1, 1, 2, 1, 1, 3, 1},
            {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
            {1, 2, 1, 1, 2, 1, 2, 1, 1, 1, 1, 1, 2, 1, 2, 1, 1, 2, 1},
            {1, 2, 2, 2, 2, 1, 2, 2, 2, 1, 2, 2, 2, 1, 2, 2, 2, 2, 1},
            {1, 1, 1, 1, 2, 1, 1, 1, 0, 1, 0, 1, 1, 1, 2, 1, 1, 1, 1},
            {0, 0, 0, 1, 2, 1, 0, 0, 0, 0, 0, 0, 0, 1, 2, 1, 0, 0, 0},
            {1, 1, 1, 1, 2, 1, 0, 1, 1, 1, 1, 1, 0, 1, 2, 1, 1, 1, 1},
            {2, 2, 2, 2, 2, 0, 0, 1, 0, 0, 0, 1, 0, 0, 2, 2, 2, 2, 2},
            {1, 1, 1, 1, 2, 1, 0, 1, 1, 1, 1, 1, 0, 1, 2, 1, 1, 1, 1},
            {0, 0, 0, 1, 2, 1, 0, 0, 0, 0, 0, 0, 0, 1, 2, 1, 0, 0, 0},
            {1, 1, 1, 1, 2, 1, 0, 1, 1, 1, 1, 1, 0, 1, 2, 1, 1, 1, 1},
            {1, 2, 2, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 2, 2, 1},
            {1, 2, 1, 1, 2, 1, 1, 1, 2, 1, 2, 1, 1, 1, 2, 1, 1, 2, 1},
            {1, 3, 2, 1, 2, 2, 2, 2, 2, 0, 2, 2, 2, 2, 2, 1, 2, 3, 1},
            {1, 1, 2, 1, 2, 1, 2, 1, 1, 1, 1, 1, 2, 1, 2, 1, 2, 1, 1},
            {1, 2, 2, 2, 2, 1, 2, 2, 2, 1, 2, 2, 2, 1, 2, 2, 2, 2, 1},
            {1, 2, 1, 1, 1, 1, 1, 1, 2, 1, 2, 1, 1, 1, 1, 1, 1, 2, 1},
            {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
    };
    private int[][] map2 = {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
            {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
            {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
            {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
            {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
            {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
            {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
            {1, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 0, 1, 2, 1, 1, 1, 1},
            {1, 2, 2, 2, 2, 2, 2, 1, 0, 0, 0, 1, 0, 0, 2, 2, 2, 2, 1},
            {1, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 1},
            {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
            {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
            {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
            {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
            {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
            {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
            {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
            {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
            {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
    };
    private int[][] map3 = {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
            {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
            {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
            {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
            {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
            {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
            {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
            {1, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 0, 1, 2, 1, 1, 1, 1},
            {1, 2, 2, 2, 2, 2, 2, 1, 0, 0, 0, 1, 0, 0, 2, 2, 2, 2, 1},
            {1, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 1},
            {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
            {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
            {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
            {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
            {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
            {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
            {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
            {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
            {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
    };
    private int[][] map4 = {
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 1, 0, 0, 1, 1, 1, 0, 1, 1, 1, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 1, 0, 1, 1, 1, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0},
            {0, 0, 0, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0},
            {0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0},
            {0, 0, 0, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
    };



    private int[][] currentMap = map1;
    private int rows = currentMap.length;
    private int cols = currentMap[0].length;
    private int pacmanX = 9 * blockSize; // Initial x position
    private int pacmanY = 15 * blockSize; // Initial y position
    private int pacmanSize = blockSize; // Size of the Pacman sprite
    private int step = 40; // Step size for movement
    private int totalDots = 0; // Total number of dots
    private int dotsCollected = 0; // Number of dots collected
    private Timer timer; // Timer for animation
    private Timer sirenTimer; // Timer for siren sound
    private Random random = new Random();
    private int count = 0;
    private enum Direction {UP, DOWN, LEFT, RIGHT}
    private Direction currentDirection = Direction.UP;
    private Ghost[] ghosts;


    public Pacman() {

        setPreferredSize(new Dimension(cols * blockSize, rows * blockSize));
        setBackground(Color.BLACK);
        addKeyListener(this);
        setFocusable(true);
        loadMap(map1);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (currentMap[i][j] == 2) {
                    totalDots++;
                }
            }
        }
        try {
            redDown = ImageIO.read(new File("RedDown.png"));
            redUp = ImageIO.read(new File("RedUp.png"));
            redLeft = ImageIO.read(new File("RedLeft.png"));
            redRight = ImageIO.read(new File("RedRight.png"));
            pinkDown = ImageIO.read(new File("pinkDown.png"));
            pinkUp = ImageIO.read(new File("pinkUp.png"));
            pinkLeft = ImageIO.read(new File("pinkLeft.png"));
            pinkRight = ImageIO.read(new File("pinkRight.png"));
            yellowDown = ImageIO.read(new File("yellowDown.png"));
            yellowUp = ImageIO.read(new File("yellowUp.png"));
            yellowLeft = ImageIO.read(new File("yellowLeft.png"));
            yellowRight = ImageIO.read(new File("yellowRight.png"));
            blueDown = ImageIO.read(new File("blueDown.png"));
            blueUp = ImageIO.read(new File("blueUp.png"));
            blueLeft = ImageIO.read(new File("blueLeft.png"));
            blueRight = ImageIO.read(new File("blueRight.png"));
            powerPelletGhost = ImageIO.read(new File("PowerPelletGhost.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ghosts = new Ghost[4];
        ghosts[0] = new Ghost(9 * blockSize, 7 * blockSize, Color.RED);
        ghosts[1] = new Ghost(8 * blockSize, 9 * blockSize, Color.BLUE);
        ghosts[2] = new Ghost(9 * blockSize, 9 * blockSize, Color.ORANGE);
        ghosts[3] = new Ghost(10 * blockSize, 9 * blockSize, Color.PINK);

        timer = new Timer(200, this);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (currentMap[i][j] == 1 && i == 8 && j == 9) {
                    g.setColor(Color.LIGHT_GRAY);
                    g.fillRect(j * blockSize, i * blockSize, blockSize, blockSize); // Draw walls
                } else if (currentMap[i][j] == 1) {
                    if (currentMap == map1) {
                        g.setColor(Color.CYAN);
                    } else if (currentMap == map2) {
                        g.setColor(Color.RED);
                    } else if (currentMap == map3) {
                        g.setColor(Color.GREEN);
                    } else {
                        g.setColor(Color.PINK);
                    }
                    g.fillRect(j * blockSize, i * blockSize, blockSize, blockSize); // Draw walls
                } else if (currentMap[i][j] == 2) {
                    g.setColor(Color.WHITE);
                    g.fillOval(j * blockSize + blockSize / 2 - 3, i * blockSize + blockSize / 2 - 3, 6, 6); // Draw dots
                } else if (currentMap[i][j] == 3) {
                    g.setColor(Color.WHITE);
                    g.fillOval(j * blockSize + blockSize / 2 - 3 * (20/6), i * blockSize + blockSize / 2 - 3 * (20/6), 20, 20); // Draw dots
                }
            }
        }
        for (Ghost ghost : ghosts) {
            ghost.draw(g);
        }

        g.setColor(Color.YELLOW);
        int mouthAngle = 0;

        if (timer.isRunning()) {
            long elapsed = System.currentTimeMillis();
            mouthAngle = (int) (Math.sin(elapsed * 0.01) * 20);
        }

        if (currentDirection == Direction.UP) {
            g.fillArc(pacmanX, pacmanY, pacmanSize, pacmanSize, 135 + mouthAngle, 270 - 2 * mouthAngle); // Pacman facing up
        } else if (currentDirection == Direction.DOWN) {
            g.fillArc(pacmanX, pacmanY, pacmanSize, pacmanSize, 315 + mouthAngle, 270 - 2 * mouthAngle); // Pacman facing down
        } else if (currentDirection == Direction.LEFT) {
            g.fillArc(pacmanX, pacmanY, pacmanSize, pacmanSize, 225 + mouthAngle, 270 - 2 * mouthAngle); // Pacman facing left
        } else if (currentDirection == Direction.RIGHT) {
            g.fillArc(pacmanX, pacmanY, pacmanSize, pacmanSize, 45 + mouthAngle, 270 - 2 * mouthAngle); // Pacman facing right
        }
        if (lives == 2) {
            g.fillArc(20, 860, pacmanSize, pacmanSize, 45, 285);
        } else if (lives == 3) {
            g.fillArc(20, 860, pacmanSize, pacmanSize, 45, 285);
            g.fillArc(65, 860, pacmanSize, pacmanSize, 45, 285);
        }

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString("Score: " + score, 500, 900);
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT) {
            currentDirection = Direction.LEFT;
        } else if (key == KeyEvent.VK_RIGHT) {
            currentDirection = Direction.RIGHT;
        } else if (key == KeyEvent.VK_UP) {
            currentDirection = Direction.UP;
        } else if (key == KeyEvent.VK_DOWN) {
            currentDirection = Direction.DOWN;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void actionPerformed(ActionEvent e) {
        if (lives <= 0) {
            endGame();
            return;
        }
        if (!powerPelletConsumed && isGhostCollision(pacmanX, pacmanY)) {
            for (int i = 0; i < ghosts.length; i++) {
                ghosts[i].sendAllGhostsSpawn();
                count = 30;
            }
            pacmanX = 9 * blockSize;
            pacmanY = 15 * blockSize;
            currentDirection = Direction.UP;
            lives--;
            return;
        } else if (powerPelletConsumed && isGhostCollision(pacmanX, pacmanY)) {
            for (int i = 0; i < ghosts.length; i++) {
                ghosts[i].sendGhostToSpawn();
                playPacmanGhostEatSound();
            }
        }

        if (powerPelletConsumed) {
            long elapsedTime = System.currentTimeMillis() - (long) powerPelletStartTime;
            if (elapsedTime >= POWER_PELLET_DURATION) {
                powerPelletConsumed = false;
            }
        }

        int nextX = pacmanX;
        int nextY = pacmanY;

        if (currentDirection == Direction.LEFT) {
            nextX -= step;
            if (nextX < 0)
                nextX = cols * blockSize - blockSize;
        } else if (currentDirection == Direction.RIGHT) {
            nextX += step;
            if (nextX >= cols * blockSize)
                nextX = 0;
        } else if (currentDirection == Direction.UP) {
            nextY -= step;
            if (nextY < 0)
                nextY = rows * blockSize - blockSize;
        } else if (currentDirection == Direction.DOWN) {
            nextY += step;
            if (nextY >= rows * blockSize)
                nextY = 0;
        }

        for (int i = 3; i < 4; i++) {
            ghosts[0].moveToTarget(pacmanX,pacmanY);
            ghosts[1].moveInky();
            ghosts[2].moveClyde();
            ghosts[3].movePinky();
            count++;
            if (count > 30) {
                if (!powerPelletConsumed) {
                    ghosts[i].releaseNextGhost();
                    count = 0;
                }
            }
        }


        if (!isWallCollision(nextX, nextY)) {
            pacmanX = nextX;
            pacmanY = nextY;
            collectDot(pacmanX, pacmanY);
        }

        repaint();
    }

    private void loadMap(int[][] map) {
        currentMap = map;
        rows = map.length;
        cols = map[0].length;
    }

    private void switchToNextMap() {

        currentMapIndex++;
        if (currentMapIndex == 1) {
            loadMap(map2);
        } else if (currentMapIndex == 2) {
            loadMap(map3);
        } else {
            currentMapIndex = 0;
            loadMap(map1);
        }
    }
    private boolean isGhostCollision(int x, int y) {
        for (Ghost ghost : ghosts) {
            if (x >= ghost.getX() && x < ghost.getX() + blockSize && y >= ghost.getY() && y < ghost.getY() + blockSize) {
                return true;
            }
        }
        return false;
    }

    private boolean isWallCollision(int x, int y) {
        int row = y / blockSize;
        int col = x / blockSize;
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            return true;
        }

        return currentMap[row][col] == 1;
    }

    private void collectDot(int x, int y) {
        int row = y / blockSize;
        int col = x / blockSize;
        if (currentMap[row][col] == 2) {
            currentMap[row][col] = 0;
            dotsCollected++;
            playPacmanPelletEatSound();
            score += 10;
            if (dotsCollected == totalDots) {
                switchToNextMap();
                for (int i = 0; i < ghosts.length; i++) {
                    ghosts[i].sendAllGhostsSpawn();
                    count = 30;
                }
                powerPelletConsumed = false;
                pacmanX = 9 * blockSize;
                pacmanY = 15 * blockSize;
                totalDots = 0;
                dotsCollected = 0;
                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < cols; j++) {
                        if (currentMap[i][j] == 2) {
                            totalDots++;
                        }
                    }
                }
            }
        } else if (currentMap[row][col] == 3) {
            currentMap[row][col] = 0;
            powerPelletConsumed = true;
            powerPelletStartTime = System.currentTimeMillis();
            score += 50;
        }
    }

    private void endGame() {
        timer.stop();
    }
    private class Ghost {
        private int x;
        private int y;
        private Color color;
        private Direction previousDirection;
        private Direction currentGhostDirection;

        public Ghost(int x, int y, Color color) {
            this.x = x;
            this.y = y;
            this.color = color;
            this.previousDirection = null;
            this.currentGhostDirection = Direction.UP;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public void setX(int x) {
            this.x = x;
        }

        public void setY(int y) {
            this.y = y;
        }

        public void releaseNextGhost() {
            if (ghosts[0].getY() == 9 * blockSize && (ghosts[0].getX() == 8 * blockSize || ghosts[0].getX() == 9 * blockSize || ghosts[0].getX() == 10 * blockSize)) {
                ghosts[0].setX(9 * blockSize);
                ghosts[0].setY(7 * blockSize);
            } else if (ghosts[1].getY() == 9 * blockSize && (ghosts[1].getX() == 8 * blockSize || ghosts[1].getX() == 9 * blockSize || ghosts[1].getX() == 10 * blockSize)) {
                ghosts[1].setX(9 * blockSize);
                ghosts[1].setY(7 * blockSize);
            } else if (ghosts[2].getY() == 9 * blockSize && (ghosts[2].getX() == 8 * blockSize || ghosts[2].getX() == 9 * blockSize || ghosts[2].getX() == 10 * blockSize)) {
                ghosts[2].setX(9 * blockSize);
                ghosts[2].setY(7 * blockSize);
            } else if (ghosts[3].getY() == 9 * blockSize && (ghosts[3].getX() == 8 * blockSize || ghosts[3].getX() == 9 * blockSize || ghosts[3].getX() == 10 * blockSize)) {
                ghosts[3].setX(9 * blockSize);
                ghosts[3].setY(7 * blockSize);
            }
        }

        public void movePinky() {
            int targetX = pacmanX;
            int targetY = pacmanY;

            if (currentDirection == Direction.UP) {
                targetY -= 4 * step;
            } else if (currentDirection == Direction.DOWN) {
                targetY += 4 * step;
            } else if (currentDirection == Direction.LEFT) {
                targetX -= 4 * step;
            } else if (currentDirection == Direction.RIGHT) {
                targetX += 4 * step;
            }
            moveToTarget(targetX, targetY);
        }

        public void moveInky() {
            int blinkyX = ghosts[0].getX();
            int blinkyY = ghosts[0].getY();

            int targetX = pacmanX;
            int targetY = pacmanY;

            if (currentDirection == Direction.UP) {
                targetY -= 2 * step;
                targetX -= 2 * step;
            } else if (currentDirection == Direction.DOWN) {
                targetY += 2 * step;
            } else if (currentDirection == Direction.LEFT) {
                targetX -= 2 * step;
            } else if (currentDirection == Direction.RIGHT) {
                targetX += 2 * step;
            }

            int intermediateX = 2 * (targetX - blinkyX) + blinkyX;
            int intermediateY = 2 * (targetY - blinkyY) + blinkyY;

            moveToTarget(intermediateX, intermediateY);
        }

        public void moveClyde() {
            int clydeRange = 8 * blockSize;
            double distance = Math.sqrt(Math.pow(pacmanX - x, 2) + Math.pow(pacmanY - y, 2));
            if (distance > clydeRange) {
                moveToTarget(pacmanX, pacmanY);
            } else {
                moveRandomly();
            }
            if (x < 0) {
                x = (cols - 1) * blockSize;
            } else if (x >= cols * blockSize) {
                x = 0;
            }
        }

        private void sendGhostToSpawn() {
            if (x == pacmanX && y == pacmanY) {
                y = 9 * blockSize;
                if (!(ghosts[0].getX() == 8 * blockSize || ghosts[1].getX() == 8 * blockSize || ghosts[2].getX() == 8 * blockSize)) {
                    x = 8 * blockSize;
                } else if (!(ghosts[0].getX() == 9 * blockSize || ghosts[1].getX() == 9 * blockSize || ghosts[2].getX() == 9 * blockSize)) {
                    x = 9 * blockSize;
                } else if (!(ghosts[0].getX() == 10 * blockSize || ghosts[1].getX() == 10 * blockSize || ghosts[2].getX() == 10 * blockSize)) {
                    x = 10 * blockSize;
                } else {
                    x = 8 * blockSize;
                }
            }
        }
        public void sendAllGhostsSpawn() {
            y = 9 * blockSize;
            if (!(ghosts[0].getX() == 8 * blockSize || ghosts[1].getX() == 8 * blockSize || ghosts[2].getX() == 8 * blockSize)) {
                x = 8 * blockSize;
            } else if (!(ghosts[0].getX() == 9 * blockSize || ghosts[1].getX() == 9 * blockSize || ghosts[2].getX() == 9 * blockSize)) {
                x = 9 * blockSize;
            } else if (!(ghosts[0].getX() == 10 * blockSize || ghosts[1].getX() == 10 * blockSize || ghosts[2].getX() == 10 * blockSize)) {
                x = 10 * blockSize;
            } else {
                x = 8 * blockSize;
            }
        }

        public void moveRandomly() {
            if (!(y == 9 * blockSize && (x == 8 * blockSize || x == 9 * blockSize || x == 10 * blockSize))) {
                int[] directions = {0, 1, 2, 3};
                ArrayList<Integer> validMoves = new ArrayList<>();

                for (int dir : directions) {
                    if (previousDirection != null &&
                            ((previousDirection == Direction.LEFT && dir == 1) || (previousDirection == Direction.RIGHT && dir == 0) || (previousDirection == Direction.UP && dir == 3) || (previousDirection == Direction.DOWN && dir == 2))) {
                        continue;
                    }

                    if (dir == 0) { // LEFT
                        if (x - step >= 0 && !isWallCollision(x - step, y)) {
                            validMoves.add(dir);
                        }
                    } else if (dir == 1) {
                        if (x + step < cols * blockSize && !isWallCollision(x + step, y)) {
                            validMoves.add(dir);
                        }
                    } else if (dir == 2) {
                        if (y - step >= 0 && !isWallCollision(x, y - step)) {
                            validMoves.add(dir);
                        }
                    } else if (dir == 3) {
                        if (y + step < rows * blockSize && !isWallCollision(x, y + step)) {
                            validMoves.add(dir);
                        }
                    }
                }

                if (!validMoves.isEmpty()) {
                    int nextMove = validMoves.get(random.nextInt(validMoves.size()));
                    previousDirection = currentGhostDirection;

                    if (nextMove == 0) {
                        x -= step;
                        currentGhostDirection = Direction.LEFT;
                    } else if (nextMove == 1) {
                        x += step;
                        currentGhostDirection = Direction.RIGHT;
                    } else if (nextMove == 2) {
                        y -= step;
                        currentGhostDirection = Direction.UP;
                    } else if (nextMove == 3) {
                        y += step;
                        currentGhostDirection = Direction.DOWN;
                    }

                    if (x < 0) {
                        x = (cols - 1) * blockSize;
                    } else if (x >= cols * blockSize) {
                        x = 0;
                    }
                }
            }
        }

        private void moveToTarget(int targetX, int targetY) {
            if (!(y == 9 * blockSize && (x == 8 * blockSize || x == 9 * blockSize || x == 10 * blockSize))) {
                if (x < 0) {
                    x = cols * blockSize - blockSize;
                } else if (x >= cols * blockSize) {
                    x = 0;
                } else if (y < 0) {
                    y = rows * blockSize - blockSize;
                } else if (y >= rows * blockSize) {
                    y = 0;
                }
                targetX = Math.max(0, Math.min(targetX, (cols - 1) * blockSize));
                targetY = Math.max(0, Math.min(targetY, (rows - 1) * blockSize));

                int dx = targetX - x;
                int dy = targetY - y;

                if (powerPelletConsumed) {
                    if (Math.abs(dx) > Math.abs(dy)) {
                        if (dx > 0 && !isWallCollision(x - step, y)) {
                            x -= step;
                            currentGhostDirection = Direction.LEFT;
                        } else if (dx < 0 && !isWallCollision(x + step, y)) {
                            x += step;
                            currentGhostDirection = Direction.RIGHT;
                        } else if (dy > 0 && !isWallCollision(x, y - step)) {
                            y -= step;
                            currentGhostDirection = Direction.UP;
                        } else if (dy < 0 && !isWallCollision(x, y + step)) {
                            y += step;
                            currentGhostDirection = Direction.DOWN;
                        } else {
                            moveRandomly();
                        }
                    } else {
                        if (dy > 0 && !isWallCollision(x, y - step)) {
                            y -= step;
                            currentGhostDirection = Direction.UP;
                        } else if (dy < 0 && !isWallCollision(x, y + step)) {
                            y += step;
                            currentGhostDirection = Direction.DOWN;
                        } else if (dx > 0 && !isWallCollision(x - step, y)) {
                            x -= step;
                            currentGhostDirection = Direction.LEFT;
                        } else if (dx < 0 && !isWallCollision(x + step, y)) {
                            x += step;
                            currentGhostDirection = Direction.RIGHT;
                        } else {
                            moveRandomly();
                        }
                    }
                } else {
                    if (Math.abs(dx) > Math.abs(dy)) {
                        if (dx > 0 && !isWallCollision(x + step, y)) {
                            x += step;
                            currentGhostDirection = Direction.RIGHT;
                        } else if (dx < 0 && !isWallCollision(x - step, y)) {
                            x -= step;
                            currentGhostDirection = Direction.LEFT;
                        } else if (dy > 0 && !isWallCollision(x, y + step)) {
                            y += step;
                            currentGhostDirection = Direction.DOWN;
                        } else if (dy < 0 && !isWallCollision(x, y - step)) {
                            y -= step;
                            currentGhostDirection = Direction.UP;
                        } else {
                            moveRandomly();
                        }
                    } else {
                        if (dy > 0 && !isWallCollision(x, y + step)) {
                            y += step;
                            currentGhostDirection = Direction.DOWN;
                        } else if (dy < 0 && !isWallCollision(x, y - step)) {
                            y -= step;
                            currentGhostDirection = Direction.UP;
                        } else if (dx > 0 && !isWallCollision(x + step, y)) {
                            x += step;
                            currentGhostDirection = Direction.RIGHT;
                        } else if (dx < 0 && !isWallCollision(x - step, y)) {
                            x -= step;
                            currentGhostDirection = Direction.LEFT;
                        } else {
                            moveRandomly();
                        }
                    }
                }
            }
        }

        public void draw(Graphics g) {

            Image img = null;
            if (powerPelletConsumed) {
                img = powerPelletGhost;
            } else {
                if (currentGhostDirection == Direction.UP) {
                    if (color == Color.RED) {
                        img = redUp;
                    } else if (color == Color.BLUE) {
                        img = blueUp;
                    } else if (color == Color.ORANGE) {
                        img = yellowUp;
                    } else if (color == Color.PINK) {
                        img = pinkUp;
                    }
                } else if (currentGhostDirection == Direction.DOWN) {
                    if (color == Color.RED) {
                        img = redDown;
                    } else if (color == Color.BLUE) {
                        img = blueDown;
                    } else if (color == Color.ORANGE) {
                        img = yellowDown;
                    } else if (color == Color.PINK) {
                        img = pinkDown;
                    }
                } else if (currentGhostDirection == Direction.LEFT) {
                    if (color == Color.RED) {
                        img = redLeft;
                    } else if (color == Color.BLUE) {
                        img = blueLeft;
                    } else if (color == Color.ORANGE) {
                        img = yellowLeft;
                    } else if (color == Color.PINK) {
                        img = pinkLeft;
                    }
                } else if (currentGhostDirection == Direction.RIGHT) {
                    if (color == Color.RED) {
                        img = redRight;
                    } else if (color == Color.BLUE) {
                        img = blueRight;
                    } else if (color == Color.ORANGE) {
                        img = yellowRight;
                    } else if (color == Color.PINK) {
                        img = pinkRight;
                    }
                }
            }
            g.drawImage(img, x, y, blockSize, blockSize, null);
        }
    }
    private void playPacmanPelletEatSound() {
        count2 = 0;
        try {
            File soundFile = new File("pacmanPelletEat.wav");
            File soundFile2 = new File("munch_2.wav");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            AudioInputStream audioIn2 = AudioSystem.getAudioInputStream(soundFile2);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            Clip clip2 = AudioSystem.getClip();
            clip2.open(audioIn2);
            if (count2 == 0) {
                clip.start();
                count2 = 1;
            } else {
                clip2.start();
                count2 = 0;
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    private void pLaySiren() {
        try {
            File soundFile = new File("siren_1.wav");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    private void playPacmanGhostEatSound() {
        try {
            File soundFile = new File("pacmanGhostEat.wav");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

}
