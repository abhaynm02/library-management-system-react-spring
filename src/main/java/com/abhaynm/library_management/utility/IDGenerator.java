package com.abhaynm.library_management.utility;

import java.util.UUID;

public class IDGenerator {
    public static String generateUniqueId() {

        return UUID.randomUUID().toString().replace("-", "").substring(0, 8).toUpperCase();
    }
}
