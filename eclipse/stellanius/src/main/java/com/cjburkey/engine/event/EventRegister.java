package com.cjburkey.engine.event;

import com.cjburkey.engine.resource.Registry;
import com.cjburkey.engine.resource.ResourceItem;

public abstract class EventRegister<T extends ResourceItem> {
	
	public final Registry<T> registry;
	
	public EventRegister(Registry<T> registry) {
		this.registry = registry;
	}
	
}