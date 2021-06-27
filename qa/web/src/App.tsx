import React, { useEffect, useState } from 'react';
import FlatSection from '@/components/FlatSelection';
import Button from '@/components/Button';
import { SubmitErrorHandler, SubmitHandler, Controller, FormProvider, useForm } from 'react-hook-form';
import { ToolboxForm } from '@/types';
import { getToolboxOptions, createToolboxPreference, getToolboxList } from './models/services/toolbox';
import styles from './App.scss';

const App = () => {
  const methods = useForm<ToolboxForm>();
  const { handleSubmit, control } = methods;
  const [options, setOptions] = useState<string[]>([]);
  const [loading, setLoading] = useState(false);
  const [toolboxList, setToolboxList] = useState<ToolboxForm[]>([]);
  const [errorString, setErrorString] = useState('');
  const onSubmit: SubmitHandler<ToolboxForm> = async (data) => {

    setLoading(true);

    const createResult = await createToolboxPreference(data);
    console.dir(createResult);
    if (createResult) {
      setErrorString(String(createResult));
    }
    const list = await getToolboxList();
    if (list) {
      setToolboxList(list);
    }
    setLoading(false);

  };

  const onSubmitError: SubmitErrorHandler<ToolboxForm> = (error) => {
    alert('Please enter all fields before submit');
    console.error(error);
  };


  useEffect(() => {
    (async () => {
      setLoading(true);
      const newOptions = await getToolboxOptions();
      const list = await getToolboxList();
      if (list) {
        setToolboxList(list);
      }
      setLoading(false);
      if (newOptions) {
        setOptions(newOptions);
      }
    })();
  }, [])
  return (
    <div className={styles.appContainer}>
      <div className={styles.listContainer}>
        <h3>QA Engineer's Toolbox Arsenal:</h3>
        {toolboxList.map(toolbox => (
          <span className={styles.item} key={toolbox.name}>
            {toolbox.name}:{toolbox.tools}
          </span>)
        )
        }
        {!toolboxList.length && 'Empty, add some!'}
      </div>
      <FormProvider {...methods}>
        <div className={styles.form} >
          <Controller name="name"
            control={control}
            as={<input className={styles.input} placeholder="Please enter Engineer's name" />}
          />
          <Controller
            name="tools"
            control={control}
            defaultValue={1}
            render={({ onChange, value }) => (
              <FlatSection
                defaultValue={options.length && options[0]}
                onValueChange={(value) => {
                  onChange(value);
                  console.dir(value)
                }}
                options={options}
                value={value}
              />)} />
          {(errorString !== '' && errorString !== undefined) && <div className={styles.errorString}>{errorString}</div>}
          <Button loading={loading} onClick={handleSubmit(onSubmit, onSubmitError)}>
            Submit
          </Button>
        </div>
      </FormProvider>
    </div>
  );
}

export default App;
