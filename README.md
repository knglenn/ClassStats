# ClassStats
Calculate and display various statistics about the grades of a class.  


Usage: java ClassStats `<file to load>`  
  Once the file is loaded, possible commands are -  
  * `exit` - exits the program  
  * `help` - prints a list of commands  
  * `roll` - prints out a list of students from the class, along with total points and final
grades for each student  
  * `search [partial name]` - searches for students with a first or last name that matches the partial name  
  * `assignment` - prints out a list of assignments from the file, along with how many points are possible for each
assignment  
* `student [student name]` - prints a report for the specified student
* `assignment [assignment name]` - Prints a report about a particular assignment 
* `report` - prints a report about overall grades in the class  
  
The CSV file to be loaded must be formatted in the following manner: 
  - The first line must contain the name of the class followed by the section number, followed by the names of all of the
assignments in the class, separated by commas.  
Example: `Philosophy 101,Section 1,essay 1,test 1,essay 2,test 2,final`  
  - The second line must have the fields "firstName" and
"lastName", followed by the number of points possible for each assignment.  
Example: `firstName,lastName,20,50,20,50,100`
  
