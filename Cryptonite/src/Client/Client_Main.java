package Client;

import java.io.IOException;


/**
 * @author user
 *
 */
public class Client_Main
{
	public static void main(String[] args)
	{
			Client_Server_Connecter  ccs;
			try {
				ccs = new Client_Server_Connecter (4444);
				 ccs.SendByte(null, 0);
			} catch (InterruptedException e) {
				// TODO �ڵ� ������ catch ���
				e.printStackTrace();
			} catch (IOException e) {
				// TODO �ڵ� ������ catch ���
				e.printStackTrace();
			}
	       
	}
}
