package tas;

public class Configs {

    public static int port = 6000;
    /* Common private IP-Address start. */
    public static String ip = "192.168.0.000";
    public static int delay = 28;
    public static final String[] connectionScript = {
            "clickSeq A,W400",
            "configure mainLoopSleepTime " + Configs.delay,
            "configure buttonClickSleepTime " + Configs.delay,
            "clickSeq A",
            "clickSeq A",
    };

    public static int invertLX = 1;
    public static int invertLY = -1;
    public static int invertRX = 1;
    public static int invertRY = 1;
    public static String path = "";
}
