package de.hbrs.aspgen.api.diff;

import java.util.LinkedList;
import java.util.List;

public class AnnotationData {
    private String id;
    private final List<String> modified = new LinkedList<>();
    private final List<String> deleted = new LinkedList<>();
    private final List<String> excluded = new LinkedList<>();

    public AnnotationData(final String id) {
        this.id = id;
    }

    public void addModified(final String modified) {
        this.modified.add(modified);
    }

    public void addDeleted(final String deleted) {
        this.deleted.add(deleted);
    }

    public void addExcluded(final String excluded) {
        this.excluded.add(excluded);
    }

    public List<String> getDeleted() {
        return deleted;
    }

    public List<String> getModified() {
        return modified;
    }

    public String getId() {
        return id;
    }

    public List<String> getExcluded() {
        return excluded;
    }

    public void updateId(final String id) {
        this.id = id;
    }
}
