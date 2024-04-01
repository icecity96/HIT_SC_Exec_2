import java.util.Map;

public interface ParkingField {
    /**
     * 创建一个新的停车场实例。
     *
     * 本方法根据提供的停车位编号和相应的宽度信息，初始化一个停车场对象。停车场中的每个停车位将根据提供的
     * 编号和宽度进行设置，且初始时，所有车位均为空（即没有车辆停放）。
     *
     * @param nos 一个整型数组，表示停车位的唯一编号。这些编号必须是自然数（正整数），且在数组中不能重复。
     * @param widths 一个整型数组，表示对应编号停车位的宽度（以某个单位表示，例如米）。
     *               该数组中的元素数量必须与`nos`数组的元素数量完全相同。每个宽度值必须大于或等于5（包括5）。
     *
     * @return ParkingField 返回一个新初始化的ParkingField对象，该对象包含与`widths`数组长度相同的停车位数量。
     *                      每个停车位的宽度与`nos`数组中相对应的编号的宽度一致。
     *
     * @throws IllegalArgumentException 如果输入参数`nos`和`widths`的长度不相等，或者如果`nos`数组中包含重复的编号，
     *                                  或者`widths`数组中的任何宽度小于5，则抛出此异常。
     * @throws NullPointerException 如果`nos`或`widths`为null，则抛出此异常。
     */
    public static ParkingField create(int[] nos, int[] widths) throws Exception {
        return new ConcreteParkingField(nos, widths);
    }

    /**
     * 创建一个新的停车场对象。
     *
     * 本方法接受一个Map作为输入，该Map的Key为车位的唯一编号（自然数），值为对应车位的宽度（自然数）。
     * 使用这些信息，方法初始化一个停车场对象，其中包含的车位数量与输入映射的大小相同。
     * 每个车位的编号和宽度将根据映射中的键值对设置，并保证在初始化时所有车位都是空的（即没有车辆停放）。
     *
     * @param lots 一个Map，其中的键（Integer类型）代表车位的编号，值（Integer类型）代表相应车位的宽度。
     *             所有的编号都应为正整数，所有的宽度也都应为正整数。`lots`的大小必须大于或等于5。
     *
     * @return ParkingField 返回一个新初始化的ParkingField对象。该对象根据输入的映射信息包含相应数量`lots.size()`的车位，
     *                      每个车位的编号和宽度与输入映射中的对应键值对一致。
     *
     * @throws IllegalArgumentException 如果输入的映射`lots`的大小小于等于5，或者映射中的任何键（车位编号）或值（车位宽度）
     *                                  不是正整数，则抛出此异常。
     * @throws NullPointerException 如果`lots`为null，则抛出此异常。
     */
    public static ParkingField create(Map<Integer, Integer> lots) throws Exception {
        return new ConcreteParkingField(lots);
    }

    public void parking(String plate, int width, int num); // mutator, 指定车位

    public void parking(String plate, int width); // mutator, 不指定车位, 随机选择车位

    public double depart(String plate); // mutator, 离场计费

    public Map<Integer, String> status(); // observer
}
