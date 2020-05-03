![logo](https://i.imgur.com/WtqFOwP.png)

# Team Information

### Group number: GP12 
### Team members:
- Bryan Jude Toh Wei Han, A0190282H
- Chloe Tan Xin Ru, A0187980L
- Darrel Hong Cheng Kit, A0184367U
- Ho Jun Cong Keith, A0184106L 

# Setup Instructions

## (A) JavaEE Backend

Firstly, create a database on netbeans with the name 'grabbt'

Clean all project files and then deploy the enterprise application 'graBBT'

## (B) Static Image Server

For images on the Angular Client to load, please run the static image server in graBBT-imageServer first in a separate cmd window.

Please do not close or terminate the window while the application is running.

```
cd grabBBT-imageServer
npm i
node app.js

# listening on 3000
```

## (C) Angular Client

### (1) Main Customer Client

Start the customer client with

```
cd graBBT-client
npm i
ng serve
```

### (2) Geolocation

The angular client also features geolocation via Google Maps API. 
For geolocation to work, we must allow location and use `localhost`

If serving over LAN, use ng serve with https
`ng serve --ssl=true`
then access website through
`https://<local ip>:4200`


# Project Information
This project constitutes the group project component of the NUS module, IS3106.

For more information on the project, please refer to our .docx report.

