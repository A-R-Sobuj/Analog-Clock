import java.awt.*;
import java.awt.geom.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.*;

// This class creates a clock that runs in a separate thread
public class Analog_Clock_With_GUI extends JPanel implements Runnable
{
    // These store the current time values
    private int hours;
    private int minutes;
    private int seconds;
    private double smoothSecond;
    private double smoothMinute;
    private double smoothHour;

    // When the clock is made, it starts running automatically
    public Analog_Clock_With_GUI()
    {
        Thread thread = new Thread(this);
        thread.start();
    }

    // This method runs again and again to keep the clock moving
    @Override
    public void run()
    {
        while (true)
        {
            // Get the current time
            Calendar now = GregorianCalendar.getInstance();
            hours   = now.get(Calendar.HOUR);
            minutes = now.get(Calendar.MINUTE);
            seconds = now.get(Calendar.SECOND);

            // Smooth movement for second, minute, and hour hands
            smoothSecond = seconds + now.get(Calendar.MILLISECOND) / 1000.0;
            smoothMinute = minutes + smoothSecond / 60.0;
            smoothHour   = hours + smoothMinute / 60.0;

            // Redraw the clock
            repaint();

            // Wait for a short time before updating again
            try
            {
                Thread.sleep(20);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }

    // This method draws everything on the clock
    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Get size of the panel and center point
        int width   = getWidth();
        int height  = getHeight();
        int centerX = width  / 2;
        int centerY = height / 2;
        int outerRadius = Math.min(centerX, centerY) - 6;
        int faceRadius  = (int) (outerRadius * 0.88);

        int borderThickness = (int) (outerRadius * 0.06);

        // Make things look smooth
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw black border around clock
        float[] dist = { 0.0f, 1.0f };
        Color[] colors = {
            Color.BLACK,
            new Color(30, 30, 30)
        };
        RadialGradientPaint gradient = new RadialGradientPaint(
            new Point2D.Double(centerX, centerY),
            outerRadius,
            dist,
            colors
        );
        g2.setPaint(gradient);
        g2.fillOval(centerX - outerRadius,
                    centerY - outerRadius,
                    outerRadius * 2,
                    outerRadius * 2);

        // Draw white clock face
        g2.setColor(Color.WHITE);
        g2.fillOval(centerX - faceRadius,
                    centerY - faceRadius,
                    faceRadius * 2,
                    faceRadius * 2);

        // Draw the hour numbers and bold marks
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Serif", Font.BOLD, 28));

        for (int i = 1; i <= 12; i++)
        {
            // Angle for each number
            double angle = Math.toRadians(i * 30 - 90);

            // Line for each hour mark
            int x1 = (int) (centerX + (faceRadius - 10) * Math.cos(angle));
            int y1 = (int) (centerY + (faceRadius - 10) * Math.sin(angle));
            int x2 = (int) (centerX + faceRadius * Math.cos(angle));
            int y2 = (int) (centerY + faceRadius * Math.sin(angle));

            g2.setStroke(new BasicStroke(4));
            g2.drawLine(x1, y1, x2, y2);

            // Draw the number at the right position
            String num = String.valueOf(i);
            FontMetrics fm = g2.getFontMetrics();
            int strWidth  = fm.stringWidth(num);
            int strHeight = fm.getAscent();

            int textX = (int) (centerX + (faceRadius - 30) * Math.cos(angle));
            int textY = (int) (centerY + (faceRadius - 30) * Math.sin(angle));
            g2.drawString(num, textX - strWidth / 2, textY + strHeight / 3);
        }

        // Draw small minute marks between hours
        for (int i = 0; i < 60; i++)
        {
            double angle = Math.toRadians(i * 6 - 90);
            int tickStart = (i % 5 == 0) ? faceRadius - 10 : faceRadius - 5;
            int tickEnd   = faceRadius;

            int x1 = (int) (centerX + tickStart * Math.cos(angle));
            int y1 = (int) (centerY + tickStart * Math.sin(angle));
            int x2 = (int) (centerX + tickEnd   * Math.cos(angle));
            int y2 = (int) (centerY + tickEnd   * Math.sin(angle));

            g2.setStroke(new BasicStroke((i % 5 == 0) ? 2f : 1.2f));
            g2.drawLine(x1, y1, x2, y2);
        }

        // Set the length of clock hands
        int secondHandLength = faceRadius - 45;
        int minuteHandLength = faceRadius - 85;
        int hourHandLength   = (int) (faceRadius * 0.45);

        // Draw red second hand
        drawHand(g2, centerX, centerY,
                 Math.toRadians(smoothSecond * 6 - 90),
                 secondHandLength, Color.RED, 2);

        // Draw blue minute hand
        drawHand(g2, centerX, centerY,
                 Math.toRadians(smoothMinute * 6 - 90),
                 minuteHandLength, new Color(0, 70, 200), 8);

        // Draw black hour hand
        drawHand(g2, centerX, centerY,
                 Math.toRadians(smoothHour * 30 - 90),
                 hourHandLength, new Color(0, 0, 0), 10);

        // Draw green center dot
        g2.setColor(Color.GREEN);
        g2.fillOval(centerX - 7, centerY - 7, 14, 14);

        // Draw name "Sobuj" and "1319" below it
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Garamond", Font.BOLD, 18));
        FontMetrics fm = g2.getFontMetrics();
        String label = "Sobuj";
        int labelWidth = fm.stringWidth(label);
        int labelX = centerX - labelWidth / 2;
        int labelY = centerY + (int)(faceRadius * 0.50);
        g2.drawString(label, labelX, labelY);

        // Draw "1319" just below "Sobuj"
        String label2 = "1319";
        int label2Width = fm.stringWidth(label2);
        int label2X = centerX - label2Width / 2;
        int label2Y = labelY + fm.getHeight();
        g2.drawString(label2, label2X, label2Y);
    }

    // This function draws each hand of the clock
    private void drawHand(Graphics2D g2,
                          int centerX,
                          int centerY,
                          double angle,
                          int length,
                          Color color,
                          int thickness)
    {
        int x = (int) (centerX + length * Math.cos(angle));
        int y = (int) (centerY + length * Math.sin(angle));

        g2.setColor(color);
        g2.setStroke(new BasicStroke(thickness,
                                     BasicStroke.CAP_SQUARE,
                                     BasicStroke.JOIN_MITER));
        g2.drawLine(centerX, centerY, x, y);
    }

    // This method starts the whole program and shows the window
    public static void main(String[] args)
    {
        JFrame frame = new JFrame("Sobuj's Analog Clock");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.add(new Analog_Clock_With_GUI());
        frame.setVisible(true);
    }
}
