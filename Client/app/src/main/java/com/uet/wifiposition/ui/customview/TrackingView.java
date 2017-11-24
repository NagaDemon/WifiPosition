package com.uet.wifiposition.ui.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.uet.wifiposition.R;
import com.uet.wifiposition.remote.model.getposition.LocationModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ducnd on 11/24/17.
 */

public class TrackingView extends View {
    private Path pathRoot;
    private Paint pRoot;
    private int sizeCell;
    private Paint pCel;
    private Path pathCel;
    private Paint pTracking;
    private Path pathTracking;
    private Paint pPoint;
    private boolean isStart;
    private List<LocationModel> locationModels;

    public TrackingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TrackingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        locationModels = new ArrayList<>();
        pRoot = new Paint();
        pRoot.setAntiAlias(true);
        pRoot.setColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        pRoot.setStyle(Paint.Style.STROKE);
        pRoot.setStrokeWidth(8);

        pCel = new Paint();
        pCel.setAntiAlias(true);
        pCel.setColor(Color.BLACK);
        pCel.setStyle(Paint.Style.STROKE);
        pCel.setStrokeWidth(1.0f);

        pathTracking = new Path();

        pTracking = new Paint();
        pTracking.setAntiAlias(true);
        pTracking.setColor(Color.RED);
        pTracking.setStyle(Paint.Style.STROKE);
        pTracking.setStrokeWidth(8.0f);

        pPoint = new Paint();
        pPoint.setAntiAlias(true);
        pPoint.setColor(Color.GREEN);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (sizeCell == 0) {
            sizeCell = w / 19;
            pathRoot = new Path();
            pathRoot.moveTo(sizeCell * 8, sizeCell * 3);
            pathRoot.lineTo(sizeCell * 1, sizeCell * 3);
            pathRoot.lineTo(sizeCell * 1, sizeCell * 4);
            pathRoot.lineTo(sizeCell * -5, sizeCell * 4);
            pathRoot.lineTo(sizeCell * -5, sizeCell * 17);

            pathCel = new Path();
            int row = h / sizeCell;
            int col = w / sizeCell;
            for (int i = -10; i < row + 30; i++) {
                Path path = new Path();
                path.moveTo(-h, i * sizeCell);
                path.lineTo(h, i * sizeCell);

                pathCel.addPath(path);
            }

            for (int i = -10; i < col + 30; i++) {
                Path path = new Path();
                path.moveTo(i * sizeCell, -h);
                path.lineTo(i * sizeCell, h);

                pathCel.addPath(path);
            }


            invalidate();
        }
    }

    public void addPath(int x, int y) {
        if (!isStart) {
            isStart = true;
            pathTracking.moveTo(x * sizeCell, y * sizeCell);
        } else {
            pathTracking.lineTo(x * sizeCell, y * sizeCell);
        }
        LocationModel model = new LocationModel();
        model.setX(x);
        model.setY(y);
        locationModels.add(model);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.rotate(90, getWidth() / 2, getHeight() / 2);
        canvas.translate(200, 250);
        if (pathCel != null) {
            canvas.drawPath(pathCel, pCel);
        }
        if (pathRoot != null) {
            canvas.drawPath(pathRoot, pRoot);
        }

        if (locationModels.size() > 0) {
            canvas.drawPath(pathTracking, pTracking);
            for (LocationModel locationModel : locationModels) {
                canvas.drawCircle(
                        locationModel.getX() * sizeCell,
                        locationModel.getY() * sizeCell,
                        10,
                        pPoint
                );
            }
        }
        canvas.restore();
    }

    public boolean isPathEmpty() {
        return locationModels.size()==0;
    }
}
