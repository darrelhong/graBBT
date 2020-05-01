![logo](https://i.imgur.com/WtqFOwP.png)

# Team Information

### Group number: 
### Team members:
- Bryan Toh 
- Chloe Tan Xin Ru, A0187980L
- Darrel Hong 
- Keith Ho 

# Setup Instructions

## Backend

Firstly, create a database on netbeans with the name 'grabbt'

Clean all project files and then deploy the enterprise application 'graBBT'

## Angular Client

### Main Client

Set up with

```
cd graBBT-client
npm i
ng serve
```

### Geolocation

The angular client also features geolocation via Google Maps API. For geolocation it to work, we must allow location and use `localhost`

If serving over LAN, use ng serve with https
`ng serve --ssl=true`
then access website through
`https://<local ip>:4200`

## Static image server

For images to load, please run the static image server in graBBT-imageServer

```
cd grabBBT-imageServer
npm i
node app.js

# listening on 3000
```

## Others


