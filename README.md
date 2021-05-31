
This file presents the performance of SourceAFIS, an open source fingerprint matching software.
The link to software's main homepage is https://sourceafis.machinezoo.com/.

### High-Level Overview of How the Algorithm Works

- First, minuitae (ridge endings and bifurcations) are saved into templates. Each minutiae
is saved as a point on the image and its associated direction angle. An example is shown below.

![example_minuitae](./angle_ex.png)
- Edges are then created between every pair of minutiae, and so for each edge, there is an edge and 
two relative angles.
- In the matching step, the algorithm tries to first find a single edge that is shared between the two input 
fingerprints (aka a root pair) using a nearest neighbor algorithm.
- After the root pair is identified, the algorithm finds other shared edges by building the shared graph outward
from the root pair.
- For the final step, the algorithm scores the similarity between the two input images by taking into account
each pair of "matching" edges and looking at how closely each of these pairs truly match.


### Performance

The performance of the algorithm is measured in the following two applications:

1. 1:1 matching; given a pair of fingerprint images, determines whether the two images come from the 
same finger or not.

2. 1:N matching; given a fingerprint, determine which images, if any, from a database of prints match
that fingerprint.

#### Current Performance on Images in Box

Example of an image from the Box files:

![box_image](./FingerprintMachineLearningProject/A1158_index1.jpg)

For 1:1 matching, the OpenSourceAFIS algorithm has the following results:

![Confusion_Matrix](./Confusion_Matrix.png)

![Performance_Metrics](./Performance_Metrics.png)

- In layman's terms, the algorithm is to be trusted when it says it has found a potential match. However, the
algorithm too frequently determines that are no match(es) when one or more do exist.

For 1:N matching, the OpenSourceAFIS algorithm has the following results:

- Approximately 10% of the time, the algorithm correctly indentified *all* matching prints in the
database (typically 3-4 images in the provided data).

- Approximately 35% of the time, the algorithm correctly identified at least one matching fingerprint.

- Very low rate false positives/negatives.

- Link to detailed results: https://docs.google.com/spreadsheets/d/1L8g8CDl8iLljO5X1tMXT8DqgZOMJk_8BOoZEbRV6pGQ/edit?usp=sharing

- The same issues exist as seen in 1:1 matching. 

#### Performance on Clean Images

Example of a "clean" input image:

![clean_image](./png/1_1.png)

![clean_image2](./CleanImageExp.jpg)

The following is the performance of the algorithm on clean input images:

![Confusion_Matrix2](./Confusion_Matrix2.png)

![Performance_Metrics2](./Performance_Metrics2.png)

- Given clean input data, the software performs very well.
