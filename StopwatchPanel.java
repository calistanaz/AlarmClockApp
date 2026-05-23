import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StopwatchPanel extends JPanel implements ActionListener {
    private JLabel timeLabel;
    private JButton startBtn, stopBtn, resetBtn;
    private Timer timer;
    private long elapsedMillis = 0;
    private long startMillis = 0;
    private boolean running = false;

    public StopwatchPanel() {
        setLayout(new GridBagLayout());
        setBackground(Color.decode("#F8C8C8"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(15, 0, 15, 0);

        // Time display
        timeLabel = new JLabel("00:00:00", SwingConstants.CENTER);
        timeLabel.setFont(new Font("Consolas", Font.BOLD, 60));
        timeLabel.setForeground(Color.decode("#7B2D26"));

        gbc.gridy = 0;
        add(timeLabel, gbc);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(Color.decode("#F8C8C8"));

        startBtn = createButton("Start", "#E57373");
        stopBtn = createButton("Stop", "#C94C4C");
        resetBtn = createButton("Reset", "#FF8A65");

        buttonPanel.add(startBtn);
        buttonPanel.add(stopBtn);
        buttonPanel.add(resetBtn);

        gbc.gridy = 1;
        add(buttonPanel, gbc);

        // CORRECT: javax.swing.Timer initialization for stopwatch
        timer = new Timer(1000, e -> updateDisplay());
    }

    private JButton createButton(String text, String hexColor) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 18));
        btn.setForeground(Color.BLACK); // Now button text is black
        btn.setBackground(Color.decode(hexColor));
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(120, 45));
        btn.addActionListener(this);
        return btn;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startBtn) startStopwatch();
        if (e.getSource() == stopBtn) stopStopwatch();
        if (e.getSource() == resetBtn) resetStopwatch();
    }

    public void startStopwatch() {
        if (!running) {
            startMillis = System.currentTimeMillis() - elapsedMillis;
            timer.start();
            running = true;
        }
    }

    public void stopStopwatch() {
        if (running) {
            elapsedMillis = System.currentTimeMillis() - startMillis;
            timer.stop();
            running = false;
        }
    }

    public void resetStopwatch() {
        timer.stop();
        running = false;
        elapsedMillis = 0;
        startMillis = 0;
        timeLabel.setText("00:00:00");
    }

    private void updateDisplay() {
        long now = running ? (System.currentTimeMillis() - startMillis) : elapsedMillis;
        long totalSec = now / 1000;
        long h = totalSec / 3600;
        long m = (totalSec % 3600) / 60;
        long s = totalSec % 60;
        timeLabel.setText(String.format("%02d:%02d:%02d", h, m, s));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Stopwatch");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 400);
            frame.setLocationRelativeTo(null);
            frame.add(new StopwatchPanel());
            frame.setVisible(true);
        });
    }
}
