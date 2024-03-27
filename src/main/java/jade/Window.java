package jade;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11C;


import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11C.GL_TRIANGLES;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
    private int width, height;
    private String title;
    private  static Window window = null;
    private long glfwWindow;
    LevelEditorScene level = new LevelEditorScene();
    private Window(){
        this.width = 640;
        this.height = 480;
        this.title = "Graphics";
    }

    public static Window get(){
        if(Window.window == null){
            Window.window = new Window();
        }
        return Window.window;
    }

    public void run(){
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");
        init();
        loop();
    }

    public void init(){
        GLFWErrorCallback.createPrint(System.err).set();
        //init GLFW
        if(!glfwInit()){
            throw new IllegalStateException("Unable to initialize GLFW.");
        }
        //Configure GLFW
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE,GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);


        //Create the window
        glfwWindow = glfwCreateWindow(this.width,this.height,this.title,NULL, NULL);
        if(glfwWindow == NULL){
            throw new IllegalStateException("Failed to create the GLFW window.");
        }
        //Make the OpenGL context current
        glfwMakeContextCurrent(glfwWindow);
        //Enable v-sync
        glfwSwapInterval(1);

        //Make the window visible
        glfwShowWindow(glfwWindow);
        GL.createCapabilities();

       level.init();

    }
    public void loop(){
        while(!glfwWindowShouldClose(glfwWindow)){
            //Poll event
            glfwPollEvents();
            /*glBegin(GL_TRIANGLES);
            glVertex2f(0.0f,1.0f);
            glVertex2f(1.0f,-1.0f);
            glVertex2f(-1.0f,-1.0f);
            glEnd();*/
            //glColor3b((byte) 1,(byte)1,(byte)1);


            glClearColor(1.0f,0.6f,0.3f,1.0f);
            glClear(GL_COLOR_BUFFER_BIT);

            level.update();
            glfwSwapBuffers(glfwWindow);

        }
    }
}
