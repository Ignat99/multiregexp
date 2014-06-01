
Memory and runtime optimize regexp bulks using multiregexp and brics automaton.  If you use non intersecting regexps you may be able to implement bucket selection heuristics.  This implementation brings back the longest match (it is not eager).  First Commit.  This was done as part of my work for http://www.sizmek.com/.
credits to:
http://www.brics.dk/automaton/
https://github.com/fulmicoton/multiregexp
