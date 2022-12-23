import { PluginContext } from '../types';
import { EventPlugin } from '../event-plugin-handler';
import React from 'react';

const createDraggablePlugin = (): EventPlugin<PluginContext> => {
  let oriPausedState = false;
  let oriStyle = {};
  let oriX = 0;
  return {
    onMouseDown: (e, context) => {
      const { setPaused, setStyle } = context;
      setPaused(oldState=>{
        oriPausedState = oldState;
        return true;
      });
      setStyle(oldStyle => {
        oriStyle = oldStyle;
        return oldStyle;
      });
      oriX = (e as React.MouseEvent).clientX;
    },
    onMouseUp: (e, context) => {
      const { setActiveIndex,setPaused, setStyle, getLength } = context;
      setPaused(oriPausedState);
      setStyle(oriStyle);
      let offset = (e as React.MouseEvent).clientX - oriX;
      if(!offset) return;
      setActiveIndex(activeIdx=>{
        let idx = activeIdx - offset/Math.abs(offset);
        const length = getLength();
        while(idx<0) {
          idx +=length;
        }
        return idx%length;
      });
    },
  };
};
export default createDraggablePlugin;