package solutions;

import room.RoomCommandManager;

public class Command {
	RoomCommandManager rcm = new RoomCommandManager();
	public void execute(String command, String room, int state) {
		System.out.println(rcm.processRoom(room, state, command));
	}
}
