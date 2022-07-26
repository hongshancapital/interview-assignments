import React, { CSSProperties } from 'react';

export interface ISlideRendererProps {
  titles?: string[];
  descriptions?: string[];
  image: {
    src: string;
    alt?: string;
  },
  wrapperStyle?: CSSProperties;
}

export function SlideRenderer(props: ISlideRendererProps): JSX.Element {
  return (
    <div className='slideWrapper' style={props.wrapperStyle}>
      {props.titles?.map((title, index) =>
        <h1 key={index}>{title}</h1>
      )}
      {props.descriptions?.map((desc, index) =>
        <div key={index}>{desc}</div>
      )}
      <img src={props.image.src} alt={props.image.alt} />
    </div>
  );
}