import java.util.Map;

public class ConscreteParkingField implements ParkingField{
    /**
     * @param plate
     * @param width
     * @param num
     */
    @Override
    public void parking(String plate, int width, int num) {

    }

    /**
     * @param plate
     * @param width
     */
    @Override
    public void parking(String plate, int width) {

    }

    /**
     * @param plate
     * @return
     */
    @Override
    public double depart(String plate) {
        return 0;
    }

    /**
     * @return
     */
    @Override
    public Map<Integer, String> status() {
        return null;
    }
}
