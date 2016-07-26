package Server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.*;

public class Server_Client_Accepter extends Thread
{
	private Selector selector;
	private SelectionKey socketServerSelectionKey;
	
	public Server_Client_Accepter(String hostname ,int port)
	{
		 try 
		 {
			ServerSocketChannel channel = ServerSocketChannel.open();
			channel.bind(new InetSocketAddress(hostname, port)); 
	        channel.configureBlocking(false);
	        
	        selector = Selector.open();
	        socketServerSelectionKey = channel.register(selector, SelectionKey.OP_ACCEPT);	       
		 }
		 catch (IOException e)
		 {
			e.printStackTrace();
		 }
	}
	
	public void run()
	{
		try 
		{
			while(!Thread.interrupted())
			{							
				if(selector.select() == 0) continue;
				
			    Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
			   
			    while (keys.hasNext()) 
			    {
			        SelectionKey key = keys.next();
			        keys.remove();
			        
			        if(key.isAcceptable())
			        {
			        	System.out.println("����");
			        	new Server_Client_Activity(selector, key); 	
			        }
			        else if(key.isReadable())
			        {
			        	System.out.println("����");
			        	Server_Client_Activity activity = (Server_Client_Activity)key.attachment();
			        	activity.Receiver();
			        }
			        else if(key.isWritable())
			        {
			        	System.out.println("����");
			        	Server_Client_Activity activity = (Server_Client_Activity)key.attachment();
			        }        
			        else if(key.isValid())
			        {
			        	System.out.println("�������");
			        	Server_Client_Activity activity = (Server_Client_Activity)key.attachment();
			        }
			        
			    } 
			    //System.out.println("�������Դ�!");
	            
	        }        
		} catch (IOException e) {
			// TODO �ڵ� ������ catch ���
			e.printStackTrace();
		}
	}
}
