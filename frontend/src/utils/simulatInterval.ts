const simulatInterval = (fn: Function, delay: number, setTimeFn: Function) => {
  let timer: any = null;
  function inside() {
    // console.log(new Date().toLocaleString());
    clearTimeout(timer);
    fn();
    timer = window.setTimeout(inside, delay);
    setTimeFn(timer);
  }
  timer = window.setTimeout(inside, delay);
  return {
    clear() {
      clearTimeout(timer);
    },
  };
};

export default simulatInterval;
