package kr.planetearth.planetdiscordcommands;

import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class RegisterCommands extends ListenerAdapter {

    public List<CommandData> getCommands() {
        List<CommandData> commandData = new ArrayList<>();
        OptionData optionTown = new OptionData(OptionType.STRING, "name", "마을 이름을 입력해주세요!", true );
        commandData.add(Commands.slash("town", "마을 정보를 봅니다.").addOptions(optionTown));
        OptionData optionResident = new OptionData(OptionType.STRING, "name", "플레이어 이름을 입력해주세요!", true );
        commandData.add(Commands.slash("resident", "플레이어 정보를 봅니다!").addOptions(optionResident));
        OptionData optionNation = new OptionData(OptionType.STRING, "name", "국가 이름을 입력해주세요!", true );
        commandData.add(Commands.slash("nation", "국가 정보를 봅니다!").addOptions(optionNation));
        OptionData optionOnline = new OptionData(OptionType.STRING, "name", "국가 이름을 입력해주세요!", true );
        commandData.add(Commands.slash("online", "국가 접속자수를 봅니다!").addOptions(optionOnline));
        return commandData;
    }
    @Override
    public void onGuildReady(@NotNull GuildReadyEvent event) {
        event.getGuild().updateCommands().addCommands(getCommands()).queue();
    }

    @Override
    public void onGuildJoin(GuildJoinEvent event) {
        event.getGuild().updateCommands().addCommands(getCommands()).queue();
    }

}
