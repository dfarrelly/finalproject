Final Project Write Up


Dylan Hand, Zachary Kiihne, Darren Farrelly


We started with a vision of the end goal of what we wanted. We wanted to have a 3d map of our day, something that would let us visualize where we went during the day in 3d space. Starting from this concept we built up our project. The project has two main parts, the app for data collection and the python script used to analyze the data.


To create the data collection app we built off of the platform that A0 provided. We choose A0 as opposed to another project because it was easier to see how to integrate the GPS location data and barometer readings that we would need to create a 3d point that was our location at any given time. At first to ensure everything worked properly we only used the GPS data. While we were able to get the data we were having trouble getting it from the phone to the computer where it would be analyzed. After hours of messing around trying to get the data on a file on the phone so that it could be transferred to a computer we decided upon the work around of sending an email to ourselves containing each data point, comma separated. This let us take the email copy it into a text editor and save it as a csv that our python script would be able to parse.


Once we had basic GPS coordinates in csv form we took that and started messing with it in Excel, trying to figure out the best way to smooth and parse the data. When we were satisfied with this we started on the python script. The script in its basic form would take in the csv and output a plotly graph. Each data point was referenced back to the library as a base point, which acted as (0,0). Then we smoothed the data, eliminating any point that was too far off from the previous point and finally averaging the points so the gaps were smoother.

We did this for just longitude and latitude before we worked on adding in the barometric readings. One quirk of Android programming is that you do not go get sensor readings they come to you. This meant that whenever the GPS coordinates changed we would not be able to get a barometer reading. We had to save the barometer readings as they came in and then fill in the saved value whenever the coordinates changed. When we first ran the script on this new 3d data what we saw was that the pressure readings were being poorly populated. For a set of maybe thirty GPS data points we would have about three different barometric readings. This lead to graphs that looked fine in 2d but jumped around when the pressure reading was added.


The clear solution to this was to have more richness in our data points. Elevation changes were not being represented in the data. To add richness to the data we made it so that whenever the GPS or barometer got a reading data from both would be recorded. When we added this feature and walked around collecting data we got many more points and much more sensitivity in the barometer readings.


With this framework in place we then collected several data sets by walking around campus. Each data set could then but put into the python script and a 3d graph would come out. We got several good looking graphs from this, a clear demonstration of our results.


Due to the constraints of the medium we could not find a way to overlay our 3d map with a map of the real world. Ideally this is what we would do letting you see your path through buildings and along hills. What we have created for this project is the first step in this larger vision.


Add screenshots below
Add pics of graphs
