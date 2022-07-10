import React from 'react';
import { ContentItemType } from './conf';
import './index.scss';

function ContentItem(props: ContentItemType) {
  const { Title, Describetion, imgUrl, style } = props;
  return (
    <div className="content-item" style={{ ...style }}>
      <article>
        <Title />
        <Describetion />
      </article>
      <img src={imgUrl} alt="" />
    </div>
  );
}

export default ContentItem;
