package com.jonathanluco.doctorapp.exception;

/**
 * Exception levée lorsqu'un conflit de données est détecté (ex: doublon).
 */
public class DuplicateResourceException extends RuntimeException {

    private String resourceName;
    private String fieldName;
    private Object fieldValue;

    /**
     * Constructor with message.
     *
     * @param message the error message
     */
    public DuplicateResourceException(String message) {
        super(message);
    }

    /**
     * Constructor with resource details.
     *
     * @param resourceName the name of the resource
     * @param fieldName the field with duplicate
     * @param fieldValue the value of the field
     */
    public DuplicateResourceException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s avec %s '%s' existe déjà", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public String getResourceName() {
        return resourceName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Object getFieldValue() {
        return fieldValue;
    }
}
