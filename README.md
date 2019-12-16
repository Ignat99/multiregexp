##Some tests and classes are taken from

https://github.com/alexvetter/LibAutomaton

To run the tests, use:

mvn test

For install maven on Linux Ubuntu use:

apt install maven

###General Description

Memory and runtime optimized regexp bulks for hundreds of patterns using multiregexp and brics automaton.
If you use non intersecting regexps you may be able to implement bucket selection heuristics.

This was done as part of my work for
<a href="http://www.sizmek.com/">Sizmek</a>

Start with RegexpsMachineHelper<V> class and read it's JavaDoc for a quick start.

In case you use the same regexpMachine with multiple threads all working on the same regexpMachine
under high load. you may consider using atomic reference for when you need to reconstruct the machine
and start using it only when ready, see example in the test.

credits to:

<a href="http://www.brics.dk/automaton/">dk.brics.automaton</a>

<a href="https://github.com/fulmicoton/multiregexp">multiregexp</a>

###Motivation and use cases:

###Future work:

