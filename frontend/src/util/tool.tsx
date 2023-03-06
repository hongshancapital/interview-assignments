/*
 * @Description: 工具库
 * @Author: cmh
 * @Date: 2023-03-06 12:08:11
 * @LastEditTime: 2023-03-06 19:53:15
 * @LastEditors: cmh
 */
  /**
   * 重置全局动画
   */
  export const replayAnimations = () => {
    document.getAnimations().forEach((anim) => {
      anim.cancel();
      anim.play();
    });
  };