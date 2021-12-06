import React from "react";
import SlideComponent from "./components/Slide";
import { Slide } from "./types/Slide";

export const getSlideFunctionByData = (slide: Slide) => () =>
  <SlideComponent key={slide.id} data={slide}></SlideComponent>;
