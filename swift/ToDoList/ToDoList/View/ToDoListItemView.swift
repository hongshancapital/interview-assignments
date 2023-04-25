//
//  ToDoListItemView.swift
//  TodoList
//
//  Copyright © 2022 ZhangHeng. All rights reserved.
//

import SwiftUI

struct ToDoListItemView: View {
    @ObservedObject var listItem : ListItem

    @EnvironmentObject private var listViewModel: ListViewModel
    @FocusState private var isInputFocused:Bool
    
    var body: some View {
        VStack(alignment:.leading) {
            Spacer()
            HStack() {
                Image(systemName:self.listItem.hasDone ? "largecircle.fill.circle" : "circle")
                    .foregroundColor(self.listItem.hasDone ? .gray : .black)
                    .padding(.leading)
                
                if self.listItem.editMode {
                    TextField("string", text: $listItem.title, prompt: Text("Planing"))
                        .font(.system(size: 15,weight: .bold, design: .default))
                        .onSubmit {
                            listViewModel.finishEditItem(item: listItem)
                            isInputFocused = false
                        }
                        .focused($isInputFocused)
                }else{
                    if self.listItem.hasDone {
                        Text(self.listItem.title)
                            .strikethrough()
                            .foregroundColor(.gray)
                            .font(.system(size : 15))
                            .fontWeight(.bold)
                    }else{
                        Text(self.listItem.title)
                            .font(.system(size : 15))
                            .fontWeight(.bold)
                    }
                }
                Spacer()
            }
            Spacer()
        }
        .background(.white)
        .cornerRadius(12)
        .onTapGesture {
            if !listItem.editMode {
                listViewModel.finishItem(item: listItem)
            }
        }
        .onLongPressGesture {
            if !listItem.hasDone {
                listViewModel.beginEditingItem(item :listItem)
                DispatchQueue.main.asyncAfter(deadline: .now() + 0.25){
                    isInputFocused.toggle()
                }
            }
        }
    }
}

struct ToDoListItemView_Previews: PreviewProvider {
    static var previews: some View {
        let listItem = testList()
        let viewModel = testViewModel()
        
        ToDoListItemView(listItem: listItem)
            .environmentObject(viewModel)
    }
    
    static func testList() -> ListItem {
        let listItem = ListItem()
        listItem.title = "test"
        listItem.editMode = false
        listItem.hasDone = true
        
        return listItem
    }
    
    static func testViewModel() -> ListViewModel {
        let viewModel = ListViewModel()
        viewModel.loadModel()
        
        return viewModel
    }
}
