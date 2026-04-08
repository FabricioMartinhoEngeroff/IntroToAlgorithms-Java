Training ML — CLRS Chapter 33: Machine Learning Algorithms
Java implementation of three Machine Learning algorithms from Introduction to Algorithms (CLRS), Chapter 33, applied to strength training and workout analysis. No frameworks. Pure Java 17.

What This Project Does
You input the data from a training session — sets, RPE, load, fatigue, technique — and the algorithm outputs a conclusion: whether homeostasis was broken, whether the volume was junk, and what load to use next.
Three algorithms from the chapter solve this problem in different ways. You pick which one runs from the menu.

The CLRS Chapter — Core Ideas
What the chapter is about
Chapter 33 introduces three foundational algorithms used in machine learning. The key insight of the chapter is that all three share the same underlying structure: you have a state, you observe an outcome, and you update the state to improve future predictions. Only the update rule changes between them.
Algorithm 1 — Gradient Descent
The chapter frames this as an optimization problem. You have a function that measures how wrong your current solution is (the loss function), and you want to find the parameters that minimize it.
The core update rule from CLRS:
w = w - η * gradient(w)
Where η (eta) is the learning rate — how big a step you take. If it is too large, you overshoot the minimum. If it is too small, convergence is very slow.
The chapter proves that for convex functions (bowl-shaped), gradient descent always reaches the global minimum. For non-convex functions it may get stuck in a local minimum.
The constrained variant adds a projection step: after each update, if the solution left the valid region, you snap it back to the nearest valid point.
Algorithm 2 — Weighted Majority
This is an online learning algorithm. You have N experts making predictions. You do not know which expert is good. The algorithm starts trusting all of them equally and learns over time.
The update rule from CLRS:
if expert predicted wrong:
weight = weight * (1 - γ)
Where γ (gamma) is the penalty factor. Each mistake reduces the expert's weight exponentially. Experts that keep getting it wrong quickly become irrelevant. The algorithm's prediction at each round is a weighted vote:
vote for outcome 1 = sum of weights of experts who predicted 1
vote for outcome 0 = sum of weights of experts who predicted 0
predict whichever side is heavier
The chapter proves a regret bound: the total mistakes made by the algorithm is at most O(log N) more than the best single expert, regardless of how adversarial the sequence of outcomes is.
Algorithm 3 — K-Means
This is an unsupervised algorithm. You have data points and want to find natural groups (clusters) in them. You do not know the labels in advance.
The CLRS formulation:
1. Initialize k centroids (one per cluster)
2. Assign each point to the nearest centroid
3. Recompute each centroid as the mean of its assigned points
4. Repeat steps 2–3 until assignments stop changing
   The objective being minimized is the sum of squared distances from each point to its centroid:
   f(S, C) = Σ min_j ||x - c(j)||²
   The algorithm always converges but only to a local minimum. The result depends on the initial centroids. The chapter notes that K-Means is O(T · d · k · n) per iteration where T is iterations, d is dimensions, k is clusters, n is points.

Project Structure
training-ml/
└── src/
└── training/
├── app/
│   └── Main.java                  ← menu, entry point
├── model/
│   ├── TrainingSession.java        ← input: sets, rpe, load, fatigue, technique
│   ├── TrainingConclusion.java     ← output: flags, scores, recommendation
│   └── Expert.java                ← weighted majority expert with penalty
└── service/
├── DataService.java            ← 9 fictitious training sessions
├── GradientDescentService.java ← algorithm 1
├── WeightedMajorityService.java← algorithm 2
└── KMeansService.java          ← algorithm 3

Input Parameters
Every algorithm receives the same training session data:
ParameterVariableTypeDescriptionWorking SetsworkingSetsintSets that actually generate adaptation (not junk volume)IntensityrpedoubleRate of Perceived Exertion, 1–10. Below 7 = junk volumeLoadloaddoubleAbsolute weight used in kgResidual FatiguefatigueIndexdouble0 = fully rested, 10 = exhaustedTechnique ScoretechniqueScoredouble0–10. Below 6 = technical failure, load must decreasePerformance ScoreperformanceScoredoublePerceived session output, 0–100

Output — TrainingConclusion
Every algorithm produces the same output structure:
FieldTypeMeaningisHomeostasisBrokenbooleanTrue if the stimulus was enough to force adaptationisJunkVolumebooleanTrue if RPE was too low to mattertechniqueFailedbooleanTrue if technique score dropped below thresholdadaptationScoredoubleCalculated stimulus quality, 0–10systemicFatiguedoubleAccumulated fatigue after this session, 0–10suggestedLoadNextdoubleRecommended load for next session in kgrecommendationStringPlain text conclusion

How Each Algorithm Uses the Data
Gradient Descent
Trains a linear regression model on the historical sessions. The model learns weights for each feature (sets, rpe, load, fatigue, technique) that best predict performance score. After training, it applies those weights to evaluate the current session: how much adaptation did the stimulus generate? Is the load progression justified or should it decrease due to technique failure?
Weighted Majority
Three training philosophy experts make a binary prediction each round: did this session generate adaptation (1) or not (0)? The experts are:

Heavy Duty — votes yes only if RPE ≥ 9 and sets ≤ 3
Moderate Volume — votes yes if sets are 3–6 and RPE is 7.5–9.5
Deload — votes yes if fatigue is ≥ 7 (meaning rest was the right call)

After each historical session, experts who predicted wrong are penalized with w = w * (1 - 0.2). The dominant expert after all rounds dictates the conclusion philosophy for the current session.
K-Means
Groups the historical sessions into 3 clusters by stimulus profile: low stimulus, moderate, high/overreaching. The current session is assigned to its nearest cluster centroid. The cluster profile determines the recommendation: low cluster → increase intensity, moderate cluster → progress linearly, high cluster → deload.

Constraints and Constants
ConstantValueMeaningJUNK_VOLUME_RPE7.0RPE below this = volume mortoTECHNIQUE_THRESHOLD6.0Technique below this = reduce loadHOMEOSTASIS_SCORE5.0Adaptation score above this = homeostasis brokenLOAD_INCREMENT2.5 kgAdded to load when progression is warrantedOVERLOAD_PENALTY0.90Load multiplier when technique failsGAMMA0.2Weighted Majority penalty per mistakeLEARNING_RATE0.000001Gradient Descent step sizeK3Number of K-Means clusters

How to Run
bash# From the src/ folder
javac training/model/*.java training/service/*.java training/app/Main.java
java training.app.Main
Or open in IntelliJ, right-click src/ → Mark as Sources Root, then run Main.java.
Example input for a Heavy Duty session:
Working sets: 2
RPE: 9.5
Load: 120
Fatigue: 4
Technique: 8.5
Performance: 88
Expected conclusion: homeostasis broken, no junk volume, no technique failure, load progresses by 2.5 kg.

Connection to the Chapter
The CLRS chapter ends by noting that Gradient Descent, Weighted Majority, and the analysis behind K-Means all minimize the same type of objective — they are different views of the same optimization idea. Gradient Descent updates a continuous parameter vector. Weighted Majority updates a discrete weight over experts. K-Means updates cluster centers. All three iterate toward a lower cost. This project makes that connection concrete by running all three on the same dataset and producing the same output format from each.