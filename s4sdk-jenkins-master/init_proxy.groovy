import jenkins.model.*
import hudson.ProxyConfiguration
import java.net.URLDecoder
import java.util.logging.Logger

Logger logger = Logger.getLogger("s4sdk.InitProxy")

def instance = Jenkins.instance

String httpProxyEnv = System.getenv("http_proxy")
String httpsProxyEnv = System.getenv("https_proxy")
String noProxyEnv = System.getenv("no_proxy")

// delete potentially existing proxy.xml (we only use transient values derived from env vars)
def proxyFile = new File(instance.getRootDir(), "proxy.xml")
if(proxyFile.exists()) {
    logger.severe("Unexpected proxy.xml file detected. Trying to delete it...")
    boolean success = proxyFile.delete()
    if(success) {
        logger.info("Successfully deleted proxy.xml")
    }
    else {
        throw new Error("Failed to delete proxy.xml")
    }
}

URL proxyUrl = null;
if (httpsProxyEnv?.trim()) {
    proxyUrl = new URL(httpsProxyEnv)
}

// prefer https_proxy server over http_proxy
if (!proxyUrl && httpProxyEnv?.trim()) {
    proxyUrl = new URL(httpProxyEnv)
}

String[] nonProxyHosts = null
if ( noProxyEnv?.trim() && proxyUrl )  {
    /*
    Insert wildcards to have a rough conversion between the unix-like no-proxy list and the java notation.
    For example, `localhost,.corp,.maven.apache.org,x.y.,myhost` will be transformed to
    `[*localhost,*.corp,*.maven.apache.org,*x.y,*myhost]`.
    */

    nonProxyHosts = noProxyEnv.split(',')
        .collect { it.replaceFirst('\\.$', '')}
        .collect { "*${it}" };
}

if(proxyUrl) {
    Map credentials = extractCredentials(proxyUrl)
    String host = proxyUrl.getHost()
    int port = proxyUrl.getPort()

    logger.info("Setting Jenkins network proxy to ${host}:${port} ${!credentials ? "without" : ""} using credentials. No proxy patterns: ${nonProxyHosts}")
    instance.proxy = new ProxyConfiguration(host, port, credentials?.username, credentials?.password, nonProxyHosts.join("\n"))
}
else {
    logger.fine("No network proxy configured.")
    instance.proxy = null
}

def extractCredentials(URL proxyURL) {
    def userInfo = proxyURL.getUserInfo()
    if(!userInfo) {
        return null
    }

    String[] splitted = userInfo.split(":")
    if(splitted.length != 2) {
        throw new Error("Failed to extract network proxy credentials. Expected format: 'http://myuser:mypass@myproxy.corp:8080'")
    }

    return [ username: URLDecoder.decode(splitted[0], "UTF-8"), password: URLDecoder.decode(splitted[1], "UTF-8") ]
}
