package com.stone.mylibrary.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.stone.mylibrary.R;

/**
 * Created by stone on 17-5-2.
 */

public class GroupIcon extends View {

    private static final String TAG = GroupIcon.class.getSimpleName();

    private static final int PIC_GAP = 2;
    private static final int BORDER_GAP = 2;
    private static final int WECHAT_TYPE = 1;
    private static final int QQ_TYPE = 2;

    private Bitmap[] mBitmaps = null;
    private Position[] mPositions = null;
    private int mRadius = 0;
    private int mSideLength = 0;
    private int mPicGap = PIC_GAP;
    private int mBorderGap = BORDER_GAP;
    private Paint mPaint = null;
    private int mType = WECHAT_TYPE;
    private BitmapShader mBitmapShader = null;

    public GroupIcon(Context context) {
        this(context, null);
    }

    public GroupIcon(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GroupIcon(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = getResources().obtainAttributes(attrs, R.styleable.GroupIcon);
        mType = typedArray.getInt(R.styleable.GroupIcon_type, WECHAT_TYPE);
        typedArray.recycle();
        Log.v(TAG, "type:" + mType);
        init();
    }

    private void init(){
        this.setBackgroundColor(Color.CYAN);
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

        if (mType == WECHAT_TYPE){
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
        else if(mType == QQ_TYPE){
            for (int i = 0; i < mPositions.length; i++){
                mBitmapShader = new BitmapShader(mBitmaps[i], Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
                mPaint.setShader(mBitmapShader);
                canvas.drawCircle(mPositions[i].getX(), mPositions[i].getY(), mRadius, mPaint);
            }
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

        if (mType == WECHAT_TYPE){
            calculatePositionWeChatType();
        }
        else if(mType == QQ_TYPE){
            calculatePositionQQType();
        }
    }

    private void calculatePositionWeChatType(){
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

    private void calculatePositionQQType(){
        int sideLength = Math.min(getWidth(), getHeight());

        if (mBitmaps.length >= 5){

        }
        else if(mBitmaps.length >= 3){
            mRadius = sideLength / 4;
            mPositions = new Position[3];
            mPositions[0] = new Position();
            mPositions[0].setX(mRadius);
            mPositions[0].setY((int)(mRadius + mRadius / 1.732f * 3 + (sideLength - (2+1.732f) * mRadius) / 2));
            mPositions[1] = new Position();
            mPositions[1].setX(sideLength / 2);
            mPositions[1].setY((int)(mRadius + (sideLength - (2+1.732f) * mRadius) / 2));
            mPositions[2] = new Position();
            mPositions[2].setX(sideLength - mRadius);
            mPositions[2].setY((int)(mRadius + mRadius / 1.732f * 3 + (sideLength - (2+1.732f) * mRadius) / 2));
        }
        else if(mBitmaps.length == 2){
            // with two pictures direction horizontal
            mRadius = (sideLength - mBorderGap * 2 - mPicGap) / 4;
            mPositions = new Position[2];
            for (int i = 0; i < mPositions.length; i++){
                mPositions[i] = new Position();
                mPositions[i].setX(mBorderGap + mRadius + (mRadius * 2 + mPicGap) * i);
                mPositions[i].setY(sideLength / 2);
            }
        }
        else if(mBitmaps.length == 1){
            mRadius = (sideLength - mBorderGap * 2) / 2;
            mPositions[0] = new Position();
            mPositions[0].setX(mBorderGap + mRadius);
            mPositions[0].setY(mBorderGap + mRadius);
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
