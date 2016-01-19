package com.customview.app.views;

/**
 * @author Mark Alvarez
 */

import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * The class is the responsible of adding the animation
 * feature to the graph drawing process
 */
public class CircleAnimation extends Animation {
    private CircleGraph pieGraph;
    private float newAngle;
    private int circleNum;

    public CircleAnimation(CircleGraph pieGraph, float newAngle, int circleNum) {
        this.newAngle = newAngle;
        this.pieGraph = pieGraph;
        this.circleNum = circleNum;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation transformation) {
        float angle = newAngle * interpolatedTime;
        if (circleNum == 1) {
            pieGraph.setCircleEndAngle1(angle);
        } else if (circleNum == 2) {
            pieGraph.setCircleEndAngle2(angle);
        } else if (circleNum == 3) {
            pieGraph.setCircleEndAngle3(angle);
        }
        pieGraph.invalidate();
    }
}

