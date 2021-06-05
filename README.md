# Cryptocurrency pocket app for Android
This app helps users to search, and track their favorite Cryptocurrency.<br>
It's just a simple app for practising purpose.<br>
## Requirements
Build a wallet to view the price of crypto currencies.<br>
Use MVVM architecture.<br>
Use Koin dependency injection for app architecture
Add a search function or filter function to find currencies easier.<br>
Allow users to add their favorite currencies.<br>
Send a Git repository/bundle that can be compiled<br>
## Description
App only allow user to refresh online data every 4 hours.<br>
The first time openning app, it will get data from CoinHako API and cache in Room database.<br>
UI only load data from Room database, not directly from internet, to make sure it still works in offline mode.<br>
Database structure includes:<br> 
currency_table: store all currencies data fetched from API<br>
pocket_table: store list of currencies' codes that user had added to pocket (favorite list)<br>
currency_view: a view by joining 2 above tables, is the main object used for UI
<br>
There are 2 screens:<br>
##### My pocket
Default screen, showing favorite coins' selling/buying prices, user can remove items from the list. Using floating button to navigate to the next screen.
##### Search
Search by currency's name, code. User can add new item to "my pocket" screen.

## Credits
Some vector resources were download from https://www.flaticon.com/
Created by
Nhor Phai (https://www.flaticon.com/authors/nhor-phai)
Freepik (https://www.freepik.com)