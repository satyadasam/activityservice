package com.  .activity.qna.model;


/**
 * This class defines the DataModel used to save the activity
 * info in activity result table.
 * The class defines the required parameters and 
 * their corrosponding Getters and Setters.
 * 
 * @author Vikas Gupta
 * @version 1.0
 */
public class DBActivityResult {

	/*
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * 
	 * data members
	 * 
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */
	
	private int qnaId = 0; 
	private long guid = 0;	
	private String profileComplete = "";
	private int sessionID = 0;
	private int professionId = 0;				
	private int occupationId = 0;
	private int degreeId = 0;
	private int specialtyId = 0;
	private int pass = 0;

	private String countryAbbrev = "";
    private String userAgent = "";

	/*
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * 
     * Public methods ( Getters and Setters )
     * 
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */
	
	public int getQnaId() {
		return qnaId;
	}
	public void setQnaId(int qnaId) {
		this.qnaId = qnaId;
	}
	public long getGuid() {
		return guid;
	}
	public void setGuid(long guid) {
		this.guid = guid;
	}
	public String getProfileComplete() {
		return profileComplete;
	}
	public void setProfileComplete(String profileComplete) {
		this.profileComplete = profileComplete;
	}
	public int getSessionID() {
		return sessionID;
	}
	public void setSessionID(int sessionID) {
		this.sessionID = sessionID;
	}
	public int getProfessionId() {
		return professionId;
	}
	public void setProfessionId(int professionId) {
		this.professionId = professionId;
	}
	public int getOccupationId() {
		return occupationId;
	}
	public void setOccupationId(int occupationId) {
		this.occupationId = occupationId;
	}
	public int getDegreeId() {
		return degreeId;
	}
	public void setDegreeId(int degreeId) {
		this.degreeId = degreeId;
	}
	public int getSpecialtyId() {
		return specialtyId;
	}
	public void setSpecialtyId(int specialtyId) {
		this.specialtyId = specialtyId;
	}
	public int getPass() {
		return pass;
	}
	public void setPass(int pass) {
		this.pass = pass;
	}
	public String getCountryAbbrev() {
		return countryAbbrev;
	}
	public void setCountryAbbrev(String countryAbbrev) {
		this.countryAbbrev = countryAbbrev;
	}

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String toString(){
		
		StringBuffer sb = new StringBuffer();
		sb.append( "----------------------------------------------------------");
		sb.append( "qnaId = " + qnaId );
		sb.append( "::");
		sb.append( "guid = " + guid );
		sb.append( "::");
		sb.append( "profileComplete = " + profileComplete );
		sb.append( "::");
		sb.append( "professionId = " + professionId );
		sb.append( "::");
		sb.append( "occupationId = " + occupationId );
		sb.append( "::");
		sb.append( "degreeId = " + degreeId );
		sb.append( "::");
		sb.append( "specialtyId = " + specialtyId );
		sb.append( "::");
		sb.append( "pass = " + pass );
		sb.append( "::");
		sb.append( "countryAbbrev = " + countryAbbrev );
        sb.append( "::");
        sb.append( "userAgent = " + userAgent );
		sb.append( "----------------------------------------------------------");
		
		
		return sb.toString();
		
	}
	
}
