package kr.planetearth.planetdiscordcommands;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.TownyEconomyHandler;
import com.palmergames.bukkit.towny.object.Town;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import java.awt.*;

public class TownCommand extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

        if (!event.getName().equals("town")) {
            return;
        }

        if (!event.getChannelId().equals(Main.instance.getConfig().getString("command-channel-id"))) {
            EmbedBuilder embFailRes = new EmbedBuilder();
            embFailRes.setDescription("명령어 채널에서만 사용할 수 있습니다!");
            embFailRes.setColor(Color.RED);
            event.replyEmbeds(embFailRes.build()).queue();
            return;
        }

        if (!event.getMember().getRoles().stream().anyMatch(role -> role.getId().equals(Main.instance.getConfig().getString("premium-role-id")))) {
            EmbedBuilder embFailRes = new EmbedBuilder();
            embFailRes.setDescription("후원자만 사용할 수 있습니다!");
            embFailRes.setColor(Color.RED);
            event.replyEmbeds(embFailRes.build()).queue();
            return;
        }

        Town t = TownyAPI.getInstance().getTown(event.getInteraction().getOption("name").getAsString());

        if (t == null) {

            EmbedBuilder embFailTown = new EmbedBuilder();
            embFailTown.setDescription("존재하지 않는 마을입니다!");
            embFailTown.setColor(Color.RED);
            event.replyEmbeds(embFailTown.build()).queue();
            return;
        }

        EmbedBuilder emb = new EmbedBuilder();
        emb.setColor(Color.GREEN);
        emb.setTitle(t.getName());
        emb.setDescription("**공지:** " + t.getBoard());
        emb.addField("시장:", t.getMayor().getName(), true);
        if (t.hasNation()) {
            emb.addField("국가:", t.getNationOrNull().getName(), true);
        }
        if (TownyEconomyHandler.isActive()) {
            emb.addField("금고:", String.valueOf(Math.round(t.getAccount().getHoldingBalance()*10)/10.0), true);
        }
        emb.addField("PVP:", t.isPVP() ? "켜짐" : "꺼짐", true);
        emb.addField("불 번짐:", t.isFire() ? "켜짐" : "꺼짐", true);
        emb.addField("폭발:", t.isExplosion() ? "켜짐" : "꺼짐", true);
        emb.addField("주민 수:" , String.valueOf(t.getNumResidents()), true);

        event.replyEmbeds(emb.build()).queue();

    }
}
