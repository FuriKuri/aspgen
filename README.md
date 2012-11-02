## Welcome to AspGen

## Installation
### Step 1: Unzip the archive to the directory you wish. For example in Windows C:\Program Files\aspgen or in Linux /usr/local/aspgen.
### Step 2: Add the ASPGEN_HOME enviroment variable with the directory you choose the step before.
### Step 3: Add ASPGEN_HOME/bin to the PATH environment variable.

## Gettings Started
### Step 1: Open your java project in your favorite IDE.
### Step 2: Add all jar files in ASPGEN_HOME/generators and ASPGEN_HOME/exlibs to the classpath.
### Step 3: Start the AspGen generator system with in the project directory with

```
  D:\workspace\my-java-project [master]> aspgen
```
### Step 4: Add a generator annotation (i.e @ToString) to a java class.

```
@ToString
public class Person {
	private String name;
	private int age;
}
```
### Step 5: After saving the java file aspgen will generate an aspectJ file with the ToString method.