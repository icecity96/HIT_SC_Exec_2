import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 测试策略
 * <p>
 * 按照`plate`（车牌号）划分：
 * 1. 该车已经停在该停车场：尝试将一辆已经在停车场中的车辆再次停入，预期抛出IllegalStateException。
 * 2. 该车未在停车场：尝试将一辆不在停车场中的车辆停入，进一步按照下列条件测试。
 * <p>
 * 按照`num`（车位编号）划分：
 * 1. 该车位是车场的合法车位：车位编号在停车场的范围内，进一步按照下列条件测试。
 * 2. 不是合法车位：车位编号超出停车场范围，预期抛出IllegalArgumentException。
 * <p>
 * 按照车位占用情况划分：
 * 1. 该车位合法，已被其他车占用：尝试将车辆停到已经有车的车位上，预期抛出IllegalStateException。
 * 2. 该车位合法，未被占用：进一步按照车位宽度条件测试。
 * <p>
 * 按照`num`和`width`（车辆宽度）划分：
 * 1. 车位宽度不超过车辆宽度：尝试将车辆停入宽度不足以容纳该车辆的车位上，预期抛出IllegalStateException。
 * 2. 车位宽度等于车辆宽度：尝试将车辆停入宽度刚好与车辆宽度相等的车位上，预期成功停车。
 * 3. 车位宽度大于车辆宽度：尝试将车辆停入宽度超过车辆宽度的车位上，预期成功停车。
 * <p>
 * 特殊情况测试：
 * 1. `plate`为空：预期抛出IllegalArgumentException。
 * 2. `width`不是正整数：预期抛出IllegalArgumentException。
 * 3. `num`不是正整数：预期抛出IllegalArgumentException。
 *
 */
class ParkingFieldInstanceTest {
    private ParkingField parkingField;

    @BeforeEach
    void setUp() {
        // 假设停车场有5个车位，编号1到5，宽度依次为10, 15, 20, 20, 20。
        Map<Integer, Integer> lots = new HashMap<>();
        lots.put(1, 2);
        lots.put(2, 2);
        lots.put(3, 3);
        lots.put(4, 3);
        lots.put(5, 4);
        try {
            parkingField = ParkingField.create(lots);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // 覆盖：该车已经停在该停车场
    @Test
    void testParkingWithCarAlreadyParked() throws Exception {
        String plate = "ABC123";
        parkingField.parking(plate, 2, 1); // 假设该车辆已成功停入1号位
        assertThrows(IllegalStateException.class, () -> parkingField.parking(plate, 2, 2),
                "Should throw IllegalStateException if the car is already parked in the parking field.");
    }

    // 覆盖：不是合法车位
    @Test
    void testParkingInNonexistentLot() {
        String plate = "XYZ789";
        assertThrows(IllegalArgumentException.class, () -> parkingField.parking(plate, 2, 99),
                "Should throw IllegalArgumentException for a non-existent parking lot number.");
    }

    // 覆盖：该车位合法，已被其他车占用
    @Test
    void testParkingInOccupiedLot() throws Exception {
        parkingField.parking("CAR456", 2, 3); // 假设3号车位已被CAR456占用
        assertThrows(IllegalStateException.class, () -> parkingField.parking("NEW789", 2, 3),
                "Should throw IllegalStateException if the parking lot is already occupied.");
    }

    // 覆盖：车位宽度不超过车辆宽度
    @Test
    void testParkingInLotWithInsufficientWidth() {
        assertThrows(IllegalStateException.class, () -> parkingField.parking("BIG999", 4, 1),
                "Should throw IllegalStateException if the lot's width is insufficient for the car.");
    }

    // 覆盖：车位宽度等于车辆宽度
    @Test
    void testParkingInLotWithExactWidth() {
        assertDoesNotThrow(() -> parkingField.parking("PERFECT1", 2, 2),
                "Parking should succeed when the lot's width exactly matches the car's width.");
    }

    // 覆盖：车位宽度大于车辆宽度
    @Test
    void testParkingInLotWithMoreThanEnoughWidth() {
        assertDoesNotThrow(() -> parkingField.parking("SMALL100", 2, 5),
                "Parking should succeed when the lot's width is more than enough for the car.");
    }

    // 特殊情况测试：`plate`为空
    @Test
    void testParkingWithInvalidPlate() {
        assertThrows(IllegalArgumentException.class, () -> parkingField.parking(null, 2, 2),
                "Should throw IllegalArgumentException if the plate is null.");
    }

    // 特殊情况测试：`width`不是正整数
    @Test
    void testParkingWithInvalidWidth() {
        assertThrows(IllegalArgumentException.class, () -> parkingField.parking("TEST123", 0, 2),
                "Should throw IllegalArgumentException if the width is not a positive integer.");
    }

    // 特殊情况测试：`num`不是正整数
    @Test
    void testParkingWithInvalidLotNumber() {
        assertThrows(IllegalArgumentException.class, () -> parkingField.parking("TEST456", 2, -1),
                "Should throw IllegalArgumentException if the lot number is not a positive integer.");
    }

    @Test
    void parking() {
    }
}