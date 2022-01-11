//
//  ContentView.swift
//  interview-assignments
//
//  Created by never88gone on 2022/1/20.
//

import SwiftUI

struct ContentView: View {
    @State private var showAlert: Bool = false
    @State private var curGroupName: String = ""
    @State private var curSearchTxt: String = ""
    
    @State private var showToast: Bool = false
    @State private var toastMessage: String = "Group Name can't be empty"
    
    @StateObject var todoManager: TodoManager = .shared
    private let navTile: String = "List"
    init() {
        UINavigationBar.appearance().backgroundColor = UIColor(named: "ngmainbackgroud")
        UINavigationBar.appearance().titleTextAttributes = [.foregroundColor: UIColor(red: 55/255, green: 59/255, blue: 81.0/255, alpha: 1)]
        UINavigationBar.appearance().largeTitleTextAttributes = [.foregroundColor: UIColor(red: 55/255, green: 59/255, blue: 81.0/255, alpha: 1)]
        
        UITableView.appearance().separatorColor = UIColor.clear
        UITableView.appearance().backgroundColor = UIColor(named: "ngmainbackgroud")
    }
    
    var body: some View {
        return NavigationView {
            ZStack {
                VStack {
                    List {
                        ForEach(self.todoManager.groupNameList, id: \.self) { oneKey in
                            let sectionTodoList = self.todoManager.groupDic[oneKey] ?? []
                            let sectionSortedTodos = sectionTodoList.sorted { $0.checked != true || $1.checked == true
                            }
                            TodoSectionView(groupName: oneKey, sectionTodoList: sectionSortedTodos, sectionCellTextChangedAction: { oneTodo, inputText in
                                if inputText.count == 0 {
                                    withAnimation(.spring()) {
                                        let curIndex = self.todoManager.indexOfTodo(todo: oneTodo)
                                        self.todoManager.removeTodo(index: curIndex)
                                    }
                                }
                            }, sectionCellCheckedChangedAction: {
                                oneTodo in
                                withAnimation(.spring()) {
                                    let curIndex = self.todoManager.indexOfTodo(todo: oneTodo)
                                    self.todoManager.updateTodo(index: curIndex, todo: oneTodo)
                                }
                            })
                        }
                    }.listStyle(GroupedListStyle())
                    Spacer()
                    BottomInputView(groupNameList: self.todoManager.groupNameList, groupName: $todoManager.curGroupName, appendTodoAction: {
                        oneTitle, oneGroupName in
                        if oneGroupName.count != 0, oneTitle.count != 0 {
                            withAnimation(.spring()) {
                                self.todoManager.addTodo(todo: Todo(title: oneTitle, groupName: oneGroupName))
                            }
                        } else {
                            self.toastMessage = oneGroupName.count == 0 ? "Group Name can't be empty" : "Input txt can't be empty"
                            withAnimation(Animation.easeInOut(duration: 0.5)) {
                                self.showToast = true
                            }
                            DispatchQueue.main.asyncAfter(deadline: .now() + 1.5) {
                                withAnimation(Animation.easeInOut(duration: 0.5)) {
                                    self.showToast = false
                                }
                            }
                        }
                    })
                }.background(Color("ngmainbackgroud")).navigationBarTitleDisplayMode(.large).navigationTitle(Text(self.navTile)).navigationBarItems(trailing: SearchBar(text: $curSearchTxt, searchTextChangedAction: {
                    _ in
                    self.todoManager.searchText = self.curSearchTxt

                }).frame(width: 150, height: 44).overlay(RoundedRectangle(cornerRadius: 15).stroke().fill(Color.blue)))
                    .navigationBarItems(leading: Button(action: {
                        withAnimation(Animation.easeInOut(duration: 1)) {
                            self.showAlert.toggle()
                        }
                    }) {
                        Image(systemName: "rectangle.stack.badge.plus").resizable().frame(width: 30, height: 30).foregroundColor(Color.blue)
                    
                    })
                VStack {
                    Spacer()
                    Text(self.toastMessage).font(.custom("PingFangSC-Regular", size: 12)).padding().foregroundColor(.white)
                        .background(RoundedRectangle(cornerRadius: 15).fill(.black)).padding(EdgeInsets(top: 5, leading: 5, bottom: 100, trailing: 5))
                }.opacity(self.showToast ? 1 : 0)
            }
 
        }.edgesIgnoringSafeArea(.all).navigationViewStyle(StackNavigationViewStyle()).textFieldAlert(isShowing: $showAlert, text: $todoManager.curGroupName)
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        return ContentView().previewDevice("iPhone 13")
    }
}
