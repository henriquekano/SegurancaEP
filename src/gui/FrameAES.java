package src.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import src.algorithms.AES;
import src.algorithms.utils.Utils;

public class FrameAES extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JButton bt_generateIV = null;
	private JButton bt_generateKey = null;
	private JButton bt_encrypt = null;
	private JButton bt_decrypt = null;
	private JLabel jLabel_plantext;
	private JLabel jLabel_cyphertext;
	private JLabel jLabel_mode;
	
	private JPanel jPanel_Input = null;
	
	private JTextArea ta_iv;
	private JTextArea ta_key;
	private JTextArea ta_plantext;
	private JTextArea ta_cyphertext;
	private JComboBox cbox_mode;
	
	private static final String[] OPERATION_MODES = {"ECB", "CBC", "CTR"};
	
	private Map<String, AES.OpMode> operationModesMap;
	/**
	 * This is the default constructor
	 */
	public FrameAES() {
		super();
		initialize();
	}
	
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.operationModesMap = new HashMap<String, AES.OpMode>();
		this.operationModesMap.put("ECB", AES.OpMode.ECB);
		this.operationModesMap.put("CBC", AES.OpMode.CBC);
		this.operationModesMap.put("CTR", AES.OpMode.CTR);
		
		
		
		this.setSize(620, 500);
		this.setContentPane(getJContentPane());
		this.setTitle("AES Encryption");
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getBt_encrypt(), null);
			jContentPane.add(getBt_decrypt(), null);
			jContentPane.add(getJPanel_Input(), null);

			jLabel_mode = new JLabel();
			jLabel_mode.setBounds(22, 402, 66, 28);
			jContentPane.add(jLabel_mode);
			jLabel_mode.setFont(new Font("Tahoma", Font.BOLD, 14));
			jLabel_mode.setText("Mode:");
			jContentPane.add(getCbox_mode());
						
		}
		return jContentPane;
	}
	
	private void aes_generateIV() {
		 SecureRandom random = new SecureRandom();
		 String initializationVector = new BigInteger(128, random).toString(32);
		 
		 System.out.println(initializationVector.length());
		 JTextArea ivTextArea = getTa_iv();
		 
		 ivTextArea.setText(Utils.toHexString(initializationVector));
	}


	private void aes_generateKey() {
		SecureRandom random = new SecureRandom();
		String key = new BigInteger(128, random).toString(32);
		 
		JTextArea keyTextArea = getTa_key();
		 
		keyTextArea.setText(Utils.toHexString(key));
	}

	
	private void aes_encrypt() {
		JTextArea messageTextArea = getTa_plantext();
		JTextArea keyTextArea = getTa_key();
		JTextArea encryptedMessageTextArea = getTa_cyphertext();
		JTextArea initializationVectorTextArea = getTa_iv();
		JComboBox<String> operationModeCombo = getCbox_mode();
		
		if(!messageTextArea.getText().isEmpty() && !keyTextArea.getText().isEmpty()){
			
			if(operationModeCombo.getSelectedItem().equals("CBC") && initializationVectorTextArea.getText().isEmpty()){
				JOptionPane.showMessageDialog(null, "No modo CBC, escreva um IV!");
				return;
			}
			
			String hexEncryptedMessage = AES.encrypt(
					messageTextArea.getText(), 
					keyTextArea.getText(), 
					operationModesMap.get((String)operationModeCombo.getSelectedItem()), 
					AES.PaddingType.NIST,
					initializationVectorTextArea.getText()
			);
			
			encryptedMessageTextArea.setText(hexEncryptedMessage);
		}else{
			JOptionPane.showMessageDialog(null, "Escreva uma chave e uma mensagem!");
		}
	}


	private void aes_decrypt() {
		JTextArea messageTextArea = getTa_plantext();
		JTextArea keyTextArea = getTa_key();
		JTextArea encryptedMessageTextArea = getTa_cyphertext();
		JTextArea initializationVectorTextArea = getTa_iv();
		JComboBox<String> operationModeCombo = getCbox_mode();
		
		if(!encryptedMessageTextArea.getText().isEmpty() && !keyTextArea.getText().isEmpty()){
			
			if(operationModeCombo.getSelectedItem().equals("CBC") && initializationVectorTextArea.getText().isEmpty()){
				JOptionPane.showMessageDialog(null, "No modo CBC, escreva um IV!");
				return;
			}
			
			String hexDecryptedMessage = AES.decrypt(
					encryptedMessageTextArea.getText(), 
					keyTextArea.getText(), 
					operationModesMap.get((String)operationModeCombo.getSelectedItem()), 
					AES.PaddingType.NIST,
					initializationVectorTextArea.getText()
			);
			
			messageTextArea.setText(hexDecryptedMessage);
		}else{
			JOptionPane.showMessageDialog(null, "Escreva uma chave e uma mensagem encriptada!");
		}
	}

	
	/**
	 * This method initializes bt_generateIV
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBt_generateIV() {
		if (bt_generateIV == null) {
			bt_generateIV = new JButton();
			bt_generateIV.setBounds(7, 45, 134, 28);
			bt_generateIV.setText("Generate IV:");
			bt_generateIV.setHorizontalAlignment(SwingConstants.LEADING);
			bt_generateIV.setFont(new Font("Tahoma", Font.BOLD, 14));
			bt_generateIV.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					aes_generateIV();
				}
			});
		}
		return bt_generateIV;
	}

	
	/**
	 * This method initializes bt_generateKey
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBt_generateKey() {
		if (bt_generateKey == null) {
			bt_generateKey = new JButton();
			bt_generateKey.setBounds(7, 92, 134, 28);
			bt_generateKey.setText("Generate Key:");
			bt_generateKey.setHorizontalAlignment(SwingConstants.LEADING);
			bt_generateKey.setFont(new Font("Tahoma", Font.BOLD, 14));
			bt_generateKey.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					aes_generateKey();
				}
			});
		}
		return bt_generateKey;
	}

	
	/**
	 * This method initializes bt_encript
	 * 	
	 * @return javax.swing.JButton	
	 */	
	private JButton getBt_encrypt() {
		if (bt_encrypt == null) {
			bt_encrypt = new JButton();
			bt_encrypt.setFont(new Font("Tahoma", Font.BOLD, 14));
			bt_encrypt.setBounds(new Rectangle(242, 402, 113, 29));
			bt_encrypt.setText("Encrypt");
			bt_encrypt.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					aes_encrypt();
				}
			});
		}
		return bt_encrypt;
	}

	
	/**
	 * This method initializes bt_decript	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBt_decrypt() {
		if (bt_decrypt == null) {
			bt_decrypt = new JButton();
			bt_decrypt.setFont(new Font("Tahoma", Font.BOLD, 14));
			bt_decrypt.setBounds(new Rectangle(452, 402, 113, 29));
			bt_decrypt.setText("Decrypt");
			bt_decrypt.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					aes_decrypt();
				}
			});
		}
		return bt_decrypt;
	}
	
	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel_Input() {
		if (jPanel_Input == null) {
			jPanel_Input = new JPanel();
			jPanel_Input.setBorder(new TitledBorder(new LineBorder(new Color(171, 173, 179)), "AES Parametes", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
			jPanel_Input.setToolTipText("");
			jPanel_Input.setLayout(null);
			jPanel_Input.setBounds(new Rectangle(12, 25, 578, 354));

			jLabel_plantext = new JLabel();
			jLabel_plantext.setFont(new Font("Tahoma", Font.BOLD, 14));
			jLabel_plantext.setBounds(12, 158, 81, 28);
			jPanel_Input.add(jLabel_plantext);
			jLabel_plantext.setText("Plain Text:");
			
			jLabel_cyphertext = new JLabel();
			jLabel_cyphertext.setFont(new Font("Tahoma", Font.BOLD, 14));
			jLabel_cyphertext.setBounds(12, 259, 170, 28);
			jPanel_Input.add(jLabel_cyphertext);
			jLabel_cyphertext.setText("Cypher Text (hex):");
			
			jPanel_Input.add(getTa_iv());
			jPanel_Input.add(getTa_key());
			jPanel_Input.add(getTa_plantext());
			jPanel_Input.add(getTa_cyphertext());
			jPanel_Input.add(getBt_generateKey());
			jPanel_Input.add(getBt_generateIV());
			
		}
		return jPanel_Input;
	}
	
	private JTextArea getTa_iv() {
		if (ta_iv == null) {
			ta_iv = new JTextArea();
			ta_iv.setLineWrap(true);
			ta_iv.setFont(new Font("Tahoma", Font.PLAIN, 12));
			ta_iv.setBounds(153, 45, 413, 28);
		}
		return ta_iv;
	}
	
	private JTextArea getTa_key() {
		if (ta_key == null) {
			ta_key = new JTextArea();
			ta_key.setLineWrap(true);
			ta_key.setFont(new Font("Tahoma", Font.PLAIN, 12));
			ta_key.setBounds(153, 92, 413, 28);
		}
		return ta_key;
	}
	
	private JTextArea getTa_plantext() {
		if (ta_plantext == null) {
			ta_plantext = new JTextArea();
			ta_plantext.setLineWrap(true);
			ta_plantext.setFont(new Font("Tahoma", Font.PLAIN, 12));
			ta_plantext.setBounds(153, 133, 413, 90);
		}
		return ta_plantext;
	}
	
	private JTextArea getTa_cyphertext() {
		if (ta_cyphertext == null) {
			ta_cyphertext = new JTextArea();
			ta_cyphertext.setLineWrap(true);
			ta_cyphertext.setFont(new Font("Tahoma", Font.PLAIN, 12));
			ta_cyphertext.setBounds(153, 236, 413, 90);
		}
		return ta_cyphertext;
	}


	private JComboBox getCbox_mode() {
		if (cbox_mode == null) {
			cbox_mode = new JComboBox();
			cbox_mode.setModel(new DefaultComboBoxModel(FrameAES.OPERATION_MODES));
			cbox_mode.setFont(new Font("Tahoma", Font.BOLD, 14));
			cbox_mode.setBounds(100, 402, 73, 29);
		}
		return cbox_mode;
	}
}  //  @jve:decl-index=0:visual-constraint="10,10"
