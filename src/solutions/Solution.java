package solutions;

import room.GameState;

public interface Solution {
	public void execute(String command, String room, GameState state);
}
