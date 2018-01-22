//Frame FB
//Frame HOME
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.io.*;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

class mini extends JFrame
{       
        public static Connection conn;
	private JFrame F1;
	JLabel us,pw;
	JButton li,ca,help,fp,enter;
	JTextField ust,usid,urls,pws;
        JTextField title,usname,urladd,muidt,caust,ans;
	JTextField pwd,cpwd;
        JPasswordField pwt;
        JPasswordField mupwdp;
        JPasswordField mucpwdp,capwd,cacpwd;
	JButton home,mng,view,lo,clp,cnfview,chngepwd;
	CheckboxGroup cbg;
	Checkbox add,mod,del;
	JComboBox box,box1;
        String selectdbox;
        JLabel tit,uid,upass,url;
	JTextArea msg;
        ResultSet Rs;
        String sql;
	public static String [] secqns;
        String datauid = null;
	public static int choice;
	public static String [] name;
        public static Statement sta = null;
	public static String titledb;
	public static String uiddb,userid;
	public static String pwddb;
	private JLabel statusLabel;
        private JLabel headerlabel;
	public mini()
	{
		prepareGUI();//initial login page
	}
	public static void main(String[] args)
	{
                mini m = new mini();
		choice=0;
		secqns  = new String[] {"What is your first pet ?","What is the first vehicle you drove?","What is your favourite colour?","What is your favourite dish?","What is your age?"};
		String [] choicename={"no choice","add pass","mod pass","del pass","view pass"};
		    
		System.out.println("Choice number :"+choice +"  Choice name:"+choicename[choice]);
		System.out.println("After object creation");
		try
		{
                    System.out.println("Before query");
                    Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
                    conn = DriverManager.getConnection("jdbc:odbc:mini");
                    sta = conn.createStatement(); 
		}
		catch(Exception ex)
                {
					
                    System.out.println(ex.getMessage());
                }           
	}

        private void prepareGUI()//This is for the main login frame
        {
            F1 = new JFrame("Password Wallet");
            F1.setSize(400,500);
            F1.setVisible(true);
            F1.setLayout(null);
			F1.getContentPane().setBackground(Color.RED);
            F1.setResizable(false); //p
            F1.getContentPane().setBackground(new Color(255,255,204)); //p
            F1.setIconImage(new ImageIcon("E:/PROJECTS/Password Wallet/modfiles/pwl.png").getImage()); //default icon
		//statusLabel = new JLabel("");  
            ImageIcon imageIcon = new ImageIcon("E:/PROJECTS/Password Wallet/modfiles/main.png"); //p
            statusLabel = new JLabel(imageIcon); //p
		statusLabel.setBounds(55,75,330,60);
            li = new JButton("Login");
            li.setBounds(120,260,100,25);
            li.setBackground(new Color(255, 255,153));
            ca = new JButton("Sign Up");
            ca.setBounds(250,260,100,25);
            ca.setBackground(new Color(255, 255,153));
			fp = new JButton("Forgot Password?");
			fp.setBounds(140,330,200,15);
			fp.setFocusPainted(false);
		fp.setBorder(null);   // jbutton border
		fp.setContentAreaFilled(false);
			fp.setBackground(new Color(255, 255,153));			
            help = new JButton("HELP ME");
            help.setBounds(180,400,100,25);
            help.setBackground(new Color(255, 255,153));
			ust = new JTextField(6);
            ust.setBounds(200,150,150,30);
            pwt = new JPasswordField(6);
            pwt.setBounds(200,190,150,30);
			
    	us = new JLabel("User Name:");
            us.setBounds(120,150,100,30);
            us.setForeground(Color.RED); //p
            pw = new JLabel("Password:");
            pw.setBounds(120,190,100,30);
            pw.setForeground(Color.RED); //p
			F1.add(help);
            F1.add(li);
            F1.add(ca);
            F1.add(ust);
            F1.add(pwt);
            F1.add(us);
            F1.add(pw);
            F1.add(fp);
    	    F1.add(statusLabel);
            F1.setVisible(true); 
            li.addActionListener(new ButtonClickListener()); 
            ca.addActionListener(new ButtonClickListener());
			help.addActionListener(new ButtonClickListener());
			fp.addActionListener(new ButtonClickListener());
            F1.repaint();
            F1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
                
	private class ButtonClickListener implements ActionListener,ItemListener
	{
            public void actionPerformed(ActionEvent ae)
            {
                String command = ae.getActionCommand();
                if(command.equals("Login"))
                {
                    try
                    {
                        String datapwd = new String(pwt.getPassword());                         
                        datauid = ust.getText(); 
                        String salt = null;
                        String sqlin="Select * from MAIN_TB";// where ID='"+datauid+"'";
                        ResultSet rs=sta.executeQuery(sqlin);
                        int found=0;
                        while(rs.next())
                        {                       
                            found=0;
                            String compdbid=null;
                            compdbid=rs.getString("UID");
                            if(datauid.equals(compdbid))
                            {
                                salt = datauid;
                                System.out.println("UID MATCHED.");
                                found++;
                                break;
                            }
				
                        }
				
		
			
                        if(found==1)
                        {
                            String securePassword = get_SHA_256_SecurePassword(datapwd, salt);
                            System.out.println("SHA-256:" + securePassword);  
                            sql="select PWD from MAIN_TB where UID='"+datauid+"'";                      
                            ResultSet rs2=sta.executeQuery(sql);                      
                            while(rs2.next())
                            {
                                String compdbpwd = rs2.getString("PWD");
                               if(securePassword.equals(compdbpwd))
                                { 
                                    System.out.println("PWD MATCHED.");
                                    HOME();         
                                    statusLabel.setText("satisfied");						   
                                }   
								else if(pwt.getPassword().length == 0)
			{
			JOptionPane.showMessageDialog(F1, "Password field is empty","Blank field",JOptionPane.WARNING_MESSAGE);
			}
								
                                else
                                {    
                                    JOptionPane.showMessageDialog(F1, "Entered Password is incorrect,Please Recheck it.",
                                    "Wrong Password", JOptionPane.WARNING_MESSAGE);
                                    statusLabel.setText("not satisfied");                    
                                }
								/*else if(securePassword.length == 0)
								{
								
								} */
                            }                
                        }
						else if(datauid.length() == 0)
							{
			 JOptionPane.showMessageDialog(F1, "Please Enter Your Username", "Blank Field",JOptionPane.WARNING_MESSAGE);
			  }
                        else 
                        {
                            JOptionPane.showMessageDialog(F1, "Entered UID is wrong,Please Recheck it.",
                            "UserId doesnt exist", JOptionPane.WARNING_MESSAGE);
                            System.out.println("UID doesnt exist.");                                                            
                        }
			
                    }
                    catch(Exception ex)
                    {
                        System.out.println("Exception arised in Login Action Listener");
                        System.out.println(ex.getMessage());
                    }
                }
                else if(command.equals("Sign Up"))
                {
                    CA();
                    statusLabel.setText("Create your account");
                    F1.remove(help);
					F1.remove(fp);
                } 
                else if(command.equals("View"))
                {
                    F1.getContentPane().removeAll();
                    HOME();
                    F1.add(statusLabel);
                    F1.remove(msg);
                    view();
                }
                else if(command.equals("Manage"))
                {
                    F1.getContentPane().removeAll();
                    HOME();
                    F1.add(statusLabel);
                    F1.remove(msg);
                    manage();
                }
                else if(command.equals("Log Out"))                    
                {
                    JOptionPane.showMessageDialog(F1, "Thank you,for using Password Wallet!",
                            "LOGOUT", JOptionPane.INFORMATION_MESSAGE);
                    F1.dispose();
                    prepareGUI();
                }
                else if(command.equals("Home"))
                {
                    F1.getContentPane().removeAll();
                    HOME();
                    F1.add(statusLabel);
                }
                else if(command.equals("CANCEL"))
                {
                    F1.getContentPane().removeAll();
                    F1.dispose();
                    prepareGUI();
                }
                else if(command.equals("SELECT"))
                {                    
                    System.out.println("enterd pwd:"+mupwdp.getText());
                    System.out.println("Modify clicked bro");
                    try
                    {
                        Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
                        conn=DriverManager.getConnection("jdbc:odbc:mini");
                        // Creating a database table
                        sta = conn.createStatement(); 
                        String ch="FB PWD";
                        sql = "select UID from "+datauid+" where Title='"+box.getSelectedItem()+"'";
                        ResultSet rs = sta.executeQuery(sql);
                        while(rs.next())
                        {
                            muidt.setText(rs.getString("UID"));                            
                        }
                        managechange();                       
                    }
                    catch(Exception ex)
                    {
                        System.out.println("Exception in Manage->Modify->Select:"+ex.getMessage());
                    }
                }
                else if(command.equals("Delete"))
                {
                    try
                    {
                        Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
                        conn=DriverManager.getConnection("jdbc:odbc:Mini");
                        // Creating a database table
                        sta = conn.createStatement();                         
                        sql = "delete from "+datauid+" where Title='"+box.getSelectedItem()+"'";
                        System.out.println(sql);                 
                        int response = JOptionPane.showConfirmDialog(F1,"Are you sure you want to Delete?","Confirm",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                        if (response == JOptionPane.NO_OPTION) 
                        {   
                            F1.getContentPane().removeAll();
                            HOME();
                            F1.remove(msg);
                            manage();
                            cbg.setSelectedCheckbox(del);
                            delpass();
                            statusLabel.setText("IN del");
                            System.out.println("No button clicked");
                        }
                        else if (response == JOptionPane.YES_OPTION) 
                        {
                            sta.executeUpdate(sql);
                            JOptionPane.showMessageDialog(F1, "Password Successfully Deleted.",
                                    "Password Deleted", JOptionPane.INFORMATION_MESSAGE);
							F1.getContentPane().removeAll();
                            HOME();
                            
							
                            
                        } 
                        else if (response == JOptionPane.CLOSED_OPTION) 
                        {
                            System.out.println("JOptionPane closed");
							F1.getContentPane().removeAll();
                    HOME();
                    F1.remove(msg);
                    manage();
                    cbg.setSelectedCheckbox(del);
                    delpass();
                    
                        }
                        System.out.println("Manage->Delete clicked bro");    
                    }
                    catch(Exception ex)
                    {
                        System.out.println("Exception in Manage->Modify->Delete:"+ex.getMessage());
                    }                    
                }
                else if(command.equals("CANCEL Operation"))
                {
                    F1.getContentPane().removeAll();
                    HOME();
                    F1.add(statusLabel);
                    F1.remove(msg);
                    manage();
                }
                else if(command.equals("ADD Entry"))
                {
                    System.out.println("Entered Here");
                    
                    try
                    {
                        String sqlin="Select TITLE from "+datauid;// where ID='"+datauid+"'";
                        ResultSet rs=sta.executeQuery(sqlin);
                        int found=0;
                        while(rs.next())
                        {                       
                            found=0;  
                            if(!((title.getText()).equals("")))
                            {
                                if(title.getText().equals(rs.getString("TITLE")))
                                {                                
                                    System.out.println("TITLE ALREADY EXISTS");
                                    JOptionPane.showMessageDialog(F1, "TITLE ALREADY EXISTS.",
                                            "TITLE EXISTS", JOptionPane.WARNING_MESSAGE);
                                    found++;
                                    break;
                                }
                            }
                            else
                            {
                                JOptionPane.showMessageDialog(F1, "TITLE FIELD CANNOT BE BLANK.",
                                            "TITLE NULL", JOptionPane.WARNING_MESSAGE); 
                                
                            }
                        }
                        if(found==0)
                        {
                            if(!((usname.getText()).equals(""))&&!((mupwdp.getText()).equals(""))&&!((urladd.getText()).equals("")))
                                {
                                
                            if((mupwdp.getText().equals(mucpwdp.getText())))
                            {
                                    AES callaes=new AES();    
                            //creating an object of AES class
                            String EPWD=mupwdp.getText();
                            EPWD=callaes.getPassword1(EPWD,0);
                            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
                            Connection conn=DriverManager.getConnection("jdbc:odbc:Mini");
                            // Creating a database table
                            Statement sta = conn.createStatement(); 
                            sql="insert into "+datauid+" values('"+title.getText()+"','"+urladd.getText()+"','"+usname.getText()+"','"+EPWD+"')";
                            sta.executeUpdate(sql);
                            JOptionPane.showMessageDialog(F1, "Password Record Added.",
                                    "Password Stored", JOptionPane.INFORMATION_MESSAGE);
                            F1.getContentPane().removeAll();
                            HOME();
                            F1.add(statusLabel);
                            }
							else
							{
								JOptionPane.showMessageDialog(F1, "Entered Password's do not match.",
                                    "Password Mismatch", JOptionPane.WARNING_MESSAGE); 
                            
							}
                            
                            }
                            else
                            {                                
									JOptionPane.showMessageDialog(F1, "Blank Fields are not Accepted.",
                                    "NULL values not accepted", JOptionPane.WARNING_MESSAGE);
                            }
                        }
                    }                
                        
                        /*else 
                        {
                            JOptionPane.showMessageDialog(F1, "Entered UID is wrong,Please Recheck it.",
                            "UserId doesnt exist", JOptionPane.WARNING_MESSAGE);
                            System.out.println("UID doesnt exist.");                                                            
                        }*/
                    
                        
                    
                    catch(Exception ex)
                    {
                        System.out.println("Exception");
                        System.out.println(ex.getMessage());
                    }
                }
                else if(command.equals("SUBMIT"))
                {
                    try
                    {
                        if(capwd.getText().equals(cacpwd.getText()))
                        {
                            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
                            Connection conn=DriverManager.getConnection("jdbc:odbc:Mini");
                            // Creating a database table
                            Statement sta = conn.createStatement(); 
                            String ch="FB PWD";
                            String datauid="CHECK";
                            String sql ="CREATE TABLE "+datauid+"(TITLE varchar(255) NOT NULL,URL varchar(255),UID varchar(255),PWD varchar(255),PRIMARY KEY (TITLE))";
                            System.out.println(sql);
                            //creating an object of AES class                            
                            System.out.println("ENTERD ACTION PERFORMED");
                            sta.executeUpdate(sql);
                            //JOptionPane.showMessageDialog(F1, "Password Successfully Modified.",
                            //      "Password Modified", JOptionPane.INFORMATION_MESSAGE);
                            F1.getContentPane().removeAll();
                        }   
                        else                           
                        {
                            JOptionPane.showMessageDialog(F1, "Entered Password's do not match!Please Re-enter.",
                                    "Password's Unmatched", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                    catch(Exception ex)
                    {
                        System.out.println("Error arised in Modify Confirm Action Listener Section.");
                        System.out.println(ex.getMessage());
                    }
                    System.out.println("Entered in SIGN UP->SUBMIT clicked");
                }
                else if(command.equals("MOD PWD"))
                {
                    try
                    {
						if(!((mupwdp.getText()).equals("")))
						{
							if(mupwdp.getText().equals(mucpwdp.getText())&&!((mupwdp.getText()).equals("")))
							{	
								Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
								conn=DriverManager.getConnection("jdbc:odbc:Mini");
								// Creating a database table
								sta = conn.createStatement(); 
								String ch="FB PWD";
								AES callaes=new AES();    
								//creating an object of AES class
								String EPWD=mupwdp.getText();
								EPWD=callaes.getPassword1(EPWD,0);
								sql = "update "+datauid+" set PWD ='"+EPWD+"' where TITLE='"+box.getSelectedItem()+"'" ;
								sta.executeUpdate(sql);
								JOptionPane.showMessageDialog(F1, "Password Successfully Modified.",
                                    "Password Modified", JOptionPane.INFORMATION_MESSAGE);
								F1.getContentPane().removeAll();
								HOME();							
							}
							else
							{
								JOptionPane.showMessageDialog(F1, "Entered Password's do not match!Please Re-enter.",
										"Password's Unmatched", JOptionPane.WARNING_MESSAGE);                                    
							}
						}	
						else
						{
							JOptionPane.showMessageDialog(F1, "Password Field cannot be NULL.",
										"Enter Password", JOptionPane.WARNING_MESSAGE);                                    
						}
                    }
                    catch(Exception ex)
                    {
                        System.out.println("Error arised in Modify Confirm Action Listener Section.");
                        System.out.println(ex.getMessage());
                    }               
                }
                else if(command.equals("CREATE"))
                {
                    System.out.println("CREATE");
                    try
                    {                        
                        datauid = caust.getText();   
                        System.out.println(caust.getText());
                        System.out.println("DATAID"+datauid);
                        String sqlin="Select * from MAIN_TB";// where ID='"+datauid+"'";
                        ResultSet rs=sta.executeQuery(sqlin);
                        int found=0;
                        while(rs.next())
                        {                       
                            found=0;
                            String compdbid=null;
                            compdbid=rs.getString("UID");
                            if(datauid.equals(compdbid))
                            {                                
                                System.out.println("UID MATCHED.");
                                found++;
                                break;
                            }
                        }
                        if(found==0)
                        {
							if((!(caust.getText()).equals(""))&&(!(capwd.getText()).equals(""))&&(!(ans.getText()).equals(""))&&((box.getSelectedIndex())!=0))
							{
                            if((capwd.getText().equals(cacpwd.getText())))
                            {
                            sql="CREATE TABLE "+datauid+" (TITLE varchar(50) NOT NULL,URL varchar(50) NOT NULL,UID varchar(50) NOT NULL,PWD varchar(255) NOT NULL,PRIMARY KEY (TITLE))";                 
                            System.out.println(sql);
                            sta.executeUpdate(sql);   
                            String salt=caust.getText();
                            String ecapwd=get_SHA_256_SecurePassword(capwd.getText(), salt);
                            
                            sql ="insert into MAIN_TB values('"+caust.getText()+"','"+ecapwd+"','"+box.getSelectedIndex()+"','"+ans.getText()+"')";
                            System.out.println(sql);
                            sta.executeUpdate(sql);     
							JOptionPane.showMessageDialog(F1, "Account Created!",
                                    "SIGN UP Successfull", JOptionPane.INFORMATION_MESSAGE);							
									F1.getContentPane().removeAll();
									F1.dispose();
									prepareGUI();
                            }
							
                            else 
                            {
                                JOptionPane.showMessageDialog(F1, "Entered Password's do not match.",
                                    "Password Mismatch", JOptionPane.WARNING_MESSAGE);                            
                            }
							}
							else
							{
								JOptionPane.showMessageDialog(F1, "Null Fields are not accepted.",
                                    "Blank Fields", JOptionPane.WARNING_MESSAGE);                            
							}
                        }                
                        else 
                        {
                            JOptionPane.showMessageDialog(F1, "Entered UID already exists.Please try another one",
                            "UserId exists", JOptionPane.WARNING_MESSAGE);                            
                        }
                        System.out.println(caust.getText());
                        System.out.println("Entered in try->CA->CREATE");
                        System.out.println(box.getSelectedIndex());
                        System.out.println(capwd.getText());
                        System.out.println(cacpwd.getText());
                        System.out.println(ans.getText());
                        
                    }
                    catch(Exception ex)
                    {
                        System.out.println("Error arised in SIGN UP->CREATE Action Listener Section.");
                        System.out.println(ex.getMessage());
                    }
                }
                else if(command.equals("Help")||command.equals("HELP ME"))
                {
                    System.out.println("ENTERED HELP");
                    F1.add(statusLabel);
                    help();	
                }
                else if(command.equals("Change Login Password"))
                {
                    F1.getContentPane().removeAll();
                    HOME();
                    F1.add(statusLabel);
                    F1.remove(msg);
                    change();	
                }
				else if(command.equals("Cancel Change"))
				{
					F1.getContentPane().removeAll();
                    HOME();
                    F1.add(statusLabel);
				}
				else if(command.equals("Forgot Password?"))
				{					
					F1.remove(li);
					F1.remove(us);
					F1.remove(pwt);
					F1.remove(pw);
					F1.remove(ust);
					F1.remove(ca);					
                    fpwd();
				}				
				else if(command.equals("ENTER"))
				{
					
					System.out.println("IN ENTER");
					try
					{
					int found1=0;
					System.out.println("US:"+ust.getText());
					sql="SELECT * from MAIN_TB";
					ResultSet rs=sta.executeQuery(sql);
					int qn=0;
					while(rs.next())
					{
						if((ust.getText()).equals(rs.getString("UID")))
						{
							found1++;
							datauid=ust.getText();							
						}
					}
					rs.close();
					sql="SELECT SEC_QN from MAIN_TB where UID='"+datauid+"'";
					rs=sta.executeQuery(sql);
					while(rs.next())
					{
						qn=rs.getInt("SEC_QN");								
					}
					System.out.println(qn);
					rs.close();
					if(found1==1)
					{
						fpwdnew(qn);						
					}
					else
					{
						JOptionPane.showMessageDialog(F1, "Entered UID does not exist.Please try another one",
                            "UserId false", JOptionPane.WARNING_MESSAGE);                            
							F1.remove(li);
					F1.remove(us);
					F1.remove(pwt);
					F1.remove(pw);
					F1.remove(ust);
					F1.remove(ca);					
                    fpwd();
					}
					
					
					}
					catch(Exception ex)
					{
						System.out.println("Exception arised in ForgotPWD-->UID ENTER\n"+ex.getMessage());
					}
				}
				else if(command.equals("Change Password"))
				{
					String securePassword=null;
					try
					{
					
                            System.out.println("PWD MATCHED.");
                            sql="select PWD from MAIN_TB where UID='"+datauid+"'";                      
                            ResultSet rs2=sta.executeQuery(sql);                      
                            while(rs2.next())
                            {
                                String compdbpwd = rs2.getString("PWD");								    
                                if(compdbpwd.equals(get_SHA_256_SecurePassword(mucpwdp.getText(), datauid)))
                                {                                     
								System.out.println("OLD PWD MATCHED.");
									if((capwd.getText().equals(cacpwd.getText()))&&(!(capwd.getText()).equals("")))
									{
										securePassword = get_SHA_256_SecurePassword(capwd.getText(), datauid);
										if(securePassword.equals(compdbpwd))
										{
											JOptionPane.showMessageDialog(F1, "New Password & Old Password cannot be the same.",
										"Similar Password's!", JOptionPane.WARNING_MESSAGE);
										F1.getContentPane().removeAll();
										HOME();
										F1.add(statusLabel);
										F1.remove(msg);
										change();		
										}
										else
										{
											sql="update MAIN_TB set PWD='"+securePassword+"' where UID='"+datauid+"'";
											sta.executeUpdate(sql);
											JOptionPane.showMessageDialog(F1, "Password Changed.",
										"Changed Login PWD", JOptionPane.INFORMATION_MESSAGE);
										F1.getContentPane().removeAll();
										HOME();
										F1.add(statusLabel);
										
											
											System.out.println(sql);
											System.out.println("SHA PWD NEW PWD:"+securePassword);
										}
									}
									else
									{
										if(capwd.getText().equals(""))
										{
											JOptionPane.showMessageDialog(F1, "New Password Cannot be blank.",
										"Blank New Password", JOptionPane.WARNING_MESSAGE);
										F1.getContentPane().removeAll();
										HOME();
										F1.add(statusLabel);
										F1.remove(msg);
										change();										
										}
										else
										{
											JOptionPane.showMessageDialog(F1, "Entered Password's do not match.",
										"Password Mismatch.", JOptionPane.WARNING_MESSAGE);
										F1.getContentPane().removeAll();
										HOME();
										F1.add(statusLabel);
										F1.remove(msg);
										change();										
										}
									}
                                }   
                                else
                                {    
                                    JOptionPane.showMessageDialog(F1, "Entered Old Password is incorrect,Please Recheck it.",
                                    "Wrong Password", JOptionPane.WARNING_MESSAGE);
									F1.getContentPane().removeAll();
										HOME();
										F1.add(statusLabel);
										F1.remove(msg);
										change();	
                                    
                                }
                            }
					}
					catch(Exception ex)
					{
						System.out.println(ex.getMessage());
					}
				}
				else if(command.equals("VERIFY"))
				{
					try
					{
						String answer=urls.getText();
						if(!(urls.getText()).equals(""))
						{
							sql="SELECT SEC_ANS from MAIN_TB where UID='"+datauid+"'";
							ResultSet rs=sta.executeQuery(sql);
							while(rs.next())
							{
								if((urls.getText()).equals(rs.getString("SEC_ANS")))
								{
									fpwdnew1();
									System.out.println("USER IS VALID");
								}
								else
								{	
									JOptionPane.showMessageDialog(F1, "Entered answer does not match.Please retry",
									"Wrong Answer.", JOptionPane.WARNING_MESSAGE);
									urls.setText("");
									System.out.println("USER IS INVALID");
								}
							
							}
						}
						else 
						{
							System.out.println("USER ANS IS BLANK");
						}
					}
					catch(Exception ex)
					{
						System.out.println("Error in FORGOT->ENTER->VERIFY:"+ex.getMessage());
					}
				}
				else if(command.equals("confirm password"))
				{
					try
                    {
						if(!((capwd.getText()).equals("")))
						{
							if(capwd.getText().equals(cacpwd.getText())&&!((cacpwd.getText()).equals("")))
							{	
								Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
								conn=DriverManager.getConnection("jdbc:odbc:Mini");
								String securePassword = get_SHA_256_SecurePassword(capwd.getText(), datauid);								
								sql="update MAIN_TB set PWD='"+securePassword+"' where UID='"+datauid+"'";
								sta.executeUpdate(sql);							
								JOptionPane.showMessageDialog(F1, "Password Successfully Modified.",
                                    "Password Modified", JOptionPane.INFORMATION_MESSAGE);
								F1.getContentPane().removeAll();
								F1.dispose();
								prepareGUI();
							}
							else
							{
								JOptionPane.showMessageDialog(F1, "Entered Password's do not match!Please Re-enter.",
										"Password's Unmatched", JOptionPane.WARNING_MESSAGE);                                    
										cacpwd.setText("");
										capwd.setText("");
							}
						}	
						else
						{
							JOptionPane.showMessageDialog(F1, "New Password Field cannot be NULL.",
										"Enter Password", JOptionPane.WARNING_MESSAGE);                                    
						}
                    }
                    catch(Exception ex)
                    {
                        System.out.println("Error arised in FORGOT PWD->confirm password-> SECTION");
                        System.out.println(ex.getMessage());
                    }            
				}
				else if(command.equals("BACK"))
				{
					F1.getContentPane().removeAll();
								F1.dispose();
								prepareGUI();
				}
			}
            public void itemStateChanged(ItemEvent ie)
            {
            	String secchoice = cbg.getSelectedCheckbox().getLabel();
            	if(secchoice=="Add Password")
            	{
                    F1.getContentPane().removeAll();
                    HOME();
                    F1.remove(msg);
					F1.add(statusLabel);
                    manage();
                    cbg.setSelectedCheckbox(add);
                    addpass();
                    
                }
                else if(secchoice=="Modify Password")
                {
                    F1.getContentPane().removeAll();
                    HOME();
                    F1.remove(msg);
                    manage();
					F1.add(statusLabel);
                    cbg.setSelectedCheckbox(mod);
                    modpass();
                    
                }
                else if(secchoice=="Delete Password")
                {
                    F1.getContentPane().removeAll();
                    HOME();
                    F1.remove(msg);
                    manage();
					F1.add(statusLabel);
                    cbg.setSelectedCheckbox(del);
                    delpass();
                    
                }
				
			}		
        }
		private void fpwdnew1()
		{
			li.setEnabled(false);
			capwd = new JPasswordField();
			capwd.setBounds(195,300,170,25);
			capwd.setText("");
			cacpwd = new JPasswordField();
			cacpwd.setBounds(195,330,170,25);
			F1.add(capwd);
			F1.add(cacpwd);
			us = new JLabel("New Password:");
			pw = new JLabel("Confirm New Password:");
			us.setBounds(55,300,150,25);
			pw.setBounds(55,330,150,25);
			ca = new JButton("confirm password");
			ca.setBounds(140,370,160,20);
            ca.setBackground(new Color(255, 255,153));
			ca.addActionListener(new ButtonClickListener());
			F1.add(ca);			
			F1.add(us);
			F1.add(pw);
			F1.repaint();
			
		}
		private void fpwdnew(int ch)
		{
			enter.setEnabled(false);
			JTextField secqnus=new JTextField();
			secqnus.setBounds(175,200,200,25);
			secqnus.setText(secqns[ch]);
			JLabel fplbl = new JLabel("Your Question is:");
			fplbl.setBounds(60,200,200,25);
			F1.add(fplbl);
			JLabel fplbla = new JLabel("Your Answer?");
			fplbla.setBounds(60,230,200,25);
			F1.add(fplbla);
			urls=new JTextField();//ANSWER FOR SEC_QN
			urls.setBounds(175,230,200,25);
			urls.setEditable(true);
			urls.setText("");
			F1.add(urls);		
			F1.add(secqnus);
			secqnus.setEditable(false);
			ust.setEditable(false);
			li = new JButton("VERIFY");
			li.setBounds(180,260,80,20);
            li.setBackground(new Color(255, 255,153));
			li.addActionListener(new ButtonClickListener());
			F1.add(li);
			F1.repaint();
			
		}
		private void fpwd()
		{
		
		F1.remove(fp);
		ust = new JTextField(60);
		us = new JLabel("User Name:");
		us.setBounds(100,140,100,20);
		F1.add(us);
		ust.setBounds(200,140,150,20);
		ust.setText("");
		F1.add(ust);
		enter = new JButton("ENTER");
		enter.setBounds(180,167,80,20);    
		enter.setBackground(new Color(255, 255,153));
		F1.add(enter);
		cnfview = new JButton("BACK");
		cnfview.setBounds(70,400,100,25);
        cnfview.setBackground(new Color(255, 255,153));
		cnfview.addActionListener(new ButtonClickListener());
		enter.addActionListener(new ButtonClickListener());
		F1.add(cnfview);
		System.out.println("IN FORGOT PWD");
		F1.repaint();
	}
	private void HOME() //Home Frame
	{
            F1.remove(help);
            F1.remove(li);
            F1.remove(ca);
            F1.remove(ust);
            F1.remove(pwt);
            F1.remove(us);
            F1.remove(pw);
			F1.remove(fp);
			F1.add(statusLabel);
            home = new JButton("Home",new ImageIcon("E:/PROJECTS/Password Wallet/modfiles/h.png"));//p
		home.setBounds(0,140,150,25);
		home.setBackground(new Color(255, 255,153));
		mng = new JButton("Manage",new ImageIcon("E:/PROJECTS/Password Wallet/modfiles/m.jpg"));//p
		mng.setBounds(135,140,140,25);
		mng.setBackground(new Color(255, 255,153));
		view= new JButton("View",new ImageIcon("E:/PROJECTS/Password Wallet/modfiles/v.png"));//p
		view.setBounds(270,140,150,25);
		view.setBackground(new Color(255, 255,153));
		lo= new JButton("Log Out",new ImageIcon("E:/PROJECTS/Password Wallet/modfiles/lg.png"));//p
		lo.setBounds(355,20,50,45);
		lo.setFocusPainted(false);
		lo.setBorder(null);   // jbutton border
		lo.setContentAreaFilled(false); //jbutton border
		help = new JButton("Help");
		help.setBounds(250,425,100,25);
		help.setBackground(new Color(255, 255,153));
		clp = new JButton("Change Login Password");
		clp.setBounds(10,425,180,25);
		clp.setBackground(new Color(255, 255,153));
		msg = new JTextArea();
		msg.setBounds(22,200,360,250);
		msg.setBackground(new Color(255,255,204));
		msg.setEditable(false);
		msg.setText("Welcome!!  \n \n This is your \n personalized account  \n to store passwords. \n \n \n To understand how to use Password Wallet click \n HELP. ");
		msg.setFont( new Font("Times New Roman", Font.BOLD,16));
		JTextPane pane = new JTextPane ();
            F1.add(home);
            F1.add(mng);
            F1.add(view);
            F1.add(lo);
            F1.add(clp);
            F1.add(help);
			F1.add(pane);
            F1.add(msg);
            clp.addActionListener(new ButtonClickListener());
		help.addActionListener(new ButtonClickListener());	
            mng.addActionListener(new ButtonClickListener());
            view.addActionListener(new ButtonClickListener());
            home.addActionListener(new ButtonClickListener());
            lo.addActionListener(new ButtonClickListener());
            F1.setVisible(true);
            F1.repaint();
            F1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
        public void CA() //CREATEOUNTountount Form
	{
            JLabel uid,lipwd,cnpwd,secqn,secans;
            JButton submit,cancel;
            F1.remove(help);
            F1.remove(li);
            F1.remove(ca);
            F1.remove(ust);
            F1.remove(pwt);
            F1.remove(us);
            F1.remove(pw);
            submit = new JButton("CREATE");            
        submit.setBounds(70,350,150,25);
		submit.setBackground(new Color(255, 255,153));
        cancel = new JButton("CANCEL");
        cancel.setBounds(240,350,120,25);
		cancel.setBackground(new Color(255, 255,153));
        uid = new JLabel("Enter UserName :");
        uid.setBounds(20,155,100,30);
		uid.setForeground(Color.RED);
        lipwd = new JLabel("Enter Login Password :");
        lipwd.setBounds(20,190,150,30);
		lipwd.setForeground(Color.RED);
        cnpwd = new JLabel("Confirm Login Password :");
        cnpwd.setBounds(20,225,150,30);
		cnpwd.setForeground(Color.RED);
        secqn = new JLabel("Choose Security Question :");
        secqn.setBounds(20,265,180,30);
		secqn.setForeground(Color.RED);
        secans = new JLabel("Enter Security Answer :");
        secans.setBounds(20,305,170,30);
		secans.setForeground(Color.RED);
            F1.add(submit);
            F1.add(cancel);
            F1.add(uid);
            F1.add(lipwd);
            F1.add(cnpwd);
            F1.add(secqn);
            F1.add(secans);
            F1.setVisible(true); 
            caust = new JTextField(20);
            caust.setBounds(200,160,140,20);
            caust.setText(null);
            capwd = new JPasswordField();
            capwd.setBounds(200,195,140,20);
            cacpwd = new JPasswordField();
            cacpwd.setBounds(200,230,140,20);
            ans = new JTextField(100);
            ans.setBounds(200,310,140,20);
                        
						caust.setText("");
						capwd.setText("");
						cacpwd.setText("");
						ans.setText("");

            box = new JComboBox();
			box.addItem("");
            box.addItem(secqns[0]);
            box.addItem(secqns[1]);
			box.addItem(secqns[2]);
			box.addItem(secqns[3]);
			box.addItem(secqns[4]);
			box.setBounds(180,270,200,25);
            //box.addItemListener(this);
            submit.addActionListener(new ButtonClickListener());
            cancel.addActionListener(new ButtonClickListener());
            F1.add(box);
            F1.add(caust);
            F1.add(capwd);
            F1.add(cacpwd);
            F1.add(ans);
            F1.repaint();
            F1.setVisible(true);
            F1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
        private void help()
	{
                JFrame Fh;
		JTextArea msg1;
		Fh = new JFrame("Help");
		Fh.setSize(710,400);
		Fh.setResizable(false);
		Fh.getContentPane().setBackground(Color.RED);//p
		Fh.setIconImage(new ImageIcon("C:/Users/Raghu/Desktop/45454/modfiles/pwl.jpg").getImage()); //p
		Fh.setVisible(true);
		Fh.setLayout(null);
		msg1 = new JTextArea();
		msg1.setBounds(00,00,1000,400);
		msg1.setForeground(Color.BLUE);//p
		msg1.setEditable(false);
		msg1.setFont( new Font("Courier", Font.BOLD,16));//p
		msg1.setText(" Welcome to password wallet \n 1.If you are not an existing user then sign up to password wallet \n  by entering required details\n 2. After sign up, login to the application by giving the login ID \n  and password. \n 3. User can manage his passwords by using ADD/MODIFY/DELETE buttons. \n --> Add button is for adding new passwords. \n --> Modify is for changing exiting passwords. \n --> Delete button is for deleting existing passwords. \n 4.View button retrieves the password of the user' choice. \n 5. Change Login Pass changes the login password of the user. \n 6. Log Out ");
		Fh.add(msg1);
		
	}
        private void addpass() //Addpasswrd chckbox
	{
            F1.remove(msg);
            //add a = new add();
            JButton addbutton,cancelbutton;
            JTextField pwd,cpwd,url;
            JLabel ltitle,lusname,lpwd,lcpwd,lurl;
            ltitle = new JLabel("TITLE :");
		ltitle.setBounds(70,200,100,30);
		ltitle.setForeground(Color.RED);
		lusname = new JLabel("User ID :");
		lusname.setBounds(70,230,100,30);
		lusname.setForeground(Color.RED);
		lpwd = new JLabel ("Password :");
		lpwd.setBounds(70,260,120,30);
		lpwd.setForeground(Color.RED);
		lcpwd = new JLabel ("Confirm Password :");
		lcpwd.setBounds(70,290,160,30);
		lcpwd.setForeground(Color.RED);
		lurl = new JLabel ("URL :");
		lurl.setBounds(70,320,100,30);
		lurl.setForeground(Color.RED);
            F1.add(ltitle);
            F1.add(lusname);
            F1.add(lpwd);
            F1.add(lcpwd);
            F1.add(lurl);
            title=new JTextField(200);
            usname= new JTextField(100);
            mupwdp= new JPasswordField();
            mucpwdp = new JPasswordField();
            urladd = new JTextField(100);
            title.setBounds(200,200,150,30);
            usname.setBounds(200,230,150,30);
            mupwdp.setBounds(200,260,150,30);
            mucpwdp.setBounds(200,290,150,30);
            urladd.setBounds(200,320,150,30);
            F1.add(title);
            F1.add(usname);
            F1.add(mupwdp);
            F1.add(mucpwdp);
            F1.add(urladd);
			title.setText("");
			usname.setText("");
                        mupwdp.setText("");
			urladd.setText("");
            addbutton = new JButton ("ADD Entry");
            addbutton.setBounds(45,370,140,22);   
            addbutton.setBackground(new Color(255, 255,153));
            addbutton.addActionListener(new ButtonClickListener());
            cancelbutton = new JButton ("CANCEL Operation");
            cancelbutton.setBounds(205,370,140,22);
            cancelbutton.setBackground(new Color(255, 255,153));
            cancelbutton.addActionListener(new ButtonClickListener());
            F1.add(addbutton);
            F1.add(cancelbutton);
            F1.setVisible(true);
            F1.repaint();
            //F1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}   
        private void delpass() //Delpass chckbox
	{
            F1.remove(msg);
            JLabel maittl = new JLabel ("Choose which password to delete : ");
            maittl.setBounds(115,195,300,20);
            		maittl.setForeground(Color.RED);

            F1.add(maittl);
            JLabel Title = new JLabel ( "TITLE :");
            Title.setBounds(70,220,80,20);
            		Title.setForeground(Color.RED);

            F1.add(Title);
            box = new JComboBox();
            box.addItem("");
            extrTitle();
            box.setBounds(115,220,200,20);
            F1.add(box);
            JButton Cnfdel = new JButton ("Delete");
            Cnfdel.setBounds(175,250,80,25);
			Cnfdel.setBackground(new Color(255, 255,153));
            Cnfdel.addActionListener(new ButtonClickListener());
            F1.add(Cnfdel);
            F1.setVisible(true);
            F1.repaint();
            F1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}	
	private void modpass() //Modpass chckbox
	{
            F1.remove(msg);
            JLabel maittl = new JLabel ("Choose which password to modify : ");
            maittl.setBounds(115,195,300,20);
            		maittl.setForeground(Color.RED);

            F1.add(maittl);
            JLabel Title = new JLabel ( "TITLE :");
            Title.setBounds(70,220,80,20);
            		Title.setForeground(Color.RED);

            F1.add(Title);
            box = new JComboBox();
            box.addItem("");
            System.out.println("Bro here in modpass");
            extrTitle();
            box.setBounds(115,220,200,20);
            F1.add(box);
            JButton CnfMod = new JButton ("SELECT");
            CnfMod.setBounds(175,250,80,25);
            CnfMod.addActionListener(new ButtonClickListener());
            		CnfMod.setBackground(new Color(255, 255,153));

            JLabel muid = new JLabel("USER ID:");
            muid.setBounds(90,280,80,20);
            JLabel mpwd = new JLabel("Enter New Password:");
            mpwd.setBounds(90,305,150,20);
            JLabel mcpwd = new JLabel("Confirm New Password:");
            mcpwd.setBounds(90,330,150,20);
			F1.add(muid);
            F1.add(mpwd);
            F1.add(mcpwd);
            muidt = new JTextField();
            muidt.setBounds(250,280,120,20);
            F1.add(muidt);
            muidt.setEditable(false);
            mupwdp = new JPasswordField();
            mupwdp.setBounds(250,305,120,20);
            mucpwdp = new JPasswordField();
            mucpwdp.setBounds(250,330,120,20);
			mupwdp.setText("");
            F1.add(mupwdp);
            F1.add(mucpwdp);
            F1.add(CnfMod);
            F1.setVisible(true);
            F1.repaint();
            F1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public void view() //View Frame
	{
            F1.remove(msg);
	    tit = new JLabel("Select Title :");
            tit.setBounds(50,180,100,30);
            tit.setForeground(Color.RED);

            uid = new JLabel("Your ID is :");
            uid.setBounds(50,240,100,30);
            		uid.setForeground(Color.RED);

            upass = new JLabel("Your Password is :");
            upass.setBounds(50,280,150,30);		upass.setForeground(Color.RED);

            url = new JLabel("URL :");
            url.setBounds(50,320,150,30);
            		url.setForeground(Color.RED);

            F1.add(tit);
            F1.add(uid);
            F1.add(upass);
            F1.add(url);
            F1.setVisible(true); 
            box = new JComboBox();
            System.out.println("Before try in IN");
            userid=	ust.getText();
            System.out.println("123");
            System.out.println(userid);
            cnfview = new JButton("View Password");
            cnfview.setBounds(140, 210, 125, 20);
			cnfview.setBackground(new Color(255, 255,153));
            F1.add(cnfview);
            cnfview.addActionListener(new ButtonListener() );
            box.addItem("");
            box.setBounds(200,185,150,20);   
            //box.addItemListener(this);
            try
            {
                System.out.println("Bro here in extrTitle");
                String sql = "select * from "+datauid;
		System.out.println("Bro here");
		ResultSet rs1 = sta.executeQuery(sql);
                while(rs1.next())
		{
                    System.out.println(rs1);
                    String title = rs1.getString("TITLE");
                    System.out.println(title);
                    box.addItem(title);
		}
            }
            catch (Exception ex)
            {
                System.out.println(ex.getMessage());
            }            
            usid = new JTextField(100);
            usid.setBounds(200,245,150,20);
            
            pws = new JTextField(100);
            pws.setBounds(200,285,150,20);
            urls = new JTextField(100);
            urls.setBounds(200,325,150,20);
            F1.add(box);
            F1.add(usid);
            F1.add(pws);
            F1.add(urls);
            usid.setEditable(false);
            //pwd.setEditable(false);        
            urls.setEditable(false);            
            
            
            F1.repaint();
            F1.setVisible(true);
            F1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	private class ButtonListener implements ActionListener
        {
            public void actionPerformed(ActionEvent ae)
            {
                String command = ae.getActionCommand();
                if(command.equals("View Password"))
                {                    
                    System.out.println("Selected View Password.");
                    try
                    {    
                        Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
                        conn=DriverManager.getConnection("jdbc:odbc:Mini");
                        // Creating a database table
                        sta = conn.createStatement(); 
                        String ch="FB PWD";
                        sql = "select * from "+datauid+" where Title='"+box.getSelectedItem()+"'";
                        ResultSet rs = sta.executeQuery(sql);
                        while(rs.next())
                        {
                            System.out.println("entered in BL:");
                            usid.setText(rs.getString("UID"));
                            AES callaes=new AES();    
                            //creating an object of AES class
                            //String ch1=callaes.getPassword1(rs.getString("PWD"),1);
                            // calling getPassword function of AES class
                            //System.out.println("Decrypted Pwd:"+ch1);
                            pws.setText(callaes.getPassword1(rs.getString("PWD"),1));
                            urls.setText(rs.getString("URL"));
                        }
                    }
                    catch(Exception ex)
                    {   System.out.println("error in view pass action listerner");
                        System.out.println(ex.getMessage());
                    }
                }
            }
        }
        private void manage() //Manage Frame
	{	
            F1.remove(msg);
            cbg = new CheckboxGroup();
            add = new Checkbox("Add Password",cbg,false);
            mod = new Checkbox("Modify Password",cbg,false);
            del = new Checkbox("Delete Password",cbg,false);
            add.setBounds(30,175,100,15);
            mod.setBounds(140,175,115,15);
            del.setBounds(260,175,120,15);
            add.setForeground(Color.RED);
		mod.setForeground(Color.RED);
		del.setForeground(Color.RED);
            add.addItemListener(new ButtonClickListener());
            mod.addItemListener(new ButtonClickListener());
            del.addItemListener(new ButtonClickListener());	
            F1.add(add);
            F1.add(mod);
            F1.add(del);
            F1.repaint();
            F1.setVisible(true);
            F1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public void pullThePlug() 
	{
    // this will make sure WindowListener.windowClosing() et al. will be called.
		WindowEvent wev = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
		Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(wev);
    // this will hide and dispose the frame, so that the application quits by
    // itself if there is nothing else around. 
		F1.setVisible(false);
		F1.dispose();
                try
                {
                sta.close(); 
                System.out.println("sta closed");
		conn.close();
                System.out.println("conn closed");    
                }
                catch(Exception ex)
                {
                    System.out.println("Error in pull the plug:"+ex.getMessage());
                }
    // if you have other similar frames around, you should dispose them, too.
    // finally, call this to really exit. 
    // i/o libraries such as WiiRemoteJ need this. 
    // also, this is what swing does for JFrame.EXIT_ON_CLOSE
		System.exit(0); 
	}
    public void extrTitle()
    {
            try
            {
                System.out.println("Bro here in extrTitle");
                String sql = "select * from "+datauid;
		System.out.println("Bro here");
		ResultSet rs1 = sta.executeQuery(sql);
                while(rs1.next())
		{
                    System.out.println(rs1);
                    String title = rs1.getString("TITLE");
                    System.out.println(title);
                    box.addItem(title);
		}
            }
		catch (Exception ex)
                {
                    System.out.println(ex.getMessage());
                }
        }
    public void managechange()
    {
            chngepwd = new JButton ("MOD PWD");
            chngepwd.setBounds(165,370,120,25);
			chngepwd.setBackground(new Color(255, 255,153));
            F1.add(chngepwd);
            F1.repaint();
            chngepwd.addActionListener(new ButtonClickListener());                
    }
 
	public void change() // change login password
	{
		
		JLabel uid,lipwd,cnpwd;
        JButton submit,cancel;
        submit = new JButton("Change Password");
        submit.setBounds(50,350,150,25);
		submit.setBackground(new Color(255, 255,153));
		cancel = new JButton("Cancel Change");
        cancel.setBounds(220,350,150,25);
		cancel.setBackground(new Color(255, 255,153));
        uid = new JLabel("Enter Old Password:");
        uid.setBounds(50,200,150,30);
		uid.setForeground(Color.RED);
        lipwd = new JLabel("Enter New Password :");
        lipwd.setBounds(50,240,150,30);
		lipwd.setForeground(Color.RED);
        cnpwd = new JLabel("confirm password :");
        cnpwd.setBounds(50,280,150,30);
		cnpwd.setForeground(Color.RED);
        F1.add(submit);
		F1.add(cancel);
		F1.add(uid);
		F1.add(lipwd);
		F1.add(cnpwd);
		F1.setVisible(true); 		
		mucpwdp = new JPasswordField();
		mucpwdp.setBounds(190,205,150,20);
		capwd = new JPasswordField();
		capwd.setText("");
		capwd.setBounds(190,245,150,20);
		cacpwd = new JPasswordField();
		cacpwd.setBounds(190,285,150,20);
		cacpwd.setText("");
		cancel.addActionListener(new ButtonClickListener());
		submit.addActionListener(new ButtonClickListener());
		F1.add(mucpwdp);
		F1.add(cacpwd);
		F1.add(capwd);
		F1.repaint();
		F1.setVisible(true);
		F1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
	}
    private static String get_SHA_256_SecurePassword(String passwordToHash, String salt)
    {
        //Use MessageDigest md = MessageDigest.getInstance("SHA-256");
		String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt.getBytes());
            byte[] bytes = md.digest(passwordToHash.getBytes());
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } 
        catch (NoSuchAlgorithmException e) 
        {
            e.printStackTrace();
        }
        return generatedPassword;
    }
	
}


