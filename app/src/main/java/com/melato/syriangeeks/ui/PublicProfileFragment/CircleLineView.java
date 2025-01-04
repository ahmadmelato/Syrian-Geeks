package com.melato.syriangeeks.ui.PublicProfileFragment;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;

public class CircleLineView extends View {

    private Paint circlePaint;
    private Paint linePaint;

    public CircleLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        // Paint for the circles
        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setColor(0xFFB0B0B0); // Light gray color
        circlePaint.setStyle(Paint.Style.FILL);

        // Paint for the line
        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setColor(0xFFB0B0B0); // Light gray color
        linePaint.setStrokeWidth(2f);
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);

        // Get width and height of the view
        int width = getWidth();
        int height = getHeight();

        // Calculate positions
        float circleRadius = 8f; // Radius of the circles
        float centerX = width / 2f;

        // Draw top circle
        canvas.drawCircle(centerX, circleRadius, circleRadius, circlePaint);

        // Draw line
        float lineStartY = 2 * circleRadius;
        float lineEndY = height - 2 * circleRadius;
        canvas.drawLine(centerX, lineStartY, centerX, lineEndY, linePaint);

        // Draw bottom circle
        canvas.drawCircle(centerX, height - circleRadius, circleRadius, circlePaint);
    }
}
