package com.atlassian.clover.util;


/**
 * code to detect what version of Java we are running on
 * based on the JavaEnvUtils from Ant
 */
public class JavaEnvUtils {
    /** Version of currently running VM. */
    private static String javaVersion;

    public static final String JAVA_1_0 = "1.0";
    public static final String JAVA_1_1 = "1.1";
    public static final String JAVA_1_2 = "1.2";
    public static final String JAVA_1_3 = "1.3";
    public static final String JAVA_1_4 = "1.4";
    public static final String JAVA_5 = "5";
    public static final String JAVA_6 = "6";
    public static final String JAVA_7 = "7";
    public static final String JAVA_8 = "8";

    static {
        try {
            javaVersion = JAVA_1_0;
            Class.forName("java.lang.Void");
            javaVersion = JAVA_1_1;
            Class.forName("java.lang.ThreadLocal");
            javaVersion = JAVA_1_2;
            Class.forName("java.lang.StrictMath");
            javaVersion = JAVA_1_3;
            Class.forName("java.lang.CharSequence");
            javaVersion = JAVA_1_4;
            Class.forName("java.lang.Readable");
            javaVersion = JAVA_5;
            Class.forName("java.util.concurrent.ConcurrentSkipListMap");
            javaVersion = JAVA_6;
            Class.forName("java.nio.file.FileSystem");
            javaVersion = JAVA_7;
            Class.forName("java.lang.reflect.Executable");
            javaVersion = JAVA_8;
        } catch (Throwable t) {
            // ignore
        }
    }

    public static String getJavaVersion() {
        return javaVersion;
    }
}
