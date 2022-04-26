import React, { useMemo } from 'react';
import { useClassName } from '../../hooks';
import classNames from './Aspect.module.css';

export type AspectRatio = '16:9' | '4:3' | '1:1';

export type AspectProps = {
  ratio?: AspectRatio;
  className?: string | undefined | null;
  innerClassName?: string | undefined | null;
}

export const Aspect: React.FC<AspectProps & JSX.IntrinsicElements['div']> = ({
  ratio = '16:9',
  className,
  innerClassName,
  children,
  ...props
}) => {

  const styles = useMemo(() => {
    switch (ratio) {
      case '16:9' :
        return { paddingTop: 9 / 16 * 100 + '%' };
      case '4:3' :
        return { paddingTop: 3 / 4 * 100 + '%' };
      default :
        return {};
    }
  }, [ratio]);

  return (
    <div
      {...props}
      className={useClassName(classNames.outer, className, 'outer')}
      style={styles}
    >
      <div className={useClassName(classNames.inner, innerClassName, 'inner')}>
        {children}
      </div>
    </div>
  );
};
