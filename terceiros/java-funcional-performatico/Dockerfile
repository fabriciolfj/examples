FROM openjdk:14
RUN mkdir /tmp/java-funcional-performatico
ADD . /tmp/java-funcional-performatico
RUN chmod +x /tmp/java-funcional-performatico/gradlew
WORKDIR /tmp/java-funcional-performatico
RUN ls -lsah
RUN ./gradlew clean build
RUN mv /tmp/java-funcional-performatico/build/libs/*.jar /tmp/app.jar

FROM openjdk:14
COPY --from=0 /tmp/app.jar /tmp
RUN ls /tmp
ENV _JAVA_OPTIONS -XX:+UseSerialGC -XX:+UseStringDeduplication -XX:MaxMetaspaceFreeRatio=80 -XX:MetaspaceSize=100M -XX:+PrintFlagsFinal -XX:+UseContainerSupport -XX:InitialRAMPercentage=30 -XX:MaxRAMPercentage=90 -Xmx256m
ENTRYPOINT ["java", "-jar", "/tmp/app.jar"]
EXPOSE 8080

