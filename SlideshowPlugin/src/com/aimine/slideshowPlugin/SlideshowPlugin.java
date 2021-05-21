package com.aimine.slideshowPlugin;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import com.tek.idisplays.Reference;
import com.tek.idisplays.Selection;
import com.tek.idisplays.async.MapCreationManager;
import com.tek.idisplays.map.MapManager;
import com.tek.idisplays.tasks.SelectionHighlightTask;
import com.tek.idisplays.tasks.WandCheckingTask;

public class SlideshowPlugin extends JavaPlugin {
	
	private static SlideshowPlugin instance;
	private Map<UUID, Selection> selections;
	private List<UUID> deletions;
	private MapManager mapManager;
	private MapCreationManager mapCreationManager;
	public static ConsoleCommandSender Console;
	
	@Override
	public void onEnable() {
		
		Console = Bukkit.getServer().getConsoleSender();
		
    	this.getCommand("slideshow").setExecutor(new CommandHello());
    	getServer().getPluginManager().registerEvents(new InteractListener(), this);
		
		instance = this;
		selections = new HashMap<UUID, Selection>();
		deletions = new ArrayList<UUID>();
		mapManager = new MapManager();
		mapCreationManager = new MapCreationManager();
		
		Reference.init();
		Ref.init();
		mapManager.getMapCache().addAll(mapManager.readCache());
		
		
		getServer().getScheduler().scheduleSyncRepeatingTask(this, new WandCheckingTask(), 0, 20);
		getServer().getScheduler().scheduleSyncRepeatingTask(this, new SelectionHighlightTask(), 0, 5);
		getServer().getScheduler().scheduleSyncRepeatingTask(SlideshowPlugin.getInstance(), mapCreationManager, 0, 4);
	}
	
	@Override
	public void onDisable() {
		mapManager.saveCache();
	}
	
	public static SlideshowPlugin getInstance() {
		return instance;
	}
	
	public Map<UUID, Selection> getSelections() {
		return selections;
	}
	
	public List<UUID> getDeletions() {
		return deletions;
	}
	
	public MapManager getMapManager() {
		return mapManager;
	}
	
	public MapCreationManager getMapCreationManager() {
		return mapCreationManager;
	}
}