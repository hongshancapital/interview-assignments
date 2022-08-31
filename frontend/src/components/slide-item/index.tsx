import React from 'react';
import { DescContentConfig } from '../../models';
import './index.css';

interface SlideItemProps {
  /** 图片url */
  imgUrl: string;
  /** 文本描述配置 */
  descContents: DescContentConfig[];
}

export const SlideItem: React.FC<SlideItemProps> = (props: SlideItemProps) => {
  const { imgUrl, descContents = [] } = props;

  /** 渲染背景图 */
  const renderBgImg = () => {
    return imgUrl ? <img className='slide-img' src={imgUrl} alt='slide-img' /> : null;
  }

  /** 渲染文字描述内容 */
  const renderDescContents = () => {
    return (
      <div className='slide-desc' data-testid='slide-desc'>
        {
          descContents.map((desc) => {
            const { id, text, style } = desc;
            return <div key={id} style={{...style}}>{text}</div>;
          })
        }
      </div>
    );
  }

  return (
    <div className='slide-item'>
      {renderBgImg()}
      {renderDescContents()}
    </div>
  );
}
