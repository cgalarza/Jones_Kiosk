Jones Kiosk 0.9.1 (Beta), 2/14/14
===========

Author
------

Created by Carla Galarza for the Jones Media Center (JMC), Dartmouth College.<br>
Carla.M.Galarza@dartmouth.edu


Basic Overview and Features
---------------------------

This kiosk was specifically created for the needs of the Jones Media Center. The goal of the kiosk is to give patrons an interactive way to browse and select movies available at the Jones Media Center.

Once opening, the kiosk displays a set of pre-selected promotional movies (these will usually be new aquisitions, but can be changed based on holidays and events). Results are displayed with the item's basic information along with an image (if available). At the top of the kiosk, there are genre buttons which reveal canned search results for a variety of genres. If the user wishes to see more information on the item at any time, simply clicking on the item's image will reveal the entire catalog entry. After looking through the catalog information the user can return to the previous panel by clicking the green arrow. The user can return to the "home screen" or promotional panel at any time by clicking on the JMC logo.


Installation Instructions
-------------------------

This kiosk was optimized for a 21.5-inch touchscreen (1980px x 1080px). The application is entirely in Java full-screen mode therefore once the application starts it takes over the entire screen. On Macs, users are able to quit out of the application by using command + Q. The application might take a minute to load depending on the size of the promotional images.

The application should run from the terminal using the following command (Currently, the JAR does not run properly if it is double-clicked).

```
java -Xmx2g -jar JonesKiosk.jar
```

The Kiosk requires two external resources one of them is a folder containing DVD covers/images and the other is a text file listing the promotional movies.

1. ~/Pictures/DVD/

  This folder should contain DVD covers named corresponding to their Jones accession number. The extension of every file should be .jpg. The kiosk resizes images so the exact size of the image doesn't matter, but it is important that the images are in portrait and not landscape.
  <br/>**Note:** The file extension of the images should be .jpg NOT .jpeg or .JPG.
  <br/>**Note:** Only numbers should be in the title of the file (before the file extension).
  
2. ~/Desktop/Promotional_Movies.txt

  The kiosk reads this text file and displays the movies listed. This text file should be in plain text and should list the title of the promotional movies and the URL link to the corresponding catalog entry. There is no limit to the number of movies that can be displayed. The format should be as follows. 
  <br/>**Note:** The title of the film should not contain any commas.
  
  ```
  title
  (empty line)
  movie name, stable url catalog link
  movie name, stable url catalog link
  movie name, stable url catalog link
  ```
  Example:
  ```
  Recent Acquisitions, August 2013

  Stoker, http://libcat.dartmouth.edu/record=b5409071~S1
  Workaholics: Season 1, http://libcat.dartmouth.edu/record=b5374501~S1
  Oz the Great and Powerful, http://libcat.dartmouth.edu/record=b5409058~S1
  Toys in the Attic, http://libcat.dartmouth.edu/record=b5409081~S1
  Batman Begins, http://libcat.dartmouth.edu/record=b5409867~S1
  Rizzoli & Isles: Season 2, http://libcat.dartmouth.edu/record=b5408880~S1
  Identity Thief, http://libcat.dartmouth.edu/record=b5409024~S1
  The Newsroom: Season 1, http://libcat.dartmouth.edu/record=b5408874~S1
  People Like Us, http://libcat.dartmouth.edu/record=b5409076~S1
  Suits: Season 2, http://libcat.dartmouth.edu/record=b5408868~S1
  The Man with the Iron Fists, http://libcat.dartmouth.edu/record=b5409884~S1
  Elementary: Season 1, http://libcat.dartmouth.edu/record=b5420679~S4
  ```

Troubleshooting
---------------
* Make sure you are connected to the internet.
* Make sure there is a file called Promotional_Movies.txt located on the Desktop. 


Future Features/Bug Fixes
-------------------------

1. Ensure that double-clicking the Jar starts the kiosk properly. 
2. Default promotional movies, in case their isn't a list of movies on the Desktop.
3. Timer that resets the kiosk after it has been used by a patron.
4. Decreasing response time.
5. Using the most efficient way to load images.

Libraries Used
--------------

JSoup was used to parse the Dartmouth Library Catalog.

