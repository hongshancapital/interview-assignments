//
//  TodoSectionView.swift
//  interview-assignments
//
//  Created by never88gone on 2022/1/20.
//

import SwiftUI

/// todogroupview class
///
/// todogroupview
///
///
struct TodoSectionView: View {
    @State var  groupName : String
    var sectionTodoList : [Todo]
    let sectionCellTextChangedAction: ((Todo,String) -> Void)?
    let sectionCellCheckedChangedAction: ((Todo) -> Void)?
    
    var body: some View {
        return Section(header: Text(self.groupName).font(.custom("PingFangSC-Regular", size: 16).weight(.bold)).foregroundColor(Color("ngtextback")))
        {
            ForEach(self.sectionTodoList) { oneTodo in
                TodoTVCell(todo:oneTodo, cellTextChangedAction: {
                    inputText in
                    self.sectionCellTextChangedAction?(oneTodo,inputText)
                    
                }, cellCheckedChangedAction:{
                    oneTodo.checked.toggle()
                    self.sectionCellCheckedChangedAction?(oneTodo)
                })
            }
            .listRowBackground(Color.clear)
        }
    }
}

struct TodoSectionView_Previews: PreviewProvider {
    
    static var previews: some View {
        var todoList = [Todo(title: "Building Lists and Navigation",groupName:"SwiftUI Essentials"), Todo(title: "Creating and Combining Views",groupName:"SwiftUI Essentials"), Todo(title: "Hanline User Input",groupName:"SwiftUI Essentials")]
        TodoSectionView(groupName: "哈哈", sectionTodoList: todoList,sectionCellTextChangedAction: { oneTodo, inputText in
            if (inputText.count == 0) {
                todoList.remove(at: 0)
            }
        }, sectionCellCheckedChangedAction: {
            oneTodo in
            todoList[0]=oneTodo
        })
    }
}
