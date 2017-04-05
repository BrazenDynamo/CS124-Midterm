package solutions;

import room.GameState;
import room.Room1;

public class Start implements Solution {

	@Override
	public void execute(String command, String room, GameState state) {
		// TODO Add intro
		Room1 room1 = new Room1();
		
		room1.checkRoom(state);
	}
}