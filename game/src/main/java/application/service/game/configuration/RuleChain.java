package application.service.game.configuration;

import application.service.game.rules.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class RuleChain {
    @Bean
    public List<Rule> produceRuleChain() {
        return List.of(
                new CheckTurnRule(),
                new SowStonesRule(),
                new CaptureStonesRule(),
                new NextTurnRule(),
                new ScoreMoreThanHalfRule(),
                new AllPitsAreEmptyRule(),
                new FindWinnerRule());
    }
}
