import { useState } from "react";

const useIndicator = (length: number): [number, () => void] => {
  const [activeIndex, setActiveIndex] = useState(0);

  const handleAnimationEnd = () => {
    setActiveIndex((prevActiveIndex) => (prevActiveIndex + 1) % length);
  };

  return [activeIndex, handleAnimationEnd];
};

export default useIndicator;
