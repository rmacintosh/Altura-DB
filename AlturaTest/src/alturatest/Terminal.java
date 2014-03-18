/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alturatest;

/**
 *
 * @author Macintosh
 */
public class Terminal {
    
    // Public member vars
    public static final int maxFails          = 10;
    public static final int CONDITION_SCRAP   = 3;
    public static final int CONDITION_GOOD    = 2;
    public static final int CONDITION_BAD     = 1;
    public static final int CONDITION_UNKNOWN = 1;
    
    // Private member vars
    private static final int row               = 10;
    private static final int col               = 2;
    private int              condition;
    private int              numFails;
    private long             serialNum;
    private long             pimsNum;
    private String           location;
    private String[][]       pastRepairs;
        
    // Public Methods
    public Terminal() {
        this(431901001948L, 650046825571L, "Altura Production", 0, CONDITION_GOOD);
    }
    
    public Terminal(long serialNum) {
        this(serialNum, 650046825571L, "Altura Production", 0, CONDITION_GOOD);
    }
    
    public Terminal(long serialNum, long pimsNum) {
        this(serialNum, pimsNum, "Altura Production", 0, CONDITION_GOOD);
    }
    
    public Terminal(long serialNum, long pimsNum, String location, int numFails, int condition) {
        this.serialNum = serialNum;
        this.pimsNum   = pimsNum;
        this.location  = location;
        this.numFails  = numFails;
        this.condition = condition;
        this.pastRepairs = new String[row][col];
    }
    
    public String getTerminalCondition() {
        switch(condition) {
            case CONDITION_BAD:
                return "Bad";
            case CONDITION_SCRAP:
                return "Scrapped";
            case CONDITION_GOOD:
                return "Good";
            default:
                return "Unknown";
        }
    }
    
    public boolean setTerminalBad(String fault) {
        if(condition == CONDITION_SCRAP)
            return false;
        condition = CONDITION_BAD;
        pastRepairs[numFails][0] = fault;
        numFails++;
        if(numFails > 10) {
            setTerminalScrapped("Scrapped due to too many failures.");
            return false;
        }
        return true;
    }
    
    public boolean setTerminalScrapped(String reason) {
        if((condition == CONDITION_GOOD) || (condition == CONDITION_UNKNOWN)) {
            setTerminalBad(reason);
            condition = CONDITION_SCRAP;
            numFails = -1;
            pastRepairs[numFails][1] = "Scrapped.";
            return true;
        }
        else if(condition == CONDITION_BAD) {
            condition = CONDITION_SCRAP;
            numFails = -1;
            pastRepairs[numFails][1] = reason;
            return true;
        }
        else
            return false;
    }
    
    public void shipTerminal(String destination) {
        transferTerminal("In Transit: " + destination);
    }
    
    public boolean recieveShipment() {
        if(!location.contains("In Transit: "))
            return false;
        transferTerminal(location.replaceAll("In Transit: ", ""));
        return true;
    }
    
    public boolean repairTerminal(String repairDesc) {
        if((numFails <= 10) && (condition == CONDITION_BAD)) {
            setTerminalGood();
            pastRepairs[numFails][1] = repairDesc;
            transferTerminal("Altura Production");
            return true;
        }
        else
            return false;
    }
    
    public int getNumFails() {
        return numFails;
    }
    
    public long getSerialNum() {
        return serialNum;
    }
    
    public long getPimsNum() {
        return pimsNum;
    }
    
    public String getLocation() {
        return location;
    }
    
    // Private Methods
    private void transferTerminal(String newLocation) {
        location = newLocation;
    }
    
    private void setTerminalGood() {
        condition = CONDITION_GOOD;
    }
    
}
