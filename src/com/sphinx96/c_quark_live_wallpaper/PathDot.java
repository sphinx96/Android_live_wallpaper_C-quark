package com.sphinx96.c_quark_live_wallpaper;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
//import com.example.sph.Dots1.DotsWallpaperService;import java.lang.Math;import java.lang.Override;

/**
 * Created by Sfynkz on 16/08/2014.
 */
public class PathDot extends DrawableObject {
    private float xNew, yNew,xR, yR,xR1,yR1;
    private final int rndModifer = 2;
    private final int rndModifer2 = 3;
    private int alpha,tempAlpha=0;
    private float[] xOld = new float[CquarkWallpaperService.MAX_DOT_TRACES];
    private float[] yOld = new float[CquarkWallpaperService.MAX_DOT_TRACES];
    private int traceLength = 1;


    public PathDot(float x, float y, float c0){
        super(x,y);
        alpha = 200+(int)Math.random()*50;
        alpha =250;
        xNew = x;
        yNew = y;

        color[0] = c0;
        color[1] = 1;
        color[2] = 1;
        paint.setColor(Color.HSVToColor(alpha,color));
        xR = (float)Math.random()*rndModifer - (float)Math.random()*rndModifer;
        yR = (float)Math.random()*rndModifer - (float)Math.random()*rndModifer;
        xR1 = (float)Math.random()*rndModifer2 - (float)Math.random()*rndModifer2;
        yR1 = (float)Math.random()*rndModifer2 - (float)Math.random()*rndModifer2;



    }

    @Override
    public void draw(Canvas canvas){
        if (isActive){
            //canvas.drawCircle(xNew,yNew,20,paint);
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            paint.setStrokeWidth(2);
            if (traceLength > CquarkWallpaperService.MAX_DOT_TRACES) traceLength = CquarkWallpaperService.MAX_DOT_TRACES;
            if (traceLength > 0) {
                for (int i = 0; i < traceLength-1; i++) {
                    tempAlpha = alpha - i*10;
                    if (tempAlpha<0) tempAlpha = 0;
                    paint.setAlpha(tempAlpha);
                    //canvas.drawPoint(xOld[i], yOld[i], paint);
                    if(i>0)
                        canvas.drawLine(xOld[i], yOld[i],xOld[i-1],yOld[i-1], paint);
                       // canvas.drawCircle(xOld[i], yOld[i],5-(float)Math.random()*tempAlpha/100,paint);
                    //if(i==traceLength-2)    canvas.drawCircle(xOld[i], yOld[i],10,paint);
                       // canvas.drawPoint(xOld[i], yOld[i], paint);
                }
                if (alpha>0)paint.setAlpha(alpha);

                canvas.drawCircle(xOld[0], yOld[0],2+(float)Math.random()*1,paint);
            }
        }
    }

    @Override
    public void nextState(){


        for (int i = traceLength-1; i>=0;i--){
            if(i==0){
                xOld[i] = xNew;
                yOld[i] = yNew;
            }
            else {
                xOld[i] = xOld[i-1];
                yOld[i] = yOld[i-1];
            }
        }
        traceLength++;
        if (xNew < -3000 || xNew >3000 || yNew < -3000 || yNew>3000) isActive = false;
        else {
            xNew = xNew + xR + xR1 * yNew / 25;
            yNew = yNew + yR + yR1 * xNew / 25;
        }
        /*if (xNew > -5 && xNew < 1500) {
            if (yNew > -5 && yNew < 1500){*/

        //}}

        /*if (xNew<-100 || xNew>2000) xNew = -100;
        if (yNew<-100|| yNew>2000) yNew = -100;*/
        alpha--;
        if (alpha<0) {
            isActive = false;
        }

    }
}
