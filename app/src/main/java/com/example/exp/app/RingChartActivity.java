package com.example.exp.app;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;


public class RingChartActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RingChartView view = new RingChartView(this);
        view.setPadding(150,150,150,150);
        view.setPortions(3,2,1);
        setContentView(view);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.ring_chart, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public static class RingChartView extends View {

        int width, height;
        RectF mRectF;
        Paint rectPaint, arcPaint;

        static int colorLightGreen = 0xffb1d42b;
        static int colorDarkGreen = 0xffe7b103;
        static int colorDark = 0xff4b5a4a;
        static int[] colorList = new int[] {colorDarkGreen,colorLightGreen,colorDark};
        public RingChartView(Context context) {
            this(context, null, 0);
        }

        public RingChartView(Context context, AttributeSet attrs) {
            this(context, attrs, 0);
        }

        public RingChartView(Context context, AttributeSet attrs,
                int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            init();
        }

        private void init() {
            rectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            rectPaint.setColor(Color.BLACK);


            int arcWidth = getResources().getDimensionPixelSize(R.dimen.arc_width);
            arcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            arcPaint.setStrokeWidth(arcWidth);
            arcPaint.setStyle(Paint.Style.STROKE);
        }

        static class Item {
            float sweepAngle;
            float startAngle;
            int color;

            Item(float startAngle, float sweepAngle, int color) {
                this.sweepAngle = sweepAngle;
                this.startAngle = startAngle;
                this.color = color;
            }
        }

        List<Item> mItemList;

        public void setPortions(int ... portions) {
            int sum = 0;
            for(int i:portions) {
                sum += i;
            }

            if(mItemList == null)
                mItemList = new ArrayList<Item>();
            else
                mItemList.clear();


            float startAngle = 0f, sweepAngle;
            for (int i = 0; i < portions.length; i++) {
                sweepAngle = portions[i]/(float)sum*360;
                mItemList.add(new Item(startAngle,sweepAngle,colorList[i%3]));
                startAngle += sweepAngle;
            }

        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            // Try for a width based on our minimum
            int minw = getPaddingLeft() + getPaddingRight() + getSuggestedMinimumWidth();
            int w = resolveSizeAndState(minw, widthMeasureSpec, 1);

            // Whatever the width ends up being, ask for a height that would let the pie
            // get as big as it can
            int h = getPaddingBottom() + getPaddingTop()+w-getPaddingLeft()-getPaddingRight();
            setMeasuredDimension(w, h);
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            width = w;
            height = h;
            mRectF = new RectF(getPaddingLeft(),getPaddingTop(),w-getPaddingRight(),h-getPaddingBottom());
        }

        @Override
        protected void onDraw(Canvas canvas) {
            //canvas.drawRect(mRectF,rectPaint);
            canvas.save();
            canvas.rotate(-90, mRectF.centerX(), mRectF.centerY());
            for (int i = 0; i < mItemList.size(); i++) {
                arcPaint.setColor(mItemList.get(i).color);
                canvas.drawArc(mRectF,mItemList.get(i).startAngle,mItemList.get(i).sweepAngle,false,arcPaint);
            }
            canvas.restore();
        }
    }

}
