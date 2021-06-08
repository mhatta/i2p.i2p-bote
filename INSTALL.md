How to install I2P-Bote
=======================

Getting I2P-Bote installed on your I2P Router successfully
requires 2 steps:

### 1. Obtain and Install the Certificate

In order to use I2P-Bote with your I2P router as a plugin, you will
need to download mhatta's plugin signing keys and place them in the
plugin certificates directory.

#### Windows

On Windows, you will need to download the certificate to I2P's "Application
Data" directory. The way that will work on the maximum number of Windows
platforms is to open the "`Command Prompt`" and paste the filowing commands:


```batch
bitsadmin /create mhattacert
bitsadmin /addfile mhattacert https://people.debian.org/~mhatta/mhatta_at_mail.i2p.crt "%LOCALAPPDATA%/I2P/certificates/plugins/mhatta_at_mail.i2p.crt"
bitsadmin /resume mhattacert
```

If you are able to use "`PowerShell`" instead, you may run the command:

```ps
Invoke-WebRequest -Uri https://people.debian.org/~mhatta/mhatta_at_mail.i2p.crt -OutFile "%LOCALAPPDATA%/I2P/certificates/plugins/mhatta_at_mail.i2p.crt"
```

#### Mac OSX

On OSX, you can run the following command:

```bash
wget -O "/Users/$(whoami)/Library/Application Support/i2p/certificates/plugins/mhatta_at_mail.i2p.crt" \
	"https://people.debian.org/~mhatta/mhatta_at_mail.i2p.crt"
```

OSX may ask you for elevated privileges to write to this directory.

#### Linux

There are two possible types of Linux installs.

##### Jar Install

On Linux, if you installed from a .jar package, you can run the following
command:

```bash
wget -O "$HOME/i2p/certificates/plugins/mhatta_at_mail.i2p.crt" \
	"https://people.debian.org/~mhatta/mhatta_at_mail.i2p.crt"
```

Or, anonymously:

```
torsocks wget -O "$HOME/i2p/certificates/plugins/mhatta_at_mail.i2p.crt" \
	"https://people.debian.org/~mhatta/mhatta_at_mail.i2p.crt"
```

##### Debian Install

If you installed from using `sudo apt-get install i2p` or similar, you will
need to do this as the I2P user instead, like this:

```bash
sudo -u i2psvc mkdir "/var/lib/i2p/i2p-config/certificates/plugins/"
sudo -u i2psvc wget -O "/var/lib/i2p/i2p-config/certificates/plugins/mhatta_at_mail.i2p.crt" \
	"https://people.debian.org/~mhatta/mhatta_at_mail.i2p.crt"
```

### 2. Install I2P-Bote

Now that the certificate is installed, you can install the I2P-Bote plugin
as you normally would install an I2P plugin, by copy-and-pasting the download
URL into the "[Plugins Page](http://127.0.0.1:7657/configplugins)."

This is the current URL.

`https://github.com/mhatta/i2p.i2p-bote/releases/download/v0.4.8.6/i2pbote-0.4.8.6.su3`

By default, I2P will try to download the plugin using I2P to obfuscate the location
of the downloader. If you don't have an I2P Outproxy to use, then you may need to
use an in-I2P mirror of the plugin.

`http://o4rsxdeepfrnncsnjq675xogp5v5qkbfgbt6ooqeyfvlifobrjxq.b32.i2p/i2pbote-0.4.8.6.su3`