package net.minecraft.src;

import java.util.Map;
import java.util.Map.Entry;

import org.lwjgl.input.Mouse;

public class GCF_GuiControls extends GuiControls {

	protected GuiScreen parentScreen;
	// 親クラスでprivate宣言されている部分について書き直している。
	protected GameSettings options;
	protected int buttonId;
	// スクロール用
	protected float amountScrolled;
	protected float deltascrolled;

	public GCF_GuiControls(GuiScreen guiscreen, GameSettings gamesettings) {
		super(guiscreen, gamesettings);

		parentScreen = guiscreen;
		options = gamesettings;
		buttonId = -1;
		amountScrolled = 0F;
		deltascrolled = 0F;
	}

	private int getButtonPosition() {
		return width / 2 - 155;
	}

	@Override
	protected void actionPerformed(GuiButton guibutton) {
		if (guibutton.id != 200) {
			// ButtonIDをゲット
			buttonId = guibutton.id;
		}
		super.actionPerformed(guibutton);
	}

	@Override
	protected void mouseClicked(int i, int j, int k) {
		if (buttonId >= 0) {
			buttonId = -1;
		}
		super.mouseClicked(i, j, k);
	}

	@Override
	protected void keyTyped(char c, int i) {
		if (buttonId >= 0) {
			buttonId = -1;
		}
		super.keyTyped(c, i);
	}

	@Override
	public void drawScreen(int i, int j, float f) {
		// 描画関係を全部上書き
		bindAmountScrolled();
		
		drawDefaultBackground();
		drawCenteredString(fontRenderer, screenTitle, width / 2, 20, 0xffffff);
		int k = getButtonPosition();
		for (int l = 0; l < options.keyBindings.length; l++) {
			boolean flag = false;
			for (int i1 = 0; i1 < options.keyBindings.length; i1++) {
				if (i1 != l && options.keyBindings[l].keyCode == options.keyBindings[i1].keyCode) {
					flag = true;
				}
			}
			
			int j1 = l;
			int yy = 36 - (int) amountScrolled + 24 * (l >> 1);
			GuiButton gb = (GuiButton) buttonList.get(j1);
			if (buttonId == l) {
				gb.displayString = "" + EnumChatFormatting.WHITE + "> " + EnumChatFormatting.YELLOW + "??? " + EnumChatFormatting.WHITE + "<";
			} else if (flag) {
				gb.displayString = EnumChatFormatting.RED + this.options.getOptionDisplayString(j1);
			} else {
				gb.displayString = options.getOptionDisplayString(j1);
			}
			gb.yPosition = yy;
			gb.drawButton(mc, i, j);
			drawString(fontRenderer, options.getKeyBindingDescription(l), k
					+ (l % 2) * 160 + 70 + 6, yy + 7, -1);
		}
		// Done
		((GuiButton) buttonList.get(buttonList.size() - 1)).drawButton(mc, i, j);
	}

	@Override
	public void handleMouseInput() {
		super.handleMouseInput();
		// ホイールの獲得
		int i = Mouse.getEventDWheel();
		if (i != 0) {
			deltascrolled -= (float) i * 0.25F;
		}
	}

	private void bindAmountScrolled() {
		// スクロール範囲の制限
		amountScrolled += deltascrolled;
		deltascrolled = 0F;
		
		int i = 24 * (buttonList.size() >> 1) - (height - 36 - 36);
		if (i < 0) {
			i /= 2;
		}
		if (amountScrolled < 0.0F) {
			amountScrolled = 0.0F;
		}
		if (amountScrolled > (float) i) {
			amountScrolled = i;
		}
	}

}
