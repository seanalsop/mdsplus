
Window {#dt_window}
======

MDSplus provides a window data type which is used exclusively in conjunction
with a dimension data item. The window provides a means for bracketing a
potentially infinite vector of values generated by a range data item. A window
data type is a structure containing three fields: the start index, the end
index and the value at index 0. The window brackets the axis portion of a
dimension item by finding the nearest element in the axis to the "value at
index 0" value. The subset of the axis elements are selected using the start
index and end index using this starting element as the index 0. For example if
the window was BUILD_WINDOW(-5,10,42.), MDSplus would find the element in the
axis portion of the dimension closest to a value of 42 (assuming the axis is
always increasing) and call that element, element number 0. The subset would be
this element and the 5 elements before it and the 10 elements after it. For a
more detailed explanation see the description of the dimension data type.




The following table lists some of the TDI functions available for creating or
accessing windows:

\latexonly { \tiny \endlatexonly

| **Function**  | **Description**                              |
|---------------|----------------------------------------------|
| BEGIN\_OF     | Return begin portion of a window             |
| BUILD\_WINDOW | Build a window structure                     |
| END\_OF       | Return the end portion of a window structure |
| MAKE\_WINDOW  | Make a window structure                      |

\latexonly } \endlatexonly


