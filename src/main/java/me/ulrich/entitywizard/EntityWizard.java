package me.ulrich.entitywizard;

import org.bukkit.plugin.java.JavaPlugin;

import com.github.retrooper.packetevents.PacketEvents;

import io.github.retrooper.packetevents.factory.spigot.SpigotPacketEventsBuilder;
import me.tofaa.entitylib.APIConfig;
import me.tofaa.entitylib.EntityLib;
import me.tofaa.entitylib.spigot.SpigotEntityLibPlatform;
import me.ulrich.entitywizard.VersionUtil.VersionEnum;

public class EntityWizard  extends JavaPlugin{

	@Override
	public void onLoad() {
		
        //Creating and loading the API is necessary when shading!
        
        //Register the listener
		
        PacketEvents.setAPI(SpigotPacketEventsBuilder.build(this));
        
        PacketEvents.getAPI().getSettings().reEncodeByDefault(false)
        .debug(false)
        .kickOnPacketException(false)
                .checkForUpdates(false);
        
        PacketEvents.getAPI().load();


	}
	
	@Override
	public void onEnable() {
		
		if(VersionUtil.isAbove(VersionEnum.V118)) {
			PacketEvents.getAPI().init();
			
	        SpigotEntityLibPlatform platform = new SpigotEntityLibPlatform(this);
	        APIConfig settings = new APIConfig(PacketEvents.getAPI())
	                .tickTickables()
	                .usePlatformLogger();

	        EntityLib.init(platform, settings);
	        
			this.getLogger().info("========================================");
			this.getLogger().info("Wizard initialized package system for Entities!");
			this.getLogger().info("========================================");
		} else {
			this.getLogger().info("========================================");
			this.getLogger().info("This Wizard needs Minecraft version 1.18+");
			this.getLogger().info("========================================");
			this.onDisable();
		}

		
		
	}
	
	@Override
	public void onDisable() {
		
		EntityLib.getApi().getDefaultContainer().clearEntities(true);
		PacketEvents.getAPI().terminate();
		this.getLogger().info("========================================");
		this.getLogger().info("Terminated package system for Entities!");
		this.getLogger().info("========================================");

	}
}
