#/bin/bash
echo 'Adicionando module java.efp.module.main'
/home/cleber/programs/jdk-14.0.2/bin/java --module-path ../java-efp-module/build/libs/ --add-modules java.efp.module.main -jar  build/libs/java-funcional-performatico-0.0.4-SNAPSHOT.jar
