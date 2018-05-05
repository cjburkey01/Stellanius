package com.cjburkey.engine.resource;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Registry<T extends ResourceItem> {
	
	private final Map<String, T> registry = new HashMap<>();
	
	public boolean register(T item) {
		if (has(item)) {
			return false;
		}
		registry.put(item.getResourceName(), item);
		return true;
	}
	
	public boolean has(String resourceName) {
		return registry.containsKey(resourceName);
	}
	
	public boolean has(T resource) {
		return has(resource.getResourceName());
	}
	
	public T get(String resourceName) {
		return registry.get(resourceName);
	}
	
	public Collection<T> getAll() {
		return registry.values();
	}
	
}