package Server;

public class Server_SignUp extends Server_Funtion 
{
	@Override
	public void Checker(byte[] packet) {
		// TODO �ڵ� ������ �޼ҵ� ����	
		_packetMaxCount =packet[1];
		System.out.println(_packetMaxCount);
	}

	@Override
	public void running(Server_Client_Activity activity) {
		// TODO �ڵ� ������ �޼ҵ� ����
		
	}
}
