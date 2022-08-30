/**
 * service层提供方法，用于前端获取组件的业务数据
 * 这里暂时是前端写死的默认数据，如果数据改成读后台，可直接重构getCarouselData即可。
 */
import { CarouselData } from '../models';
import { mockAnimationConfig, mockSlideItems } from './mock';

/** 获取走马灯组件的业务数据 */
export function getCarouselData(): CarouselData {
  // TODO: 可改成 fetch server cgi 的方式
  return {
    slideItems: mockSlideItems,
    animationConfig: mockAnimationConfig,
  };
}
