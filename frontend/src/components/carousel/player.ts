/**
 * 模拟 html video 元素的状态
 * 用于控制自动播放 carousel 时每一页的进度效果
 */
export class Player extends EventTarget {
  private _raf: number | null = null;
  private _currentTime = 0;
  private _paused = true;

  constructor(public duration: number, _id: number) {
    super();
  }

  get paused() {
    return this._paused;
  }
  set paused(val: boolean) {
    if (val) {
      this.pause();
    } else {
      this.play();
    }
  }

  get currentTime() {
    return this._currentTime;
  }
  set currentTime(time: number) {
    this._currentTime = Math.max(0, Math.min(this.duration, time));
  }

  public pause() {
    this._paused = true;

    if (this._raf) {
      cancelAnimationFrame(this._raf);
    }
  }

  play() {
    if (!this.paused) {
      return;
    }

    let start = performance.now();

    let nextFrame = () =>
      requestAnimationFrame(() => {
        const now = performance.now();

        if (!this.paused) {
          this.currentTime += now - start;

          if (this.currentTime < this.duration) {
            start = now;
            this._raf = nextFrame();
            // TODO 可以节流下
            this.dispatchEvent(new Event("timeupdate"));
          } else {
            this.pause();
            this.dispatchEvent(new Event("ended"));
          }
        }
      });

    this._paused = false;
    this._raf = nextFrame();
  }
}
