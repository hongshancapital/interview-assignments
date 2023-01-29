import { useEffect } from 'react';
import './style/pagination.css';

/** 简易实现，细节未处理 */
export const Pagination: React.FC<{
  active: number;
  count: number;
  duration: number;
}> = ({ active, count, duration }) => {
  useEffect(() => {
    document.body.style.setProperty('--duration', `${duration / 1000}s`);
  }, []);

  return (
    <div className="pagination">
      {Array.from({ length: count }).map((_, index) => {
        return <PaginationItem key={index} isActive={active === index} />;
      })}
    </div>
  );
};

const PaginationItem: React.FC<{
  isActive: boolean;
}> = ({ isActive }) => {
  return (
    <div className="pagination-item">
      <div className="pagination-item-back" />
      <div
        className={`pagination-item-front ${
          isActive ? 'pagination-item-loading' : ''
        }`}
      />
    </div>
  );
};
