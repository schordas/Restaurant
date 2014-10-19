//Samuel Chordas 
//2652701844
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.charset.spi.CharsetProvider;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;



public class Main extends JFrame{
	JFileChooser jfc = new JFileChooser();
	static ArrayList <Table> tableArray= new ArrayList <Table>();
	static ArrayList <Wall> wallArray= new ArrayList <Wall>();
	static ArrayList <Title> titleArray= new ArrayList <Title>();
	static ArrayList <ColumnData> columnArray= new ArrayList <ColumnData>();
	static int numRows = 0;
	static int rowHeight = 0;
	static String rowFontName = "";
	static String rowFontType = "";
	static int rowFontSize = 0;
	static Rows rowData = new Rows();
	static Podium podiumData = new Podium();
	static DefaultTableModel dtm = new DefaultTableModel();
	static JPanel tablePanel = new JPanel(new BorderLayout());
	static JCheckBox openTable = new JCheckBox("Open Tables");
	static JCheckBox occupiedTable = new JCheckBox("Occupied Tables");
	static JPanel btnPanel = new JPanel(new BorderLayout());
	static boolean isOpenTable = false;
	static boolean isOccupiedTable = false;
	static ArrayList<Integer> openTableNum = new ArrayList<Integer>();
	static ArrayList<Integer> numOpenSeats = new ArrayList<Integer>();
	static ArrayList<Integer> occuTableNum = new ArrayList<Integer>();
	static ArrayList<Integer> numOccuSeats = new ArrayList<Integer>();
	static ArrayList<Integer> tableNum = new ArrayList<Integer>();
	static ArrayList<Integer> numSeats = new ArrayList<Integer>();
	static boolean fileRead = false;
	
	public Main(){
		super("CSCI 201 Restaurant");
		setSize(800, 600);
		
		JMenuBar jmb = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenuItem open = new JMenuItem("Open");
		open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		occupiedTable.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent me){
				if(!isOpenTable && !isOccupiedTable){
					isOccupiedTable = true;
					for (int i = 0; i < tableArray.size(); i++){
						if (tableArray.get(i).isOccupied){
							occuTableNum.add(tableArray.get(i).tableNum);
						}
					}
					Collections.sort(occuTableNum);
					for (int j = 0; j < occuTableNum.size(); j++){
						for (int i = 0; i < tableArray.size(); i++){
							if (occuTableNum.get(j) == tableArray.get(i).tableNum){
								numOccuSeats.add(tableArray.get(i).numSeats);
							}
						}
					}
					for (int i = 0; i < occuTableNum.size(); i++){
						String table = "Table " + String.valueOf(occuTableNum.get(i));
						dtm.setValueAt(table, i, 0);
						dtm.setValueAt(numOccuSeats.get(i), i, 1);
					}
				}
				else if (isOpenTable && !isOccupiedTable){
					isOccupiedTable = true;
					for (int i = 0; i < openTableNum.size(); i++){
						dtm.setValueAt("", i, 0);
						dtm.setValueAt("", i, 1);
					}
					openTableNum.clear();
					for (int i = 0; i < tableArray.size(); i++){
						tableNum.add(tableArray.get(i).tableNum);
					}
					Collections.sort(tableNum);
					for (int j = 0; j < tableNum.size(); j++){
						for (int i = 0; i < tableArray.size(); i++){
							if (tableNum.get(j) == tableArray.get(i).tableNum){
								numSeats.add(tableArray.get(i).numSeats);
							}
						}
					}
					for (int i = 0; i < tableNum.size(); i++){
						String table = "Table " + String.valueOf(tableNum.get(i));
						dtm.setValueAt(table, i, 0);
						dtm.setValueAt(numSeats.get(i), i, 1);
					}
					
				}
				else if (isOpenTable && isOccupiedTable){
					isOccupiedTable = false;
					for (int i = 0; i < tableNum.size(); i++){
						dtm.setValueAt("", i, 0);
						dtm.setValueAt("", i, 1);
					}
					tableNum.clear();
					for (int i = 0; i < tableArray.size(); i++){
						if (!tableArray.get(i).isOccupied){
							openTableNum.add(tableArray.get(i).tableNum);
						}
					}
					Collections.sort(openTableNum);
					for (int j = 0; j < openTableNum.size(); j++){
						for (int i = 0; i < tableArray.size(); i++){
							if (openTableNum.get(j) == tableArray.get(i).tableNum){
								numOpenSeats.add(tableArray.get(i).numSeats);
							}
						}
					}
					for (int i = 0; i < openTableNum.size(); i++){
						String table = "Table " + String.valueOf(openTableNum.get(i));
						dtm.setValueAt(table, i, 0);
						dtm.setValueAt(numOpenSeats.get(i), i, 1);
					}
				}
				else if (!isOpenTable && isOccupiedTable){
					isOccupiedTable = false;
					for (int i = 0; i < occuTableNum.size(); i++){
						dtm.setValueAt("", i, 0);
						dtm.setValueAt("", i, 1);
					}
					occuTableNum.clear();
					numOccuSeats.clear();
				}
			}
		});
		openTable.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent me){
				if(!isOpenTable && !isOccupiedTable){
					isOpenTable = true;
					for (int i = 0; i < tableArray.size(); i++){
						if (!tableArray.get(i).isOccupied){
							openTableNum.add(tableArray.get(i).tableNum);
						}
					}
					Collections.sort(openTableNum);
					for (int j = 0; j < openTableNum.size(); j++){
						for (int i = 0; i < tableArray.size(); i++){
							if (openTableNum.get(j) == tableArray.get(i).tableNum){
								numOpenSeats.add(tableArray.get(i).numSeats);
							}
						}
					}
					for (int i = 0; i < openTableNum.size(); i++){
						String table = "Table " + String.valueOf(openTableNum.get(i));
						dtm.setValueAt(table, i, 0);
						dtm.setValueAt(numOpenSeats.get(i), i, 1);
					}
				}
				else if (!isOpenTable && isOccupiedTable){
					isOpenTable = true;
					for (int i = 0; i < occuTableNum.size(); i++){
						dtm.setValueAt("", i, 0);
						dtm.setValueAt("", i, 1);
					}
					occuTableNum.clear();
					for (int i = 0; i < tableArray.size(); i++){
						tableNum.add(tableArray.get(i).tableNum);
					}
					Collections.sort(tableNum);
					for (int j = 0; j < tableNum.size(); j++){
						for (int i = 0; i < tableArray.size(); i++){
							if (tableNum.get(j) == tableArray.get(i).tableNum){
								numSeats.add(tableArray.get(i).numSeats);
							}
						}
					}
					for (int i = 0; i < tableNum.size(); i++){
						String table = "Table " + String.valueOf(tableNum.get(i));
						dtm.setValueAt(table, i, 0);
						dtm.setValueAt(numSeats.get(i), i, 1);
					}
					
				}
				else if (isOpenTable && isOccupiedTable){
					isOpenTable = false;
					for (int i = 0; i < tableNum.size(); i++){
						dtm.setValueAt("", i, 0);
						dtm.setValueAt("", i, 1);
					}
					tableNum.clear();
					for (int i = 0; i < tableArray.size(); i++){
						if (tableArray.get(i).isOccupied){
							occuTableNum.add(tableArray.get(i).tableNum);
						}
					}
					Collections.sort(occuTableNum);
					for (int j = 0; j < occuTableNum.size(); j++){
						for (int i = 0; i < tableArray.size(); i++){
							if (occuTableNum.get(j) == tableArray.get(i).tableNum){
								numOccuSeats.add(tableArray.get(i).numSeats);
							}
						}
					}
					for (int i = 0; i < occuTableNum.size(); i++){
						String table = "Table " + String.valueOf(occuTableNum.get(i));
						dtm.setValueAt(table, i, 0);
						dtm.setValueAt(numOccuSeats.get(i), i, 1);
					}
				}
				else if (isOpenTable && !isOccupiedTable){
					isOpenTable = false;
					for (int i = 0; i < openTableNum.size(); i++){
						dtm.setValueAt("", i, 0);
						dtm.setValueAt("", i, 1);
					}
					openTableNum.clear();
					numOpenSeats.clear();
				}
			}
		});

		NewPanel nP = new NewPanel(wallArray, tableArray, titleArray, columnArray, rowData, podiumData);
		add(nP);
		open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae){
				
				int fileReturnVal = jfc.showOpenDialog(Main.this);
				boolean isCorrectFile = true;
				while (isCorrectFile){
				if(fileReturnVal == JFileChooser.APPROVE_OPTION){
					File file = jfc.getSelectedFile();
					try {
						DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
						DocumentBuilder build = dbf.newDocumentBuilder();
						Document doc = null;
						
						try{
							doc = build.parse(file.getName());
						} catch(SAXException e){
							JOptionPane.showMessageDialog(Main.this, "File contains errors, please choose a properly formatted XML file.", 
									"Attention", JOptionPane.ERROR_MESSAGE);
			
							isCorrectFile = false;
							break;
						}catch (IOException e){
							JOptionPane.showMessageDialog(Main.this, "Incorrect file type, please choose an XML file.", 
									"Attention", JOptionPane.ERROR_MESSAGE);
							isCorrectFile = false;
							break;
						}
						doc.getDocumentElement().normalize();
						NodeList titleList = doc.getElementsByTagName("title");
						
						for (int i = 0; i < titleList.getLength(); i++){
							Node titleNode = titleList.item(i);
							if (titleNode.getNodeType() == Node.ELEMENT_NODE){
								Title title = new Title();
								Element eElement = (Element) titleNode;
								String titleStr = eElement.getElementsByTagName("name").item(0).getTextContent();
								int titleX = Integer.parseInt(eElement.getElementsByTagName("x").item(0).getTextContent());
								int titleY = Integer.parseInt(eElement.getElementsByTagName("y").item(0).getTextContent());
								title.setData(titleStr, titleX, titleY);
								titleArray.add(title);
							}
						}
						NodeList tableList = doc.getElementsByTagName("table");
						for (int i = 0; i < tableList.getLength(); i++ ){
							Node tableNode = tableList.item(i);
							
							if (tableNode.getNodeType() == Node.ELEMENT_NODE){
								Table table = new Table();
								int tableNum = Integer.parseInt(tableNode.getAttributes().getNamedItem("number").getNodeValue());
								table.setTableNum(tableNum);
								Element eElement = (Element) tableNode;
								int numSeats = Integer.parseInt(eElement.getElementsByTagName("numseats").item(0).getTextContent());
								table.setNumSeats(numSeats);
								table.setShape(eElement.getElementsByTagName("shape").item(0).getTextContent());
								table.setStartX(Integer.parseInt(eElement.getElementsByTagName("x").item(0).getTextContent()));
								table.setStartY(Integer.parseInt(eElement.getElementsByTagName("y").item(0).getTextContent()));
								if (eElement.getElementsByTagName("shape").item(0).getTextContent().equals("round")){
									table.setRadius(Integer.parseInt(eElement.getElementsByTagName("size").item(0).getTextContent()));
								}
								else if(eElement.getElementsByTagName("shape").item(0).getTextContent().equals("square")){
									table.setSides(Integer.parseInt(eElement.getElementsByTagName("size").item(0).getTextContent()), 
											Integer.parseInt(eElement.getElementsByTagName("size").item(0).getTextContent()));
								}
								else{
									table.setSides(Integer.parseInt(eElement.getElementsByTagName("width").item(0).getTextContent()), 
											Integer.parseInt(eElement.getElementsByTagName("height").item(0).getTextContent()));
								}
								table.setStatus(eElement.getElementsByTagName("status").item(0).getTextContent());
								tableArray.add(table);
							}
						}
						NodeList startLocList = doc.getElementsByTagName("startlocation");
						NodeList endLocList = doc.getElementsByTagName("endlocation");
						for (int i = 0; i < startLocList.getLength(); i++ ){
							Node endLoc = endLocList.item(i);
							Node startLoc = startLocList.item(i);
							int xStrt = 0;
							int yStrt = 0;
							int xEnd = 0;
							int yEnd = 0;
							if (startLoc.getNodeType() == Node.ELEMENT_NODE){
								Element eElement = (Element) startLoc;
								xStrt = Integer.parseInt(eElement.getElementsByTagName("x").item(0).getTextContent());
								yStrt = Integer.parseInt(eElement.getElementsByTagName("y").item(0).getTextContent());								
							}
							if (endLoc.getNodeType() == Node.ELEMENT_NODE){
								Element eElement = (Element) endLoc;
								xEnd = Integer.parseInt(eElement.getElementsByTagName("x").item(0).getTextContent());
								yEnd = Integer.parseInt(eElement.getElementsByTagName("y").item(0).getTextContent());
							}
							Wall wall = new Wall(xStrt, yStrt, xEnd, yEnd);
							wallArray.add(wall);
						}
						
						NodeList columnList = doc.getElementsByTagName("column");
						for(int i = 0; i < columnList.getLength(); i++){
							Node statusNode = columnList.item(i);
							if(statusNode.getNodeType() == Node.ELEMENT_NODE){
								Element eElement = (Element) statusNode;
								ColumnData dTable = new ColumnData();
								dTable.setColumnName(statusNode.getAttributes().getNamedItem("name").getTextContent());
								dTable.setFont(eElement.getElementsByTagName("font").item(0).getAttributes().getNamedItem("name").getTextContent());
								dTable.setType(eElement.getElementsByTagName("font").item(0).getAttributes().getNamedItem("type").getTextContent());
								dTable.setSize(Integer.parseInt(eElement.getElementsByTagName("font").item(0).getAttributes().getNamedItem("size").getTextContent()));
								dTable.setStartX(Integer.parseInt(eElement.getElementsByTagName("x").item(0).getTextContent()));
								dTable.setStartY(Integer.parseInt(eElement.getElementsByTagName("y").item(0).getTextContent()));
								columnArray.add(dTable);
							}
							
						}
						NodeList rowList = doc.getElementsByTagName("rows");
						Node rowNode = rowList.item(0);
						if (rowNode.getNodeType() == Node.ELEMENT_NODE){
							Element rowElement = (Element) rowNode;
							rowData.setNumRows(Integer.parseInt(rowNode.getAttributes().getNamedItem("number").getTextContent()));
							rowData.setRowHeight(Integer.parseInt(rowNode.getAttributes().getNamedItem("height").getTextContent()));
							rowData.setFontName(rowElement.getElementsByTagName("font").item(0).getAttributes().getNamedItem("name").getTextContent());
							rowData.setFontType(rowElement.getElementsByTagName("font").item(0).getAttributes().getNamedItem("type").getTextContent());
							rowData.setFontSize(Integer.parseInt(rowElement.getElementsByTagName("font").item(0).getAttributes().getNamedItem("size").getTextContent()));
						}
						NodeList podiumList = doc.getElementsByTagName("podium");
						Node podiumNode = podiumList.item(0);
						if (podiumNode.getNodeType() == Node.ELEMENT_NODE){
							Element podiumElement = (Element) podiumNode;
							podiumData.setStartX(Integer.parseInt(podiumElement.getElementsByTagName("x").item(0).getTextContent()));
							podiumData.setStartY(Integer.parseInt(podiumElement.getElementsByTagName("y").item(0).getTextContent()));
							podiumData.setWidth(Integer.parseInt(podiumElement.getElementsByTagName("width").item(0).getTextContent()));
							podiumData.setHeight(Integer.parseInt(podiumElement.getElementsByTagName("height").item(0).getTextContent()));
						}
						repaint();

						tablePanel.setBounds(columnArray.get(0).startX, columnArray.get(0).startY*2,
								(columnArray.get(1).startX - columnArray.get(0).startX)*2, 
								rowData.numRows*rowData.rowHeight-rowData.rowHeight*3);
						JScrollPane tableContain = new JScrollPane();
						JTable jt = new JTable(dtm);
						for (int i = 0; i < columnArray.size(); i++){
							dtm.addColumn(columnArray.get(i).columnNames);
							if (columnArray.get(i).type.equals("bold"))
									jt.getTableHeader().setFont(new Font(columnArray.get(i).font, Font.BOLD, columnArray.get(i).size));
							else if (columnArray.get(i).type.equals("plain"))
								jt.getTableHeader().setFont(new Font(columnArray.get(i).font, Font.PLAIN, columnArray.get(i).size));
							else
								jt.getTableHeader().setFont(new Font(columnArray.get(i).font, Font.ITALIC, columnArray.get(i).size));
						}
						dtm.setNumRows(rowData.numRows);
						tableContain.getViewport().add(jt);
						jt.setShowGrid(true);
						jt.setGridColor(Color.GRAY);
						tablePanel.add(tableContain, BorderLayout.CENTER);
						getContentPane().add(tablePanel);
						tablePanel.repaint();
						tablePanel.validate();
						btnPanel.setBounds(columnArray.get(0).startX, rowData.numRows*rowData.rowHeight+rowData.rowHeight*2, 140, 50);
						btnPanel.add(openTable, BorderLayout.NORTH);
						btnPanel.add(occupiedTable, BorderLayout.SOUTH);
						getContentPane().add(btnPanel);
						btnPanel.repaint();
						btnPanel.validate();
						setComponentZOrder(tablePanel, 0);
						setComponentZOrder(btnPanel, 0);
						isCorrectFile = false;
						break;
						
					}catch(ParserConfigurationException pce){
						JOptionPane.showMessageDialog(Main.this, "File contains errors, please choose a properly formatted XML file.", 
								"Attention", JOptionPane.ERROR_MESSAGE);
						isCorrectFile = false;
						break;
					}
				}
				isCorrectFile = false;
				break;
			}
			}
			
		});
		fileMenu.add(open);
		jmb.add(fileMenu);
		this.setJMenuBar(jmb);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
	}
	public static void main(String [] args){
		new Main();
	}

}

class NewPanel extends JPanel{
	
	int chairSize = 5;
	int chairPad = 2;
	ArrayList<Wall> drawWallArray = new ArrayList <Wall>();
	ArrayList<Table> drawTableArray = new ArrayList <Table>();
	ArrayList <Title> drawTitle = new ArrayList <Title>();
	ArrayList <ColumnData> columnData = new ArrayList <ColumnData>();
	Rows rowData = new Rows();
	Podium podiumData = new Podium();
	public NewPanel(ArrayList<Wall> drawWallArray, ArrayList<Table> drawTableArray, ArrayList <Title> titleArray, 
			ArrayList <ColumnData> columnData, Rows rowData, Podium podiumData){
		this.drawWallArray = drawWallArray;
		this.drawTableArray = drawTableArray;
		this.drawTitle = titleArray;
		this.columnData = columnData;
		this.rowData = rowData;
		this.podiumData = podiumData;
	}

	protected void paintComponent(Graphics g){
		super.paintComponent(g);

		if (podiumData.startX != 0){
			g.setColor(Color.CYAN);
			g.fillRect(podiumData.startX, podiumData.startY, podiumData.width, podiumData.height);
			FontMetrics metrics = g.getFontMetrics(g.getFont());
			g.setColor(Color.BLACK);
			g.drawString("P", podiumData.startX + podiumData.width - podiumData.width/2
					- metrics.stringWidth("p")/2, podiumData.startY + podiumData.height - podiumData.height/2 
					+ metrics.getHeight()/4);
		}
		for (int i = 0; i < drawTitle.size(); i++){
			g.setFont(new Font("Dialog", Font.BOLD, 20));
			g.drawString(this.drawTitle.get(i).titleStr, this.drawTitle.get(i).titleX, this.drawTitle.get(i).titleY+15);
			g.setFont(new Font("Dialog", Font.PLAIN, 12));
		}
		for (int i = 0; i < drawWallArray.size(); i++){
			g.setColor(Color.BLACK);
			g.drawLine(this.drawWallArray.get(i).getXStrt(), this.drawWallArray.get(i).getYStrt(), 
					this.drawWallArray.get(i).getXEnd(), this.drawWallArray.get(i).getYEnd());
		}
		for (int i = 0; i < drawTableArray.size(); i++){
			if(drawTableArray.get(i).isOccupied){
				g.setColor(Color.RED);
			}
			else{
				g.setColor(Color.GREEN);
			}
			if (drawTableArray.get(i).isRect){
				g.fillRect(drawTableArray.get(i).startX, drawTableArray.get(i).startY, 
							drawTableArray.get(i).width, drawTableArray.get(i).height);
				g.setColor(Color.BLACK);
				FontMetrics metrics = g.getFontMetrics(g.getFont());
				//center text
				g.drawString(String.valueOf(drawTableArray.get(i).tableNum), 
						(drawTableArray.get(i).width + drawTableArray.get(i).startX) - (drawTableArray.get(i).width)/2
						- metrics.stringWidth(String.valueOf(drawTableArray.get(i).tableNum))/2, 
						(drawTableArray.get(i).height + drawTableArray.get(i).startY) - 
						(drawTableArray.get(i).height)/2 + metrics.getHeight()/4);
				int[] numChairs = new int[2];
				int totalChair = drawTableArray.get(i).numSeats - 2;
				for (int j = 0; j < 2; j++) {
					numChairs[j] = totalChair/2;
					if (j < totalChair % 2)
						numChairs[j]++;
				}
				if (drawTableArray.get(i).width < drawTableArray.get(i).height){
					
					if (totalChair >1){
						g.fillRect(drawTableArray.get(i).startX+(drawTableArray.get(i).width-chairSize)/2, 
								drawTableArray.get(i).startY-chairSize-chairPad, 
								chairSize, chairSize);
					}
					if (totalChair > 2){
						g.fillRect(drawTableArray.get(i).startX+(drawTableArray.get(i).width-chairSize)/2, 
								drawTableArray.get(i).startY + drawTableArray.get(i).height + chairPad, 
								chairSize, chairSize);
						//left
						for (int j = 0; j < numChairs[0]; j++) {
							g.fillRect(drawTableArray.get(i).startX-chairSize-chairPad, 
									drawTableArray.get(i).startY+(j+1)*(drawTableArray.get(i).height-chairSize*numChairs[0])/(numChairs[0]+1)+j*chairSize, 
									chairSize, chairSize);
						}
						//right
						for (int j = 0; j < numChairs[1]; j++) {
							g.fillRect(drawTableArray.get(i).startX + drawTableArray.get(i).width+chairPad, 
									drawTableArray.get(i).startY+(j+1)*(drawTableArray.get(i).height-chairSize*numChairs[1])/(numChairs[1]+1)+j*chairSize, 
									chairSize, chairSize);
						}
					}
					
				}
				else{
					if (totalChair >1){
						g.fillRect(drawTableArray.get(i).startX-chairSize-chairPad, 
								drawTableArray.get(i).startY+(drawTableArray.get(i).height-chairSize)/2, 
								chairSize, chairSize);
					}
					if (totalChair > 2){
						g.fillRect(drawTableArray.get(i).startX + drawTableArray.get(i).width+chairPad, 
								drawTableArray.get(i).startY+(drawTableArray.get(i).height-chairSize)/2, 
								chairSize, chairSize);
						//top
						for (int j = 0; j < numChairs[0]; j++) {
							g.fillRect(drawTableArray.get(i).startX+(j+1)*(drawTableArray.get(i).width-chairSize*numChairs[0])/(numChairs[0]+1)+j*chairSize, 
									drawTableArray.get(i).startY-chairSize-chairPad, 
									chairSize, chairSize);
						}
						//bottom
						for (int j = 0; j < numChairs[1]; j++) {
							g.fillRect(drawTableArray.get(i).startX+(j+1)*(drawTableArray.get(i).width-chairSize*numChairs[1])/(numChairs[1]+1)+j*chairSize, 
									drawTableArray.get(i).startY+drawTableArray.get(i).height+chairPad, 
									chairSize, chairSize);
						}
					}
					
				}
			}
			else if (drawTableArray.get(i).isSqr){
				g.fillRect(drawTableArray.get(i).startX, drawTableArray.get(i).startY, 
							drawTableArray.get(i).width, drawTableArray.get(i).height);
				g.setColor(Color.BLACK);
				FontMetrics metrics = g.getFontMetrics(g.getFont());
				//center text
				g.drawString(String.valueOf(drawTableArray.get(i).tableNum), 
						(drawTableArray.get(i).width + drawTableArray.get(i).startX) - (drawTableArray.get(i).width)/2
						- metrics.stringWidth(String.valueOf(drawTableArray.get(i).tableNum))/2, 
						(drawTableArray.get(i).height + drawTableArray.get(i).startY) - 
						(drawTableArray.get(i).height)/2 + metrics.getHeight()/4);
				
				//calculate how many chairs on each side
				int[] numChairs = new int[4];
				int totalChair = drawTableArray.get(i).numSeats;
				for (int j = 0; j < 4; j++) {
					numChairs[j] = totalChair / 4;
					if (j < totalChair % 4)
						numChairs[j]++;
				}
				//left
				for (int j = 0; j < numChairs[0]; j++) {
					g.fillRect(drawTableArray.get(i).startX-chairSize-chairPad, 
							drawTableArray.get(i).startY+(j+1)*(drawTableArray.get(i).height-chairSize*numChairs[0])/(numChairs[0]+1)+j*chairSize, 
							chairSize, chairSize);
				}
				//right
				for (int j = 0; j < numChairs[1]; j++) {
					g.fillRect(drawTableArray.get(i).startX + drawTableArray.get(i).width+chairPad, 
							drawTableArray.get(i).startY+(j+1)*(drawTableArray.get(i).height-chairSize*numChairs[1])/(numChairs[1]+1)+j*chairSize, 
							chairSize, chairSize);
				}
				//top
				for (int j = 0; j < numChairs[2]; j++) {
					g.fillRect(drawTableArray.get(i).startX+(j+1)*(drawTableArray.get(i).width-chairSize*numChairs[2])/(numChairs[2]+1)+j*chairSize, 
							drawTableArray.get(i).startY-chairSize-chairPad, 
							chairSize, chairSize);
				}
				//bottom
				for (int j = 0; j < numChairs[3]; j++) {
					g.fillRect(drawTableArray.get(i).startX+(j+1)*(drawTableArray.get(i).width-chairSize*numChairs[3])/(numChairs[3]+1)+j*chairSize, 
							drawTableArray.get(i).startY+drawTableArray.get(i).height+chairPad, 
							chairSize, chairSize);
				}
				
			}
			else if (drawTableArray.get(i).isRnd){
				g.fillOval(drawTableArray.get(i).startX, drawTableArray.get(i).startY, 
						drawTableArray.get(i).radius, drawTableArray.get(i).radius);
				g.setColor(Color.BLACK);
				FontMetrics metrics = g.getFontMetrics(g.getFont());
				//center text
				g.drawString(String.valueOf(drawTableArray.get(i).tableNum), 
						(drawTableArray.get(i).radius + drawTableArray.get(i).startX) - (drawTableArray.get(i).radius)/2
						- metrics.stringWidth(String.valueOf(drawTableArray.get(i).tableNum))/2, 
						(drawTableArray.get(i).radius + drawTableArray.get(i).startY) - 
						(drawTableArray.get(i).radius)/2 + metrics.getHeight()/4);
				
				double alpha = (2*Math.PI)/drawTableArray.get(i).numSeats;
				double r = (double) (drawTableArray.get(i).radius/2) + 2.0 + (double)chairSize/Math.sqrt(2.0);
				for (int j = 0; j < drawTableArray.get(i).numSeats; j++) {
					double beta = 3*Math.PI/4.0 + j*alpha;
					int x = (int) (r * Math.cos(beta)) + drawTableArray.get(i).startX + drawTableArray.get(i).radius/2 - chairSize/2;
					int y = -(int) (r * Math.sin(beta)) + drawTableArray.get(i).startY + drawTableArray.get(i).radius/2 - chairSize/2;
					g.fillRect(x, y, chairSize, chairSize);
					
				}
				
			}
		}
	}
}
