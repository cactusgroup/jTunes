package jTunes.gui;

import java.awt.Color;

/**
 * This class defines Colors that are uese in the GUI's color scheme.
 * @author joshuachu
 */
final class ColorConstants {
    public static final Color MINT = Color.decode("0x00FFAB");
    public static final Color LIGHT_MINT;
    // static initializer
    // slightly red-shifts and heavily desaturates ColorConstants::MINT.
    static {
        Color c = ColorConstants.MINT;
        float[] hsbvals = null;
        hsbvals = Color.RGBtoHSB(c.getRed(),
                                 c.getGreen(),
                                 c.getBlue(),
                                 hsbvals);
        LIGHT_MINT = Color.getHSBColor(0.97f*hsbvals[0],
                                       0.3f*hsbvals[1],
                                       hsbvals[2]);
    }
}
