const CarouselCaption = ({ title, subtitle, color }) => {
  const divStyle = {
    color,
    position: 'absolute',
    top: '20vh'
  };

  const titleStyle = {
    fontSize: '60px',
    whiteSpace: 'pre-line'
  };

  const subtitleStyle = {
    fontSize: '40px',
    marginTop: '20px'
  };

  return (
    <div style={ divStyle }>
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