# Drawing tool
The application is to draw picture based on information about canvas, line, rectangle, and bucket in the data/input.txt. Based on such information, the application will produce corresponding canvas, lines, etc and export such result to the data/output.txt

## Prerequisites
- java 8
- Gradle >= 4.0

## Download source code from Github

## Build project
Go to the source code folder, and execute the following command to build project. This step will generate ```build/libs/drawingtool-1.0-SNAPSHOT.jar``` file which can be used to run for the application
```
chmod +x gradlew
```
```
./gradlew build
```

## Run application 
Make sure the input.txt is in the data source folder
```
java -jar build\libs\drawingtool-1.0-SNAPSHOT.jar data/input.txt data/output.txt
```

## Verify result
The application will produce the output.txt in the data source folder

