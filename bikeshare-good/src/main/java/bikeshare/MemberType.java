package bikeshare;

public enum MemberType {
    STUDENT(2),
    STAFF(3);

    private final int rate;

    MemberType(int rate) {
        this.rate = rate;
    }

    public int feeFor(int minutes) {
        return rate * minutes;
    }
}
