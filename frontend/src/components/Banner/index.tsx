import { memo, FC } from 'react';
import './index.css';

interface BannerProps {
  /** carousel classname */
  className?: string;
  /** banner related data */
  data: {
    /** the title of the banner element */
    title: string;
    /** detailed description of the banner element */
    text?: string;
    /** the background image of the banner element */
    img?: string;
    /** the font color of the banner element */
    color?: string;
    /** the background color of the banner element */
    backgroundColor?: string;
  };
}

const Banner: FC<BannerProps> = function ({ className = '', data }) {
  const { title, text, color, backgroundColor, img } = data;
  return (
    <div
      className={`banner ${className}`}
      style={{
        color,
        backgroundColor,
        backgroundImage: img ? `url(${img})` : undefined,
      }}
    >
      <div className="banner-content">
        <h3 className="title">{title}</h3>
        {text && <p className="text">{text}</p>}
      </div>
    </div>
  );
};

export default memo(Banner);
