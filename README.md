# Liferay GSearch Elasticsearch Adapter

This is a customized Liferay Elasticsearch Adapter for the GSearch project. For details please see the GSearch project page at https://github.com/peerkar/liferay-gsearch.

# How To Build a Custom Elasticsearch Adapter for Liferay

## Requirements

 * blade cli 

## Instruction for Linux Environment

 1. Create a working folder
 2. Go to working folder and run "blade init WORKSPACE_NAME" for example `blade init elasticsearch-adapter-2.1.3-patched`
 3. Go to workspace folder and clone com-liferay-search from git: `git clone https://github.com/liferay/com-liferay-portal-search.git`
 4. Go to clone folder and switch to the commit version of your preference. Find out the commit id you want to checkout and run for example 
`git fetch origin 226300aa249dbe147303a15b77099396575124a3` and `git reset --hard FETCH_HEAD`
 5. From the local clone, remove all the other project than com-portal-search-elasticsearch
 6. Modify com-portal-search-elasticsearch build.gradle file (see below) for compiling to succeed. Thanks to Sampsa Sohlman for this:
 7. Run ../../../gradlew clean build
 
 That's it.
 
 
##  Additions to build.gradle

```
if ( !System.getenv().containsKey("GITHUB_TOKEN") ) {
        gradle.taskGraph.whenReady { graph ->

                        def disabledTasks = [
                                "findbugsMain"  ,
                                "compileTestIntegrationJava",
                                "findbugsTestIntegration",
                                "unzipJar",
                                "generateJSPJava",
                                "writeFindBugsProject",
                                "compileTestJava",
                                "findSecurityBugs",
                                "findbugsTest"
                        ]

            graph.allTasks.findAll {
                println "Name : " + it.name
                
                if (disabledTasks.contains(it.name)) {
                        println "Disable : " + it.name
                        it.enabled=false
                }
                else {
                                        println "Enabled : " + it.name
                                }       
            }
        }
}
else {
        apply from: "build-ext.gradle"
}
```
