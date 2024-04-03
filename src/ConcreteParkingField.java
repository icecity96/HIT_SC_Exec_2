import java.util.*;

public class ConcreteParkingField implements ParkingField{
    // Rep
    private final List<Lot> lots = new ArrayList<>(); // 一组车位
    private final Map<Lot, Car> status = new HashMap<>(); // 占用情况
    private final List<Record> records = new ArrayList<>(); // 停车记录

    /**
     * Abstraction Function (AF):
     * AF(c) = 一个停车场，如果c.lots为空，则代表一个没有车位的停车场；否则，对于每个非空的Lot l ∈ c.lots，
     * 表示一个具有编号l.getNumber()和宽度l.getWidth()的车位。如果c.status包含l作为键，
     * 则该车位被c.status.get(l)表示的Car占用。c.records表示该停车场的所有停车记录，其中每个Record r
     * 描述了一次停车行为，包括车辆r.getCar()在时间r.getTimeIn()时停入车位r.getLot()，并在r.getTimeOut()时离开，
     * 该次停车花费了r.getFee()元。
     * <p>
     * Representation Invariant (RI):
     * - c.lots.size() >= 5 表示停车场至少有5个车位。
     * - c.lots.size() >= c.status.size() 确保车位数不少于已停车辆数。
     * - c.status中的每个键均为c.lots中的元素，保证每个已占用的车位都是有效车位。
     * - c.status中的值（Car对象）之间不重复，确保每辆车只占用一个车位。
     * - 对于c.status中的每个条目<key, value>，value（Car对象）的宽度不大于key（Lot对象）的宽度，保证车辆可以适合其车位。
     * - 对于c.records中的每个Record对象r，如果r.getTimeOut()为空，则必须有一个与之对应的条目<key, value>在c.status中，
     *   其中key为r.getLot()且value为r.getCar()，表示正在停车中的记录必须与当前占用状态一致。
     */

    /**
     * 检查表示不变量是否被保持。
     * 这个方法应该在构造器和修改内部状态的方法后被私有调用，以确保类的状态始终有效。
     */
    private void checkRep() {
        assert lots.size() >= 5 : "停车场至少应有5个车位。";
        assert lots.size() >= status.size() : "车位数应不少于已停车辆数。";

        // 检查status中的每个key是否在lots中
        for (Lot lot : status.keySet()) {
            assert lots.contains(lot) : "每个已占用的车位都应为有效车位。";
        }

        // 检查status中的车辆是否不重复
        Set<Car> cars = new HashSet<>(status.values());
        assert cars.size() == status.values().size() : "每辆车只能占用一个车位。";

        // 检查车辆宽度是否适合其车位
        for (Map.Entry<Lot, Car> entry : status.entrySet()) {
            assert entry.getKey().getWidth() >= entry.getValue().getWidth() : "车辆宽度应小于等于车位宽度。";
        }

        // 检查正在停车中的记录是否与当前占用状态一致
        for (Record record : records) {
            if (record.getTimeOut() == null) { // 表示车辆尚未离开
                assert status.containsKey(record.getLot()) && status.get(record.getLot()).equals(record.getCar()) :
                        "正在停车中的记录应与当前占用状态一致。";
            }
        }
    }

    public ConcreteParkingField(int[] nos, int[] widths) {
        // TODO
        checkRep();
    }

    /**
     * 创建一个新的停车场对象。
     *
     * @param lotsInfo 一个Map，其中的键（Integer类型）代表车位的编号，值（Integer类型）代表相应车位的宽度。
     * @throws IllegalArgumentException 如果输入的映射`lotsInfo`的大小小于5，或者映射中的任何键（车位编号）或值（车位宽度）
     *                                  不是正整数，则抛出此异常。
     * @throws NullPointerException     如果`lots`为null，则抛出此异常。
     */
    public ConcreteParkingField(Map<Integer, Integer> lotsInfo) {
        if (lotsInfo == null) {
            throw new NullPointerException("The input map `lotsInfo` cannot be null.");
        }
        if (lotsInfo.size() < 5) {
            throw new IllegalArgumentException("The size of `lotsInfo` must be at least 5.");
        }

        for (Map.Entry<Integer, Integer> entry : lotsInfo.entrySet()) {
            Integer lotNumber = entry.getKey();
            Integer width = entry.getValue();
            if (lotNumber == null || lotNumber <= 0 || width == null || width <= 0) {
                throw new IllegalArgumentException("Lot numbers and widths must be positive integers.");
            }
            // 创建Lot对象并添加到lots列表中
            lots.add(new Lot(lotNumber, width));
        }

        checkRep();
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
        // 检查参数有效性
        if (plate == null || plate.isEmpty() || width <= 0 || num <= 0) {
            throw new IllegalArgumentException("Invalid parameters.");
        }

        // 检查车位编号是否合法
        Optional<Lot> targetLot = lots.stream()
                .filter(lot -> lot.getNumber() == num)
                .findFirst();
        if (!targetLot.isPresent()) {
            throw new IllegalArgumentException("Parking lot number " + num + " is not a valid lot number.");
        }

        // 检查车位是否已被占用
        if (status.containsKey(targetLot.get())) {
            throw new IllegalStateException("Parking lot number " + num + " is already occupied.");
        }

        // 检查车位宽度是否适合
        if (targetLot.get().getWidth() < width) {
            throw new IllegalStateException("The car's width is too wide for the parking lot.");
        }

        // 检查车辆是否已在停车场中
        boolean isAlreadyParked = status.values().stream()
                .anyMatch(car -> car.getPlate().equals(plate));
        if (isAlreadyParked) {
            throw new IllegalStateException("The car with plate " + plate + " is already parked in the parking field.");
        }

        // 执行停车操作
        Car car = new Car(plate, width); // 假设Car类有一个合适的构造器
        status.put(targetLot.get(), car);

        // 记录停车记录
        Record record = new Record(car, targetLot.get()); // 假设Record类有一个合适的构造器
        records.add(record);

        checkRep();
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
        // TODO
        checkRep();
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
        // TODO
        checkRep();
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
        Map<Integer, String> parkingStatus = new HashMap<>();
        for (Lot lot : lots) {
            // 默认车位为空闲状态
            String plate = "";
            // 如果当前车位有车，则获取车牌号
            if (status.containsKey(lot)) {
                plate = status.get(lot).getPlate();
            }
            // 将车位编号和车牌号（或空字符串）放入返回的映射中
            parkingStatus.put(lot.getNumber(), plate);
        }
        return parkingStatus;
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
        return lots.size();
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
        for (Lot lot : lots) {
            if (lot.getNumber() == num && lot.getWidth() == width)
                return true;
        }

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
        return status.isEmpty();
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
        for (Lot lot : lots) {
            if (lot.getNumber() == num) {
                return lot.getWidth();
            }
        }
        throw new IllegalArgumentException("Invalid lot number: " + num);
    }

    /**
     * 返回停车场的当前状态的字符串表示。
     * <p>
     * 字符串包含以下信息：
     * - 停车场的总车位数
     * - 当前占用的车位百分比，四舍五入到最接近的整数
     * - 每个车位的编号、宽度和当前状态（被占用的车辆车牌号或表示空闲的"Free"）
     * <p>
     * 格式示例：
     * The parking field has total number of lots: 5
     * Now 60% lots are occupied
     * Lot 1 (200):   Car AB001
     * Lot 2 (180):   Free
     * Lot 3 (200):   Car CD002
     * Lot 4 (170):   Car EF003
     * Lot 5 (190):   Free
     * <p>
     * 此方法不抛出异常。
     *
     * @return 停车场状态的字符串表示，包括车位的使用情况和占用信息。
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        // 计算占用的车位数量
        int occupiedLots = (int) status.keySet().stream().filter(lot -> lots.contains(lot)).count();
        // 计算占用的百分比
        double occupiedPercentage = ((double) occupiedLots / lots.size()) * 100;

        sb.append("The parking field has total number of lots: ").append(lots.size()).append("\n");
        sb.append("Now ").append(String.format("%.0f%%", occupiedPercentage)).append(" lots are occupied").append("\n");

        for (Lot lot : lots) {
            sb.append("Lot ").append(lot.getNumber()).append(" (").append(lot.getWidth()).append("): \t");
            if (status.containsKey(lot)) {
                Car car = status.get(lot);
                sb.append("Car ").append(car.getPlate());
            } else {
                sb.append("Free");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
