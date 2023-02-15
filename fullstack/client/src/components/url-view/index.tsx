import React from "react";
import { QRCodeSVG } from 'qrcode.react';
import copy from 'copy-text-to-clipboard';

import { Button, Card, Space, message } from 'antd';

interface urlViewProps {
    shortUrl: string;
    fullUrl: string;
}

const UrlView: React.FC<urlViewProps> = (props) => {
    const { shortUrl, fullUrl } = props;
    const [messageApi, contextHolder] = message.useMessage();

    const copyUrl = () => {
        copy(shortUrl);
        messageApi.open({
            type: 'success',
            content: `短网址：${shortUrl} 已复制到剪贴板`,
        });
    }

    return <Space direction="vertical" size="small">
        <h3>生成结果</h3>
        <div>
            短链网址：<Button type="link">{shortUrl}</Button> <Button onClick={copyUrl}> 复制 </Button>
        </div>
        <Card>
            <QRCodeSVG value={shortUrl} />
        </Card>
        <div>
            源网址：<Button type="link">{fullUrl}</Button>
        </div>
        {contextHolder}
    </Space>
};
export default UrlView;