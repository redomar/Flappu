package com.redomar.flappu.util;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL11.GL_FALSE;

public class LoadShaders {

	private LoadShaders(){
		
	}

	public static int load(String vertPath, String fragPath){
		String vertex = LoadFiles.loadAsString(vertPath);
		String fragment = LoadFiles.loadAsString(fragPath);
		return create(vertex, fragment);
	}

	public static int create(String vertex, String fragment){
		int program = glCreateProgram();
		int vertID = glCreateShader(GL_VERTEX_SHADER);
		int fragID = glCreateShader(GL_FRAGMENT_SHADER);

		glShaderSource(vertID, vertex);
		glShaderSource(fragID, fragment);
		glCompileShader(vertID);
		if (glGetShaderi(vertID, GL_COMPILE_STATUS) == GL_FALSE){
			System.err.println("Failed to compile vertex shader!");
			System.err.println(glGetShaderInfoLog(vertID, 2048));
		}
		glCompileShader(fragID);
		if (glGetShaderi(fragID, GL_COMPILE_STATUS) == GL_FALSE){
			System.err.println("Failed to compile fragment shader!");
			System.err.println(glGetShaderInfoLog(fragID, 2048));
		}
		glAttachShader(program, vertID);
		glAttachShader(program, fragID);

		glLinkProgram(program);
		return program;
	}

}
