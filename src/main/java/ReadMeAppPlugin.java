import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.bladerunnerjs.model.App;
import org.bladerunnerjs.model.BRJS;
import org.bladerunnerjs.model.engine.Node;
import org.bladerunnerjs.model.events.NodeCreatedEvent;
import org.bladerunnerjs.model.events.NodeDiscoveredEvent;
import org.bladerunnerjs.plugin.Event;
import org.bladerunnerjs.plugin.EventObserver;
import org.bladerunnerjs.plugin.base.AbstractModelObserverPlugin;

//This plugin was created by Thomas Jager (QA from rob moore)

public class ReadMeAppPlugin extends AbstractModelObserverPlugin implements EventObserver {
    private BRJS brjs;

    @Override
    public void onEventEmitted(Event event, Node node) {
        if (event instanceof NodeCreatedEvent && node instanceof App || event instanceof NodeDiscoveredEvent && node instanceof App && node.dirExists()) {
            createReadMe((App) node);
        }
    }

    @Override
    public void setBRJS(BRJS brjs) {
        this.brjs = brjs;
        this.brjs.addObserver(NodeCreatedEvent.class, this);
        this.brjs.addObserver(NodeDiscoveredEvent.class, this);
    }

    private void createReadMe(App app) {
        String name = app.getName();
        List<String> text = Arrays.asList("# " + name, "Default readme for " + name, "App created on " + new Date(), "App created by " + System.getProperty("user.name"));
        writeContentsToReadMe(app.file("README.md"), text);
    }

    private void writeContentsToReadMe(File appRead, List<String> text) {
        try {
            FileUtils.writeLines(appRead, text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}