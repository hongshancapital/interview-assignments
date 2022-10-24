import React from 'react';
import './index.css';

export interface PageProps {
  title: string;
  content?: string;
  logo: string;
  style: 'gray' | 'white' | 'black';
  index: number;
}

const Page: React.FC<PageProps> = ({ title, content, style, logo, index }) => {
  return (
    <div style={{ left: `${index}00%` }} className={`page_container page_${style}`}>
      <div className='page_layout-center'>
        {title && <p className='title'>{title}</p>}
        {content && <p className='text'>{content}</p>}
      </div>
      {<img className='page_logo' src={logo} alt={title} />}
    </div>
  )
};

export default Page;
