package fr.xephi.authme.process;

import fr.xephi.authme.AuthMe;
import fr.xephi.authme.process.login.ProcessSyncPlayerLogin;
import fr.xephi.authme.process.logout.ProcessSyncPlayerLogout;
import fr.xephi.authme.process.quit.ProcessSyncPlayerQuit;
import fr.xephi.authme.process.register.ProcessSyncEmailRegister;
import fr.xephi.authme.process.register.ProcessSyncPasswordRegister;
import fr.xephi.authme.service.BukkitService;
import org.bukkit.entity.Player;

import javax.inject.Inject;
import java.util.List;

/**
 * Manager for scheduling synchronous processes internally from the asynchronous processes.
 * These synchronous processes are a continuation of the associated async processes; they only
 * contain certain tasks which may only be run synchronously (most interactions with Bukkit).
 * These synchronous tasks should never be called aside from the asynchronous processes.
 *
 * @see Management
 */
public class SyncProcessManager {

    @Inject
    private AuthMe authMe;

    @Inject
    private ProcessSyncEmailRegister processSyncEmailRegister;
    @Inject
    private ProcessSyncPasswordRegister processSyncPasswordRegister;
    @Inject
    private ProcessSyncPlayerLogin processSyncPlayerLogin;
    @Inject
    private ProcessSyncPlayerLogout processSyncPlayerLogout;
    @Inject
    private ProcessSyncPlayerQuit processSyncPlayerQuit;
    // todo: remove after debugging
    private final Runnable retiredError = new Runnable() {
        @Override
        public void run() {
            authMe.getLogger().info("Player has retired");
        }
    };


    public void processSyncEmailRegister(Player player) {
        player.getScheduler().run(authMe, st -> processSyncEmailRegister.processEmailRegister(player), retiredError);
    }

    public void processSyncPasswordRegister(Player player) {
        player.getScheduler().run(authMe, st -> processSyncPasswordRegister.processPasswordRegister(player), retiredError);
    }

    public void processSyncPlayerLogout(Player player) {
        player.getScheduler().run(authMe, st -> processSyncPlayerLogout.processSyncLogout(player), retiredError);
    }

    public void processSyncPlayerLogin(Player player, boolean isFirstLogin, List<String> authsWithSameIp) {
        player.getScheduler().run(authMe, st -> processSyncPlayerLogin.processPlayerLogin(player, isFirstLogin, authsWithSameIp), retiredError);
    }

    public void processSyncPlayerQuit(Player player, boolean wasLoggedIn) {
        player.getScheduler().run(authMe, st -> processSyncPlayerQuit.processSyncQuit(player, wasLoggedIn), retiredError);
    }
}
