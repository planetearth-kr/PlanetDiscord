package kr.planetearth.planetdiscordcommands;

import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;

import javax.security.auth.login.LoginException;
import java.util.Objects;

public class BotMain {
      public ShardManager shardM;

    public BotMain() throws LoginException {
        String tkn = Main.instance.getConfig().getString("token");
        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(tkn);
        switch (Main.instance.getConfig().getString("bot-status")) {
            case "ONLINE":
              builder.setStatus(OnlineStatus.ONLINE);
              break;
            case "DO_NOT_DISTURB":
              builder.setStatus(OnlineStatus.DO_NOT_DISTURB);
              break;
            case "IDLE":
                builder.setStatus(OnlineStatus.IDLE);
                break;
            case "INVISIBLE":
                builder.setStatus(OnlineStatus.INVISIBLE);
                break;
        }
        if (Objects.requireNonNull(Main.instance.getConfig().getString("activity")).length() > 15) {
            builder.setActivity(Activity.playing("Huyu"));
        } else {
            builder.setActivity(Activity.playing(Main.instance.getConfig().getString("activity")));
        }
        shardM = builder.build();
        shardM.addEventListener(new RegisterCommands());
        shardM.addEventListener(new TownCommand());
        shardM.addEventListener(new ResidentCommand());
        shardM.addEventListener(new NationCommand());
        shardM.addEventListener(new OnlineCommand());
    }

    public ShardManager getShardM() {
        return shardM;
    }

    public static void main() {
        try {
            BotMain bot = new BotMain();
        } catch (LoginException e) {
            System.out.println("INVALID BOT TOKEN");
        }
    }
}
