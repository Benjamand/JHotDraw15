/*
 * @(#)RoundRectangleFigure.java
 *
 * Copyright (c) 1996-2010 The authors and contributors of JHotDraw.
 * You may not use, copy or modify this file, except in compliance with the
 * accompanying license terms.
 */
package org.jhotdraw.draw.figure;

import java.awt.*;
import java.awt.geom.*;
import java.io.*;
import java.util.*;

import org.jhotdraw.draw.AttributeKeys;
import org.jhotdraw.draw.connector.ChopRoundRectangleConnector;
import org.jhotdraw.draw.connector.Connector;
import org.jhotdraw.draw.handle.Handle;
import org.jhotdraw.draw.handle.RoundRectangleRadiusHandle;
import org.jhotdraw.geom.Geom;
import org.jhotdraw.xml.DOMInput;
import org.jhotdraw.xml.DOMOutput;

/**
 * A {@link Figure} with a rounded rectangular shape.
 * <p>
 * This figure has two JavaBeans properties {@code arcWidth} and
 * {@code arcHeight} which specify the corner radius.
 * <p>
 * This figure creates a {@link RoundRectangleRadiusHandle} which allows
 * to interactively change the corner radius.

 * (Refactored to remove duplicate code.)
 *
 * @author Werner Randelshofer
 */
public class RoundRectangleFigure extends AbstractAttributedFigure {

    private static final long serialVersionUID = 1L;

    /** Identifies the {@code arcWidth} JavaBeans property. */
    public static final String ARC_WIDTH_PROPERTY = "arcWidth";

    /** Identifies the {@code arcHeight} JavaBeans property. */
    public static final String ARC_HEIGHT_PROPERTY = "arcHeight";

    protected RoundRectangle2D.Double roundrect;
    protected static final double DEFAULT_ARC = 20;

    /**
     * Creates a new instance.
     */
    public RoundRectangleFigure() {
        this(0, 0, 0, 0);
    }

    public RoundRectangleFigure(double x, double y, double width, double height) {
        roundrect = new RoundRectangle2D.Double(x, y, width, height, DEFAULT_ARC, DEFAULT_ARC);
    }

//new Helper to remove duplicate code (SRP + OCP improvement)

    /**
     * Returns a grown clone of the round rectangle. Growth increases x/y bounds
     * as well as arc radii.
     */
    private RoundRectangle2D.Double getGrownRoundRect(double growth) {
        RoundRectangle2D.Double r = (RoundRectangle2D.Double) roundrect.clone();
        r.x -= growth;
        r.y -= growth;
        r.width += growth * 2;
        r.height += growth * 2;
        r.arcwidth += growth * 2;
        r.archeight += growth * 2;
        return r;
    }

//drawing

    @Override
    protected void drawFill(Graphics2D g) {
        double grow = AttributeKeys.getPerpendicularFillGrowth(
                this,
                AttributeKeys.getScaleFactorFromGraphics(g)
        );

        RoundRectangle2D.Double r = getGrownRoundRect(grow);

        if (r.width > 0 && r.height > 0) {
            g.fill(r);
        }
    }

    @Override
    protected void drawStroke(Graphics2D g) {
        double grow = AttributeKeys.getPerpendicularDrawGrowth(
                this,
                AttributeKeys.getScaleFactorFromGraphics(g)
        );

        RoundRectangle2D.Double r = getGrownRoundRect(grow);

        if (r.width > 0 && r.height > 0) {
            g.draw(r);
        }
    }

// shape and boound

    @Override
    public Rectangle2D.Double getBounds() {
        return (Rectangle2D.Double) roundrect.getBounds2D();
    }

    @Override
    public Rectangle2D.Double getDrawingArea() {
        Rectangle2D.Double r = (Rectangle2D.Double) roundrect.getBounds2D();
        double grow = AttributeKeys.getPerpendicularHitGrowth(this, 1.0) + 1;
        Geom.grow(r, grow, grow);
        return r;
    }

    //height and wieght properties

    public double getArcWidth() {
        return roundrect.arcwidth;
    }

    public double getArcHeight() {
        return roundrect.archeight;
    }

    public void setArcWidth(double newValue) {
        double oldValue = roundrect.arcwidth;
        roundrect.arcwidth = newValue;
        firePropertyChange(ARC_WIDTH_PROPERTY, oldValue, newValue);
    }

    public void setArcHeight(double newValue) {
        double oldValue = roundrect.archeight;
        roundrect.archeight = newValue;
        firePropertyChange(ARC_HEIGHT_PROPERTY, oldValue, newValue);
    }

    public void setArc(double width, double height) {
        setArcWidth(width);
        setArcHeight(height);
    }



    @Override
    public boolean contains(Point2D.Double p) {
        double grow = AttributeKeys.getPerpendicularHitGrowth(this, 1.0);
        RoundRectangle2D.Double r = getGrownRoundRect(grow);
        return r.contains(p);
    }

    // this part is for changing what the figure is bound to

    @Override
    public void setBounds(Point2D.Double anchor, Point2D.Double lead) {
        roundrect.x = Math.min(anchor.x, lead.x);
        roundrect.y = Math.min(anchor.y, lead.y);
        roundrect.width = Math.max(0.1, Math.abs(lead.x - anchor.x));
        roundrect.height = Math.max(0.1, Math.abs(lead.y - anchor.y));
    }

    @Override
    public void transform(AffineTransform tx) {
        Point2D.Double anchor = getStartPoint();
        Point2D.Double lead = getEndPoint();

        setBounds(
                (Point2D.Double) tx.transform(anchor, anchor),
                (Point2D.Double) tx.transform(lead, lead)
        );
    }

//this part is for editing the figure

    @Override
    public Collection<Handle> createHandles(int detailLevel) {
        LinkedList<Handle> handles = (LinkedList<Handle>) super.createHandles(detailLevel);
        handles.add(new RoundRectangleRadiusHandle(this));
        return handles;
    }

    @Override
    public void restoreTransformTo(Object geometry) {
        RoundRectangle2D.Double r = (RoundRectangle2D.Double) geometry;
        roundrect.x = r.x;
        roundrect.y = r.y;
        roundrect.width = r.width;
        roundrect.height = r.height;
    }

    @Override
    public Object getTransformRestoreData() {
        return roundrect.clone();
    }

//this part is for connecting corners

    @Override
    public Connector findConnector(Point2D.Double p, ConnectionFigure prototype) {
        return new ChopRoundRectangleConnector(this);
    }

    @Override
    public Connector findCompatibleConnector(Connector c, boolean isStartConnector) {
        return new ChopRoundRectangleConnector(this);
    }

// this part is for cloning

    @Override
    public RoundRectangleFigure clone() {
        RoundRectangleFigure that = (RoundRectangleFigure) super.clone();
        that.roundrect = (RoundRectangle2D.Double) this.roundrect.clone();
        return that;
    }

// persistance

    @Override
    public void read(DOMInput in) throws IOException {
        super.read(in);
        roundrect.arcwidth = in.getAttribute("arcWidth", DEFAULT_ARC);
        roundrect.archeight = in.getAttribute("arcHeight", DEFAULT_ARC);
    }

    @Override
    public void write(DOMOutput out) throws IOException {
        super.write(out);
        out.addAttribute("arcWidth", roundrect.arcwidth);
        out.addAttribute("arcHeight", roundrect.archeight);
    }
}
