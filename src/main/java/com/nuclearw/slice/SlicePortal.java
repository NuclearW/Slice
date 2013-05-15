package com.nuclearw.slice;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;

public abstract class SlicePortal {
	protected Location corner;
	protected Direction direction;

	// It's like BlockFace but less!
	enum Direction {
		NORTH(BlockFace.NORTH),
		EAST(BlockFace.EAST),
		SOUTH(BlockFace.SOUTH),
		WEST(BlockFace.WEST);

		final BlockFace face;

		Direction(BlockFace face) {
			this.face = face;
		}

		BlockFace getFace() {
			return face;
		}
	};
}
