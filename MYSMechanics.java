import java.util.List;
import java.io.File;
import java.util.ArrayList;
public class MYSMechanics extends SUMMechanics
{   
    public static boolean GUARANTEEDFIVESTAR = false;
    public static boolean GOTCURRENTMOONLIGHT = false;
    public static boolean GOTTHEHERO = false;
    
    private static String currentMoonlightFive;
    private static String currentMoonlightFour;
    private static String currentCovenantFive;
    private static String[] currentCovenantFour = new String[4];
    private static String[] currentCovenantThree = new String[3];
    private static String currentArtifactFive;
    private static String[] currentArtifactFour = new String[3];
    private static String[] currentArtifactThree = new String[3];
    
    private List<String> ml5 = new ArrayList(moonlightFive);
    private List<String> ml4 = new ArrayList(moonlightFour);
    private List<String> cov5 = new ArrayList(covenantFive);
    private List<String> art5 = new ArrayList(artifactFive);
    
    private String[] pick(List<String> type, int howMany)
    {
        List<String> list = new ArrayList(type);
        String[] picked = new String[howMany];
        for(int j = 0; j < picked.length; j++) {
            int ran = (int)(Math.random() * list.size());
            picked[j] = list.get(ran);
            list.remove(ran);
        }
        return picked;
    }
    
    public List<String> pickCurrentPool() 
    { 
        if (ml5.size() == 0) ml5 = new ArrayList(moonlightFive);
        if (ml4.size() == 0) ml4 = new ArrayList(moonlightFour);
        if (cov5.size() == 0) cov5 = new ArrayList(covenantFive);
        if (art5.size() == 0) art5 = new ArrayList(artifactFive);
        List<String> curPool = new ArrayList();
        int random = (int)(Math.random() * ml5.size());
        currentMoonlightFive = ml5.get(random);
        ml5.remove(random);
        random = (int)(Math.random() * ml4.size());
        currentMoonlightFour = ml4.get((int)(Math.random() * ml4.size()));
        ml4.remove(random);
        random = (int)(Math.random() * cov5.size());
        currentCovenantFive = cov5.get((int)(Math.random() * cov5.size()));
        cov5.remove(random);
        currentCovenantFour = pick(covenantFour, 4);
        currentCovenantThree = pick(covenantThree, 4);
        random = (int)(Math.random() * art5.size());
        currentArtifactFive = art5.get((int)(Math.random() * art5.size()));
        art5.remove(random);
        currentArtifactFour = pick(artifactFour, 3);
        currentArtifactThree = pick(artifactThree, 3);
        curPool.add("ML5Icon/" + currentMoonlightFive);
        curPool.add("ML4Icon/" + currentMoonlightFour);
        curPool.add("COV5Icon/" + currentCovenantFive);
        curPool.add("ART5Icon/" + currentArtifactFive); 
        for(int j = 0; j < currentCovenantFour.length; j++) curPool.add("COV4Icon/" + currentCovenantFour[j]);
        for(int j = 0; j < currentArtifactFour.length; j++) curPool.add("ART4Icon/" + currentArtifactFour[j]);
        for(int j = 0; j < currentCovenantThree.length; j++) curPool.add("COV3Icon/" + currentCovenantThree[j]);
        for(int j = 0; j < currentArtifactThree.length; j++) curPool.add("ART3Icon/" + currentArtifactThree[j]);
        for(int j = 0; j < curPool.size(); j++)
            curPool.set(j, curPool.get(j).substring(0, curPool.get(j).indexOf("/")) + curPool.get(j).substring(curPool.get(j).lastIndexOf("/")));
            //Change from ML5Icon/ML5Star/HeroName.JPG to ML5Icon/HeroName.JPG
        return curPool;
    }
    
    public List<String> summonMystic()
    {
        int totalChance = (int)(Math.random() * 100000);
        if (totalChance <= 625 || GUARANTEEDFIVESTAR == true) { //0.625% ml 5
            GOTTHEHERO = true;
            GUARANTEEDFIVESTAR = false;
            GOTCURRENTMOONLIGHT = true;
            return summon(fiveStarResult, currentMoonlightFive); 
        }
        else if (totalChance <= 1250) return summon(covenantFiveResult, currentCovenantFive); //0.625 cov 5
        else if (totalChance <= 2150) return summon(fourStarResult, currentMoonlightFour); //0.9% ml 4
        else if (totalChance <= 3900) return summon(artifactFiveResult, currentArtifactFive); //1.75% art 5
        else if (totalChance <= 7500) { //3.6% cov 4
            int ran = (int)(Math.random() * currentCovenantFour.length);
            return summon(covenantFourResult, currentCovenantFour[ran]);
        } 
        else if (totalChance <= 14000) {//6.5% art 4
            int ran = (int)(Math.random() * currentArtifactFour.length);
            return summon(artifactFourResult, currentArtifactFour[ran]);
        }
        else if (totalChance <= 55000) {//41% cov 3
            int ran = (int)(Math.random() * currentCovenantThree.length);
            return summon(covenantThreeResult, currentCovenantThree[ran]);
        }
        else {//45% art 3
            int ran = (int)(Math.random() * currentArtifactThree.length);
            return summon(artifactThreeResult, currentArtifactThree[ran]);
        }
    }
    
    private List<String> summon(List<String> typeResult, String typeList) //mystic summon
    {
        typeResult.remove(typeResult.size()-1);
        typeResult.add(typeList);
        return typeResult;
    }
}
