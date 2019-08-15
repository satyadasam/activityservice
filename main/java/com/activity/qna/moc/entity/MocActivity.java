package com.  .activity.qna.moc.entity;

public class MocActivity {

	private int activityId;
	private int providerId;
	private int accreditorId;
	private double points;
	private int parsActivityId;
	private int evalRequired;
	
	public int getActivityId() {
		return activityId;
	}
	public void setActivityId(int activityId) {
		this.activityId = activityId;
	}
	public int getProviderId() {
		return providerId;
	}
	public void setProviderId(int providerId) {
		this.providerId = providerId;
	}
	public int getAccreditorId() {
		return accreditorId;
	}
	public void setAccreditorId(int accreditorId) {
		this.accreditorId = accreditorId;
	}
	public double getPoints() {
		return points;
	}
	public void setPoints(double points) {
		this.points = points;
	}
	public int getParsActivityId() {
		return parsActivityId;
	}
	public void setParsActivityId(int parsActivityId) {
		this.parsActivityId = parsActivityId;
	}
	public int getEvalRequired() {
		return evalRequired;
	}
	public void setEvalRequired(int evalRequired) {
		this.evalRequired = evalRequired;
	}
	@Override
	public String toString() {
		return "MocActivity [activityId=" + activityId + ", providerId="
				+ providerId + ", accreditorId=" + accreditorId + ", points="
				+ points + ", parsActivityId=" + parsActivityId
				+ ", evalRequired=" + evalRequired + "]";
	}
	
	
}
