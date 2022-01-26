export enum TimerState {
  ready,
  timing,
  suspend
}

export class Timer {
  private startTime: number = 0
  private passedTime: number = 0
  public timerState: TimerState = TimerState.ready

  public start() {
    if (this.timerState !== TimerState.ready) {
      // todo Tip error and inform developers using the right method
      return
    }
    this.startTime = new Date().getTime()
    this.timerState = TimerState.timing
  }

  public pause() {
    if (this.timerState !== TimerState.timing) {
      // todo Tip error and inform developers using the right method
      return
    }
    this.passedTime += new Date().getTime() - this.startTime
    this.timerState = TimerState.suspend
  }

  public resume() {
    if (this.timerState !== TimerState.suspend) {
      // todo Tip error and inform developers using the right method
      return
    }
    this.startTime = new Date().getTime()
    this.timerState = TimerState.timing
  }

  public reset() {
    this.startTime = 0
    this.passedTime = 0
    this.timerState = TimerState.ready
  }

  public getPassedTime(): number {
    switch (this.timerState) {
      case TimerState.ready: {
        return 0;
      }
      case TimerState.suspend: {
        return this.passedTime
      }
      case TimerState.timing: {
        return this.passedTime + new Date().getTime() - this.startTime
      }
    }
  }
}