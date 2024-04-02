import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

    @BeforeEach
    void setUp() {

    }

    @Test
    void parking() {
    }
}