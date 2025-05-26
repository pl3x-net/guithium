---
title: Developers
parent: Getting Started
nav_order: 2
---

## Developers

Javadoc: https://jd.pl3x.net/net/pl3x/guithium/guithium-api/latest/

Repository (Gradle)

```groovy
repositories {
    maven { url = 'https://repo.pl3x.net/public/' }
}

dependencies {
    compileOnly 'net.pl3x.guithium:guithium-api:0.0.1-SNAPSHOT'
}
```

Repository (Maven)

```xml

<project>
    <repository>
        <id>pl3x-repo</id>
        <url>https://repo.pl3x.net/public/</url>
    </repository>
    <dependency>
        <groupId>net.pl3x.guithium</groupId>
        <artifactId>guithium-api</artifactId>
        <version>0.0.1-SNAPSHOT</version>
        <scope>provided</scope>
    </dependency>
</project>
```
