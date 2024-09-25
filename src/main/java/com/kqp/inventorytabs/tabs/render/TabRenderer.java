package com.kqp.inventorytabs.tabs.render;

import static com.kqp.inventorytabs.init.InventoryTabs.*;

import java.awt.Rectangle;

import com.kqp.inventorytabs.init.InventoryTabs;
import com.kqp.inventorytabs.mixin.accessor.AbstractContainerScreenAccessor;
import com.kqp.inventorytabs.tabs.TabManager;
import com.kqp.inventorytabs.tabs.tab.Tab;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

/**
 * Handles the rendering of tabs.
 */
@OnlyIn(Dist.CLIENT)
public class TabRenderer {
    private static final ResourceLocation TABS_TEXTURE_FOR_SHADER = ResourceLocation.withDefaultNamespace("textures/gui/sprites/container/creative_inventory/tab_top_selected_1.png");
    private static final ResourceLocation TABS_TOP_LEFT_SELECTED_TEXTURE = ResourceLocation.withDefaultNamespace("container/creative_inventory/tab_top_selected_1");
    private static final ResourceLocation TABS_TOP_MIDDLE_SELECTED_TEXTURE = ResourceLocation.withDefaultNamespace("container/creative_inventory/tab_top_selected_4");
    private static final ResourceLocation TABS_TOP_RIGHT_SELECTED_TEXTURE = ResourceLocation.withDefaultNamespace("container/creative_inventory/tab_top_selected_7");
    private static final ResourceLocation TABS_BOTTOM_LEFT_SELECTED_TEXTURE = ResourceLocation.withDefaultNamespace("container/creative_inventory/tab_bottom_selected_1");
    private static final ResourceLocation TABS_BOTTOM_MIDDLE_SELECTED_TEXTURE = ResourceLocation.withDefaultNamespace("container/creative_inventory/tab_bottom_selected_4");
    private static final ResourceLocation TABS_BOTTOM_RIGHT_SELECTED_TEXTURE = ResourceLocation.withDefaultNamespace("container/creative_inventory/tab_bottom_selected_7");
    private static final ResourceLocation TABS_TOP_LEFT_UNSELECTED_TEXTURE = ResourceLocation.withDefaultNamespace("container/creative_inventory/tab_top_unselected_1");
    private static final ResourceLocation TABS_TOP_MIDDLE_UNSELECTED_TEXTURE = ResourceLocation.withDefaultNamespace("container/creative_inventory/tab_top_unselected_4");
    private static final ResourceLocation TABS_TOP_RIGHT_UNSELECTED_TEXTURE = ResourceLocation.withDefaultNamespace("container/creative_inventory/tab_top_unselected_7");
    private static final ResourceLocation TABS_BOTTOM_LEFT_UNSELECTED_TEXTURE = ResourceLocation.withDefaultNamespace("container/creative_inventory/tab_bottom_unselected_1");
    private static final ResourceLocation TABS_BOTTOM_MIDDLE_UNSELECTED_TEXTURE = ResourceLocation.withDefaultNamespace("container/creative_inventory/tab_bottom_unselected_4");
    private static final ResourceLocation TABS_BOTTOM_RIGHT_UNSELECTED_TEXTURE = ResourceLocation.withDefaultNamespace("container/creative_inventory/tab_bottom_unselected_7");
    private static final ResourceLocation BUTTONS_TEXTURE = InventoryTabs.id("textures/gui/buttons.png");

    public static final int TAB_WIDTH = 26;
    public static final int TAB_HEIGHT = 32;
    public static final int BUTTON_WIDTH = 15;
    public static final int BUTTON_HEIGHT = 13;

    public final TabManager tabManager;

    private TabRenderInfo[] tabRenderInfos;

    private long pageTextRefreshTime;

    public TabRenderer(TabManager tabManager) {
        this.tabManager = tabManager;
    }

    public void renderBackground(GuiGraphics gui) {
        gui.pose().pushPose();

        tabRenderInfos = getTabRenderInfos();

        for (int i = 0; i < tabRenderInfos.length; i++) {
            TabRenderInfo tabRenderInfo = tabRenderInfos[i];

            if (tabRenderInfo != null) {
                if (tabRenderInfo.tabReference != tabManager.currentTab) {
                    renderTab(gui, tabRenderInfo);
                }
            }
        }
        gui.pose().popPose();
    }

    public void renderForeground(GuiGraphics gui, double mouseX, double mouseY) {
        RenderSystem.setShaderTexture(0, TABS_TEXTURE_FOR_SHADER);

        for (int i = 0; i < tabRenderInfos.length; i++) {
            TabRenderInfo tabRenderInfo = tabRenderInfos[i];

            if (tabRenderInfo != null) {
                if (tabRenderInfo.tabReference == tabManager.currentTab) {
                    renderTab(gui, tabRenderInfo);
                }
            }
        }

        drawButtons(gui, mouseX, mouseY);

        drawPageText(gui);
    }

    private void drawButtons(GuiGraphics gui, double mouseX, double mouseY) {
        AbstractContainerScreen<?> currentScreen = tabManager.getCurrentScreen();

        RenderSystem.setShaderTexture(0, BUTTONS_TEXTURE);

        int width = ((AbstractContainerScreenAccessor) currentScreen).getImageWidth();
        int height = ((AbstractContainerScreenAccessor) currentScreen).getImageHeight();
        int oX = (currentScreen.width - width) / 2;
        int oY = (currentScreen.height - height) / 2;

        // Drawing back button
        int x = oX - BUTTON_WIDTH - 4;
        x += ((TabRenderingHints) currentScreen).getTopRowXOffset();
        int y = oY - 16;
        y += ((TabRenderingHints) currentScreen).getTopRowYOffset();
        boolean hovered = new Rectangle(x, y, BUTTON_WIDTH, BUTTON_HEIGHT).contains(mouseX, mouseY);
        int u = 0;
        u += tabManager.canGoBackAPage() && hovered ? BUTTON_WIDTH * 2 : 0;
        int v = tabManager.canGoBackAPage() ? 0 : 13;
        gui.blit(BUTTONS_TEXTURE, x, y, u, v, BUTTON_WIDTH, BUTTON_HEIGHT);

        // Drawing forward button
        x = oX + width + 4;
        x += ((TabRenderingHints) currentScreen).getTopRowXOffset();
        y = oY - 16;
        y += ((TabRenderingHints) currentScreen).getTopRowYOffset();
        hovered = new Rectangle(x, y, BUTTON_WIDTH, BUTTON_HEIGHT).contains(mouseX, mouseY);
        u = 15;
        u += tabManager.canGoForwardAPage() && hovered ? BUTTON_WIDTH * 2 : 0;
        v = tabManager.canGoForwardAPage() ? 0 : 13;
        gui.blit(BUTTONS_TEXTURE, x, y, u, v, BUTTON_WIDTH, BUTTON_HEIGHT);
    }

    private void drawPageText(GuiGraphics gui) {
        if (tabManager.getMaxPages() > 1 && pageTextRefreshTime > 0) {
            // TODO: Figure out rendering

            int color = 0xFFFFFFFF;

            if (pageTextRefreshTime <= 20) {
                //RenderSystem.disableTexture();
                RenderSystem.enableBlend();
                RenderSystem.defaultBlendFunc();
                RenderSystem.colorMask(true, true, true, true);
                float transparency = pageTextRefreshTime / 20F;

                color &= 0x00FFFFFF;
                color = ((int) (0xFF * transparency) << 24) | color;
            }

            AbstractContainerScreen<?> currentScreen = tabManager.getCurrentScreen();
            Font font = Minecraft.getInstance().font;

            int height = ((AbstractContainerScreenAccessor) currentScreen).getImageHeight();
            int oX = currentScreen.width;
            int oY = (currentScreen.height - height) / 2;

            String text = (tabManager.currentPage + 1) + " / " + (tabManager.getMaxPages() + 1);
            int x = (oX - font.width(text)) / 2;
            int y = oY - 34;

            gui.drawString(font, text, x, y, color);
        }
    }

    private void renderTab(GuiGraphics gui, TabRenderInfo tabRenderInfo) {
        AbstractContainerScreen<?> currentScreen = tabManager.getCurrentScreen();

        RenderSystem.setShaderTexture(0, TABS_TEXTURE_FOR_SHADER);
        gui.blitSprite(getRendarTabTexture(tabRenderInfo), tabRenderInfo.x, tabRenderInfo.y, tabRenderInfo.texW, tabRenderInfo.texH);

        tabRenderInfo.tabReference.renderTabIcon(gui, tabRenderInfo, currentScreen);
    }

    public void renderHoverTooltips(GuiGraphics gui, double mouseX, double mouseY) {
        for (int i = 0; i < tabRenderInfos.length; i++) {
            TabRenderInfo tabRenderInfo = tabRenderInfos[i];

            if (tabRenderInfo != null) {
                Rectangle itemRec = new Rectangle(tabRenderInfo.itemX, tabRenderInfo.itemY, 16, 16);

                if (itemRec.contains(mouseX, mouseY)) {
                    gui.renderTooltip(Minecraft.getInstance().font, tabRenderInfo.tabReference.getHoverText(),
                            (int) mouseX, (int) mouseY);
                }
            }
        }
    }

    public TabRenderInfo[] getTabRenderInfos() {
        AbstractContainerScreen<?> currentScreen = tabManager.getCurrentScreen();

        int maxRowLength = tabManager.getMaxRowLength();
        int numVisibleTabs;
        if(isBigInvLoaded) {
            numVisibleTabs = (maxRowLength * 2) + 5;
        } else if (isPlayerExLoaded) {
            numVisibleTabs = (maxRowLength * 2) - 3;
        } else if (isLevelzLoaded) {
            numVisibleTabs = (maxRowLength * 2) - 2;
        }else {
            numVisibleTabs = maxRowLength * 2;
        }
        int startingIndex = tabManager.currentPage * numVisibleTabs;

        TabRenderInfo[] tabRenderInfo = new TabRenderInfo[numVisibleTabs];

        int x = (currentScreen.width - ((AbstractContainerScreenAccessor) currentScreen).getImageWidth()) / 2;
        int y = (currentScreen.height - ((AbstractContainerScreenAccessor) currentScreen).getImageHeight()) / 2;

        for (int i = 0; i < numVisibleTabs; i++) {
            if (startingIndex + i < tabManager.tabs.size()) {
                // Setup basic info
                Tab tab = tabManager.tabs.get(startingIndex + i);

                // Create tab info object
                TabRenderInfo tabInfo = new TabRenderInfo();
                tabInfo.topRow = (i < maxRowLength);
                if(isPlayerExLoaded) {
                	tabInfo.topRow = i < maxRowLength - 3;
                } else if(isLevelzLoaded) {
                	tabInfo.topRow = i < maxRowLength - 2;
                }

                tabInfo.selected = (tab == tabManager.currentTab);
                tabInfo.tabReference = tab;
                tabInfo.index = startingIndex + i;

                // Calc x value
                tabInfo.x = x + i * (TAB_WIDTH + 1);
                if (!tabInfo.topRow) {
                    tabInfo.x -= maxRowLength * (TAB_WIDTH + 1);
                }

                // Calc y value
                if (tabInfo.topRow) {
                    tabInfo.y = y - 28;
                } else {
                    if(isBigInvLoaded) {
                        tabInfo.y = y + ((AbstractContainerScreenAccessor) currentScreen).getImageHeight() + 32;
                    } else {
                        tabInfo.y = y + ((AbstractContainerScreenAccessor) currentScreen).getImageHeight() - 4;
                    }
                }

                // Calc texture dimensions
                tabInfo.texW = 26;
                tabInfo.texH = 32;

                // Calc texture U ※旧処理も残す
                if (i == 0 || i == maxRowLength) {
                    tabInfo.texU = 0;
                } else {
                    tabInfo.texU = 26;
                }
                
                if (i == 0)            {tabInfo.renderPattern = TabRenderInfo.RenderTabType.LEFT; } else
                if (i == maxRowLength) {tabInfo.renderPattern = TabRenderInfo.RenderTabType.RIGHT; }
                else                   {tabInfo.renderPattern = TabRenderInfo.RenderTabType.MIDDLE; };
                
                // Calc texture V ※旧処理
                if (tabInfo.topRow) {
                    if (tabInfo.selected) {
                        tabInfo.texV = 32;
                    } else {
                        tabInfo.texV = 0;
                    }
                } else {
                    if (tabInfo.selected) {
                        tabInfo.texV = 96;
                    } else {
                        tabInfo.texV = 64;
                    }
                }

                // Calc item position
                if (tabInfo.topRow) {
                    tabInfo.itemX = tabInfo.x + 6;
                    tabInfo.itemY = tabInfo.y + 8;
                } else {
                    tabInfo.itemX = tabInfo.x + 6;
                    tabInfo.itemY = tabInfo.y + 6;
                }

                // Apply rendering hints
                if (currentScreen instanceof TabRenderingHints) {
                    if (tabInfo.topRow) {
                        if(isPlayerExLoaded) {
                            tabInfo.x += ((TabRenderingHints) currentScreen).getTopRowXOffset() + 87;
                            tabInfo.itemX += ((TabRenderingHints) currentScreen).getTopRowXOffset() + 87;
                        } else if(isLevelzLoaded) {
                            tabInfo.x += ((TabRenderingHints) currentScreen).getTopRowXOffset() + 54;
                            tabInfo.itemX += ((TabRenderingHints) currentScreen).getTopRowXOffset() + 54;
                        }else {
                            tabInfo.x += ((TabRenderingHints) currentScreen).getTopRowXOffset();
                            tabInfo.itemX += ((TabRenderingHints) currentScreen).getTopRowXOffset();
                        }
                        tabInfo.y += ((TabRenderingHints) currentScreen).getTopRowYOffset();
                        tabInfo.itemY += ((TabRenderingHints) currentScreen).getTopRowYOffset();
                    } else {
                        if(isBigInvLoaded) {
                            tabInfo.x += ((TabRenderingHints) currentScreen).getBottomRowXOffset() - 145;
                            tabInfo.itemX += ((TabRenderingHints) currentScreen).getBottomRowXOffset() - 145;
                        } else if(isPlayerExLoaded) {
                            tabInfo.x += ((TabRenderingHints) currentScreen).getBottomRowXOffset() + 86;
                            tabInfo.itemX += ((TabRenderingHints) currentScreen).getBottomRowXOffset() + 86;
                        } else if(isLevelzLoaded) {
                            tabInfo.x += ((TabRenderingHints) currentScreen).getBottomRowXOffset() + 60;
                            tabInfo.itemX += ((TabRenderingHints) currentScreen).getBottomRowXOffset() + 60;
                        }else {
                            tabInfo.x += ((TabRenderingHints) currentScreen).getBottomRowXOffset();
                            tabInfo.itemX += ((TabRenderingHints) currentScreen).getBottomRowXOffset();
                        }
                        tabInfo.y += ((TabRenderingHints) currentScreen).getBottomRowYOffset();
                        tabInfo.itemY += ((TabRenderingHints) currentScreen).getBottomRowYOffset();
                    }
                }

                tabRenderInfo[i] = tabInfo;
            }
        }

        return tabRenderInfo;
    }
    
    private ResourceLocation getRendarTabTexture(TabRenderInfo renderinfo) {
    	if (renderinfo.selected) {
        	if (renderinfo.topRow) {
        		return switch(renderinfo.renderPattern) {
        			case TabRenderInfo.RenderTabType.LEFT   -> TABS_TOP_LEFT_SELECTED_TEXTURE;
        			case TabRenderInfo.RenderTabType.MIDDLE -> TABS_TOP_MIDDLE_SELECTED_TEXTURE;
        			case TabRenderInfo.RenderTabType.RIGHT  -> TABS_TOP_RIGHT_SELECTED_TEXTURE;
        			default                                 -> null;
        		};
        	} else {
        		return switch(renderinfo.renderPattern) {
	    			case TabRenderInfo.RenderTabType.LEFT   -> TABS_BOTTOM_LEFT_SELECTED_TEXTURE;
	    			case TabRenderInfo.RenderTabType.MIDDLE -> TABS_BOTTOM_MIDDLE_SELECTED_TEXTURE;
	    			case TabRenderInfo.RenderTabType.RIGHT  -> TABS_BOTTOM_RIGHT_SELECTED_TEXTURE;
	    			default                                 -> null;
	    		};
        	}
    	} else {
        	if (renderinfo.topRow) {
        		return switch(renderinfo.renderPattern) {
        			case TabRenderInfo.RenderTabType.LEFT   -> TABS_TOP_LEFT_UNSELECTED_TEXTURE;
        			case TabRenderInfo.RenderTabType.MIDDLE -> TABS_TOP_MIDDLE_UNSELECTED_TEXTURE;
        			case TabRenderInfo.RenderTabType.RIGHT  -> TABS_TOP_RIGHT_UNSELECTED_TEXTURE;
        			default                                 -> null;
        		};
        	} else {
        		return switch(renderinfo.renderPattern) {
	    			case TabRenderInfo.RenderTabType.LEFT   -> TABS_BOTTOM_LEFT_UNSELECTED_TEXTURE;
	    			case TabRenderInfo.RenderTabType.MIDDLE -> TABS_BOTTOM_MIDDLE_UNSELECTED_TEXTURE;
	    			case TabRenderInfo.RenderTabType.RIGHT  -> TABS_BOTTOM_RIGHT_UNSELECTED_TEXTURE;
	    			default                                 -> null;
	    		};
        	}
    	}
    }

    public void update() {
        pageTextRefreshTime = Math.max(pageTextRefreshTime - 1, 0);
    }

    public void resetPageTextRefreshTime() {
        pageTextRefreshTime = 60;
    }
}
