package bridge;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 다리 건너기 게임을 관리하는 클래스
 */
public class BridgeGame {

    private final List<String> bridge;
    private final List<String> moving = new ArrayList<>();
    private int count = 0;

    public BridgeGame(int size) {
        validateSize(size);
        bridge = new BridgeMaker(new BridgeRandomNumberGenerator()).makeBridge(size);
    }

    /**
     * 사용자가 칸을 이동할 때 사용하는 메서드
     * <p>
     * 이동을 위해 필요한 메서드의 반환 타입(return type), 인자(parameter)는 자유롭게 추가하거나 변경할 수 있다.
     */
    public void move(String input) {
        validateMoving(input);
        moving.add(input);
    }

    /**
     * 사용자가 게임을 다시 시도할 때 사용하는 메서드
     * <p>
     * 재시작을 위해 필요한 메서드의 반환 타입(return type), 인자(parameter)는 자유롭게 추가하거나 변경할 수 있다.
     */
    public void retry() {
        moving.clear();
        count++;
    }

    public String makeSymbol(int index, String position) {
        if (moving.get(index).equals(position)) {
            if (moving.get(index).equals(bridge.get(index))) {
                return BridgeConstant.SUCCESS;
            }
            return BridgeConstant.FAIL;
        }
        return BridgeConstant.NOTHING;
    }

    private List<String> makeRow(String position) {
        return IntStream.range(0, moving.size())
                .mapToObj(index -> makeSymbol(index, position))
                .collect(Collectors.toList());
    }

    public List<List<String>> makeBridgeResult() {
        List<List<String>> map = new ArrayList<>();

        map.add(makeRow(BridgeConstant.UP));
        map.add(makeRow(BridgeConstant.DOWN));

        return map;
    }

    public int getCount() {
        return count;
    }

    private void validateSize(int size) {
        if (size < BridgeConstant.MIN_LENGTH || BridgeConstant.MAX_LENGTH < size) {
            throw new IllegalArgumentException(ExceptionConstant.INCORRECT_LENGTH.getMessage());
        }
    }

    private void validateMoving(String input) {
        if (!List.of(BridgeConstant.DOWN, BridgeConstant.UP).contains(input)) {
            throw new IllegalArgumentException(ExceptionConstant.INCORRECT_MOVING_INPUT.getMessage());
        }
    }
}
