const raf =
  window.requestAnimationFrame ||
  function (callback) {
    window.setTimeout(callback, 1000 / 60);
  };

export  const cancelRaf = window.cancelAnimationFrame || window.clearTimeout;

export const optimizedPoll = function (timeout: number, callback: Function) {
  let lastCallTime = Date.now();
  let timerId = -1;
  function rafCallback() {
    const nowTime = Date.now();
    if (nowTime - lastCallTime >= timeout) {
      callback();
      lastCallTime = Date.now();
    }
    timerId = raf(rafCallback);
  }
  //   初次执行
  timerId = raf(rafCallback);
  return () => cancelRaf(timerId);
};
