package com.github.mike10004.xvfbmanager;

import com.github.mike10004.xvfbunittesthelp.Assumptions;
import com.github.mike10004.xvfbunittesthelp.PackageManager;
import com.google.common.io.Files;
import com.novetta.ibg.common.image.ImageInfo;
import com.novetta.ibg.common.image.ImageInfos;
import com.novetta.ibg.common.io.ByteSources;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;

import static org.junit.Assert.*;

public class XwdFileToPngConverterTest {

    @Rule
    public TemporaryFolder tmp = new TemporaryFolder();

    @BeforeClass
    public static void checkPrequisites() throws Exception {
        Iterable<String> prereqs = XwdFileToPngConverter.getRequiredPrograms();
        Assumptions.assumeTrue("prequsities: " + prereqs, PackageManager.getInstance().queryCommandsExecutable(prereqs));
    }

    @Test
    public void convert() throws Exception {
        File xwdFile = tmp.newFile("example.xwd");
        ByteSources.gunzipping(getClass().getResource("/example.xwd.gz")).copyTo(Files.asByteSink(xwdFile));
        XwdFileToPngConverter converter = new XwdFileToPngConverter(tmp.getRoot().toPath());
        ImageioReadableScreenshot pngShot = converter.convert(XwdFileScreenshot.from(xwdFile));
        ImageInfo info = ImageInfos.read(pngShot.getRawFile());
        assertEquals("format", ImageInfo.Format.PNG, info.getFormat());
    }

}