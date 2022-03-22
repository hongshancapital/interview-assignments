export function createInterval() {
  return {
    intervalTimer: 0,
    setInterval(callback: () => void, interval: number) {
      let now = Date.now;
      let stime = now();
      let etime = stime;
      const loop = () => {
        this.intervalTimer = requestAnimationFrame(loop);
        etime = now();
        if (etime - stime >= interval) {
          stime = now();
          etime = stime;
          callback();
        }
      };
      this.intervalTimer = requestAnimationFrame(loop);
      return this.intervalTimer;
    },
    clearInterval() {
      cancelAnimationFrame(this.intervalTimer);
    },
  };
}
