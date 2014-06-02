
Memory and runtime optimized regexp bulks for hundreds of patterns using multiregexp and brics automaton.
If you use non intersecting regexps you may be able to implement bucket selection heuristics.

This was done as part of my work for
<a href="http://www.sizmek.com/">Sizmek</a>

Start with RegexpsMachineHelper<V> class and read it's JavaDoc for a quick start.

In case you use the same regexpMachine with multiple threads all working on the same regexpMachine
under high load. you may consider using atomic reference for when you need to reconstruct the machine
and start using it only when ready:

<p>
<code>
AtomicReference%3CRegexpsMachineHelper%3CString%3E%3E%20regexpsMachineHelper%3B
</code>
</p>

<p>
<code>
regexpsMachineHelper%20%3D%20new%20AtomicReference%3CRegexpsMachineHelper%3CString%3E%3E()%3B
</code>
</p>

<p>
<code>
regexpsMachineHelper.set(new%20RegexpsMachineHelper%3CString%3E())%3B
</code>
</p>

<p>
<code>
regexpsMachineHelper.get().constructAutomatonMapping()%3B
</code>
</p>

<p>
<code>
return%20regexpsMachineHelper.get()%3B
</code>
</p>

credits to:

<a href="http://www.brics.dk/automaton/">dk.brics.automaton</a>

<a href="https://github.com/fulmicoton/multiregexp">multiregexp</a>
