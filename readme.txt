It should be working. All 3 test files compressed. I added some comments in order to clarify what the purpose of variables and methods are, but it might not be good enough. I call on another class that converts things to bytes.

If you want to use the binary you can, but you can alter the method compress() to output the ArrayList of integers to a file and use that. The integers in there are the dictionary values like he said on the board

Peter's notes:

None of the test cases can be decompressed, but the specific case of "adcabab" works. I couldn't reduce the runtime by much, since I could only test small files, but I expanded the program to write the decoded message to a text file, which the original decoder didn't do. I still need to figure out why the 3 test cases we were given aren't working.