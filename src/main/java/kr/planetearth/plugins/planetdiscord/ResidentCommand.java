package kr.planetearth.plugins.planetdiscord.planetdiscord;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.TownyEconomyHandler;
import com.palmergames.bukkit.towny.object.Resident;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import java.awt.*;

public class ResidentCommand extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

        if (!event.getName().equals("resident")) {
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

        Resident r = TownyAPI.getInstance().getResident(event.getInteraction().getOption("name").getAsString());

        if (r == null) {
            EmbedBuilder embFailRes = new EmbedBuilder();
            embFailRes.setDescription("존재하지 않는 플레이어입니다!");
            embFailRes.setColor(Color.RED);
            event.replyEmbeds(embFailRes.build()).queue();
            return;
        }

        EmbedBuilder emb = new EmbedBuilder();
        emb.setTitle(r.getName());
        emb.setDescription("**상태:** " + (r.isOnline() ? "온라인" : "오프라인"));
        emb.setThumbnail("https://mc-heads.net/avatar/" + r.getName() + "/600.png");
        emb.setColor(Color.GREEN);
        emb.addField("최초 접속일:", "<t:" + r.getRegistered()/1000 + ":f>", true);
        emb.addField("최근 접속일:", "<t:" + r.getLastOnline()/1000 + ":f>", true);
        if (r.hasTown()) {
            emb.addField("마을:" , r.getTownOrNull().getName(), true);
        }
        if (r.hasNation()) {
            emb.addField("국가: " , r.getNationOrNull().getName(), true);
        }
        if (TownyEconomyHandler.isActive()) {
            emb.addField("돈:", String.valueOf(Math.round(r.getAccount().getHoldingBalance()*10)/10.0), true);
        }

        event.replyEmbeds(emb.build()).queue();

    }
}
