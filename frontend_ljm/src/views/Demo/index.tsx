import React, { memo, useState, useEffect  } from 'react';
import p1 from '@/assets/img/p2.jpeg';
import p2 from '@/assets/img/p3.jpeg';
import p3 from '@/assets/img/p1.webp';

interface Props {}

const View: React.FC<Props> = (props) => {
  const [currentIndex, setCurrentIndex] = useState(0);
  const images = [p1, p2, p3];
  useEffect(() => {
    const interval = setInterval(() => {
      setCurrentIndex((prevIndex) =>
        prevIndex === images.length - 1 ? 0 : prevIndex + 1
      );
    }, 5000);

    return () => clearInterval(interval);
  }, [images.length]);
  return (
    <React.Fragment>
      <div className="h-screen w-screen bg-cover bg-center flex items-center justify-center">
      {images.map((image, index) => (
        <img
          key={index}
          src={image}
          alt={`Slide ${index + 1}`}
          className={`absolute top-0 left-0 w-full h-full object-cover transition-opacity duration-1000 ${
            index === currentIndex ? 'opacity-100' : 'opacity-0'
          }`}
        />
      ))}
      <div className="absolute z-10 bottom-6 left-1/2 transform -translate-x-1/2 flex space-x-2">
        {images.map((_, index) => (
          <div
            key={index}
            className={`h-2 w-6 rounded-full transition-colors duration-300 ${
              index === currentIndex
                ? "bg-white"
                : "bg-gray-300 hover:bg-gray-400"
            }`}
            onClick={() => setCurrentIndex(index)}
          />
        ))}
      </div>

      <button
        className="absolute z-10 top-1/2 left-4 transform -translate-y-1/2 text-white text-2xl font-bold bg-black bg-opacity-50 px-4 py-2 rounded-full focus:outline-none hover:bg-opacity-75 transition-colors duration-300"
        onClick={() =>
          setCurrentIndex((prevIndex) =>
            prevIndex === 0 ? images.length - 1 : prevIndex - 1
          )
        }
      >
        &#8249;
      </button>

      <button
        className="absolute z-10 top-1/2 right-4 transform -translate-y-1/2 text-white text-2xl font-bold bg-black bg-opacity-50 px-4 py-2 rounded-full focus:outline-none hover:bg-opacity-75 transition-colors duration-300"
        onClick={() =>
          setCurrentIndex((prevIndex) =>
            prevIndex === images.length - 1 ? 0 : prevIndex + 1
          )
        }
      >
        &#8250;
      </button>
      </div>
    </React.Fragment>
  );
};

export default memo(View);
