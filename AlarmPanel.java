import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalTime;

public class AlarmPanel extends JPanel {
    private int alarmHour = -1;
    private int alarmMinute = -1;
    private String alarmNote = "";
    private Timer timer;
    private boolean timerActive = false;

    public AlarmPanel() {
        Color deepRose = new Color(194, 24, 91);
        Color peachyPink = new Color(252, 228, 236);

        setBackground(peachyPink);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        int verticalGap = 40;

        // Title
        JLabel label = new JLabel("Set Alarm");
        label.setFont(new Font("Serif", Font.BOLD, 48));
        label.setForeground(deepRose);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(verticalGap, 0, verticalGap, 0);
        add(label, gbc);

        // Spinner Panel
        JPanel spinnerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 0));
        spinnerPanel.setOpaque(false);

        SpinnerModel hoursModel = new SpinnerNumberModel(0, 0, 23, 1);
        JSpinner hoursSpinner = new JSpinner(hoursModel);
        Dimension biggerSpinner = new Dimension(130, 48);
        hoursSpinner.setPreferredSize(biggerSpinner);
        JFormattedTextField hoursEditor = ((JSpinner.DefaultEditor) hoursSpinner.getEditor()).getTextField();
        hoursEditor.setFont(new Font("Serif", Font.BOLD, 26));
        hoursEditor.setBorder(BorderFactory.createEmptyBorder());

        JLabel hourLabel = new JLabel("Hour:");
        hourLabel.setFont(new Font("Serif", Font.BOLD, 34));
        spinnerPanel.add(hourLabel);
        spinnerPanel.add(hoursSpinner);

        SpinnerModel minutesModel = new SpinnerNumberModel(0, 0, 59, 1);
        JSpinner minutesSpinner = new JSpinner(minutesModel);
        minutesSpinner.setPreferredSize(biggerSpinner);
        JFormattedTextField minutesEditor = ((JSpinner.DefaultEditor) minutesSpinner.getEditor()).getTextField();
        minutesEditor.setFont(new Font("Serif", Font.BOLD, 26));
        minutesEditor.setBorder(BorderFactory.createEmptyBorder());

        JLabel minuteLabel = new JLabel("Minute:");
        minuteLabel.setFont(new Font("Serif", Font.BOLD, 34));
        spinnerPanel.add(minuteLabel);
        spinnerPanel.add(minutesSpinner);

        gbc.gridy = 1;
        add(spinnerPanel, gbc);

        // Note Field
        JTextField noteField = new JTextField();
        noteField.setBackground(deepRose);
        noteField.setForeground(peachyPink);
        noteField.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 26));
        noteField.setMaximumSize(new Dimension(420, 56));
        noteField.setPreferredSize(new Dimension(420, 56));
        noteField.setHorizontalAlignment(JTextField.CENTER);

        gbc.gridy = 2;
        add(noteField, gbc);

        // Button
        JButton setAlarmButton = new JButton("Set Alarm");
        setAlarmButton.setBackground(deepRose);
        setAlarmButton.setForeground(Color.BLACK); // Changed to black
        setAlarmButton.setFocusPainted(false);
        setAlarmButton.setFont(new Font("Serif", Font.BOLD, 24));
        setAlarmButton.addActionListener(e -> {
            alarmHour = (Integer) hoursSpinner.getValue();
            alarmMinute = (Integer) minutesSpinner.getValue();
            alarmNote = noteField.getText();
            JOptionPane.showMessageDialog(AlarmPanel.this,
                    "Alarm set for " + String.format("%02d", alarmHour) + ":" + String.format("%02d", alarmMinute)
                            + (alarmNote.isEmpty() ? "" : "\nNote: " + alarmNote));
            if (timer != null && timerActive) timer.stop();
            timer = new Timer(1000, evt -> checkAlarm());
            timerActive = true;
            timer.start();
        });

        gbc.gridy = 3;
        add(setAlarmButton, gbc);
    }

    private void checkAlarm() {
        LocalTime now = LocalTime.now();
        if (now.getHour() == alarmHour && now.getMinute() == alarmMinute) {
            if (timer != null) {
                timer.stop();
                timerActive = false;
            }
            triggerAlarm();
        }
    }

    private void triggerAlarm() {
        setBackground(new Color(255, 182, 193)); // blush pink
        Toolkit.getDefaultToolkit().beep();
        String message = "Alarm ringing! Time to wake up!";
        if (!alarmNote.isEmpty()) message += "\nNote: " + alarmNote;
        JOptionPane.showMessageDialog(this, message);
        setBackground(new Color(252, 228, 236));
    }
}
