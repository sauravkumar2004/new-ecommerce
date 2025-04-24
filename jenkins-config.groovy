import jenkins.model.*
import hudson.model.*
import hudson.tools.*
import jenkins.plugins.nodejs.tools.*
import jenkins.plugins.nodejs.tools.NodeJSInstallation

// Install required plugins
def plugins = [
    'docker-plugin',
    'docker-workflow',
    'nodejs',
    'pipeline',
    'git'
]

plugins.each { plugin ->
    if (!Jenkins.instance.pluginManager.plugins.collect { it.shortName }.contains(plugin)) {
        Jenkins.instance.updateCenter.getPlugin(plugin).deploy()
    }
}

// Configure Node.js
def nodeJS = new NodeJSInstallation(
    'NodeJS',
    '/usr/local/bin/node',
    [
        new NodeJSInstaller('16.17.0', '')
    ]
)

def descriptor = Jenkins.instance.getDescriptorByType(NodeJSInstallation.DescriptorImpl.class)
descriptor.setInstallations(nodeJS)
descriptor.save()

// Configure Docker
def dockerTool = new DockerTool(
    'Docker',
    '/usr/bin/docker',
    [
        new DockerToolInstaller('latest', '')
    ]
)

def dockerDescriptor = Jenkins.instance.getDescriptorByType(DockerTool.DescriptorImpl.class)
dockerDescriptor.setInstallations(dockerTool)
dockerDescriptor.save() 