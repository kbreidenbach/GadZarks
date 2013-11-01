GadZarks
========

Coding problem for 955Dreams.

Info
----
There are two branches.

master:
This contains a version that uses GridLayout. It builds and shows conrrectly, but is not capable of scrolling

GridView:
This branch contains a version that uses GridView, and can scroll. It is not yet complete and is a work in progress.

The implementation has been tested on Android Emulator with following characteristics:
- AVD for 3.7" WVGA Nexus One (480 x 800: hdpi)
- Android 4.0 - API level 14
- RAM 2048
- VM Heap 256
- Internal Storage 512 

Known Issues
------------

- The required fonts were not available for Android, and I could not download. Close alternative shown
- Border image does not appear to display correctly in emulator, but appears correct in IntelliJ designer
- Double digit cell labels sometime only display first character in emulator, clicking on and off to another cell sometimes displays full number

To Do
-----

- Would like to refactor time management to be more streamlined
- Need to implement scrolling on grid

Additions
---------

- By default the system runs as 1 minutes = 1 day. This is intentionally and will be changed when Settings window is complete

