package com.meeku.tutorialPlugin;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.plugin.java.JavaPlugin;

import com.tek.idisplays.ImageDisplayCommand;
import com.tek.idisplays.Reference;
import com.tek.idisplays.Selection;
import com.tek.idisplays.async.MapCreationManager;
import com.tek.idisplays.listeners.ItemFrameListener;
import com.tek.idisplays.listeners.MovementListener;
import com.tek.idisplays.listeners.WorldListener;
import com.tek.idisplays.map.MapManager;
import com.tek.idisplays.tasks.SelectionHighlightTask;
import com.tek.idisplays.tasks.WandCheckingTask;

public class SpigotBlankPlugin extends JavaPlugin {
	
	private static SpigotBlankPlugin instance;
	private Map<UUID, Selection> selections;
	private List<UUID> deletions;
	private MapManager mapManager;
	private MapCreationManager mapCreationManager;
	
	@Override
	public void onEnable() {
		
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
		
		getCommand("imagedisplays").setExecutor(new ImageDisplayCommand());
		getCommand("imagedisplays").setTabCompleter(new ImageDisplayCommand.ImageDisplayCompleter());
		
		getServer().getPluginManager().registerEvents(new WorldListener(), this);
		getServer().getPluginManager().registerEvents(new ItemFrameListener(), this);
		getServer().getPluginManager().registerEvents(new MovementListener(), this);
		getServer().getPluginManager().registerEvents(new InteractListener(), this);
		
		getServer().getScheduler().scheduleSyncRepeatingTask(this, new WandCheckingTask(), 0, 20);
		getServer().getScheduler().scheduleSyncRepeatingTask(this, new SelectionHighlightTask(), 0, 5);
		getServer().getScheduler().scheduleSyncRepeatingTask(SpigotBlankPlugin.getInstance(), mapCreationManager, 0, 4);
	}
	
	@Override
	public void onDisable() {
		mapManager.saveCache();
	}
	
	public static SpigotBlankPlugin getInstance() {
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