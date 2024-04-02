import java.util.Map;

public class ConcreteParkingField implements ParkingField{
    public ConcreteParkingField(int[] nos, int[] widths) {
    }

    public ConcreteParkingField(Map<Integer, Integer> lots) {
    }

    /**
     * 将指定车辆停放在停车场的特定停车位上。
     * <p>
     * 此方法尝试将一个具有特定车牌号的车辆停放在指定编号的停车位上。为了成功停车，该停车位必须满足以下条件：
     * 未被其他车辆占用、宽度大于车辆的宽度，且编号有效存在于停车场中。
     * 同时，车辆在停车前不应已经停在停车场中的任何车位上。
     *
     * @param plate 要停进来的车辆的车牌号。此参数不能为空（not null）。
     * @param width 车辆的宽度，必须是自然数（正整数）。
     * @param num   指定的停车位编号，必须是自然数（正整数）。
     * @throws IllegalArgumentException 如果满足以下任一条件:
     *                                  - `plate`为空，
     *                                  - `width`不是正整数，
     *                                  - `num`不是正整数，
     *                                  - `num`并不是一个合法的车位编号。
     * @throws IllegalStateException    如果满足以下任一条件:
     *                                  - `plate`对应的车辆已经停在该停车场中的任意车位上，
     *                                  - `num`对应的停车位已被其他车辆占用，
     *                                  - `num`对应的停车位的宽度不超过`width`。
     * @apiNote 应当确保在调用此方法前，`plate`、`width`、和`num`的有效性已被验证。
     */
    @Override
    public void parking(String plate, int width, int num) throws Exception {

    }

    /**
     * 在停车场中为车辆自动分配一个空闲的停车位。
     * <p>
     * 此方法尝试为具有特定车牌号和宽度的车辆自动寻找并分配一个空闲的停车位。
     * 只有当车辆之前未停在停车场中，且存在至少一个空闲的停车位其宽度大于车辆宽度时，
     * 操作才会成功。成功执行后，车辆将被分配到满足条件的一个空闲停车位上，而其他车位的状态保持不变。
     *
     * @param plate 要停进来的车辆的车牌号，不能为空（not null）且不为空字符串。
     * @param width 车辆的宽度，以某个单位（如米）表示。必须是正整数，表示车辆所需的最小车位宽度。
     * @throws IllegalArgumentException 如果满足以下任一条件:
     *                                  - `plate`为空（null或空字符串）
     *                                  - `width`不是正整数。
     * @throws IllegalStateException    如果满足以下任一条件:
     *                                  - `plate`对应的车辆已经停在停车场中，
     *                                  - 停车场中没有足够宽的空闲车位可供该车辆停放。
     * @apiNote 在调用此方法之前，应确保`plate`和`width`的有效性。
     */
    @Override
    public void parking(String plate, int width) throws Exception {

    }

    /**
     * 处理车辆驶离停车场的操作，并计算本次停车的费用。
     * <p>
     * 此方法根据车辆的车牌号码，执行车辆驶离停车场的操作。车辆驶离后，原来占用的车位将被标记为空闲。基于车辆在停车场停留的时间，
     * 本方法还会计算并返回停车费用。计费规则为：每半小时收费10元，不足半小时的部分按半小时计算。
     *
     * @param plate 待驶离车辆的车牌号，必须为非空字符串。
     * @return double 本次停车的费用，为精确计算的结果。
     * @throws IllegalArgumentException 如果`plate`为空字符串或null。
     * @throws IllegalStateException    如果车牌号为`plate`的车辆未停在本停车场中。
     * @apiNote 假定每个车辆的停车时间已被跟踪和记录，此方法在计算费用时将使用这些信息。
     */
    @Override
    public double depart(String plate) throws Exception {
        return 0;
    }

    /**
     * 获取停车场中每个车位的当前状态。
     * <p>
     * 此方法返回一个映射，该映射的键为停车场中每个车位的编号，值为对应车位上的车辆车牌号。
     * 如果某个车位当前没有车辆停放，则该车位的值为一个空字符串。这允许调用者快速了解哪些
     * 车位被占用以及每个被占用车位上停放的车辆车牌号。
     *
     * @return Map<Integer, String> 一个映射，其中键（Integer类型）表示车位编号，
     * 值（String类型）表示该车位上车辆的车牌号。
     * 如果车位空闲，则值为空字符串（""）。
     * @apiNote 此方法不抛出任何异常，总是返回当前停车场的状态，即使停车场为空（此时返回一个空映射）。
     */
    @Override
    public Map<Integer, String> status() {
        return null;
    }

    /**
     * 返回当前停车场的总车位数量。
     * <p>
     * 此方法提供了一种快速获取停车场中车位总数的方式。它计算并返回停车场中所有车位的数量，
     * 包括被占用的和空闲的车位。
     *
     * @return int 表示停车场中总车位数的整数。此数值包括当前所有的车位，无论它们是否被占用。
     */
    @Override
    public int getNumberOfLots() {
        return 0;
    }

    /**
     * 检查停车场中是否存在具有指定编号和宽度的车位。
     * <p>
     * 此方法用于确定停车场中是否有一个车位，其编号和宽度分别匹配方法调用中提供的参数。
     *
     * @param num   车位的编号，为一个正整数。
     * @param width 车位的宽度，也为一个正整数。这表示车位所需满足的最小宽度。
     * @return boolean 返回true如果停车场中存在至少一个符合指定编号和宽度要求的车位；
     * 如果没有符合条件的车位，则返回false。
     */
    @Override
    public boolean isLotInParkingField(int num, int width) {
        return false;
    }

    /**
     * 检查停车场当前是否为空。
     * <p>
     * 此方法用于确定停车场是否没有任何车辆停放，即所有车位均为空闲状态。
     *
     * @return boolean 返回true如果停车场中没有任何车辆停放；
     * 如果至少有一个车位被占用，则返回false。
     */
    @Override
    public boolean isEmpty() {
        return false;
    }

    /**
     * 返回指定车位的宽度。
     * <p>
     * 此方法根据提供的车位号返回对应车位的宽度。车位号必须是有效的，
     * 即它对应一个存在于停车场中的车位。如果指定的车位号无效（即没有对应的车位存在），
     * 方法将抛出一个异常。
     *
     * @param num 车位号，必须对应一个有效且存在的车位。
     * @return 指定车位的宽度，以整数形式返回。
     * @throws IllegalArgumentException 如果是非法的车位号，
     *                                  即它不对应停车场中的任何现有车位。
     */
    @Override
    public int getLotWidth(int num) throws IllegalArgumentException {
        return 0;
    }
}
