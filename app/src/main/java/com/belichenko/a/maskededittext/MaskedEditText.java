package com.belichenko.a.maskededittext;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Created by Belichenko Anton on 25.01.17.
 * mailto: a.belichenko@gmail.com
 */

public class MaskedEditText extends AppCompatEditText {

    private static final int DEFAULT_LENGTH = 4;
    private static final String DEFAULT_SYMBOL = "0";

    private int mCurrentLength;
    private Paint mPaint = new Paint();
    private int mColor;
    private int mDrawable;
    private Bitmap mBitmap;

    public MaskedEditText(Context context) {
        super(context);
        mCurrentLength = DEFAULT_LENGTH;
        init();
    }

    public MaskedEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        //set the max length or DEFAULT_LENGTH if parameter maxLength not determined in xml
        mCurrentLength = attrs.getAttributeIntValue("http://schemas.android.com/apk/res/android", "maxLength", DEFAULT_LENGTH);
        init();
    }

    public MaskedEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mCurrentLength = attrs.getAttributeIntValue("http://schemas.android.com/apk/res/android", "maxLength", DEFAULT_LENGTH);
        init();
    }

    @ColorInt
    public int getPointColor() {
        return mColor;
    }

    public void setPointColor(@ColorRes int color) {
        mColor = color;
        int myColor = ContextCompat.getColor(getContext(), mColor);
        mPaint.setColor(myColor);
    }

    @DrawableRes
    public int getPointDrawable() {
        return mDrawable;
    }

    public void setPointDrawable(@DrawableRes int drawable) {
        if (drawable == 0) return;
        mDrawable = drawable;
        BitmapFactory.Options option = new BitmapFactory.Options();
        option.inScaled = false;
        mBitmap = BitmapFactory.decodeResource(getResources(), mDrawable, option);
        super.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
        int viewHeight = this.getMeasuredHeight();
        Log.d("Tag", "View height = " + String.valueOf(viewHeight));
        if (viewHeight > 0 && viewHeight < mBitmap.getHeight()){
            Log.d("Tag", "Drawable height is over more = " + String.valueOf(mBitmap.getHeight()));
            mBitmap = Bitmap.createScaledBitmap(mBitmap, viewHeight / 4, viewHeight / 4, false);
        }

    }

    private void init() {
        setMaxLines(1);
        setSingleLine(true);
        setMinEms(mCurrentLength);
        setMaxEms(mCurrentLength);
        setEms(mCurrentLength);
        int myColor = ContextCompat.getColor(getContext(), R.color.colorAccent);
        mPaint.setColor(myColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        TextPaint textPaint = getPaint();
        // calculating point size from current font size
        float pointSize = textPaint.getTextSize() / 6.0f;
        float symbolsLengths;
        float leftPadding = (float) getPaddingLeft();
        // getting the symbol length from real text or from default symbol if text is empty
        if (length() > 0) {
            symbolsLengths = textPaint.measureText(length() > 0 ? getText().toString() : DEFAULT_SYMBOL, 0, length() > 0 ? length() : 1);
        } else {
            symbolsLengths = 0.0f;
        }
        float oneSymbolLength = textPaint.measureText(DEFAULT_SYMBOL);
        int centerY = getHeight() / 2;
        // drawing points or bitmaps after the padding and text
        for (int i = length(); i < mCurrentLength; i++) {
            if (mBitmap != null) {
                int halfOfBitmapHeigth = mBitmap.getHeight() / 2;
                int halfOfBitmapWidth = mBitmap.getWidth() / 2;
                canvas.drawBitmap(mBitmap, leftPadding + symbolsLengths + oneSymbolLength * (float) (i - length()) + oneSymbolLength / 1.5f - halfOfBitmapWidth
                        , centerY - halfOfBitmapHeigth, mPaint);
            } else {
                canvas.drawCircle(leftPadding + symbolsLengths + oneSymbolLength * (float) (i - length()) + oneSymbolLength / 1.5f, centerY, pointSize, mPaint);
            }
        }
    }

}
