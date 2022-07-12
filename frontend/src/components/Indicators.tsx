import React, { useState, useEffect } from "react";
import useRaf from "../hooks/useRaf";
import "./Indicators.css";
import { TSlide } from "./carousel-types";

interface TIndicatorProps {
  onPlayEnded: () => void;
  playSpeed: number;
}

interface TIndicatorsProps extends TIndicatorProps {
  slides: Array<TSlide>;
  currentSlide: number
}

function NormalIndicator() {
  return <progress max="100" value="0" className="indicator" />;
}

function ActiveIndicator(props: TIndicatorProps) {
  const progress = useRaf(props.playSpeed, props.onPlayEnded);
  return <progress max="100" value={`${progress}`} className="indicator" />;
}

function Indicators(props: TIndicatorsProps) {
  const { slides, currentSlide, onPlayEnded, playSpeed } = props;

  return (
    <div className="indicators">
      {slides.map((item, index) => {
        if (index === currentSlide) {
          return (
            <ActiveIndicator
              playSpeed={playSpeed}
              onPlayEnded={onPlayEnded}
              key={`indicator_${index}`}
            />
          );
        }
        return <NormalIndicator key={`indicator_${index}`} />;
      })}
    </div>
  );
}

export default Indicators;
