### Command list
**Create directory in the HDFS**
```
hadoop fs -mkdir /user
hadoop fs -mkdir /user/root
hadoop fs -mkdir /user/root/{YOUR_FOLDER_NAME}
```

**Upload file into the directory**
```
hadoop fs -put /root/wordcount.txt /user/root/wordcount_data
```

**Export Hadoop clas path**
```
export HADOOP_CLASSPATH=$(hadoop classpath)
```

**Compile the JAVA file into classes**
```
javac -classpath ${HADOOP_CLASSPATH} -d tutorial_classes WordCount.java
```

**Build classes file into JAR**
```
jar -cvf firstTutorial.jar -C tutorial_classes/ .
```

**Run JAR file with input and output file**
```
hadoop jar firstTutorial.jar org.myorg.WordCount /user/root/wordcount_data /user/root/wordcount_output
```

**Check the result**
```
hadoop dfs -cat /user/root/wordcount_output
```

### TODO
```
1. Volume folder and files for easier development
```