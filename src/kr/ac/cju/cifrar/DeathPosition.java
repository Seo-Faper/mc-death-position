package kr.ac.cju.cifrar;

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
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.java.JavaPlugin;

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
        shootfireworks();
    }
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e){
       Player player = e.getEntity();
       int x = (int)player.getLastDeathLocation().getX();
       int  y = (int)player.getLastDeathLocation().getY();
       int  z = (int)player.getLastDeathLocation().getZ();
       player.sendMessage(ChatColor.GREEN+"당신의 사망 좌표 : "+ChatColor.BOLD+"( "+x+" , "+y+" , "+z+" )");
    }
    

    private void shootfireworks() {

        for (Player player : Bukkit.getOnlinePlayers()) {
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
            fireworkm.setPower(1);// 폭죽이 올라가는 높이 1~3
            firework.setFireworkMeta(fireworkm);

        }
    }


}
