package com.sphinx96.c_quark_live_wallpaper;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by Sfynkz on 17/08/2014.
 */
abstract public class DrawableObject {
    protected  float x,y;
    protected Paint paint;
    protected float[] color = new float[3];
    protected boolean isActive = true;



    public DrawableObject(float x, float y){
        this.x=x;
        this.y=y;
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(1);

    }

    abstract public void draw(Canvas canvas);
    abstract public void nextState();
    public boolean getActiveState(){
        return isActive;
    }
}
