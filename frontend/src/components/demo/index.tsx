import React from 'react'
import './styles/index.css'

const Demo: React.FC<{
  className?: string;
  title?: string;
  desc?: string;
}> = ({className, title, desc}) => {
  return <div className={`demo-page ${className || ''}`}>
    {title ? <div className='title'>{title}</div> : null}
    {desc ? <div className='desc'>{desc}</div> : null}
  </div>
}

export default Demo