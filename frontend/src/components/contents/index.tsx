import React from 'react';
import { ContentItemType } from './conf';
import './index.scss';

function ContentItem(props: ContentItemType) {
  const { title, describetion, imgUrl, style } = props;
  return (
    <div className="content-item" style={{ ...style }}>
      <article>
        <h1 dangerouslySetInnerHTML={{ __html: title }}></h1>
        <p dangerouslySetInnerHTML={{ __html: describetion }}></p>
      </article>
      <img src={imgUrl} alt="" />
    </div>
  );
}

export default ContentItem;
