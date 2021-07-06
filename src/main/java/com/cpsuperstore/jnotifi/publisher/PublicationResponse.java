package com.cpsuperstore.jnotifi.publisher;

public class PublicationResponse {
    private final int success;
    private final int failed;
    private final int total;

    public PublicationResponse(int success, int failed, int total) {
        this.success = success;
        this.failed = failed;
        this.total = total;
    }

    public int getSuccess() {
        return success;
    }

    public int getFailed() {
        return failed;
    }

    public int getTotal() {
        return total;
    }

    public float getSuccessPercent() {
        if(total == 0){
            return 0;
        }
        return (float) success / total * 100;
    }

    public float getFailedPercent() {
        if(total == 0){
            return 0;
        }
        return (float) failed / total * 100;
    }

    @Override
    public String toString() {
        return "PublicationResponse{" +
                "success=" + success +
                ", failed=" + failed +
                ", total=" + total +
                '}';
    }
}
