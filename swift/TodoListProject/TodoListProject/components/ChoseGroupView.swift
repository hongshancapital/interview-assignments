//
//  ChoseGroupView.swift
//  TodoListProject
//
//  Created by 钱伟龙 on 2022/1/16.
//

import SwiftUI

struct ChoseGroupView: View {
    
    @EnvironmentObject var todoListViewModel : TodoListViewModel
    @Binding var selectedIndex: NSInteger
    @Binding var choseGroupViewIsExpanded: Bool
    @State private var groupTitle = ""
    @Binding var isEdit: Bool

    var body: some View {
        VStack {
            VStack {
                ForEach(0..<todoListViewModel.todoList.count){index in
                    HStack{
                        if index == selectedIndex {
                            Text(todoListViewModel.todoList[index].title).padding(.horizontal).padding(.top, 10).foregroundColor(.blue)
                        }
                        else {
                            Text(todoListViewModel.todoList[index].title).padding(.horizontal).padding(.top, 10)
                        }
                        Spacer()
                        if index == selectedIndex {
                            Image(systemName: "checkmark").padding(.horizontal).padding(.top, 10).foregroundColor(.blue)
                        }
                    }
                    .background(Color.white)
                    .onTapGesture {
                        selectedIndex = index;
                        choseGroupViewIsExpanded = false
                    }
                }
            }
            .background(Color.white)
            .frame(height: 170)
            .overlay(RoundedRectangle(cornerRadius: 15, style: .continuous)
                        .stroke(Color.gray, lineWidth: 1)
            )
        }
    }
}
