package io.github.gutyerrez.market;

import io.github.gutyerrez.core.spigot.CustomPlugin;
import lombok.Getter;

/**
 * @author SrGutyerrez
 */
public class MarketPlugin extends CustomPlugin {

    @Getter
    private static MarketPlugin instance;

    public MarketPlugin() {
        MarketPlugin.instance = this;
    }

    /**
     * Enable this plugin an call all functions to
     * be executed when this plugin is enabled
     */
    @Override
    public void onEnable() {
    }

}
