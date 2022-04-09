import * as React from "react";
import {
    List, Datagrid, Show, SimpleShowLayout,
    Edit, Create, SimpleForm,
    EditButton, ShowButton,
    BooleanInput, BooleanField,
    TextField, TextInput, UrlField, FunctionField,

    ListProps, ShowProps, EditProps, CreateProps, Record
} from 'react-admin';

import { baseUrl } from '@/api/base'

export const ShorturlList = (props: ListProps) => (
    <List {...props}>
        <Datagrid>
            <TextField source="id" />
            <FunctionField
                label="Short url"
                render={(record: Record | undefined) => (
                    <a target="__blank" href={`${baseUrl}/${record?.s_url}`}>{`${baseUrl}/${record?.s_url}`}</a>
                )}
            />
            <UrlField source="url" />
            {/* <BooleanField source="status" /> */}
            <ShowButton basePath="/Shorturls" />
            <EditButton basePath="/Shorturls" />
        </Datagrid>
    </List>
);

const ShorturlTitle = ({ record }: { record: Record }) => {
    return <span>Shorturl {record ? `"${record.title}"` : ''}</span>;
};

export const ShorturlView = (props: ShowProps) => (
    <Show {...props}>
        <SimpleShowLayout>
            <TextField source="id" />
            <FunctionField
                label="Short url"
                render={(record: Record | undefined) => (
                    <a target="__blank" href={`${baseUrl}/${record?.s_url}`}>{`${baseUrl}/${record?.s_url}`}</a>
                )}
            />
            <UrlField source="url" />
            <TextField source="desc" />
            {/* <BooleanField source="status" /> */}
        </SimpleShowLayout>
    </Show>
);

export const ShorturlEdit = (props: EditProps) => (
    <Edit {...props}>
        <SimpleForm>
            <TextInput disabled source="id" />
            <TextInput source="url" options={{ multiline: true }} />
            <TextInput multiline source="desc" />
            {/* <BooleanInput source="status" /> */}
        </SimpleForm>
    </Edit>
);

export const ShorturlCreate = (props: CreateProps) => (
    <Create title="Create a Shorturl" {...props}>
        <SimpleForm>
            <TextInput source="url" options={{ multiline: true }} />
            <TextInput multiline source="desc" />
            {/* <BooleanInput source="status" /> */}
        </SimpleForm>
    </Create>
);