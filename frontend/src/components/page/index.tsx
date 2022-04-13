import React from 'react';

export interface IProps {
  image: string;
  title: string;
  descriptions: string[];
  className: string;
}

export default function Page(props: IProps) {
  const { className, title, descriptions, image } = props;
  return (
    <div className={className} key={className}>
      {title && <p className='title'>{title}</p>}
      {descriptions.map((desc, index) => <p key={desc} className={`desc desc-${index}`}>{desc}</p>)}
      {image && 
        <div className='image-container'>
          <img className='img' src={props.image} alt=''/>
        </div>
      }
    </div>
  )
}