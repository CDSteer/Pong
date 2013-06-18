/**
 * @author cdsteer
 * Date: 17/06/2013
 * Time: 23:03
 */

import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Main {
    private static final List<Box> shapes = new ArrayList<Box>(16);
    private static boolean somethingIsSelected = false;
    private static long lastColourChange;

    public static void main(String[] args){

        try {
            Display.setDisplayMode(new DisplayMode(640, 480));
            Display.setTitle("Hello World");
            Display.create();
        } catch (LWJGLException e) {
            Display.destroy();
            e.printStackTrace();
            System.exit(0);
        }

        shapes.add(new Box(15, 15));
        shapes.add(new Box(100, 150));

        glMatrixMode(GL_PROJECTION);
        glOrtho(0, 640, 480, 0, 1, -1);
        glMatrixMode(GL_MODELVIEW);


        while (!Display.isCloseRequested()){
            glClear(GL_COLOR_BUFFER_BIT);

            while (Keyboard.next()) {
                if (Keyboard.getEventKey() == Keyboard.KEY_C && Keyboard.getEventKeyState()) {
                    shapes.add(new Box(15, 15));
                }
            }

            if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
                Display.destroy();
                System.exit(0);
            }
            /*
            glBegin(GL_QUADS);
                glVertex2i(400, 400);
                glVertex2i(450, 400);
                glVertex2i(450, 450);
                glVertex2i(400, 450);
            glEnd();

            glBegin(GL_LINES);
                glVertex2i(100, 100);
                glVertex2i(200, 200);
            glEnd();
            */

            for (Box box : shapes){
                if (Mouse.isButtonDown(0) && box.isInBounds(Mouse.getX(), 480 - Mouse.getY()) && !somethingIsSelected) {
                    System.out.println("boo");
                    somethingIsSelected = true;
                    box.selected = true;
                }
                if (Mouse.isButtonDown(2) && box.isInBounds(Mouse.getX(), 480 - Mouse.getY()) && !somethingIsSelected) {
                    if ((System.currentTimeMillis() - lastColourChange) >= 200 /* milliseconds */) {
                        box.randomiseColors();
                        lastColourChange = System.currentTimeMillis();
                    }
                }
                if (Mouse.isButtonDown(1)) {
                    box.selected = false;
                    somethingIsSelected = false;
                }

                if (box.selected) {
                    box.update(Mouse.getDX(), -Mouse.getDY());
                }
                box.draw();
            }


            Display.update();
            Display.sync(60);
        }

        Display.destroy();
    }

    private static class Box {

        public int x, y;
        public boolean selected = false;
        private float colorRed, colorBlue, colorGreen;

        Box(int x, int y) {
            this.x = x;
            this.y = y;

            Random randomGenerator = new Random();
            colorRed = randomGenerator.nextFloat();
            colorBlue = randomGenerator.nextFloat();
            colorGreen = randomGenerator.nextFloat();
        }

        boolean isInBounds(int mouseX, int mouseY) {
            return mouseX > x && mouseX < x + 50 && mouseY > y && mouseY < y + 50;
        }

        void update(int dx, int dy) {
            x += dx;
            y += dy;
        }

        void randomiseColors() {
            Random randomGenerator = new Random();
            colorRed = randomGenerator.nextFloat();
            colorBlue = randomGenerator.nextFloat();
            colorGreen = randomGenerator.nextFloat();
        }

        void draw() {
            glColor3f(colorRed, colorGreen, colorBlue);
            glBegin(GL_QUADS);
            glVertex2f(x, y);
            glVertex2f(x + 50, y);
            glVertex2f(x + 50, y + 50);
            glVertex2f(x, y + 50);
            glEnd();
        }
    }


}
