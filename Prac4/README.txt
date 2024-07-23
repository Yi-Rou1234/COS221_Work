# COS221

In order to build the application, one needs to simply use the lib and src folders provided in the same folder. Use an IDE to compile the database.java file in the src folder.
To connect to the database, change the parameters at line 67 to correctly connect to the database, with the url being:

'jdbc:mysql://<server_name(localhost)>:<port>/<sakila_database_name>' changing the values in <> to the correct parameters. 

Then on the same connection object, change the username to the correct username (root for instance) and the password to the password that is used to connect to the sql server. After doing so and compiling, use the IDE to run the application and it should run correctly. 

Ideally, run the program in VSCode with the java extensions installed.
