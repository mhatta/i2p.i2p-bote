package i2p.bote.android.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import androidx.preference.PreferenceManager;
import android.util.Log;

import net.i2p.android.ui.I2PAndroidHelper;
import net.i2p.client.I2PClient;
import net.i2p.data.DataHelper;
import net.i2p.util.FileUtil;
import net.i2p.util.OrderedProperties;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import i2p.bote.android.Constants;
import i2p.bote.android.R;

public class Init {
    private final Context ctx;
    private final String myDir;

    public enum RouterChoice {
        INTERNAL,
        ANDROID,
        REMOTE
    }

    public Init(Context c) {
        ctx = c;
        // This needs to be changed so that we can have an alternative place
        myDir = c.getFilesDir().getAbsolutePath();
    }

    /**
     * Parses settings and prepares the system for starting the Bote service.
     * @return the router choice.
     */
    public RouterChoice initialize(I2PAndroidHelper helper) {
        // Set up the locations so Router and WorkingDir can find them
        // We do this again here, in the event settings were changed.
        System.setProperty("i2p.dir.base", myDir);
        System.setProperty("i2p.dir.config", myDir);
        System.setProperty("wrapper.logfile", myDir + "/wrapper.log");

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        RouterChoice routerChoice;
        String i2cpHost, i2cpPort;
        if (prefs.getBoolean("i2pbote.router.auto", true)) {
            if (helper.isI2PAndroidInstalled())
                routerChoice = RouterChoice.ANDROID;
            else
                routerChoice = RouterChoice.INTERNAL;
            i2cpHost = "internal";
            i2cpPort = "internal";
        } else {
            // Check manual settings
            String which = prefs.getString("i2pbote.router.use", "internal");
            if ("remote".equals(which)) {
                routerChoice = RouterChoice.REMOTE;
                i2cpHost = prefs.getString("i2pbote.i2cp.tcp.host", "127.0.0.1");
                i2cpPort = prefs.getString("i2pbote.i2cp.tcp.port", "7654");
            } else {
                if ("android".equals(which))
                    routerChoice = RouterChoice.ANDROID;
                else // Internal router
                    routerChoice = RouterChoice.INTERNAL;
                i2cpHost = "internal";
                i2cpPort = "internal";
            }
        }
        // Set the I2CP host/port
        System.setProperty(I2PClient.PROP_TCP_HOST, i2cpHost);
        System.setProperty(I2PClient.PROP_TCP_PORT, i2cpPort);

        if (routerChoice == RouterChoice.INTERNAL) {
            mergeResourceToFile();

            File certDir = new File(myDir, "certificates");
            certDir.mkdir();
            File certificates = new File(myDir, "certificates");
            File[] allcertificates = certificates.listFiles();
            if ( allcertificates != null) {
                for (File f : allcertificates) {
                    Log.d(Constants.ANDROID_LOG_TAG, "Deleting old certificate file/dir " + f);
                    FileUtil.rmdir(f, false);
                }
            }
            unzipResourceToDir();
        }

        return routerChoice;
    }

    /**
     *  Load defaults from resource,
     *  then add props from settings,
     *  and write back
     *  */
    private void mergeResourceToFile() {
        InputStream in = null;
        InputStream fin = null;

        try {
            in = ctx.getResources().openRawResource(R.raw.router_config);
            Properties props = new OrderedProperties();
            try {
                fin = new FileInputStream(new File(myDir, "router.config"));
                DataHelper.loadProps(props, fin);
            } catch (IOException ignored) {
            }

            // write in default settings
            DataHelper.loadProps(props, in);

            // override with user settings
            File path = new File(myDir, "router.config");
            DataHelper.storeProps(props, path);
        } catch (IOException | Resources.NotFoundException ignored) {
        } finally {
            if (in != null) try {
                in.close();
            } catch (IOException ignored) {
            }
            if (fin != null) try {
                fin.close();
            } catch (IOException ignored) {
            }
        }
    }

    /**
     */
    private void unzipResourceToDir() {
        InputStream in = null;
        FileOutputStream out = null;
        ZipInputStream zis = null;

        Log.d(Constants.ANDROID_LOG_TAG, "Creating files in '" + myDir + "/" + "certificates" + "/' from resource");
        try {
            // Context methods
            in = ctx.getResources().openRawResource(R.raw.certificates_zip);
            zis = new ZipInputStream((in));
            ZipEntry ze;
            while ((ze = zis.getNextEntry()) != null) {
                try {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];
                    int count;
                    while ((count = zis.read(buffer)) != -1) {
                        baos.write(buffer, 0, count);
                    }
                    String name = ze.getName();
                    File f = new File(myDir + "/" + "certificates" +"/" + name);
                    if (ze.isDirectory()) {
                        Log.d(Constants.ANDROID_LOG_TAG, "Creating directory " + myDir + "/" + "certificates" +"/" + name + " from resource");
                        f.mkdir();
                    } else {
                        Log.d(Constants.ANDROID_LOG_TAG, "Creating file " + myDir + "/" + "certificates" +"/" + name + " from resource");
                        byte[] bytes = baos.toByteArray();
                        out = new FileOutputStream(f);
                        out.write(bytes);
                    }
                } catch (IOException ignored) {
                } finally {
                    if (out != null) { try { out.close(); } catch (IOException ignored) {} out = null; }
                }
            }
        } catch (IOException | Resources.NotFoundException ignored) {
        } finally {
            if (in != null) try { in.close(); } catch (IOException ignored) {}
            if (out != null) try { out.close(); } catch (IOException ignored) {}
            if (zis != null) try { zis.close(); } catch (IOException ignored) {}
        }
    }
}
