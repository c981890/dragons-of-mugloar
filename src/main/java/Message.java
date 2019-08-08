public class Message {

    private String adId;
    private String message;
    private int reward;
    private int expiresIn;
    private String probability;

    public Message() {
    }

    public Message(String adId, String message, int reward, int expiresIn, String probability) {
        this.adId = adId;
        this.message = message;
        this.reward = reward;
        this.expiresIn = expiresIn;
        this.probability = probability;
    }

    public String getAdId() {
        return adId;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public String getProbability() {
        return probability;
    }

    public void setAdId(String adId) {
        this.adId = adId;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    public void setProbability(String probability) {
        this.probability = probability;
    }
}
