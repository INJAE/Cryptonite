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
		_manager=Server_Client_Manager.getInstance();
		_manager.requestManage(activity.getClientCode());
	}

	@Override
	public void running() throws IOException {
		// TODO �ڵ� ������ �޼ҵ� ����
		Server_Client_Manager.getInstance().logOut(_activity.getClientCode());
		_activity.setClientCode(_manager._code_manager.getAcCode());
	}
	

}
