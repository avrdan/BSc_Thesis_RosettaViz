# The Thesis Project: Visualization Interface For ESA's Rosetta Mission

During my traineeship at the **European Space Agency** (ESA), European Space Astronomy Center, near Madid, Spain, I worked on a **2D and 3D visualization interface for Rosetta**. The visualization project was made so that a user could create an entirely different scene using **metadata** in an **SQL database**. This way they just needed to specify some object properties in order to create the objects in the scene (In a way, this is similar to a tailored rendering engine, albeit with limited functionality). The **jMonkeyEngine3** (a Java-based game engine) was used for rendering. A charting library (**jFreeChart**) was used to plot parameters, as they change in real time.

In a nutsheel, the idea was to set up a base framework so that it can be easy to load multiple **Solar** or **Spacecraft** Objects in a 3D scene and **load orbit data**, as well as having
**simple plotting** functionality.

Some prototype screenshots follow. The first figure, shows the orbiting spacecraft(with a purple trace representing the time from simulation start to current simulation time)

![](https://github.com/avrdan/BSc_Thesis_RosettaViz/blob/master/RosettaOrbit.png)

Next, the Spacecraft's boresight as it intersects the comet is shown:

![](https://github.com/avrdan/BSc_Thesis_RosettaViz/blob/master/RosettaBoresight.PNG)

The last figure shows the two main objects(comet and Rosetta), with their XYZ axes (albeit with a huge scale on the axis of the comet) and other details.

![](https://github.com/avrdan/BSc_Thesis_RosettaViz/blob/master/RosettaCometAxes.png)


Why was Java used as the main language? This was a project constraint :). An analysis of tools is included in the thesis.

## Both Text and Code Available

The thesis' text is uploaded as pdf: **AvramDan\_BSc\_Thesis.pdf**.

The source code and database are included in the **RosettaViz** folder.

## Installation

In order to install the application one must first deploy the database on a **MySQL**/**MariaDB** server
using the supplied SQL file(s). If you are using a **WAMP** or **XAMPP** server, please
note that the dataset is large and may not be imported correctly with **phpMyAdmin**. A
more robust tool is the **MySQL WorkBench**. Of course, the data can also be imported
manually.

In order to connect to the database its name must be "RosettaViz" on the user "root" with no password.
These parameters can be changed from the **MainWindow** class (sorry, making this flexible was not a priority :) ).

Please consult Chapter 8 of the thesis if you need more information on how to run and use the application.

**Note**: two versions of the database data have been provided. One consists in a single large SQL file 
          which can import all DB tables, while the other has a separate file for each DB table.

## Running

In order to run the application you can use the **provided jar file** or generate a new one from the provided code/class files (the **bin** folder has also been committed).

The jar can be run from a terminal:
**java -jar rosettaViz.jar**
