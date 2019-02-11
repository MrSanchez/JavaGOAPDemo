# Goal-Oriented Action Planning in Java

This is an implementation of GOAP in Java with a visual demo using JavaFX.

## Motivation
GOAP is a action-planning system most notably used in the 2005 F.E.A.R. to control the in-game AI's actions.
There are a handful of resources out there explaining the internal workings of GOAP.

I was inspired to work with GOAP after reading [this interview](https://web.archive.org/web/20090414101717/http://aigamedev.com/interviews/stalker-alife) with the AI developer for one of my favorite games, S.T.A.L.K.E.R.
For action selection in STALKER's A-Life system, GOAP was used. This got me researching me more about it.
The man behind the idea of GOAP, Jeff Orkin, published a few articles about it which were quite useful. See the bottom of this page.


## GOAP explained in short
In GOAP there is a planner that selects a sequence of actions for an agent (e.g. AI unit). The used actions depends on the goal that is given to the agent.
The actions are chosen by looking at their preconditions, costs and their effects(how they will contribute to satisfying the agent's goal)
The action selection procedure in my implementation is done by placing the agent's possible actions in a graph and using the A* pathfinding algorithm in reverse to find the most optimal path from the desired goal state to the current world state.

## Demo
This very basic demo shows an AI agent, a sword, a forge, a box and a boat.
The agent is given a goal that states one thing: "inBoat" must be true.
Next, the agent is given four actions: ForgeSword, GetInBoat, OpenBox and PickupSword.
The planner is then given the task to find the optimal sequence of actions that results in the agent getting in the boat.

![Basic UI](https://i.gyazo.com/fbcd21141932fe1fcec03e447c914f33.jpg)

The GetInBoatAction however has two preconditions that are defined by the world (aka me), that must be met, prior to executing the action: The box must be opened and the agent must have a sword.
Coincidentally, to open the box, a sword is also required.
A sword can be obtained by either forging it for 5 seconds, or by picking it up.
These two actions have costs attached to them which can be used to bias the planner to prefer one action over the other.


## Notes
This demo shows that one can give an agent a goal and an array of Actions and the planner will decide the agent's behaviour for you.
GOAP has its pros and cons.
Pros: When used in game AI GOAP can give emergent/realistic behaviour without having to specify it yourself.
Cons: In big projects or when you want specific behaviour, the tweaking of the costs, effects and preconditions can become very time consuming and you may feel like GOAP fights against you.

Nevertheless, the GOAP approach is an interesting one and has been a fun challenge to implement.


## Used resources
[Jeff Orkin's GOAP page](http://alumni.media.mit.edu/~jorkin/goap.html)
[His 2003 early GOAP draft](http://alumni.media.mit.edu/~jorkin/GOAP_draft_AIWisdom2_2003.pdf)
[His 2006 GDC article](http://alumni.media.mit.edu/~jorkin/gdc2006_orkin_jeff_fear.pdf)

[Brent Owen's GOAP implementation for Unity3D which helped me implement the connection between the basic FSM and the planner](https://github.com/sploreg/goap)

[My earlier A* Java implementation](https://github.com/MrSanchez/JavaFX_AStar_Showcase)