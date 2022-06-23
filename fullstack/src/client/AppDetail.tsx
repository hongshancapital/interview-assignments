import React from 'react';
import { ShortUrl } from '../models/shortUrl';
import { Descriptions, Typography } from 'antd';
const { Paragraph } = Typography;

interface AppDetailProps {
  data: ShortUrl | null
}

interface AppDetailState { }

export default class extends React.Component<AppDetailProps, AppDetailState> {
  constructor(props: AppDetailProps) {
    super(props);
  }

  render() {
    const style = { display: this.props.data ? 'block' : 'none' };
    return (
      <Descriptions title="" style={style} column={1}>
        <Descriptions.Item label="长网址">
          <Paragraph>{this.props.data?.long_url}</Paragraph>
        </Descriptions.Item>
        <Descriptions.Item label="短网址">
          <Paragraph copyable={{ tooltips: true }}>{this.props.data?.short_url}</Paragraph>
        </Descriptions.Item>
        <Descriptions.Item label="创建日期">
        <Paragraph>{this.props.data?.created_at}</Paragraph>
        </Descriptions.Item>
      </Descriptions>
    );
  }
}
