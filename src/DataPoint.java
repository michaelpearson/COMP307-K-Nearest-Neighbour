public class DataPoint {
    private int sepalLength;
    private int sepalWidth;
    private int petalLength;
    private int petalWidth;
    private DataPointClassification classification;


    public DataPoint(int sepalLength, int sepalWidth, int petalLength, int petalWidth) {
        this.sepalLength = sepalLength;
        this.sepalWidth = sepalWidth;
        this.petalLength = petalLength;
        this.petalWidth = petalWidth;
    }

    public int getSepalLength() {
        return sepalLength;
    }

    public int getSepalWidth() {
        return sepalWidth;
    }

    public int getPetalLength() {
        return petalLength;
    }

    public int getPetalWidth() {
        return petalWidth;
    }

    public DataPointClassification getClassification() {
        return classification;
    }

    public void setClassification(DataPointClassification classification) {
        this.classification = classification;
    }
}
