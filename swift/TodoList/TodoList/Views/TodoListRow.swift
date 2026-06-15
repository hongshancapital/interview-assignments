//
//  TodoListRow.swift
//  TodoList
//
//  Created by 边边 on 2021/12/11.
//

import SwiftUI

struct TodoListRow: View {
    @State var item:TodoItem
    
    var body: some View {
        HStack{
            RadioButton(checked: $item.completed)
            if item.completed {
                Text(item.itemText).foregroundColor(.gray)
                    .strikethrough()
            } else {
                Text(item.itemText)
            }
            
        }
        
    }
}

struct TodoListRow_Previews: PreviewProvider {
    static var previews: some View {
        TodoListRow(item: todoItems[0])
    }
}
