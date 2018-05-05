package com.cjburkey.engine.resource;

public abstract class ResourceItem {
	
	protected final String resourceName;
	
	protected ResourceItem(String resourceName) {
		this.resourceName = resourceName;
	}
	
	public String getResourceName() {
		return resourceName;
	}
	
}