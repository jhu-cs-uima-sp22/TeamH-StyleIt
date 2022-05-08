# TeamH-StyleIt
Team H's repo for the app Style It

During our first sprint we set up the backbone and framework for the android application. The first step was to create the different activities and fragments so that all the pages we would need later are properly created. Additionally, we set up our bottom navigation bar so that we could properly route to our different app pages. 

A log-in/register page was set up and used in conjunction with Forebase to provide user authentication and accounts for eht eapp. Additionally, on the post displayal through the home page, we incorporated an on-click feature for bringing up a details page or the post. On this page users can view more information about the post, including likes and dislikes where they can upvote or downvote the post. 

We also decided to implement the main functionality for the Profile and Home pages of our app. In a technical sense, this included:
* Creating a Post class that holds the information regarding all posts
* User class for login/creating posts
* Firebase integration for Authentication and Real-Time Database functionality.
* RecyclerView with Adapter methods to display the Posts.

A post creation page was implemented so that users are able to contribute to the community(Home) feed. In this activity users must specify the necessary components of a post and publish their thoughts. The logged in users are tied to the post and their name will be properly displayed.

A user profile page displays the logged in user's personal information including their name and email. THere they can also upload a profile picture and add a short description of who they are. 

All the relevant pages were styled following our initial design document, utilizing the purple color scheme. 

For our second sprint we spent most of our time setting up the search, marketplace, and comments pages so that the main functionality of the app would be in tact. We also spent a substantial amount of time testing our app and looking for minor bugs/tweaks. 

The marketplace page is very similar to the home page but instead of displaying posts, there are items displayed. Here users can see what is available on the app for sale. Upon clicking on the item, the details are presented where users can see all the subsequent details about the item. Additionally, if a user is interested in the item, they may click the buy button where an email draft will be displayed with the seller's email in their native mail app. 

For both posts and items, we added functionality to remove the post/item if it was made by the signed in user. This way users can manage their posts and have freedom in the app experience.

Additional flexibility was added through the ability to input a profile picture and change your password in the profile page.

The addition of images was a big part of our sprint as we utilized firebase storage to store all the images entered by users while on the app. Users now have the ability to add images onto their posts or items. 

The search page displays items based on keywords entered by the user. The search not only looks through the titles, but other aspects such as the seller as well. It is strictly a search for items because this expands on the marketplace feature of our app. 

Some bugs we faced were users liking a post more than once, images not populating correctly, getting user/post keys, and comments displaying on the wrong posts. With the help of the entire team, we were able to fix these issues and provide a fully functioning app!


User information:
Email: jhu@gmail.com
Password: Hop123

Email: jpineda5@jh.edu
Password: absucks
