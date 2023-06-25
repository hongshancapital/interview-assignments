import React from 'react';
import './index.css';

export interface CardProps {
  imgUrl: string;
  title?: string;
  descriptions?: string[];
  mode?: 'light' | 'dark';
}

export default function Card({ imgUrl, title, descriptions, mode = 'dark' }: CardProps) {
  return (
    <div className='card'>
      <img className='card__img' src={imgUrl} alt={title ?? ''} />
      {title && <h3 className={`card__title ${mode === 'light' ? 'card__title--white' : ''}`}>{title}</h3>}
      {descriptions?.map(text =>
        <p
          key={text}
          className={`card__desc ${mode === 'light' ? 'card__desc--white' : ''}`}
        >
          {text}
        </p>
      )}
    </div>
  );
}