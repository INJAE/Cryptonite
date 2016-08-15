package Function;

import Function.PacketProcessor;
import Function.PacketRule;

import java.io.*;
import java.nio.charset.Charset;

/*
 * Developer : Youn Hee Seung
 * Date : 2016 - 08 - 08
 *
 * Name : FileShare
 * Description : It can share file
 *
 * */

public class Client_FileShare_Receive implements PacketRule
{
    // OTP Instance
    private String _OTP = "937012";

    // File Instance
    private String _downloadFolder = null;
    private RandomAccessFile _raf = null;

    private String _downloadFlag = null;
    private String _fileName = null;
    private long _fileSize = 0;
    private PacketProcessor p = null;

    // Another Class Instance
    private Client_Server_Connector _csc = null;

    // Constructors
    public Client_FileShare_Receive()
    {
        _csc = Client_Server_Connector.getInstance();
    }

    public void receiveFiles(String path)
    {
        Charset cs = Charset.forName("UTF-8");
        if(_OTP.length() != 6)
        {
            System.out.println("OTP length must be 6 letters.");
            System.exit(1);		// temporary
        }
        else if(_OTP.length() == 6)
        {
            try
            {

                _downloadFolder = path;

                while(true)
                {
                    byte[] event = new byte[1024];
                    event[0] = FILE_SHARE_SEND;
                    _csc.send.setPacket(event).write();

                    _csc.send.setPacket(_OTP.getBytes(), 30).write();	// OTP Sending

                    _downloadFlag = new String(_csc.receive.read().getByte()).trim();

                    if(_downloadFlag.equals("FALSE"))
                    {
                        System.out.println("폴스 되었습니다.");
                        break;
                    }
                    else if(_downloadFlag.equals("TRUE"))
                    {
                        _csc.receive.setAllocate(500);
                        _fileName = cs.decode(_csc.receive.read().getByteBuf()).toString().trim();
                        System.out.println("파일 이름 : " + _fileName);

                        _csc.receive.setAllocate(500);
                        _fileSize = Long.parseLong(new String(_csc.receive.read().getByte()).trim());
                        System.out.println("파일 사이즈 : " + _fileSize);

                        System.out.println(_csc.receive.allocatorCapacity());
                        _raf = new RandomAccessFile(_downloadFolder + "\\" + _fileName, "rw");

                        p = new PacketProcessor(_raf.getChannel(), false);
                        _csc.receive.setAllocate(_fileSize);

                        while(!_csc.receive.isAllocatorEmpty())
                        {
                            p.setPacket(_csc.receive.read().getByte()).write();
                            //System.out.println(_csc.receive.allocatorCapacity());
                        }
                        System.out.println("다읽음");
                        _raf.close();
                        p.close();
                    }
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            catch (NullPointerException e)
            {
                System.out.println("You does not select the folder.");
                System.exit(1);
            }

        }
    }
}