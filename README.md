![logo](https://i.imgur.com/WtqFOwP.png)

# Notes

## Darrel

- Can't figure out how to selectively process only certain form fields (for create new listing in jsf)

- Super dodgy image upload for listing - the ui is weird cos gotta click upload separately, couldn't figure out how to upload together with submit button - files get uploaded to `C:/NetBeansProjects/proj/graBBT/graBBT-war/web/resources/image` so that it can be accessed through web resources (def not the right way, prolly need to consult on the best way to do it) - so either move your project folder accordingly OR adjust your `alternatedocroot_1` variable in `web.xml`
- Still figuring out categories (CAN WE EVEN REACH HERE)
- things to work on SORTING BY LOCATION? VIEW MAP?

## Chloe

### Retailer
- Retailer "my account page" - Include Profile details & simple overview details of outlets, listings and revenue - Each detail should be able to be clicked on --> redirect to the relevant page with their full details
- Want to build a retailer Notifications tab (when order comes in, when payment completes)

### Client
- test


# Angular Client

Set up with

```
cd graBBT-client
npm i
ng serve
```

hopefully it works
if using vscode, install Prettier for code formatiing

- Index, login and signup are three static pages
- Onced logged in, redirected to Main module which is another NgModule
- this allows for another routler outlet with child paths
- currently can view a list of outlets THATS ALL

# Static image server

Make sure to run static image server in graBBT-imageServer if not images won't load

```
cd grabBBT-imageServer
npm i
node app.js

# listening on 3000
```
