#!/bin/bash

export SPRING_PROFILES_ACTIVE=central
export SPRING_DATASOURCE_URL=jdbc:mysql://mysql-ait.stud.idi.ntnu.no:3306/jonatanv
export SPRING_DATASOURCE_USERNAME=jonatanv
export SPRING_DATASOURCE_PASSWORD=MnUUreHOz

java -jar Backend/ECOMBack/target/ECOMBack-0.0.1-SNAPSHOT.jar
