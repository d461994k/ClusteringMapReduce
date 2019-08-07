# ClusteringMapReduce
Clustering using MapReduce in Hadoop


Parallel K-Means Based on MapReduce 
 
 
For a MapReduce job we have to define two functions, map and reduce. We take an initial set of cluster centers randomly from the data set. The mapper assigns the points to clusters based on minimum distance from cluster centers. The reducer updates the cluster centers based on the current point assignments. A combiner is also used to combine the values of same key from different mappers before it is sent to the reducer. 
 
 
Mapper Function : 
 
Input: Global variable centers, the samples 
Output: <key’, value’>pair, where the key’ is the index of the closest center point and value’ is a string comprise of sample information  
1.	Construct the sample instance from value;  
2.	minDis = Double.MAX VALUE;  
3.	index = -1;  
4.	For i=0 to centers.length do  dis= ComputeDist(instance, centers[i]); If dis < minDis {minDis = dis;index = i;} 
5.	End For 
6.	Take string comprising of the values of different dimensions of the center point at index as key’; 
7.	Construct value’ as a string comprise of the values of different dimensions; 
8.	output < key’, value’>pair; 
9.	End 
 
Step 4 finds the closest cluster center. 
 
 
 
Combiner Function : 
 
Input: key is the center of the cluster, V is the list of the string represented samples assigned to the same cluster from each mapper 
Output: < key, value>pair, where the key’ is the center of the cluster, value’ is a string representation of all samples in the same cluster. 
1.	Take a string S initialized as null. 
2.	while(V.hasNext()){ 
Append the string represented samples to the string S; } 
3.	Take key as key’; 
4.	Take value as S; 
5.	output < key, value>pair; 
6.	End 
 
 
Reducer Function : 
 
Input: key is the cluster center, value(V) is the list of the samples from different host 
Output: < key , value>pair, where the key’ is the string representaion of the new cluster cluster, value is samples assigned to current center. 
1. Initialize one array record the sum of value of each dimensions of the samples contained in the same cluster, e.g. the samples in the list V; 2. Initialize a counter NUM as 0 to record the sum of sample number in the same cluster; 3. while(V.hasNext()){ 
Construct the sample instance from V.next(); 
Add the values of different dimensions of instance to the array NUM += num;} 
4.	Divide the entries of the array by NUM to get the new center’s coordinates; 
5.	Construct key’ as a string comprise of the center’s coordinates; 
6.	output < key, value>pair; 
7.	End 
 
 
 
 
 
Plotting the Output Points 
 
A java program is written to plot the output 2D points.This program aims to plot the points in different clusters with different colors.This step is not important as it only used to visually interpret the output points in 2D space. Plotting Program can only be when the input data is 2-dimensional. The points in the same cluster are marked in similar color whereas different clusters are marked by different colors. The cluster centers are marked in white for the data set with 80 points. 
 
 
Implementation : 
 
A global array creates a problem for a multiple node cluster. So initially we configured the mappers to read the cluster centers from the input data file. In the subsequent iterations the mapper reads the updated cluster centers from the output file of the previous iteration. This continues until a steady set of cluster centers is obtained. However for some random data samples the centers keep oscillating due to the data sample itself. So an iteration counter is kept to stop after 10 iterations. 
