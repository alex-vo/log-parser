Job Interview test for wallethub.com

This is a solution for coding assignment from Wallethub , which sufficed to advance to the next job interview round.

https://wallethub.com/


https://stackoverflow.com/jobs/companies/wallethub

This program assumes a running MySQL instance at localhost:13306.
Database settings can be changed in hibernate.properties file.

To set up a local MySQL instance inside docker, following can be run:

```
docker pull mysql
docker run --name mysql -p 13306:3306 -e MYSQL_ROOT_PASSWORD=123 mysql
docker exec -it mysql bash
root@blabla:/# mysql -u root -p
mysql> create database test_database;
mysql> create user test_user identified by '123';
mysql> grant all privileges on test_database.* to test_user@'%';
```

To run the program:

```
./gradlew clean jar
java -cp "./build/libs/parser.jar" com.ef.Parser --accesslog=/path/to/access.log --startDate=2017-01-01.13:00:00 --duration=hourly --threshold=100
```
