import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class UI {

	static final int MAXWIDTH = 50;
	
	static int option1 = 0;
	static int option2 = 0;
	static JFrame myFrame;
		static JPanel top;
		static JPanel top1;
		static JPanel top3;
			static JButton mode_fill;
			static JButton mode_dress;
			static JTextPane txt_example;
			static JTextField fill;
			static JTextField size;
//		static JPanel center;
			static JTextArea input;
//		static JPanel bottom;
			static JButton generate;
	
	public static void main(String[] args){
		initComponents();
		initClickListener();
	}
	
	static final String align1 = "Align Center";
	static final String align2 = "Align Left";
	static final String align3 = "Align Right";
	static final String fill1 = "Fill, Include Line";
	static final String fill2 = "Non-Fill, Include Line";
	static final String fill3 = "Fill";
	static final String fill4 = "Non-Fill";
	static final String txtexample0 = "============";
	static final String txtexample11 = "====test====\n";
	static final String txtexample12 = "==test======\n";
	static final String txtexample13 = "======test==\n";
	static final String txtexample21 = "    test    \n";
	static final String txtexample22 = "  test      \n";
	static final String txtexample23 = "      test  \n";
	//v 1.1 : 버그픽스
	//v 1.2 : maxwidth 크기 변경
	//v 1.3 : 비쥬얼 제공
	public static void initComponents(){
		myFrame = new JFrame("Comment Generator V1.3 - By Won-woo (www.NoizBuster.com)");
		myFrame.setSize(600, 300);
		myFrame.setLayout(new BorderLayout());
			top = new JPanel(new GridLayout(1,3));
			top1 = new JPanel(new GridLayout(2,1));
			top3 = new JPanel(new GridLayout(2,2));
				mode_dress = new JButton(align1);
				mode_fill = new JButton(fill1);
				size = new JTextField("50");
				size.setHorizontalAlignment(JTextField.CENTER);
				fill = new JTextField("=");
				fill.setHorizontalAlignment(JTextField.CENTER);
				top1.add(mode_dress);
				top1.add(mode_fill);
				JLabel ls = new JLabel("Symbol : ");
				ls.setHorizontalAlignment(JTextField.RIGHT);
				top3.add(ls);
				top3.add(fill);
				JLabel lz = new JLabel("Size : ");
				lz.setHorizontalAlignment(JTextField.RIGHT);
				top3.add(lz);
				top3.add(size);

			top.add(top1);
			txt_example = new JTextPane();
			txt_example.setFont(new Font("Monospaced", 0, 15));
			txt_example.setAlignmentX(JTextPane.CENTER_ALIGNMENT);
			try {
				SimpleAttributeSet attrs=new SimpleAttributeSet();
				StyleConstants.setAlignment(attrs,StyleConstants.ALIGN_CENTER);
				StyledDocument doc=(StyledDocument)txt_example.getDocument();
				doc.insertString(0,"test",attrs);
				doc.setParagraphAttributes(0,doc.getLength()-1,attrs,false);
				}
				catch (Exception ex) {
				ex.printStackTrace();
				}
			updateExam();
			top.add(txt_example);
			top.add(top3);
			myFrame.add(top, "North");
				input = new JTextArea();
				input.setAlignmentX(JTextArea.RIGHT_ALIGNMENT);
				input.setAlignmentY(JTextArea.RIGHT_ALIGNMENT);
			myFrame.add(input,"Center");
				generate = new JButton("Generate!");
				
			myFrame.add(generate, "South");
		myFrame.setVisible(true);
	}
	
	public static void initClickListener(){
		myFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
		
		//정렬 모드 결정
		//mode_dress : option1
		//0 : Align Center
		//1 : Align Left
		//2 : Align Right
		mode_dress.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				option1++;
				option1%=3;
				switch(option1){
				case 0:
					mode_dress.setText(align1);
					input.setAlignmentX(JTextArea.CENTER_ALIGNMENT);
					break;
				case 1:
					mode_dress.setText(align2);
					input.setAlignmentX(JTextArea.LEFT_ALIGNMENT);
					break;
				case 2:
					mode_dress.setText(align3);
					input.setAlignmentX(JTextArea.RIGHT_ALIGNMENT);
					break;
				}
				updateExam();
			}
		});
		
		//채우기 모드
		//mode_fill _ option2
		//0 : fill		,include start, end line
		//1 : non-fill	,include start, end line
		//2 : fill
		//3 : non-fill
		mode_fill.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				option2++;
				option2%=4;
				switch(option2){
				case 0:
					mode_fill.setText(fill1);
					break;
				case 1:
					mode_fill.setText(fill2);
					break;
				case 2:
					mode_fill.setText(fill3);
					break;
				case 3:
					mode_fill.setText(fill4);
					break;
				}
				updateExam();
			}
		});
		
		//생성
		generate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				//init attributes
				String splitdata[] = input.getText().split("\n");
				System.out.println("generate");
				char symbol = fill.getText().toCharArray()[0];
				String output = "";
				
				
				
				//start line
				if(option2 <= 1){
					output += "//";
					for(int cnt = 0; cnt < getMAXWIDTH(); cnt ++){
						output += symbol ;
					}
					output += "\n";
				}
				
				
				//contents line
				for(int line = 0; line < splitdata.length; line++){
					int front = 0;
					int back = 0;
					int contentsSize =  getSize(splitdata[line]);
					
					System.out.println("contentsSize of "+splitdata[line] + " is "+ contentsSize);
					switch(option1){
					case 0:
						front = back = (getMAXWIDTH() - contentsSize)/2;
						if((getMAXWIDTH() - contentsSize) %2 == 1){
							back++;
						}
						break;
					case 1:
						front = 2;
						back = getMAXWIDTH() - contentsSize - 2;
						break;
					case 2:
						front = getMAXWIDTH() - contentsSize - 2;
						back = 2;
					}
					switch(option2){
					case 1:
					case 3:
						symbol = ' ';
					}
					output += "//";
					for(int cnt = 0; cnt < front; cnt++){
						output += symbol;
					}
					//본문 출력
					output += splitdata[line];
					for(int cnt = 0; cnt < back; cnt++){
						output += symbol;
					}
					output += "\n";
				}

				symbol = fill.getText().toCharArray()[0];
				//end line
				if(option2 <= 1){
					output += "//";
					for(int cnt = 0; cnt < getMAXWIDTH(); cnt ++){
						output += symbol ;
					}
					output += "\n";
				}
				
				
				StringSelection data = new StringSelection(output);
				Clipboard clipboard = myFrame.getToolkit().getDefaultToolkit().getSystemClipboard();
				clipboard.setContents(data, data);

			}
		});
	}

	public void actionPerformed(ActionEvent e)
	{
		Object obj = e.getSource();

		if(obj == generate)
		{

		}
		else if(obj == mode_fill)
		{
		}
		else if(obj == mode_dress)
		{
		}
	}
	public static int getSize(String input){
		int n =0;
		char[] row = input.toCharArray();
		for(int cnt = 0; cnt < input.length(); cnt++){
			if(isFullWord(row[cnt])){
				n+=2;
			}
			else{
				n++;
			}
		}
		
		return n;
	}
	public static boolean isFullWord(char input)
	{
		boolean value = true; 
//		System.out.println("type of ("+input+") : "+Character.getType(input));
		if(Character.getType(input) == 5){
			System.out.println("FULL : "+input + " : "+Character.getType(input));
			return true;
		}
//		if (input < 256 || (input >= 0xff61 && input <= 0xff9f)) 
		if( (input >= 0x0 && input < 0x81) || (input == 0xf8f0) || (input >= 0xff61 && input < 0xffa0) || (input >= 0xf8f1 && input < 0xf8f4))
		{ 
			System.out.println("half : "+input +" : "+(int)input);
			return false;
		} 
		System.out.println("Not Defined : "+input + " : "+Character.getType(input) +" : "+(int)input);		
		return false;
	}
	
	public static int getMAXWIDTH(){
		return Integer.parseInt(size.getText().toString());
	}
	public static void updateExam(){
		switch(option2){
		case 0:
		case 1:
			txt_example.setText(txtexample0);
			break;
		case 2:
		case 3:
			txt_example.setText("");
			break;
		}
		
		txt_example.setText(txt_example.getText()+"\n");
		
		switch(option2){
		case 0:
		case 2:
			if(option1 == 0){				
				txt_example.setText(txt_example.getText()+txtexample11);
			}
			else if(option1 == 1){
				txt_example.setText(txt_example.getText()+txtexample12);				
			}
			else if(option1 == 2){
				txt_example.setText(txt_example.getText()+txtexample13);				
			}
			break;
		case 1:
		case 3:
			if(option1 == 0){				
				txt_example.setText(txt_example.getText()+txtexample21);
			}
			else if(option1 == 1){
				txt_example.setText(txt_example.getText()+txtexample22);				
			}
			else if(option1 == 2){
				txt_example.setText(txt_example.getText()+txtexample23);				
			}
			break;
		}
		
		switch(option2){
		case 0:
		case 1:
			txt_example.setText(txt_example.getText()+txtexample0);
			break;
		case 2:
		case 3:
			txt_example.setText(txt_example.getText());
			break;
		}	
		System.out.println(txt_example.getText());
	}
}

