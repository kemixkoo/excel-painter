# Excel Pixel Painter

Try to paint the images to Excel for pixel  with 2 ways:
1. [POI](http://poi.apache.org/) can write *.xls and *.xlsx
2. [JXL](http://jexcelapi.sourceforge.net/) seems only support for *.xls, and didn't update so long time


# Max of Columns and Rows
From the API [SpreadsheetVersion](https://svn.apache.org/repos/asf/poi/trunk/src/java/org/apache/poi/ss/SpreadsheetVersion.java)

- *.xls:  64k (2^16) rows, 256 (2^8) columns, 4000 cell styles, 32767 text contents.
- *xlsx: 1M (2^20) rows, 16K (2^14) columns, 64000 cell styles, 32767 text contents.

So, only POI XSSF and SXSSF can do some painting with pixel. POI HSSF and JXL can't at all.

# Demo

The demo icons are in [excel-java-painter/src/test/resources/icons](https://github.com/kemixkoo/excel-painter/tree/master/excel-java-painter/src/test/resources/icons)

The result of excel files are in [excel-java-painter/src/test/resources/results](https://github.com/kemixkoo/excel-painter/tree/master/excel-java-painter/src/test/resources/results)
