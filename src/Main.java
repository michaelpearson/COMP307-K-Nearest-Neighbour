public class Main {

    public static void main(String[] args) {
        DataPoint[] trainingSet = DataPoint.parseFile(args[0]);
        DataPoint[] testSet = DataPoint.parseFile(args[1]);

        for(DataPoint dp : trainingSet) {
            System.out.println(dp);
        }
    }
}
