# Pokemon
Android tech talk demo for hackRU.

This talk was for those more familiar with Android development as it covers more advanced features such as making HTTP requests and manipulating JSON objects. For a more beginner-friendly talk/guide, refer to this: https://github.com/thevarunshah/ToDoList

# Project location
All of the relevant project files are located under <b>app/src/main</b>

# Important folders and files
The <b>build.gradle</b> file under <b>app</b> defines the Android versions the app is compatible with along with the various dependencies that are requried for the project.

The <b>AndroidManifest.xml</b> file is where you would list all of the different activities (screens) for your app. You can also specify various permissions (such as the internet permission) and general app attributes within this file.

The <b>java/com/thevarunshah/pokemondemo</b> folder contains your Java classes. This is where you design the backend of your project. All of your Activity classes also go here (they are the ones which end with '...View') - these classes are where you would manipulate your various screens. 

The <b>res</b> folder contains all of the resources used in your app. The <b>drawable</b> and <b>minimap</b> folders contain image resources. The <b>layout</b> folder contains the layouts for your screens (activities). The <b>menu</b> folder contains layout files for the action bar. The <b>values</b> folder contains modifications to your app along with global settings such as themes (styles), colors, dimensions, and strings.

# Components
The various components, structure, and methods are commented in detail in the Java files. 

For a more detailed explanation of the Android life cycle, please refer to this image: http://developer.android.com/images/activity_lifecycle.png

