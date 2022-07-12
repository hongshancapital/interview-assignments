interface TThrottleOptions {
  noTrailing: boolean;
  noLeading: boolean;
  debounceMode: boolean;
}

function throttle<T extends (...agms: any) => any>(
  delay: number,
  callback: T,
  options?: TThrottleOptions
) {
  const {
    noTrailing = false,
    noLeading = false,
    debounceMode = false,
  } = options || {};
  let timeoutID: any;
  let cancelled = false;

  let lastExec = 0;

  function clearExistingTimeout() {
    if (timeoutID) {
      clearTimeout(timeoutID);
    }
  }

  function cancel(options?: any) {
    const { upcomingOnly = false } = options || {};
    clearExistingTimeout();
    cancelled = !upcomingOnly;
  }

  function wrapper(this: any, ...arguments_: any[]) {
    let self = this;
    let elapsed = Date.now() - lastExec;

    if (cancelled) {
      return;
    }

    function exec() {
      lastExec = Date.now();
      callback.apply(self, arguments_);
    }

    function clear() {
      timeoutID = undefined;
    }

    if (!noLeading && debounceMode && !timeoutID) {
      exec();
    }

    clearExistingTimeout();

    if (debounceMode === undefined && elapsed > delay) {
      if (noLeading) {
        lastExec = Date.now();
        if (!noTrailing) {
          timeoutID = setTimeout(debounceMode ? clear : exec, delay);
        }
      } else {
        exec();
      }
    } else if (noTrailing !== true) {
      timeoutID = setTimeout(
        debounceMode ? clear : exec,
        debounceMode === undefined ? delay - elapsed : delay
      );
    }
  }

  wrapper.cancel = cancel;

  return wrapper;
}

export default throttle;
