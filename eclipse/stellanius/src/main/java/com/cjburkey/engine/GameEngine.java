package com.cjburkey.engine;

import java.util.Set;
import org.reflections.Reflections;
import com.cjburkey.engine.event.EventEngineExit;
import com.cjburkey.engine.event.EventEngineInit;
import com.cjburkey.engine.event.EventEnginePreExit;
import com.cjburkey.engine.event.EventEnginePreInit;
import com.cjburkey.engine.event.EventEngineRender;
import com.cjburkey.engine.event.EventEngineUpdate;
import com.cjburkey.engine.eventsys.EventSystem;
import com.cjburkey.engine.graphic.Window;

public final class GameEngine {
	
	private static GameEngine gameEngine;
	
	private Object gameInstance;
	private boolean running;
	private double deltaTime;
	
	public final Window window = new Window();
	
	public static void start() {
		gameEngine = new GameEngine();
		gameEngine.launch();
	}
	
	private void launch() {
		Debug.log("Locating game");
		Reflections reflections = new Reflections();
		Set<Class<?>> set = reflections.getTypesAnnotatedWith(Game.class);
		if (set.size() != 1) {
			Debug.error("Failed to locate game class, found {} classes", set.size());
			return;
		}
		Class<?> game = set.toArray(new Class<?>[1])[0];
		Game annotGame = AnnotUtil.getAnnotationOnClass(game, Game.class);
		if (annotGame == null) {
			Debug.error("Failed to locate game annotation in game class: \"{}\"", game.getName());
			return;
		}
		Debug.log("Located game: \"{}\" in class \"{}\"", annotGame.name(), game.getName());
		EventSystem.locateEventHandlers();
		begin();
	}
	
	private void begin() {
		running = true;
		EventSystem.TriggerEvent(new EventEnginePreInit());
		window.create("Stellanius", true);
		window.show();
		boolean first = true;
		long last = System.nanoTime();
		long start;
		while (running) {
			if (first) {
				EventSystem.TriggerEvent(new EventEngineInit());
				first = false;
			}
			start = System.nanoTime();
			deltaTime = (start - last) / 1000000000.0d;
			last = start;
			window.preRender();
			EventSystem.TriggerEvent(new EventEngineUpdate());
			EventSystem.TriggerEvent(new EventEngineRender());
			window.postRender();
			if (window.getShouldClose()) {
				stop();
			}
		}
		EventSystem.TriggerEvent(new EventEngineExit());
		window.destroy();
	}
	
	private void stop() {
		EventSystem.TriggerEvent(new EventEnginePreExit());
		running = false;
	}
	
	public static GameEngine getGameEngine() {
		return gameEngine;
	}
	
	public static Object getGameInstance() {
		return gameEngine.gameInstance;
	}
	
	public static double getDeltaTime() {
		return gameEngine.deltaTime;
	}
	
	public static float getDeltaTimeF() {
		return (float) gameEngine.deltaTime;
	}
	
}