import java.util.ArrayList;
import java.util.List;

public class Clustering {
    public static final int iterationCount = 200;

    public static void main(String[] argv) {
        DataPoint[] trainingSet = DataPoint.parseFile(argv[0]);
        DataPoint[] testSet = DataPoint.parseFile(argv[1]);
        int clusterCount = Integer.parseInt(argv[2]);
        DataPoint[] superSet = new DataPoint[trainingSet.length + testSet.length];
        System.arraycopy(trainingSet, 0, superSet, 0, trainingSet.length);
        System.arraycopy(testSet, 0, superSet, trainingSet.length, testSet.length);


        Cluster[] clusters = new Cluster[clusterCount];

        for(int a = 0;a < clusterCount;a++) {
            clusters[a] = Cluster.newRandomCluster(superSet[a]);
        }


        for(int a = 0;a < iterationCount; a++) {
            populateClusters(clusters, superSet);
            for (Cluster c : clusters) {
                c.moveToCenterOfMassAndReset();
            }
        }

        populateClusters(clusters, superSet);
        for(Cluster cluster : clusters) {
            System.out.printf("\n\n\n\nCluster has %d member(s)\n", cluster.members.size());
            for(DataPoint member : cluster.members) {
                System.out.printf("Type: %s\n\t", member.getClassification().toString());
            }
        }
    }

    private static void populateClusters(Cluster[] clusters, DataPoint[] dataPoints) {
        if(clusters.length == 0) {
            throw new RuntimeException("There must be at least one cluster");
        }
        for(DataPoint dp : dataPoints) {
            Double minDistance = null;
            Integer minIndex = null;
            for(int a = 0;a < clusters.length;a++) {
                double distance = clusters[a].findDistance(dp);
                if(minDistance == null || distance < minDistance) {
                    minDistance = distance;
                    minIndex = a;
                }
            }
            if(minIndex != null) {
                clusters[minIndex].addMember(dp);
            }
        }
    }

    private static class Cluster extends DataPoint {
        private List<DataPoint> members = new ArrayList<>();
        private static Cluster newRandomCluster(DataPoint dp) {
            Cluster c = new Cluster();
            c.sepalLength = dp.sepalLength;
            c.sepalWidth = dp.sepalWidth;
            c.petalLength = dp.petalLength;
            c.petalWidth = dp.petalWidth;
            return c;
        }
        public void addMember(DataPoint dataPoint) {
            members.add(dataPoint);
        }
        public void moveToCenterOfMassAndReset() {
            double sepalLengthTotal = 0;
            double sepalWidthTotal = 0;
            double petalLengthTotal = 0;
            double petalWidthTotal = 0;
            for(DataPoint member : members) {
                sepalLengthTotal += member.sepalLength;
                sepalWidthTotal += member.sepalWidth;
                petalLengthTotal += member.petalLength;
                petalWidthTotal += member.petalWidth;
            }
            sepalWidth = sepalWidthTotal / members.size();
            sepalLength = sepalLengthTotal / members.size();
            petalWidth = petalWidthTotal / members.size();
            petalLength = petalLengthTotal / members.size();
            members.clear();
        }
    }
}
