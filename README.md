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

Set up with

```
cd graBBT-client
npm i
ng serve
```

hopefully it works
if using vscode, install Prettier for code formatting

- Index, login and signup are three static pages
- Onced logged in, redirected to Main module which is another NgModule
- this allows for another routler outlet with child paths
- currently can view a list of outlets THATS ALL

## Static image server

Make sure to run static image server in graBBT-imageServer for images to load

```
cd grabBBT-imageServer
npm i
node app.js

# listening on 3000
```

## Others

### Geolocation

For geolocation to work, must allow location and use `localhost`

If serving over LAN, use ng serve with https
`ng serve --ssl=true`
then access website through
`https://<local ip>:4200`
