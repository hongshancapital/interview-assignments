//
//  TodoListItemView.swift
//  TodoSwiftUI
//
//  Created by Jader Yang on 2021/12/5.
//

import SwiftUI

struct TodoListItemView: View {
    
    let todoItem: TodoItem
    
    var body: some View {
        HStack(alignment: .center, spacing: 20) {
            Image(todoItem.compeleted ? "check_complete" : "checkbox")
                .frame(width: 10, height: 10, alignment: .center)
            Text(todoItem.content)
                .foregroundColor(todoItem.compeleted ? .secondary : .primary)
                .strikethrough(todoItem.compeleted)
        }
        .background(Color.white)
        .padding(.vertical, 8)
        .padding(.horizontal, 10)
        .cornerRadius(10)
        .font(.system(size: 14))
    }
}

struct TodoListItemView_Previews: PreviewProvider {
    
    static var previews: some View {
        TodoListItemView(todoItem: TodoItem(content: "abc", compeleted: true))
    }
}
