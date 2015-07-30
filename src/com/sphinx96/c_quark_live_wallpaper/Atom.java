package com.sphinx96.c_quark_live_wallpaper;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by Sfynkz on 16/08/2014.
 */
public class Atom extends DrawableObject {
    private float xNew, yNew,xR, yR,xR1,yR1,radius,baseRadius=0.5f,rotation=0,rotationBase;
    private final int rndModifer = 3;
    private final int rndModifer2 = 2;
    private final int rndModifer3 = 10;
    private final int rndModifer4 = 8;
    private int alpha,tempAlpha=0,atomsCount;
    private float[] xOld = new float[CquarkWallpaperService.MAX_DOT_TRACES];
    private float[] yOld = new float[CquarkWallpaperService.MAX_DOT_TRACES];
    private int traceLength = 1;
    private float[] colorAtom = new float[3];


    public Atom(float x, float y, float c0){
        super(x,y);
        alpha = 100+(int) Math.random()*100;
        atomsCount = 3+(int)(7* Math.random());
        rotationBase = 0+ (float) Math.random()*rndModifer4 - (float) Math.random()*rndModifer4;
        //alpha =250;
        xNew = x;
        yNew = y;
        colorAtom [0] = color[0] = c0;
        colorAtom [1] = color[1] = 1;
        colorAtom [2] = color[2] = 1;
        do {colorAtom [0] = colorAtom [0] + ((float) Math.random()*70-(float) Math.random()*70);}
        while (colorAtom [0] < 0 && colorAtom [0] > 360);
        paint.setColor(Color.HSVToColor(alpha, color));
        xR = (float) Math.random()*rndModifer - (float) Math.random()*rndModifer;
        yR = (float) Math.random()*rndModifer - (float) Math.random()*rndModifer;
        xR1 = (float) Math.random()*rndModifer2 - (float) Math.random()*rndModifer2;
        yR1 = (float) Math.random()*rndModifer2 - (float) Math.random()*rndModifer2;
        radius = CquarkWallpaperService.atomRadius+(float) Math.random()*rndModifer3 - (float) Math.random()*rndModifer3;



    }

    @Override
    public void draw(Canvas canvas){
        if (isActive){
            canvas.save();
            canvas.translate(xNew, yNew);
            paint.setStyle(Paint.Style.STROKE);
            paint.setAlpha(alpha);
            paint.setColor(Color.HSVToColor(alpha, color));
            canvas.drawCircle(0, 0, baseRadius+(float)Math.random(), paint);
            paint.setStyle(Paint.Style.FILL);
            rotation=rotation+rotationBase;
            canvas.rotate(rotation);
            //tempAlpha = alpha+50;
            //paint.setColor(Color.HSVToColor(alpha,colorAtom));
            for (int i = 0; i<atomsCount;i++){
                canvas.drawCircle(0,(float) Math.random()+baseRadius/3,(float) Math.random()+baseRadius/3,paint);
                canvas.drawCircle(0,(float)Math.random()+baseRadius/3,(float)Math.random()+baseRadius/6,paint);
                canvas.rotate((360/atomsCount));
            }
            canvas.restore();

        }
    }

    @Override
    public void nextState(){
        if (baseRadius<radius)
        baseRadius+=4;

        xNew = xNew + xR + xR1*yNew/100;
        yNew = yNew + yR+ yR1*xNew/100;

        alpha--;
        if (alpha<0) {
            isActive = false;
        }

    }
}
