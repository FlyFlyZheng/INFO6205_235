# INFO6250_FinalProject

## About
This is final project for Data Structure and Algorithm lecture. 

This project is for the path planning of cleanner robot of a specfic map, with Genetic Algorithm approach.

Detailly, In a specific gridding map, there are some cups in fix number that are randomly scattered across the whole map. Meanwhile, a cleaner robot can take some actions (up, down, left, right, pick up, random move or do nothing) in a specific sequences. For each action, if a cup is picked successfully, the robot will get score accordingly. However, if some useless action happened, the robot will lose marks as well. Finally, the mission of cleaner robot is to clean those cups of entire environment in a specific sequence with highest score.

Rules for score:
* Clean a gird with a cup -> +10 points
* Clean a cup without a cup -> -1 points
* Hit the wall -> -5 points
* Normal move -> 0 points

To resolve this problem, we propose an evolutionary approach based on Genetic Algorithm (GA) which consisted of several steps to get final solutions.


## Team Info
* Zheng Liu: [liu.zheng4@husky.neu.edu](liu.zheng4@husky.neu.edu) 001826884
* Yang Li : [li.yang5@husky.neu.edu](li.yang5@husky.neu.edu) 001811572

## Director
* Robin Hillyard
