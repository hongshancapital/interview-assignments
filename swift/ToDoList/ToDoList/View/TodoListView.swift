//
//  TodoListView.swift
//  TodoList
//
//  Copyright © 2022 ZhangHeng. All rights reserved.
//

import SwiftUI

struct TodoListView: View {
    @ObservedObject var listViewModel : ListViewModel = ListViewModel()
    
    var body: some View {
        NavigationView {
            ZStack(alignment: .top) {//???
                List{
                    ForEach(listViewModel.listModel, id:\.self) { listGroup in
                        Section(header:
                            Text(listGroup.sectionTitle)
                                .fontWeight(.bold)
                                .foregroundColor(.black)
                                .font(.title3)
                                .textCase(nil)
                        ) {
                            ForEach(listGroup.listItems, id: \.self) { listItem in
                                ToDoListItemView(listItem: listItem)
                            }
                            .listRowSeparator(SwiftUI.Visibility.hidden, edges: SwiftUI.VerticalEdge.Set.all)
                            .listRowBackground(Color.clear)
                        }
                    }
                }
                .onTapGesture(perform: {
                    listViewModel.isFocused = false
                })
                .background(Color.init(hex: "#efefef"))
                .toolbar {
                    ToolbarItem(placement: .bottomBar) {
                        Button(action: {
                            listViewModel.showInputView = true
                            listViewModel.isFocused = true
//                            inputTodoView!.focusInputField()
                        } ) {
                            RoundedRectangle(cornerRadius: 8)
                                .foregroundColor(.white)
                                .overlay(alignment: .center) {
                                    Text("add new")
                                        .foregroundColor(.gray)
                                        .padding()
                                        .frame(width: UIScreen.main.bounds.size.width - 40, height: 48, alignment: .leading)
                                }
                                .overlay(
                                    Capsule(style: .continuous)
                                        .stroke(Color.gray, style: StrokeStyle(lineWidth: 1, dash: [3]))
                                        .cornerRadius(6)
                                        )
                                .frame(width: UIScreen.main.bounds.size.width - 40, height: 48)
                        }
                        .foregroundColor(.clear)
                        .opacity(listViewModel.showInputView ? 0 : 1)
                    }
                }
                .overlay(
                    InputTodoView()
                        .opacity(listViewModel.showInputView ? 1 : 0),
                    alignment: .bottom
//                    inputTodoView
//                            .opacity(listViewModel.showInputView ? 1 : 0)
//                         ,alignment: .bottom
                )
                
            }
            .navigationTitle("List")
            .environmentObject(listViewModel)
        }
    }
}

struct TodoListView_Previews: PreviewProvider {
    static var previews: some View {
        TodoListView()
    }
}


struct InputBarViewModifier: ViewModifier {
    let isForuces: Bool
    
    func body(content: Content) -> some View {
        VStack(spacing: 0) {
            content
            inputBar
        }
    }
    
    var inputBar: some View {
        EmptyView()
    }
}
