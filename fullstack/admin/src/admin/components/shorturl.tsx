import * as React from "react";
import {
    List, Datagrid, Show, SimpleShowLayout,
    Edit, Create, SimpleForm,
    EditButton, ShowButton,
    BooleanInput, BooleanField,
    TextField, TextInput
} from 'react-admin';

export const ShorturlList = (props) => (
    <List {...props}>
        <Datagrid>
            <TextField source="id" />
            <TextField source="s_url" />
            <TextField source="url" />
            <TextField source="desc" />
            <BooleanField source="status" />
            <ShowButton basePath="/Shorturls" />
            <EditButton basePath="/Shorturls" />
        </Datagrid>
    </List>
);

const ShorturlTitle = ({ record }) => {
    return <span>Shorturl {record ? `"${record.title}"` : ''}</span>;
};

export const ShorturlView = (props) => (
    <Show {...props}>
        <SimpleShowLayout>
            <TextField disabled source="id" />
            <TextField source="s_url" />
            <TextField source="url" options={{ multiline: true }} />
            <TextField multiline source="desc" />
            <BooleanField source="status" />
        </SimpleShowLayout>
    </Show>
);

export const ShorturlEdit = (props) => (
    <Edit {...props}>
        <SimpleForm>
            <TextInput disabled source="id" />
            <TextInput source="url" options={{ multiline: true }} />
            <TextInput multiline source="desc" />
            <BooleanInput source="status" />
        </SimpleForm>
    </Edit>
);

export const ShorturlCreate = (props) => (
    <Create title="Create a Shorturl" {...props}>
        <SimpleForm>
            <TextInput source="url" options={{ multiline: true }} />
            <TextInput multiline source="desc" />
            <BooleanInput source="status" />
        </SimpleForm>
    </Create>
);