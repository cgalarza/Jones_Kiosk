Jones Kiosk, Version 0.0.1
===========

Author
--------

Created by Carla Galarza for the Jones Media Center (JMC), Dartmouth College.<br>
Carla.M.Galarza@dartmouth.edu<br>
September 9, 2013


Basic Overview and Features
---------------------------

This Kiosk was specifically created for the needs of the Jones Media Center. Once opening the Kiosk displays a set of promotional movies that are pre-selected by the manager (these will usually be new aquisitions, but can also change based on holidays and events). Once movies are selected they are written in a text file and the Kiosk reads the file (~/Desktop/Promotional_Movies.txt) and displays the movies. The Kiosk allows users to search the catalog specifically for items located at the Jones Media Center. Results are displayed with the item's basic information along with an image (if available). At the top of the Kiosk there are genre buttons which reveal canned search results for a variety of genres. At any time if the user wishes to see more information on the item simply clicking on the item's image will reveal the entire catalog entry. After looking through the catalog information the user can return to the previous panel by clicking the green arrow. The user can return to the "home screen" or promotional panel at any time by clicking on the JMC logo.

Installation Instructions
-----------------------
The Kiosk requires two external resources one of them is a folder containing DVD covers/images and the other is a text file listing the promotional movies.

1. ~/Desktop/Promotional_Movies.txt

  This text file should be in plain text and should list the title of the promotional movies and the promotional   movies themselves. There is no limit to the number of movies that can be displayed. The format should be as follows. 
  
  ```
  title
  
  movie name, stable url catalog link
  movie name, stable url catalog link
  movie name, statble url catalog link
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

2. ~/Pictures/DVD/

  This folder should contain DVD covers named corresponding to their Jones Accesion number. The extension of every file should be .jpg. The Kiosk resizes images therefore the exact size of the image doesn't matter. But it is important that the image is portrait and not landscape.

Libraries Used
--------------

JSoup was heavily used to parse the library catalog.

