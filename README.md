# LibDemo

# build command

## MobilePlatform

```
gradlew clean build generatePomFileForMavenPublication publishMavenPublicationToMavenLocal bintrayUpload -PbintrayUser=lixiaohui -PbintrayKey=xxx -PdryRun=false

```


## Biz-Common

```
gradlew :amp:biz-common:clean :amp:biz-common:build :amp:biz-common:javadocJar :amp:biz-common:sourcesJar :amp:biz-common:install :amp:biz-common:bintrayUpload

```

