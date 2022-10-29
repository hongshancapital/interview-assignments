import React, { Fragment } from 'react';

// 支持使用换行符 \n 分割多行文本
export default function MultiLine({ text }: { text: string }) {
  return (
    <>
      {String(text || '')
        .split('\n')
        .map((x: string, i: number) => (
          <Fragment key={x}>
            {i > 0 ? <br /> : null}
            {x}
          </Fragment>
        ))}
    </>
  );
}
