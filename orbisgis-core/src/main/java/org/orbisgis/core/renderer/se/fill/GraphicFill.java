package org.orbisgis.core.renderer.se.fill;

import java.awt.Graphics2D;

import java.awt.Shape;
import java.awt.TexturePaint;

import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.renderable.RenderContext;
import java.io.IOException;

import javax.media.jai.RenderableGraphics;
import org.gdms.data.DataSource;
import org.orbisgis.core.renderer.se.common.RenderContextFactory;
import org.orbisgis.core.renderer.se.common.Uom;
import org.orbisgis.core.renderer.se.graphic.GraphicCollection;
import org.orbisgis.core.renderer.se.parameter.ParameterException;
import org.orbisgis.core.renderer.se.parameter.real.RealParameter;

public class GraphicFill extends Fill{

    public GraphicFill(){
    }

    public void setGraphic(GraphicCollection graphic){
        this.graphic = graphic;
        graphic.setParent(this);
    }

    public GraphicCollection getGraphic(){
        return graphic;
    }

    public void setUom(Uom uom){
        this.uom = uom;
    }

    @Override
    public Uom getUom(){
        if (uom == null)
            return parent.getUom();
        else
            return uom;
    }

    public void setGapX(RealParameter gap){
        gapX = gap;
    }
    public void setGapY(RealParameter gap){
        gapY = gap;
    }

    public RealParameter getGapX(){
        return gapX;
    }
    public RealParameter getGapY(){
        return gapY;
    }


    /**
     * see Fill
     */
    @Override
    public void draw(Graphics2D g2, Shape shp, DataSource ds, long fid) throws ParameterException, IOException {
        g2.setPaint(this.getStipplePainter(ds, fid));
        g2.fill(shp);
    }

    /**
     * Create a new TexturePaint according to this GraphicFill
     * 
     * @param ds DataSource
     * @param fid feature id
     * @return a TexturePain ready to be used
     * @throws ParameterException
     * @throws IOException
     */
    public TexturePaint getStipplePainter(DataSource ds, long fid) throws ParameterException, IOException{
        RenderableGraphics img = graphic.getGraphic(ds, fid);

        double gX = 0.0;
        double gY = 0.0;

        if (gapX != null){
           gX = gapX.getValue(ds, fid);
           if (gX < 0.0)
               gX = 0.0;
        }

        if (gapY != null){
           gY = gapY.getValue(ds, fid);
           if (gY < 0.0)
               gY = 0.0;
        }

        gX = Uom.toPixel(gX, getUom(), 96, 35000);
        gY = Uom.toPixel(gY, getUom(), 96, 35000);

        BufferedImage i = new BufferedImage((int)(img.getWidth() + gX) , (int)(img.getHeight() + gY), BufferedImage.TYPE_INT_ARGB);
        Graphics2D tile = i.createGraphics();

        RenderContext ctc = RenderContextFactory.getContext();


        tile.drawRenderedImage(img.createRendering(ctc), AffineTransform.getTranslateInstance(-img.getMinX() + gX/2.0, -img.getMinY() + gY/2.0));
        tile.finalize();

        return new TexturePaint(i, new Rectangle2D.Double(0, 0, i.getWidth(), i.getHeight()));
    }


    private GraphicCollection graphic;
    private Uom uom;
    private RealParameter gapX;
    private RealParameter gapY;
}
