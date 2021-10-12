package xyz.sunnytoday.common.task;


import xyz.sunnytoday.common.config.AppConfig;

public class ShortTermForecastTask extends TaskTimer{

    public ShortTermForecastTask(int interval) {
        super(interval);
    }

    @Override
    protected void start() {
        if(AppConfig.getForecastRepository().updateLastShortTermForecastVersion()) {
            System.out.println("ShortTermForecastTask.start");
            System.out.println(AppConfig.getForecastRepository().getLastShortTermForecastVersion());
           super.setInterval(3 * 60);
        }
    }
}
