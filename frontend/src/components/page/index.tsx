import React from 'react';

export interface IProps {
  image: string;
  title: string;
  descriptions: string[];
  className: string;
}

export default function Page(props: IProps) {
  return (
    <div className={props.className} key={props.className}>
      {props.title && <p className='title'>{props.title}</p >}
      {props.descriptions.map((desc, index) => <p key={desc} className={`desc desc-${index}`}>{desc}</p >)}
      {props.image && 
        <div className='image-container'>
          < img className='img' src={props.image} />
        </div>
      }
    </div>
  )
}