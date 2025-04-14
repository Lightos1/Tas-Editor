package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class StickPanel extends JPanel {

    private final Sticktype sticktype;
    private static final int PANEL_SIZE = 200;
    private static final int RADIUS = 100;
    private static final int CENTER_X = PANEL_SIZE / 2;
    private static final int CENTER_Y = PANEL_SIZE / 2;

    private int x = 0;
    private int y = 0;

    private final JTextField xPos;
    private final JTextField yPos;

    private final InputField inputField;

    public StickPanel(InputField inputField, Sticktype sticktype) {
        this.sticktype = sticktype;
        this.inputField = inputField;
        setPreferredSize(new Dimension(PANEL_SIZE, PANEL_SIZE + 40));
        setLayout(null);

        xPos = new JTextField("0");
        yPos = new JTextField("0");

        xPos.setBounds(10, PANEL_SIZE + 5, 100, 30);
        yPos.setBounds(120, PANEL_SIZE + 5, 100, 30);

        add(xPos);
        add(yPos);

        xPos.addActionListener(_ -> updatePos());
        yPos.addActionListener(_ -> updatePos());

        MouseAdapter mouseAdapter = new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                updateMouse(e.getX(), e.getY());
            }

            public void mouseDragged(MouseEvent e) {
                updateMouse(e.getX(), e.getY());
            }
        };
        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);
    }


    private void updatePos() {
        try {
            int inputX = Integer.parseInt(xPos.getText());
            int inputY = Integer.parseInt(yPos.getText());

            inputX = clamp(inputX);
            inputY = clamp(inputY);

            double normX = inputX / 32768.0 * RADIUS;
            double normY = inputY / 32768.0 * RADIUS;

            if ((normX * normX) + (normY * normY) > RADIUS * RADIUS) {
                double angle = Math.atan2(normY, normX);
                normX = Math.cos(angle) * RADIUS;
                normY = Math.cos(angle) * RADIUS;
            }

            x = (int) normX;
            y = (int) normY;

            inputField.getTableModel().setValueAt(xPos.getText() + "," + yPos.getText(), inputField.getHighlightedRow(), sticktype.getValue());
            repaint();
        } catch (NumberFormatException _) {

        }
    }

    private void updateMouse(int mouseX, int mouseY) {
        int dx = mouseX - CENTER_X;
        int dy = mouseY - CENTER_Y;

        double dist = Math.sqrt((dx * dx) + (dy * dy));
        if (dist > RADIUS) {
            dx = (int) ((dx * RADIUS) / dist);
            dy = (int) ((dy* RADIUS) / dist);
        }

        x = dx;
        y = dy;

        xPos.setText(String.valueOf((int) (x / (double) RADIUS * 32768)));
        yPos.setText(String.valueOf((int) (y / (double) RADIUS * 32768)));
        inputField.getTableModel().setValueAt(xPos.getText() + "," + yPos.getText(), inputField.getHighlightedRow(), sticktype.getValue());
        repaint();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.LIGHT_GRAY);
        g.drawOval(CENTER_X - RADIUS, CENTER_Y - RADIUS, RADIUS * 2, RADIUS * 2);

        g.setColor(Color.BLUE);
        g.drawLine(CENTER_X, CENTER_Y, CENTER_X + x, CENTER_Y + y);

        g.setColor(Color.RED);
        g.fillOval(CENTER_X + x - 5, CENTER_Y + y - 5, 10, 10);
    }

    private int clamp(int value) {
        return Math.max(Short.MIN_VALUE, Math.min(Short.MAX_VALUE, value));
    }

}
