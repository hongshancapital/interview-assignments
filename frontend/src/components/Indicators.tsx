import React, { useState, useEffect } from "react";
import useRaf from "../hooks/useRaf";
import "./Indicators.css";
import { TSlide } from "./types";

export interface IndicatorsProps {
  slides: Array<TSlide>;
  currentSlide: number;
  onPlayEnded: () => void;
  playSpeed: number;
}

export interface IndicatorProps {
  onPlayEnded: () => void;
  playSpeed: number;
}

function Indicator() {
  return <progress max="100" value="0" className="indicator" />;
}

function ActiveIndicator(props: IndicatorProps) {
  const progress = useRaf(props.playSpeed, props.onPlayEnded);
  return <progress max="100" value={`${progress}`} className="indicator" />;
}

function Indicators(props: IndicatorsProps) {
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
        return <Indicator key={`indicator_${index}`} />;
      })}
    </div>
  );
}

export default Indicators;
