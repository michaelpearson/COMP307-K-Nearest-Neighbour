import java.util.HashMap;
import java.util.Map;

public class Classifier {

    public static void main(String[] args) {
        DataPoint[] trainingSet = DataPoint.parseFile(args[0]);
        DataPoint[] testSet = DataPoint.parseFile(args[1]);
        int correctCount = 0;
        int incorrectCount = 0;


        for(DataPoint dp : testSet) {
            DataPointClassification classification = classify(trainingSet, dp, 5);
            boolean correct = classification == dp.getClassification();
            System.out.printf("%s -> classifies as: %s, which is %s\n", dp.toString(), classification.toString(), correct ? "Correct" : "Incorrect");
            if(correct) {
                correctCount++;
            } else {
                incorrectCount++;
            }
        }
        System.out.printf("The system got %d correct and %d incorrect which is %2.2f%% correct ", correctCount, incorrectCount, 100 * ((double)correctCount / (incorrectCount + correctCount)));

    }


    private static DataPointClassification classify(DataPoint[] trainingSet, DataPoint dataPoint, int k) {
        BestResults results = new BestResults(k);
        for(DataPoint p : trainingSet) {
            results.insertResult(new DataPointWithMetric(p, p.findDistance(dataPoint)));
        }
        return results.getClassification();
    }

    private static class BestResults {
        private DataPointWithMetric bestDataPoints[];
        private int worstIndex = 0;

        public BestResults(int k) {
            bestDataPoints = new DataPointWithMetric[k];
        }

        public void insertResult(DataPointWithMetric dp) {
            DataPointWithMetric worst = bestDataPoints[worstIndex];
            if(worst == null || bestDataPoints[worstIndex].getDistance() > dp.getDistance()) {
                bestDataPoints[worstIndex] = dp;
                for(int a = 0;a < bestDataPoints.length; a++) {
                    if (bestDataPoints[a] == null) {
                        worstIndex = a;
                        return;
                    }
                }
                for(int a = 0;a < bestDataPoints.length; a++) {
                    if(bestDataPoints[a].getDistance() > bestDataPoints[worstIndex].getDistance()) {
                        worstIndex = a;
                    }
                }
            }
        }

        public DataPointClassification getClassification() {
            Map<DataPointClassification, Integer> votes = new HashMap<>();
            for(DataPointClassification c : DataPointClassification.values()) {
                votes.put(c, 0);
            }
            for(DataPointWithMetric dp : bestDataPoints) {
                if(dp == null) {
                    throw new RuntimeException("Must have more then k datapoints");
                }
                votes.put(dp.getDataPoint().getClassification(), votes.get(dp.getDataPoint().getClassification()) + 1);
            }
            DataPointClassification bestClassification = null;
            int bestClassificationVotes = 0;
            for(Map.Entry<DataPointClassification, Integer> e : votes.entrySet()) {
                if(bestClassification == null || bestClassificationVotes < e.getValue()) {
                    bestClassification = e.getKey();
                    bestClassificationVotes = e.getValue();
                }
            }
            return bestClassification;
        }
    }
    private static class DataPointWithMetric {
        private DataPoint dataPoint;
        private double distance;

        public double getDistance() {
            return distance;
        }

        public DataPoint getDataPoint() {
            return dataPoint;
        }

        public DataPointWithMetric(DataPoint dataPoint, double distance) {
            this.dataPoint = dataPoint;
            this.distance = distance;
        }
    }
}
