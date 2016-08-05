
package Client;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.StringTokenizer;

import javax.imageio.ImageIO;
import javax.lang.model.element.PackageElement;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import Function.Base64Coder;
import Function.PacketRule;
import Server.Server_DataBase;



public class Client_Login extends JFrame implements PacketRule
{
	public static void main(String[] args){
		new Client_Login();
	}
	BufferedImage _img = null;
	
	class MyPanel extends JPanel {
        public void paint(Graphics g) {
            g.drawImage(_img, 0, 0, null);
        }
    }
    
	private boolean _checkLogin =false;
    private boolean _firstTime = true;
    
    JTextField _loginField;
    JPasswordField _passwordField;
    
    private String _id = "id";
    private String _password = "password";
    private String _tempPassword = "init";

    private Client_Server_Connector csc;
    /*//private Client_FolderChooser_UI fc = null;
    
    // 로그인 카운터 읽어주기 위한 것
    private FileReader fr = null;
    private String loginCount = null;
    private StringTokenizer st = null;
    
    // 로그인 횟수가 2번이상일 경우 여기서 스레드 실행
    private Client_FolderScan cfs = null;
    private Client_SendFiles csf = null;
    private Client_checkEncryptionAnime cea = null;
    private Client_FileShare_Send cfss = null;
    
    // 메인 프레임 UI
    private boolean mainFrameFlag = false;
    private Client_MainFrame_UI cmfu = null;
    
    // AES_Key 추출 관련
    private User loginedUser = null;
    private byte[] AES_Key = null;
    */
    // 맥 어드레스 추출
    
    Font _font1 = new Font("SansSerif", Font.BOLD, 25);
    Font _fontjoin = new Font("SansSerif", Font.BOLD,13);
    Font _fontid = new Font ("SansSerif", Font.BOLD,15);
    
    JButton _Login;      
    JButton _Resistor;
    
    JRadioButton _individual = new JRadioButton();
    JRadioButton _group = new JRadioButton();
    public ButtonGroup _buttonGroup = new ButtonGroup();
    
    private void showMessage(String title, String message) {
		JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
	}
    
    public Client_Login(){
    	try {
			csc=Client_Server_Connector.getInstance(4444);
		} catch (InterruptedException e1) {
			// TODO 자동 생성된 catch 블록
			e1.printStackTrace();
		}
    	setTitle("Cryptonite");
        setBounds(0,0,470,645);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
 

        
        getContentPane().setLayout(null);
        JLayeredPane _layeredPane = new JLayeredPane();
        _layeredPane.setBounds(0, 0, 470, 645);
        _layeredPane.setLayout(null);

      
        try {
            _img = ImageIO.read(new File("img/_login.png"));//input image
        } catch (IOException e) {
            System.out.println("No Image");
            System.exit(0);
        }
         
        MyPanel _panel = new MyPanel();
        _panel.setBounds(0, 0, 470, 645);
        
        _buttonGroup.add(_individual);
        _buttonGroup.add(_group);
        
        _individual.setBounds(180, 200, 20, 20);
        _individual.setBorder(BorderFactory.createEmptyBorder());
        _individual.setOpaque(false);
        _group.setBounds(300, 200, 20, 20);
        _group.setBorder(BorderFactory.createEmptyBorder());
        _group.setOpaque(false);
        
        _layeredPane.add(_individual);
        _layeredPane.add(_group);
  
        
        _loginField = new JTextField(15);
        _loginField.setBounds(162, 264, 180, 20);
        _layeredPane.add(_loginField);
        _loginField.setOpaque(false);
        _loginField.setForeground(Color.BLACK);
        _loginField.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        _loginField.setHorizontalAlignment(JTextField.CENTER);
        _loginField.setText("ID");
        _loginField.addKeyListener(new KeyListener(){
     		@Override
     		public void keyPressed(KeyEvent e) {}
     		@Override
     		public void keyReleased(KeyEvent e) {
     			_id = _loginField.getText();
     		}
     		@Override
     		public void keyTyped(KeyEvent e) {
     			if(_firstTime == true){
     				_loginField.setText("");
     				_firstTime = false;
     			}
     		}
           });
         _loginField.addMouseListener(new MouseAdapter(){
         	public void mouseClicked(MouseEvent e){
         		_loginField.setText("");
         	}
         });
        
         _passwordField = new JPasswordField(15);
         _passwordField.setBounds(162, 350, 200, 20);
         _passwordField.setOpaque(false);
         _passwordField.setForeground(Color.BLACK);
         _passwordField.setFont(_fontid);
         _passwordField.setBorder(javax.swing.BorderFactory.createEmptyBorder());
         _passwordField.setHorizontalAlignment(JTextField.CENTER);
         _passwordField.setEchoChar((char)0);
         _passwordField.setText("PASSWORD");
         _passwordField.addFocusListener(new FocusAdapter(){
        	 public void focusGained(FocusEvent fe){
        		 _passwordField.setText("");
        		 _passwordField.setEchoChar('●');
        	 }
         });
         _passwordField.addKeyListener(new KeyListener(){
     		@Override
     		public void keyPressed(KeyEvent e) {}
     		@Override
     		public void keyReleased(KeyEvent e) {
     			_password = Encode_password(new String(_passwordField.getPassword()));
     		}
     		@Override
     		public void keyTyped(KeyEvent e) {}
           });
         _layeredPane.add(_passwordField);
         
         _passwordField.addMouseListener(new MouseAdapter(){
         	public void mouseClicked(MouseEvent e){
         		_passwordField.setText("");
         		_passwordField.setEchoChar('●');
         	}
         });
         
         _Login = new JButton(new ImageIcon("img/_loginbt.png"));//input buttonimage
         _Login.setBounds(184, 424, 80, 37);
         _Login.setBorderPainted(false);
         _Login.setFocusPainted(false);
         _Login.setContentAreaFilled(false);
         _Login.setRolloverIcon(new ImageIcon("img/_loginbtover"));//input buttonimage
         _Login.setPressedIcon(new ImageIcon("img/_loginbtpr.png"));
         _Login.addMouseListener(new MouseAdapter(){
          	public void mouseClicked(MouseEvent e){
          		byte size=3;
          		System.out.println("눌렸습니다.");
          		csc.configurePacket("login");
          		byte[] event =new byte[2];
          		event[0]=LOGIN;
          		event[1]=size;
          		csc.setPacket("login", event);
          		csc.setPacket("login", _id.getBytes());
          		csc.setPacket("login", _password.getBytes());
          		System.out.println(_id+"\t"+_password);
          		csc.send("login");
          		try 
          		{
	          		byte[] checkLogin = new byte[1024];
					
					checkLogin = csc.receiveByteArray();
				
	          		switch(checkLogin[0]){
	          		case 1 :
	          			showMessage("Error", "No id");
	          			break;
	          		case 2 : 
	          			showMessage("LOGIN", "Welcome,\t"+_id); 
	          			break;
	          		case 3 : 
	          			showMessage("Error", "Wrong password"); 
	          		}
	          		if(_individual.isSelected()){
	          			
	          		}
	          		if(_group.isSelected()){
	          			
	          		}
				} catch (IOException e1) {
					// TODO 자동 생성된 catch 블록
					e1.printStackTrace();
				}
          		
          	}
         });
         _Resistor = new JButton(new ImageIcon("img/_joinbt.png"));
         _Resistor.setFont(_fontjoin);
         _Resistor.setForeground(Color.white);
         _Resistor.setBounds(184,480,80,37);
         _Resistor.setBorderPainted(false);
         _Resistor.setFocusPainted(false);
         _Resistor.setContentAreaFilled(false);
         _Resistor.setRolloverIcon(new ImageIcon("img/_joinbtover"));
         _Resistor.setPressedIcon(new ImageIcon("img/_joinbtpr.png"));
         _Resistor.addActionListener(new ActionListener() {
         	public void actionPerformed(ActionEvent arg0) {
         		new Client_SignUp();
         	}
         });
        
         _layeredPane.add(_Login);
         _layeredPane.add(_Resistor);
         _layeredPane.add(_panel);
              
         getContentPane().add(_layeredPane);          
         setVisible(true);
          	
    }	    
    
    
    public String Encode_password(String _password){
    	MessageDigest _md = null;
		try {
			_md = MessageDigest.getInstance("SHA-256");
			byte[] temp = _password.getBytes();
			_md.update(temp);
			_password = new String(Base64Coder.encode(_md.digest()));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
    	return _password;
    }
}
