<a href="https://github.com/apoplawski96/projects-summary/blob/master/assets/header.png"><img src="https://github.com/apoplawski96/projects-summary/blob/master/assets/header.png"></a>

<h1 align="center">DAZN - Code Challenge App</h4>

<p align="center">
  <a href="#requirements">Requirements</a> •
  <a href="#technology-and-functionality-overview">Technology and Functionality Overview</a> 
</p>

<img align="center" src="https://github.com/apoplawski96/dazn-code-challenge/blob/master/app/src/main/res/mipmap-xxxhdpi/dazn_logo.png" /></a>
<p><h3 align="center">
  Kotlin •
  MVVM •
  Room •
  Coroutines •
  LiveData •
  Kodein •
  ExoPlayer •
  Navigation •
  Retrofit2 •
  Glide •
  DiffUtil
</h3></p>

## Requirements

* <b>Min SDK</b> - 26
* <b>Kotlin</b> - 1.3.50

## Technology and Functionality Overview

The app is built in <strong>MVVM</strong> design pattern, writen solely in <strong>Kotlin</strong>. For concurrency and reactive programming tools I decided to go with <strong>LiveData</strong> and <strong>Coroutines</strong>. The app fetches the data from the network using <strong>Retrofit2</strong>, then stores it locally with help of <strong>Room</strong> library. It uses <strong>Kodein</strong> for dependency injection.

<p><h3 align="center">Main Objectives</h3></p>
✅ <em>"There should be two tabs in the app, preferably put in the form of bottom navigation."</em><br> <strong> • BottomNavigationView</strong> and <strong>Navigation Component</strong> (Android Jetpack). <br><br>
✅ <em>"Events screen should present a list of events available to be watched. When an item is tapped, the appropriate video is played on Playback Screen."</em> <br> <strong> • RecyclerView</strong>. <br><br>
✅ <em>"Use the standard APIs to present a list of items provided by our API."</em> <br> <strong> • Retrofit2</strong>. <br><br>
✅❓ <em>"Assume that the list can get fairly long (e.g up to 100 items)."</em> <br> Data from the network is cached in db (<strong>Room</strong>), but it would be even better with <strong>Paging</strong> (network + cache), which I didn't manage to implement in time.<br><br>
✅ <em>"Events should be ordered by date in ascending order. Extra points will be awarded for date formatting similar to the one presented in the mockups."</em> <br> <strong> • Java Time</strong>.<br><br>
✅ <em>"Schedule screen, with a schedule for today only. It should auto-refresh every 30 seconds."</em> <br> Auto-refresh done with <strong> Coroutines</strong>.<br><br>
✅ <em>"Extra points will be awarded for updating the state of the list without losing its scroll position and without flickering."</em> <br> <strong> • DiffUtil</strong>.<br><br>
✅ <em>"Thumbnails should be retrieved from the listings feed."</em> <br> <strong> • Glide</strong>.<br><br>
✅ <em>"Additionally, there should be a Playback Screen, playing a video in the default player. Use whichever player you prefer. The aim is to be able to play the video that is provided in the selected event."</em> <br> <strong> • ExoPlayer2</strong>.<br><br>

  
## Contact

To <strong>DAZN Recruitment Team</strong> - thank you for giving me a chance to participate in "Code Challenge" phase. Feel free to contact in case of any questions or confusion. I'm really looking forward to hearing back from you! <br>
<em>Artur</em>

✉️ a.poplawski96@gmail.com
<br>
👷 <a href="http://www.linkedin.com/in/apoplawski96/">linkedin.com/in/apoplawski96/</a>

