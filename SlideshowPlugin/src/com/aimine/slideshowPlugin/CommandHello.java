package com.aimine.slideshowPlugin;


//import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.tek.idisplays.Reference;
import com.tek.idisplays.Selection;

public class CommandHello implements CommandExecutor{
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		if(args.length == 1) {
			if(args[0].equalsIgnoreCase("create")) {
				if(sender.hasPermission(Ref.PERMISSION_CREATE)) {
					if(!SlideshowPlugin.getInstance().getSelections().containsKey(player.getUniqueId())) {
						String[] images = Ref.bannerFolder.list();
						Selection selection = new Selection(images[0]);
						if(selection.imageExists()) {
							SlideshowPlugin.getInstance().getSelections().put(player.getUniqueId(), selection);
							player.getInventory().addItem(Reference.creationWand);
							sender.sendMessage(Ref.PREFIX + Ref.colour("&aGiven you the display creation wand"));
							sender.sendMessage(Ref.colour("&7- &6Right click on the face of the first block"));
							sender.sendMessage(Ref.colour("&7- &6Right click on the other block"));
							sender.sendMessage(Ref.colour("&7- &6You can cancel display creation by dropping the item"));
							}
						else {
							sender.sendMessage(Ref.PREFIX + Ref.colour("&cThat image does not exist"));
						}
					}
					else {
						sender.sendMessage(Ref.PREFIX + Ref.colour("&cYou're already creating a display"));
					}
				}
				else {
					sender.sendMessage(Ref.PREFIX + Ref.colour("&cYou do not have enough permissions to use this"));
				}
			}
			else {
				sender.sendMessage(Ref.PREFIX + Ref.colour("&cInvalid syntax"));
			}
		}
		else {
			sender.sendMessage(Ref.PREFIX + Ref.colour("&cInvalid syntax"));
		}
		
		return true;
	}

}
