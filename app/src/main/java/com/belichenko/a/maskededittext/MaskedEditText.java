package com.belichenko.a.maskededittext;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextPaint;
import android.util.AttributeSet;

/**
 * Created by Belichenko Anton on 25.01.17.
 * mailto: a.belichenko@gmail.com
 */

public class MaskedEditText extends AppCompatEditText {

    private int currentLength;
    private int defaultLength = 4;
    private String defaultSymbol = "0";
    private Paint mPaint = new Paint();
    private int mColor;
    private int mDrawable;
    private Bitmap mBitmap;

    public MaskedEditText(Context context) {
        super(context);
        init();
    }

    public MaskedEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        currentLength = attrs.getAttributeIntValue("http://schemas.android.com/apk/res/android", "maxLength", defaultLength);
        init();
    }

    public MaskedEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        currentLength = attrs.getAttributeIntValue("http://schemas.android.com/apk/res/android", "maxLength", defaultLength);
        init();
    }

    public
    @ColorInt
    int getPointColor() {
        return mColor;
    }

    public void setPointColor(@ColorRes int color) {
        mColor = color;
        int myColor = getContext().getResources().getColor(mColor);
        mPaint.setColor(myColor);
    }

    public int getPointDrawable() {
        return mDrawable;
    }

    public void setPointDrawable(@DrawableRes int drawable) {
        if (drawable == 0) return;
        mDrawable = drawable;
        BitmapFactory.Options option = new BitmapFactory.Options();
        option.inScaled = false;
        mBitmap = BitmapFactory.decodeResource(getResources(), mDrawable, option);
//        if (this.getMeasuredHeight() < mBitmap.getHeight()){
//            Log.d("Tag", "Drawable height is over more = " + String.valueOf(mBitmap.getHeight()));
//            mBitmap = null;
//            mDrawable = 0;
//        }
    }

    private void init() {
        setMaxLines(1);
        setSingleLine(true);
        setMinEms(currentLength);
        setMaxEms(currentLength);
        setEms(currentLength);
        int myColor = getContext().getResources().getColor(R.color.colorAccent);
        mPaint.setColor(myColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //Layout layout = getLayout();
        TextPaint textPaint = getPaint();
        float pointSize = textPaint.getTextSize() / 6.0f;
        float symbolsLengths;
        float leftPadding = (float) getPaddingLeft();
        if (length() > 0) {
            symbolsLengths = textPaint.measureText(length() > 0 ? getText().toString() : defaultSymbol, 0, length() > 0 ? length() : 1);
        } else {
            symbolsLengths = 0.0f;
        }
        float oneSymbolLength = textPaint.measureText(defaultSymbol);
        int centerY = getHeight() / 2;
        for (int i = length(); i < currentLength; i++) {
            if (mBitmap != null) {
                canvas.drawBitmap(mBitmap, leftPadding + symbolsLengths + oneSymbolLength * (float) (i - length()) + oneSymbolLength / 1.5f, centerY, mPaint);
            } else {
                canvas.drawCircle(leftPadding + symbolsLengths + oneSymbolLength * (float) (i - length()) + oneSymbolLength / 1.5f, centerY, pointSize, mPaint);
            }
        }
    }

}
