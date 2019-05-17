import org.sonatype.nexus.blobstore.api.BlobStoreManager
import org.sonatype.nexus.repository.config.Configuration
import org.sonatype.nexus.repository.maven.LayoutPolicy
import org.sonatype.nexus.repository.maven.VersionPolicy


synchronized (this) {
    Map result = [created: [], deleted: []]
    def repoManager = repository.repositoryManager
    List existingRepoNames = repoManager.browse().collect { repo -> repo.name }
    String mvnProxyName = 'mvn-proxy'
    if (!existingRepoNames.contains(mvnProxyName)) {
        Configuration config = this.createProxyConfigWithoutNegativeCache(mvnProxyName, 'maven2-proxy', '<%= mvn_repository_url %>')
        config.attributes.maven = [versionPolicy: VersionPolicy.RELEASE, layoutPolicy: LayoutPolicy.STRICT]
        repository.createRepository(config)
        result.created.add(mvnProxyName)
    }
    String npmProxyName = 'npm-proxy'
    if (!existingRepoNames.contains(npmProxyName)) {
        Configuration config = this.createProxyConfigWithoutNegativeCache(npmProxyName, 'npm-proxy', '<%= npm_registry_url %>')
        repository.createRepository(config)
        result.created.add(npmProxyName)
    }
    List proxyRepos = [npmProxyName, mvnProxyName]
    List toDelete = existingRepoNames.findAll { !proxyRepos.contains(it) }
    for (def repoName : toDelete) {
        repoManager.delete(repoName)
        result.deleted.add(repoName)
    }

    if ('<%= http_proxy %>'?.trim()) {
        URL httpProxy = new URL('<%= http_proxy %>')
        println("httpProxy: ${httpProxy}")

        Map httpCredentials = extractCredentials(httpProxy)
        if(!httpCredentials) {
            core.httpProxy(httpProxy.host, httpProxy.port)
        }
        else {
            core.httpProxyWithBasicAuth(httpProxy.host, httpProxy.port, httpCredentials.username, httpCredentials.password)
        }
    }

    if ('<%= https_proxy %>'?.trim()) {
        URL httpsProxy = new URL('<%= https_proxy %>')
        println("httpsProxy: ${httpsProxy}")

        Map httpsCredentials = extractCredentials(httpsProxy)
        if(!httpsCredentials) {
            core.httpsProxy(httpsProxy.host, httpsProxy.port)
        }
        else {
            core.httpsProxyWithBasicAuth(httpsProxy.host, httpsProxy.port, httpsCredentials.username, httpsCredentials.password)
        }
    }

    if (('<%= http_proxy %>'?.trim() || '<%= https_proxy %>'?.trim()) && '<%= no_proxy %>'?.trim()) {
        String[] nonProxyHosts = '<%= no_proxy %>'.split(',')
        /* Insert wildcards to have a rough conversion between the unix-like no-proxy list and the java notation.
         * For example, `localhost,.corp,.maven.apache.org,x.y.,myhost` will be transformed to
         * `[*localhost,*.corp,*.maven.apache.org,*x.y,*myhost]`. */
                .collect { it.replaceFirst('\\.$', '')}
                .collect { "*${it}" }
        println("nonProxyHosts: ${nonProxyHosts.join(',')}")
        core.nonProxyHosts(nonProxyHosts)
    }

    return result
}

Map extractCredentials(URL proxyURL) {
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

private Configuration createProxyConfigWithoutNegativeCache(String name, String recipeName, String remoteUrl) {
    Configuration config = new Configuration(
        repositoryName: name,
        recipeName: recipeName,
        online: true,
        attributes: [
            httpclient: [
                connection: [
                    blocked: false,
                    autoBlock: true
                ]
            ],
            proxy: [
                remoteUrl: remoteUrl,
                // ToDo: consider setting those 2 values to -1. See https://support.sonatype.com/hc/en-us/articles/115010182627-Understanding-Caching-Configuration
                contentMaxAge: 1440,
                metadataMaxAge: 1440
            ],
            negativeCache: [
                enabled: false
            ],
            storage: [
                blobStoreName: BlobStoreManager.DEFAULT_BLOBSTORE_NAME,
                strictContentTypeValidation: true
            ]
        ]
    )
  return config
}
