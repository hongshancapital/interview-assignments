/*
 * @Author: danjp
 * @Date: 2022/6/25 11:06
 * @LastEditTime: 2022/6/25 11:06
 * @LastEditors: danjp
 * @Description:
 */
import { useEffect } from 'react';

const useResize = (listener: () => void) => {
  useEffect(() => {
    window.addEventListener('resize', listener);
    return () => {
      window.removeEventListener('resize', listener);
    };
  }, [listener]);
};

export default useResize;
