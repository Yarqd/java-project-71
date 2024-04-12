package hexlet.code;

public final class Diff {
    private final String key;
    private final Object oldValue;
    private final Object newValue;
    private final DiffType type;

    public Diff(String diffKey, Object diffOldValue, Object diffNewValue, DiffType diffType) {
        this.key = diffKey;
        this.oldValue = diffOldValue;
        this.newValue = diffNewValue;
        this.type = diffType;
    }


    /**
     * Returns the key associated with this diff.
     *
     * @return the key
     */
    public String getKey() {
        return key;
    }

    /**
     * Returns the old value associated with the key if any.
     *
     * @return the old value, or null if no old value is present
     */
    public Object getOldValue() {
        return oldValue;
    }

    /**
     * Returns the new value associated with the key.
     *
     * @return the new value, or null if the key was removed
     */
    public Object getNewValue() {
        return newValue;
    }

    /**
     * Returns the type of the diff.
     *
     * @return the type of the diff
     */
    public DiffType getType() {
        return type;
    }


    public enum DiffType {
        ADDED, REMOVED, UPDATED, UNCHANGED
    }
}
