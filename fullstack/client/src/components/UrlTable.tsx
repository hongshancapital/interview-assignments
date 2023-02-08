import { requestAll, ShortListType } from '../request'
import {
  useQuery,
} from '@tanstack/react-query'
import { Table, Spin } from 'antd'

const columns = [
  {
    title: 'origin url',
    dataIndex: 'url',
    key: 'url',
  },
  {
    title: 'short url',
    dataIndex: 'short',
    key: 'short',
  },
];

export default function UrlTable() {
  const { isLoading, isError, data, error } = useQuery<ShortListType, Error>(['all'], requestAll)
  if (isLoading) {
    return <Spin />
  }
  if (isError) {
    return <span>Error: {error.message}</span>
  }
  const { data: rows = [] } = data
  const dataSource =
    rows.map(({
      long_url,
      short_key
    }) => (
      {
        short: short_key,
        url: long_url,
        key: short_key
      }
    ))
  
  return (
    <Table dataSource={dataSource} columns={columns} />
  )
}
