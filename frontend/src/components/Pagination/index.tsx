import { useEffect, useRef, useState } from "react";
import './index.css'

type PaginationProps = {
  pagination: React.Key[];
  current: number;
  time: number;
  onChange?: (current: number) => void;
}

function Pagination ({ pagination, current, time, onChange }: PaginationProps) {

  const [progress, setProgress] = useState<number>(0)
  const timerRef = useRef<NodeJS.Timeout>()

  useEffect(() => {
    changeCurrent()
  }, [progress])

  useEffect(() => {
    changeProgress()
    return () => {
      clearInterval(timerRef.current)
    }
  }, [current])

  const changeProgress = () => {
    timerRef.current && clearInterval(timerRef.current)
    timerRef.current = setInterval(() => {
      setProgress(state => state + 1)
    }, time / 100)
  }

  const changeCurrent = () => {
    if (progress >= 100) {
      onChange?.(current >= pagination?.length - 1 ? 0: current + 1)
      setProgress(0)
      clearInterval(timerRef.current)
    }
  }

  return (
    <div className="pagination-component">
      {pagination.map((item, index) => (
        <div 
          className="pagination-container" 
          key={item}
        >
          <div 
            className="progress" 
            style={{ width: `${current === index ? progress: 0}%` }}
          />
        </div>
      ))}
    </div>
  )
}

export default Pagination