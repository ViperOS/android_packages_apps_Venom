package com.viper.venom.about;

import android.graphics.Path;

import com.viper.venom.about.Squint.Direction;
import com.viper.venom.about.Squint.Gravity;

import static com.viper.venom.about.Squint.Gravity.BOTTOM;
import static com.viper.venom.about.Squint.Gravity.LEFT;
import static com.viper.venom.about.Squint.Gravity.RIGHT;
import static com.viper.venom.about.Squint.Gravity.TOP;

public class PathHelper {

    public static Path getPathFor(float width, float height, double angle, Direction direction, Gravity gravity) {
        switch (direction) {
            case LEFT_TO_RIGHT:
                return leftToRight(width, height, angle, gravity);
            case RIGHT_TO_LEFT:
                return rightToLeft(width, height, angle, gravity);
            case TOP_TO_BOTTOM:
                return topToBottom(width, height, angle, gravity);
            case BOTTOM_TO_TOP:
                return bottomToTop(width, height, angle, gravity);
        }
        return null;
    }


    private static Path leftToRight(float width, float height, double angle, Gravity gravity) {
        if (gravity == LEFT || gravity == RIGHT)
            gravity = BOTTOM;
        Path path = new Path();
        float attitude = (float) Math.abs(Math.tan(Math.toRadians(angle)) * width);
        if (gravity == BOTTOM) {
            path.moveTo(0, 0);
            path.lineTo(width, 0);
            path.lineTo(width, height - attitude);
            path.lineTo(0, height);
            path.close();
        } else {
            path.moveTo(0, 0);
            path.lineTo(width, attitude);
            path.lineTo(width, height);
            path.lineTo(0, height);
            path.close();
        }
        return path;
    }

    private static Path rightToLeft(float width, float height, double angle, Gravity gravity) {
        if (gravity == LEFT || gravity == RIGHT)
            gravity = BOTTOM;
        Path path = new Path();
        float attitude = (float) Math.abs(Math.tan(Math.toRadians(angle)) * width);
        if (gravity == BOTTOM) {
            path.moveTo(0, 0);
            path.lineTo(width, 0);
            path.lineTo(width, height);
            path.lineTo(0, height - attitude);
            path.close();
        } else {
            path.moveTo(0, attitude);
            path.lineTo(width, 0);
            path.lineTo(width, height);
            path.lineTo(0, height);
            path.close();
        }
        return path;
    }

    private static Path topToBottom(float width, float height, double angle, Gravity gravity) {
        if (gravity == TOP || gravity == BOTTOM)
            gravity = LEFT;
        float attitude = (float) Math.abs(Math.tan(Math.toRadians(angle)) * height);
        Path path = new Path();
        if (gravity == LEFT) {
            path.moveTo(0, 0);
            path.lineTo(0, height);
            path.lineTo(width - attitude, height);
            path.lineTo(width, 0);
            path.close();
        } else {
            path.moveTo(0, 0);
            path.lineTo(attitude, height);
            path.lineTo(width, height);
            path.lineTo(width, 0);
            path.close();
        }
        return path;
    }

    private static Path bottomToTop(float width, float height, double angle, Gravity gravity) {
        if (gravity == TOP || gravity == BOTTOM)
            gravity = LEFT;

        float attitude = (float) Math.abs(Math.tan(Math.toRadians(angle)) * height);
        Path path = new Path();
        if (gravity == LEFT) {
            path.moveTo(0, 0);
            path.lineTo(0, height);
            path.lineTo(width, height);
            path.lineTo(width - attitude, 0);
            path.close();
        } else {
            path.moveTo(attitude, 0);
            path.lineTo(width, 0);
            path.lineTo(width, height);
            path.lineTo(0, height);
            path.close();
        }
        return path;
    }
}
