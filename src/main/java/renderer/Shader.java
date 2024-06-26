package renderer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;


public class Shader {
    private int shaderProgramID;

    private String vertexSource;
    private String fragmentSource;
    private String filepath;

    public Shader(String filepath){
        this.filepath = filepath;
        try{
            String source = new String(Files.readAllBytes(Paths.get(filepath)));
            String[] splitString = source.split("(#type)( )+([a-zA-Z]+)");

            int index = source.indexOf("#type") + 6;
            int eol = source.indexOf("\r\n",index);
            String firstPattern = source.substring(index,eol).trim();

            index = source.indexOf("#type",eol) + 6;
            eol = source.indexOf("\r\n",index);
            String secondPattern = source.substring(index,eol).trim();
            if(firstPattern.equals("vertex")) {
                vertexSource = splitString[1];
            }else if(firstPattern.equals("fragment")){
                fragmentSource = splitString[1];
            }else{
                throw new IOException("Bad");
            }


            if(secondPattern.equals("vertex")) {
                vertexSource = splitString[2];
            }else if(secondPattern.equals("fragment")){
                fragmentSource = splitString[2];
            }else{
                throw new IOException("Bad");
            }

        }catch (IOException e){
            e.printStackTrace();
            assert  false : "Error : Can't open file";
        }

    }

    public void compile(){
        int vertexID,fragmentID;
        //compile and link shaders
        vertexID = glCreateShader(GL_VERTEX_SHADER);

        glShaderSource(vertexID,vertexSource);
        glCompileShader(vertexID);

        int success = glGetShaderi(vertexID,GL_COMPILE_STATUS);
        if(success == GL_FALSE){
            int len = glGetShaderi(vertexID,GL_INFO_LOG_LENGTH);
            System.out.println("Error 1");
            System.out.println(glGetShaderInfoLog(vertexID,len));
            assert false : "";
        }
        fragmentID = glCreateShader(GL_FRAGMENT_SHADER);

        glShaderSource(fragmentID,fragmentSource);
        glCompileShader(fragmentID);

        success = glGetShaderi(fragmentID,GL_COMPILE_STATUS);
        if(success == GL_FALSE){
            int len = glGetShaderi(fragmentID,GL_INFO_LOG_LENGTH);
            System.out.println("Error 2");
            System.out.println(glGetShaderInfoLog(fragmentID,len));
            assert false : "";
        }

        //link
        shaderProgramID = glCreateProgram();
        glAttachShader(shaderProgramID,vertexID);
        glAttachShader(shaderProgramID,fragmentID);
        glLinkProgram(shaderProgramID);

        success = glGetProgrami(shaderProgramID, GL_LINK_STATUS);
        if(success == GL_FALSE){
            int len = glGetProgrami(shaderProgramID,GL_INFO_LOG_LENGTH);
            System.out.println("Error 3");
            System.out.println(glGetProgramInfoLog(shaderProgramID,len));
            assert false : "";
        }
    }

    public void use(){
        //bind shader program
        glUseProgram(shaderProgramID);

    }

    public void detach(){
        glUseProgram(0);
    }

    public int getShaderProgramID() {
        return shaderProgramID;
    }
}
