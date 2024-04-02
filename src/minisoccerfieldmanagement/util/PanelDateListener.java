package minisoccerfieldmanagement.util;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import minisoccerfieldmanagement.model.ModelDate;

/**
 *
 * @author Raven
 */
public interface PanelDateListener {

    public boolean cellPaint(Graphics2D g2, Rectangle2D rectangle, ModelDate e);
}
