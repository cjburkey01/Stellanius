package com.cjburkey.stellanius;

import com.cjburkey.engine.Debug;
import com.cjburkey.engine.Format;
import com.cjburkey.engine.Game;
import com.cjburkey.engine.GameEngine;
import com.cjburkey.engine.event.EventEngineExit;
import com.cjburkey.engine.event.EventEngineInit;
import com.cjburkey.engine.event.EventEnginePreExit;
import com.cjburkey.engine.event.EventEnginePreInit;
import com.cjburkey.engine.event.EventEngineRender;
import com.cjburkey.engine.event.EventEngineUpdate;

@Game(name = "Stellanius")
public final class Stellanius {
	
	public static void main(String[] args) {
		GameEngine.start();
	}
	
	protected void onPreinit(EventEnginePreInit e) {
		Debug.log("Preinitialization");
	}
	
	protected void onInit(EventEngineInit e) {
		Debug.log("Initialization");
	}
	
	protected void onPreexit(EventEnginePreExit e) {
		Debug.log("Preexit");
	}
	
	protected void onExit(EventEngineExit e) {
		Debug.log("Exit");
	}
	
	protected void onUpdate(EventEngineUpdate e) {
		Debug.log("Update delta: {}", Format.format1(GameEngine.getDeltaTime()));
	}
	
	protected void onRender(EventEngineRender e) {
		//Debug.log("Render");
	}
	
}