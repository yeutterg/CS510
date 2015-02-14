Greg Yeutter
CS510 Artificial Intelligence
Winter 2015
HW3.3 Extra Credit 
2/13/15

Part 2 of this assignment used the Manhattan distance between the master brick and the goal as
the heuristic. The Manhattan distance can be computed as follows:

|y_goal - y_brick| + |x_goal - x_brick|

When running tests on the sliding block puzzle, there were many instances in which multiple
nodes had the same h(n) or f(n) values. If that were the case, the computer would pick the
first node in the frontier with the lowest f(n) value, even if other nodes had the same f(n)
value. What if two nodes had the same f(n) value, but one was closer to the goal than the 
other? E.g.:

f(a) = 10
f(b) = 10
h(a) = 5
h(b) = 3

In this example, the total cost values for a and b are identical, but the heuristic says b is
closer to the goal. In the basic Manhattan distance heuristic, a would be expanded first
because it is listed first. However, we may reach the goal sooner if we choose to expand b 
first.

To make this work, the Manhattan distance heuristic would work normally, expanding nodes with
the lowest f(n) values first. However, if the frontier contains nodes with two identical f(n) 
values, the algorithm will break the tie by choosing the node (of the two) that also contains
the lowest h(n) value. In the above example, b would be explored first.

If A* did reach a point such that f(a) = 10, f(b) = 10, h(a) = 5, h(b) = 3 and implemented 
this modified algorithm, it would make a straight line to the goal (assuming the heuristic
is perfect). For example, it would proceed as follows:

f(a) = 10
f(b) = 10
h(a) = 5
h(b) = 3
g(a) = 5
g(b) = 7

expand b

f(a) = 10
f(c) = 10
h(a) = 5
h(c) = 2
g(a) = 5
g(c) = 8

expand c

f(a) = 10
f(d) = 10
h(a) = 5
h(d) = 1
g(a) = 5
g(d) = 9

expand d

goal reached

Even if the heuristic was only admissible but not perfect, i.e. it underestimates the path to 
the goal, this tie breaking algorithm would help the algorithm find a solution more quickly.




