package com.wongt8.hunting_game.Command;

import com.wongt8.hunting_game.CountPoint.CountPoint;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
            sender.sendMessage("------ §lRank §r------ ");
            int index = CountPoint.pointOfEveryone.size()-1;
            int classement = 1;
            for(Player p : Bukkit.getOnlinePlayers()){
                sender.sendMessage("§l["+ classement +"] "+CountPoint.pointOfEveryone.get(index).toString());
                classement++;
                index--;
            }
            return true;
        }
        Player target = Bukkit.getServer().getPlayer(args[0]);
        if(target == null){
            sender.sendMessage("§cThe player §r§l"+ args[0] + "§r §c§lis not connected");
            return true;
        }
        int index = 0;
        for(Player p : Bukkit.getOnlinePlayers()){
            if(target.getUniqueId().equals(CountPoint.pointOfEveryone.get(index).getUuid())) {
                sender.sendMessage(CountPoint.pointOfEveryone.get(index).toString());
                return true;
            }
            index++;
        }
        return false;
    }
}