//
//  TodoListCellView.swift
//  TodoListProject
//
//  Created by 钱伟龙 on 2022/1/11.
//

import SwiftUI
import AVKit

struct TodoListCellView: View {
    
    @EnvironmentObject var todoListViewModel : TodoListViewModel
    @ObservedObject var listItem: TodoListItem
    @State private var doListContent = ""
    @FocusState var focus:FocusedField?

    var body: some View {
        VStack {
            
            HStack(spacing: 10) {
                
                Image(listItem.finishTime > 0 ? "chose_on" : "chose_off")
                    .resizable()
                    .renderingMode(.original)
                    .frame(width: 24, height: 24)
                    .padding(.top,10)
                    .padding(.leading,15)

                if (listItem.finishTime > 0) {
                    Text(listItem.content)
                        .strikethrough()
                        .foregroundColor(.gray)
                        .font(.system(size: 15))
                        .padding(.top,10)
                }
                else {
                    TextField("", text: $doListContent,onEditingChanged: { isChange in
                        if !isChange {
                            listItem.content = doListContent;
                            todoListViewModel.editTodoListItem(todoListItem: listItem)
                        }
                    })
                        .font(.system(size: 15))
                        .padding(.top,10)
                        .focused($focus, equals: .content)
                        .allowsHitTesting(false)
                        .onSubmit {
                            listItem.content = doListContent;
                            todoListViewModel.editTodoListItem(todoListItem: listItem)
                        }
                }
                
                Spacer()
                
            }
            .padding(.bottom,10)
            .background(Color.white)
            .cornerRadius(10)
            
        }.onAppear {
            doListContent = listItem.content
        }
        .onTapGesture {
            withAnimation(.linear) {
                if (!(listItem.finishTime > 0)) {
                    todoListViewModel.finishTask(todoListItem: listItem);
                }
                else {
                    todoListViewModel.unfinishTask(todoListItem: listItem);
                }
            }
        }
        .onLongPressGesture {
            if (!(listItem.finishTime > 0)) {
                DispatchQueue.main.asyncAfter(deadline: .now() + 0.5){
                    focus = .content
                }
            }
        };
    }
    
    enum FocusedField:Hashable{
            case content
    }
}

struct TodoListBlackCellView: View {
    var body: some View {
        VStack {
            
        }
    }
}
