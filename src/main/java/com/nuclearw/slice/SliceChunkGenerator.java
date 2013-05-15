package com.nuclearw.slice;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;

public class SliceChunkGenerator extends ChunkGenerator {
	@Override
	public byte[][] generateBlockSections(World world, Random random, int cx, int cz, BiomeGrid biomes) {
		byte[][] result = new byte[world.getMaxHeight() / 16][];

		for(int x = 0; x < 16; x++) {
			for(int z = 0; z < 16; z++) {
				for(int y = 0; y < world.getMaxHeight(); y++) {
					if(y % 6 == 0) {
						setBlock(result, x, y, z, (byte) Material.BEDROCK.getId());
					} else {
						if(cx > 1 || cz > 1 || cx < -1 || cz < -1) {
							setBlock(result, x, y, z, (byte) Material.NETHERRACK.getId());
						} else if(x == 0 && z == 0 && y % 6 == 5) {
							setBlock(result, x, y, z, (byte) Material.GLOWSTONE.getId());
						}
					}
				}
			}
		}

		return result;
	}

	@Override
	public Location getFixedSpawnLocation(World world, Random random) {
		int y = world.getHighestBlockYAt(0, 0);
		return new Location(world, 0, y, 0);
	}

	private void setBlock(byte[][] result, int x, int y, int z, byte block) {
		if(result[y >> 4] == null) {
			result[y >> 4] = new byte[4096];
		}
		result[y >> 4][((y & 0xF) << 8) | (z << 4) | x] = block;
	}
}
