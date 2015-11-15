package com.civcraft.command.town;

import com.civcraft.command.CommandBase;
import com.civcraft.exception.CivException;
import com.civcraft.main.CivMessage;
import com.civcraft.object.Civilization;
import com.civcraft.object.Resident;
import com.civcraft.object.Town;
import com.civcraft.structure.Bank;
import com.civcraft.structure.Blacksmith;
import com.civcraft.structure.Grocer;
import com.civcraft.structure.Library;
import com.civcraft.structure.ScoutTower;
import com.civcraft.structure.Stable;
import com.civcraft.structure.Store;
import com.civcraft.structure.Structure;

public class TownSetCommand extends CommandBase {

	@Override
	public void init() {
		command = "/town set";
		displayName = "Town Set";
		commands.put("taxrate", "Change the town's property tax rate.");
		commands.put("flattax", "Change the town's flat tax on membership.");
		commands.put("bankfee", "Change the town Bank's non member fee");
		commands.put("storefee", "Change the town Store's non member fee");
		commands.put("grocerfee", "Change the town Grocer's non member fee");
		commands.put("libraryfee", "Change the town Library's non member fee");
		commands.put("blacksmithfee", "Change the town Blacksmith's non member fee");
		commands.put("stablefee", "Change the town Stable's non member fee");
		commands.put("scoutrate", "[10/30/60] Change the rate at which scout towers report no player positions.");
		
	}
	
	public void stablefee_cmd() throws CivException {
		Town town = getSelectedTown();
		Integer feeInt = getNamedInteger(1);
		
		Structure struct = town.findStructureByConfigId("s_stable");
		if (struct == null) {
			throw new CivException("Your town does not own a Stable.");
		}
		
		Stable stable = (Stable)struct;
		
		if (feeInt < Stable.FEE_MIN || feeInt > Stable.FEE_MAX) {
			throw new CivException("Must be a number between 5% and 100%");
		}
	
		stable.setNonResidentFee(((double)feeInt/100));
		stable.updateSignText();
		CivMessage.sendSuccess(sender, "Set Stable fee rate to "+feeInt+"%");
	}
	
	public void scoutrate_cmd() throws CivException {
		Town town = getSelectedTown();
		Integer rate = getNamedInteger(1);
		
		if (rate != 10 && rate != 30 && rate != 60) {
			throw new CivException("Reporting rate must be 10,30, or 60 seconds.");
		}
		
		for (Structure struct : town.getStructures()) {
			if (struct instanceof ScoutTower) {
				((ScoutTower)struct).setReportSeconds(rate);
			}
		}
		
		CivMessage.sendSuccess(sender, "Set scout tower report interval to "+rate+" seconds.");
	}
	
	public void blacksmithfee_cmd() throws CivException {
		Town town = getSelectedTown();
		Integer feeInt = getNamedInteger(1);
		
		if (feeInt < 5 || feeInt > 15) {
			throw new CivException("Must be a number between 5% and 15%");
		}
		
		Structure struct = town.findStructureByConfigId("s_blacksmith");
		if (struct == null) {
			throw new CivException("Your town does not own a Blacksmith.");
		}

		((Blacksmith)struct).setNonResidentFee(((double)feeInt/100));
		((Blacksmith)struct).updateSignText();
		
		CivMessage.sendSuccess(sender, "Set Blacksmith fee rate to "+feeInt+"%");
	}
	
	
	public void libraryfee_cmd() throws CivException {
		Town town = getSelectedTown();
		Integer feeInt = getNamedInteger(1);
		
		if (feeInt < 5 || feeInt > 15) {
			throw new CivException("Must be a number between 5% and 15%");
		}
		
		Structure struct = town.findStructureByConfigId("s_library");
		if (struct == null) {
			throw new CivException("Your town does not own a library.");
		}

		((Library)struct).setNonResidentFee(((double)feeInt/100));
		((Library)struct).updateSignText();
		
		CivMessage.sendSuccess(sender, "Set library fee rate to "+feeInt+"%");
	}
	
	public void grocerfee_cmd() throws CivException {
		Town town = getSelectedTown();
		Integer feeInt = getNamedInteger(1);
		
		if (feeInt < 5 || feeInt > 15) {
			throw new CivException("Must be a number between 5% and 15%");
		}
		
		Structure struct = town.findStructureByConfigId("s_grocer");
		if (struct == null) {
			throw new CivException("Your town does not own a grocer.");
		}

		((Grocer)struct).setNonResidentFee(((double)feeInt/100));
		((Grocer)struct).updateSignText();
		
		CivMessage.sendSuccess(sender, "Set grocer fee rate to "+feeInt+"%");
		
	}
	
	public void storefee_cmd() throws CivException {
		Town town = getSelectedTown();
		Integer feeInt = getNamedInteger(1);
		
		if (feeInt < 5 || feeInt > 15) {
			throw new CivException("Must be a number between 5% and 15%");
		}
		
		Structure struct = town.findStructureByConfigId("s_store");
		if (struct == null) {
			throw new CivException("Your town does not own a store.");
		}
		
		((Store)struct).setNonResidentFee(((double)feeInt/100));
		((Store)struct).updateSignText();
		
		CivMessage.sendSuccess(sender, "Set store fee rate to "+feeInt+"%");
		
	}
	
	public void bankfee_cmd() throws CivException {
		Town town = getSelectedTown();
		Integer feeInt = getNamedInteger(1);
		
		if (feeInt < 5 || feeInt > 15) {
			throw new CivException("Must be a number between 5% and 15%");
		}
		
		Structure struct = town.findStructureByConfigId("s_bank");
		if (struct == null) {
			throw new CivException("Your town does not own a bank.");
		}
		
		((Bank)struct).setNonResidentFee(((double)feeInt/100));
		((Bank)struct).updateSignText();
		
		CivMessage.sendSuccess(sender, "Set bank fee rate to "+feeInt+"%");
		
	}
	
	public void taxrate_cmd() throws CivException {
		Town town = getSelectedTown();
		
		if (args.length < 2) {
			throw new CivException("Please specify a tax rate.");
		}
		
		try { 
			town.setTaxRate(Double.valueOf(args[1])/100);
		} catch (NumberFormatException e) {
			throw new CivException(args[1]+" is not a number.");
		}
		
		town.quicksave();
		CivMessage.sendTown(town, "Town changed property tax rate to "+args[1]+"%");
	}

	public void flattax_cmd() throws CivException {
		Town town = getSelectedTown();	
		if (args.length < 2) {
			throw new CivException("Please specify a tax rate.");
		}
				
		try { 
			town.setFlatTax(Integer.valueOf(args[1]));
		} catch (NumberFormatException e) {
			throw new CivException(args[1]+" is not a number.");
		}
		
		town.quicksave();
		CivMessage.send(town, "Town changed flat tax to "+args[1]);
	}
	
	@Override
	public void doDefaultAction() throws CivException {
		showHelp();
	}

	@Override
	public void showHelp() {
		showBasicHelp();
	}

	@Override
	public void permissionCheck() throws CivException {
		Civilization civ = getSenderCiv();
		Town town = getSelectedTown();
		Resident resident = getResident();
		
		if (!town.isInGroup("mayors", resident) && !town.isInGroup("assistants", resident)
				&& !civ.getLeaderGroup().hasMember(resident)) {
			throw new CivException("Only mayors, assistants, and leaders can use this command.");
		}		
	}
}
