import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DataPoint {
    private double sepalLength;
    private double sepalWidth;
    private double petalLength;
    private double petalWidth;
    private DataPointClassification classification;


    private DataPoint(double sepalLength, double sepalWidth, double petalLength, double petalWidth, DataPointClassification classification) {
        this.sepalLength = sepalLength;
        this.sepalWidth = sepalWidth;
        this.petalLength = petalLength;
        this.petalWidth = petalWidth;
        this.classification = classification;
    }

    public double getSepalLength() {
        return sepalLength;
    }

    public double getSepalWidth() {
        return sepalWidth;
    }

    public double getPetalLength() {
        return petalLength;
    }

    public double getPetalWidth() {
        return petalWidth;
    }

    public DataPointClassification getClassification() {
        return classification;
    }

    public void setClassification(DataPointClassification classification) {
        this.classification = classification;
    }

    public static DataPoint fromString(String line) {
        String[] data = line.split("[ ]+");
        if(data.length < 5) {
            throw new RuntimeException("Invalid data");
        }
        try {
            return new DataPoint(Double.parseDouble(data[0]),
                    Double.parseDouble(data[1]),
                    Double.parseDouble(data[2]),
                    Double.parseDouble(data[3]),
                    DataPointClassification.fromString(data[4]));
        } catch(NumberFormatException e) {
            throw new RuntimeException("Invalid data");
        }
    }

    public static DataPoint[] parseFile(String filePath) {
        List<DataPoint> build = new ArrayList<>();
        try {
            Scanner s = new Scanner(new File(filePath));
            while(s.hasNextLine()) {
                String line = s.nextLine();
                if(line.equals("")) {
                    continue;
                }
                build.add(fromString(line.trim()));
            }
        } catch(FileNotFoundException e) {
            throw new RuntimeException("Could not find file");
        }
        DataPoint[] dataPoints = new DataPoint[build.size()];
        build.toArray(dataPoints);
        return dataPoints;
    }

    @Override
    public String toString() {
        return "DataPoint{" +
                "sepalLength=" + sepalLength +
                ", sepalWidth=" + sepalWidth +
                ", petalLength=" + petalLength +
                ", petalWidth=" + petalWidth +
                ", classification=" + classification +
                '}';
    }

    private double square(double number) {
        return Math.pow(number, 2);
    }

    /**
     * Find the euclidean distance from the parameter dp
     * @param dp the datapoint to compare this one to
     * @return
     */
    public double findDistance(DataPoint dp) {
        return Math.sqrt(square(dp.petalLength - petalLength) + square(dp.petalWidth - petalWidth) + square(dp.sepalWidth - sepalWidth) + square(dp.sepalLength - sepalLength));
    }
}
