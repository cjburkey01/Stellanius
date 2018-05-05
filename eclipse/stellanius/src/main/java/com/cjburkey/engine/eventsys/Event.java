package com.cjburkey.engine.eventsys;

public abstract class Event {
	
	protected boolean cancellable;
	protected boolean cancelled;
	
	public boolean getIsCancellable() {
		return cancellable;
	}
	
	public boolean getIsCancelled() {
		return getIsCancellable() && cancelled;
	}
	
	public void cancel() {
		cancelled = getIsCancellable();
	}
	
	public String getName() {
		return getClass().getSimpleName();
	}
	
}