package com.stone.mylibrary.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by stone on 17-5-2.
 */

public class GroupIcon extends View {

    private static final int PIC_GAP = 2;

    private Bitmap[] mBitmaps = null;
    private Position[] mPositions = null;
    private int mRadius = 0;
    private int mSideLength = 0;
    private int mPicGap = PIC_GAP;
    private Paint mPaint = null;

    public GroupIcon(Context context) {
        this(context, null);
    }

    public GroupIcon(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GroupIcon(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        calculatePositions();
        drawBitmaps(canvas);
    }

    private void drawBitmaps(Canvas canvas){
        if (mBitmaps == null){
            return;
        }

        for (int i = 0; i < mPositions.length; i++){
            Log.v("GroupIcon", "count:" + mBitmaps.length + " x:" + mPositions[i].getX() + " y:" + mPositions[i].getY());

            float scaleWidth = ((float) mSideLength) / mBitmaps[i].getWidth();
            float scaleHeight = ((float) mSideLength) / mBitmaps[i].getHeight();
            // 取得想要缩放的matrix参数
            Matrix matrix = new Matrix();
            matrix.postScale(scaleWidth, scaleHeight);
            // 得到新的图片
            Bitmap bitmap = Bitmap.createBitmap(mBitmaps[i], 0, 0, mBitmaps[i].getWidth(), mBitmaps[i].getHeight(), matrix,
                    true);

            canvas.drawBitmap(bitmap, mPositions[i].getX(), mPositions[i].getY(), mPaint);

        }
    }

    public void setmBitmaps(Bitmap[] bitmaps){
        this.mBitmaps = bitmaps;
        // calculatePositions();
        invalidate();
    }

    private void calculatePositions(){
        if (mBitmaps == null || mBitmaps.length == 0){
            return;
        }

        int sideLength = Math.min(getWidth(), getHeight());

        if (mBitmaps.length > 4){
            mSideLength = (sideLength - mPicGap * 4) / 3;
            mPositions = new Position[mBitmaps.length > 9 ? 9 : mBitmaps.length];
            for (int i = 0; i < mPositions.length; i++){
                mPositions[i] = new Position();
                mPositions[i].setX(mPicGap + i % 3 * (mSideLength + mPicGap));
                mPositions[i].setY(mPicGap + i / 3 * (mSideLength + mPicGap));
            }
        }
        else if(mBitmaps.length > 1){
            mSideLength = (sideLength - mPicGap * 3) / 2;
            mPositions = new Position[mBitmaps.length];
            for (int i = 0; i < mPositions.length; i++){
                mPositions[i] = new Position();
                mPositions[i].setX(mPicGap + i % 2 * (mSideLength + mPicGap));
                mPositions[i].setY(mPicGap + i / 2 * (mSideLength + mPicGap));
            }
        }
        else if(mBitmaps.length == 1){
            mSideLength = sideLength - mPicGap * 2;
            mPositions = new Position[mBitmaps.length];
            mPositions[0] = new Position();
            mPositions[0].setX(mPicGap);
            mPositions[0].setY(mPicGap);
        }
    }

    class Position {
        private int x = 0;
        private int y = 0;

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }
    }
}
