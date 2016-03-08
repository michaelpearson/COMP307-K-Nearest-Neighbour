public enum DataPointClassification {
    IrisSetosa,
    IrisVersicolour,
    IrisVirginica,
    UNKNOWN;
    public static DataPointClassification fromString(String type) {
        switch(type.toLowerCase()) {
            default:
                return UNKNOWN;
            case "iris setosa":
            case "iris-setosa":
                return IrisSetosa;
            case "iris versicolour":
            case "iris-versicolour":
            case "iris-versicolor":
                return IrisVersicolour;
            case "iris virginica":
            case "iris-virginica":
                return IrisVirginica;
        }
    }
}
