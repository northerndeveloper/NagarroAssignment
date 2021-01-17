# Statement Investigation Project

A sample Spring Boot Project was implemented for Nagarro Assignment.

It's a Spring Web MVC project used Thymeleaf in the User Interface and also the application is secured
by using spring-security.The DB is Microsoft Access(MSAccess) and  the db file is embedded inside the project.

The passwords are encoded with the Spring Security's encoding algorithm.

According to the assignment, accontsdb.accdb DB is given and embedded inside the project
under src/main/resources. UCanAccess is used to query the MsAccess db file.

For the DB Operations, Spring Data JPA is used and JPA's Query methods are chosed instead of using Hibernate's
Criteria Queries.

User is not able to view the statements without login to the system. Username and Passwords 
are already given in the assignment which are also encoded during the login process. 
Spring Security is used to maintain security.

Also authorization is established by giving testadmin user as ADMIN role and testuser user as USER role and limits the 
operations of user as it's mentioned in the assignment.

## Important Notice ##
The application is implemented in the UNIX systems.Therefore it's adviced to test the application in the UNIX systems 
such like Linux or MAC.

# How to start the application
As it's a Spring Boot Application, you can directly execute the application or execute the StatementInvestigationApplication.java.

After the application starts please type

'http:localhost:/8080'

You will be forwarded directly to the Login Page  and write the UserName / Passwords which are given in the assignment.
Functionality differs to the user as normal user has only limited search operations.

The session limit is 5 minutes . After 5 minutes if you click any button you will be automatically forwarded to the login page as it's
implemented inside the application.properties.

Account_ID is an obligation area according to the assignment.

Normal user is not able to type amount and date areas according to the assignment.

Both dates or Both Amounts should be filled to make a query. It means if you write from Date and do not write To Date you
will come across with errors.

For Validations, I would want to change the type of Database columns if I have a selection.

You can logout by clicking the logout button.

You can clear the input fields by clicking the clear button.



