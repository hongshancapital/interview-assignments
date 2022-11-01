import throttle from "./throttle";

describe("测试 throttle", function () {
  beforeEach(() => {
    jest.useFakeTimers();
  });

  it("开始时执行一次，后面每隔 500ms 执行一次，中间执行 interval，节流函数不会响应", function () {
    const target = jest.fn();
    const fn = throttle(target, 500);
    let times = 0;
    const timer = setInterval(() => {
      if (times === 6) {
        clearInterval(timer);
      } else {
        fn();
        times++;
      }
    }, 250);
    jest.advanceTimersByTime(2000);
    expect(target).toBeCalledTimes(4);
  });
});
