import React from 'react'
import { IPagination } from './interface'
import './Pagination.css'

function Pagination(props: IPagination) {
  const {count, delay, active, duration, onClick} = props
  let buttons = new Array(count).fill({})

  return (
    <div className='pagination'>
      {buttons.map((_btn, index) => {
        const isActive = active === index
        return <div key={`pagination_btn_${index}`} className='btn-wrapper' onClick={() => {
          console.log('click', index)
          onClick?.(index)
        }}>
          {isActive && <div className='active-btn' style={{animationDelay:`${duration / 1000}s`, animationDuration: isActive ? `${delay/1000}s`:'0s'}}></div>}
        </div>
      })}
    </div>
  )
}

export default Pagination
