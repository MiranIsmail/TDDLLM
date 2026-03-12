# Welcome!

Thank you for taking part in this experiment! The goal of this study is to explore how different development approaches affect the outcome of LLM-generated code.

You are part of **Group B**. Your task is to build a time-logging web application with authentication, role-based access, and admin functionality. You'll be prompting the LLM to implement the features directly. You can take inspiration from the provided LLM guidelines on how to structure your prompts and interact with the model. Just make sure all the described functionality ends up implemented.

Everything you need to get started is described below. Please read through the user stories and instructions carefully before you begin. Good luck, and have fun!

# Your Task
Your task is to implement as many of the User stories below using the following process. These tasks should be completed within 150 minutes; this is a hard limit.

You are to implement the missing functionalities described above using standard LLM prompting. This means that you should not write any code yourself, but instead generate everything. You are allowed to manually make small changes, such as fixing variable names or adjusting the orientation of the code, but the main functions must be generated.
## Provided resources
- **llm.miranis.cc**: This is the website provided to you to use Claude opus 4-6. Your credentials have been sent to you by email.

  **NOTE! The LLM has context about the repository already, you do not need to explain the structure and files to it**
- **The GitHub repository**: This is where the experiment code is located. **PROVIDE LINK HERE**
- **Guideline PDF**: This document contains a detailed description of the process, covering common prompting practices and strategies to maximize the effectiveness of your prompts.
## The process
- You are to follow the guidelines to generate code that implements the user stories
- You should not write your own code, but minor bug fixes or changes is allowed. You can think that if it takes longer time to prompt the fix you can do it by hand

## Todo list:
This is our suggestion for the implementation order. If you have downloaded the repo as a zip file don't forget to initiate it as a git repo:
Stories are marked with the following priorities and should be completed in this order:

- <span style="color:red;">High</span>
- <span style="color:orange;">Medium</span>
- <span style="color:green;">Low</span>


-[ ] Put an "x" in the box to the left and commit with this message "Start".
-[ ] Read these md files in order (README.md,START_HERE.md).
-[ ] Quickly look at the repository structure and test run the app.java.
-[ ] Implement the high priority functionalities.
-[ ] Implement the medium priority functionalities.
-[ ] Implement the low priority functionalities.
-[ ] Fill out the Survey.md
-[ ] Put an "x" in the box to the left and commit with this message "Stop".
-[ ] Either make a new repository on Github and share it with us (MiranIsmail and FelixAnderssonBTH), or use `Git bundle` and send us the file to this email tdd-xjob@googlegroups.com .
-[ ] You are done, thank you very much for contributing.
---
# Goals
## G1 - Secure authentication and Role-Based Access Control.
## G2 - Accurate recording of work hours with data integrity.
## G3 - Administrative governance.

# Potential User
## E1 - As a Potential User i can register so that i can login
### E1S1 - As a Potential User i can go to the websites register page and register as a user using a unique username and a password (<span style="color:red;">High</span>)
### E1S2 - As a Potential User i can not access other pages of the website except for the register and login page (<span style="color:red;">High</span>)


# User Story
## E2 - As a User i can login into the webside so that i can see my log time and log new time as well as logout.
### E2S1 - As a User i can go to the login page and login with my unique Username and my password which was registered beforehand (<span style="color:red;">High</span>)
### E2S2 - As a User i can go to the register page but can not register using my existing Username. (<span style="color:red;">High</span>)
### E2S3 - As a User i can go to the User page and view my existing time logs. (<span style="color:orange;">Medium</span>)
### E2S4 - As a User i can go to the User page and log new timelogs. (<span style="color:orange;">Medium</span>)
### E2S5 - As a User i can not edit my time logs. (<span style="color:green;">Low</span>)
### E2S6 - As a signed in User i can ONLY access the User page and not other pages such as admin, login and register. (<span style="color:red;">High</span>)
### E2S7 - As a signed in User i can logout. (<span style="color:red;">High</span>)
### E2S8 - As a signed in User i am not allowed to work more than 5 hours without a break 30 minutes break. (<span style="color:green;">Low</span>)

# Admin Story
## E3 - As an Admin I can log in to the website so that i can administrate users logs and their pay check, and i can also see my log time and log new time as well as logout. 
### E3S1 - As an Admin i can access the login page and log in using my unique username and password. (<span style="color:red;">High</span>)
### E2S2 - As an Admin i can go to the register page but can not register using my existing Username. (<span style="color:red;">High</span>)
### E3S3 - As an Admin i can go to the User page and view my existing time logs. (<span style="color:green;">Low</span>)
### E3S4 - As an Admin i can go to the User page and log new timelogs. (<span style="color:green;">Low</span>)
### E3S5 - As a Admin i can edit every users time logs in the admin page. (<span style="color:green;">Low</span>)
### E3S6 - As a signed in Admin i can ONLY access the User page / Admin page and not other pages such as login and register. (<span style="color:orange;">Medium</span>)
### E3S7 - As a signed in Admin i can logout. (<span style="color:red;">High</span>)
### E3S8 - As an Admin i can access the admin page and view every Users time logs. (<span style="color:orange;">Medium</span>)
### E3S9 - As an Admin i can access the admin page and elevate an existing User to Admin level. (<span style="color:red;">High</span>)
### E3S10 - As an Admin i can assign Users hourly wage (<span style="color:orange;">Medium</span>)
### E3S11 - As an Admin i can view Users total paycheck but also their daily paycheck (<span style="color:green;">Low</span>)
### E3S12 - As an Admin i can issue payout for a user, the payout is for total accumulated hours.(<span style="color:orange;">Medium</span>)

---


# Authentication
### Requirement: Users must authenticate to reach protected routes.
#### Role-Based Access Control Logic:
- Potential User(Not logged in): Access to `/login`, `/register`. Redirected to `/user` if already logged in.
- USER(Logged in): Access to `/user`, `/logout`. Unauthorized for `/admin`. Redirect away from `/login` and `/register`.
- ADMIN(Logged in): Access to `/user`, `/admin`, `/logout`. Redirect away from `/login` and `/register`.

# Application structure
## `src/main/java/com.expermint/`
This is where the code is located, and it is further divided into these packages:
### `/controller`
All files inside the controller handles the API calls, this is where you can implement the logic for the new API calls and edit them.
At the moment there are some boilerplate data inside these files just so that we can showcase it in the frontend. You can remove those if you want.
### `/model`
This is where the base classes (Object classes) are located. They have everything necessary in them but if you want you can modify them.
There are two helper functions inside the User class (stringifyUser,calculateTotalLoggedTime) that are there to make sure that the frontend gets the correct JSON structure. try to not modify these or the fron-end might brake.
### `/repository`
This is where the database managers are located, here you can for example write a function to add a User and so on.
It is the middleman between the API and the Sqlite database.
### `/service`
Business logic lives here, sitting between controllers and repositories.
### `App.java`
This is where the Application and classes are initialized. The routes are defined here as well, you can modify as you like.
### `Database.java`
The database have every necessary table and variables but if you want to modify it, go ahead.
## `src/main/java/resources/`
The HTLM files are located here, they should not be modified, but you can if you really want to.
## `src/test/java/com.expermint/`
The tests are located here, there is some boilerplate to get you started. You can modify as you please.

---



