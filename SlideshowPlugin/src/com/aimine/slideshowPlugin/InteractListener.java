package com.aimine.slideshowPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.persistence.PersistentDataType;

import com.tek.idisplays.Reference;
import com.tek.idisplays.Selection;
import com.tek.idisplays.map.DisplayManager;
import com.tek.idisplays.tasks.SelectionHighlightTask;

public class InteractListener implements Listener { //A listener that waits for interaction from all players
	
	
	private static Selection selectTwo; //Selection variable that contains 
	private static int count; //Variable used to store place in the slideshow
	private static String[] images; //An array that is used to store the filenames of all images in the slideshow
	
	@EventHandler
	public void onInteract(PlayerInteractEvent event) { //Executes when a player interacts with anything
		
		if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(event.getItem() != null) { //Checks the player is interacting with an item
				final Player player = event.getPlayer();
				if(event.getItem().hasItemMeta() && event.getItem().getItemMeta().getPersistentDataContainer().has(Ref.remoteIdentifier, PersistentDataType.BYTE)) { //Checks that the item is a forward remote
					Block baseBlock = selectTwo.getFrom();
					for (Entity e : baseBlock.getWorld().getNearbyEntities(baseBlock.getLocation(), 2, 2, 2)) {
						ItemFrame frame = (ItemFrame) e; //Selects the slideshow display
						DisplayManager.attemptDisplayDeletion(frame, player); //Clears the slideshow display 
						selectTwo.setImageName(images[count]);
						DisplayManager.attemptCreateDisplay(selectTwo, player); //Display image with the current count
						if (count == images.length - 1) {
							count = 0;
							break;
						}
						else {
							count++; //Increments count in order to display next image
							break;
						}
					}
				}
			}
		}
		
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(event.getItem() != null) {
				final Player player = event.getPlayer();
				if(SlideshowPlugin.getInstance().getSelections().containsKey(player.getUniqueId())) {
					if(event.getItem().hasItemMeta() && event.getItem().getItemMeta().getPersistentDataContainer().has(Reference.wandIdentifier, PersistentDataType.BYTE)) { //Checks that the player has the creation wand to draw the display for the slideshow
						Selection selection = SlideshowPlugin.getInstance().getSelections().get(player.getUniqueId());
						if(selection.getFrom() == null) {
							Block blockLook = player.getTargetBlockExact(5);
							BlockFace faceLooking = SelectionHighlightTask.getBlockFace(player);
							
							if(faceLooking == BlockFace.UP || faceLooking == BlockFace.DOWN) {
								player.sendMessage(Ref.PREFIX + Ref.colour("&cYou cannot place displays on the floor or ceiling"));
							} else {
								selection.setFrom(blockLook.getRelative(faceLooking));
								selection.setFace(faceLooking);
								player.sendMessage(Ref.PREFIX + Ref.colour("&aFirst display corner location set"));
							}
						} else {
							Block blockLook = player.getTargetBlockExact(5);
							BlockFace faceLooking = SelectionHighlightTask.getBlockFace(player);
							if(faceLooking == null || blockLook == null) return;
							Block fromBlock = selection.getFrom();
							Block toBlock = blockLook.getRelative(faceLooking);
							
							boolean works = true;
							if(selection.getFace() == BlockFace.WEST || selection.getFace() == BlockFace.EAST) if(Math.abs(toBlock.getX() - fromBlock.getX()) != 0) works = false;
							if(selection.getFace() == BlockFace.SOUTH || selection.getFace() == BlockFace.NORTH) if(Math.abs(toBlock.getZ() - fromBlock.getZ()) != 0) works = false;
						
							if(works) { //Only allows player to cyle through slideshow if there is a slideshow set up to cycle through
								selection.setTo(toBlock);
								player.getInventory().setItemInMainHand(null);
								player.sendMessage(Ref.PREFIX + Ref.colour("&aSelection completed! Creating display..."));
								
								selectTwo = selection;
								DisplayManager.attemptCreateDisplay(selection, player);
								
								player.getInventory().addItem(Ref.forwardRemote);
								images = Reference.bannerFolder.list();
								count = 1;
								VerifyImages(player);
								player.sendMessage(Ref.PREFIX + Ref.colour("&aGiven you the forward remote"));
								player.sendMessage(Ref.colour("&7- &6Right click to move to the next slide"));
								
								SlideshowPlugin.getInstance().getSelections().remove(player.getUniqueId());
							} else {
								player.sendMessage(Ref.PREFIX + Ref.colour("&cYou cannot place a display at this location (Must be 1 block thick)"));
							}
						}
					}
				}
			}
		}
	}
	public static Selection getSelection() { //Getter method for selection
		return selectTwo;
	}
	private void VerifyImages(Player sender) { //Verify that images in directory are of the right filetype and exclude them from the slideshow if not
		for(int i = 0; i < images.length; i++) {
			String filetype = (images[i].substring(images[i].length() - 4)).toLowerCase();
			String a = ".png";
			String b = ".jpg";
			if(!(filetype.equalsIgnoreCase(a) || filetype.equalsIgnoreCase(b))) {
				sender.sendMessage(images[i] + " is not a supported filetype and will not be included in slideshow");
				List<String> imageList = new ArrayList<String>(Arrays.asList(images));
				imageList.remove(images[i]);
				images = imageList.toArray(new String[imageList.size()]);
			}
			
		}
	}
}
