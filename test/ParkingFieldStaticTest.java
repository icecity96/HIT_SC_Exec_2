import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * ParkingField.create(Map<Integer, Integer> lots) 方法的测试策略。
 * <p>
 * 测试策略基于以下组合情况覆盖各种场景：
 * 1. 车位数量：
 *    - 使用0个车位测试，确保对空映射输入的异常处理。
 *    - 使用少于5个车位测试，确保对车位不足的异常处理。
 *    - 使用正好5个车位测试，验证方法的功能性。
 *    - 使用超过5个车位测试，验证方法的功能性。
 * <p>
 * 2. 车位编号（映射中的键）：
 *    - 使用全部自然数测试，确保正常执行。
 *    - 包含非自然数（零、负数）测试，确保异常处理。
 * <p>
 * 3. 车位宽度（映射中的值）：
 *    - 使用全部自然数测试，确保正常执行。
 *    - 包含零或负数测试，确保异常处理。
 * <p>
 * 4. 特殊情况：
 *    - 测试所有车位宽度相同的情况。
 *    - 测试车位宽度不同的情况。
 * <p>
 * 5. 特殊输入：
 *    - 测试输入映射为null的情况，确保抛出NullPointerException。
 * <p>
 * 测试设计至少覆盖每个值场景一次，对车位编号和宽度的维度考虑了笛卡尔积全覆盖，特别是对有效和无效输入组合的情况。
 */
class ParkingFieldStaticTest {

    // 测试用例1：lots为空，期望抛出IllegalArgumentException
    @Test
    void testCreateWithEmptyLots() {
        Map<Integer, Integer> lots = new HashMap<>();
        assertThrows(IllegalArgumentException.class, () -> {
            ParkingField.create(lots);
        });
    }

    // 测试用例2：lots大小小于5，期望抛出IllegalArgumentException
    @Test
    void testCreateWithLessThanFiveLots() {
        Map<Integer, Integer> lots = new HashMap<>();
        lots.put(1, 10);
        lots.put(2, 12);
        lots.put(3, 15);
        lots.put(4, 20);
        assertThrows(IllegalArgumentException.class, () -> {
            ParkingField.create(lots);
        });
    }

    // 测试用例3：lots大小等于5，期望成功创建对象
    @Test
    void testCreateWithFiveLots() throws Exception {
        Map<Integer, Integer> lots = new HashMap<>();
        for (int i = 1; i <= 5; i++) {
            lots.put(i, 10 + i);
        }
        assertNotNull(ParkingField.create(lots));
    }

    // 测试用例4：lots大小大于5，期望成功创建对象
    @Test
    void testCreateWithMoreThanFiveLots() throws Exception{
        Map<Integer, Integer> lots = new HashMap<>();
        for (int i = 1; i <= 6; i++) {
            lots.put(i, 10 + i);
        }
        assertNotNull(ParkingField.create(lots));
    }

    // 测试用例5：lots包含非自然数的车位编号，期望抛出IllegalArgumentException
    @Test
    void testCreateWithNonNaturalNumberLotID() {
        Map<Integer, Integer> lots = new HashMap<>();
        lots.put(-1, 10);
        lots.put(2, 12);
        assertThrows(IllegalArgumentException.class, () -> {
            ParkingField.create(lots);
        });
    }

    // 测试用例7：lots包含非自然数的宽度，期望抛出IllegalArgumentException
    @Test
    void testCreateWithNonNaturalNumberWidth() {
        Map<Integer, Integer> lots = new HashMap<>();
        lots.put(1, -10);
        lots.put(2, 12);
        assertThrows(IllegalArgumentException.class, () -> {
            ParkingField.create(lots);
        });
    }

    // 测试用例10：lots为null，期望抛出NullPointerException
    @Test
    void testCreateWithNullLots() {
        assertThrows(NullPointerException.class, () -> {
            ParkingField.create(null);
        });
    }

}
