package solutions;

import room.GameState;
import room.Room1;
import framework.Framework;

public class Go implements Solution{

	@Override
	public void execute(String command, String room, GameState state) {
		// TODO Auto-generated method stub
		String newRoom = command.split(" ")[1];
		newRoom = "R" + newRoom.substring(1);
		
		Framework.room = newRoom;
	}

}
