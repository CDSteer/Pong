/**
 * @author cdsteer
 * Date: 17/06/2013
 * Time: 23:03
 */
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.opengl.*;
import org.lwjgl.*;

public class Main {
    //Constants
    private static final int FRAME_WIDTH = 400;
    private static final int FRAME_HEIGHT = FRAME_WIDTH / 16 * 9;
    private static final int SCALE = 3;

    public Main(){
        try {
            Display.setDisplayMode(new DisplayMode(FRAME_WIDTH * SCALE, FRAME_HEIGHT * SCALE));
            Display.setTitle("Space Game");
            Display.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
            Display.destroy();
            System.exit(1);
        }

        while (!Display.isCloseRequested()) {
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
            Display.update();
        }

        Display.destroy();
        Display.sync(60);
        System.exit(0);
    }

    public static void main(String[] args){
        new Main();
    }
}
