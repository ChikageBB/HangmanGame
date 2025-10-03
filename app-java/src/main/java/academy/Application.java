package academy;

import static java.util.Objects.nonNull;

import academy.config.AppConfig;
import academy.service.GameSession;
import academy.model.HangmanGame;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.function.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(name = "Application Example", version = "Example 1.0", mixinStandardHelpOptions = true)
public class Application implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);
    private static final ObjectReader YAML_READER =
            new ObjectMapper(new YAMLFactory()).findAndRegisterModules().reader();
  //  private static final Predicate<String[]> IS_TESTING_MODE = words -> nonNull(words) && words.length == 2;
    private static final Predicate<AppConfig> IS_TESTING_MODE =
      cfg -> cfg.words() != null
                    && cfg.words().size() == 1
                    && cfg.words().get(0).elements().size() == 2;

    @Option(
            names = {"-s", "--font-size"},
            description = "Font size")
    int fontSize;

    @Parameters(paramLabel = "<word>", description = "Words pair for testing mode")
    private String[] words;

    @Option(
            names = {"-c", "--config"},
            description = "Path to YAML config file")
    private File configPath;

    public static void main(String[] args) {
        int exitCode = new CommandLine(new Application()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public void run() {
        AppConfig config = loadConfig();
        LOGGER.atInfo().addKeyValue("config", config).log("Config content");

//        if (IS_TESTING_MODE.test(config)) {
//                LOGGER.atInfo().log("Non-interactive testing mode enabled");
//
//
//                var word = config.words().get(0).elements().get(0).word();
//                var userInput = config.words().get(0).elements().get(1).word();
//
//                HangmanGame game = new HangmanGame(config);
//                GameSession session = new GameSession(game);
//                String res = session.playTest(word, userInput);
//
//                System.out.println(res);
//
//        } else {
//            LOGGER.atInfo().log("Interactive mode enabled");
//
//            HangmanGame game = new HangmanGame(config);
//            GameSession session = new GameSession(game);
//            session.startInteractive();
//
//        }

            GameSession gameSession = new GameSession();
            if (IS_TESTING_MODE.test(config)) {
                LOGGER.atInfo().log("Non-interactive testing mode enabled");
                gameSession.startNonInteractive(config);
            } else {
                LOGGER.atInfo().log("Interactive testing mode enabled");
                gameSession.startInteractive(config);
            }

    }

    private AppConfig loadConfig() {
        // fill with cli options
        if (words != null && words.length == 2) {
            // 🔹 Тестовый режим: два слова из CLI
            return new AppConfig(
                fontSize > 0 ? fontSize : 12,
                java.util.List.of(
                    new AppConfig.Category(
                        "test",
                        java.util.List.of(
                            new AppConfig.Word(words[0], ""), // загаданное слово
                            new AppConfig.Word(words[1], "")  // ввод пользователя
                        )
                    )
                )
            );
        }
        // use config file if provided
        try {
            return YAML_READER.readValue(configPath, AppConfig.class);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
