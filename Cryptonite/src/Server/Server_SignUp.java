package Server;

public class Server_SignUp extends Server_Funtion 
{
	@Override
<<<<<<< HEAD
	public void Checker(byte[] packet) 
	{
		_packetMaxCount = packet[1];
=======
	public void Checker(byte[] packet) {
		// TODO �ڵ� ������ �޼ҵ� ����	
		_packetMaxCount =packet[1];
		System.out.println(_packetMaxCount);
>>>>>>> refs/remotes/origin/LeeDEV
	}

	@Override
	public void running(Server_Client_Activity activity) 
	{
		System.out.println("SignUp running");
	}
}
