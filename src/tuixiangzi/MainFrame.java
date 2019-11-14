package tuixiangzi;

import java.awt.Container;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class MainFrame extends JFrame implements KeyListener{
	private Container panel;
	private JLabel player;
	private int playerX;
	private int playerY;
	int count = 0;
	int total = 3;
	public MainFrame(){
		this.mainFrameBaseUI();
		this.dateInit();
		this.dateUI();
		this.backgroundUI();
		this.addKeyListener(this);
		this.setVisible(true);
	}
	private JLabel[][] uis = new JLabel[12][16];
	private void dateUI() {
		for (int i = 0; i < dates.length; i++) {
			for (int j = 0; j < dates[i].length; j++) {
				if(dates[i][j] == 1){
					JLabel temp = new JLabel(new ImageIcon("1.png"));
					temp.setBounds(10+j*50,10+i*50, 50, 50);
					this.panel.add(temp);
				}
			}
		}
		this.playerX = 3;
		this.playerY = 3;
		player = new JLabel(new ImageIcon("-10.png"));
		player.setBounds(10+playerX*50,10+playerY*50, 50, 50);
		this.panel.add(player);
		

		JLabel tar1 = new JLabel(new ImageIcon("8.png"));
		tar1.setBounds(10+14*50,10+5*50, 50, 50);
		this.panel.add(tar1);
		dates[5][14] = LogicHelper.TARGET;
		uis[5][14] = tar1;
		JLabel tar2 = new JLabel(new ImageIcon("8.png"));
		tar2.setBounds(10+14*50,10+6*50, 50, 50);
		this.panel.add(tar2);
		dates[6][14] = LogicHelper.TARGET;
		uis[6][14] = tar2;
		JLabel tar3 = new JLabel(new ImageIcon("8.png"));
		tar3.setBounds(10+14*50,10+7*50, 50, 50);
		this.panel.add(tar3);
		dates[7][14] = LogicHelper.TARGET;
		uis[7][14] = tar3;
		
		
		JLabel box1 = new JLabel(new ImageIcon("4.png"));
		box1.setBounds(10+7*50,10+3*50, 50, 50);
		this.panel.add(box1);
		dates[3][7] = LogicHelper.BOX;
		uis[3][7] = box1;
		JLabel box2 = new JLabel(new ImageIcon("4.png"));
		box2.setBounds(10+7*50,10+5*50, 50, 50);
		this.panel.add(box2);
		dates[5][7] = LogicHelper.BOX;
		uis[5][7] = box2;
		JLabel box3 = new JLabel(new ImageIcon("4.png"));
		box3.setBounds(10+7*50,10+7*50, 50, 50);
		this.panel.add(box3);
		dates[7][7] = LogicHelper.BOX;
		uis[7][7] = box3;
		
	}
	private int[][] dates = new int[12][16];
	private void dateInit() {
		dates = new int[][]{
				{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
				{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
				{1,0,1,0,1,1,1,0,1,1,1,1,1,0,0,1},
				{1,0,1,0,0,0,1,0,0,0,0,0,1,0,0,1},
				{1,0,1,0,1,1,1,0,0,0,0,0,1,0,0,1},
				{1,0,1,0,1,0,0,0,0,1,1,1,1,0,0,1},
				{1,0,1,0,1,1,1,0,0,0,0,0,1,0,0,1},
				{1,0,1,0,0,0,0,0,0,0,0,0,1,0,0,1},
				{1,0,1,0,0,0,0,0,1,1,1,1,1,0,0,1},
				{1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,1},
				{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
				{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
				};
	}

	private void backgroundUI() {
		JLabel floor  = new JLabel(new ImageIcon("floor.png"));
		floor.setBounds(10, 10, 800, 600);
		this.panel.add(floor);
	}

	private void mainFrameBaseUI() {
		this.panel = this.getContentPane();
		this.setSize(826, 650);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.panel.setLayout(null);
		this.setTitle("是个人就通关一次！");
		this.setResizable(false);
		this.getContentPane().setLayout(null);
	}
	private static void move(JLabel c,int vMoveType,int hMoveType){
		c.setLocation(c.getX()-hMoveType*50, c.getY()-vMoveType*50);
	}
	
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		int x = keyCode%2 == 0?39-keyCode:0;
		int y = keyCode%2 == 1?38-keyCode:0;
		
		if(keyCode >= 37 && keyCode <= 40){
			player.setIcon(new ImageIcon(x+""+y+".png"));
			if(dates[playerX-x][playerY-y] == LogicHelper.FLOOR || dates[playerX-x][playerY-y] == LogicHelper.TARGET){
				move(player,x,y);
				playerX = playerX-x;
				playerY = playerY-y;
				return;
			}
			if(dates[playerX-x][playerY-y] == LogicHelper.BOX || dates[playerX-x][playerY-y] == LogicHelper.BOX_AND_TARGET){
				int code1 = dates[playerX-x][playerY-y];
				int code2 = dates[playerX-x*2][playerY-y*2];
				if(code2 == LogicHelper.FLOOR || code2 == LogicHelper.TARGET){
					JLabel box = uis[playerX-x][playerY-y];
					move(box,x,y);
					uis[playerX-x*2][playerY-y*2] = uis[playerX-x][playerY-y];
					uis[playerX-x][playerY-y] = null;

					if(code1 == LogicHelper.BOX && code2 == LogicHelper.FLOOR ){
						dates[playerX-x*2][playerY-y*2] = LogicHelper.BOX;
						dates[playerX-x][playerY-y] = LogicHelper.FLOOR;
					}
					if(code1 == LogicHelper.BOX && code2 == LogicHelper.TARGET){
						dates[playerX-x*2][playerY-y*2] = LogicHelper.BOX_AND_TARGET;
						dates[playerX-x][playerY-y] = LogicHelper.FLOOR;
						box.setIcon(new ImageIcon("40.png"));
						count++;
					}
					if(code1 == LogicHelper.BOX_AND_TARGET && code2 == LogicHelper.FLOOR ){
						dates[playerX-x*2][playerY-y*2] = LogicHelper.BOX;
						dates[playerX-x][playerY-y] = LogicHelper.TARGET;
						box.setIcon(new ImageIcon("4.png"));
						count--;
					}
					if(code1 == LogicHelper.BOX_AND_TARGET && code2 == LogicHelper.TARGET){
						dates[playerX-x*2][playerY-y*2] = LogicHelper.BOX_AND_TARGET;
						dates[playerX-x][playerY-y] = LogicHelper.TARGET;
						box.setIcon(new ImageIcon("40.png"));
					}
				
					move(player,x,y);
					playerX = playerX-x;
					playerY = playerY-y;
					
					victory();
				}
				return;
			}
		}
	}
	private void victory() {
		if(count == total){
			JOptionPane.showMessageDialog(null,"o");
		}
	}
	
	public void keyReleased(KeyEvent e) {
	}

	public void keyTyped(KeyEvent e) {
	}
}