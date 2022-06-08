import { CSSProperties, default as React } from 'react';
import ICarouselItem from './carousel/ICarouselItem';
import './MyCarouseContent.scss';

interface IMyCarouseContentProps {
  /**
   * 轮播数
   */
  data: ICarouselItem;

  /**
   * 自定义样式
   */
  style?: CSSProperties;
}

export const MyCarouseContentHooks: React.FC<IMyCarouseContentProps> = (
  props
) => {
  const { data, style } = props;
  const { title, des, image } = data;

  return (
    <div className="MyCarouseContent" style={style}>
      <h1 dangerouslySetInnerHTML={{ __html: title }} />
      {des && <p dangerouslySetInnerHTML={{ __html: des }} />}
      <div className="Img" style={{ backgroundImage: `url(${image})` }} />
    </div>
  );
};

export default MyCarouseContentHooks;
