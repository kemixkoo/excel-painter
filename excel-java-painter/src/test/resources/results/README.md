HSSF only enable to deal with smal pictures.


Preformance
============

Do paint to excel, how many seconds to spend?

| POI Type	 | eclipse_256x256.png  (all FillTypes)	|  singbird_599x450.jpg	 |  bluebird_571x648.jpg |

| HSSF     |  1.7  |    -    |     -       |

| XSSF     |  17   |   413 |   1050  |

| SXSSF   |  11   |   405 |   1070  |

And only test SXSSF for some big pictures:

| bird_872x1000.jpg     	|   233  | 

| duck_918x1201.jpg     |   225  | 

| firebird_1024x1024.jpg|   402  | 


Unsolved
============

1. If png, the AWT api will return "black" color, even not "white", maybe need check "alpha".

2. when XSSF and SXSSF, it's very very slow to paint.
