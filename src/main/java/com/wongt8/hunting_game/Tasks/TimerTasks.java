package com.wongt8.hunting_game.Tasks;

import com.wongt8.hunting_game.Command.StartCommand;
import com.wongt8.hunting_game.CountPoint.CountPoint;
import com.wongt8.hunting_game.Hunting_Game;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.mrmicky.fastboard.FastBoard;

import java.util.Map;
import java.util.UUID;

public class TimerTasks extends BukkitRunnable {

	public static boolean RUN = false;
	private static int time = 0;

	private Hunting_Game main;

	public TimerTasks(Hunting_Game uhc) {
		this.main = uhc;
		time = 0;
	}

	@Override
	public void run() {
		this.updateBoards();
		
		if(RUN) {
			if(time%300 == 0){
				for(Map.Entry entry : StartCommand.killerTarget.entrySet()){
					Player pToSendMessage = Bukkit.getPlayer((UUID) entry.getKey());
					Player pToLocate = Bukkit.getPlayer((UUID) entry.getValue());
					if(pToLocate != null && pToLocate.getGameMode().equals(GameMode.SURVIVAL) && pToSendMessage != null){
						Location location = pToLocate.getLocation();
						pToSendMessage.sendMessage("§eCoordonate of your target : §c§lx §r§6> " + location.getBlockX() + "§f, §c§ly §r§6> " + location.getBlockY() + "§f, §c§lz §r§6> " + location.getBlockZ());
					}
				}
			}
			if(time == 10){ this.main.WORLD.setDifficulty(Difficulty.HARD); }
			time ++;

		}
		
	}
	
	public static void setRunning(boolean state) {
		RUN = state;
	}

	public static String formatTime(int secs, boolean printHour) {
		ChatColor color = RUN ? ChatColor.YELLOW : ChatColor.RED;
		String ret = "";
		if(!printHour) ret = String.format("%02d:%02d", secs / 60, secs % 60);
		else ret = String.format("%02d:%02d:%02d", secs / 3600, (secs % 3600) / 60, secs % 60);

		return color + ret.replace(":", ChatColor.RESET + ":" + color);
	}

	public static String formatLine(String key, Object value, ChatColor cVal) {
		ChatColor cKey = ChatColor.GOLD;
		ChatColor cRst = ChatColor.RESET;
		return cKey + key + cRst + " > " + cVal + value;
	}

	public static String formatLine(String key, Object value) {
		return formatLine(key, value, ChatColor.YELLOW);
	}

    private void updateBoards() {
        for(FastBoard board : this.main.boards) {
			board.updateLine(1, formatTime(time, true));
			if(CountPoint.getPtsOf(board.getPlayer().getUniqueId()) == 0){
				board.updateLine(3, formatLine("Rank", "§k???"));
				board.updateLine(4, formatLine("Your point","§k???"));
			}
			else{
				board.updateLine(3, formatLine("Rank", CountPoint.getClassementOf(board.getPlayer().getUniqueId())));
				board.updateLine(4, formatLine("Your point", CountPoint.getPtsOf(board.getPlayer().getUniqueId())));
			}
			board.updateLine(6, formatLine("Players", this.main.getAlivePlayer()));
			board.updateLine(8, formatLine("Kill", CountPoint.getNbKillOf(board.getPlayer().getUniqueId())));
        }
	}
}
 