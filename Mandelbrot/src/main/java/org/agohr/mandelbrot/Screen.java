package org.agohr.mandelbrot;

import java.awt.Frame;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/** contains the image.*/
public class Screen {

    // TODO iterator on pixels

    /** the image. */
    private final BufferedImage img;

    /** creates the screen object.
     * Height is based on default ratio 16:9.
     * @param width width in pixels. positive and a multiple of 16. */
    Screen(final int width) {
        assert width > 0 && width % 16 == 0;

        final int height = width * 9 / 16;

        this.img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    }

    /** @return width in pixels. */
    final int width() {
        return this.img.getWidth();
    }

    /** @return height in pixels. */
    final int height() {
        return this.img.getHeight();
    }

    // TODO dont provide this
    /** @return the image object. */
    final BufferedImage image() {
        return this.img;
    }

    /** diplays the picture in a fullscreen frame. */
    final void displayImage() {
        final JFrame jf = new JFrame();
        jf.setExtendedState(Frame.MAXIMIZED_BOTH);
        jf.setUndecorated(true);
        final JLabel jl = new JLabel();
        final ImageIcon ii = new ImageIcon(this.img);
        jl.setIcon(ii);
        jf.add(jl);
        jf.pack();
        jf.setVisible(true);
    }

    @Override
    public final String toString() {
        return "org.agohr.mandelbrot.Screen: ( x( " + 0 + " / " + this.width() + " ) , y(  " + 0 + " / " + this.height() + " ) )";
    }

}
