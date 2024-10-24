package de.banarnia.fancyhomes.lang;

import de.banarnia.api.lang.ILanguage;

public enum Message implements ILanguage {
    PREFIX("§8[§6FancyHomes§8]§7"),
    COMMAND_ERROR_CONSOLE_NOT_SUPPORTED("%prefix% §cThis command may not be executed by console."),

    // COMMAND /home
    COMMAND_INFO_HOME_CONFIG_RELOADED("%prefix% The config has been reloaded."),
    COMMAND_INFO_HOME_TELEPORT("%prefix% Teleporting to §e%home%§7..."),
    COMMAND_INFO_HOME_WARMUP_STARTED("%prefix% You will be teleported in §e%time%s§7."),
    COMMAND_ERROR_HOME_CANCELED("%prefix% §cYou cannot do that right now."),
    COMMAND_ERROR_HOME_NOT_FOUND("%prefix% §cCould not find a home with the name §e%home%§c."),
    COMMAND_ERROR_HOME_EMPTY("%prefix% §cYou do not have any homes."),
    COMMAND_ERROR_HOME_OTHERS_NOT_FOUND("%prefix% §cCould not find a home with the name §e%home%§7 for player §e%player%§7."),
    COMMAND_ERROR_HOME_LIMIT_EXCEEDED("%prefix% §cYou can't use any homes because you exceeded the home limit."),
    COMMAND_ERROR_HOME_COOLDOWN("%prefix% §cYou can't do that again within the next §e%time%s§c."),
    COMMAND_ERROR_HOME_WARMUP_ABORT("%prefix% §eTeleport aborted..."),
    COMMAND_ERROR_HOME_NOT_SPECIFIED("%prefix% §cYou need to specify a home name."),
    COMMAND_ERROR_HOME_LOCATION_NOT_LOADED("%prefix% §cYou can't teleport to this home because the world is not loaded."),

    // Import
    COMMAND_INFO_HOME_IMPORT_STARTED("%prefix% §7Starting import from §e%source%§7."),
    COMMAND_ERROR_HOME_IMPORT_INVALID_IMPORTSOURCE("§cThis source is not implemented."),

    // COMMAND /sethome
    COMMAND_ERROR_SETHOME_UNDERSCORE("%prefix% §cThe home name may not contain underscores."),
    COMMAND_ERROR_SETHOME_LIMIT_REACHED("%prefix% §cYou don't have any homes left."),
    COMMAND_ERROR_SETHOME_LIMIT_EXCEEDED("%prefix% §cYou can't edit any homes because you exceeded the home limit."),
    COMMAND_INFO_SETHOME_RELOCATED("%prefix% You relocated the home §e%home% §7to your current location."),
    COMMAND_ERROR_SETHOME_RELOCATION_FAILED("%prefix% §cFailed to relocate the home."),
    COMMAND_ERROR_SETHOME_CANCELED("%prefix% §cYou cannot create the home §e%home% §cright now."),
    COMMAND_INFO_SETHOME_CREATED("%prefix% Successfully created a new home §e%home%§7."),
    COMMAND_ERROR_SETHOME_FAILED("%prefix% §cFailed to create a new home."),

    // COMMAND /delhome
    COMMAND_INFO_DELHOME_SUCCESS("%prefix% Successfully deleted the home §e%home%§7."),
    COMMAND_ERROR_DELHOME_CANCELED("%prefix% §cYou cannot delete the home §e%home% §cright now."),
    COMMAND_ERROR_DELHOME_FAILED("%prefix% §cFailed to delete the home."),

    // GUI
    GUI_ICON_SELECTION_TITLE("§7Select a new icon"),
    GUI_SAVE_NAME("§aSave"),
    GUI_CANCEL_NAME("§cCancel"),
    GUI_ICON_UPDATE_FAILED("%prefix% §cFailed to update the home icon."),
    GUI_CONFIRMATION_TITLE("§7Delete home?"),
    GUI_HOME_LEFTCLICK_TELEPORT("§eLeftclick to teleport"),
    GUI_HOME_SHIFTLEFTCLICK_EDIT_ICON("§eShift-Leftclick to edit the icon"),
    GUI_HOME_RIGHTCLICK_DELETE("§cRightclick to delete"),
    GUI_HOME_PAGE_PREVIOUS("§cBack"),
    GUI_HOME_PAGE_NEXT("§aNext")
    ;

    String defaultMessage, message;

    Message(String defaultMessage) {
        this.defaultMessage = defaultMessage;
    }

    @Override
    public String getKey() {
        return this.toString().toLowerCase().replace("_", "-");
    }

    @Override
    public String getDefaultMessage() {
        return defaultMessage;
    }

    @Override
    public String get() {
        String message = this.message != null ? this.message : defaultMessage;
        if (this == PREFIX)
            return message;

        return message.replace("%prefix%", PREFIX.get());
    }

    @Override
    public void set(String message) {
        this.message = message;
    }
}
