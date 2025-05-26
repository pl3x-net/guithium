---
title: Home
nav_order: 1
---

<div class="center" markdown="1">

![Guithium](https://cdn.modrinth.com/data/z2cqY1PE/images/bd4cce870d7950dd3af457e78c379e0c8c17ebc1.png)

# <b>Guithium Documentation</b>

[![][Build Badge]][Build Url]{:target="_blank"}
[![][Downloads Badge]][Modrinth Url]{:target="_blank"}
[![][Discord Badge]][Discord Url]{:target="_blank"}
<br>
[![][bStats Badge]][bStats Url]{:target="_blank"}
[![][MIT Badge]][License Url]{:target="_blank"}
[![][CodeFactor Badge]][CodeFactor Url]{:target="_blank"}

<big><b>Guithium allows [Paper](https://papermc.io/){:target="_blank"} plugins to add GUIs to [Fabric](https://fabricmc.net/){:target="_blank"}
clients.</b></big>

This documentation is a work in progress.<br/>
Many sections may be inaccurate or incomplete<br/>
while the project is in active development.

</div>

----

## Supported Platforms

Server side plugin:

![][Paper Logo] [Paper][Paper Url]<br>
![][Purpur Logo] [Purpur][Purpur Url]

Client side mod:

![][Fabric Logo] [Fabric][Fabric Url] (requires [Fabric API][Fabric API Url])

----

* Downloads: [https://modrinth.com/project/guithium/](https://modrinth.com/project/guithium/)
* Maven Repo: [https://repo.pl3x.net/#/public/net/pl3x/guithium/guithium-api/](https://repo.pl3x.net/#/public/net/pl3x/guithium/guithium-api/)
* Source Code: [https://github.com/pl3x-net/guithium/](https://github.com/pl3x-net/guithium/)
* Javadoc: [https://jd.pl3x.net/net/pl3x/guithium/guithium-api/latest/](https://jd.pl3x.net/net/pl3x/guithium/guithium-api/latest/)

----

## bStats

[![bStats Graph Data](https://bstats.org/signatures/bukkit/Guithium.svg)](https://bstats.org/plugin/bukkit/Guithium/25813)

[Build Url]: https://github.com/pl3x-net/guithium/actions
[Modrinth Url]: https://modrinth.com/plugin/guithium/
[Discord Url]: https://pl3x.net/discord
[bStats Url]: https://bstats.org/plugin/bukkit/Guithium/25813
[CodeFactor Url]: https://www.codefactor.io/repository/github/pl3x-net/guithium
[Paper Url]:https://papermc.io/
[Purpur Url]: https://purpurmc.org/
[Fabric Url]: https://fabricmc.net/
[Fabric API Url]: https://modrinth.com/mod/fabric-api
[GuithiumExample Url]: https://github.com/pl3x-net/GuithiumExample
[JDK 21 Url]: https://www.oracle.com/java/technologies/downloads/#java21
[GitHub Url]: https://github.com/pl3x-net/guithium
[License Url]: https://github.com/pl3x-net/Guithium/blob/master/LICENSE

[Build Badge]: https://img.shields.io/github/actions/workflow/status/pl3x-net/guithium/builds.yml?event=push&logo=travis
[Downloads Badge]: https://img.shields.io/modrinth/dt/guithium?logo=data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAABhWlDQ1BJQ0MgcHJvZmlsZQAAKJF9kT1Iw0AcxV9bxSIVh1YQcYhQnSyIiuimVShChVArtOpgcukXNGlIUlwcBdeCgx+LVQcXZ10dXAVB8APE0clJ0UVK/F9SaBHjwXE/3t173L0D/PUyU82OMUDVLCOViAuZ7KrQ9YogwujDEGYkZupzopiE5/i6h4+vdzGe5X3uz9Gj5EwG+ATiWaYbFvEG8dSmpXPeJ46woqQQnxOPGnRB4keuyy6/cS447OeZESOdmieOEAuFNpbbmBUNlXiSOKqoGuX7My4rnLc4q+Uqa96TvzCU01aWuU5zEAksYgkiBMioooQyLMRo1UgxkaL9uId/wPGL5JLJVQIjxwIqUCE5fvA/+N2tmZ8Yd5NCcaDzxbY/hoGuXaBRs+3vY9tunACBZ+BKa/krdWD6k/RaS4seAb3bwMV1S5P3gMsdoP9JlwzJkQI0/fk88H5G35QFwrdA95rbW3Mfpw9AmrpK3gAHh8BIgbLXPd4dbO/t3zPN/n4Ax9dyyerighsAAAAGYktHRAAAAAAAAPlDu38AAAAJcEhZcwAADdcAAA3XAUIom3gAAAAHdElNRQfmCBMVIw496dpyAAACm0lEQVQ4y2XTTYjVdRTG8c/vd//33sQJZyEVrYZeNJ1BSgKTsVbekRRbZVSrIqHwNUxtFVkboXyBQnORK6kICXQxSaOG0oSQiJAz4xuaUWvvCFdr7p37Py0Uszyrs3j4HnjO8yT/n4mhATlWS5YKfSC5JhxTpi/NHxm7V57ubuOrairN3aR30BRpWI4rIDyB5eiVfGG69z39B9v/AsZX1VQmj+AF4UM9rd2m63XtYsBUfdyC4aY/Fs9wc+Ym0jac1O1drv9gu4A7l5+nXInTWg/uk+I1VNWnHnexscUtj5rOm1TLs8JhxY0d2JBNDA2Q3ha2mS5+kfKoFK8I26W8yJxjv+Gq8KqiHDVVO0X6SMQa48v6sxyrMamntVu13IU+kZaoxS5RLnRp6T5FHBRpEI+ptXeK+Ik0pVJ+loUGvjddrwuvC58oKmM66RD2CrM0m7fMGzkj4lvhTckJoi48k1xovKRMV+WYhVEpL6KcLQyTVntqZP/tT6yqOD+5TDZMfpfubGzM9+UgpwqpDzeleNr5xpALjbMuTu4krt+GxWkpdSAL2+V4XzXGiI6y+52IPZJzwjrJD5IeRdopW4G2ojKmdBOXs+QoVujkvaSqiEfktNHco4tFvKxMQ2LGQtPxMGkLDmj3/K3s/Vyr+Vwy1hhUGJXiL/LHyvINKfURnyoNKyuXFOU8HBeueKAzqF3dIKzTrs3NBo7+jBGRqsSvOvXF+Jq0VU6nFOVWZfdP4iud2hKdYlD4AN9YMNy8bWK3dyVOCofVOmvNbK1VjYewRDcfMP/H37VmrFdvr1emQyKd0Lq++f4yFTd2iFiDSRyRXL7j+hzSi5iFPVrXN3v2TOe/gLugZf1y9y0pNbhTZ64JI4pyvyePT9wr/wdL6hgY+Xe2UgAAAABJRU5ErkJggg==
[Discord Badge]: https://img.shields.io/discord/171810209197457408.svg?label=&logo=discord&logoColor=ffffff&color=7389D8&labelColor=6A7EC2
[bStats Badge]: https://img.shields.io/bstats/servers/25813
[MIT Badge]: https://img.shields.io/github/license/pl3x-net/guithium?&logo=github
[CodeFactor Badge]: https://img.shields.io/codefactor/grade/github/pl3x-net/guithium?logo=data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8%2F9hAAABhWlDQ1BJQ0MgcHJvZmlsZQAAKJF9kT1Iw0AcxV9bxSIVh1YQcYhQnSyIiuimVShChVArtOpgcukXNGlIUlwcBdeCgx%2BLVQcXZ10dXAVB8APE0clJ0UVK%2FF9SaBHjwXE%2F3t173L0D%2FPUyU82OMUDVLCOViAuZ7KrQ9YogwujDEGYkZupzopiE5%2Fi6h4%2BvdzGe5X3uz9Gj5EwG%2BATiWaYbFvEG8dSmpXPeJ46woqQQnxOPGnRB4keuyy6%2FcS447OeZESOdmieOEAuFNpbbmBUNlXiSOKqoGuX7My4rnLc4q%2BUqa96TvzCU01aWuU5zEAksYgkiBMioooQyLMRo1UgxkaL9uId%2FwPGL5JLJVQIjxwIqUCE5fvA%2F%2BN2tmZ8Yd5NCcaDzxbY%2FhoGuXaBRs%2B3vY9tunACBZ%2BBKa%2FkrdWD6k%2FRaS4seAb3bwMV1S5P3gMsdoP9JlwzJkQI0%2Ffk88H5G35QFwrdA95rbW3Mfpw9AmrpK3gAHh8BIgbLXPd4dbO%2Ft3zPN%2Fn4Ax9dyyerighsAAAAGYktHRAAAAAAAAPlDu38AAAAJcEhZcwAADdcAAA3XAUIom3gAAAAHdElNRQfmCBMVKAA5pS6%2BAAABlElEQVQ4y82PP2gVQRDGf7N3t%2Bvdixpi0N5OELFKJ1iohBciKlgYJLX6YkBbC0sVooVFBAvBPw%2BFZzrJs7DR2iYHRhBsxNI8VLwUx92MRXJGxKCp9AfL7DfDfPutFO3z5wy5DuRlWU2OvLj7hduLYXh0ZSEkOh4SjUKiBK%2BEZP34Gu%2FtbebLE86Qa8BO4FDwyWmAbPjzMWACiNgEMdun6macwfJ6z2qxZYBI6ndAxR%2BRN%2FL1ZGeXlDqFkm%2Fv33nZjHZ0u2OZrw%2F7pBYf16Re8UEJ8VpNE33fP3BxgX%2BOFOOdtjmuGpoPtT51pNcrMZORx4%2FmslQnslAlWahItymZrz%2Bmqc4%2B2z%2B71BjE5uwesEeQsaLY%2FQp42LrfPUqwy2DNO03ZK9hN4Ehj4IDBjzjKCoC5aMDG9q%2BhBz%2BrWCN3KqptBtG89Xx%2BEWB1%2Bszr8OTBFMgkSLKWQAA%2BVCU3%2BK%2BQb%2B0LB4FLGHmrP39LNv3773Ei9IBphLnVduf4VhM4M9JGqGzc%2F5bYnDsrqlcQloaK0adbNfgOUn6NRlZZ46YAAAAASUVORK5CYII%3D
[Paper Logo]: /guithium/assets/images/paper.png
[Purpur Logo]: /guithium/assets/images/purpur.png
[Fabric Logo]: /guithium/assets/images/fabric.png
[Modrinth Button]: https://i.imgur.com/5C4fVJC.png
