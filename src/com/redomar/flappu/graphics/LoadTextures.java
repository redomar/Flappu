package com.redomar.flappu.graphics;

import static org.lwjgl.opengl.GL11.*;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.redomar.flappu.util.LoadBuffers;

public class LoadTextures {

	private int width, height;
	private int texture;
	
	public LoadTextures(String path){
		texture = load(path);
	}
	
	/**
	 * Loads Textures from a file and then obtains colour data.
	 * Returns texture data.
	 */
	private int load(String path){
		int[] pixels = null;
		try {
			BufferedImage image = ImageIO.read(new FileInputStream(path)); // Loads the images in a buffer
			width = image.getWidth();
			height = image.getHeight();
			pixels = new int[width * height];
			image.getRGB(0, 0, width, height, pixels, 0, width); //Obtains the colours of each pixels as ARGB
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int[] data = convertARGBtoRGBA(width, height, pixels);
		
		int gentex = glGenTextures(); // Sets a new texture
		glBindTexture(GL_TEXTURE_2D, gentex); // Selects texture to be modified
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST); // Disables anti-aliasing
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, LoadBuffers.createIntBuffer(data));
		glBindTexture(GL_TEXTURE_2D, 0); // De-selects the texture, texture cannot be modified.
		return gentex;
	}
	
	/**
	 * converts the standard ARGB format to a RGBA format which OpenGL requires.
	 * @return Int[] array
	 */
	private int[] convertARGBtoRGBA(int width, int height, int[] pixels){
		int[] data = new int[width * height];
		for (int i = 0; i < width; i++){
			int a = (pixels[i] & 0xff000000) >> 24;
			int r = (pixels[i] & 0xff0000) >> 16;
			int g = (pixels[i] & 0xff00) >> 8;
			int b = (pixels[i] & 0xff);
			
			data[i] = a << 24 | b << 16 | g << 8 | r;
		}
		return data;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getTexture() {
		return texture;
	}

	public void setTexture(int texture) {
		this.texture = texture;
	}

}
