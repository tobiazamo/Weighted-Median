# weighted-median
 A linear time complexity algorithm that determines the weighted median from a series of positive rational numbers.
 
This algorithm takes as input a sequence of positive rational numbers w1, ..., wn ∈ Q+ (excluding zero) separated by a comma and ending with a period. The output of this algorithm will be the element that, among those passed in as input, satisfies the definition of a weighted lower median; this element will be written to terminal.

To find the weighted median, if the total number of occurrences (let's call it 'n', i.e. the sum of the frequencies / the total number of students) is odd, then the median is the ((n+1) / 2)-th value. If n is even, then the median is the average of the (n/2)-th and the ((n/2) + 1)-th value.

The project and the report (relazione.pdf) describing the problem, approach, solution and time complexity are in Italian.

To compile use the following commands in the teminal:
> javac Progetto.java

> java Progetto < input.txt > output.txt
