package com.meeku.tutorialPlugin;


//import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
//import org.bukkit.entity.Entity;
//import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;

import com.tek.idisplays.Reference;
import com.tek.idisplays.Selection;

public class CommandHello implements CommandExecutor{
	
	/*private static int count = 0;
	private String[] websites = {
			"https://images.unsplash.com/photo-1612416365463-9ddb415d7f71?ixlib=rb-1.2.1&ixid=MXwxMjA3fDB8MHxleHBsb3JlLWZlZWR8M3x8fGVufDB8fHw%3D&auto=format&fit=crop&w=500&q=60",
			"https://images.unsplash.com/photo-1612596551406-507d16b622d5?ixlib=rb-1.2.1&ixid=MXwxMjA3fDB8MHxleHBsb3JlLWZlZWR8M3x8fGVufDB8fHw%3D&auto=format&fit=crop&w=500&q=60",
			"https://images.unsplash.com/photo-1612359586864-07cb961d640f?ixlib=rb-1.2.1&ixid=MXwxMjA3fDB8MHxleHBsb3JlLWZlZWR8OHx8fGVufDB8fHw%3D&auto=format&fit=crop&w=500&q=60",
			"https://images.unsplash.com/photo-1612588086184-89c0b2d69467?ixlib=rb-1.2.1&ixid=MXwxMjA3fDB8MHxleHBsb3JlLWZlZWR8OXx8fGVufDB8fHw%3D&auto=format&fit=crop&w=500&q=60",
			"https://images.unsplash.com/photo-1612570620825-27392aed9fde?ixlib=rb-1.2.1&ixid=MXwxMjA3fDB8MHxleHBsb3JlLWZlZWR8MTF8fHxlbnwwfHx8&auto=format&fit=crop&w=500&q=60",
	};*/
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		if(args.length >= 1 && !(args[0].equalsIgnoreCase("folder"))) {
			if(args[0].equalsIgnoreCase("create")) {
				if(args.length == 2) {
					if(sender.hasPermission(Ref.PERMISSION_CREATE)) {
						if(!SpigotBlankPlugin.getInstance().getSelections().containsKey(player.getUniqueId())) {
							Selection selection = new Selection(args[1]);
							if(selection.imageExists()) {
								SpigotBlankPlugin.getInstance().getSelections().put(player.getUniqueId(), selection);
								player.getInventory().addItem(Reference.creationWand);
								sender.sendMessage(Ref.PREFIX + Ref.color("&aGiven you the display creation wand"));
								sender.sendMessage(Ref.color("&7- &6Right click on the face of the first block"));
								sender.sendMessage(Ref.color("&7- &6Right click on the other block"));
								sender.sendMessage(Ref.color("&7- &6You can cancel display creation by dropping the item"));
							} else {
								sender.sendMessage(Ref.PREFIX + Ref.color("&cThat image does not exist"));
							}
						} else {
							sender.sendMessage(Ref.PREFIX + Ref.color("&cYou're already creating a display"));
						}
					} else {
						sender.sendMessage(Ref.PREFIX + Ref.color("&cYou do not have enough permissions to use this"));
					}
				} else {
					sender.sendMessage(Ref.PREFIX + Ref.color("&cInvalid syntax"));
				}
			} 
			else {
				sender.sendMessage(Ref.PREFIX + Ref.color("&cInvalid syntax"));
			}
		} else {
			sender.sendMessage(Ref.PREFIX + Ref.color("&cInvalid syntax"));
		}
		
		return true;
	}

}
