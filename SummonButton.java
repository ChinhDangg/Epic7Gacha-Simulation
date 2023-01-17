import java.util.List;
import java.util.ArrayList;
import javax.swing.JCheckBox;
import javax.swing.JScrollPane;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.BorderFactory;
import javax.swing.SwingUtilities;
import javax.swing.SwingConstants;
import java.awt.RenderingHints;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Color;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.File;
public class SummonButton extends JPanel implements ActionListener, ItemListener
{
    MainPanel mainPanel;
    
    public static String CURRENTBUTTON = "covenant";
    
    JLabel garanteedSummonLabel = new JLabel();
    JButton summonButton = new JButton("5 Summon");
    JButton backButton = new JButton("Back");
    JButton refreshButton = new JButton("Refresh");
    JButton autoButton = new JButton("Auto");
    JButton stopAutoButton = new JButton("Stop");
    JButton bannerInfoButton = new JButton();
        JButton rateUpArtifactButton = new JButton();
        JButton rateUpHeroButton = new JButton();
    JButton summonResultButton = new JButton();
    JButton selectAutoOptionButton = new JButton("Select");
    
    //Result Panel 
    JButton heroResultButton = new JButton("Hero");
    JButton artifactResultButton = new JButton("Artifact");
    JButton clearResultButton = new JButton("Clear");
    private int summonCount = 0;
    JLabel summonCountLabel = new JLabel("Summon Count: " + summonCount);
    
    JButton decreAniSpdButton = new JButton("-");
    JButton increAniSpdButton = new JButton("+");
    JLabel summonSpeedText = new JLabel();
    private int currentAniSpd = mainPanel.animationSpeed;
    
    Information infoButton = new Information();
    MLMechanics ml = new MLMechanics();
    COVMechanics cov = new COVMechanics();
    MYSMechanics mys = new MYSMechanics();
    
    ImageIcon moonlightIcon = getCurrencyImage("Pic/moonIcon.JPG",70,70);
    ImageIcon galaxySummon = getCurrencyImage("Pic/galaxyBookmark.png",30,30);
    ImageIcon covenantIcon = getCurrencyImage("Pic/covenantIcon.JPG",70,70);
    ImageIcon covenantSummon = getCurrencyImage("Pic/covenantBookmark.png",30,30);
    ImageIcon mysticIcon = getCurrencyImage("Pic/mysticIcon.JPG", 70,70);
    ImageIcon mysticSummon = getCurrencyImage("Pic/mysticMedal.jpeg", 30,30);
    ImageIcon bannerInfoIcon = getCurrencyImage("Pic/bannerInfo.JPG", 30, 30);
    ImageIcon summonResultIcon = getCurrencyImage("Pic/summonedlist.JPG", 30, 30);
    
    ImageIcon rateUpArtifactIcon;
    ImageIcon rateUpHeroIcon;
    
    private String[] heroFiveStarList = infoButton.getFiveStarList("COV5Star");
    
    JScrollPane scroller = new JScrollPane();
    private String heroChoice = "";
    private List<String> cov5Icon = infoButton.getIcon("COV5Icon");
    private boolean ignoreConfirmation = false;
    public static boolean auto = false;
    
    public static JCheckBox noLimitBox = new JCheckBox("No limit Summon", true);
    public static JCheckBox untilGotTheHeroBox = new JCheckBox("Summon until got the Hero", false);
    public static JCheckBox skipSummonAnimationBox = new JCheckBox("Skip Summon Animation", false);
    public static JCheckBox untilGotTheArtifactBox = new JCheckBox("Summon until got the Artifact", false);
    public static JCheckBox untilGotMoonlightFiveBox = new JCheckBox("Summon until got ML 5", false);
    public static JCheckBox noWhiteSkipAnimationBox = new JCheckBox("No white screen skip animation", true);
    
    Dialog autoSummonOptionDialog;
    
    JPanel resultPanel = new JPanel();
    JPanel allResultPanel = new JPanel();
    JScrollPane resultScroller = new JScrollPane();
                                
    private List<String> artifactResult = new ArrayList();
    private List<String> heroResult = new ArrayList();
    private List<Integer> specificArtifactCount = new ArrayList();
    private List<Integer> specificHeroCount = new ArrayList(); 
    private List<String> fiveStarHeroResult = new ArrayList();
    private List<String> fourStarHeroResult = new ArrayList();
    private List<String> threeStarHeroResult = new ArrayList();
    private List<String> fiveStarArtifactResult = new ArrayList();
    private List<String> fourStarArtifactResult = new ArrayList();
    private List<String> threeStarArtifactResult = new ArrayList();
    private List<Integer> fiveStarHeroCount = new ArrayList();
    private List<Integer> fourStarHeroCount = new ArrayList();
    private List<Integer> threeStarHeroCount = new ArrayList();
    private List<Integer> fiveStarArtifactCount = new ArrayList();
    private List<Integer> fourStarArtifactCount = new ArrayList();
    private List<Integer> threeStarArtifactCount = new ArrayList();
    
    JCheckBox fiveStarCheckBox = new JCheckBox("Show Five Star", true);
    JCheckBox fourStarCheckBox = new JCheckBox("Show Four Star", true);
    JCheckBox threeStarCheckBox = new JCheckBox("Show Three Star", true);
    
    private String currentResultShow = "hero";
    
    public SummonButton(MainPanel mainPanel)
    {
        add(backButton);
        backButton.addActionListener(this);
        this.mainPanel = mainPanel;
        backButton.setVisible(false);
        
        decreAniSpdButton.addActionListener(this);
        increAniSpdButton.addActionListener(this);
        
        heroResultButton.addActionListener(this);
        heroResultButton.setBackground(Color.WHITE);
        heroResultButton.setForeground(Color.BLACK);
        artifactResultButton.addActionListener(this);
        artifactResultButton.setBackground(Color.BLACK);
        artifactResultButton.setForeground(Color.WHITE);
        clearResultButton.addActionListener(this);
        clearResultButton.setBackground(Color.GRAY);
              
        noLimitBox.addItemListener(this);
        untilGotTheHeroBox.addItemListener(this);
        untilGotTheArtifactBox.addItemListener(this);
        untilGotMoonlightFiveBox.addItemListener(this);
        skipSummonAnimationBox.addItemListener(this);
        noWhiteSkipAnimationBox.addItemListener(this);
        noWhiteSkipAnimationBox.setEnabled(skipSummonAnimationBox.isSelected());
        
        fiveStarCheckBox.addItemListener(this);
        fourStarCheckBox.addItemListener(this);
        threeStarCheckBox.addItemListener(this);
        
        add(Box.createHorizontalGlue()); //separate the the back button and summon again button
        setBorder(BorderFactory.createEmptyBorder(0,8,8,8)); //create borders between buttons
        
        selectAutoOptionButton.addActionListener(this);
        autoButton.addActionListener(this);
        stopAutoButton.addActionListener(this);
        stopAutoButton.setVisible(false);
        summonResultButton.addActionListener(this);
        summonResultButton.setIcon(summonResultIcon);
        summonResultButton.setBackground(Color.BLACK);
        bannerInfoButton.addActionListener(this);
        bannerInfoButton.setIcon(bannerInfoIcon);
        bannerInfoButton.setBackground(Color.BLACK);
        bannerInfoButton.setVisible(false);
            rateUpArtifactButton.addActionListener(this);
            rateUpArtifactButton.setBackground(Color.BLACK);
            rateUpArtifactButton.setForeground(Color.WHITE);
            rateUpHeroButton.addActionListener(this);       
            rateUpHeroButton.setBackground(Color.BLACK);
            rateUpHeroButton.setForeground(Color.WHITE);
        refreshButton.addActionListener(this);
        refreshButton.setVisible(false);
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setBackground(Color.BLACK);
        garanteedSummonLabel.setOpaque(true);
        garanteedSummonLabel.setBackground(Color.WHITE);
        garanteedSummonLabel.setVisible(false);
        summonButton.addActionListener(this);
        summonButton.setIcon(covenantSummon); //default-first summon = covenant         
        summonButton.setBackground(Color.BLACK);
        summonButton.setForeground(Color.WHITE);
        add(refreshButton);
        add(stopAutoButton);
        add(autoButton);
        add(garanteedSummonLabel);
        add(summonButton);
        add(bannerInfoButton);
        add(summonResultButton);
        this.validate();   
    }
    
    public ImageIcon test = new ImageIcon("ML5Icon/Judge Kise.JPG");
    
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == summonButton) {
            ignoreConfirmation = false;
            mainPanel.changeAniSpd(150);
            summonBanner(CURRENTBUTTON);
        }
        else if (e.getSource() == backButton) {
            if (CURRENTBUTTON.equals("covenant") || CURRENTBUTTON.equals("moonlight")) {
                mainPanel.disableBgReplayAnimation(true); //play the bg
                changeSummonText("5 Summon");  //Change button text from "Summon Again" to "Summon"
            }
            else if (CURRENTBUTTON.equals("dropRateUp")) {
                mainPanel.disableBgReplayAnimation(false);
                mainPanel.playRateUpHeroBg();
                mainPanel.disablePityTextTop(true);
                disableRefreshButton(true);
                disablePityTextBottom(false);
                disableBannerInfoButton(true);
                changeSummonText("5 Summon");  
            }
            else if (CURRENTBUTTON.equals("mystic")) {
                changeSummonText("50 Summon");
                disablePityTextBottom(false);
                disableRefreshButton(true);
                mainPanel.disablePityTextTop(true);
                mainPanel.disableBgReplayAnimation(true);
            }
            summonButton.setVisible(true);
            autoButton.setVisible(true);
            summonResultButton.setVisible(true);
            this.setLayout(new FlowLayout());      //To make summon button in middle
            mainPanel.disableBannerButtons(true);  //display the banner buttons
            backButton.setVisible(false);                //disable the back button when go to main screen
        }
        else if (e.getSource() == autoButton) {
            ignoreConfirmation = true;
            increAniSpdButton.setEnabled(skipSummonAnimationBox.isSelected());
            decreAniSpdButton.setEnabled(skipSummonAnimationBox.isSelected());
            JLabel autoOptionsText = new JLabel("Auto Options:");
            JPanel optionsPanel = new JPanel();
            optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.PAGE_AXIS));
            optionsPanel.add(autoOptionsText);
            optionsPanel.add(noLimitBox);
            JPanel summonSpeedPanel = new JPanel();
            summonSpeedText.setText("Summon Speed: " + currentAniSpd);
            summonSpeedPanel.add(summonSpeedText);
            summonSpeedPanel.add(decreAniSpdButton);
            summonSpeedPanel.add(increAniSpdButton);
            
            if (CURRENTBUTTON.equals("dropRateUp") || CURRENTBUTTON.equals("mystic")) 
                optionsPanel.add(untilGotTheHeroBox);
            if (CURRENTBUTTON.equals("dropRateUp")) 
                optionsPanel.add(untilGotTheArtifactBox);
            if (CURRENTBUTTON.equals("covenant") || CURRENTBUTTON.equals("moonlight"))
                optionsPanel.add(untilGotMoonlightFiveBox);      
            optionsPanel.add(skipSummonAnimationBox);
            optionsPanel.add(noWhiteSkipAnimationBox);
            optionsPanel.add(summonSpeedPanel);
            optionsPanel.add(selectAutoOptionButton);
            autoSummonOptionDialog = new Dialog(optionsPanel);
            autoSummonOptionDialog.setPreferredSize(new Dimension(300,300));
            autoSummonOptionDialog.setLocationRelativeTo(mainPanel);
        }
        else if (e.getSource() == increAniSpdButton) {
            currentAniSpd += 100;
            if (currentAniSpd > 1000) currentAniSpd = 1;
            summonSpeedText.setText("Summon Speed: " + currentAniSpd);
        }
        else if (e.getSource() == decreAniSpdButton) {
            currentAniSpd--;
            if (currentAniSpd < 1) currentAniSpd = 1000;
            summonSpeedText.setText("Summon Speed: " + currentAniSpd);
        }
        else if (e.getSource() == selectAutoOptionButton) {
            if (noLimitBox.isSelected() || untilGotTheHeroBox.isSelected() || untilGotTheArtifactBox.isSelected() || 
                untilGotMoonlightFiveBox.isSelected()) {
                mainPanel.changeAniSpd(currentAniSpd);
                auto = true;
                cov.GOTMOONLIGHTFIVE = false;
                cov.GOTRATEUPHERO = false;
                cov.GOTRATEUPARTIFACT = false;
                ml.GOTMOONLIGHTFIVE = false;
                mys.GOTCURRENTMOONLIGHT = false;
                stopAutoButton.setVisible(true);
                summonBanner(CURRENTBUTTON);
                autoSummonOptionDialog.setVisible(false);
            }
        }
        else if (e.getSource() == stopAutoButton) stopAuto();
        else if (e.getSource() == refreshButton) {
            if (CURRENTBUTTON.equals("mystic")) {
                try {
                    refreshMysticPool();
                } catch(IOException eh) {
                    JOptionPane.showMessageDialog(this, "AT REFRESH - DIFFERENCES IN IMAGES' NAME", "ERROR", JOptionPane.ERROR_MESSAGE);
                  }
                mainPanel.disablePityTextTop(true);
                setPityTextBottom(mainPanel.currentMysticSummonLeft, mainPanel.CURRENT_MOONLIGHT_FIVE);  //display initial or resetted pitty summon
                mainPanel.setPityTextTop(mainPanel.currentMysticSummonLeft, mainPanel.CURRENT_MOONLIGHT_FIVE);
                if (mainPanel.currentMysticSummonLeft == 0) mainPanel.setPityTextTop(mainPanel.CURRENT_MOONLIGHT_FIVE + " Garanteed Summon!");
            }
            else { //rate up refresh
                /**
                mainPanel.DROP_RATE_UP_HERO = (String)JOptionPane.showInputDialog(null, "Pick a hero for drop rate up: 1%", 
                                 "Drop Rate Up Hero Selection", JOptionPane.PLAIN_MESSAGE, null, heroFiveStarList, heroFiveStarList[0]);
                */
                mainPanel.DROP_RATE_UP_HERO = pickRateUpHero();
                mainPanel.getDropRateUpBanner();
            }
        }
        else if (e.getSource() == bannerInfoButton) {
            if (CURRENTBUTTON.equals("dropRateUp")) {
                rateUpHeroIcon = getCurrencyImage("COV5Icon/"+mainPanel.DROP_RATE_UP_HERO+".JPG", 70, 70);
                rateUpHeroButton.setIcon(rateUpHeroIcon);
                rateUpHeroButton.setText(mainPanel.DROP_RATE_UP_HERO);
                JPanel content = new JPanel();
                content.setBackground(Color.BLACK);
                content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
                content.setPreferredSize(new Dimension(200,160));
                if (cov.LIMITEDARTIFACT) {
                    rateUpArtifactIcon = getCurrencyImage("ART5Icon/"+infoButton.getRateUpArtifact(mainPanel.DROP_RATE_UP_HERO)+".JPG", 70, 70);
                    rateUpArtifactButton.setIcon(rateUpArtifactIcon);
                    rateUpArtifactButton.setText(infoButton.getRateUpArtifact(mainPanel.DROP_RATE_UP_HERO));
                    content.add(rateUpArtifactButton);
                }
                content.add(rateUpHeroButton);
                Dialog rateUpInfo = new Dialog(content);
                rateUpInfo.setLocationRelativeTo(mainPanel);
            }
        }
        else if (e.getSource() == rateUpHeroButton) {
            try {
                mainPanel.showInfo("COV5Star/"+mainPanel.DROP_RATE_UP_HERO+".JPG");
            }
            catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "AT Summon Button Class-rateUpHeroButton", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
        else if (e.getSource() == rateUpArtifactButton) {
            try {
                mainPanel.showInfo("ART5Star/"+infoButton.getRateUpArtifact(mainPanel.DROP_RATE_UP_HERO)+".JPG");
            }
            catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "AT Summon Button Class-rateUpArtifactButton", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
        else if (e.getSource() == summonResultButton) { 
            resultPanel.setPreferredSize(new Dimension(mainPanel.getWidth()-100,mainPanel.getHeight()-100));
            resultPanel.setBackground(Color.BLACK);
            resultPanel.setLayout(new BorderLayout());
            
            JPanel buttonsPanel = new JPanel();
            summonCountLabel.setForeground(Color.WHITE);
            buttonsPanel.setOpaque(false);
            buttonsPanel.add(heroResultButton);
            buttonsPanel.add(artifactResultButton);
            buttonsPanel.add(clearResultButton);
            buttonsPanel.add(summonCountLabel); 
            JPanel showPanel = new JPanel();
            showPanel.add(fiveStarCheckBox);
            showPanel.add(fourStarCheckBox);
            showPanel.add(threeStarCheckBox);
            showPanel.setOpaque(false);
            JPanel optionPanel = new JPanel();
            optionPanel.setLayout(new BorderLayout());
            optionPanel.add(buttonsPanel, BorderLayout.NORTH);
            optionPanel.add(showPanel, BorderLayout.SOUTH);
            optionPanel.setOpaque(false);

            setEnableResultCheckBox(true);
            if (heroResult.size() > artifactResult.size()) showSummonResult(heroResult, specificHeroCount);
            else showSummonResult(artifactResult, specificArtifactCount);
            resultPanel.add(optionPanel, BorderLayout.NORTH);
            resultPanel.add(resultScroller, BorderLayout.CENTER);
            
            JDialog resultDialog = new JDialog();
            resultDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            resultDialog.setModal(true);
            resultDialog.getContentPane().add(resultPanel);
            resultDialog.setResizable(true);
            resultDialog.pack();
            resultDialog.setLocationRelativeTo(mainPanel);
            resultDialog.setVisible(true);
        }
        else if (e.getSource() == heroResultButton) {
            heroResultButton.setBackground(Color.WHITE);
            heroResultButton.setForeground(Color.BLACK);
            artifactResultButton.setBackground(Color.BLACK);
            artifactResultButton.setForeground(Color.WHITE);
            setEnableResultCheckBox(true);
            currentResultShow = "hero";
            showSummonResult(heroResult, specificHeroCount);
        }
        else if (e.getSource() == artifactResultButton) {
            artifactResultButton.setBackground(Color.WHITE);
            artifactResultButton.setForeground(Color.BLACK);
            heroResultButton.setBackground(Color.BLACK);
            heroResultButton.setForeground(Color.WHITE);
            setEnableResultCheckBox(true);
            currentResultShow = "artifact";
            showSummonResult(artifactResult, specificArtifactCount);
        }
        else if (e.getSource() == clearResultButton) {
            allResultPanel = new JPanel();
            heroResult.clear();
            artifactResult.clear();
            specificArtifactCount.clear();
            specificHeroCount.clear();
            clearRarityResult();
            summonCount = 0;
            summonCountLabel.setText("Summon Count: " + summonCount);
            showSummonResult(heroResult, specificHeroCount);
            showSummonResult(artifactResult, specificArtifactCount);
        } 
    }
    
    private void clearRarityResult()
    {
        fiveStarHeroResult.clear();
        fourStarHeroResult.clear();
        threeStarHeroResult.clear();
        fiveStarArtifactResult.clear();
        fourStarArtifactResult.clear();
        threeStarArtifactResult.clear();
        fiveStarHeroCount.clear();
        fourStarHeroCount.clear();
        threeStarHeroCount.clear();
        fiveStarArtifactCount.clear();
        fourStarArtifactCount.clear();
        threeStarArtifactCount.clear();
    }
    
    private void setEnableResultCheckBox(boolean state) 
    {
        fiveStarCheckBox.setSelected(state);
        fourStarCheckBox.setSelected(state);
        threeStarCheckBox.setSelected(state);
    }
    
    private void getRarityResult() 
    {
        for (int j = 0; j < heroResult.size(); j++) {
            if (heroResult.get(j).contains("COV5") || heroResult.get(j).contains("ML5")) {
                fiveStarHeroResult.add(heroResult.get(j));
                fiveStarHeroCount.add(specificHeroCount.get(j));
            }
            else if (heroResult.get(j).contains("COV4") || heroResult.get(j).contains("ML4")) {
                fourStarHeroResult.add(heroResult.get(j));
                fourStarHeroCount.add(specificHeroCount.get(j));
            }
            else {
                threeStarHeroResult.add(heroResult.get(j));
                threeStarHeroCount.add(specificHeroCount.get(j));
            }
        }
        for (int j = 0; j < artifactResult.size(); j++) {
            if (artifactResult.get(j).contains("ART5")) {
                fiveStarArtifactResult.add(artifactResult.get(j));
                fiveStarArtifactCount.add(specificArtifactCount.get(j));
            }
            else if (artifactResult.get(j).contains("ART4")) {
                fourStarArtifactResult.add(artifactResult.get(j));
                fourStarArtifactCount.add(specificArtifactCount.get(j));
            }
            else {
                threeStarArtifactResult.add(artifactResult.get(j));
                threeStarArtifactCount.add(specificArtifactCount.get(j));
            }
        }
    }
    
    private void showSummonResult(List<String> type, List<Integer> typeCount) 
    {
        allResultPanel = new JPanel();
        clearRarityResult();
        getRarityResult(); 
        List<String> summonResult = new ArrayList(type);
        List<Integer> summonResultCount = new ArrayList(typeCount);
        allResultPanel.setLayout(new GridLayout(summonResult.size()/5+1,5));
        allResultPanel.setBackground(Color.BLACK);
            for (int j = 0; j < summonResult.size(); j++) {
                ImageIcon resultIcon = new ImageIcon(summonResult.get(j));
                BufferedImage scaledImage = getScaledImage(resultIcon, 70, 70);
                JLabel resultLabel = new JLabel(new ImageIcon(scaledImage));
                resultLabel.setForeground(Color.WHITE);
                resultLabel.setText("" + summonResultCount.get(j));
                allResultPanel.add(resultLabel);
            }
        resultScroller.setViewportView(allResultPanel);
        resultScroller.setOpaque(false);
    }
    
    private BufferedImage getScaledImage(ImageIcon srcImg, int w, int h) {
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg.getImage(), 0, 0, w, h, null);
        g2.dispose();
        return resizedImg;
    }
    
    private List<String> mergeStrList(List<String> list1, List<String> list2) 
    {
        List<String> newList = new ArrayList(list1);
        for (String j : list2) newList.add(j);
        return newList;
    }
    
    private List<Integer> mergeIntList(List<Integer> list1, List<Integer> list2) 
    {
        List<Integer> newList = new ArrayList(list1);
        for (int j : list2) newList.add(j);
        return newList;
    }
    
    public void itemStateChanged(ItemEvent e) 
    {
        if (e.getItem() == fiveStarCheckBox) {
            if (fiveStarCheckBox.isSelected() && !fourStarCheckBox.isSelected() && !threeStarCheckBox.isSelected()) 
                if (currentResultShow.equals("hero")) 
                    showSummonResult(fiveStarHeroResult, fiveStarHeroCount);
                else showSummonResult(fiveStarArtifactResult, fiveStarArtifactCount);
            else if (fiveStarCheckBox.isSelected() && fourStarCheckBox.isSelected() && !threeStarCheckBox.isSelected()) 
                if (currentResultShow.equals("hero")) 
                    showSummonResult(mergeStrList(fiveStarHeroResult, fourStarHeroResult), mergeIntList(fiveStarHeroCount, fourStarHeroCount));
                else 
                    showSummonResult(mergeStrList(fiveStarArtifactResult, fourStarArtifactResult), mergeIntList(fiveStarArtifactCount, fourStarArtifactCount));
            else if (fiveStarCheckBox.isSelected() && !fourStarCheckBox.isSelected() && threeStarCheckBox.isSelected()) 
                if (currentResultShow.equals("hero")) 
                    showSummonResult(mergeStrList(fiveStarHeroResult, threeStarHeroResult), mergeIntList(fiveStarHeroCount, threeStarHeroCount));
                else 
                    showSummonResult(mergeStrList(fiveStarArtifactResult, threeStarArtifactResult), mergeIntList(fiveStarArtifactCount, threeStarArtifactCount));
            else if (fiveStarCheckBox.isSelected() && fourStarCheckBox.isSelected() && threeStarCheckBox.isSelected())
                if (currentResultShow.equals("hero")) {
                    List<String> five4Str = mergeStrList(fiveStarHeroResult, fourStarHeroResult);
                    List<Integer> five4Int = mergeIntList(fiveStarHeroCount, fourStarHeroCount);
                    showSummonResult(mergeStrList(five4Str, threeStarHeroResult), mergeIntList(five4Int, threeStarHeroCount));
                }
                else {
                    List<String> five4Str = mergeStrList(fiveStarArtifactResult, fourStarArtifactResult);
                    List<Integer> five4Int = mergeIntList(fiveStarArtifactCount, fourStarArtifactCount);
                    showSummonResult(mergeStrList(five4Str, threeStarArtifactResult), mergeIntList(five4Int, threeStarArtifactCount));
                }
            else if (!fiveStarCheckBox.isSelected() && fourStarCheckBox.isSelected() && threeStarCheckBox.isSelected()) 
                if (currentResultShow.equals("hero")) 
                    showSummonResult(mergeStrList(fourStarHeroResult, threeStarHeroResult), mergeIntList(fourStarHeroCount, threeStarHeroCount));
                else 
                    showSummonResult(mergeStrList(fourStarArtifactResult, threeStarArtifactResult), mergeIntList(fourStarArtifactCount, threeStarArtifactCount));
            else if (!fiveStarCheckBox.isSelected() && fourStarCheckBox.isSelected() && !threeStarCheckBox.isSelected()) 
                if (currentResultShow.equals("hero")) 
                    showSummonResult(fourStarHeroResult, fourStarHeroCount);
                else 
                    showSummonResult(fourStarArtifactResult, fourStarArtifactCount);
            else if (!fiveStarCheckBox.isSelected() && !fourStarCheckBox.isSelected() && threeStarCheckBox.isSelected()) 
                if (currentResultShow.equals("hero"))
                    showSummonResult(threeStarHeroResult, threeStarHeroCount);
                else showSummonResult(threeStarArtifactResult, threeStarArtifactCount);
        }
        if (e.getItem() == fourStarCheckBox) {
            if (fourStarCheckBox.isSelected() && !fiveStarCheckBox.isSelected() && !threeStarCheckBox.isSelected()) 
                if (currentResultShow.equals("hero")) 
                    showSummonResult(fourStarHeroResult, fourStarHeroCount);
                else showSummonResult(fourStarArtifactResult, fourStarArtifactCount);
            else if (fourStarCheckBox.isSelected() && fiveStarCheckBox.isSelected() && !threeStarCheckBox.isSelected()) 
                if (currentResultShow.equals("hero")) 
                    showSummonResult(mergeStrList(fiveStarHeroResult, fourStarHeroResult), mergeIntList(fiveStarHeroCount, fourStarHeroCount));
                else 
                    showSummonResult(mergeStrList(fiveStarArtifactResult, fourStarArtifactResult), mergeIntList(fiveStarArtifactCount, fourStarArtifactCount));
            else if (fourStarCheckBox.isSelected() && !fiveStarCheckBox.isSelected() && threeStarCheckBox.isSelected()) 
                if (currentResultShow.equals("hero")) 
                    showSummonResult(mergeStrList(fourStarHeroResult, threeStarHeroResult), mergeIntList(fourStarHeroCount, threeStarHeroCount));
                else 
                    showSummonResult(mergeStrList(fourStarArtifactResult, threeStarArtifactResult), mergeIntList(fourStarArtifactCount, threeStarArtifactCount));
            else if (fourStarCheckBox.isSelected() && fiveStarCheckBox.isSelected() && threeStarCheckBox.isSelected()) 
                if (currentResultShow.equals("hero")) {
                    List<String> five4Str = mergeStrList(fiveStarHeroResult, fourStarHeroResult);
                    List<Integer> five4Int = mergeIntList(fiveStarHeroCount, fourStarHeroCount);
                    showSummonResult(mergeStrList(five4Str, threeStarHeroResult), mergeIntList(five4Int, threeStarHeroCount));
                }
                else {
                    List<String> five4Str = mergeStrList(fiveStarArtifactResult, fourStarArtifactResult);
                    List<Integer> five4Int = mergeIntList(fiveStarArtifactCount, fourStarArtifactCount);
                    showSummonResult(mergeStrList(five4Str, threeStarArtifactResult), mergeIntList(five4Int, threeStarArtifactCount));
                }
            else if (!fourStarCheckBox.isSelected() && fiveStarCheckBox.isSelected() && threeStarCheckBox.isSelected())
                if (currentResultShow.equals("hero")) 
                    showSummonResult(mergeStrList(fiveStarHeroResult, threeStarHeroResult), mergeIntList(fiveStarHeroCount, threeStarHeroCount));
                else 
                    showSummonResult(mergeStrList(fiveStarArtifactResult, threeStarArtifactResult), mergeIntList(fiveStarArtifactCount, threeStarArtifactCount));
            else if (!fourStarCheckBox.isSelected() && fiveStarCheckBox.isSelected() && !threeStarCheckBox.isSelected()) 
                if (currentResultShow.equals("hero")) 
                    showSummonResult(fiveStarHeroResult, fiveStarHeroCount);
                else showSummonResult(fiveStarArtifactResult, fiveStarArtifactCount);
            else if (!fourStarCheckBox.isSelected() && !fiveStarCheckBox.isSelected() && threeStarCheckBox.isSelected()) 
                if (currentResultShow.equals("hero"))
                    showSummonResult(threeStarHeroResult, threeStarHeroCount);
                else showSummonResult(threeStarArtifactResult, threeStarArtifactCount);
        }
        if (e.getItem() == threeStarCheckBox) {
            if (threeStarCheckBox.isSelected() && !fourStarCheckBox.isSelected() && !fiveStarCheckBox.isSelected()) 
                if (currentResultShow.equals("hero")) 
                    showSummonResult(threeStarHeroResult, threeStarHeroCount);
                else showSummonResult(threeStarArtifactResult, threeStarArtifactCount);
            else if (threeStarCheckBox.isSelected() && fiveStarCheckBox.isSelected() && !fourStarCheckBox.isSelected()) 
                if (currentResultShow.equals("hero")) 
                    showSummonResult(mergeStrList(fiveStarHeroResult, threeStarHeroResult), mergeIntList(fiveStarHeroCount, threeStarHeroCount));
                else 
                    showSummonResult(mergeStrList(fiveStarArtifactResult, threeStarArtifactResult), mergeIntList(fiveStarArtifactCount, threeStarArtifactCount));
            else if (threeStarCheckBox.isSelected() && fourStarCheckBox.isSelected() && !fiveStarCheckBox.isSelected()) 
                if (currentResultShow.equals("hero")) 
                    showSummonResult(mergeStrList(fourStarHeroResult, threeStarHeroResult), mergeIntList(fourStarHeroCount, threeStarHeroCount));
                else 
                    showSummonResult(mergeStrList(fourStarArtifactResult, threeStarArtifactResult), mergeIntList(fourStarArtifactCount, threeStarArtifactCount));
            else if (threeStarCheckBox.isSelected() && fourStarCheckBox.isSelected() && fiveStarCheckBox.isSelected()) 
                if (currentResultShow.equals("hero")) {
                    List<String> five4Str = mergeStrList(fiveStarHeroResult, fourStarHeroResult);
                    List<Integer> five4Int = mergeIntList(fiveStarHeroCount, fourStarHeroCount);
                    showSummonResult(mergeStrList(five4Str, threeStarHeroResult), mergeIntList(five4Int, threeStarHeroCount));
                }
                else {
                    List<String> five4Str = mergeStrList(fiveStarArtifactResult, fourStarArtifactResult);
                    List<Integer> five4Int = mergeIntList(fiveStarArtifactCount, fourStarArtifactCount);
                    showSummonResult(mergeStrList(five4Str, threeStarArtifactResult), mergeIntList(five4Int, threeStarArtifactCount));
                }
            else if (!threeStarCheckBox.isSelected() && fourStarCheckBox.isSelected() && fiveStarCheckBox.isSelected()) 
                if (currentResultShow.equals("hero")) 
                    showSummonResult(mergeStrList(fiveStarHeroResult, fourStarHeroResult), mergeIntList(fiveStarHeroCount, fourStarHeroCount));
                else 
                    showSummonResult(mergeStrList(fiveStarArtifactResult, fourStarArtifactResult), mergeIntList(fiveStarArtifactCount, fourStarArtifactCount));
            else if (!threeStarCheckBox.isSelected() && !fourStarCheckBox.isSelected() && fiveStarCheckBox.isSelected()) 
                if (currentResultShow.equals("hero"))
                    showSummonResult(fiveStarHeroResult, fiveStarHeroCount);
                else showSummonResult(fiveStarArtifactResult, fiveStarArtifactCount);
            else if (!threeStarCheckBox.isSelected() && fourStarCheckBox.isSelected() && !fiveStarCheckBox.isSelected())
                if (currentResultShow.equals("hero")) 
                    showSummonResult(fourStarHeroResult, fourStarHeroCount);
                else showSummonResult(fourStarArtifactResult, fourStarArtifactCount);
        }
        if (e.getItem() == noLimitBox) {
            if (untilGotTheHeroBox.isSelected()) 
                untilGotTheHeroBox.setSelected(false);
            if (untilGotTheArtifactBox.isSelected()) 
                untilGotTheArtifactBox.setSelected(false);
            if (untilGotMoonlightFiveBox.isSelected()) 
                untilGotMoonlightFiveBox.setSelected(false);
        }
        if (e.getItem() == untilGotTheHeroBox) {
            if (noLimitBox.isSelected()) 
                noLimitBox.setSelected(false);
            if (untilGotTheArtifactBox.isSelected()) 
                untilGotTheArtifactBox.setSelected(false);
        }
        if (e.getItem() == untilGotTheArtifactBox) {
            if (untilGotTheHeroBox.isSelected()) 
                untilGotTheHeroBox.setSelected(false);
            if (noLimitBox.isSelected()) 
                noLimitBox.setSelected(false);
        }
        if (e.getItem() == untilGotMoonlightFiveBox) {
            if (noLimitBox.isSelected()) 
                noLimitBox.setSelected(false);
        }
        if (e.getItem() == skipSummonAnimationBox) {
            noWhiteSkipAnimationBox.setEnabled(skipSummonAnimationBox.isSelected());
            increAniSpdButton.setEnabled(skipSummonAnimationBox.isSelected());
            decreAniSpdButton.setEnabled(skipSummonAnimationBox.isSelected());
            if (!skipSummonAnimationBox.isSelected()) currentAniSpd = 150;
        }
        if (e.getItem() == noWhiteSkipAnimationBox) {}
    }

    public void stopAuto()
    {
        ignoreConfirmation = false;
        auto = false;
        stopAutoButton.setVisible(false);
    }
    
    public void summonBanner(String banner) 
    {
        if (banner.equals("dropRateUp")) {
            summon("dropRateUp");
            if (cov.GOTTHEHERO == true) {
                mainPanel.currentRateUpSummonLeft = 120;
                setPityTextBottom(mainPanel.currentRateUpSummonLeft, mainPanel.DROP_RATE_UP_HERO);
                mainPanel.setPityTextTop(mainPanel.currentRateUpSummonLeft, mainPanel.DROP_RATE_UP_HERO);
                cov.GOTTHEHERO = false;
            }
        }
        else if (banner.equals("covenant")) summon("covenant");
        else if (banner.equals("moonlight")) summon("moonlight");
        else if (banner.equals("mystic")) {
            summon("mystic");
            if (mys.GOTTHEHERO == true) {
                mainPanel.currentMysticSummonLeft = 200;
                setPityTextBottom(mainPanel.currentMysticSummonLeft, mainPanel.CURRENT_MOONLIGHT_FIVE);
                mainPanel.setPityTextTop(mainPanel.currentMysticSummonLeft, mainPanel.CURRENT_MOONLIGHT_FIVE);
                mys.GOTTHEHERO = false;
            }
        }
    }
    
    public void refreshMysticPool() throws IOException
    {
        List<String> pool = mys.pickCurrentPool();
        mainPanel.poolInfo = new ArrayList(); //erase previous pool
        for (int j = 0; j < pool.size(); j++) mainPanel.poolInfo.add(pool.get(j).substring(pool.get(j).indexOf("/")+1));
        File ml5Ani = new File("ML5HeroAni/"+pool.get(0).substring(pool.get(0).indexOf("/")+1, pool.get(0).indexOf("."))); //file of ml 5 animation
        String[] fileName = ml5Ani.list();
        mainPanel.clearMysticAnimation();
        for (int j = 0; j < fileName.length; j++) {
            mainPanel.displayMysticPool(pool, fileName[j]);
            mainPanel.setMysticAnimation(mainPanel.mysticPool);
        }
    }
    
    public void setSummonIcon(String iconName)
    {
        if (iconName.equals("covenant")) 
            summonButton.setIcon(covenantSummon);
        else if (iconName.equals("moonlight")) 
            summonButton.setIcon(galaxySummon);
        else if (iconName.equals("mystic"))
            summonButton.setIcon(mysticSummon);
    }
    
    public void disableRefreshButton(boolean state)
    {
        refreshButton.setVisible(state);
    }
    
    public void corneredBackAndSummonButton()
    {
        this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS)); 
    }
    
    public void disablePityTextBottom(boolean state)
    {
        garanteedSummonLabel.setVisible(state);
    }
    
    public void disableBackButton(boolean state)
    {
        backButton.setVisible(state);
    }
    
    public void changeSummonText(String text)
    {
        summonButton.setText(text);
    }
    
    public void disableSummonButton(boolean state)
    {
        summonButton.setVisible(state);
    }
    
    public ImageIcon getCurrencyImage(String imageLoc, int xScale, int yScale)
    {
         return new ImageIcon(new ImageIcon(imageLoc).getImage().getScaledInstance(xScale,yScale, Image.SCALE_SMOOTH));
    }
    
    public void setPityTextBottom(int count, String hero) 
    {
        garanteedSummonLabel.setText(count + " summon(s) left until " + hero + " Garanteed Summon");
    }
    
    public void disableInfoButtons(boolean state) 
    {
        bannerInfoButton.setVisible(state);
        summonResultButton.setVisible(state); 
        autoButton.setVisible(state);
    }
    
    public void disableBannerInfoButton(boolean state)
    {
        bannerInfoButton.setVisible(state);
    }
    
    public String pickRateUpHero()
    {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.BLACK);
        buttonPanel.setLayout(new GridLayout(cov5Icon.size()/4+1,4));
        for(int j = 0; j < heroFiveStarList.length; j++) {
            JButton hero = new JButton(heroFiveStarList[j]);
            ImageIcon what = new ImageIcon(new ImageIcon(cov5Icon.get(j)).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
            hero.setVerticalTextPosition(SwingConstants.BOTTOM);
            hero.setHorizontalTextPosition(SwingConstants.CENTER); 
            hero.setBackground(Color.BLACK);
            hero.setIcon(what);
            hero.addActionListener(new ActionListener() {
                @Override 
                public void actionPerformed(ActionEvent e) {
                    heroChoice = e.getActionCommand();
                    JButton button = (JButton)e.getSource();
                    SwingUtilities.getWindowAncestor(button).dispose();
                   }
               });
               buttonPanel.add(hero);
           } 
           JDialog asking = new JDialog();
           asking.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
           asking.setPreferredSize(new Dimension(550, 400));
           asking.setTitle("Select Hero for rate up: 1%");
           asking.setModal(true);
           scroller.setViewportView(buttonPanel);
           asking.getContentPane().add(scroller);
           asking.setResizable(true);
           asking.pack();
           asking.setLocationRelativeTo(mainPanel);
           asking.setVisible(true);
           return heroChoice;
    }
    
    private static final int CANCEL = 0;
    private static final int CONFIRM = 1;
    private int choice = CANCEL;
    private Color orange = new Color(190,140,110);
    private int askForConfirmation(String type)
    {
        JButton cancel = new JButton("Cancel");
        cancel.addActionListener(new ActionListener() {
            @Override 
            public void actionPerformed(ActionEvent e) {
                choice = CANCEL;
                JButton button = (JButton)e.getSource();
                SwingUtilities.getWindowAncestor(button).dispose();
            }
        });
        JButton confirm = new JButton("Confirm");
        confirm.addActionListener(new ActionListener() {
            @Override 
            public void actionPerformed(ActionEvent e) {
                choice = CONFIRM;
                JButton button = (JButton)e.getSource();
                SwingUtilities.getWindowAncestor(button).dispose();
            }
        });
        
        JLabel question = new JLabel();
        JLabel typeText = new JLabel();
        JLabel current = new JLabel();
        if (type.equals("moonlight")) {
            current.setText("Current Galaxy Bookmarks: infinite");
            question.setText("Would you like to use Galaxy Bookmarks to summon?");
            typeText.setText("<html><font size='2' color=orange>Summon:</font><br/><font size='4' color=white>Galaxy<br/>Bookmarks</font></html>");
            typeText.setIcon(moonlightIcon);
        }
        else if (type.equals("covenant") || type.equals("dropRateUp")) {
            current.setText("Current Covenant Bookmarks: infinite");
            question.setText("Would you like to use Covenant Bookmarks to summon?");
            typeText.setText("<html><font size='2' color=orange>Summon:</font><br/><font size='4' color=white>Covenant<br/>Bookmarks</font></html>");
            typeText.setIcon(covenantIcon);
        }
        else if (type.equals("mystic")) {
            current.setText("Current Mystic Medals: infinite");
            question.setText("Would you like to use mystic medals to summon?");
            typeText.setText("<html><font size='2' color=orange>Summon:</font><br/><font size='4' color=white>Mystic Medals</font></html>");
            typeText.setIcon(mysticIcon);
        }
        JLabel summon = new JLabel("<html><font size='6' color=white>Summon</font></html>", SwingConstants.CENTER);
        
        JPanel buttons = new JPanel();
        cancel.setBackground(Color.LIGHT_GRAY);
        cancel.setForeground(Color.WHITE);
        buttons.setOpaque(false);
        buttons.add(cancel);
        buttons.add(confirm);
        
        JPanel contents = new JPanel(new GridLayout(5,1));
        contents.setBackground(Color.BLACK);
        current.setHorizontalAlignment(SwingConstants.CENTER);
        current.setForeground(orange);
        question.setHorizontalAlignment(SwingConstants.CENTER);
        question.setForeground(Color.GRAY);
        typeText.setHorizontalAlignment(SwingConstants.CENTER);
        contents.add(summon);
        contents.add(question);
        contents.add(typeText);
        contents.add(current);
        contents.add(buttons);
        
        JDialog asking = new JDialog();
        asking.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        asking.setPreferredSize(new Dimension(mainPanel.defaultWidth, mainPanel.defaultHeight-115));
        asking.setModal(true);
        asking.getContentPane().add(contents);
        asking.setUndecorated(true);
        asking.pack();
        asking.setLocationRelativeTo(mainPanel);
        asking.setVisible(true);
        
        return choice;
    }

    public void summon(String type)
    {
        int input = 0;
        if (!backButton.isVisible() && ignoreConfirmation == false) input = askForConfirmation(type);
            if (input == 1 || backButton.isVisible() || ignoreConfirmation == true) { //1 = Confirm or dont ask for confirmation to continue 
                if (type.equals("moonlight")) mainPanel.setSummonAnimation(ml.summonMoonLight()); //give the summon result to the mainpanel
                else if (type.equals("covenant")) mainPanel.setSummonAnimation(cov.summonCovenant());
                else if (type.equals("mystic")) {
                    mainPanel.setSummonAnimation(mys.summonMystic());
                    mainPanel.currentMysticSummonLeft--;
                    mainPanel.setPityTextTop(mainPanel.currentMysticSummonLeft, mainPanel.CURRENT_MOONLIGHT_FIVE);
                    setPityTextBottom(mainPanel.currentMysticSummonLeft, mainPanel.CURRENT_MOONLIGHT_FIVE);
                    if (mainPanel.currentMysticSummonLeft == 0) {
                        garanteedSummonLabel.setText(mainPanel.CURRENT_MOONLIGHT_FIVE + " Guaranteed Summon!");
                        mainPanel.setPityTextTop(mainPanel.CURRENT_MOONLIGHT_FIVE + " Guaranteed Summon!");
                        mys.GUARANTEEDFIVESTAR = true; 
                    }
                }
                else if (type.equals("dropRateUp")) { 
                    mainPanel.setSummonAnimation(cov.summonDropRateUp());
                    mainPanel.currentRateUpSummonLeft--;
                    mainPanel.setPityTextTop(mainPanel.currentRateUpSummonLeft, mainPanel.DROP_RATE_UP_HERO);
                    setPityTextBottom(mainPanel.currentRateUpSummonLeft, mainPanel.DROP_RATE_UP_HERO);
                    if (mainPanel.currentRateUpSummonLeft == 0) {
                        garanteedSummonLabel.setText(mainPanel.DROP_RATE_UP_HERO + " Guaranteed Summon!");
                        mainPanel.setPityTextTop(mainPanel.DROP_RATE_UP_HERO + " Guaranteed Summon!");
                        cov.GUARANTEEDFIVESTAR = true; 
                    }
                }
                summonCount++;
                summonCountLabel.setText("Summon Count: " + summonCount);
                String initialResult = mainPanel.getSummonAnimationResult().get(mainPanel.getSummonAnimationResult().size()-1);
                if (initialResult.contains("ART3")) initialResult = "ART3Icon" + initialResult.substring(initialResult.indexOf("/"));
                else if (initialResult.contains("ART4")) initialResult = "ART4Icon" + initialResult.substring(initialResult.indexOf("/"));
                else if (initialResult.contains("ART5")) initialResult = "ART5Icon" + initialResult.substring(initialResult.indexOf("/"));
                else if (initialResult.contains("COV3")) initialResult = "COV3Icon" + initialResult.substring(initialResult.indexOf("/"));
                else if (initialResult.contains("COV4")) initialResult = "COV4Icon" + initialResult.substring(initialResult.indexOf("/"));
                else if (initialResult.contains("COV5")) initialResult = "COV5Icon" + initialResult.substring(initialResult.indexOf("/"));
                else if (initialResult.contains("ML3")) initialResult = "ML3Icon" + initialResult.substring(initialResult.indexOf("/"));
                else if (initialResult.contains("ML4")) initialResult = "ML4Icon" + initialResult.substring(initialResult.indexOf("/"));
                else initialResult = "ML5Icon" + initialResult.substring(initialResult.indexOf("/"));
                boolean founded = false; //if result is already in the list or not
                //Keeping count of summoning results
                if (initialResult.contains("ART")) {
                    for (int j = 0; j < artifactResult.size(); j++) {
                        if (artifactResult.size() > 0 && artifactResult.get(j).equals(initialResult)) {
                            int temp = specificArtifactCount.get(j);
                            specificArtifactCount.set(j, temp + 1);
                            founded = true;
                        }
                    }
                    if (founded == false) {
                        artifactResult.add(initialResult);
                        specificArtifactCount.add(1);
                    }
                }
                else {
                    for (int j = 0; j < heroResult.size(); j++) {
                        if (heroResult.size() > 0 && heroResult.get(j).equals(initialResult)) {
                            int temp = specificHeroCount.get(j);
                            specificHeroCount.set(j, temp + 1);
                            founded = true;
                        }
                    }
                    if (founded == false) {
                        heroResult.add(initialResult);
                        specificHeroCount.add(1);
                    }
                } 
                mainPanel.playSummonAnimation(true); //allow the mainpanel to play the animation
                mainPanel.disableBgReplayAnimation(false); //stop the bg animation
            }
    }
}
