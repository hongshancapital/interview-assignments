//
//  ToDoContentCell.swift
//  ToDoList
//
//  Created by zhoubo on 2021/11/24.
//

import SwiftUI
import Combine

struct ToDoContentCell: View {
    
    @ObservedObject var todoModel: ToDoModel
    
    var body: some View {
        Group {
            HStack {
                Image(todoModel.checked ? "checkmark.circle.fill" : "checkmark.circle").foregroundColor(Color.gray)
                    .padding(5)
                
                Text(todoModel.content ?? "")
                    .font(Font.system(size: 10,
                                      weight: .medium,
                                      design: .monospaced))
                    .strikethrough(todoModel.checked,
                                   color: ColorPattern.strikethrough.color
                    )
                    .foregroundColor(todoModel.checked ? ColorPattern.strikethrough.color : ColorPattern.mainText.color)
                    .truncationMode(.tail)
                    .lineLimit(1)
                Spacer()
            }
            .cornerRadius(8.0)
        }
        .padding(EdgeInsets(top: 3, leading: 10, bottom: 3, trailing: 10))
        .background(ColorPattern.todoCard.color)
    }
}

struct ToDoContentCell_Previews: PreviewProvider {
    static var previews: some View {
        ToDoContentCell(todoModel: ToDoModel(content: "Building Lists and Navigation",
                                             checked: false, tagId: 1, sortIndex: 10))
    }
}
