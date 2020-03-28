package ws.restful.model;


public class ErrorResp {
    private String message;
    
    public ErrorResp() {}
    
    public ErrorResp(String message) {
        this.message = message;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }
    
}
