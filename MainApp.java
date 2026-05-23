import javax.swing.*;
public class MainApp {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Alarm Clock App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        JTabbedPane tabs = new JTabbedPane();
        tabs.add("Alarm", new AlarmPanel());
        tabs.add("Timer", new TimerPanel());
        tabs.add("StopWatch", new StopwatchPanel());
        frame.add(tabs);
        frame.setVisible(true);  
    }
}
