package lobbi44.ctf.util;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.function.Consumer;

/**
 * Created by HT on 04.05.2016.
 */
public class Countdown implements Runnable {

    private Plugin plugin;
    private int taskId = -1;
    private Consumer<Integer> task;
    /**
     * After how many ticks after run has been called the timer will start executing
     */
    private long after;
    /**
     * How many ticks delay there are between each execution of the timer
     */
    private long delay;
    /**
     * How many times this timer is executed
     */
    private int repeat;
    /**
     * How many times this timer already has been executed
     */
    private int timesExecuted;

    private Countdown(Plugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Creates a timer object for the specified plugin
     *
     * @param plugin The plugin the spawned timer thread belongs to
     * @return The new timer object
     */
    public static Countdown forPlugin(Plugin plugin) {
        return new Countdown(plugin);
    }

    public Countdown after(long ticks) {
        this.after = ticks;
        return this;
    }

    public Countdown delayBetween(long ticks) {
        this.delay = ticks;
        return this;
    }

    public Countdown repeat(int times) {
        this.repeat = times;
        return this;
    }

    /**
     * This method starts the timer in a new asynchronous thread
     *
     * @param toExecute The method to be executed, takes the times the timer already
     *                  has called this method as a parameter
     * @return The BukkitTask created by the bukkit scheduler
     */
    public BukkitTask run(Consumer<Integer> toExecute) {
        checkState();
        this.task = toExecute;
        final BukkitTask t = Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, this, after, delay);
        this.taskId = t.getTaskId();
        return t;
    }

    private void cancel() {
        Bukkit.getScheduler().cancelTask(this.taskId);
    }

    private void checkState() {
        if (taskId != -1) {
            throw new IllegalStateException("Already scheduled as " + taskId);
        }
    }

    @Override
    public void run() {
        if (timesExecuted >= repeat)
            this.cancel();
        else
            task.accept(timesExecuted++);
    }
}
