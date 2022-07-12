import React, { useState, useCallback } from "react";
import useDocumentSize from "../hooks/useDocumentSize";
import Slides from "./Slides";
import Indicators from "./Indicators";
import "./Carousel.css";
import { TSlide } from "./carousel-types";

interface TCarouselProps {
  slides: Array<TSlide>;
  playSpeed?: number;
}

export default function Carousel(props: TCarouselProps) {
  const [currentSlide, updateCurrentSlide] = useState(0);
  const [width, height] = useDocumentSize();
  const { playSpeed = 3000, slides } = props;

  const onPlayEnded = useCallback(() => {
    updateCurrentSlide((currentSlide + 1) % slides.length);
  }, [currentSlide, slides]);

  return (
    <div className="carousel" style={{ height: `${height}px` }}>
      <Slides slides={slides} currentSlide={currentSlide} width={width} />
      <Indicators
        slides={slides}
        currentSlide={currentSlide}
        playSpeed={playSpeed}
        onPlayEnded={onPlayEnded}
      />
    </div>
  );
}
