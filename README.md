# Mine-GitHub-Repository

INTRODUCTION
------------
Thank you for downloading the Mine-GitHub-Repository program. This program analyses all the commits in a repository and finds commits that have added a parameter to an existing method. The program takes in the name of a github repository and report the number of change method signature in each commit in a csv file.

REQUIREMENTS
------------
JDK 1.6 

CONTENTS
--------
This sample zip contains:

    /src - source code of the program and other libraries that is used.
    
    /src/refrences - jar files of the refdiff-core-0.1.1 library and all of its dependencies.
    
    /src/bin - contains class files.
    
    /.classpath & .project - project files for eclipse
    
PRE-REQUISITES
--------------
The following is a pre-requisite to successfully run the sample code:

    Make sure that Windows can find the Java compiler and interpreter:
    
        1.Select Start -> Computer -> System Properties -> Advanced system settings -> Environment Variables -> System variables ->                 PATH.
        2.Prepend C:\Program Files\Java\jdk1.6.0_27\bin; to the beginning of the PATH variable.
        3.Click OK three times.
        
COMPILING THE PROGRAM
-------------------
1. Unzip the files contained in the Mine-GitHub-Repository-master.zip file to a folder on you hard drive.
2. Open command prompt.
3. From the Command Prompt, navigate to the directory containing .java files, by typing the cd command as below:

        cd …………….. /MineGithubRepository/src
4. Type the javac command in command prompt as below to compile the program:

        javac –cp ".;./refrences/*" MineGithubRepo/MineGithubRepo.java
   
RUNNING THE PROGRAM
-------------------
Type the java command in command prompt as below to run the program:
  - Run with one input parameter:
  
        java –cp ".;./refrences/*" MineGithubRepo/MineGithubRepo input1(path to create local repository)
        
        Example:
        java –cp ".;./refrences/*" MineGithubRepo/MineGithubRepo g:\tmp
  - Run with two input parameters:
  
        java –cp ".;./refrences/*" MineGithubRepo/MineGithubRepo input1(path to create local repository) input2(path and name of the output csv file)
        
        Example:
        java –cp ".;./refrences/*" MineGithubRepo/MineGithubRepo g:\tmp g:\out.csv
  - Run with three input parameters:
  
        java –cp ".;./refrences/*" MineGithubRepo/MineGithubRepo input1(path to create local repository) input2(path and name of the output csv file) input3(repository name)
        
        Example:
        java –cp ".;./refrences/*" MineGithubRepo/MineGithubRepo g:\tmp g:\out.csv abahgat/suffixtree
        
If input 2 and 3 are not specified the default values are as follows:

Input 2: projectDirectory/src/out.csv

Input3: saramsh/common-token-subsequences
