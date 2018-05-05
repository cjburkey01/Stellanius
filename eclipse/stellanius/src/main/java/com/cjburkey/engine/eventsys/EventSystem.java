package com.cjburkey.engine.eventsys;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.reflections.Reflections;
import com.cjburkey.engine.Debug;
import com.cjburkey.engine.Game;

public class EventSystem {
	
	private static final Map<Class<? extends Event>, Map<Object, Method>> eventHandlers = new HashMap<>();
	private static int count = 0;
	
	public static void locateEventHandlers() {
		count = 0;
		Debug.log("Locating event handlers...");
		Reflections reflections = new Reflections();
		Set<Class<?>> eventHandlers = reflections.getTypesAnnotatedWith(EventHandler.class);
		eventHandlers.addAll(reflections.getTypesAnnotatedWith(Game.class));
		for (Class<?> eventHandler : eventHandlers) {
			addEventHandler(eventHandler);
		}
		Debug.log("Located {} event handlers with {} handling methods total", eventHandlers.size(), count);
	}
	
	public static void TriggerEvent(Event event) {
		Map<Object, Method> map = eventHandlers.get(event.getClass());
		if (map == null) {
			Debug.warn("No event handlers for event: \"{}\"", event.getName());
			return;
		}
		for (Entry<Object, Method> entry : map.entrySet()) {
			try {
				entry.getValue().invoke(entry.getKey(), event);
			} catch (Exception e) {
				Debug.error("Failed to invoke handler method: \"{}\" in handler \"{}\"; {}", entry.getValue().getName(), entry.getKey().getClass().getName());
				e.printStackTrace();
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private static void addEventHandler(Class<?> eventHandler) {
		Method[] methods = eventHandler.getDeclaredMethods();
		for (Method method : methods) {
			method.setAccessible(true);
			Class<?>[] parameters = method.getParameterTypes();
			if (parameters.length == 1 && Event.class.isAssignableFrom(parameters[0])) {
				addEventHandler((Class<? extends Event>) parameters[0], method);
			}
		}
	}
	
	private static void addEventHandler(Class<? extends Event> event, Method method) {
		Object instantiated = null;
		try {
			instantiated = method.getDeclaringClass().newInstance();
			if (instantiated == null) {
				throw new Exception();
			}
		} catch (Exception e) {
			Debug.error("Failed to create an instance of class: \"{}\"", method.getDeclaringClass().getName());
			return;
		}
		if (eventHandlers.containsKey(event)) {
			eventHandlers.get(event).put(instantiated, method);
			return;
		}
		Map<Object, Method> list = new HashMap<>();
		list.put(instantiated, method);
		count ++;
		eventHandlers.put(event, list);
	}
	
}