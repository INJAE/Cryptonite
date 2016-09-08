package Client;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.DefaultListModel;
import javax.swing.Icon;



public class Client_Group_Main extends JFrame{
	
	private BufferedImage img = null;

	private JButton _Select;
	private JButton _Download;
	private JButton Search;
	private JButton[] Button;
	private JButton OK;
	private JButton Withdrawal;
	private JButton Delete;
	private JTextField _idField;
	
	private boolean _passCheck = true;

	private ArrayList<String> _directoryArray;
	private ArrayList<String> _nameArray;
	
	private String[] _result;
	private String[] _fileList;
	private String[] _name;
	private String _id;
	private String _receivedID;
	private String _gpCode;
	private String _gpName;
	private String _downloadDirectory;
	private int _mod;
	private int _x=0;
	private int _y=0;
	
	private DefaultListModel<String> _model;
	private JList<String> _list;
	JScrollPane scrollPane = null;
	
	private int _count = 1;
	
	private Font fontbt = new Font("SansSerif", Font.BOLD,24);
	private Font _precondition_font = new Font ("Dialog", Font.BOLD,20);
	
	private Client_FolderSelector _cfs;
	private Client_Group_Search _cgs;
	private Client_Group_Invite _cgi;
	private Client_Group_Withdrawal _cgw;
	private Client_Delete_Group _cdg;
	private Client_File_ListReceiver _cfl;
	private Client_File_Download _cfd;
	
	public static void main(String args[])
	{
		new Client_Group_Main(null, null, null, 0, null);
	}


	public Client_Group_Main(String id, String gpCode, String gpName, int mod, Client_File_ListReceiver cfl){
		
		_receivedID = id;
		_gpCode = gpCode;
		_gpName = gpName;
		_mod = mod;
		_cfs = new Client_FolderSelector();
		_cgs = new Client_Group_Search();
		_cgi = new Client_Group_Invite();
		_cgw = new Client_Group_Withdrawal();
		_cdg = new Client_Delete_Group();
		_cfd = new Client_File_Download();
		_cfl = cfl;
		_fileList = _cfl.getFileList();
		_directoryArray = new ArrayList<String>();
		_nameArray = new ArrayList<String>();
		
		
		 _name = new String[_fileList.length];
	        for(int i = 0; i < _fileList.length; i++)
	        {	
	        	StringTokenizer st=new StringTokenizer(_fileList[i], "\\");

	        	while(st.hasMoreTokens())
	        	{
	    			_name[i] = st.nextToken();
	    		}
	    	}
	
		try{
			 Toolkit tk = Toolkit.getDefaultToolkit(); 
			 Image image = tk.getImage("gui/logo.png");
			 this.setIconImage(image);
		}
		catch(Exception e)
		{
			System.out.println("Appilcation icon not found");
		}	
		
		getContentPane().setBackground(Color.WHITE);
		setTitle("Cryptonite");
		setBounds(0,0,816,480);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		
		getContentPane().setLayout(null);
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 816, 480);
        layeredPane.setLayout(null);
        
        try 
        {
            img = ImageIO.read(new File("img/�ʴ���ȭ��.png"));
        }
        catch (IOException e)
        {
            System.out.println("�̹��� �ҷ����� ����");
            System.exit(0);
        }
        
        MyPanel panel = new MyPanel();
        panel.setBounds(0, 0, 816, 480);
        
        _idField = new JTextField(15);
        _idField.setBounds(640, 115, 100, 31);
        _idField.setOpaque(false);
        _idField.setForeground(Color.BLACK);
        _idField.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        _idField.setHorizontalAlignment(JTextField.CENTER);
        _idField.addKeyListener(new KeyListener()
        {
     		@Override
     		public void keyPressed(KeyEvent e) {}
     		@Override
     		public void keyReleased(KeyEvent e) 
     		{
     			_id = _idField.getText();
     		}
     		@Override
     		public void keyTyped(KeyEvent e) {}
           });
        	_idField.addMouseListener(new MouseAdapter(){
         	public void mouseClicked(MouseEvent e){
         		 _idField.setText("");
         	}
         });
        layeredPane.add( _idField);
        
        
        Search = new JButton(new ImageIcon("img/Search.png"));
        Search.setRolloverIcon(new ImageIcon("img/Searchh.png"));
        Search.setBounds(740, 107, 50, 50);
        Search.setFocusPainted(false);
        Search.setContentAreaFilled(false);
        Search.setBorderPainted(false);
        Search.addActionListener(new ActionListener() {     
         	public void actionPerformed(ActionEvent arg0)
         	{	
         		_cgs.setDefault();
         		_cgs.search(_id);
         		_result=_cgs.getID();
         		if(_result.length == 0)
         		{
         			_list.setVisible(false);
         			showMessage("ID Error","�����ϴ� ID�� �����ϴ�.");
         		}
         		else
         		{
         			if(_count != 1)
         			{
         				layeredPane.remove(scrollPane);
         			}
         			_count = 2;
         			_model = new DefaultListModel<>();
                    for(int i=0;i<_result.length;i++)
                    {
                     	if(!_result[i].equals(_receivedID))
                     	{
                     		_model.addElement(_result[i]);
                     	}
                    }
                    _list = new JList<>(_model);
                    _list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                    scrollPane = new JScrollPane(_list);
                    scrollPane.setVisible(true);
                    scrollPane.setBounds(628, 200, 115, 150);
                    layeredPane.add(scrollPane);
         		}
         	}
         });
        layeredPane.add(Search);
        
        OK = new JButton(new ImageIcon("img/OK.png"));
        OK.setRolloverIcon(new ImageIcon("img/OKR.png"));
        OK.setBounds(747, 304, 45, 45);
        OK.setFocusPainted(false);
        OK.setContentAreaFilled(false);
        OK.setBorderPainted(false);
        OK.addActionListener(new ActionListener() {     
         	public void actionPerformed(ActionEvent arg0)
         	{	
         		String selectedID = _model.getElementAt(_list.getSelectedIndex());
         		String check = _cgi.running(selectedID, _gpCode);
         		if(check.equals("TRUE"))
         		{
         			showMessage("Notification","�׷�� ���������� �߰� �Ǿ����ϴ�.");
         		}
         		else if(check.equals("FALSE"))
         		{
         			showMessage("Notification","�׷���� �ߺ� �˴ϴ�.");
         		}
         	}
         });
        layeredPane.add(OK);
        
        Withdrawal = new JButton(new ImageIcon("img/Check.png"));
        Withdrawal.setRolloverIcon(new ImageIcon("img/Checkp.png"));
        Withdrawal.setBounds(685, 380, 50, 50);
        Withdrawal.setFocusPainted(false);
        Withdrawal.setContentAreaFilled(false);
        Withdrawal.setBorderPainted(false);
        Withdrawal.addActionListener(new ActionListener() {     
         	public void actionPerformed(ActionEvent arg0)
         	{
         		_cgw.running(_gpCode);
         		dispose();
         	}
         });
        
        Delete = new JButton(new ImageIcon("img/Settingbt.png"));
        Delete.setPressedIcon(new ImageIcon("img/Settinghbt.png"));
        Delete.setBounds(685, 380, 50, 50);
        Delete.setFocusPainted(false);
        Delete.setContentAreaFilled(false);
        Delete.setBorderPainted(false);
        Delete.addActionListener(new ActionListener() {     
         	public void actionPerformed(ActionEvent arg0)
         	{
         		_cdg.deleteGroup(_gpCode);
         		dispose();
         	}
         });
       
        
        _Select = new JButton(new ImageIcon("img/select.png"));
        _Select.setRolloverIcon(new ImageIcon("img/selectR.png"));
        _Select.setBounds(685, 300, 80, 40);
        _Select.setFocusPainted(false);
        _Select.setContentAreaFilled(false);
        _Select.setBorderPainted(false);
        _Select.addActionListener(new ActionListener() 
        {     
         	public void actionPerformed(ActionEvent arg0)
         	{	
         		_cfs.folderSelectorON();
         		while(!_cfs.getSelectionEnd())
         		{
         			try 
         			{
						Thread.sleep(1);
					}
         			catch (InterruptedException e)
         			{
						e.printStackTrace();
					}
         		}
         		_downloadDirectory = _cfs.getSelectedPath();
         	}
         });
        
        _Download = new JButton(new ImageIcon("img/OK.png"));	
		_Download.setRolloverIcon(new ImageIcon("img/OKR.png"));
		_Download.setBounds(700, 100, 45,45);
		_Download.setVerticalTextPosition ( SwingConstants.BOTTOM ) ;
		_Download.setVerticalAlignment    ( SwingConstants.TOP ) ;
		_Download.setHorizontalTextPosition( SwingConstants.CENTER ) ;
		_Download.setBorderPainted(false);
		_Download.setFocusPainted(false);
		_Download.setContentAreaFilled(false);
		_Download.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				for(int i = 0; i < _directoryArray.size(); i++)
				{
					_cfd.requestFile(_directoryArray.get(i), _downloadDirectory + "\\" + _nameArray.get(i));
				}
			}
		});
        
    	Button = new JButton[_name.length];
		for(int i = 1; i < _name.length + 1; i++)
		{
			System.out.println("����");

			if((i % 6) == 0){
				Button[i - 1] = new JButton(_name[i - 1],new ImageIcon("gui/logo_mini.png"));		
				Button[i - 1].setPressedIcon(new ImageIcon("gui/logo_mini.png"));
				Button[i - 1].setBounds((10 - _x),(70 + _y), 92, 120);
				Button[i - 1].setVerticalTextPosition ( SwingConstants.BOTTOM ) ;
				Button[i - 1].setVerticalAlignment    ( SwingConstants.TOP ) ;
				Button[i - 1].setHorizontalTextPosition( SwingConstants.CENTER ) ;
				Button[i - 1].setBorderPainted(false);
				Button[i - 1].setFocusPainted(false);
				Button[i - 1].setContentAreaFilled(false);
				Button[i - 1].addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e)
					{
						_passCheck = true;
						int index = findIndex(e.getActionCommand());
						if(!_directoryArray.isEmpty())
						{
							for(int j = 0; j < _directoryArray.size(); j++)
							{
								if(_fileList[index].equals(_directoryArray.get(j)))
								{
									_directoryArray.remove(j);
									_nameArray.remove(j);
									_passCheck = false;
									break;
								}
							}
							if(_passCheck)
							{
								_directoryArray.add(_fileList[index]);
								_nameArray.add(e.getActionCommand());
							}
						}
						else
						{
							_directoryArray.add(_fileList[index]);
							_nameArray.add(e.getActionCommand());
						}
						Button[index].setBackground(Color.BLACK);
						
						for(int k = 0 ; k < _directoryArray.size(); k++)
						{
							System.out.println(_directoryArray.get(k));
						}
						System.out.println("----------------------");
					}
				});
				_y+=100;
				_x=0;
			}
			else{
				Button[i-1] = new JButton(_name[i-1],new ImageIcon("gui/logo_mini.png"));		
				Button[i-1].setPressedIcon(new ImageIcon("gui/logo_mini.png"));
				Button[i-1].setBounds((10+_x),70,92,120);
				Button[i-1].setVerticalTextPosition ( SwingConstants.BOTTOM ) ;
				Button[i-1].setVerticalAlignment    ( SwingConstants.TOP ) ;
				Button[i-1].setHorizontalTextPosition( SwingConstants.CENTER ) ;
				Button[i-1].setBorderPainted(false);
				Button[i-1].setFocusPainted(false);
				Button[i-1].setContentAreaFilled(false);
				Button[i-1].addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e)
					{
						_passCheck = true;
						int index = findIndex(e.getActionCommand());
						if(!_directoryArray.isEmpty())
						{
							for(int j = 0; j < _directoryArray.size(); j++)
							{
								if(_fileList[index].equals(_directoryArray.get(j)))
								{
									_directoryArray.remove(j);
									_nameArray.remove(j);
									_passCheck = false;
									break;
								}
							}
							if(_passCheck)
							{
								_directoryArray.add(_fileList[index]);
								_nameArray.add(e.getActionCommand());
							}
						}
						else
						{
							_directoryArray.add(_fileList[index]);
							_nameArray.add(e.getActionCommand());
						}
						Button[index].setBackground(Color.BLACK);
						
						for(int k = 0 ; k < _directoryArray.size(); k++)
						{
							System.out.println(_directoryArray.get(k));
						}
						System.out.println("----------------------");
					}
				});
				_x+=120;
			}
			layeredPane.add(Button[i-1]);
		}
        
        
        if(_mod == 1)
        {	
        	layeredPane.remove(Withdrawal);
        	layeredPane.add(Delete);
        	
        }
        else
        {
        	layeredPane.remove(Delete);
        	layeredPane.add(Withdrawal);
        }
        
        layeredPane.add(panel);
        getContentPane().add(layeredPane);
        setVisible(true);
	}
	
	class MyPanel extends JPanel 
	{
        public void paint(Graphics g) 
        {
            g.drawImage(img, 0, 0, null);
            g.setColor(Color.BLACK);
        	g.setFont(fontbt);
        	g.drawString("Group Name : " + _gpName, 230, 35);
        }
   }
	private void showMessage(String title, String message) 
	{
		JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
	}
	
	private int findIndex(String text)
	{
		int temp = 0;
		
		for(int i = 0; i < _name.length; i++)
		{
			if(_name[i].equals(text))
			{
				temp = i;
			}
		}
		
		return temp;
	}


}