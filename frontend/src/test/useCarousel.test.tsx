import { renderHook } from '@testing-library/react';
import useCarousel from '../Carousel/hooks/useCarousel';
import items from '../carouselItems';

const delay = (timeout: number) => {
  return new Promise((rs) => {
    setTimeout(rs, timeout);
  })
}

describe('test hook useCarousel', () => {
  const containerRef: any = { current: { clientWidth: 300 } };
  
  test('容器宽度与样式计算--无效 items', () => {
    const { result: { current }, unmount  } = renderHook(() => useCarousel({
      items: [],
      containerRef,
    }))
    expect(current.itemWidth).toBe(300);
    expect(current.itemListStyle?.width).toBe(0);
    expect(current.itemListStyle?.marginLeft).toBe(0);
    unmount()
  })
  test('容器宽度与样式计算--有效 items', () => {
    
    const { result: { current }, unmount  } = renderHook(() => useCarousel({
      items,
      containerRef,
    }))
    expect(current.itemWidth).toBe(300);
    expect(current.itemListStyle?.width).toBe(300 * items.length);
    expect(current.itemListStyle?.marginLeft).toBe(0);
    unmount();
  })

  jest.setTimeout(15000);
  test('验证 autoPlay', async () => {
    const { result, unmount } = renderHook(() => useCarousel({
      autoPlay: true,
      autoPlayGap: 3000,
      items,
      containerRef,
    }))
    for (let i = 0; i < items.length; i++) {
      // 确认 activeKey 在更换
      expect(result.current.activeKey).toBe(items[i].key);
      // // 确认 偏移量 在变更
      expect(result.current.itemListStyle?.marginLeft).toBe(0 - 300 * i);
      // 时间间隔大点，代码执行有时间损耗
      await delay(3500);
    }
    // 从 最有一项到第一项
    expect(result.current.activeKey).toBe(items[0].key);
    unmount();
  })

  test('验证跳转函数 goto、next、last', async () => {
    const { result, unmount } = renderHook(() => useCarousel({
      autoPlay: false,
      items,
      containerRef,
    }));
    expect(result.current.activeKey).toBe(items[0].key);
    result.current.goTo(items[2].key);
    // fiber 更新不是同步的
    await delay(10);
    expect(result.current.activeKey).toBe(items[2].key);
    result.current.next();
    await delay(10);
    expect(result.current.activeKey).toBe(items[0].key);
    result.current.next();
    await delay(10);
    expect(result.current.activeKey).toBe(items[1].key);
    result.current.last();
    await delay(10);
    expect(result.current.activeKey).toBe(items[0].key);
    unmount();
  })

});
