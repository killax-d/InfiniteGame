/**
 * InfiniteGame plugin for PaperMC 1.14.4
 * Copyright (c) 2020-present, Killax-D.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.phoenixgames.infinite;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.libs.org.apache.commons.io.FileUtils;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.gson.Gson;
import com.phoenixgames.infinite.settings.InfiniteSettings;

public class InfiniteGame extends JavaPlugin {

	private static InfiniteGame instance;
	private static InfiniteSettings settings;
	
	@Override
	public void onLoad() {
		// LOAD config file from world folder
        Gson gson = new Gson();
        
		try (FileReader reader = new FileReader("infinite.json")) {
			settings = gson.fromJson(reader, InfiniteSettings.class);
			initMap();
		} catch (IOException e) {
	    	System.err.println("No infinite.json in root folder, shutting down plugin...");
	    	Bukkit.getPluginManager().disablePlugin(this);
	    }
		instance = this;
	}

	@Override
	public void onEnable() {
		
	}

	@Override
	public void onDisable() {
		if(settings != null)
			restart();
	}
	
	public static InfiniteGame getInstance() {
		return instance;
	}
	
	public InfiniteSettings getSettings() {
		return settings;
	}
	
	public void restart() {
		Thread shutdownHook = new Thread() {
			@Override
			public void run() {
				try {
				    Bukkit.getLogger().log(Level.INFO, "Try restarting game");
					String os = System.getProperty("os.name").toLowerCase();
		            Bukkit.getLogger().log(Level.INFO, "OS name found : " + os);
					if (os.contains("win")) {
						File file = new File(settings.getWindowsScript());
						Runtime.getRuntime().exec("cmd /c start " + file.getPath());
					} else {
			            File f = new File(System.getProperty("java.class.path"));
			            File dir = f.getAbsoluteFile().getParentFile();
			            String path = dir.toString();
			            Bukkit.getLogger().log(Level.INFO, "Try restarting with : " + path + "/" + settings.getLinuxScript());
						String[] cmd = new String[] {"sh", path + "/" + settings.getLinuxScript()};
						Runtime.getRuntime().exec(cmd);
					}
				} catch (Exception e) {
		            Bukkit.getLogger().log(Level.SEVERE, "Unable to restart game error : ");
		            Bukkit.getLogger().log(Level.SEVERE, e.getLocalizedMessage());
				}
			}
		};
		shutdownHook.setDaemon(true);
		shutdownHook.run();
	}
	
	public void initMap() {
		Bukkit.getLogger().log(Level.INFO, "Trying to regenerate map...");
					
		File src = new File(settings.getWorldBackup());
		File dst = new File(settings.getWorldFolder());
					
		Bukkit.getLogger().log(Level.INFO, "copying files map from " + src.getPath() + " to " + dst.getPath());

		try {
			FileUtils.deleteDirectory(dst);
			FileUtils.copyDirectory(src, dst);
	    	Bukkit.getLogger().log(Level.INFO, "Map was regenerated");
		} catch (Exception e) {
	    	Bukkit.getLogger().log(Level.SEVERE, "Unable to regen the map");
			e.printStackTrace();
		}
	}
	
}
