import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.File;
public class Information 
{    
    private String[] currentLimitedHero = {
        "Diene",
        "Luna",
        "Baiken",
        "Dizzy",
        "Seaside Bellona",
        "Cerise",
        "Elphelt",
    };
    
    public String[] getLimitedHeroList()
    {
        return currentLimitedHero;
    }
    
    public List<String> getIcon(String fileName)
    {
        File folder = new File(fileName);
        return getImagesList(fileName, false, 0, folder.list().length);
    }
    
    public String[] getFiveStarList(String fileName) //get images' names without .JPG
    {
        File fiveList = new File(fileName);
        String[] five = fiveList.list();
        for(int j = 0; j < five.length; j++) 
            five[j] = five[j].substring(0, five[j].lastIndexOf("."));
        return five;
    }
    
    private final String[] rateUpArtifact = {
        "Alencia:Alencinox's Wrath",
        "Aramintha:Etica's Scepter",
        "Baiken:Torn Sleeve",
        "Basar:Abyssal Crown",
        "Bellona:Iron Fan",
        "Celine:Secret Art - Storm Sword",
        "Cerise:Guiding Light",
        "Cermia:Border Coin",
        "Charles:Justice for All",
        "Charlotte:Holy Sacrifice",
        "Destina:Shimadra Staff",
        "Diene:Celestine",
        "Dizzy:Necro & Undine",
        "Elena:Stella Harpa",
        "Elphelt:Ms. Confille",
        "Haste:Rhianna & Luciella",
        "Iseria:Song of Stars",
        "Kawerik:Black Hand of the Goddess",
        "Kise:Alabastron",
        "Krau:Holy Sacrifice",
        "Lidica:Sword of Judgement",
        "Lilias:Bastion of Perlutia",
        "Lilibet:Creation & Destruction",
        "Luluca:Spirit's Breath",
        "Luna:Draco Plate",
        "Melissa:Bloody Rose",
        "Pavel:Dux Noctis",
        "Ravi:Durandal",
        "Ray:Doctor's Bag",
        "Roana:Touch of Rekos",
        "Seaside Bellona:Reingar's Special Drink",
        "Sigret:Cradle of Life",
        "Tamarine:Idol's Cheer",
        "Tenebria:Time Matter",
        "Violet:Violet Talisman",
        "Vivian:Dignus Orb",
        "Yufine:Merciless Glutton",
    };
    
    public boolean withRateUpArtifact(String name) 
    {
        for (int j = 0; j < rateUpArtifact.length; j++) 
            if(rateUpArtifact[j].contains(name)) return true;
        return false;
    }
    
    public String getRateUpArtifact(String heroName) 
    {
        for (int j = 0; j < rateUpArtifact.length; j++)
            if (rateUpArtifact[j].contains(heroName)) 
                return rateUpArtifact[j].substring(rateUpArtifact[j].indexOf(":")+1);
        System.out.println("Error, Name does not exist, Check information get rate up arifact method");
        return "Name does not exist";
    }
    
    public int findImageLocation(String imageName, String fileName)
    {
        String[] imageNameList = getFiveStarList(fileName); //get names of images without .JPG
        for(int j = 0; j < imageNameList.length; j++) 
            if (imageNameList[j].equals(imageName)) 
                return j;
        return 0; //for missing return- called if no such name 
    }
                  
    public List<String> getImagesList(String fileName, boolean notBg, int start, int end)
    {
        int pos = 0;
        File folder = new File(fileName);
        String[] fileNameList = folder.list();
        String[] newFileNameList = new String[end-start];
        for (int j = start; j < end; j++) {
            newFileNameList[pos] = fileName + "/" + fileNameList[j];
            pos++;
        }
        List<String> imageList = new ArrayList(Arrays.asList(newFileNameList));
        if (notBg == true) imageList.add("emptyImages"); //empty image will be removed at first call
        return imageList;
    }
    
    public List<String> getBackgroundAnimation()
    {
        File folder = new File("Background");
        return getImagesList("Background", false, 0, folder.list().length);
    }
    
    public List<String> getMoonlightThreeStarAnimation()
    {
        File folder = new File("ML3Ani");
        return getImagesList("ML3Ani", true, 0, folder.list().length);
    }
    
    public List<String> getMoonlightFourStarAnimation()
    {
        File folder1 = new File("ML4Ani");
        File folder2 = new File("ML3Ani");
        List<String> ml4Animation = getImagesList("ML4Ani", false, 0, folder1.list().length);
        List<String> ml3Animation = getImagesList("ML3Ani", true, 31, folder2.list().length);
        ml4Animation.addAll(ml3Animation);
        return ml4Animation;
    }
    
    public List<String> getMoonlightFiveStarAnimation() 
    {
        File folder1 = new File("ML5Ani");
        File folder2 = new File("ML3Ani");
        List<String> ml4Animation = getImagesList("ML4Ani", false, 0, 27);
        List<String> ml5Animation = getImagesList("ML5Ani", false, 0, folder1.list().length);
        List<String> ml3Animation = getImagesList("ML3Ani", true, 31,  folder2.list().length);
        ml4Animation.addAll(ml5Animation);
        ml4Animation.addAll(ml3Animation);
        return ml4Animation;
    }
    
    public List<String> getCovenantFiveStarAnimation()
    {
        File folder1 = new File("COV5StarAni");
        List<String> art4Animation = getImagesList("ART4Ani", false, 0, 22);
        List<String> cov5Animation = getImagesList("COV5StarAni", false, 0, folder1.list().length);
        List<String> art4Animation2 = getImagesList("ART4Ani", true, 42, 50);
        art4Animation.addAll(cov5Animation);
        art4Animation.addAll(art4Animation2);
        return art4Animation;
    }
    
    public List<String> getCovenantFourStarAnimation()
    {
        File folder1 = new File("COV4StarAni");
        List<String> art4Animation = getImagesList("ART4Ani", false, 0, 22);
        List<String> cov4Animation = getImagesList("COV4StarAni", false, 0, folder1.list().length);
        List<String> art4Animation2 = getImagesList("ART4Ani", true, 42, 50);
        art4Animation.addAll(cov4Animation);
        art4Animation.addAll(art4Animation2);
        return art4Animation;
    }
    
    public List<String> getCovenantThreeStarAnimation()
    {
        File folder1 = new File("ML3Ani");
        File folder2 = new File("ART4Ani");
        List<String> ml3Animation = getImagesList("ML3Ani", false, 0, 31);
        List<String> art4Animation = getImagesList("ART4Ani", true, 42, 50);
        ml3Animation.addAll(art4Animation);
        return ml3Animation;
    }
    
    public List<String> getCovFiveStar(String type) {
        File folder = new File("COV5Star");
        if (type.equals("all")) return getImagesList("COV5Star", false, 0, folder.list().length);
        else {
            List<String> temp = getImagesList("COV5Star", false, 0, folder.list().length);
            for (int j = 0; j < temp.size(); j++) {
                for (int k = 0; k < currentLimitedHero.length; k++) {
                    if (temp.get(j).equals("COV5Star/" + currentLimitedHero[k] + ".JPG")) {
                        temp.remove(j);
                        j--;
                    }
                }
            }
            return temp;
        }
    }
    
    public List<String> getCovFourStar() {
       File folder = new File("COV4Star");
       return getImagesList("COV4Star", false, 0, folder.list().length);
    }
    
    public List<String> getCovThreeStar() {
        File folder = new File("COV3Star");
        return getImagesList("COV3Star", false, 0, folder.list().length);
    }
    
    public List<String> getMoonFiveStar() {
        File folder = new File("ML5Star");
        return getImagesList("ML5Star", false, 0, folder.list().length);
    }
    
    public List<String> getMoonFourStar() {
        File folder = new File("ML4Star");
        return getImagesList("ML4Star", false, 0, folder.list().length);
    }
    
    public List<String> getMoonThreeStar() {
        File folder = new File("ML3Star");
        return getImagesList("ML3Star", false, 0, folder.list().length);
    }

    public List<String> getArtFiveStar(String type) {
       File folder = new File("ART5Star");
       if (type.equals("all")) return getImagesList("ART5Star", false, 0, folder.list().length);
       else {
            List<String> temp = getImagesList("ART5Star", false, 0, folder.list().length);
            for (int j = 0; j < temp.size(); j++) {
                for (int k = 0; k < currentLimitedHero.length; k++) {
                    if (temp.get(j).equals("ART5Star/" + getRateUpArtifact(currentLimitedHero[k]) + ".JPG")) {
                        temp.remove(j);
                        j--;
                    }
                }
            }
            return temp;
        }
    }
    
    public List<String> getArtFourStar() {
        File folder = new File("ART4Star");
        return getImagesList("ART4Star", false, 0, folder.list().length);
    }

    public List<String> getArtThreeStar() {
        File folder = new File("ART3Star");
        return getImagesList("ART3Star", false, 0, folder.list().length);
    }
     
    public List<String> getArtifactFiveStarAnimation()
    {
        File folder1 = new File("ART5Ani");
        File folder2 = new File("ART4Ani");
        List<String> art4Animation = getImagesList("ART4Ani", false, 0, 21);
        List<String> art5Animation = getImagesList("ART5Ani", false, 0, folder1.list().length);
        List<String> art4Animation2 = getImagesList("ART4Ani", true, 42, folder2.list().length);
        art4Animation.addAll(art5Animation);
        art4Animation.addAll(art4Animation2);
        return art4Animation;
    }
     
    public List<String> getArtifactFourStarAnimation()
    {
       File folder = new File("ART4Ani");
       return getImagesList("ART4Ani", true, 0, folder.list().length);
    }

    public List<String> getArtifactThreeStarAnimation()
    {
        File folder = new File("ART3Ani");
        File folder2 = new File("ART4Ani");
        List<String> ml3Animation = getImagesList("ML3Ani", false, 0, 18);
        List<String> art3Animation = getImagesList("ART3Ani", false, 0, folder.list().length);
        List<String> art4Animation = getImagesList("ART4Ani", true, 42, folder2.list().length);
        ml3Animation.addAll(art3Animation);
        ml3Animation.addAll(art4Animation);
        return ml3Animation;
    }
}