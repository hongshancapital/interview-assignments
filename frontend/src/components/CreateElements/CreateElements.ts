import React, { ReactElement } from "react";
import { HtmlsJsonSchema } from '../../@types'

export default function createElements (data: HtmlsJsonSchema): Array<ReactElement> | ''   {
    const { contentType = '', className = '', contentList = [] } = data || {}
    if ( contentList.length ) {
        return (
            contentList?.map((item): ReactElement => {
                return React.createElement(contentType, { className: className }, item || '')
            })
        )
    } else {
        return ''
    }
    
}
