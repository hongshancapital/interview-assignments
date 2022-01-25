/**
 * The timer
 *   + Support tick callback (does not accumulated if the browser is stuck)
 */
export default class Timer {
  // the timer option
  option: TimerOption;
  // the setTimeout handle
  timerHandle?: number;

  /**
   * Create the timer
   * @param {TimerOption} option  the timer configuration
   */
  constructor(option: TimerOption) {
    const interval = option?.interval;
    if (isNaN(interval) || interval <= 0) {
      throw new Error(`Invalid timer interval: ${interval}`);
    }
    this.option = option;
    const {
      autoStart = true,
    } = option;
    if (autoStart) {
      this.start(true);
    }
  }

  /**
   * Start the timer
   * @param {boolean} [tickCallback]  whether trigger onTick immediately (default: false)
   */
  public start(tickCallback?: boolean): void {
    const {
      timerHandle,
    } = this;
    if (timerHandle) {
      // the timer is already started, so start again has no effect
      return;
    }
    this.tick(tickCallback);
  }

  /**
   * runs every interval
   * @param {boolean} [tickCallback]  whether trigger onTick (default: false)
   */
  private tick(tickCallback?: boolean): void {
    const {
      option,
    } = this;
    const {
      interval,
      onTick,
    } = option;
    this.stop();
    if (tickCallback && onTick) {
      onTick();
    }
    // the timeout length is calculated by interval - last time exceeded
    this.timerHandle = window.setTimeout(this.tick.bind(this, true), interval);
  }

  /**
   * Stop the timer
   */
  public stop(): void {
    clearTimeout(this.timerHandle);
    this.timerHandle = undefined;
  }
};
