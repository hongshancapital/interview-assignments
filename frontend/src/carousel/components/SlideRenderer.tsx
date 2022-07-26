import React, { CSSProperties } from 'react';
import './SlideRenderer.css';

export interface ISlideRendererProps {
  titles?: string[];
  descriptions?: string[];
  wrapperStyle?: CSSProperties;
}

export function SlideRenderer(props: ISlideRendererProps): JSX.Element {
  return (
    <div className='slideWrapper' style={props.wrapperStyle}>
      <div className='titlesWrapper'>
        {props.titles?.map((title, index) =>
          <h1 className='title' key={index}>{title}</h1>
        )}
      </div>
      {props.descriptions?.map((desc, index) =>
        <div className='description' key={index}>{desc}</div>
      )}
    </div>
  );
}