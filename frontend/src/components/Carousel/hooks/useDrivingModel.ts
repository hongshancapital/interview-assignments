import { useEffect, useState } from 'react';
import { CarouselItem, DonutLinkedMap } from '../types';

/**
 * 通过数据源创建一个环形 linkedMap 结构，可以方便的获得到数据模型之间的关联关系
 * @param dataSource 
 * @returns 
 */
function useDrivingModel(dataSource: CarouselItem[]) {
  const [donutLinkedMap, setDonutLinkedMap] = useState<DonutLinkedMap<CarouselItem>>();

  useEffect(() => {
    if (!dataSource.length) {
      return;
    }
    
    const initialDonutLinkedMap: DonutLinkedMap<CarouselItem> = {};

    let ds = dataSource;
    // [1,2] -> [1,2,1,2]
    if (ds.length === 2) {
      ds = ds.concat(ds);
    // [1] -> [1,1,1]
    } else if (ds.length === 1) {
      ds = ds.concat(ds, ds);
    }

    ds.forEach((item, idx) => {
      let prev = idx - 1;
      if (prev < 0) {
        prev = ds.length - 1;
      }
      let next = idx + 1;
      if (next >= ds.length) {
        next = 0;
      }
      initialDonutLinkedMap[idx] = {
        key: idx,
        data: item,
        prev,
        next
      };
    });
    setDonutLinkedMap(initialDonutLinkedMap);

  }, [dataSource]);

  return donutLinkedMap;
}

export default useDrivingModel;