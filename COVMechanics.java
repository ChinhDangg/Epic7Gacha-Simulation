import java.util.List;
import java.util.ArrayList;
import java.io.File;
public class COVMechanics extends SUMMechanics
{  
    public static String RATEUPHERO = "";
    public static boolean LIMITEDARTIFACT = false; 
    public static boolean GUARANTEEDFIVESTAR = false;
    public static boolean GOTTHEHERO = false;
    public static boolean GOTRATEUPHERO = false;
    public static boolean GOTRATEUPARTIFACT = false;
    public static boolean GOTMOONLIGHTFIVE = false;
    
    private int countUntilML5 = 0;
    
    public List<String> summon(List<String> typeResult, List<String> typeList, String fileName) //rate up summon
    {
        typeResult.remove(typeResult.size()-1);
        if (fileName.equals("COV5Star")) typeResult.add(typeList.get(forSummon.findImageLocation(RATEUPHERO, fileName)));
        else typeResult.add(typeList.get(forSummon.findImageLocation(forSummon.getRateUpArtifact(RATEUPHERO), fileName)));
        return typeResult;
    }
   
    public List<String> summonCovenant()
    {  
        int totalChance = (int)(Math.random() * 10000);
        if (totalChance < 15) {
            GOTMOONLIGHTFIVE = true;
            countUntilML5 = 0;
            return summon(fiveStarResult, moonlightFive); //0.15% Moonlight 5 Hero
        }
        else if (totalChance < 65) return summon(fourStarResult, moonlightFour); //0.50% Moonlight 4 Hero
        else if (totalChance < 500) return summon(threeStarResult, moonlightThree); //4.35% Moonlight 3 Hero
        else if (totalChance < 625) return summon(covenantFiveResult, covenantFive); //1.25% Covenant 5 Hero
        else if (totalChance < 1075) return summon(covenantFourResult, covenantFour); //4.50% Covenant 4 Hero
        else if (totalChance < 5175) return summon(covenantThreeResult, covenantThree); //41.00% Covenant 3 Hero
        else if (totalChance < 5350) return summon(artifactFiveResult, artifactFive); //1.75% Artifact 5 
        else if (totalChance < 6000) return summon(artifactFourResult, artifactFour); //6.50% Artifact 4
        else return summon(artifactThreeResult, artifactThree); //40.00% Artifact 3 
    }
    
    private List<String> getNoRateUpArtifact() 
    {
        List<String> noRateUp = new ArrayList(artifactFive); //no limited artifact
        if (LIMITEDARTIFACT == false) return noRateUp;
        else noRateUp.remove(forSummon.getRateUpArtifact(RATEUPHERO));
        return noRateUp;
    }
    
    public List<String> summonDropRateUp()
    {
        int totalChance = (int)(Math.random() * 10000);
        if (totalChance < 100 || GUARANTEEDFIVESTAR == true) { //1.00% drop rate up hero
            GOTTHEHERO = true;
            GOTRATEUPHERO = true;
            GUARANTEEDFIVESTAR = false;
            return summon(covenantFiveResult, covenantFiveRateUp, "COV5Star"); 
        }
        else if (totalChance < 550) return summon(covenantFourResult, covenantFour);
        else if (totalChance < 4675) return summon(covenantThreeResult, covenantThree);
        else if (totalChance < 4850) { 
            if (LIMITEDARTIFACT == true) {
                if ((int)(Math.random() * 175) > 105) return summon(artifactFiveResult, getNoRateUpArtifact());
                else {
                    GOTRATEUPARTIFACT = true;
                    return summon(artifactFiveResult, artifactFiveRateUp, "ART5Star"); //1.05% - drop rate up artifact
                }
            }
            else return summon(artifactFiveResult, artifactFive); 
        }
        else if (totalChance < 5500) return summon(artifactFourResult, artifactFour);
        else return summon(artifactThreeResult, artifactThree);
    }
} //class closing
