<?php

require 'Connect.php';

class UserList extends Connect
{
    public $table = 'list';

    public $_attributes = [
        'id',
        'user_name',
        'user_password',
        'create_time',
        'update_time',
    ];

    /**
     * Method getName
     *
     * @param $userName
     *
     * @return array|mixed
     * @author  zhaoyinfan
     */
    public function getName($userName): array
    {
        $queryOne   = "SELECT id FROM " . $this->table . " WHERE user_name='{$userName}'";
        $result     = mysqli_query($this->link, $queryOne);
        $returnData = mysqli_fetch_array($result);

        return $returnData[0] ?? [];
    }

    /**
     * Method updateData
     *
     * @author  zhaoyinfan
     * @param       $where
     * @param array $params
     * @return bool|string
     */
    public function updateData($where, $params = [])
    {
        $dataWhere = [];
        $dataSet   = [];
        foreach ($params as $key => $value) {
            if (in_array($key, $this->_attributes)) {
                $dataSet[$key] = $value;
            }
        }
        foreach ($where as $key => $value) {
            if (in_array($key, $this->_attributes)) {
                $dataWhere[$key] = $value;
            }
        }
        $dataSet   = array_filter($dataSet);
        $dataWhere = array_filter($dataWhere);
        if ($dataSet && $dataWhere) {
            $fieldSet = " SET ";
            foreach ($dataSet as $key => $value) {
                $fieldSet .= "$key='{$value}',";
            }
            $whereSet = " WHERE ";
            foreach ($dataWhere as $key => $value) {
                $whereSet .= "$key='{$value}'";
            }
            $sql = "UPDATE " . $this->table . substr($fieldSet, 0, -1) . $whereSet;
            if (mysqli_query($this->link, $sql)) {

                return true;
            }

            return mysqli_error($this->link);
        }

    }

    /**
     * Method createData
     *
     * @author  zhaoyinfan
     * @param array $params
     * @return bool|string
     */
    public function createData($params = [])
    {
        $data = [];
        foreach ($params as $key => $value) {
            if (in_array($key, $this->_attributes)) {
                $data[$key] = $value;
            }
        }
        $data = array_filter($data);
        if ($data) {
            $field  = "(" . implode(',', array_keys($data)) . ")";
            $values = "('" . implode("','", array_values($data)) . "')";

            $insert = "INSERT INTO " . $this->table . $field . "VALUES $values";
            if (mysqli_query($this->link, $insert)) {

                return true;
            }

            return mysqli_error($this->link);
        }

    }
}