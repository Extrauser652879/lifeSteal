package me.sirhenry.lifesteal;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.Timer;
import java.util.TimerTask;

public class Clock {

    long totalMillisecondsLeft = 0;
    int daysLeft;
    int hoursLeft;
    int minutesLeft;
    int secondsLeft;
    int millisecondsLeft;

    Timer timer = new Timer();
    Runnable onEnd;


    TimerTask countDown = new TimerTask() {
        @Override
        public void run() {
            if (totalMillisecondsLeft > 0) {
                totalMillisecondsLeft--;
            } else {
                Bukkit.getScheduler().runTaskLater(plugin, onEnd, 1);
                cancel();
            }
        }
    };

    LifeSteal plugin;
    public Clock(int days, int hours, int minutes, int seconds, int milliseconds, Runnable onEnd, LifeSteal plugin) {

        this.daysLeft = days;
        this.hoursLeft = hours;
        this.minutesLeft = minutes;
        this.secondsLeft = seconds;
        this.millisecondsLeft = milliseconds;
        this.onEnd = onEnd;
        this.plugin = plugin;

        totalMillisecondsLeft = totalMillisecondsLeft + days * 86400000L;
        totalMillisecondsLeft = totalMillisecondsLeft + hours * 3600000L;
        totalMillisecondsLeft = totalMillisecondsLeft + minutes * 60000L;
        totalMillisecondsLeft = totalMillisecondsLeft + seconds * 1000L;
        totalMillisecondsLeft = totalMillisecondsLeft + milliseconds;

        timer.schedule(countDown, 1, 1);

    }

    private void calculate() {

        long remainder = totalMillisecondsLeft;

        daysLeft = (int) Math.floor(remainder / 86400000F);
        remainder -= daysLeft * 86400000L;
        hoursLeft = (int) Math.floor(remainder / 3600000F);
        remainder -= hoursLeft * 3600000L;
        minutesLeft = (int) Math.floor(remainder / 60000F);
        remainder -= minutesLeft * 60000F;
        secondsLeft = (int) Math.floor(remainder / 1000F);
        remainder -= secondsLeft * 1000F;
        millisecondsLeft = (int) remainder;

    }

    public int getDaysLeft() {
        calculate();
        return daysLeft;
    }
    public int getHoursLeft() {
        calculate();
        return hoursLeft;
    }
    public int getMinutesLeft() {
        calculate();
        return minutesLeft;
    }
    public int getSecondsLeft() {
        calculate();
        return secondsLeft;
    }
    public int getMillisecondsLeft() {
        calculate();
        return millisecondsLeft;
    }

}
