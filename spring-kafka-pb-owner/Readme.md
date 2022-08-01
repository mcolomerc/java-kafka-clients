# Schema Owner 

> schema-registry:download

Used to pull down schemas from a Schema Registry server. This plugin is used to download schemas for the requested subjects and write them to a folder on the local file system

> schema-registry:set-compatibility

This goal is used to update the configuration of a subject or at a global level directly from the plugin. This enables you to change the compatibility levels with evolving schemas, and centralize your subject and schema management.

> schema-registry:test-local-compatibility

Tests compatibility of a local schema with other existing local schemas during development and testing phases.

> schema-registry:test-compatibility

Read schemas from the local file system and test them for compatibility against the Schema Registry server(s).

> schema-registry:validate

Read schemas from the local file system and validate them locally, before registering them

> schema-registry:register

Read schemas from the local file system and register them on the target Schema Registry server

> mvn validate

The validate step would include:

* mvn schema-registry:validate@validate
* mvn schema-registry:test-local-compatibility@test-local
* mvn schema-registry:set-compatibility@set-compatibility
* mvn schema-registry:test-compatibility@test-compatibility
 
## Register the Schema 

```sh
mvn schema-registry:register  
```

## All in one 

```sh
mvn exec:exec
```
