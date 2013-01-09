Description
===============

XChart is a charting API for Java. While several charting APIs already
exist, most of them require a steep learning curve and the code base
is bloated, full of too many "features" and/or very outdated. 
Inspired by SWTCharts, simplicity is prioritized over everything else 
so you can quickly make a plot and move on. XCharts can be used in
your Swing, Java EE, and Java SE applications. If you're looking for
a Java charting API that is easy to use and does not need every feature
under the sun, XChart is for you.

Bugs
===============

Please report any bugs or submit feature requests to [XChart's Github issue tracker](https://github.com/timmolter/XChart/issues).

Getting Started
===============

Non-Maven
---------
Download Jar: http://xeiam.com/xchart.jsp

Maven
-----
The XChart release artifacts are hosted on Maven Central.

Add the XChart library as a dependency to your pom.xml file:

    <dependency>
      <groupId>com.xeiam</groupId>
      <artifactId>xchart</artifactId>
      <version>1.2.2.SNAPSHOT</version>
    </dependency>

For snapshots, add the following repository to your pom.xml file:

    <repository>
      <id>xchart-snapshot</id>
      <snapshots/>
      <url>https://oss.sonatype.org/content/repositories/snapshots</url>
    </repository>

Building
===============
mvn clean package  
mvn javadoc:javadoc  

Donations
===============
17dQktcAmU4urXz7tGk2sbuiCqykm3WLs6