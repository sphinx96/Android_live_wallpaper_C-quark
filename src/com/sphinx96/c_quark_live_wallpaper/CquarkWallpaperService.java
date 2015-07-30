package com.sphinx96.c_quark_live_wallpaper;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Handler;
import android.service.wallpaper.WallpaperService;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import java.util.ArrayList;

public class CquarkWallpaperService extends WallpaperService {

    private final Handler mHandler = new Handler();
    //public DotsEngine dotsEngine;
    static int objectsQuantity = 0;
    static boolean mustReplaceArray = false;
    final static int MAX_DOT_TRACES = 20;
    final static int MAX_ARRAY_SIZE = 100;
    public static int DISPLAY_HEIGHT = 0;
    public static float atomRadius = 25 , splashCircleRadius = 5.5f;
    public static float color = 0;

    @Override
    public Engine onCreateEngine(){return new DotsEngine();}


    class DotsEngine extends Engine {
        private SurfaceHolder holder;
        public ArrayList<ObjectGroup> objectGroups = new ArrayList<ObjectGroup>();
        private ArrayList<ObjectGroup> tempObjectGrous = new ArrayList<ObjectGroup>();
        private Canvas canvas = null;
        private Paint paint;
        private boolean mVisible = true;
        private int objectGroup_size = 0;
        private ObjectGroup tempOG;

        DotsEngine(){
            //android.os.Debug.waitForDebugger();
            color = 360*(float)Math.random();
        }

        private final Runnable runnableDraw = new Runnable() {
            public void run() {
                drawCanvas();
            }
        };

        @Override
        public void onOffsetsChanged (float xOffset, float yOffset, float xOffsetStep, float yOffsetStep, int xPixelOffset, int yPixelOffset){
            super.onOffsetsChanged(xOffset, yOffset, xOffsetStep, yOffsetStep,
                    xPixelOffset, yPixelOffset);

        }

        @Override
        public void onTouchEvent(MotionEvent motionEvent){
            int event;
            float x, y;
            event = motionEvent.getAction();
            x = motionEvent.getX();
            y = motionEvent.getY();
            switch (event){
                case MotionEvent.ACTION_DOWN:{
                    ObjectGroup og = new ObjectGroup(x,y);
                    addCanvasObject(og);
                    drawCanvas();}
                default:
            }
            super.onTouchEvent(motionEvent);
        }

        public void addCanvasObject(ObjectGroup objectGroup){
            objectGroups.add(objectGroup);
        }


        private void drawCanvas (){
            final SurfaceHolder holder = getSurfaceHolder();
            try {
                canvas = holder.lockCanvas(null);
                if (mustReplaceArray){
                    replaceOgArray();
                }
                canvas.drawColor(Color.BLACK);
                draw(canvas);
            }
            catch (Exception e){}
            finally {
                if (canvas != null) {
                    holder.unlockCanvasAndPost(canvas);
                }
                mHandler.removeCallbacks(runnableDraw);
                if (mVisible) {
                    mHandler.postDelayed(runnableDraw, 1000 / 25);
                }


            }

        }

        private void replaceOgArray() {
            tempObjectGrous.clear();
            int arraySize = objectGroups.size();
            for (int i = arraySize-1;i>(int)(arraySize-arraySize/2);i--){
                tempObjectGrous.add(objectGroups.get(i));
            }
            objectGroups.clear();
            arraySize = tempObjectGrous.size();
            for(int j = 0;j<arraySize;j++){
                objectGroups.add(tempObjectGrous.get(j));
            }
            objectsQuantity = 0;
            mustReplaceArray = false;
        }

        private void draw(Canvas canvas){
            objectGroup_size = objectGroups.size();
            for (int i=0; i<objectGroup_size;i++){
                tempOG = objectGroups.get(i);
                if (tempOG.isActive()) {
                    tempOG.drawObjects(canvas);
                }
            }


        }

        @Override
        public void onSurfaceCreated(SurfaceHolder holder) {
            super.onSurfaceCreated(holder);
            try {
                DISPLAY_HEIGHT = getDesiredMinimumHeight();}catch (Exception e){}
            intitSizes();
        }
        private void intitSizes(){
            if (DISPLAY_HEIGHT > 0){
                atomRadius = ((float)DISPLAY_HEIGHT) / 38;
                splashCircleRadius = ((float)DISPLAY_HEIGHT) / 175;
            }
        }

        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            super.onSurfaceChanged(holder, format, width, height);
            drawCanvas();
        }
        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);
            mVisible=false;
            mHandler.removeCallbacks(runnableDraw);
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            mVisible = visible;
            if (visible) {
                drawCanvas();
            } else {
                mHandler.removeCallbacks(runnableDraw);
            }
        }

        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
            setTouchEventsEnabled(true);
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
        }
    }
}
