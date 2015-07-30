package com.sphinx96.c_quark_live_wallpaper;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
//import com.example.sph.Dots1.DotsWallpaperService;import java.lang.Math;import java.lang.Override;

/**
 * Created by Sfynkz on 16/08/2014.
 */
public class SplashCircle extends DrawableObject {
    private float xNew, yNew,xR, yR,xR1,yR1,radius = 0.1f;
    private final int rndModifer = 2;
    private final int rndModifer2 = 2;
    private int alpha,tempAlpha=0;
    private float[] radiusArr = new float[CquarkWallpaperService.MAX_DOT_TRACES];
    private int traceLength = 1;


    public SplashCircle(float x, float y, float c0){
        super(x,y);
        radiusArr[0] = radius;
        alpha = 120+(int)Math.random()*50;
        //alpha =250;
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
            paint.setStyle(Paint.Style.STROKE);
            //paint.setStrokeWidth(1);
            if ((Math.random()-Math.random())>0) paint.setStrokeWidth(1);
            else paint.setStrokeWidth(3);
            paint.setAlpha(alpha);
            canvas.drawCircle(xNew, yNew,radius,paint);
            if (traceLength > CquarkWallpaperService.MAX_DOT_TRACES) traceLength = CquarkWallpaperService.MAX_DOT_TRACES;
            if (traceLength > 0) {
                for (int i = 0; i < traceLength-1; i++) {
                    tempAlpha = alpha - i*10;
                    if (tempAlpha<0) tempAlpha = 0;
                    paint.setAlpha(tempAlpha);
                    canvas.drawCircle(xNew, yNew, radiusArr[i], paint);
//                    canvas.drawCircle(xNew+5, yNew+5, radiusArr[i], paint);
//                    canvas.drawCircle(xNew-5, yNew+5, radiusArr[i], paint);
//                    canvas.drawCircle(xNew+5, yNew-5, radiusArr[i], paint);
                }
                paint.setAlpha(alpha);
                //canvas.drawCircle(xOld[0], yOld[0],2+(float)Math.random()*2,paint);
            }
        }
    }

    @Override
    public void nextState(){
        for (int i = traceLength-1; i>=0;i--){
            if(i==0){
                radiusArr[i] = radius;
            }
            else {
                radiusArr[i] = radiusArr[i-1];
            }
        }
        traceLength++;
        radius+=CquarkWallpaperService.splashCircleRadius;

        alpha-=8;
        if (alpha<0) {
            isActive = false;
        }

    }
}
