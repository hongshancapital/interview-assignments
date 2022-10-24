import React from 'react';
import './index.css';

const Progress = ({ loading }: { loading: boolean }) => {
  return (
    <div className='progress_container'>
      <div className={`progress_content ${loading ? 'progress_loading' : ''}`}></div>
    </div>
  );
};

interface ProgressProps {
  count: number;
  index: number;
}

const Container: React.FC<ProgressProps> = ({ count, index }) => {
  return (
    <div className='progress'>
      {new Array(count).fill(0).map((_v, i) => {
        let isLoading = false;
        if (index === i+1) {
          isLoading = true;
        }
        return <Progress key={i} loading={isLoading} />;
      })}
    </div>
  );
}

export default Container;