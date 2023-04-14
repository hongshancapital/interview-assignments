import React, { memo } from 'react';import {
  // RecoilRoot,
  atom,
  selector,
  useRecoilState,
  useRecoilValue,
} from "recoil";
const View: React.FC<Props> = (props) => {
    return (
      <React.Fragment>
          Demo page
      </React.Fragment>
    )
}
export default memo(View);
