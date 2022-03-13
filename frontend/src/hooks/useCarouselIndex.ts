import { useState, useEffect } from 'react';

export default function useCarouselIndex(
  getCarouselItemsRef: () => HTMLCollection | [],
  duration: number,
) {
  const [carouselIndex, setCarouselIndex] = useState(0);

  useEffect(() => {
    const timer = setInterval(() => {
      const carouselItemsRef = getCarouselItemsRef();

      setCarouselIndex((index) => {
        const newIndex = (index + 1) % carouselItemsRef.length;
        if (carouselItemsRef[newIndex]) {
          carouselItemsRef[newIndex].scrollIntoView({
            behavior: 'smooth',
          });
        }

        return newIndex;
      });
    }, duration);

    return () => clearInterval(timer);
  }, [getCarouselItemsRef, duration]);

  return carouselIndex;
}
