package net.minecraft.src;


public class mod_GCF_GuiControlsFix extends BaseMod {

	@Override
	public String getVersion() {
		return "1.6.2-2";
	}

	@Override
	public String getName() {
		return "GuiControlsFix";
	}

	@Override
	public void load() {
		// Forgeの時は要らない
		if (ModLoader.isModLoaded("Forge")) {
			ModLoader.getLogger().fine("Since Forge was found, it is not used.");
		} else {
			ModLoader.setInGUIHook(this, true, false);
		}
	}

	@Override
	public boolean onTickInGUI(float f, Minecraft minecraft, GuiScreen guiscreen) {
		// 1.4.2ではゲーム開始していないと呼ばれない模様
		if (guiscreen != null && guiscreen.getClass().isAssignableFrom(GuiControls.class)) {
			// GuiControlsを開いたらFixで乗っ取る
			System.out.println("Replace GuiControls.");
			try {
				GuiScreen guiscreen1 = (GuiScreen)ModLoader.getPrivateValue(GuiControls.class, (GuiControls)guiscreen, 0);
				GameSettings options1 = (GameSettings)ModLoader.getPrivateValue(GuiControls.class, (GuiControls)guiscreen, 2);
				minecraft.displayGuiScreen(new GCF_GuiControls(guiscreen1, options1));
			}
			catch (Exception e) {
			}
		}
		return true;
	}

}
