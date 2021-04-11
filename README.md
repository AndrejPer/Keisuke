# Keisuke
Nikoli logic puzzle written in Java


Project demands were:

In the Java programming language, write a program that allows you to play a familiar game Keisuke https://en.wikipedia.org/wiki/Keisuke (puzzle).

Game rules:

• We play on a nxm size map, some fields are shaded. We have two sets of numbers, one for horizontal entries, the other for vertical ones.
The game is a simple numerical crossword puzzle similar to Kakuro :). The goal is to fill all the empty fields (unshaded) with the digits of the numbers from both sets - horizontally for the first set, vertically for the second. The digits of an individual number are written sequentially (numbers are not divided).


The basic game should require / support:

• creating a user interface that allows play (each time a different playing field), with each new start of the game we get a new layout (random)

• creating a user interface that saves the playing field to a file (when playing, allows you to select the layout of the playing field from the file)

• game control menu (replay, setting the playing field size).

• the possibility to check whether the current position on the playing field is valid.

• possibility to display the solution.

• ”endless mode” option, which allows you to play with a dynamic increase in the playing field. When the game is restarted, a new (random) 5x5 layout is generated. When the player fills the playing field correctly, the game expands to a size of 6x6 by maintaining a filled playing field (size 5x5). The sets of numbers for horizontal and vertical entries are updated and the player is allowed to continue playing. Each time a player fills the playing field correctly, this increases by one unit in each dimension and allows for further play.

• Pay attention to the correct use of the basic elements of subject-oriented program- ming - static methods are prohibited, individual program modules are defined as independent classes.

• Make sure the contracts are used correctly!
