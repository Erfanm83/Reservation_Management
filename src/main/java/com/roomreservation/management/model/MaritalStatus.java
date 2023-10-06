package com.roomreservation.management.model;

public enum MaritalStatus {
    SINGLE("Single"),
    MARRIED("Married"),
    DIVORCED("Divorced");

    private final String displayName;

    MaritalStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static MaritalStatus fromDisplayName(String displayName) {
        for (MaritalStatus status : values()) {
            if (status.getDisplayName().equalsIgnoreCase(displayName)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown Marital Status: " + displayName);
    }
}

