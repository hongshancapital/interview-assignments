/**
 * @param {number} targetCount 不小于1的整数，表示经过targetCount帧之后返回结果
 * @return {Promise<number>}
 */
const getScreenFps = (() => {
  // 先做一下兼容性处理
  const nextFrame = [window.requestAnimationFrame].find((fn) => fn);
  if (!nextFrame) {
    console.error("requestAnimationFrame is not supported!");
    return;
  }
  return (targetCount = 50) => {
    // 判断参数是否合规
    if (targetCount < 1) throw new Error("targetCount cannot be less than 1.");
    const beginDate = Date.now();
    let count = 0;
    return new Promise((resolve) => {
      (function log() {
        nextFrame(() => {
          if (++count >= targetCount) {
            const diffDate = Date.now() - beginDate;
            const fps = (count / diffDate) * 1000;
            return resolve(fps);
          }
          log();
        });
      })();
    });
  };
})();

export default getScreenFps;
