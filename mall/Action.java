package beans;

public class Action {
	private boolean ActionType;
	private String page;
	private boolean isRedirect;
	
	public boolean isActionType() {
		return this.ActionType;
	}
	public void setActionType(boolean actionType) {
		this.ActionType = actionType;
	}
	public String getPage() {
		return this.page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public void setRedirect(boolean isRedirect) {
		this.isRedirect = isRedirect;
	}
	public boolean isRedirect() {
			return isRedirect;
		}
		
	}

