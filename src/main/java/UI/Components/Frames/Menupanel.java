package UI.Components.Frames;



import javax.swing.*;
import java.awt.*;

public class Menupanel extends JFrame {
    
    public Menupanel() {
        // Set up the frame
        setTitle("JUNO");
        setSize(400, 600);  
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        
        
    
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        
        
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.setBackground(Color.RED);
        topPanel.setPreferredSize(new Dimension(400, 240));
        topPanel.setMaximumSize(new Dimension(2000, 240));
        ImageIcon imageIcon = new ImageIcon("C:/Users/lenovo/JUNO.png");
        JLabel JUNO = new JLabel(imageIcon);
        topPanel.add(JUNO);
        
        JLabel subtitleLabel = new JLabel("UNO", JLabel.CENTER);
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        subtitleLabel.setForeground(Color.WHITE);
        topPanel.add(subtitleLabel, BorderLayout.SOUTH);
        
        
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(Color.WHITE);
        
        
        JButton startButton = createRoundButton("Start Game", Color.RED, Color.WHITE);
        JButton howToPlayButton = createRoundButton("How to Play", new Color(255, 204, 102), Color.BLACK);
        JButton exitButton = createRoundButton("Exit", Color.GRAY, Color.WHITE);
        
        
        buttonPanel.add(startButton);
        buttonPanel.add(Box.createVerticalStrut(15));
        buttonPanel.add(howToPlayButton);
        buttonPanel.add(Box.createVerticalStrut(15));
        buttonPanel.add(exitButton);
        buttonPanel.add(Box.createVerticalStrut(40));
        
    

        JPanel circlePanel = new JPanel();
        circlePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));
        
        circlePanel.add(createCircle(Color.RED));
        circlePanel.add(createCircle(Color.YELLOW)); 
        circlePanel.add(createCircle(Color.GREEN));
        circlePanel.add(createCircle(Color.BLUE));
        circlePanel.setBackground(Color.WHITE);
        
        
        bottomPanel.add(buttonPanel);
        bottomPanel.add(circlePanel);
        
        
        mainPanel.add(topPanel);
        mainPanel.add(bottomPanel);
        
    
        add(mainPanel, BorderLayout.CENTER);
        
        
        
    }
    
    // Create a round-cornered button
    private JButton createRoundButton(String text, Color bgColor, Color textColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 25, 25);
                super.paintComponent(g);
            }
            
            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(getBackground().darker());
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 25, 25);
            }
        };
        
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(bgColor);
        button.setForeground(textColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(300, 50));
        button.setPreferredSize(new Dimension(300, 50));
        
        return button;
    }
    
    
    private JPanel createCircle(Color color) {
        JPanel circle = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(color);
                g.fillOval(0, 0, getWidth(), getHeight());
                g.setColor(Color.BLACK);
                g.drawOval(0, 0, getWidth()-1, getHeight()-1);
            }
        };
        
        circle.setPreferredSize(new Dimension(40, 40));
        circle.setOpaque(false);
        return circle;
    }
    
    
    public static void main(String[] args) {
   
                Menupanel frame = new Menupanel();
                frame.setVisible(true);
            }
        };
    