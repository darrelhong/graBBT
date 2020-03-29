![logo](https://i.imgur.com/WtqFOwP.png)

# Notes

## Darrel

- Can't figure out how to selectively process only certain form fields (for create new lisiting)

- Super dodgy image upload for listing - the ui is weird cos gotta click upload separately, couldn't figure out how to upload together with submit button - files get uploaded to `C:/NetBeansProjects/proj/graBBT/graBBT-war/web/resources/image` so that it can be accessed through web resources (def not the right way, prolly need to consult on the best way to do it) - so either move your project folder accordingly OR adjust your `alternatedocroot_1` variable in `web.xml`
- Still figuring out categories

## Chloe

- Create new outlet to allow panel/list view
- Retailer "my account page" - Include Profile details & simple overview details of outlets, listings and revenue - Each detail should be able to be clicked on --> redirect to the relevant page with their full details
- Want to build a retailer Notifications tab (when order comes in, when payment completes)
- Want to work on building a navbar
- Want to work on retailer profile details

# Angular Client

Set up with

```
cd graBBT-client
npm i
ng serve
```

hopefully it works
if using vscode, install Prettier for code formatiing

- Right now all static pages
- no link to back-end
- simulated login and logout, username=customer password=password
