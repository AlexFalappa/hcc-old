What is 'hcc' ?
--------------

**hcc** is an _HMA Catalogue Client_ application.

[Heterogeneous Missions Accessibility](http://earth.esa.int/hma) (HMA) is a project by the *European Space Agency* to harmonize and standardize the ground segment services and related interfaces of national space agencies, satellite or mission owners and operators.

Within this project a series of data models and protocols have been designed to deal with the typical functionalities of satellite missions ground segments such as catalogue browsing, acquisition feasibility and scheduling, product ordering, ordered product access and so on.

**hcc** deals with catalogue browsing of earth observation product and offers a visual graphic interface for issuing catalogue queries.

Features
--------

* Definition of catalogue web servicess with name, endpoint URL, timeout and a list of collection strings which may optionally be discoveried connecting to the catalogue service itself (HMA GetCapabilities operation)
* Supported query criteria:
    * Product collections (multiple selection)
    * Time constraints (contained or overlapping a time range, before or after an instant)
    * Spatial constraints (lat/lon range, polyline, polygon, point or cirle areas of interest)
* Visual representation of query product footprints on a 3D globe (uses the [World Wind Java SDK](http://goworldwind.org) )
* Base cartography from widely known map providers (e.g. _NASA Blue Marble_ and _Microsoft Bing_)
* Geographic names and lat/lon graticule overlays
* Persistence of catalogue definitions and view settings between invocations

Screenshot
----------

![hcc screenshot](https://github.com/AlexFalappa/hcc/raw/master/hcc-0.7.png)
