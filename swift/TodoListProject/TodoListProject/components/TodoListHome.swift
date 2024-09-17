//
//  TodoListHome.swift
//  TodoListProject
//
//  Created by 钱伟龙 on 2022/1/11.
//

import SwiftUI


struct TodoListHome: View {
    
    @ObservedObject var todoListViewModel : TodoListViewModel = TodoListViewModel()
    @State private var isEdit = false
    @State var value : CGFloat = 0
    @State private var choseGroupViewIsExpanded = false
    @State private var selectedIndex = 0

    var body: some View {
        NavigationView {
            ZStack {
                List {
                    ForEach(todoListViewModel.todoList, id:\.self) { groupItem in
                        Section(header: Text(groupItem.title)
                                    .textCase(nil)
                                    .foregroundColor(Color.black)
                        ) {
                            ForEach(groupItem.todoListItems, id:\.self) { listItem in
                                TodoListCellView(listItem: listItem)
                                    .listRowBackground(Color.clear)
                            }
                            .allowsHitTesting(!self.isEdit)
                        }
                    }
                    TodoListBlackCellView()
                        .frame(height: 50)
                        .listRowBackground(Color.clear)
                }
                .listStyle(.plain)
                .navigationBarTitle("List")
                .background(Color.gray.opacity(0.1))
                .navigationBarItems(
                    trailing:
                        NavigationLink(destination: TodoListSearch(todoListViewModel: todoListViewModel)) {
                            Image(systemName: "magnifyingglass")
                                .foregroundColor(.gray)
                        }
                )
                .environmentObject(todoListViewModel)

                
                VStack(spacing: 0) {
                    Spacer()
                    if choseGroupViewIsExpanded {
                        ChoseGroupView(selectedIndex: $selectedIndex, choseGroupViewIsExpanded: $choseGroupViewIsExpanded,isEdit: $isEdit)
                            .padding(.trailing,20)
                            .padding(.leading,100)
                            .padding(.bottom,10)
                    }
                    AddTodoItemView(isEdit: $isEdit, selectedIndex: $selectedIndex, choseGroupViewIsExpanded: $choseGroupViewIsExpanded)
                    Spacer()
                        .frame(height:isEdit ? (self.value + 15) : 0)
                }
                .ignoresSafeArea()
                .environmentObject(todoListViewModel);
            }.onAppear {
                NotificationCenter.default.addObserver(forName: UIResponder.keyboardWillShowNotification, object: nil, queue: OperationQueue.current) { (noti) in
                    let value = noti.userInfo![UIResponder.keyboardFrameEndUserInfoKey] as! CGRect
                    let height = value.height
                    self.value = height - UIApplication.shared.windows.first!.safeAreaInsets.bottom
                }
                NotificationCenter.default.addObserver(forName: UIResponder.keyboardWillHideNotification, object: nil, queue: OperationQueue.current) { (noti) in
                    self.value = 0
                    self.isEdit = false
                }
            }
        }
        .navigationViewStyle(StackNavigationViewStyle())
        .background(Color.gray.opacity(0.1))
    }
}
