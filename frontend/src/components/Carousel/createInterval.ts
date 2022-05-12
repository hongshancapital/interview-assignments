const NOOP = setTimeout(() => {}, 0);

class Interval {
  intervalTimer: NodeJS.Timeout = NOOP;
  setInterval(callback: () => void, interval: number) {
    return (this.intervalTimer = setTimeout(() => {
      callback();
      this.intervalTimer = this.setInterval(callback, interval);
    }, interval));
  }
  clearInterval() {
    clearTimeout(this.intervalTimer);
  }
}

export function createInterval() {
  return new Interval();
}
