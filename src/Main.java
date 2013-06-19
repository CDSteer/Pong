/**
 * @author cdsteer
 * Date: 17/06/2013
 * Time: 23:03


import static org.lwjgl.opengl.GL11.*;

import Entity.*;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;


public class Main {

    private static long lastFrame;

    private static long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }

    private static int getDelta() {
        long currentTime = getTime();
        int delta = (int) (currentTime - lastFrame);
        lastFrame = getTime();
        return delta;
    }

    private static class Box extends AbstractMovableEntity {

        public Box(double x, double y, double width, double height) {
            super(x, y, width, height);
        }

        @Override
        public void draw() {
            glRectd(x, y, x + width, y + height);
        }
    }

    private static class Point extends AbstractEntity {

        public Point(double x, double y) {
            super(x, y, 1, 1);
        }

        @Override
        public void draw() {
            glBegin(GL_POINTS);
                glVertex2d(x, y);
            glEnd();
        }

        @Override
        public void update(int delta) {
            // Do nothing
        }
    }

    public Main(){

        try {
            Display.setDisplayMode(new DisplayMode(640, 480));
            Display.setTitle("Hello World");
            Display.create();
        } catch (LWJGLException e) {
            Display.destroy();
            e.printStackTrace();
            System.exit(0);
        }

        // Initialization code Entities
        MoveableEntity box = new Box(100, 100, 50, 50);
        Entity point = new Point(10, 10);

        // Initialization code OpenGL
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, 640, 480, 0, 1, -1);
        glMatrixMode(GL_MODELVIEW);

        lastFrame = getTime();

        while (!Display.isCloseRequested()){
            point.setLocation(Mouse.getX(), 480 - Mouse.getY() - 1);

            glClear(GL_COLOR_BUFFER_BIT);

            int delta = getDelta();
            box.update(delta);
            point.update(delta);

            if (box.intersects(point)) {
                box.setDX(0.2);
            }

            point.draw();
            box.draw();

            Display.update();
            Display.sync(60);
        }

        Display.destroy();
    }

    public static void main(String args[]){
        new Main();
    }



}
 */
