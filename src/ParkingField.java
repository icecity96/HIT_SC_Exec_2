import java.util.Map;

public interface ParkingField {
    public static ParkingField create(){return null;} // 静态工厂方法，construction

    public void parking(String plate, int width, int num); // mutator, 指定车位

    public void parking(String plate, int width); // mutator, 不指定车位, 随机选择车位

    public double depart(String plate); // mutator, 离场计费

    public Map<Integer, String> status(); // observer
}
