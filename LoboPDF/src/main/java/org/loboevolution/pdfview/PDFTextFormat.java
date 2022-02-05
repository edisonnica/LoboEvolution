/*
 * Copyright 2004 Sun Microsystems, Inc., 4150 Network Circle,
 * Santa Clara, California 95054, U.S.A. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
 */
package org.loboevolution.pdfview;

import com.mercuryred.render.interfaces.ui.Color;
import com.mercuryred.render.interfaces.ui.geom.AffineTransform;
import com.mercuryred.render.interfaces.ui.geom.GeneralPath;
import com.mercuryred.render.interfaces.ui.geom.Point2D;
import org.loboevolution.pdfview.font.PDFFont;
import org.loboevolution.pdfview.font.PDFGlyph;

import java.util.List;

/**
 * a class encapsulating the text state
 *
 * Author Mike Wessler
  *
 */
public class PDFTextFormat implements Cloneable {
    /** character spacing */
    private float tc = 0;
    /** word spacing */
    private float tw = 0;
    /** horizontal scaling */
    private float th = 1;
    /** leading */
    private float tl = 0;
    /** rise amount */
    private float tr = 0;
    /** text mode */
    private int tm = PDFShapeCmd.FILL;
    /** text knockout */
    private float tk = 0;
    /** current matrix transform */
    private final AffineTransform cur;
    /** matrix transform at start of line */
    private AffineTransform line;
    /** font */
    private PDFFont font;
    /** font size */
    private float fsize = 1;
    /** are we between BT and ET? */
    private boolean inuse = false;
    // private Object array[]= new Object[1];
    /** build text rep of word */
    private final StringBuilder word = new StringBuilder();
    // this is where we build and keep the word list for this page.
    /** start location of the hunk of text */
    private final Point2D wordStart;
    /** location of the end of the previous hunk of text */
    private final Point2D prevEnd;

    /**
     * create a new PDFTextFormat, with initial values
     */
    public PDFTextFormat() {
        this.cur = com.mercuryred.ui.RenderEngines.Get().createAffineTransform();
        this.line = com.mercuryred.ui.RenderEngines.Get().createAffineTransform();
        this.wordStart = com.mercuryred.ui.RenderEngines.Get().createPoint2D(-100, -100);
        this.prevEnd = com.mercuryred.ui.RenderEngines.Get().createPoint2D(-100, -100);
        this.tc = this.tw = this.tr = this.tk = 0;
        this.tm = PDFShapeCmd.FILL;
        this.th = 1;
    }

    /**
     * reset the PDFTextFormat for a new run
     */
    public void reset() {
        this.cur.setToIdentity();
        this.line.setToIdentity();
        this.inuse = true;
        this.word.setLength(0);
    }

    /**
     * end a span of text
     */
    public void end() {
        this.inuse = false;
    }

    /**
     * get the char spacing
     *
     * @return a float.
     */
    public float getCharSpacing() {
        return this.tc;
    }

    /**
     * set the character spacing
     *
     * @param spc a float.
     */
    public void setCharSpacing(float spc) {
        this.tc = spc;
    }

    /**
     * get the word spacing
     *
     * @return a float.
     */
    public float getWordSpacing() {
        return this.tw;
    }

    /**
     * set the word spacing
     *
     * @param spc a float.
     */
    public void setWordSpacing(float spc) {
        this.tw = spc;
    }

    /**
     * Get the horizontal scale
     *
     * @return the horizontal scale, in percent
     */
    public float getHorizontalScale() {
        return this.th * 100;
    }

    /**
     * set the horizontal scale.
     *
     * @param scl
     * the horizontal scale, in percent (100=normal)
     */
    public void setHorizontalScale(float scl) {
        this.th = scl / 100;
    }

    /**
     * get the leading
     *
     * @return a float.
     */
    public float getLeading() {
        return this.tl;
    }

    /**
     * set the leading
     *
     * @param spc a float.
     */
    public void setLeading(float spc) {
        this.tl = spc;
    }

    /**
     * get the font
     *
     * @return a {@link org.loboevolution.pdfview.font.PDFFont} object.
     */
    public PDFFont getFont() {
        return this.font;
    }

    /**
     * get the font size
     *
     * @return a float.
     */
    public float getFontSize() {
        return this.fsize;
    }

    /**
     * set the font and size
     *
     * @param f a {@link org.loboevolution.pdfview.font.PDFFont} object.
     * @param size a float.
     */
    public void setFont(PDFFont f, float size) {
        this.font = f;
        this.fsize = size;
    }

    /**
     * Get the mode of the text
     *
     * @return a int.
     */
    public int getMode() {
        return this.tm;
    }

    /**
     * set the mode of the text. The correspondence of m to mode is
     * show in the following table. m is a value from 0-7 in binary:
     *
     * 000 Fill
     * 001 Stroke
     * 010 Fill + Stroke
     * 011 Nothing
     * 100 Fill + Clip
     * 101 Stroke + Clip
     * 110 Fill + Stroke + Clip
     * 111 Clip
     *
     * Therefore: Fill corresponds to the low bit being 0; Clip
     * corresponds to the hight bit being 1; and Stroke corresponds
     * to the middle xor low bit being 1.
     *
     * @param m a int.
     */
    public void setMode(int m) {
        int mode = 0;
        if ((m & 0x1) == 0) {
            mode |= PDFShapeCmd.FILL;
        }
        if ((m & 0x4) != 0) {
            mode |= PDFShapeCmd.CLIP;
        }
        if (((m & 0x1) ^ ((m & 0x2) >> 1)) != 0) {
            mode |= PDFShapeCmd.STROKE;
        }
        this.tm = mode;
    }

    /**
     * Set the mode from another text format mode
     *
     * @param mode
     * the text render mode using the
     * codes from PDFShapeCmd and not the wacky PDF codes
     */
    public void setTextFormatMode(int mode) {
        this.tm = mode;
    }

    /**
     * Get the rise
     *
     * @return a float.
     */
    public float getRise() {
        return this.tr;
    }

    /**
     * set the rise
     *
     * @param spc a float.
     */
    public void setRise(float spc) {
        this.tr = spc;
    }

    /**
     * perform a carriage return
     */
    public void carriageReturn() {
        carriageReturn(0, -this.tl);
    }

    /**
     * perform a carriage return by translating by x and y. The next
     * carriage return will be relative to the new location.
     *
     * @param x a float.
     * @param y a float.
     */
    public void carriageReturn(float x, float y) {
        this.line.concatenate(com.mercuryred.ui.RenderEngines.Get().createAffineTransform().getTranslateInstance(x, y));
        this.cur.setTransform(this.line);
    }

    /**
     * Get the current transform
     *
     * @return a {@link com.mercuryred.render.interfaces.ui.geom.AffineTransform} object.
     */
    public AffineTransform getTransform() {
        return this.cur;
    }

    /**
     * set the transform matrix directly
     *
     * @param matrix an array of {@link float} objects.
     */
    public void setMatrix(float[] matrix) {
        this.line = com.mercuryred.ui.RenderEngines.Get().createAffineTransform(matrix);
        this.cur.setTransform(this.line);
    }

    /**
     * add some text to the page.
     *
     * @param cmds
     * the PDFPage to add the commands to
     * @param text
     * the text to add
     * @param autoAdjustStroke a boolean.
     */
    public void doText(PDFPage cmds, String text, boolean autoAdjustStroke) {
        Point2D zero = com.mercuryred.ui.RenderEngines.Get().createPoint2D();
        AffineTransform scale = com.mercuryred.ui.RenderEngines.Get().createAffineTransform(this.fsize * this.th, 0, /* 0 */
        0, this.fsize, /* 0 */
        0, this.tr /* 1 */);
        AffineTransform at = com.mercuryred.ui.RenderEngines.Get().createAffineTransform();
        List<PDFGlyph> l = this.font.getGlyphs(text);
        if (PDFDebugger.SHOW_TEXT_ANCHOR) {
            if (PDFDebugger.DEBUG_TEXT) {
                PDFDebugger.debug("POINT count: " + l.size());
            }
        }
        for (PDFGlyph glyph : l) {
            at.setTransform(this.cur);
            at.concatenate(scale);
            if (PDFDebugger.SHOW_TEXT_REGIONS) {
                GeneralPath path = com.mercuryred.ui.RenderEngines.Get().createGeneralPath();
                path.moveTo(0, 0);
                path.lineTo(1, 0);
                path.lineTo(1, 1);
                path.lineTo(0, 1);
                path.lineTo(0, 0);
                path.closePath();
                path = (GeneralPath) path.createTransformedShape(at);
                if (PDFDebugger.DEBUG_TEXT) {
                    PDFDebugger.debug("BOX " + path.getBounds());
                }
                PDFCmd lastColor = cmds.findLastCommand(PDFFillPaintCmd.class);
                if (PDFDebugger.DEBUG_TEXT) {
                    PDFDebugger.debug("BOX " + lastColor);
                }
                cmds.addFillPaint(PDFPaint.getColorPaint(new Color(160, 160, 255)));
                cmds.addPath(path, PDFShapeCmd.FILL, autoAdjustStroke);
                if (lastColor != null) {
                    cmds.addCommand(lastColor);
                }
            }
            Point2D advance = glyph.getAdvance();
            if (!PDFDebugger.DISABLE_TEXT) {
                advance = glyph.addCommands(cmds, at, this.tm);
            }
            double advanceX = (advance.getX() * this.fsize) + this.tc;
            if (glyph.getChar() == ' ') {
                advanceX += this.tw;
            }
            advanceX *= this.th;
            if (PDFDebugger.SHOW_TEXT_ANCHOR) {
                AffineTransform at2 = com.mercuryred.ui.RenderEngines.Get().createAffineTransform();
                at2.setTransform(this.cur);
                GeneralPath path = com.mercuryred.ui.RenderEngines.Get().createGeneralPath();
                path.moveTo(0, 0);
                path.lineTo(6, 0);
                path.lineTo(6, 6);
                path.lineTo(0, 6);
                path.lineTo(0, 0);
                path.closePath();
                path = (GeneralPath) path.createTransformedShape(at2);
                if (PDFDebugger.DEBUG_TEXT) {
                    PDFDebugger.debug("POINT " + advance);
                }
                PDFCmd lastColor = cmds.findLastCommand(PDFFillPaintCmd.class);
                cmds.addFillPaint(PDFPaint.getColorPaint(new Color(255, 0, 0)));
                cmds.addPath(path, PDFShapeCmd.FILL, autoAdjustStroke);
                if (lastColor != null) {
                    cmds.addCommand(lastColor);
                }
            }
            this.cur.translate(advanceX, advance.getY());
        }
        this.cur.transform(zero, this.prevEnd);
    }

    /**
     * add some text to the page.
     *
     * @param cmds
     * the PDFPage to add the commands to
     * @param ary
     * an array of Strings and Doubles, where the Strings
     * represent text to be added, and the Doubles represent kerning
     * amounts.
     * @param autoAdjustStroke a boolean.
     * @throws org.loboevolution.pdfview.PDFParseException if any.
     */
    public void doText(PDFPage cmds, Object[] ary, boolean autoAdjustStroke) throws PDFParseException {
        for (Object o : ary) {
            if (o instanceof String) {
                doText(cmds, (String) o, autoAdjustStroke);
            } else if (o instanceof Double) {
                float val = ((Double) o).floatValue() / 1000f;
                this.cur.translate(-val * this.fsize * this.th, 0);
            } else {
                throw new PDFParseException("Bad element in TJ array");
            }
        }
    }

    /**
     * finish any unfinished words. TODO: write this!
     */
    public void flush() {
        // TODO: finish any unfinished words
    }

    /**
     * {@inheritDoc}
     *
     * Clone the text format
     */
    @Override
    public Object clone() {
        PDFTextFormat newFormat = new PDFTextFormat();
        // copy values
        newFormat.setCharSpacing(getCharSpacing());
        newFormat.setWordSpacing(getWordSpacing());
        newFormat.setHorizontalScale(getHorizontalScale());
        newFormat.setLeading(getLeading());
        newFormat.setTextFormatMode(getMode());
        newFormat.setRise(getRise());
        // copy immutable fields
        newFormat.setFont(getFont(), getFontSize());
        // clone transform (mutable)
        // newFormat.getTransform().setTransform(getTransform());
        return newFormat;
    }
}
