package org.jhotdraw.draw.figure;

import org.junit.Before;
import org.junit.Test;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import static org.junit.Assert.*;

public class RoundRectangleFigureTest {

    private RoundRectangleFigure roundRect;

    @Before
    public void setUp() {
        roundRect = new RoundRectangleFigure(10, 20, 30, 40);
    }

    // BEST CASE SCENARIOS
    @Test
    public void testGetBounds() {
        Rectangle2D.Double b = roundRect.getBounds();
        assertEquals(10, b.x, 0.001);
        assertEquals(20, b.y, 0.001);
        assertEquals(30, b.width, 0.001);
        assertEquals(40, b.height, 0.001);
    }

    @Test
    public void testSetBoundsBestCase() {
        Point2D.Double anchor = new Point2D.Double(0, 0);
        Point2D.Double lead = new Point2D.Double(50, 100);

        roundRect.setBounds(anchor, lead);

        Rectangle2D.Double b = roundRect.getBounds();
        assertEquals(0, b.x, 0.001);
        assertEquals(0, b.y, 0.001);
        assertEquals(50, b.width, 0.001);
        assertEquals(100, b.height, 0.001);
    }

    @Test
    public void testContainsPointInside() {
        Point2D.Double inside = new Point2D.Double(20, 40);
        assertTrue(roundRect.contains(inside));
    }

    @Test
    public void testTransformTranslate() {
        AffineTransform tx = AffineTransform.getTranslateInstance(10, 10);

        roundRect.transform(tx);
        Rectangle2D.Double b = roundRect.getBounds();

        assertEquals(20, b.x, 0.001);
        assertEquals(30, b.y, 0.001);
    }

    // BOUNDARY CASES
    @Test
    public void testSetBoundsNegativeCoordinates() {
        Point2D.Double anchor = new Point2D.Double(50, 50);
        Point2D.Double lead = new Point2D.Double(0, 0);

        roundRect.setBounds(anchor, lead);
        Rectangle2D.Double b = roundRect.getBounds();

        assertEquals(0, b.x, 0.001);
        assertEquals(0, b.y, 0.001);
        assertEquals(50, b.width, 0.001);
        assertEquals(50, b.height, 0.001);
    }

    @Test
    public void testMinimumWidthHeightIsPointOne() {
        Point2D.Double anchor = new Point2D.Double(10, 10);
        Point2D.Double lead = new Point2D.Double(10, 10); // same point → zero size

        roundRect.setBounds(anchor, lead);
        Rectangle2D.Double b = roundRect.getBounds();

        assertEquals(0.1, b.width, 0.001);
        assertEquals(0.1, b.height, 0.001);
    }

    @Test
    public void testContainsOutsidePoint() {
        Point2D.Double outside = new Point2D.Double(200, 200);
        assertFalse(roundRect.contains(outside));
    }

    @Test
    public void testTransformScale() {
        AffineTransform tx = AffineTransform.getScaleInstance(2, 2);
        roundRect.transform(tx);

        Rectangle2D.Double b = roundRect.getBounds();

        assertEquals(20, b.x, 0.001);
        assertEquals(40, b.y, 0.001);
        assertEquals(60, b.width, 0.001);
        assertEquals(80, b.height, 0.001);
    }

    // INVARIANTS
    @Test
    public void testInvariantRoundRectWidthHeightNeverNegative() {
        roundRect.setBounds(new Point2D.Double(5,5), new Point2D.Double(5,5));
        Rectangle2D.Double b = roundRect.getBounds();

        assert b.width >= 0.1 : "Width invariant violated";
        assert b.height >= 0.1 : "Height invariant violated";
    }

    // ARC PROPERTIES
    @Test
    public void testArcWidthHeight() {
        roundRect.setArc(15, 25);

        assertEquals(15, roundRect.getArcWidth(), 0.001);
        assertEquals(25, roundRect.getArcHeight(), 0.001);
    }

    // COPY/CLONE
    @Test
    public void testCloneCreatesDeepCopy() {
        RoundRectangleFigure copy = roundRect.clone();

        Rectangle2D.Double b1 = roundRect.getBounds();
        Rectangle2D.Double b2 = copy.getBounds();

        assertEquals(b1.x, b2.x, 0.001);
        assertEquals(b1.y, b2.y, 0.001);
        assertNotSame(roundRect.getTransformRestoreData(), copy.getTransformRestoreData());
    }
}
