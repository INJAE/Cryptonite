package Client;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import Function.PacketProcessor;
import Function.PacketRule;

public class Client_File_Download implements PacketRule
{
	public void requestFile(String path, String targetpath)
	{
		try 
		{
			Client_Server_Connector csc = Client_Server_Connector.getInstance();
			byte[] event = new byte[1024];
			event[0] = FILE_DOWNLOAD;
			csc.send.setPacket(event);
			csc.send.setPacket(path.getBytes(), 500);
			long fileSize =  Long.parseLong(new String(csc.receive.setAllocate(500).read().getByte()).trim());
			csc.receive.setAllocate(fileSize);
			File file = new File(targetpath);
			RandomAccessFile raf  = new RandomAccessFile(file, "rw");
			PacketProcessor p = new PacketProcessor(raf.getChannel(), false);
			p.setAllocate(fileSize);
			while(!csc.receive.isAllocatorEmpty())
			{
				p.setPacket(csc.receive.read().getByte()).write();
			}
			p.close();
			
		} catch (NumberFormatException e) {
			// TODO �ڵ� ������ catch ���
			e.printStackTrace();
		} catch (IOException e) {
			// TODO �ڵ� ������ catch ���
			e.printStackTrace();
		}
	}
}
