import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import javax.swing.JScrollPane;
import javax.swing.JDialog;
import javax.swing.SwingUtilities;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.Image;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;
public class MainPanel extends JPanel implements ActionListener, MouseListener 
{ 
    public static final int defaultWidth = 808;
    public static final int defaultHeight = 452;
    
    Information forMain = new Information();
    String[] heroFiveStarList = forMain.getFiveStarList("COV5Star");
    String[] artifactFiveStarList = forMain.getFiveStarList("ART5Star");
    private List<String> covenantFiveRateUp = forMain.getCovFiveStar("all");
    private List<String> artifactFiveRateUp = forMain.getArtFiveStar("all");
    
    public static int currentRateUpSummonLeft; //store rate up summon left (independant)
    public static int currentMysticSummonLeft = 200; //store mystic summon left (dependant)
    private String currentRateUpHero = "";
    public static String DROP_RATE_UP_HERO;
    public static String CURRENT_MOONLIGHT_FIVE;
    
    public static BufferedImage mysticPool; //current image of mystic pool 
    BufferedImage croppedHero; //covenant hero background
    
    COVMechanics cov = new COVMechanics();
    MLMechanics ml = new MLMechanics();
    MYSMechanics mys;
    
    public static int animationSpeed = 150;
    Timer clock = new Timer(200, this); //bg animation rate
    Timer clock2 = new Timer(animationSpeed, this); //summon animation rate
    
    List<String> bgAnimation = forMain.getBackgroundAnimation();
    int currentBgPosition = 0;
    ImageIcon currentBgIcon = new ImageIcon(bgAnimation.get(currentBgPosition)); 
    ImageIcon which = currentBgIcon; 
    
    List<String> summonAnimation = new ArrayList(Arrays.asList("cat"));
    int currentAniPosition = 0;
    ImageIcon currentAniIcon = new ImageIcon(summonAnimation.get(currentAniPosition));
    
    List<BufferedImage> mysticBgAnimation = new ArrayList(); //hold list of images of mystic bg
    private int currentMysBgPosition = 0;
    static List<String> poolInfo = new ArrayList();
    
    JPanel westPanel = new JPanel(); //pity text top position
    JLabel pityTextTop = new JLabel("");
    
    JPanel eastPanel = new JPanel(); //banner button position
    JButton dropRateUp = new JButton("Drop rate up");
    JButton covenant = new JButton("Covenant Summon");
    JButton moonlight = new JButton("Moonlight Summon");
    JButton mystic = new JButton("Mystic Summon");
    JButton elemental = new JButton("Elemental Summon");
    
    SummonButton theSummonButton = new SummonButton(this);
    ImageIcon covenantSummon = theSummonButton.getCurrencyImage("Pic/covenantBookmark.png",30,30);
    ImageIcon galaxySummon = theSummonButton.getCurrencyImage("Pic/galaxyBookmark.png",30,30);
    ImageIcon mysticSummon = theSummonButton.getCurrencyImage("Pic/mysticMedal.jpeg", 30, 30);
    
    private boolean bgReplayAnimation = true; 
    private boolean playSummonAnimation = false;
    private boolean mysticFirstClick = true; //one pick when first click banner button
    private boolean rateUpFirstClick = true; //one pick when first click banner button
    
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private int screenWidth = (int)screenSize.getWidth();
    private int screenHeight = (int)screenSize.getHeight();
   
    public static void main (String[] args)
    {
        JFrame epicSeven = new JFrame("Epic Seven Summon Simulator");
        Container c = epicSeven.getContentPane();
        MainPanel mainBanner = new MainPanel();
        c.add(mainBanner);
        epicSeven.setResizable(true);
        epicSeven.pack(); //consolidate the information 
        epicSeven.setLocationRelativeTo(null); //centered the frame
        epicSeven.setVisible(true); 
        epicSeven.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
    }
    
    public MainPanel()
    {
        setPreferredSize(new Dimension(defaultWidth, defaultHeight));
        setLayout(new BorderLayout());
        addMouseListener(this);
        clock.start(); 
        mys = new MYSMechanics();
        
        westPanel.setOpaque(false);
        westPanel.add(pityTextTop);
        pityTextTop.setOpaque(true);
        pityTextTop.setBackground(Color.WHITE);
        add(westPanel, BorderLayout.WEST);
        disablePityTextTop(false);
       
        eastPanel.setOpaque(false); //for child panels to have same bg as mainpanel
        eastPanel.setLayout(new GridLayout(18,1));
        eastPanel.add(dropRateUp);
        dropRateUp.addActionListener(this);
        dropRateUp.setBackground(Color.BLACK);
        dropRateUp.setForeground(Color.WHITE);
        covenant.setIcon(covenantSummon);
        covenant.setBackground(Color.BLACK);
        covenant.setForeground(Color.WHITE);
        eastPanel.add(covenant);
        covenant.addActionListener(this);
        mystic.setIcon(mysticSummon);
        mystic.setBackground(Color.BLACK);
        mystic.setForeground(Color.WHITE);
        eastPanel.add(mystic);
        mystic.addActionListener(this);
        moonlight.setIcon(galaxySummon);
        moonlight.setBackground(Color.BLACK);
        moonlight.setForeground(Color.WHITE);
        eastPanel.add(moonlight);
        moonlight.addActionListener(this);
        eastPanel.add(elemental);
        elemental.addActionListener(this);
        elemental.setBackground(Color.BLACK);
        elemental.setForeground(Color.WHITE);
        add(eastPanel, BorderLayout.EAST); 
        
        theSummonButton.setOpaque(false);
        add(theSummonButton, BorderLayout.SOUTH);
        this.validate();
    }
   
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(which.getImage(), 0, 0, getWidth(), getHeight(), this);
    }
   
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == dropRateUp) {
            if (rateUpFirstClick == true) DROP_RATE_UP_HERO = theSummonButton.pickRateUpHero();
            if (!DROP_RATE_UP_HERO.equals("")) {
                disableBgReplayAnimation(false);  
                rateUpFirstClick = false;
                getDropRateUpBanner();
                disablePityTextTop(true); //turn on the pity summon text(top)
                theSummonButton.disableBannerInfoButton(true);
                theSummonButton.disableRefreshButton(true);
                theSummonButton.changeSummonText("5 Summon");
                theSummonButton.disablePityTextBottom(false); //turn off pity summon count text(bottom)
                theSummonButton.setSummonIcon("covenant"); 
                theSummonButton.CURRENTBUTTON = "dropRateUp";
            }
        }
        else if (e.getSource() == covenant || e.getSource() == moonlight) {
            if (e.getSource() == covenant) {
                theSummonButton.setSummonIcon("covenant");
                theSummonButton.CURRENTBUTTON = "covenant";
            }
            else {
                theSummonButton.CURRENTBUTTON = "moonlight";
                theSummonButton.setSummonIcon("moonlight");
            }
            disableBgReplayAnimation(true);
            disablePityTextTop(false);
            theSummonButton.disableBannerInfoButton(false);
            theSummonButton.disableRefreshButton(false);
            theSummonButton.changeSummonText("5 Summon");
            theSummonButton.disablePityTextBottom(false);  
        }
        else if (e.getSource() == mystic) { //mystic banner 
            if (mysticFirstClick == true) { //mysticFirstClick == true) {
                try {
                    theSummonButton.refreshMysticPool();
                } catch(IOException eh) {
                    JOptionPane.showMessageDialog(this, "AT FIRST REFRESH - CHECK IMAGES' NAME", "ERROR", JOptionPane.ERROR_MESSAGE);
                  }
                mysticFirstClick = false;
            }
            disableBgReplayAnimation(true); //turn on bg replay animation
            disablePityTextTop(true);
            theSummonButton.disableRefreshButton(true); //add refresh pool button
            theSummonButton.changeSummonText("50 Summon");
            theSummonButton.disablePityTextBottom(false);
            theSummonButton.setSummonIcon("mystic");
            theSummonButton.CURRENTBUTTON = "mystic";
            setPityTextTop(currentMysticSummonLeft, CURRENT_MOONLIGHT_FIVE);
            if (currentMysticSummonLeft == 0) setPityTextTop(CURRENT_MOONLIGHT_FIVE + " Garanteed Summon!");
        }
        else if (e.getSource() == elemental) {}
        if (e.getSource() == clock && bgReplayAnimation == true) { //bg animation
            if (theSummonButton.CURRENTBUTTON.equals("mystic")) {
                if (currentMysBgPosition >= mysticBgAnimation.size()) currentMysBgPosition = 0;
                which = new ImageIcon(mysticBgAnimation.get(currentMysBgPosition));
                currentMysBgPosition++;
            }
            else {
                if (currentBgPosition >= bgAnimation.size()) currentBgPosition = 0;
                currentBgIcon = new ImageIcon(bgAnimation.get(currentBgPosition));
                which = currentBgIcon;
                currentBgPosition++;
            }    
        }
        if (e.getSource() == clock2 && playSummonAnimation == true) { //summon animation mechanics
             disableBannerButtons(false);
             disablePityTextTop(false);
             theSummonButton.disableSummonButton(false);
             theSummonButton.disableBackButton(false);
             theSummonButton.disableInfoButtons(false); 
             theSummonButton.disableRefreshButton(false);
             theSummonButton.disablePityTextBottom(false);
             try {
                 currentAniIcon = new ImageIcon(summonAnimation.get(currentAniPosition));
                 which = currentAniIcon;
                 currentAniPosition++;
                 if (currentAniPosition >= summonAnimation.size()) {
                     if (theSummonButton.CURRENTBUTTON.equals("dropRateUp"))
                         if (theSummonButton.auto == false) theSummonButton.disablePityTextBottom(true);
                     else if (theSummonButton.CURRENTBUTTON.equals("mystic")) {
                         theSummonButton.changeSummonText("50 Summon Again");
                         if (theSummonButton.auto == false) theSummonButton.disablePityTextBottom(true);
                     }
                     playSummonAnimation(false);
                     currentAniPosition = 0;
                     if (cov.GOTRATEUPHERO == true && theSummonButton.untilGotTheHeroBox.isSelected() || 
                   mys.GOTCURRENTMOONLIGHT == true && theSummonButton.untilGotTheHeroBox.isSelected() ||
                   cov.GOTRATEUPARTIFACT == true && theSummonButton.untilGotTheArtifactBox.isSelected() ) {
                         theSummonButton.stopAuto();
                         cov.GOTRATEUPHERO = false;
                         cov.GOTRATEUPARTIFACT = false;
                         mys.GOTCURRENTMOONLIGHT = false;
                     }
                     if (theSummonButton.untilGotMoonlightFiveBox.isSelected()) {
                         if (cov.GOTMOONLIGHTFIVE == true || ml.GOTMOONLIGHTFIVE == true) {
                             theSummonButton.stopAuto();
                             ml.GOTMOONLIGHTFIVE = false;
                             cov.GOTMOONLIGHTFIVE = false;
                         }
                     }
                     if (theSummonButton.auto == true) {
                         theSummonButton.summonBanner(theSummonButton.CURRENTBUTTON);
                         disablePityTextTop(true);
                         if (theSummonButton.skipSummonAnimationBox.isSelected()) 
                            skipSummonAnimation();
                     }
                     else {
                         disablePityTextTop(false);
                         theSummonButton.corneredBackAndSummonButton();
                         theSummonButton.disableBackButton(true);
                         theSummonButton.changeSummonText("5 Summon Again");
                         theSummonButton.disableBackButton(true);
                         theSummonButton.disableSummonButton(true);
                     }
                 }
             } catch (ArrayIndexOutOfBoundsException exception) {
                 JOptionPane.showMessageDialog(this, "ERROR_ARRAYINDEX_ANIMATION", "ERROR", JOptionPane.ERROR_MESSAGE);
             }
        }
        repaint();
     }
     
     public BufferedImage resize(BufferedImage img, int newW, int newH)
     {
         Image temp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
         BufferedImage reImage = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);
         Graphics g = reImage.createGraphics();
         g.drawImage(temp, 0, 0, null);
         g.dispose();
         return reImage;
     }
     
     private final int gap = 5, startWidth = 90, startHeight = 410, betweenWidth = 90, betweenHeight = 102;
     public void displayMysticPool(List<String> curPool, String currentML5Ani) throws IOException
     {
         List<String> currentPool = curPool;
         CURRENT_MOONLIGHT_FIVE = currentPool.get(0).substring(currentPool.get(0).indexOf("/")+1, currentPool.get(0).indexOf("."));
         String currentMoonlightFour = currentPool.get(1).substring(currentPool.get(1).indexOf("/")+1, currentPool.get(1).indexOf("."));
         BufferedImage[] cur = new BufferedImage[currentPool.size()];
         for(int j = 2; j < cur.length; j++) {
             cur[j] = ImageIO.read(new File(currentPool.get(j)));
             cur[j] = resize(cur[j], betweenWidth, betweenHeight);
         }
         mysticPool = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);   
         BufferedImage ml5Bg = ImageIO.read(new File("ML5HeroAni/"+CURRENT_MOONLIGHT_FIVE+"/"+currentML5Ani));
         cur[0] = ImageIO.read(new File(currentPool.get(0)));
         cur[1] = ImageIO.read(new File(currentPool.get(1)));
         cur[0] = resize(cur[0], betweenWidth+30, betweenHeight+30);
         cur[1] = resize(cur[1], betweenWidth+30, betweenHeight+30);
                  
         Graphics g = mysticPool.createGraphics();
         g.drawImage(ml5Bg, 0, 0, ml5Bg.getWidth()+150, ml5Bg.getHeight()+280, null); //ml 5 bg
         g.setFont(new Font("TimesRoman", Font.PLAIN, 21)); 
         double stringWidth = 0.0; //try to center the name with the pic
         if (CURRENT_MOONLIGHT_FIVE.length() > 9) 
              stringWidth = (startWidth+50.0) - (CURRENT_MOONLIGHT_FIVE.length()-9.0)*(betweenWidth+30.0)/18.0;
         else stringWidth = (startWidth+30.0) + (9.0-CURRENT_MOONLIGHT_FIVE.length())*(betweenWidth+30.0)/18.0;
         g.drawString(CURRENT_MOONLIGHT_FIVE, (int)stringWidth, startHeight-145+betweenWidth); //ml 5's name
         if (currentMoonlightFour.length() > 9) 
              stringWidth = (startWidth+330.0) - (currentMoonlightFour.length()-9.0)*(betweenWidth+30.0)/18.0;
         else stringWidth = (startWidth+330.0) + (9.0-currentMoonlightFour.length())*(betweenWidth+30.0)/18.0;
         g.drawString(currentMoonlightFour, (int)stringWidth, startHeight-145+betweenWidth); //ml 4's name
         g.drawString("Normal Heroes and Artifacts", startWidth + betweenWidth + gap, startHeight + (betweenHeight + gap*3)*3);
         
         g.drawImage(cur[0], startWidth+30,  startHeight-200, null); //ml 5
         g.drawImage(cur[1], startWidth+300, startHeight-200, null); //ml 4
         g.drawImage(cur[2],  startWidth,                          startHeight, null); //cov 5
         g.drawImage(cur[3],  startWidth + (betweenWidth + gap),   startHeight, null); //art 5
         g.drawImage(cur[4],  startWidth + (betweenWidth + gap)*2, startHeight, null); //cov 4
         g.drawImage(cur[5],  startWidth + (betweenWidth + gap)*3, startHeight, null); //cov 4
         g.drawImage(cur[6],  startWidth + (betweenWidth + gap)*4, startHeight, null); //cov 4
         g.drawImage(cur[7],  startWidth + betweenWidth/2 - (betweenWidth + gap),   startHeight + betweenHeight + gap, null); //cov 4
         g.drawImage(cur[8],  startWidth + betweenWidth/2,                          startHeight + betweenHeight + gap, null); //art 4
         g.drawImage(cur[9],  startWidth + betweenWidth/2 + (betweenWidth + gap),   startHeight + betweenHeight + gap, null); //art 4
         g.drawImage(cur[10], startWidth + betweenWidth/2 + (betweenWidth + gap)*2, startHeight + betweenHeight + gap, null); //art 4
         g.drawImage(cur[11], startWidth + betweenWidth/2 + (betweenWidth + gap)*3, startHeight + betweenHeight + gap, null); //cov 3
         g.drawImage(cur[12], startWidth + betweenWidth/2 + (betweenWidth + gap)*4, startHeight + betweenHeight + gap, null); //cov 3
         g.drawImage(cur[13], startWidth,                          startHeight + (betweenHeight + gap)*2, null); //cov 3
         g.drawImage(cur[14], startWidth + (betweenWidth + gap),   startHeight + (betweenHeight + gap)*2, null); //cov 3
         g.drawImage(cur[15], startWidth + (betweenWidth + gap)*2, startHeight + (betweenHeight + gap)*2, null); //art 3
         g.drawImage(cur[16], startWidth + (betweenWidth + gap)*3, startHeight + (betweenHeight + gap)*2, null); //art 3
         g.drawImage(cur[17], startWidth + (betweenWidth + gap)*4, startHeight + (betweenHeight + gap)*2, null); //art 3
         g.dispose();
     }
     
     private void customRateUpHeroImage(String who, String what) throws IOException 
     {
         BufferedImage heroImage = ImageIO.read(new File(who)); //get rate up hero  pic
         BufferedImage artifactImageIcon = null;
         BufferedImage artifactImage = null;
         BufferedImage croppedArtifactImage = null;
         if (!what.equals("")) {
             artifactImage = ImageIO.read(new File(what));    //get rate up artifact pic
             croppedArtifactImage = artifactImage.getSubimage(700,30,700,artifactImage.getHeight()-30); //cropping artifact image
             artifactImageIcon = ImageIO.read(new File("ART5Icon"+what.substring(what.indexOf("/"))));
         } 
         croppedHero = new BufferedImage(heroImage.getWidth(), heroImage.getHeight(), BufferedImage.TYPE_INT_ARGB);//declare main BufferedImage to create Graphic of 
         Graphics g = croppedHero.createGraphics();     //create graphics to draw in
         g.drawImage(heroImage, 0, 0, heroImage.getWidth()+1000, heroImage.getHeight()+500, null);  //draw hero to croppedHero(BufferedImage)
         if (!what.equals("")) {
             g.drawImage(croppedArtifactImage, 40, 270, 600, 900, null); //draw artifact 
             g.drawImage(artifactImageIcon, 650, 800, 210, 200, null);
         }
         g.dispose(); 
     }
     
     public void showInfo(String pic) throws IOException
     {
         JLabel picHolder = new JLabel();
         BufferedImage heroInfo = ImageIO.read(new File(pic));
         Image temp = heroInfo.getScaledInstance(getWidth()-100, getHeight()-100, Image.SCALE_SMOOTH);
         picHolder.setIcon(new ImageIcon(temp));
         Dialog popUp = new Dialog(picHolder, this);
         popUp.setPreferredSize(new Dimension(getWidth()-100, getHeight()-100));
     }
     
     public void skipSummonAnimation()
     {
         if (playSummonAnimation) {
             List<String> result;
             if (theSummonButton.noWhiteSkipAnimationBox.isSelected() && theSummonButton.auto == true) 
                result = new ArrayList(Arrays.asList(summonAnimation.get(summonAnimation.size()-1))); 
             else 
                result = new ArrayList(Arrays.asList("ART4Ani/cov00056.png", summonAnimation.get(summonAnimation.size()-1))); //white screen then result
             summonAnimation = result;
             currentAniPosition = 0; //reset playAnimation
         }
     }
     
     private double difWidth;
     private double difHeight;
     public void mouseClicked(MouseEvent e) 
     {   
         skipSummonAnimation();
         if (theSummonButton.CURRENTBUTTON.equals("mystic")) {
             difWidth = (double)screenWidth/this.getWidth();
             difHeight = (double)screenHeight/this.getHeight();
             try {
                 if (e.getX() >= (startWidth+betweenWidth/2-betweenWidth-gap)/difWidth && e.getX() <= (startWidth+betweenWidth*11/2+gap*4)/difWidth &&
                     e.getY() >= (startHeight-200)/difHeight && e.getY() <= (startHeight+betweenHeight*3+gap*2)/difHeight) {
                     if (e.getY() >= (startHeight-200)/difHeight && e.getY() <= (startHeight-170+betweenHeight)/difHeight) {
                         if (e.getX() >= (startWidth+30)/difWidth && e.getX() <= (startWidth+60+betweenWidth)/difWidth)
                             showInfo("ML5Star/"+poolInfo.get(0));
                         else if (e.getX() >= (startWidth+300)/difWidth && e.getX() <= (startWidth+330+betweenWidth)/difWidth)
                             showInfo("ML4Star/"+poolInfo.get(1));
                     }
                     else if (e.getY() >= startHeight/difHeight && e.getY() <= (startHeight+betweenHeight)/difHeight) {
                         if (e.getX() >= startWidth/difWidth && e.getX() <= (startWidth+betweenWidth)/difWidth )
                             showInfo("COV5Star/"+poolInfo.get(2));
                         else if (e.getX() >= (startWidth+betweenWidth+gap)/difWidth && e.getX() <= (startWidth+betweenWidth*2+gap)/difWidth)
                             showInfo("ART5Star/"+poolInfo.get(3));
                         else if (e.getX() >= (startWidth+(betweenWidth+gap)*2)/difWidth && e.getX() <= (startWidth+betweenWidth*3+gap*2)/difWidth)
                             showInfo("COV4Star/"+poolInfo.get(4));
                         else if (e.getX() >= (startWidth+(betweenWidth+gap)*3)/difWidth && e.getX() <= (startWidth+betweenWidth*4+gap*3)/difWidth)
                             showInfo("COV4Star/"+poolInfo.get(5));
                         else if (e.getX() >= (startWidth+(betweenWidth+gap)*4)/difWidth && e.getX() <= (startWidth+betweenWidth*5+gap*4)/difWidth)
                             showInfo("COV4Star/"+poolInfo.get(6));
                     }
                     else if (e.getY() >= (startHeight+betweenHeight+gap)/difHeight && e.getY() <= (startHeight+betweenHeight*2+gap)/difHeight) {
                         if (e.getX() >= (startWidth+betweenWidth/2-betweenWidth-gap)/difWidth && e.getX() <= (startWidth+betweenWidth/2-gap)/difWidth) 
                             showInfo("COV4Star/"+poolInfo.get(7));
                         else if (e.getX() >= (startWidth+betweenWidth/2)/difWidth && e.getX() <= (startWidth+betweenWidth*3/2)/difWidth)
                             showInfo("ART4Star/"+poolInfo.get(8));
                         else if (e.getX() >= (startWidth+betweenWidth*3/2+gap)/difWidth && e.getX() <= (startWidth+betweenWidth*5/2+gap)/difWidth)
                             showInfo("ART4Star/"+poolInfo.get(9));
                         else if (e.getX() >= (startWidth+betweenWidth*5/2+gap*2)/difWidth && e.getX() <= (startWidth+betweenWidth*7/2+gap*2)/difWidth)
                             showInfo("ART4Star/"+poolInfo.get(10));
                         else if (e.getX() >= (startWidth+betweenWidth*7/2+gap*3)/difWidth && e.getX() <= (startWidth+betweenWidth*9/2+gap*3)/difWidth) 
                             showInfo("COV3Star/"+poolInfo.get(11));
                         else if (e.getX() >= (startWidth+betweenWidth*9/2+gap*4)/difWidth && e.getX() <= (startWidth+betweenWidth*11/2+gap*4)/difWidth) 
                             showInfo("COV3Star/"+poolInfo.get(12));
                     }
                     else if (e.getY() >= (startHeight+(betweenHeight+gap)*2)/difHeight && e.getY() <= (startHeight+betweenHeight*3+gap*2)/difHeight) {
                         if (e.getX() >= startWidth/difWidth && e.getX() <= (startWidth+betweenWidth)/difWidth )
                             showInfo("COV3Star/"+poolInfo.get(13));
                         else if (e.getX() >= (startWidth+betweenWidth+gap)/difWidth && e.getX() <= (startWidth+betweenWidth*2+gap)/difWidth)
                             showInfo("COV3Star/"+poolInfo.get(14));
                         else if (e.getX() >= (startWidth+(betweenWidth+gap)*2)/difWidth && e.getX() <= (startWidth+betweenWidth*3+gap*2)/difWidth)
                             showInfo("ART3Star/"+poolInfo.get(15));
                         else if (e.getX() >= (startWidth+(betweenWidth+gap)*3)/difWidth && e.getX() <= (startWidth+betweenWidth*4+gap*3)/difWidth)
                             showInfo("ART3Star/"+poolInfo.get(16));
                         else if (e.getX() >= (startWidth+(betweenWidth+gap)*4)/difWidth && e.getX() <= (startWidth+betweenWidth*5+gap*4)/difWidth)
                             showInfo("ART3Star/"+poolInfo.get(17));
                     }
                 }    
             } catch(Exception f) { System.out.println("Check mystic mouse click method"); }
         }//if current button is mystic
     }
     
     public void mouseEntered(MouseEvent e) {}
     public void mouseExited(MouseEvent e) {}
     public void mousePressed(MouseEvent e) {}
     public void mouseReleased(MouseEvent e) {}
     
     public void getDropRateUpBanner()
     {
         cov.RATEUPHERO = DROP_RATE_UP_HERO; //pass the chosen rate up hero to COVMechanics class
         dropRateUp.setText("Drop Rate Up: " + DROP_RATE_UP_HERO); //Add Hero's name to button
         if (forMain.withRateUpArtifact(DROP_RATE_UP_HERO)) {
             cov.LIMITEDARTIFACT = true;
             try { //draw rate up hero with the rate up artifact
                 customRateUpHeroImage(covenantFiveRateUp.get(forMain.findImageLocation(DROP_RATE_UP_HERO, "COV5Star")), 
                                       artifactFiveRateUp.get(forMain.findImageLocation(forMain.getRateUpArtifact(DROP_RATE_UP_HERO), "ART5Star")));
             } catch (IOException exception) { 
                 JOptionPane.showMessageDialog(this, "Image is not found, check image's name" + DROP_RATE_UP_HERO, "IMAGE_ERROR", JOptionPane.ERROR_MESSAGE);
               }
         }
         else { //hero with no rate up artifact   
             cov.LIMITEDARTIFACT = false;
             try { //draw only the hero image    
                 customRateUpHeroImage(covenantFiveRateUp.get(forMain.findImageLocation(DROP_RATE_UP_HERO, "COV5Star")), "");
             } catch (Exception excep) {
                 JOptionPane.showMessageDialog(this, "Image is not found, check image's name" + DROP_RATE_UP_HERO, "IMAGE_ERROR", JOptionPane.ERROR_MESSAGE);
               }
         }
         playRateUpHeroBg(); //Passing rate up images to the bg drawing mechanics
         if (!currentRateUpHero.equals(DROP_RATE_UP_HERO)) currentRateUpSummonLeft = 120;  //if change to different hero- reset pitty summon
         theSummonButton.setPityTextBottom(currentRateUpSummonLeft, DROP_RATE_UP_HERO);    //display initial or resetted pitty summon(bottom)
         setPityTextTop(currentRateUpSummonLeft, DROP_RATE_UP_HERO);                       //display initial or resetted pitty summon(top)
         if (currentRateUpSummonLeft == 0) setPityTextTop(DROP_RATE_UP_HERO + " Garanteed Summon!");           //if stored summon = 0 
         currentRateUpHero = DROP_RATE_UP_HERO;   //hold the current chosen hero
     }
     
     public List<String> getSummonAnimationResult() 
     {
         return summonAnimation;
     }
     
     public void changeAniSpd(int speed)
     {
         clock2 = new Timer(speed, this);
     }
     
     public void setDropRateUpText(String text)
     {
         dropRateUp.setText(text);
     }
     
     public void disablePityTextTop(boolean state)
     {
         pityTextTop.setVisible(state);
     }
     
     public void setPityTextTop(String hero)
     {
         pityTextTop.setText(hero);
     }
     
     public void setPityTextTop(int count, String hero) 
     {
         pityTextTop.setText(count + " summon(s) left until " + hero + " Garanteed Summon");
     }
     
     public void playRateUpHeroBg()
     {
         which = new ImageIcon(croppedHero);
     }
     
     public void playPool()
     {
         which = new ImageIcon(mysticPool);
     }
     
     public void disableBgReplayAnimation(boolean state)
     {
         bgReplayAnimation = state;
         if (!state) clock.stop();
         else clock.start();
     }
     
     public void setSummonAnimation(List<String> what)
     {
         summonAnimation = what;
     }
     
     public void clearMysticAnimation()
     {
         mysticBgAnimation = new ArrayList();
     }
     
     public void setMysticAnimation(BufferedImage what)
     {
         mysticBgAnimation.add(what);
     }
     
     public void playSummonAnimation(boolean state)
     {
         playSummonAnimation = state;
         if (state == true) clock2.start();
         else clock2.stop();
     }
     
     public void disableBannerButtons(boolean state)
     {
         eastPanel.setVisible(state);
     }
}
