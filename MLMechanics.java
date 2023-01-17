import java.util.List;
public class MLMechanics extends SUMMechanics
{  
    public static boolean GOTMOONLIGHTFIVE = false;
    public List<String> summonMoonLight()
    {  
        int totalChance = (int)(Math.random() * 1000);
        if (totalChance < 25) {
            GOTMOONLIGHTFIVE = true;
            return summon(fiveStarResult, moonlightFive); //2.50% 
        }
        else if (totalChance < 300) return summon(fourStarResult, moonlightFour); //27.50% 
        else return summon(threeStarResult, moonlightThree); //70.00% 
    }
} //class closing
