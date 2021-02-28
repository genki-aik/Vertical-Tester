## Vertical Jump Tester

### What is it?
This application is written in Java, using JavaFX to create the GUI. It uses `Media`
class in JavaFX to upload a mp4 video. It can calculate your vertical by calculating
how long you are in the air for.\
The equation to measure vertical jump height: _0.5 * 9.81 m/s^2 * (hang time / 2) ^2_

### How to Use
1. Record a video of yourself jumping; make sure the video file is in mp4 format
1. Put the video path into the `Media` constructor that takes in a `File`
1. Watch the video, click __"Take Off"__ right when your feet leaves the ground to jump, and 
click __"Feet Lands"__ right when your feet touches the ground again after the jump.
	1. In order to make this easier, pause the video by clicking the __"||"__ button,
	and clicking the __">>"__ button to forward the video by 0.1 second or __"<<"__
	button to rewind the video by 0.1 second.
1. Click __"Calculate Vertical"__. Check the console to see your vertical height!

### Calculating Vertical Needed for Dunking
You can click on the __"How much do I need to Dunk"__ button to calculate how much vertical
you will need to dunk. It will ask for your standing reach.

### As a Media Player
This application can be used as a media player, since it has basic video player functionality such as play,
pause, forward, and rewind. 

###### Built by Genki Aikawa
