import javax.swing.*;
import java.awt.*;
public class TimerPanel extends JPanel {
    private static final Color BG_COLOR = new Color(245, 203, 241);
    private static final Color DARK_LAVENDER = new Color(180, 152, 205);

    private JSpinner hourSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 23, 1));
    private JSpinner minuteSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 59, 1));
    private JSpinner secondSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 59, 1));

    private JButton startBtn = new JButton("Start");
    private JButton pauseBtn = new JButton("Pause");
    private JButton resumeBtn = new JButton("Resume");
    private JButton resetBtn = new JButton("Reset");
    private JLabel display = new JLabel("00:00:00", SwingConstants.CENTER);

    private javax.swing.Timer swingTimer;
    private int remainingSeconds = 0;
    private int initialSeconds = 0;

    public TimerPanel() {
        setLayout(new BorderLayout());
        setBackground(BG_COLOR);
        // Style spinners
        JTextField hourField = ((JSpinner.DefaultEditor) hourSpinner.getEditor()).getTextField();
        styleSpinnerField(hourField);
        JTextField minuteField = ((JSpinner.DefaultEditor) minuteSpinner.getEditor()).getTextField();
        styleSpinnerField(minuteField);
        JTextField secondField = ((JSpinner.DefaultEditor) secondSpinner.getEditor()).getTextField();
        styleSpinnerField(secondField);
        // Style buttons
        JButton[] buttons = {startBtn, pauseBtn, resumeBtn, resetBtn};
        for (JButton btn : buttons) {
            btn.setBackground(DARK_LAVENDER);
            btn.setOpaque(true);
            btn.setFont(new Font("Monaco", Font.BOLD, 30));
        }
        display.setFont(new Font("Monaco", Font.BOLD, 70));
        // Setup labels and time input panel
        JPanel timePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        timePanel.setBackground(BG_COLOR);

        JLabel hourLabel = new JLabel("Hours:");
        hourLabel.setFont(new Font("Monaco", Font.BOLD, 45));
        JLabel minuteLabel = new JLabel("Minutes:");
        minuteLabel.setFont(new Font("Monaco", Font.BOLD, 45));
        JLabel secondLabel = new JLabel("Seconds:");
        secondLabel.setFont(new Font("Monaco", Font.BOLD, 45));

        timePanel.add(hourLabel);
        timePanel.add(hourSpinner);
        timePanel.add(minuteLabel);
        timePanel.add(minuteSpinner);
        timePanel.add(secondLabel);
        timePanel.add(secondSpinner);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(BG_COLOR);
        for (JButton btn : buttons) buttonPanel.add(btn);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(BG_COLOR);
        display.setAlignmentX(Component.CENTER_ALIGNMENT);
        timePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        centerPanel.add(Box.createVerticalStrut(30));
        centerPanel.add(display);
        centerPanel.add(Box.createVerticalStrut(30));
        centerPanel.add(timePanel);
        centerPanel.add(Box.createVerticalStrut(5));
        centerPanel.add(buttonPanel);
        centerPanel.add(Box.createVerticalStrut(30));

        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        wrapper.setBackground(BG_COLOR);
        wrapper.add(Box.createVerticalGlue());
        wrapper.add(centerPanel);
        wrapper.add(Box.createVerticalGlue());
        add(wrapper, BorderLayout.CENTER);

        pauseBtn.setEnabled(false);
        resumeBtn.setEnabled(false);
        resetBtn.setEnabled(false);
        startBtn.addActionListener(e -> startCountdown());
        pauseBtn.addActionListener(e -> pauseCountdown());
        resumeBtn.addActionListener(e -> resumeCountdown());
        resetBtn.addActionListener(e -> resetCountdown());
        swingTimer = new javax.swing.Timer(1000, e -> tick());
    }
    private void styleSpinnerField(JTextField field) {
        field.setEditable(false);
        field.setBackground(DARK_LAVENDER);
        field.setOpaque(true);
        field.setFont(new Font("Monaco", Font.PLAIN, 25));
        ((JComponent) field.getParent()).setBackground(DARK_LAVENDER);
        ((JComponent) field.getParent()).setOpaque(true);
    }
    private void startCountdown() {
        remainingSeconds = (int) hourSpinner.getValue() * 3600
                         + (int) minuteSpinner.getValue() * 60
                         + (int) secondSpinner.getValue();
        if (remainingSeconds <= 0) {
            JOptionPane.showMessageDialog(this, "Please select a time greater than zero.");
            return;
        }
        initialSeconds = remainingSeconds;
        updateDisplay();
        swingTimer.start();
        startBtn.setEnabled(false);
        pauseBtn.setEnabled(true);
        resumeBtn.setEnabled(false);
        resetBtn.setEnabled(true);
        hourSpinner.setEnabled(false);
        minuteSpinner.setEnabled(false);
        secondSpinner.setEnabled(false);
    }
    private void pauseCountdown() {
        swingTimer.stop();
        pauseBtn.setEnabled(false);
        resumeBtn.setEnabled(true);
    }
    private void resumeCountdown() {
        swingTimer.start();
        pauseBtn.setEnabled(true);
        resumeBtn.setEnabled(false);
    }
    private void resetCountdown() {
        swingTimer.stop();
        remainingSeconds = initialSeconds;
        updateDisplay();
        startBtn.setEnabled(true);
        pauseBtn.setEnabled(false);
        resumeBtn.setEnabled(false);
        resetBtn.setEnabled(false);
        hourSpinner.setEnabled(true);
        minuteSpinner.setEnabled(true);
        secondSpinner.setEnabled(true);
    }
    private void tick() {
        if (remainingSeconds > 0) {
            remainingSeconds--;
            updateDisplay();
            if (remainingSeconds == 0) {
                swingTimer.stop();
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(this, "Time's up!");
            } } }
    private void updateDisplay() {
        int h = remainingSeconds / 3600;
        int m = (remainingSeconds % 3600) / 60;
        int s = remainingSeconds % 60;
        display.setText(String.format("%02d:%02d:%02d", h, m, s));
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame f = new JFrame("Timer");
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setSize(900, 500);
            f.setLocationRelativeTo(null);
            f.getContentPane().setBackground(BG_COLOR);
            f.add(new TimerPanel());
            f.setVisible(true);
        });
    } }
