const scrollXAnimation = (element: HTMLElement, targetX: number, time: number = 1) => {
  // 获取当前位置方法
  const initX = element.scrollLeft;
  // 计算需要移动的距离
  const needScrollX = Math.abs(targetX - initX);
  // 计算往左移动还是往右移动
  const isLeft = targetX - initX >= 0;
  // 计算每次滑动频次
  const dist = Math.round(needScrollX / (time * 60)); 
  
  let currentX = 0;
  let timer: number = 0;

  const move = () => {
    if (currentX < needScrollX) {
      // 一次调用滑动帧数，每次调用会不一样
      currentX + dist > needScrollX ? currentX = needScrollX : currentX += dist;
      if (isLeft) {
        element.scrollLeft = initX + currentX;
      } else {
        element.scrollLeft = initX - currentX;
      }
      timer = requestAnimationFrame(move);
    } else {
      cancelAnimationFrame(timer);
    }
  };

  move();
}

export default { scrollXAnimation };