#/bin/bash
export JAVA_HOME=/home/cleber/programs/jdk-14.0.2

keytool -genkeypair -alias tomcat -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore keystore.p12 -validity 3650 -storepass password

openssl req -x509 -newkey rsa:4096 -keyout key.pem -out cert.pem -days 365
openssl pkcs12 -export -in cert.pem -inkey key.pem -out cert.p12
keytool -importkeystore -srckeystore cert.p12 \
        -srcstoretype PKCS12 \
        -srcalias 1 \
        -destkeystore keystore.jks \
        -deststoretype pkcs12
        -deststoretype JKS

keytool -list -v -keystore keystore.jks

keytool -certreq -alias 1 -keystore keystore.jks -file coffeeandit.csr

keytool -genkey -alias 2 -keyalg RSA -keystore keystore.jks  -keysize 2048

keytool -list -v -keystore keystore.jks

keytool -printcert -v -file cert.pem

keytool -list -v -keystore keystore.jks -alias 1

keytool -delete -alias 2 -keystore keystore.jks

keytool -storepasswd -new java124 -keystore keystore.jks

keytool -export -alias 1 -file coffeeandit.crt -keystore keystore.jks

keytool -list -v -keystore $JAVA_HOME/lib/security/cacerts

keytool -import -trustcacerts -file /home/cleber/IdeaProjects/java-funcional-performatico/security/cert.pem -alias CA_ALIAS -keystore $JAVA_HOME/lib/security/cacerts

#changeit

keytool -export -keystore keystore.jks -alias tomcat -file myCertificate.crt
