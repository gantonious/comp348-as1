package part1;

/**
 * Created by George on 2017-12-25.
 */
public class WebLogEntry {
    private String remoteHostIpAddress;
    private int bytesTransmitted;

    public WebLogEntry(String remoteHostIpAddress, int bytesTransmitted) {
        this.remoteHostIpAddress = remoteHostIpAddress;
        this.bytesTransmitted = bytesTransmitted;
    }

    public String getRemoteHostIpAddress() {
        return remoteHostIpAddress;
    }

    public void setRemoteHostIpAddress(String remoteHostIpAddress) {
        this.remoteHostIpAddress = remoteHostIpAddress;
    }

    public int getBytesTransmitted() {
        return bytesTransmitted;
    }

    public void setBytesTransmitted(int bytesTransmitted) {
        this.bytesTransmitted = bytesTransmitted;
    }
}
