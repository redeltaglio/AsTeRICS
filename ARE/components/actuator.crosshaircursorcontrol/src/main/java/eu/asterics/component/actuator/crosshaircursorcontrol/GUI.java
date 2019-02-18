
/*
 *    AsTeRICS - Assistive Technology Rapid Integration and Construction Set
 *
 *
 *        d8888      88888888888       8888888b.  8888888 .d8888b.   .d8888b.
 *       d88888          888           888   Y88b   888  d88P  Y88b d88P  Y88b
 *      d88P888          888           888    888   888  888    888 Y88b.
 *     d88P 888 .d8888b  888   .d88b.  888   d88P   888  888         "Y888b.
 *    d88P  888 88K      888  d8P  Y8b 8888888P"    888  888            "Y88b.
 *   d88P   888 "Y8888b. 888  88888888 888 T88b     888  888    888       "888
 *  d8888888888      X88 888  Y8b.     888  T88b    888  Y88b  d88P Y88b  d88P
 * d88P     888  88888P' 888   "Y8888  888   T88b 8888888 "Y8888P"   "Y8888P"
 *
 *
 *                    homepage: http://www.asterics.org
 *
 *       This project has been partly funded by the European Commission,
 *                      Grant Agreement Number 247730
 *
 *
 *         Dual License: MIT or GPL v3.0 with "CLASSPATH" exception
 *         (please refer to the folder LICENSE)
 *
 */

package eu.asterics.component.actuator.crosshaircursorcontrol;

import java.awt.*;

import javax.swing.*;

import eu.asterics.mw.services.AstericsErrorHandling;

/**
 * Implements the Graphical User Interface for the CrosshairCursorControl plugin
 *
 * @author Chris, Date: 2019-01-20
 */
public class GUI extends JFrame {

    int width = 0, height = 0;
    int lineWidth = 0;

    double locX = 0;
    double locY = 0;

    private boolean highlightXAxis = false;
    private boolean highlightYAxis = true;
    private boolean currentHighlightXAxis = false;
    private boolean currentHighlightYAxis = false;
    private boolean highlightClick = false;
    private boolean active = true;
    // private JLabel myLabel;
    // add more GUI elements here

    /**
     * The class constructor, initialises the GUI
     *
     * @param owner
     *            the owner class instance
     * @param dim
     *            the dimension of the screen
     * @param lineWidth
     *            the width of horizontal and vertial crosshair lines
     */
    public GUI(final CrosshairCursorControlInstance owner, final Dimension dim, final int lineWidth) {
        super("CursorMovementPanel");
        this.width = width;
        setUndecorated(true);
        setAlwaysOnTop(true);
        setBackground(new Color(0, 0, 0, 0)); // transparent !
        setSize(dim);
        width = dim.width;
        height = dim.height;
        this.lineWidth = lineWidth;
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setOpacity(0.5f);
        setVisible(true);

        setLocation(0, 0);
        Point location = MouseInfo.getPointerInfo().getLocation();
        locX = location.x;
        locY = location.y;
        repaintInternal();
    }

    void setOnTop() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                setAlwaysOnTop(false);
                repaint();
                setAlwaysOnTop(true);
                repaint();
            }
        });
    }

    void showCrosshair() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                setVisible(true);
            }
        });
    }

    void hideCrosshair() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                setVisible(false);
            }
        });
    }

    void resetAxis() {
        highlightXAxis = false;
        highlightYAxis = false;
        if (currentHighlightXAxis || currentHighlightYAxis) {
            repaintInternal();
        }
    }

    void toggleAxis() {
        highlightXAxis = !highlightXAxis;
        highlightYAxis = !highlightYAxis;
        repaintInternal();
    }

    void setXAxisHighlight(boolean highlightXAxis) {
        this.highlightXAxis = highlightXAxis;
        if (currentHighlightXAxis != highlightXAxis) {
            repaintInternal();
        }
    }

    void setYAxisHighlight(boolean highlightYAxis) {
        this.highlightYAxis = highlightYAxis;
        if (currentHighlightYAxis != highlightYAxis) {
            repaintInternal();
        }
    }

    void doAxisClickHighlight() {
        highlightClick = true;
        repaintInternal();
    }

    synchronized void setCursor(float x, float y) {
        locX = x;
        locY = y;

        repaintInternal();
    }

    void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        Color xAxisColor, yAxisColor;

        xAxisColor = Color.GRAY;
        yAxisColor = Color.GRAY;
        currentHighlightXAxis = false;
        currentHighlightYAxis = false;
        if (highlightXAxis && active) {
            xAxisColor = Color.RED;
            currentHighlightXAxis = true;
        }
        if (highlightYAxis && active) {
            yAxisColor = Color.RED;
            currentHighlightYAxis = true;
        }
        if (highlightClick) {
            xAxisColor = Color.GREEN;
            yAxisColor = Color.GREEN;
        }

        g2.setColor(yAxisColor);
        g2.fillRect((int) locX - lineWidth / 2, 0, lineWidth, (int) locY - lineWidth / 2);
        g2.fillRect((int) locX - lineWidth / 2, (int) locY + lineWidth / 2, lineWidth, height - (int) locY);

        g2.setColor(xAxisColor);
        g2.fillRect(0, (int) locY - lineWidth / 2, (int) locX - lineWidth / 2, lineWidth);
        g2.fillRect((int) locX + lineWidth / 2, (int) locY - lineWidth / 2, width - (int) locX, lineWidth);

        if (highlightClick) {
            highlightClick = false;
            repaintInternal(150);
        }
    }

    private void repaintInternal(final long sleepMs) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                if (sleepMs > 0) {
                    sleepInternal(sleepMs);
                }
                repaint();
            }
        });
    }

    private void repaintInternal() {
        repaintInternal(0);
    }

    private void sleepInternal(long ms) {
        try {
            Thread.currentThread().sleep(ms);
        } catch (InterruptedException e) {
            AstericsErrorHandling.instance.getLogger().warning("GUI sleep error: " + e.getMessage());
        }
    }
}