package tk.clawhub.lmdb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * <Description>Lmdb app <br>
 *
 * @author LiZhiming<br>
 * @version 1.0<br>
 * @taskId <br>
 * @date 2018 -07-20 <br>
 */
@SpringBootApplication
public class LmdbApp {

    /**
     * Description: Main <br>
     *
     * @param args args
     * @author LiZhiming <br>
     * @taskId <br>
     */
    public static void main(String[] args) {
        ConfigurableApplicationContext confApp = null;
        try {
            confApp = SpringApplication.run(LmdbApp.class, args);
        } finally {
            close(confApp);
        }
    }

    /**
     * Description: Close <br>
     *
     * @param confApp conf app
     * @author LiZhiming <br>
     * @taskId <br>
     */
    private static void close(ConfigurableApplicationContext confApp) {

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (confApp != null) {
                confApp.close();
            }
        }));
    }
}