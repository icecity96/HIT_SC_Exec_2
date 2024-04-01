import java.util.Map;

public interface ParkingField {
    /**
     * 创建一个新的停车场实例。
     * <p>
     * 本方法根据提供的停车位编号和相应的宽度信息，初始化一个停车场对象。停车场中的每个停车位将根据提供的
     * 编号和宽度进行设置，且初始时，所有车位均为空（即没有车辆停放）。
     *
     * @param nos    一个整型数组，表示停车位的唯一编号。这些编号必须是自然数（正整数），且在数组中不能重复。
     * @param widths 一个整型数组，表示对应编号停车位的宽度（以某个单位表示，例如米）。
     *               该数组中的元素数量必须与`nos`数组的元素数量完全相同。每个宽度值必须大于或等于5（包括5）。
     * @return ParkingField 返回一个新初始化的ParkingField对象，该对象包含与`widths`数组长度相同的停车位数量。
     * 每个停车位的宽度与`nos`数组中相对应的编号的宽度一致。
     * @throws IllegalArgumentException 如果输入参数`nos`和`widths`的长度不相等，或者如果`nos`数组中包含重复的编号，
     *                                  或者`widths`数组中的任何宽度小于5，则抛出此异常。
     * @throws NullPointerException     如果`nos`或`widths`为null，则抛出此异常。
     */
    public static ParkingField create(int[] nos, int[] widths) throws Exception {
        return new ConcreteParkingField(nos, widths);
    }

    /**
     * 创建一个新的停车场对象。
     * <p>
     * 本方法接受一个Map作为输入，该Map的Key为车位的唯一编号（自然数），值为对应车位的宽度（自然数）。
     * 使用这些信息，方法初始化一个停车场对象，其中包含的车位数量与输入映射的大小相同。
     * 每个车位的编号和宽度将根据映射中的键值对设置，并保证在初始化时所有车位都是空的（即没有车辆停放）。
     *
     * @param lots 一个Map，其中的键（Integer类型）代表车位的编号，值（Integer类型）代表相应车位的宽度。
     *             所有的编号都应为正整数，所有的宽度也都应为正整数。`lots`的大小必须大于或等于5。
     * @return ParkingField 返回一个新初始化的ParkingField对象。该对象根据输入的映射信息包含该相应数量的车位，
     * 每个车位的编号和宽度与输入映射中的对应键值对一致。
     * @throws IllegalArgumentException 如果输入的映射`lots`的大小小于等于5，或者映射中的任何键（车位编号）或值（车位宽度）
     *                                  不是正整数，则抛出此异常。
     * @throws NullPointerException     如果`lots`为null，则抛出此异常。
     */
    public static ParkingField create(Map<Integer, Integer> lots) throws Exception {
        return new ConcreteParkingField(lots);
    }

    /**
     * 将指定车辆停放在停车场的特定停车位上。
     * <p>
     * 此方法尝试将一个具有特定车牌号的车辆停放在指定编号的停车位上。为了成功停车，该停车位必须满足以下条件：
     *  未被其他车辆占用、宽度大于车辆的宽度，且编号有效存在于停车场中。
     *  同时，车辆在停车前不应已经停在停车场中的任何车位上。
     *
     * @param plate 要停进来的车辆的车牌号。此参数不能为空（not null）。
     * @param width 车辆的宽度，必须是自然数（正整数）。
     * @param num 指定的停车位编号，必须是自然数（正整数）。
     *
     * @throws IllegalArgumentException 如果满足以下任一条件:
     *      - `plate`为空，
     *      - `width`不是正整数，
     *      - `num`不是正整数，
     *      - `num`并不是一个合法的车位编号。
     *  @throws IllegalStateException 如果满足以下任一条件:
     *      - `plate`对应的车辆已经停在该停车场中的任意车位上，
     *      - `num`对应的停车位已被其他车辆占用，
     *      - `num`对应的停车位的宽度不超过`width`。
     *
     * @apiNote 应当确保在调用此方法前，`plate`、`width`、和`num`的有效性已被验证。
     */
    public void parking(String plate, int width, int num) throws Exception;

    /**
     * 在停车场中为车辆自动分配一个空闲的停车位。
     * <p>
     * 此方法尝试为具有特定车牌号和宽度的车辆自动寻找并分配一个空闲的停车位。
     * 只有当车辆之前未停在停车场中，且存在至少一个空闲的停车位其宽度大于车辆宽度时，
     * 操作才会成功。成功执行后，车辆将被分配到满足条件的一个空闲停车位上，而其他车位的状态保持不变。
     *
     * @param plate 要停进来的车辆的车牌号，不能为空（not null）且不为空字符串。
     * @param width 车辆的宽度，以某个单位（如米）表示。必须是正整数，表示车辆所需的最小车位宽度。
     *
     * @throws IllegalArgumentException 如果满足以下任一条件:
     *      - `plate`为空（null或空字符串）
     *      - `width`不是正整数。
     * @throws IllegalStateException 如果满足以下任一条件:
     *      - `plate`对应的车辆已经停在停车场中，
     *      - 停车场中没有足够宽的空闲车位可供该车辆停放。
     *
     * @apiNote 在调用此方法之前，应确保`plate`和`width`的有效性。
     */
    public void parking(String plate, int width) throws Exception; // mutator, 不指定车位, 随机选择车位

    /**
     * 处理车辆驶离停车场的操作，并计算本次停车的费用。
     * <p>
     * 此方法根据车辆的车牌号码，执行车辆驶离停车场的操作。车辆驶离后，原来占用的车位将被标记为空闲。基于车辆在停车场停留的时间，本方法还会计算并返回停车费用。计费规则为：每半小时收费10元，不足半小时的部分按半小时计算。
     *
     * @param plate 待驶离车辆的车牌号，必须为非空字符串。
     * @return double 本次停车的费用，为精确计算的结果。
     *
     * @throws IllegalArgumentException 如果`plate`为空字符串或null。
     * @throws IllegalStateException 如果车牌号为`plate`的车辆未停在本停车场中。
     *
     * @apiNote 假定每个车辆的停车时间已被跟踪和记录，此方法在计算费用时将使用这些信息。
     */
    public double depart(String plate) throws Exception;

    /**
     * 获取停车场中每个车位的当前状态。
     *
     * 此方法返回一个映射，该映射的键为停车场中每个车位的编号，值为对应车位上的车辆车牌号。
     * 如果某个车位当前没有车辆停放，则该车位的值为一个空字符串。这允许调用者快速了解哪些
     * 车位被占用以及每个被占用车位上停放的车辆车牌号。
     *
     * @return Map<Integer, String> 一个映射，其中键（Integer类型）表示车位编号，
     *                              值（String类型）表示该车位上车辆的车牌号。
     *                              如果车位空闲，则值为空字符串（""）。
     *
     * @apiNote 此方法不抛出任何异常，总是返回当前停车场的状态，即使停车场为空（此时返回一个空映射）。
     */
    public Map<Integer, String> status();
}
