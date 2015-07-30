package com.sphinx96.c_quark_live_wallpaper;

import android.graphics.Canvas;
//import com.sphinx96.c_quark_live_wallpaper.Atom;import com.sphinx96.c_quark_live_wallpaper.CquarkWallpaperService;

import java.util.ArrayList;

/**
 * Created by Sfynkz on 16/08/2014.
 */
public class ObjectGroup {
    ArrayList<DrawableObject> drawableObjects = new ArrayList<DrawableObject>();
    DrawableObject tempDO = null;
    private float x, y;
    private int random;
    private int lifetime;
    private float color;


    public ObjectGroup(float x, float y) {
        lifetime = 250;
        this.x = x;
        this.y = y;
        random = (int) (3 + Math.random() * 10);
        //random = 1;
        addPathDots();

    }

    public boolean isActive() {
        lifetime--;
        if (lifetime > 0) return true;
        else return false;
    }

    private void addPathDots() {
        do {CquarkWallpaperService.color = CquarkWallpaperService.color+ ((float)Math.random()*70-(float)Math.random()*70);}
        while (CquarkWallpaperService.color < 0 || CquarkWallpaperService.color > 360);
        color = CquarkWallpaperService.color;
        for (int i = 0; i < random; i++) {
            PathDot pd = new PathDot(x, y,color );
            drawableObjects.add(pd);
            do {color = color + ((float)Math.random()*70-(float)Math.random()*70);}
            while (color < 0 || color > 360);
            refreshObjectsQuantity();
        }
        random = (int) (2 + Math.random() * 5);
        for (int i = 0; i < random; i++) {
            Atom atom = new Atom(x, y,color );
            drawableObjects.add(atom);
            do {color = color + ((float)Math.random()*30-(float)Math.random()*30);}
            while (color < 0 || color > 360);
            refreshObjectsQuantity();
        }
        //do {color = color + ((float)Math.random()*70-(float)Math.random()*70);}
        //while (color < 0 && color > 360);
        SplashCircle splashCircle = new SplashCircle(x, y,color );
        drawableObjects.add(splashCircle);
        refreshObjectsQuantity();
    }

    public void drawObjects(Canvas canvas) {
        for (int i = 0; i < drawableObjects.size(); i++) {
            tempDO = drawableObjects.get(i);
            if (tempDO.getActiveState()) {
                tempDO.draw(canvas);
                tempDO.nextState();
            }

        }

    }
    private void refreshObjectsQuantity(){
        CquarkWallpaperService.objectsQuantity++;
        if (CquarkWallpaperService.objectsQuantity>CquarkWallpaperService.MAX_ARRAY_SIZE){
            CquarkWallpaperService.mustReplaceArray = true;
        }

    }



}
