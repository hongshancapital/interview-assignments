import React, { useState } from 'react';
import { Form, Input, Button, Alert, Row, Col, message } from 'antd';
import { useRequest } from 'ahooks';
import { http } from '@utils';

import './index.css';

function fetchData(link: string): Promise<any> {
  return http.post('/api/generateShortLink', {
    link
  });
}

const GetShortLink = () => {
  const [shortLink, setShortLink] = useState('');
  const { loading, run } = useRequest(fetchData, {
    manual: true,
    throttleWait: 1000,
    onSuccess: (res, params) => {
      if (res.status === 200 && res.data.code === 0) {
        const { short, domain } = res.data.data;
        setShortLink(`${domain}/${short}`);
      } else {
        message.error(`${res.data.message}(${res.data.code})`);
      }
    },
    onError: (error: Error) => {
      message.error(error.message);
      console.log('server error:', error.stack);
    }
  });

  const onFinish = async (values: any) => {
    run(values.link);
  };

  return (
    <div className="component-wrap">
      <Form
        name="getShort"
        labelCol={{ span: 4 }}
        wrapperCol={{ span: 10 }}
        initialValues={{ remember: true }}
        onFinish={onFinish}
        autoComplete="off"
      >
        <Form.Item
          label="link"
          name="link"
          validateTrigger="submit"
          rules={[{
            required: true,
            message: 'Please input your link!'
          }, {
            pattern: /(http|ftp|https):\/\/[\w\-_]+(.[\w\-_]+)+([\w\-.,@?^=%&:/~+#]*[\w\-@?^=%&/~+#])?/,
            message: 'Url invalid! You should input full path including protocal.'
          }]}
        >
          <Input />
        </Form.Item>
        <Form.Item wrapperCol={{ offset: 4, span: 10 }}>
          <Button loading={loading} type="primary" htmlType="submit">
            Generate
          </Button>
        </Form.Item>
      </Form>
      {
        !!shortLink && (
          <Row>
            <Col offset={4} span={10}>
              <Alert
                message={
                  <>
                    Congratulations! The short url is <b>{shortLink}</b>.
                  </>
                }
                type="success" />
            </Col>
          </Row>
        )
      }
    </div>
  );
}

export default GetShortLink;
