package kr.planetearth.plugins.planetearthdiscod.planetdiscord;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.TownyEconomyHandler;
import com.palmergames.bukkit.towny.object.Nation;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import java.awt.*;

public class NationCommand extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!event.getName().equals("nation")) {
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
        emb.setDescription("**공지:** " + n.getBoard());
        emb.addField("국가장:", n.getKing().getName(), true);
        emb.addField("수도:", n.getCapital().getName(), true);
        emb.addField("마을 수:", String.valueOf(n.getNumTowns()), true);
        if (TownyEconomyHandler.isActive()) {
            emb.addField("국고:", String.valueOf(Math.round(n.getAccount().getHoldingBalance()*10)/10.0), true);
        }
        emb.addField("주민 수:" , String.valueOf(n.getNumResidents()), true);
        event.replyEmbeds(emb.build()).queue();
    }
}