FROM openjdk:26-jdk
ADD target/maqoor-webhook-processor-0.0.1.jar maqoor-webhook-processor.jar
ENTRYPOINT ["java","-jar","maqoor-webhook-processor.jar"]