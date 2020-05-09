![logo](/resources/logo.png)

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

Images are served using a custom lightweight server to allow access from multiple front-ends.

For images to load properly, please run the static image server in graBBT-imageServer first in a separate cmd window.

Please do not close or terminate the window while the application is running.

```
cd grabBBT-imageServer
npm i
node app.js

# listening on 3000
```

## (C) Java Web Client

Access through
`localhost:8080/graBBT-war/index.xhtml`

Default accounts
| Retailer | username | password |
|----------|----------|----------|
| KOI Thé | manager | password |
| Gong Cha | manager2 | password |
| LiHO Tea | manager3 | password |
| Playmade | manager4 | password |
| Admin | admin | password |

#### Important Note!

Images uploaded through Java web client will be uploaded to `alternatedocroot_1` directory as specified in web.xml. Ensure that this path points to the `images` directory within graBBT-imageserver so that uploaded images will be loaded properly.

## (D) Angular Client

### (1) Main Customer Client

Start the customer client with

```
cd graBBT-client
npm i
ng serve

# localhost:4200
```

Default accounts
| Name | username | password |
|------------|----------|----------|
| Customer 1 | customer | password |

### (2) Geolocation

The angular client also features geolocation via Geolocation API.
For geolocation to work, we must allow browser location permissions and access over `localhost`

If serving over LAN, use ng serve with https
`ng serve --ssl=true`
then access website through
`https://<local ip>:4200`

# Project Information

This project constitutes the group project component of the NUS module, IS3106.

For more information on the project, please refer to our .docx report.
