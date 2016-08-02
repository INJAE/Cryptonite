package Client;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import Function.PacketRule;
import Server.Server_DataBase;

	public class Client_SignUp extends JFrame //Create new account
	//Create new page
	{											
		BufferedImage _img=null;
		SHA_256 _sha = null;
		
		public static void main(String[] args){
			new Client_SignUp();
		}
		
		private boolean _checkID = true;
		private boolean _checkPassword=false;
		public static boolean _checkSame=false;
		public static boolean _goSignUP = false;
		public static boolean _goFolderScan = false;
		
		public static boolean getGoSignUP()
		{		   
			return _goSignUP;

		}
		public static boolean getCheckSame()
		{		   
			return _checkSame;

		}

		public static boolean getgoFoldercan()
		{
			return _goFolderScan;
		}	
		
		private void showMessage(String _title, String _message) 
		{
			JOptionPane.showMessageDialog(null, _message, _title, JOptionPane.INFORMATION_MESSAGE);
		}

		private String serverIP=null;//input serverip
		//private int serverPort = 10000;
		private SocketChannel socket=null;

		
		JTextField _nameField;
		JTextField _idField;
		JTextField _emailField;
		JPasswordField _passwdField;
		JPasswordField _passwdCorrectField;
		
		private String _name="name";
		private String _id="id";
		private String _email="email";
		private String _password="password";
		private String _passwordCorrect="passwordCorrect";

		JButton _same;
		JButton _ok;
		JButton _cancel;

		Font fontblank = new Font ("SansSerif", Font.BOLD,13);
	
		Client_Server_Connector _css;

		public Client_SignUp(){
			try {
				_css=Client_Server_Connector.getInstance(4444);
			} catch (InterruptedException e1) {
				// TODO 자동 생성된 catch 블록
				e1.printStackTrace();
			}
			setTitle("CRYPTONITE");
			setBounds(710,200,508, 660);//Input value

			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			getContentPane().setLayout(null);
			setBackground(Color.BLACK);

			try
			{
				_img = ImageIO.read(new File("D:\\crypto\\resistor.png"));//Input MainImage
				
			}catch(IOException e){
				System.out.println("No Image.");
				System.exit(0);
			}

			JLayeredPane _layeredpane =new JLayeredPane();
			_layeredpane.setBounds(0, 0, 490, 655);//input value
			_layeredpane.setLayout(null);

			Mypanel _panel = new Mypanel();
			_panel.setBounds(0,0,490,655);//input value  

			_nameField = new JTextField();
			_nameField.setBounds(193,290, 130, 20);//input value
			_nameField.setBorder(BorderFactory.createEmptyBorder());
			_nameField.setForeground(Color.BLACK);

			_nameField.addKeyListener(new KeyListener()
			{
				@Override
				public void keyPressed(KeyEvent _e) { }
			
				@Override
				public void keyReleased(KeyEvent _e) 
				{
					_name = _nameField.getText();
				}


				@Override
				public void keyTyped(KeyEvent _e)
				{}

			});
			_layeredpane.add(_nameField);

			_idField = new JTextField();
			_idField.setBounds(193, 326, 130, 20);//input value

			_idField.setForeground(Color.BLACK);
			_idField.setBorder(BorderFactory.createEmptyBorder());
			//_idField.setOpaque(false);

			_idField.addKeyListener(new KeyListener()
			{
				@Override
				public void keyPressed(KeyEvent _e)  { }

				@Override
				public void keyReleased(KeyEvent _e) 
				{
					_id = _idField.getText();
					_checkSame=false;
				}

				@Override
				public void keyTyped(KeyEvent _e) { }
			}); 
			_layeredpane.add(_idField);

			_passwdField = new JPasswordField();
			_passwdField.setBounds(194, 365, 130, 20);//input value

			_passwdField.setEchoChar('●');
			_passwdField.setBorder(BorderFactory.createEmptyBorder());
			_passwdField.setForeground(Color.BLACK);
			//_passwdField.setOpaque(false);
			_passwdField.addKeyListener(new KeyListener()
		{
				@Override
				public void keyPressed(KeyEvent _e) { }
				
				@Override
				public void keyReleased(KeyEvent _e) 
				{
					_password = new String(_passwdField.getPassword());
			}

				@Override
				public void keyTyped(KeyEvent _e) { }
		});
			_layeredpane.add(_passwdField);

			_passwdCorrectField = new JPasswordField();
			_passwdCorrectField.setBounds(194, 408, 130, 20);//input value
			_passwdCorrectField.setEchoChar('●');
			_passwdCorrectField.setBorder(BorderFactory.createEmptyBorder());
			_passwdCorrectField.setForeground(Color.BLACK);
			//_passwdCorrectField.setOpaque(false);
			_passwdCorrectField.addKeyListener(new KeyListener()
		{
				@Override
				public void keyPressed(KeyEvent _e) { }
				
				@Override
				public void keyReleased(KeyEvent _e) 
			{
				_passwordCorrect = new String( _passwdCorrectField.getPassword());

				if(_passwordCorrect.equals(_password))
				{
					_checkPassword = true;
				}
				else if( _passwdCorrectField.equals(_password) == false &&  _passwdCorrectField.equals(null) == false)
				{
					_checkPassword = false;
				}

			}

			@Override
			public void keyTyped(KeyEvent _e) { }

		});
		_layeredpane.add( _passwdCorrectField);

		_emailField = new JTextField();
		_emailField.setBounds(193, 455, 160, 20);//input value
		_emailField.setBorder(BorderFactory.createEmptyBorder());
		_emailField.setForeground(Color.BLACK);
		//_emailField.setOpaque(false);
		_emailField.addKeyListener(new KeyListener()
		{
			@Override
			public void keyPressed(KeyEvent _e) { }

			@Override
			public void keyReleased(KeyEvent _e)
			{
				_email = _emailField.getText();
			}

			@Override
			public void keyTyped(KeyEvent _e) { }

		});
		_layeredpane.add(_emailField);

		_same = new JButton(new ImageIcon(""));//Input IconImage
		_same.setRolloverIcon(new ImageIcon(""));
		_same.setBounds(356, 312, 80, 38);//input value
		_same.setBorderPainted(false);
		_same.setFocusPainted(false);
		_same.setContentAreaFilled(false);
		_same.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				_checkSame=true;
				/*Connection con=null;
				con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/cryptonite", "root", "yangmalalice3349!");*/
				Server_DataBase _db;
				_db=Server_DataBase.getInstance();
				_db.Init_DB("com.mysql.jdbc.Driver", "jdbc:mysql://127.0.0.1:3306/"+"cryptonite", "root", "yangmalalice3349!");
				_db.connect();
				
				Connection _con=(Connection) _db.Getcon();
				PreparedStatement _ps = null;// 동적 쿼리를 이용해 보기
				ResultSet _rs = null;        // 데이터 주소값 이용하기 위함
				String _sql = "select * from test";
				
				
				try{
					_ps = (PreparedStatement)_con.prepareStatement(_sql);
					_rs = _ps.executeQuery(); // 쿼리문이 select 로 시작되면 무조건
					System.out.println("id");
					while(_rs.next()){
						String _get_id = _rs.getString(2); // 두번째 필드의 데이터
						System.out.println(_get_id);
						if(_get_id.equals(_id)){
							_checkID=false;
						}
					}
				}catch(SQLException e){
					e.printStackTrace();
				}
				if (_id.equals("id")==false)
				{
					if(_checkID==false){
						showMessage("CHECK ID", "Username already taken. Please try another one.");
						_checkID=true;
					}
					else if(_checkID==true){
						showMessage("CHECK ID", "ID that you can use");
						_goSignUP = true;
						
					}
				}
				
				else
				{
					showMessage("ERROR", "Please input id.");
				}
			}
			
			
		});
		_layeredpane.add(_same);

		_cancel = new JButton(new ImageIcon(""));//Input IconImage
		_cancel.setRolloverIcon(new ImageIcon(""));
		_cancel.setPressedIcon(new ImageIcon(""));

		_cancel.setBounds(260, 544, 82, 38);//input value
		_cancel.setBorder(BorderFactory.createEmptyBorder());

		_cancel.setBorderPainted(false);
		_cancel.setFocusPainted(false);
		_cancel.setContentAreaFilled(false);

		_cancel.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				dispose();
			}

		});
		_layeredpane.add(_cancel);

		_ok = new JButton(new ImageIcon(""));//Input IconImage
		_ok.setRolloverIcon(new ImageIcon(""));
		_ok.setPressedIcon(new ImageIcon(""));

		_ok.setBounds(156, 544, 82, 38);//input value
		_ok.setBorderPainted(false);
		_ok.setFocusPainted(false);
		_ok.setContentAreaFilled(false);

		_ok.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent _arg0)
			{
				if(_name.equals("name") == false && _id.equals("id") == false && _password.equals("password") == false &&
						_passwordCorrect.equals("passwordCorrect") == false && _email.equals("email") == false && _goSignUP == true&&_checkSame==true)
				{
					if(_checkPassword == true)
					{
						showMessage("가입 완료", "회원가입이 완료되었습니다. 로그인해주세요.");

						//userKeyGenerator _ukg = new userKeyGenerator();
						_sha = new SHA_256(_name,_id,_password,_email/*,_ukg.genEncAesKey(_password), _ukg.getSalt(), _ukg.getIterationCount()*/);

						dispose();
					}
					else if(_checkPassword == false)
					{
						showMessage("비밀번호 오류", "비밀번호가 일치하지 않습니다.");
					}

				}
				else
				{
					if(_checkSame==false){
						showMessage("가입 오류", "아이디 중복을 확인해 주세요.");
					}
					else if(_goSignUP==false&&_checkSame==true)
					{
						showMessage("가입 오류", "모든 항목을 다 입력하지않았거나, 아이디 중복입니다.");
					}
				}
			}
		});

		_layeredpane.add(_ok);
		_layeredpane.add(_panel);//패널1을 레이아웃에 넣기
		getContentPane().add(_layeredpane);


		setVisible(true);
	}
	class Mypanel extends JPanel
	{
		public void paint(Graphics _g)
		{
			_g.drawImage(_img,0,0,null);
		}
	}
}
class SHA_256 implements PacketRule

	{	
		MessageDigest _messageDigest = null;
		Client_Server_Connector _css;

		private String _name;
		private String _id;
		private String _password;
		private String _email;

		private byte[] _AES_Key;
		private byte[] _salt;
		private int _iteration;

		private byte[] _temp_name;
		private byte[] _temp_pwd;
		private byte[] _temp_email;
		
		private byte[] _temp_data;
		private byte _size;

		public SHA_256(String _name, String _id, String _password, String _email/*, byte[] _AES_Key, byte[] _salt, int _iteration*/)
		{	
			try {
				_css=Client_Server_Connector.getInstance(4444);
			} catch (InterruptedException e) {
				// TODO 자동 생성된 catch 블록
				e.printStackTrace();
			}
			this._name=_name;
			this._id = _id;
			this._password = _password;
			this._email = _email;
			/*this._AES_Key = _AES_Key;
		 	this._salt = _salt;
		 	this._iteration = _iteration;*/

			SHA_Encryption();
			sendPrivacy();
		}
		
		public void sendPrivacy(){
			_css.configurePacket("resistor");
			_size=5;
					
			byte[] _buf = new byte[2];
			_buf[0] = SIGN_UP;
			_buf[1] = _size;
		 	/*ByteBuffer _message = ByteBuffer.allocateDirect(1024);
			ByteBuffer _name_byte = ByteBuffer.allocateDirect(1024);
			ByteBuffer _id_byte = ByteBuffer.allocateDirect(1024);
			ByteBuffer _password_byte = ByteBuffer.allocateDirect(1024);
			ByteBuffer _email_byte = ByteBuffer.allocateDirect(1024);

			Charset _charset = Charset.forName("UTF-8");
			
			_message.put(_buf);
			_name_byte.put( _name.getBytes());
			_name_byte=_charset.encode(_name).array();
			_id_byte.put(_id.getBytes());
			_password_byte.put(_password.getBytes());
			_email_byte.put(_email.getBytes());
			
			_message.flip();
			_name_byte.flip();
			_id_byte.flip();
			_password_byte.flip();
			_email_byte.flip();
			
			_css.setPacket("resistor", _message);
			_css.setPacket("resistor",_name_byte);
			_css.setPacket("resistor",_id_byte);
			_css.setPacket("resistor",_password_byte);
			_css.setPacket("resistor",_email_byte);
			*/
			Charset _charset = Charset.forName("UTF-8");
			
			_css.setPacket("resistor", _buf);
			_css.setPacket("resistor",_charset.encode(_name).array());
			_css.setPacket("resistor",_id.getBytes());
			_css.setPacket("resistor",_password.getBytes());
			_css.setPacket("resistor",_email.getBytes());
			
			_css.send("resistor");
		}
		public void SHA_Encryption(){
			setName();
			setPWD();
			setEmail();
		}
		private void setName()
		{
			try {
				_messageDigest = MessageDigest.getInstance("SHA-256");
				_temp_name = _name.getBytes();
				_messageDigest.update(_temp_name);
				this._name = new String(Function.Base64Coder.encode(_messageDigest.digest()));
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		}

		private void setPWD()
		{
			try {
				_messageDigest = MessageDigest.getInstance("SHA-256");
				_temp_pwd = _password.getBytes();
				_messageDigest.update(_temp_pwd);
				_password = new String(Function.Base64Coder.encode(_messageDigest.digest()));
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		}	

		private void setEmail()
		{
			try {
				_messageDigest = MessageDigest.getInstance("SHA-256");
				_temp_email = _email.getBytes();
				_messageDigest.update(_temp_email);
				_email = new String(Function.Base64Coder.encode(_messageDigest.digest()));
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		}
	}	
