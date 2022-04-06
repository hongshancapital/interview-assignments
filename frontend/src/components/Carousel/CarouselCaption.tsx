import React from 'react'
import CSS from 'csstype';

const CarouselCaption: React.FC<CarouselCaptionProps> = ({ title, subtitle, color }) => {
  const titleStyle: CSS.Properties = {
    fontSize: '60px',
    whiteSpace: 'pre-line'
  };

  const subtitleStyle: CSS.Properties = {
    fontSize: '40px',
    marginTop: '20px'
  };

  return (
    <div className="carousel__caption" style={{ color }}>
      <div style={ titleStyle }>
        { title }
      </div>
      <div style={ subtitleStyle }>
        { subtitle }
      </div>
    </div>
  );
};

export default CarouselCaption;