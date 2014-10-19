What is 'hcc' ?
--------------

**hcc** is an _HMA Catalogue Client_ application.

[Heterogeneous Missions Accessibility](http://earth.esa.int/hma) (HMA) is a project by the *European Space Agency* to harmonize and standardize the ground segment services and related interfaces of national space agencies, satellite or mission owners and operators.

Within this project a series of data models and protocols have been designed to deal with the typical functionalities of satellite missions ground segments such as catalogue browsing, acquisition feasibility and scheduling, product ordering, ordered product access and so on.

**hcc** deals with catalogue browsing of earth observation product and offers a visual graphic interface for issuing catalogue queries.

![hcc screenshot](https://github.com/AlexFalappa/hcc/raw/master/hcc-0.8.png)

Features
--------

* Definition of catalogue web servicess with name, endpoint URL, timeout and a list of collection strings which may optionally be discoveried connecting to the catalogue service itself (HMA GetCapabilities operation)
* Supported query criteria:
    * Product collections (multiple selection)
    * Time constraints (contained or overlapping a time range, before or after an instant)
    * Spatial constraints (lat/lon range, polyline, polygon, point or cirle areas of interest)
* Visual representation of query product footprints on a 3D globe or 2D map in four projections (uses the [World Wind Java SDK](http://goworldwind.org) )
* Base cartography from widely known map providers (e.g. _NASA Blue Marble_ and _Microsoft Bing_)
* Geographic names and lat/lon graticule overlays
* Results navigation and highlight
* Fly to Lat Lon point with flown-to locations history
* Persistence of catalogue definitions and view settings between invocations
* Several Look & Feels available

Changelog
---------

**v0.8**   19/10/2014

* GetRecords results are grouped by collection and put in separate layers
* Removed right pane: footprints layers visibility, color and opacity controls together with visibility for base cartography and visual aids
are now in a separate modeless dialog
* Left pane: redesign of spatial constraints widget layout and integration of the navigation panel
* Internal refactoring and enhancements

**v0.7**

* First public relelase

Building
--------

hcc is written in Java, this repository contains three linked NetBeans projects. It uses the NASA WorldWind SDK v 2.0.0 for map and
globe display. WorldWind in turn uses the JOGL library as Java-OpenGL bindings.

### Tools

* Java Development Kit version 7 (developed and tested with Oracle JDK)
* NetBeans IDE (developed and tested with v8.0.1)

### Notes

It's recommended to choose the JDK 7 as default Java Platform for NetBeans, as the projects do not explicitly require a Java 7 platform.
If you have NetBeans running on a JDK 8 assign a Java Platform explicitly.