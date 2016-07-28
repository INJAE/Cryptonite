package Server;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import Function.PacketRule;

public class Server_Client_Activity implements PacketRule 
{
	private SocketChannel _channel;
	
	public Queue<byte[]> _receiveQueue; 
	public Queue<byte[]> _sendQueue;
	
	public LinkedBlockingQueue<Byte> _runningFuntion;
	
	public int _packetCount = 0;
	private int _clientCode = 0;
	
	public HashMap<Byte, Server_Funtion> _funtionList;
	
	public Server_Client_Activity(Selector selector, SelectionKey key, int clientCode)
	{
		try 
		{
			ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
	        _channel = serverSocketChannel.accept();
	        
	        _channel.configureBlocking(false);
            SelectionKey clientKey = _channel.register(selector, 
            									SelectionKey.OP_READ,SelectionKey.OP_WRITE);
            clientKey.attach(this);
            
            _clientCode = clientCode;
            
            _receiveQueue = new LinkedList<byte[]>();            
            _sendQueue = new LinkedList<byte[]>();
            _runningFuntion = new LinkedBlockingQueue<Byte>();
            
            _funtionList = new HashMap<Byte, Server_Funtion>();
            _funtionList.put(AUTOBACKUP, new Server_AutoBackup());
            _funtionList.put(LOGIN, new Server_Login());
            _funtionList.put(FILE_SHARE_RECIEVE, new Server_FileShare_Receive());
            _funtionList.put(FILE_SHARE_SEND, new Server_FileShare_Send());
            _funtionList.put(SIGN_UP,new Server_SignUp());
            
            System.out.println(_channel.toString() + "connect");
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public int getClientCode()
	{
		return _clientCode;
	}
	
	public void Sender(byte[] packet) 
	{
		
	}
	
	public void Receiver() throws IOException 
	{	
		ByteBuffer buffer = ByteBuffer.allocateDirect(1024);		
		int count = _channel.read(buffer);			
		buffer.flip();
		
		byte[] array = new byte[buffer.remaining()];	
		buffer.get(array);

		_receiveQueue.add(array);	
		_packetCount++;
		
		System.out.println(_channel.toString() + "read :" + count);	
		
		if(_packetCount == 1)
		{
			Server_Client_Manager.getInstance().packetChecker(this);
		}
		
		if(!_runningFuntion.isEmpty())
		{
			if(_funtionList.get(_runningFuntion.element())._packetMaxCount == _packetCount)
			{
				Server_Client_Manager.getInstance().requestManage(_clientCode);
				_packetCount = 0;
			}
		}
	}
	
	public void close() 
	{
		System.out.println(_channel.toString() + "Stop Connect");
		try {
			_channel.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
