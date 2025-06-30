package kr.planetearth.plugins.planetearthdiscod.planetdiscord;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.object.Nation;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.entity.Player;

import java.awt.Color;
import java.util.List;

public class OnlineCommand extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!event.getName().equals("online")) {
            return;
        }

        if (!event.getChannelId().equals(PlanetDiscord.instance.getConfig().getString("command-channel-id"))) {
            EmbedBuilder embFailRes = new EmbedBuilder();
            embFailRes.setDescription("명령어 채널에서만 사용할 수 있습니다!");
            embFailRes.setColor(Color.RED);
            event.replyEmbeds(embFailRes.build()).queue();
            return;
        }

        if (!event.getMember().getRoles().stream().anyMatch(role -> role.getId().equals(PlanetDiscord.instance.getConfig().getString("premium-role-id")))) {
            EmbedBuilder embFailRes = new EmbedBuilder();
            embFailRes.setDescription("후원자만 사용할 수 있습니다!");
            embFailRes.setColor(Color.RED);
            event.replyEmbeds(embFailRes.build()).queue();
            return;
        }

        Nation n = TownyAPI.getInstance().getNation(event.getInteraction().getOption("name").getAsString());
        List<Player> onlineplayers = TownyAPI.getInstance().getOnlinePlayersInNation(n);
        List<Player> onlineallyplayers = TownyAPI.getInstance().getOnlinePlayersAlliance(n);
        if (n == null) {
            EmbedBuilder embFailNat = new EmbedBuilder();
            embFailNat.setDescription("존재하지 않는 국가입니다!");
            embFailNat.setColor(Color.RED);
            event.replyEmbeds(embFailNat.build()).queue();
            return;
        }

        EmbedBuilder emb = new EmbedBuilder();
        emb.setColor(Color.GREEN);
        emb.setTitle(n.getName());
        emb.setDescription("**접속중인 국가원 수:** " + onlineplayers.size());
        emb.addField("접속중인 동맹원 수:", String.valueOf(onlineallyplayers.size()), true);
        event.replyEmbeds(emb.build()).queue();
    }
}