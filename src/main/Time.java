package main;

public abstract class Time
{
    private static long lastFrameTime;
    private static double deltaTime;

    public final static double nanoToSeconds = 1_000_000_000.0;

    public static double deltaTime()
    {
        // Time since last frame
        return deltaTime;
    }

    public static void update(Long now)
    {
        deltaTime = (now - lastFrameTime) / nanoToSeconds;
        lastFrameTime = now;
    }

    public static void initialize()
    {
        lastFrameTime = System.nanoTime();
    }
}
