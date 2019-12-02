# diusshop
DiUS Shopping Cart Test - There hasn't been any UI developed for this application. The application is very generic and can be tested using test cases. And this development has been done using TDD approach. Haven't used any third-party jars including logging as well.

# Installation
Use mvn clean install to install the app

## Running tests
Test cases are written using JUnit, please feel to change the scan items for different bundles
- Rules can be modified in rules.txt based on the regex (^atv|ipd|mbp|vga) size (=|>|<) ([0-9]), (discount|free|newprice) (atv|ipd|mbp|vga|\d+(\.\d{1,2})?)
- First word is a sku code of the actual productt, followed by size and condition and the action seperated by the comma.
