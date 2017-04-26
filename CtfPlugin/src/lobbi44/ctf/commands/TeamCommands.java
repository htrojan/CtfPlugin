package lobbi44.ctf.commands;

import lobbi44.kt.command.CommandEvent;
import lobbi44.kt.command.annotations.Command;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.logging.Logger;

public class TeamCommands {

    private Scoreboard scoreboard;
    private Logger logger;

    public TeamCommands(Scoreboard scoreboard, Logger logger) {
        this.logger = logger;
        this.scoreboard = scoreboard;
    }

    @Command(name = "teams.create", permission = "teams.create", description = "Create a team", usage = "/command [teamname]")
    public boolean CreateTeam(CommandEvent event) {
        logger.info("Team is going to be created!");
        if (event.getArgs().size() != 1) {
            event.getCommandSender().sendMessage("Error. See usage");
            return false; //Error
        }

        logger.info("Arg = " + event.getArgs());

        try {
            scoreboard.registerNewTeam(event.getArgs().get(0));
        } catch (IllegalArgumentException e) {
            event.getCommandSender().sendMessage("This team does already exist");
            logger.info("Team already existent, ignoring");
            return false;
        }
        logger.info("Created team \"" + event.getArgs().get(0) + "\"");
        event.getCommandSender().sendMessage("Team registered successfully!");
        return true;
    }

    @Command(name = "teams.list", permission = "teams.list", description = "List all teams", usage = "/command")
    public boolean ListTeams(CommandEvent args) {
        StringBuilder message = new StringBuilder();
        for (Team t : scoreboard.getTeams())
            message.append(t.getDisplayName()).append("\n");
        args.getCommandSender().sendMessage(message.toString());
        return true;
    }

    @Command(name = "teams.remove", permission = "teams.remove", description = "Removes the specified team", usage = "/command [teamname]")
    public boolean RemoveTeam(CommandEvent event) {
        if (event.getArgs().size() != 1) {
            event.getCommandSender().sendMessage("Wrong number of arguments");
            return false;
        }

        try {
            //avoiding possible null pointer exception
            Team t = scoreboard.getTeam(event.getArgs().get(0));
            t.unregister();
            event.getCommandSender().sendMessage("Team removed successfully");
        } catch (IllegalArgumentException e) {
            event.getCommandSender().sendMessage("This team does not exist");
        }
        return true;
    }
}
