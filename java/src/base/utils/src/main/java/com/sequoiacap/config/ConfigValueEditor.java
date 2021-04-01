package com.sequoiacap.config;

import java.beans.PropertyEditorSupport;

public class ConfigValueEditor
	extends PropertyEditorSupport
{
	public void setAsText(String text)
		throws java.lang.IllegalArgumentException
	{
		setValue(new ConfigValue(text));
	}
}
