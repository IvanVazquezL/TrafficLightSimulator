public class Road {
    private boolean status;
    private int seconds;
    private String name;
    private int interval;



    Road(String name, boolean status, int seconds, int interval) {
        this.name = name;
        this.status = status;
        this.seconds = seconds;
        this.interval = interval;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        if (seconds == 0) {
            setStatus(!status);
            this.seconds = interval;
        } else {
            this.seconds = seconds;
        }
    }

    @Override
    public String toString() {
        String stringStatus = status ? "open" : "closed";
        return String.format(
    """
        %s will be %s for %ds.
            """,
        name, stringStatus, seconds);
    }
}
