import React, { useState, useEffect } from 'react';

interface Props {
  images: string[];
}

const Carousel: React.FC<Props> = ({ images }) => {
  const [currentImageIndex, setCurrentImageIndex] = useState(0);

  useEffect(() => {
    const interval = setInterval(() => {
      setCurrentImageIndex((currentImageIndex + 1) % images.length);
    }, 3000);

    return () => clearInterval(interval);
  }, [currentImageIndex, images.length]);

  return (
    <div style={{textAlign:'center',padding:'50px'}}>
      < img src={images[currentImageIndex]} alt="carousel" style={{width:'200px'}} />
    </div>
  );
};

export default Carousel;