import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class CalendarPanel extends JPanel {
    private Map<Integer, JPanel> dayPanels = new HashMap<>();
    private boolean editMode = false;

    public CalendarPanel() {
        setLayout(new BorderLayout());

        JLabel monthLabel = new JLabel("December", SwingConstants.CENTER);
        monthLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(monthLabel, BorderLayout.NORTH);

        JPanel calendarGrid = new JPanel(new GridLayout(6, 7));
        calendarGrid.setPreferredSize(new Dimension(720, 480));

        String[] daysOfWeek = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        for (String day : daysOfWeek) {
            JLabel dayLabel = new JLabel(day, SwingConstants.CENTER);
            dayLabel.setFont(new Font("Arial", Font.BOLD, 16));
            JPanel headerBox = new JPanel();
            headerBox.add(dayLabel);
            headerBox.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            calendarGrid.add(headerBox);
        }

        int[] weekends = {1, 7, 8, 14, 15, 21, 22, 28, 29}; 
        int[] specialDays = {24, 25};

        for (int i = 1; i <= 31; i++) {
            JPanel box = new JPanel();
            box.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            JLabel dayLabel = new JLabel(String.valueOf(i));
            box.add(dayLabel);
            dayPanels.put(i, box);

            if (contains(weekends, i) || contains(specialDays, i)) {
                box.setBackground(Color.DARK_GRAY);
                dayLabel.setForeground(Color.WHITE);
            }

            box.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    if (editMode) {
                        if (box.getBackground().equals(Color.DARK_GRAY) || box.getBackground().equals(Color.GREEN)) {
                            box.setBackground(null); // Reset to default
                            dayLabel.setForeground(Color.BLACK);
                        } else if (SwingUtilities.isLeftMouseButton(e)) {
                            box.setBackground(Color.GREEN); // Mark as available
                            dayLabel.setForeground(Color.BLACK);
                        } else if (SwingUtilities.isRightMouseButton(e)) {
                            box.setBackground(Color.DARK_GRAY); // Mark as unavailable
                            dayLabel.setForeground(Color.WHITE);
                        }
                    }
                }
            });

            calendarGrid.add(box);
        }

        add(calendarGrid, BorderLayout.CENTER);

        JPanel keyPanel = new JPanel();
        keyPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel availableKey = new JLabel("Green - Available");
        availableKey.setForeground(Color.GREEN);
        JLabel unavailableKey = new JLabel("Dark - Unavailable");
        unavailableKey.setForeground(Color.DARK_GRAY);
        keyPanel.add(availableKey);
        keyPanel.add(Box.createHorizontalStrut(20));
        keyPanel.add(unavailableKey);

        JButton toggleEditMode = new JButton("Toggle Edit Mode");
        toggleEditMode.addActionListener(e -> {
            editMode = !editMode;
            toggleEditMode.setText(editMode ? "Edit Mode: ON" : "Edit Mode: OFF");
        });
        keyPanel.add(toggleEditMode);

        add(keyPanel, BorderLayout.SOUTH);
    }

    private boolean contains(int[] array, int value) {
        for (int i : array) {
            if (i == value) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Calendar");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
            frame.setSize(1280, 720);
            frame.setLocationRelativeTo(null);

            CalendarPanel calendarPanel = new CalendarPanel();
            frame.add(calendarPanel);
            frame.setVisible(true);
        });
    }
}
