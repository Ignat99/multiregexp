
Memory and runtime optimized regexp bulks for hundreds of patterns using multiregexp and brics automaton.
If you use non intersecting regexps you may be able to implement bucket selection heuristics.

This was done as part of my work for
<a href="http://www.sizmek.com/">Sizmek</a>

Start with RegexpsMachineHelper<V> class and read it's JavaDoc for a quick start.

In case you use the same regexpMachine with multiple threads all working on the same regexpMachine
under high load. you may consider using atomic reference for when you need to reconstruct the machine
and start using it only when ready:

<code>
<p>AtomicReference\<RegexpsMachineHelper\<String\>\> regexpsMachineHelper;</p>
<p>regexpsMachineHelper = new AtomicReference\<RegexpsMachineHelper\<String\>\>();</p>
<p>regexpsMachineHelper.set(new RegexpsMachineHelper\<String\>());</p>
<p>regexpsMachineHelper.get().constructAutomatonMapping();</p>
<p>return regexpsMachineHelper.get();</p>
</code>


credits to:

<a href="http://www.brics.dk/automaton/">dk.brics.automaton</a>

<a href="https://github.com/fulmicoton/multiregexp">multiregexp</a>
