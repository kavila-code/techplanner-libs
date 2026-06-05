package com.techplanner.securitylib.security;

import java.util.LinkedHashSet;
import java.util.Set;

public final class NativeSecurity {

    private static boolean nativeLoaded = false;

    static {
        try {
            System.loadLibrary("security_native");
            nativeLoaded = true;
        } catch (UnsatisfiedLinkError e) {
            nativeLoaded = false;
        }
    }

    private NativeSecurity() {
    }

    public static String[] normalizeRoles(String[] realmRoles, String[] resourceRoles) {
        if (nativeLoaded) {
            return normalizeRolesNative(realmRoles, resourceRoles);
        }

        // Java fallback: merge, trim, uppercase, prefix and deduplicate preserving
        // order
        Set<String> out = new LinkedHashSet<>();
        if (realmRoles != null) {
            for (String r : realmRoles) {
                if (r == null)
                    continue;
                String v = r.trim();
                if (v.isBlank())
                    continue;
                out.add("ROLE_" + v.toUpperCase());
            }
        }
        if (resourceRoles != null) {
            for (String r : resourceRoles) {
                if (r == null)
                    continue;
                String v = r.trim();
                if (v.isBlank())
                    continue;
                out.add("ROLE_" + v.toUpperCase());
            }
        }
        return out.toArray(new String[0]);
    }

    // JNI method: implement in native code
    private static native String[] normalizeRolesNative(String[] realmRoles, String[] resourceRoles);
}
