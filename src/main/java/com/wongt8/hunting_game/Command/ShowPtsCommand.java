package com.wongt8.hunting_game.Command;

import com.wongt8.hunting_game.CountPoint.CountPoint;
import com.wongt8.hunting_game.Tasks.TimerTasks;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;

public class ShowPtsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if("pts".equalsIgnoreCase(command.getName())){
            return pts(sender,args);
        }
        return false;
    }

    public boolean pts(CommandSender sender, String[] args){
        if(args.length == 0){
            Collections.sort(CountPoint.pointOfEveryone);
            sender.sendMessage("------ §lRank §r------ ");

            int size_of_point = CountPoint.pointOfEveryone.size();
            int stable_point = 1;
            for(int rank = size_of_point-1; rank >= 0; rank--){
                sender.sendMessage("§l["+ (size_of_point - size_of_point + stable_point) +"] " + CountPoint.pointOfEveryone.get(rank).toString());
                stable_point++;
            }
            return true;
        }
        Player target = Bukkit.getServer().getPlayer(args[0]);
        if(target == null){
            sender.sendMessage("§cThe player §r§l"+ args[0] + "§r §c§lis not connected");
            return true;
        }
        if(!TimerTasks.RUN){
            sender.sendMessage("§7"+ args[0]+" : §a§l0");
        }else {
            for(int index = 0; index <= Bukkit.getOnlinePlayers().size(); index++) {
                if(target.getUniqueId().equals(CountPoint.pointOfEveryone.get(index).getUuid())) {
                    sender.sendMessage(CountPoint.pointOfEveryone.get(index).toString());
                    return true;
                }
            }
        }
        return false;
    }
}
