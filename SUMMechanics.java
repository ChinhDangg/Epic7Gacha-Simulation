import java.util.List;
public class SUMMechanics
{
    protected Information forSummon = new Information();
    protected List<String> covenantFiveRateUp = forSummon.getCovFiveStar("all");
    protected List<String> artifactFiveRateUp = forSummon.getArtFiveStar("all");
    
    protected String[] limitedHero = forSummon.getLimitedHeroList();
    
    protected List<String> moonlightFive = forSummon.getMoonFiveStar();
    protected List<String> moonlightFour = forSummon.getMoonFourStar();
    protected List<String> moonlightThree = forSummon.getMoonThreeStar();
    protected List<String> covenantFive = forSummon.getCovFiveStar("no limited unit");
    protected List<String> covenantFour = forSummon.getCovFourStar();
    protected List<String> covenantThree = forSummon.getCovThreeStar();
    protected List<String> artifactFive = forSummon.getArtFiveStar("no limited art");
    protected List<String> artifactFour = forSummon.getArtFourStar();
    protected List<String> artifactThree = forSummon.getArtThreeStar();
     
    protected List<String> threeStarResult = forSummon.getMoonlightThreeStarAnimation();
    protected List<String> fourStarResult = forSummon.getMoonlightFourStarAnimation();     
    protected List<String> fiveStarResult = forSummon.getMoonlightFiveStarAnimation();
    protected List<String> covenantFiveResult = forSummon.getCovenantFiveStarAnimation();
    protected List<String> covenantFourResult = forSummon.getCovenantFourStarAnimation();     
    protected List<String> covenantThreeResult = forSummon.getCovenantThreeStarAnimation();
    protected List<String> artifactFiveResult = forSummon.getArtifactFiveStarAnimation();
    protected List<String> artifactFourResult = forSummon.getArtifactFourStarAnimation();
    protected List<String> artifactThreeResult = forSummon.getArtifactThreeStarAnimation();
    
    protected List<String> summon(List<String> typeResult, List<String> typeList) //normal summon mechanics
    {
        typeResult.remove(typeResult.size()-1);
        typeResult.add(typeList.get((int)(Math.random() * typeList.size())));
        return typeResult;
    }
}
