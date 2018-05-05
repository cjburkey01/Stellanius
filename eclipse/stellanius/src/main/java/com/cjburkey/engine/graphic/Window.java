package com.cjburkey.engine.graphic;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;
import org.joml.Vector2i;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

public class Window {
	
	private long window;
	private final Vector2i windowSize = new Vector2i(300, 300);
	private String title;
	
	public void create(String title, boolean vsync) {
		this.title = title;
		
		GLFWErrorCallback.createPrint(System.err).set();
		if (!glfwInit()) {
			throw new RuntimeException("Failed to initalize GLFW");
		}
		
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
		window = glfwCreateWindow(windowSize.x, windowSize.y, title, NULL, NULL);
		if (window == NULL) {
			throw new RuntimeException("Failed to create GLFW window");
		}
		glfwMakeContextCurrent(window);
		glfwSetFramebufferSizeCallback(window, (window, width, height) -> windowSize.set(width, height));
		glfwSwapInterval(vsync ? 1 : 0);
		sizeToHalfScreen();
		center();
		
		GL.createCapabilities();
		glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
	}
	
	public void preRender() {
		glfwPollEvents();
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}
	
	public void postRender() {
		glfwSwapBuffers(window);
	}
	
	public void show() {
		glfwShowWindow(window);
	}
	
	public void hide() {
		glfwHideWindow(window);
	}
	
	public void destroy() {
		Callbacks.glfwFreeCallbacks(window);
		glfwDestroyWindow(window);
		glfwTerminate();
	}
	
	public void setTitle(String title) {
		this.title = title;
		glfwSetWindowTitle(window, title);
	}
	
	public String getTitle() {
		return title;
	}
	
	public long getWindow() {
		return window;
	}
	
	public void sizeToHalfScreen() {
		Vector2i mon = getMonitorSize();
		setWindowSize(mon.x / 2, mon.y / 2);
	}
	
	public void center() {
		Vector2i win = getWindowSize();
		Vector2i mon = getMonitorSize();
		glfwSetWindowPos(window, (mon.x - win.x) / 2, (mon.y - win.y) / 2);
	}
	
	public Vector2i getWindowSize() {
		return new Vector2i(windowSize);
	}
	
	public static Vector2i getMonitorSize() {
		return getMonitorSize(glfwGetPrimaryMonitor());
	}
	
	public static Vector2i getMonitorSize(long monitor) {
		GLFWVidMode vid = glfwGetVideoMode(monitor);
		return new Vector2i(vid.width(), vid.height());
	}
	
	public void setWindowSize(Vector2i size) {
		setWindowSize(size.x, size.y);
	}
	
	public void setWindowSize(int w, int h) {
		glfwSetWindowSize(window, w, h);
	}
	
	public boolean getShouldClose() {
		return glfwWindowShouldClose(window);
	}
	
}