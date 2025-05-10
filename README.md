## Sobuj's Analog Clock
This is a Java-based analog clock with a graphical user interface (GUI). The clock displays the current time with smooth animations for the hour, minute, and second hands. The design is modern, featuring a clean and simple interface with well-defined hands and numbers.

## Features
Real-time updating of the clock. Smooth transitions for the clock hands. Customizable clock face with numbers and ticks. Red-colored second hand for a vibrant look. Includes labels ("Sobuj" and "1319") under the clock's center.

## Screenshots
![Screenshot 2025-05-10 174836](https://github.com/user-attachments/assets/d3d37216-ddb7-4469-9a3b-6c29b5ad5a3c)

## Requirements
Java Development Kit (JDK) 8 or above. An IDE (like IntelliJ IDEA, Eclipse) or terminal for running Java programs.

Installation Clone this repository: git clone https://github.com/your-username/analog-clock.git

Navigate to the project folder: cd analog-clock

Compile and run the program: javac Analog_Clock_With_GUI.java java Analog_Clock_With_GUI

Alternatively, you can import this project into an IDE like IntelliJ IDEA or Eclipse and run it directly.

## How It Works
The clock is built using the following Java components:

JPanel : For drawing the clock face and hands. Graphics2D : To render the graphics of the clock. Thread : Used to continuously update the time and repaint the clock every 20 milliseconds. Calendar and GregorianCalendar: For retrieving the current time.

## Clock Design
Outer Circle : Represents the clock's boundary with a gradient background. Inner Circle : Represents the clock face where numbers and hands are drawn. Clock Hands : Hour, minute, and second hands with distinct colors.

  Second hand  : Bright red.
  Minute hand  : Blue.
  Hour hand    : Black.
## Customizations
The font used for the clock labels is "Garamond", and you can modify the font or colors based on your preferences. The clock label "Sobuj" and the number "1319" can also be customized in the code.

## Contributing
Feel free to fork this repository, make changes, and create pull requests. I welcome any contributions to improve the clock design or add new features!

## Acknowledgements
Thanks to the Java Swing library for providing the tools needed to build the GUI. Special thanks to anyone contributing improvements to this project.
