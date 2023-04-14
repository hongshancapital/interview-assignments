import React, { memo } from 'react';
interface Props {
}
const Com: React.FC<Props> = (props) => {
  return (
    <div className="loading_box">
      <div className="loading"></div>
    </div>
  )
}
export default memo(Com);
