package com.nuclearw.slice;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.WorldCreator;
import org.bukkit.World.Environment;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

public class SlicePlugin extends JavaPlugin {
	/*
	 * A mapping of all "chevrons" attached to portals that we know about in each overworld.
	 * We check against this when redstone lamps are toggling in order to be quick if we need to do more work
	 */
	HashMap<String, HashSet<Location>> overworldChevrons = new HashMap<String, HashSet<Location>>();

	/*
	 * A matrix of x y z -> Portal mappings for each overworld.
	 * This lets us get the portal for a given chevron of that portal quickly
	 */
	HashMap<String, HashBasedMatrix<Integer, Integer, Integer, SliceEntrancePortal>> overworldPortals = new HashMap<String, HashBasedMatrix<Integer, Integer, Integer, SliceEntrancePortal>>();

	/*
	 * A mapping of all active slices that exist to the overworld it is attached to.
	 */
	HashMap<String, Slice> slices = new HashMap<String, Slice>();

	@Override
	public void onEnable() {
		// TODO: Load config of which worlds to load slice information about
		loadWorld("world");
	}

	@Override
	public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
		return new SliceChunkGenerator();
	}

	private void loadWorld(final String worldName) {
		// Load the SliceEntrancePortals from the overworld.
		List<SliceEntrancePortal> portals = null; // TODO: Load them

		// Get and create an empty HashSet for chevrons in the overworld, if it is not made yet.
		HashSet<Location> chevronLocations = overworldChevrons.get(worldName);
		if(chevronLocations == null) {
			chevronLocations = new HashSet<Location>();
		}

		// Get and create an empty HashBasedMatrix for chevron to portal mappings in the overworld, if it is not made yet.
		HashBasedMatrix<Integer, Integer, Integer, SliceEntrancePortal> chevronToPortalMatrix = overworldPortals.get(worldName);
		if(chevronToPortalMatrix == null) {
			chevronToPortalMatrix = HashBasedMatrix.create();
		}

		// Place all chevrons from all portals into chevronLocations and chevronToPortalMatrix
		for(SliceEntrancePortal portal : portals) {
			Set<Location> locations = portal.getChevrons();
			for(Location location : locations) {
				chevronLocations.add(location);

				final int x = location.getBlockX();
				final int y = location.getBlockY();
				final int z = location.getBlockZ();
				chevronToPortalMatrix.put(x, y, z, portal);
			}
		}

		// Next, load all Slices and relevant data that pertain to this world
		

		// Finally, set the _slice world to be created
		getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			@Override
			public void run() {
				Bukkit.getServer().createWorld(new WorldCreator(worldName + "_slice").environment(Environment.NETHER).generator(new SliceChunkGenerator()));
			}
		});
	}
}
