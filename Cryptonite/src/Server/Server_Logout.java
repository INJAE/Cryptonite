package Server;

import java.io.IOException;

import org.omg.CORBA.ACTIVITY_COMPLETED;

public class Server_Logout extends Server_Funtion{

	Server_Client_Activity _activity;
	Server_Client_Manager _manager;
	@Override
	public void Checker(byte[] packet, Server_Client_Activity activity) {
		// TODO �ڵ� ������ �޼ҵ� ����
		_activity=activity;
		_packetMaxCount = 2;
		_manager=Server_Client_Manager.getInstance();
		_activity.receive.setAllocate(2);
	}

	@Override
	public void running() throws IOException {
		// TODO �ڵ� ������ �޼ҵ� ����
		_activity.receive.getByte();
		_manager.logOut(_activity.getClientCode());
	}
	

}
