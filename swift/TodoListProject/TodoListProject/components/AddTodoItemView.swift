//
//  addTodoItemView.swift
//  TodoListProject
//
//  Created by 钱伟龙 on 2022/1/16.
//

import SwiftUI

struct AddTodoItemView: View {
    
    @EnvironmentObject var todoListViewModel : TodoListViewModel
    @Binding var isEdit: Bool
    @State private var inputContent = ""
    @FocusState var focus:FocusedField?
    @Binding var selectedIndex: NSInteger
    @Binding var choseGroupViewIsExpanded: Bool
    
    var body: some View {
        HStack(spacing: 0){
            
            if (isEdit) {
                
                Spacer()
                    .frame(width: 20)
                
                TextField("   Add new...", text: $inputContent)
                    .focused($focus, equals: .title)
                    .frame(width: UIScreen.main.bounds.width - 20 - 115, height: 50)
                    .font(.system(size: 13))
                    .background(Color.white)
                    .cornerRadius(10)
                    .padding([.top,.bottom],20)
                    .onSubmit {
                        todoListViewModel.addTodoListItem(content: inputContent, groupIndex: selectedIndex)
                        inputContent = ""
                    }
                
                Spacer()
                    .frame(width: 18)
                
                HStack(spacing: 0){
                    
                    Spacer()
                        .frame(width: 8)
                    
                    Text(todoListViewModel.todoList[selectedIndex].title)
                        .lineLimit(2)
                        .font(.system(size: 10))
                    
                    Image(choseGroupViewIsExpanded ? "close" : "open")
                        .resizable()
                        .renderingMode(.original)
                        .frame(width: 16, height: 16)
                        .padding([.top,.bottom],7)
                    
                    Spacer()
                        .frame(width: 8)
                }
                .background(Color.white)
                .cornerRadius(15)
                .onTapGesture {
                    choseGroupViewIsExpanded = true;
                }
                
                Spacer()
                    .frame(width: 20)
                
            }
            else {
                Spacer()
                
                Button(action: {
                    isEdit = true;
                    DispatchQueue.main.asyncAfter(deadline: .now() + 0.5){
                        focus = .title
                    }
                }) {
                    Text("Add new...")
                        .frame(width:UIScreen.main.bounds.width - 80, height: 50,alignment: .leading)
                        .font(.system(size: 13))
                        .foregroundColor(Color.gray)
                        .padding(.leading,10)
                }
                .overlay {
                    RoundedRectangle(cornerRadius: 10)
                        .strokeBorder(style: StrokeStyle(lineWidth: 1, dash: [5]))
                        .foregroundColor(Color.gray)
                }
                .background(Color.white)
                .cornerRadius(10)
                .padding([.top,.bottom],20)
                
                Spacer()
            }
        }
        .background(Color.gray.opacity(0.1))
    }
    
    enum FocusedField:Hashable{
            case title
    }
}
