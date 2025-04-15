package tas;

public class Configs {

    public static int port = 6000;
    public static String ip = "192.168.137.31";
    public static int delay = 28;
    public static final String[] connectionScript = {
            "clickSeq A,W400",
            "configure mainLoopSleepTime " + Configs.delay,
            "configure buttonClickSleepTime " + Configs.delay,
            "click A",
            "click A"
    };

}
