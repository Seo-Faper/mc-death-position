package kr.ac.cju.cifrar;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.*;

import java.util.Random;
import java.util.logging.Level;

public class DeathPosition extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        //System.out.println("plugin Enable ");
        getLogger().log(Level.INFO,"ENABLED");
        getServer().getPluginManager().registerEvents(this,this);



    }

    @Override
    public void onDisable() {
        //System.out.println("plugin Disable");
        getLogger().log(Level.INFO,"DISABLED");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        e.setJoinMessage(ChatColor.GOLD+e.getPlayer().getName()+ChatColor.BOLD+"님이 서버에 입장 하셨습니다.");
        Player player = e.getPlayer();
        Firework firework = (Firework) player.getWorld().spawn(
                player.getLocation(), Firework.class);
        FireworkMeta fireworkm = firework.getFireworkMeta();

        FireworkEffect effect = FireworkEffect.builder().flicker(true)
                .withColor(Color.AQUA).withFade(Color.BLACK)
                .with(FireworkEffect.Type.BALL).trail(false).build();
        FireworkEffect effect2 = FireworkEffect.builder().flicker(false)
                .withColor(Color.RED).withFade(Color.LIME).with(FireworkEffect.Type.BURST)
                .trail(true).build();

        // flicker = 반짝이는여부, color = 폭죽색깔, fade = 2번째색깔, type = 모양, trail =

        // 잔상

        Random random = new Random();// 둘중하나 랜덤으로 쏨

        int rf = random.nextInt(2) + 1;

        if (rf == 1) {
            fireworkm.addEffect(effect);
        } else if (rf == 2) {
            fireworkm.addEffect(effect2);
        }
        fireworkm.setPower(rf);// 폭죽이 올라가는 높이 1~3
        firework.setFireworkMeta(fireworkm);



    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e){
        Player player = e.getPlayer();
        int x = (int) player.getLastDeathLocation().getX();
        int y = (int) player.getLastDeathLocation().getY();
        int z = (int) player.getLastDeathLocation().getZ();

        String message = ChatColor.GREEN + "당신의 사망 좌표 : " + ChatColor.BOLD + "( " + x + " , " + y + " , " + z + " ) " + ChatColor.DARK_PURPLE + "(" + player.getWorld().getName() + ")";
        player.spigot().sendMessage(TextComponent.fromLegacyText(message));

        ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
        Scoreboard scoreboard = scoreboardManager.getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("사망 좌표("+ChatColor.DARK_PURPLE + "(" + player.getWorld().getName() + ")", "criteria");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        Score scoreX = objective.getScore("X : ");
        scoreX.setScore(x);
        Score scoreY = objective.getScore("Y : ");
        scoreY.setScore(y);
        Score scoreZ = objective.getScore("Z : ");
        scoreX.setScore(z);
        player.setScoreboard(scoreboard);

    }
}
