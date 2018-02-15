# Liferay GSearch Elasticsearch Adapter

This is a customized Liferay Elasticsearch Adapter for the GSearch project. For details please see the GSearch project page at https://github.com/peerkar/liferay-gsearch.

Please see repo tags for specific adapter base versions.

# How To Build a Custom Elasticsearch Adapter for Liferay

## Requirements

 * blade cli 

## Instruction for Linux Environment

 1. Create a working folder
 1. Go to working folder and run "blade init WORKSPACE_NAME" for example `blade init elasticsearch-adapter-2.1.3-patched`
 1. Go to workspace folder and clone com-liferay-search from git: `git clone https://github.com/liferay/com-liferay-portal-search.git`
 1. Find out the hash (commit id) for the adapter version you want to have by browsing com-liferay-elasticsearch/bnd.bnd history
 1. Go to clone folder and switch to the commit version, for example 
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
## Files & Classes Customized or Added Compared to Default Adapter

### Modified

* build.gradle (To make the project to compile)
* bnd.bnd (Naming change for GSearch)
* CompanyIndexFactory.java (To add custom QuerySuggestion mapping)
* ElasticSearchQueryTranslator.java (To use custom stringQuery translator)
* resources/META-INF/index-settings.json (GSsearch analysis settings)
* resources/META-INF/mappings/liferay-type-mapping.json (Customized field settings for content,description,subtitle and title to better support non standard ASCII languages...)

### Added
* all classes in fi.soveltia* package
* resources/META-INF/mappings/gsearch-querysuggestion-mapping.json (GSsearch querySuggestion mapping)


Modified files have been made a backup copy with syntax original_file_name.original


