import React from "react";
import { useState } from "react";
import { format } from 'date-fns'
import { QRCodeSVG } from 'qrcode.react';
import { Space, Radio, Button, Input, RadioChangeEvent, Form, Table, Popover, Tag, message } from 'antd';
import { QrcodeOutlined } from '@ant-design/icons';

import * as api from "./services/index";

import UrlView from "./components/url-view";
import FormItem from "antd/es/form/FormItem";

const { TextArea } = Input;



// TypeScript declarations
interface urlData {
  fullUrl: string;
  shortUrl: string;
  createTime?: string;
}

const emptyShrinkResult = {
  shortUrl: '',
  fullUrl: '',
}

const App = () => {
  const [mode, setMode] = useState('shrink');
  const [shrinkResult, setShirnkResult]: [urlData, (params: urlData) => void] = useState(emptyShrinkResult);
  const [tableData, setTableData] = useState([]);
  const [messageApi, contextHolder] = message.useMessage();



  const handleModeChange = (e: RadioChangeEvent) => {
    setMode(e.target.value);
    setShirnkResult(emptyShrinkResult);
  };

  const handleShrink = (params: any) => {
    api.shrinkUrl(params)
      .then(res => {
        setShirnkResult(res);
      })
      .catch((e) => {
        messageApi.open({
          type: 'error',
          content: e.message || '请求失败请稍后重试',
      });
    });
  };

  const columns = [
    {
      title: 'fullUrl',
      dataIndex: 'fullUrl',
      key: 'fullUrl',
      render: (url: string) => <a href={url}>{url}</a>,
    },
    {
      title: 'shortUrl',
      dataIndex: 'shortUrl',
      key: 'shortUrl',
      render: (url: string) => <a href={url}>{url}</a>,
    },
    {
      title: 'createTime',
      dataIndex: 'createTime',
      key: 'createTime',
      render: (date: string) => format(new Date(date), 'yyyy-MM-dd HH:mm:ss')
    },
    {
      title: 'QRcode',
      dataIndex: 'QRcode',
      key: 'QRcode',
      render: (url: string) => <>
        <Popover content={<QRCodeSVG value={url} />}>
          <Tag><QrcodeOutlined /></Tag>
        </Popover>

      </>
    }
  ];

  const handleSearch = (params: any) => {
    api.searchShrinkUrl(params.short)
      .then(res => {
        setTableData(res);
      })
      .catch((e) => {
        messageApi.open({
          type: 'error',
          content: e.message || '请求失败请稍后重试',
      });
      })
  }


  return (
    <Space direction="vertical" size="middle" style={{ width: "70%", margin: 20 }}>
      <h2>短链服务</h2>
      <Radio.Group onChange={handleModeChange} value={mode}>
        <Radio.Button value="shrink">生成短链</Radio.Button>
        <Radio.Button value="expand">反解短链</Radio.Button>
      </Radio.Group>

      {/* 生成短网址 */}
      {mode === 'shrink' && <>
        <Form onFinish={handleShrink}>
          <Form.Item
            name="fullUrl"
            rules={[{ required: true, message: '请输入源网址' },
            { type: 'url', message: '网址格式输入有误，请检查后重新输入' }]}
          >
            <TextArea rows={4} allowClear placeholder="请输入源网址" />
          </Form.Item>
          <Form.Item>
            <Button type="primary" htmlType="submit">生成压缩网址</Button>
          </Form.Item>
        </Form>
        {/* 查询结果 */}
        {shrinkResult.fullUrl &&
          <UrlView shortUrl={shrinkResult?.shortUrl} fullUrl={shrinkResult?.fullUrl} />
        }
      </>
      }


      {/* 查询短网址 */}
      {mode === 'expand' && <>
        <Form onFinish={handleSearch}>
          <FormItem name="short">
            <Input style={{ width: 'calc(100% - 150px)' }} placeholder="请输入短网址" allowClear/>
          </FormItem>
          <Form.Item>
            <Button type="primary" htmlType="submit">查询短网址</Button>
          </Form.Item>
        </Form>


        <Table dataSource={tableData} columns={columns} rowKey="shortUrl"></Table>
      </>

      }
      {contextHolder}
    </Space>

  )

}

export default App;
