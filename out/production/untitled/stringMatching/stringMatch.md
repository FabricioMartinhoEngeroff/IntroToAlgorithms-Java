📘 String Matching Algorithms (CLRS Chapter)

This project is a Java OOP learning playground to understand the main algorithms from
String Matching in Introduction to Algorithms (CLRS).

The focus is on clarity, intuition, and visual experimentation, not micro-optimizations.

🧠 What Is String Matching?

String matching is the problem of finding all occurrences of a pattern inside a text.

Example:

Text:    "ratatat"
Pattern: "atat"
Matches: positions 1 and 3


This problem appears everywhere:

🔎 Search engines

🧬 DNA sequence analysis

📝 Log analysis

📄 Text editors

🛡 Security scanners

🚀 Algorithms Implemented
1️⃣ Naive String Matching

Idea: Try to match the pattern at every position in the text.

Very simple

Time complexity: O(n × m)

Good for understanding the basic problem

📌 Weakness: Rechecks characters many times.

2️⃣ KMP (Knuth–Morris–Pratt)

Idea: Use information from the pattern to avoid unnecessary comparisons.

Precomputes the prefix function (π-array)

Never moves backward in the text

Time complexity: O(n + m)

📌 Key concept:
When a mismatch happens, reuse what you already matched.

3️⃣ Suffix Array Search

Idea:
Instead of scanning the text repeatedly:

Build all suffixes of the text

Sort them lexicographically

Use binary search to find the pattern

Building suffix array: O(n log n)

Pattern search: O(m log n)

📌 Used in large-scale text systems and bioinformatics.

4️⃣ LCP Array (Longest Common Prefix)

Works together with the Suffix Array.

It tells how many characters two adjacent suffixes share at the beginning.

Used to:

Find the longest repeated substring

Analyze string structure

🧩 Project Structure
clrs-ch32-string-matching/
│
├── Main.java                      → Menu to run algorithms
├── StringSearchAlgorithm.java     → Interface for search strategies
├── SearchResult.java              → Stores matches and metrics
│
├── NaiveSearch.java               → Brute-force search
├── KMPSearch.java                 → KMP implementation
│
├── SuffixArray.java               → Builds suffix array + LCP
├── SuffixArraySearch.java         → Pattern search using suffix array
├── LongestRepeatedSubstring.java  → Finds repeated substrings