# Assignment 3 - *Twitter Client*

**Twitter Client** is a simplified twitter client, built for learning how to make android apps.

It is [Assignment #3](http://courses.codepath.com/courses/intro_to_android/unit/3#!assignment) for the [CodePath Android class](https://codepath.com/androidbootcamp).

Submitted by: **Jeff Martinez**

Time spent: ~**8** hours spent in total

## User Stories

The following **required** functionality is completed:

* [X] User can sign in to Twitter using OAuth login
* [X] User can view the tweets from their home timeline
    * [X] User should be displayed the username, name, and body for each tweet
    * [X] User should be displayed the [relative timestamp](https://gist.github.com/nesquena/f786232f5ef72f6e10a7) for each tweet "8m", "7h"
    * [X] User can view more tweets as they scroll with [infinite pagination](http://guides.codepath.com/android/Endless-Scrolling-with-AdapterViews)
* [X] User can compose a new tweet
	* [X] User can click a “Compose” icon in the Action Bar on the top right
	* [X] User can then enter a new tweet and post this to twitter
	* [X] User is taken back to home timeline with new tweet visible in timeline

The following **optional** features are implemented:

* [X] **Advanced**: While composing a tweet, user can see a character counter with characters remaining for tweet out of 140
* [X] **Advanced**: Links in tweets are clickable and will launch the web browser (see [autolink](http://guides.codepath.com/android/Working-with-the-TextView#autolinking-urls))
* [ ] **Advanced**: User can refresh tweets timeline by pulling down to refresh (i.e [pull-to-refresh](http://guides.codepath.com/android/Implementing-Pull-to-Refresh-Guide))
* [ ] **Advanced**: User can open the twitter app offline and see last loaded tweets
	* [ ] Tweets are [persisted into sqlite](http://guides.codepath.com/android/ActiveAndroid-Guide) and can be displayed from the local DB
* [ ] **Advanced**: User can tap a tweet to display a "detailed" view of that tweet
* [ ] **Advanced**: Improve the user interface and theme the app to feel "twitter branded"
* [ ] **Bonus**: User can see embedded image media within the tweet detail view
* [ ] **Bonus**: Compose activity is replaced with a [modal overlay](http://guides.codepath.com/android/Using-DialogFragment)

The following **additional** features are implemented:

* [X] Remaining tweet characters changes color when the tweet has exceeded the 140 character limit.

## Video Walkthrough

![Video Walkthrough 1](walkthrough.gif)

GIFs created with [LiceCap](http://www.cockos.com/licecap/).

## Notes

### Challenges encountered

* I could not get breakpoints inside of a callback method to work using Android Studio.
* I initially forgot to make `User` class implement `Serializable` when I made my `Tweet` class `Serializable`. This made my app crash when it tried to serialize a `Tweet` object.

## Open Source Libraries Used

* [James Smith's Android Asynchronous Http Client](http://loopj.com/android-async-http/)
* [Square's Picasso](http://square.github.io/picasso/)

## License

    The MIT License (MIT)

	Copyright (c) 2015 jeff martinez
	
	Permission is hereby granted, free of charge, to any person obtaining a copy
	of this software and associated documentation files (the "Software"), to deal
	in the Software without restriction, including without limitation the rights
	to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
	copies of the Software, and to permit persons to whom the Software is
	furnished to do so, subject to the following conditions:
	
	The above copyright notice and this permission notice shall be included in all
	copies or substantial portions of the Software.
	
	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
	IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
	FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
	AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
	LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
	OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
	SOFTWARE.
