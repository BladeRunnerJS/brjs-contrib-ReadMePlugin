import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.bladerunnerjs.model.App;
import org.bladerunnerjs.testing.specutility.engine.SpecTest;
import org.junit.Before;
import org.junit.Test;

public class ReadMeAppTest extends SpecTest {
    private App app;
    private List<String> textList;

    @Before
    public void initTestObjects() throws Exception {
        given(brjs).hasModelObserverPlugins(new ReadMeAppPlugin()).and(brjs).hasBeenCreated();
        app = brjs.app("app1");
    }

    @Test
    public void isFilePresentNormalCreate() throws Exception {
        when(app).create();
        then(app).hasFile("README.md");
    }

    @Test
    public void isFilePresentManualCreate() throws Exception {
        when(brjs.file("apps/app2")).containsFile("myNewFile");
        brjs.apps();
        then(brjs.app("app2")).hasFile("README.md");
    }

    @Test
    public void isLine1Correct() throws Exception {
        when(app).create();
        textList = FileUtils.readLines((app).file("README.md"), "UTF-8");
        assertTrue(textList.get(0).contentEquals("# " + "app1"));
    }

    @Test
    public void isLine2Correct() throws Exception {
        when(app).create();
        textList = FileUtils.readLines((app).file("README.md"), "UTF-8");
        assertTrue(textList.get(1).contentEquals("Default readme for " + "app1"));
    }

    @Test
    public void isDateCorrect() throws Exception {
        Date date = new Date();
        String textDate = date.toString().substring(0, 9);
        when(app).create();
        textList = FileUtils.readLines((app).file("README.md"), "UTF-8");
        assertTrue(textList.get(2).contains(textDate));
    }

    @Test
    public void isUserCorrect() throws Exception {
        when(app).create();
        textList = FileUtils.readLines((app).file("README.md"), "UTF-8");
        assertTrue(textList.get(3).contentEquals("App created by " + "robm"));
    }
}