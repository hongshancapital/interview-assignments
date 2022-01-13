//
//  ContentView.swift
//  ToDoList
//
//  Created by wh on 2022/1/13.
//

import SwiftUI

//屏幕宽高
let screenSize = UIScreen.main.bounds

//取消键盘
extension UIApplication {
    func dismissKeyboard() {
        sendAction(#selector(UIResponder.resignFirstResponder), to: nil, from: nil, for: nil)
    }
}

struct ContentView: View {
    @ObservedObject var mainData: MainData = .shared
    @State var searchText : String = ""
    @State var searching : Bool = false
    
    init(mainData : MainData) {
        self.mainData = mainData
    }
    
    var body: some View {
        NavigationView {
            ZStack {
                Color("BackgroundColor").edgesIgnoringSafeArea(.all)
                VStack {
                    //搜索框
                    SearchBar(searchText: $searchText, searching: $searching)
                    //事项内容View
                    ScrollView(.vertical, showsIndicators: false, content: {
                        ForEach(mainData.searchTodo(searchText: searchText)) { groupModel in
                            VStack {
                                ZStack {
                                    HStack {
                                        Text("\(groupModel.title)")
                                            .foregroundColor(Color("CheckedColor"))
                                            .font(.system(size: 16))
                                            .fontWeight(.semibold)
                                            .multilineTextAlignment(.leading)
                                            .frame(height: 44.0)
                                            .padding(.leading)
                                        Spacer()
                                    }
                                }
                                ForEach(groupModel.toDoList) {value in
                                    VStack {
                                        TodoViewCell(mainData: mainData, groupModel: groupModel, toDoModel: value)
                                    }
                                }
                            }
                        }
                        .padding(.bottom, 60.0)
                    })
                        .gesture(DragGesture().onChanged({ _ in
                            UIApplication.shared.dismissKeyboard()
                        }))
                }
                .navigationBarTitle(searching ? "Searching" : "List")
                .background(Color("BackgroundColor"))
                .toolbar {
                    if searching {
                        Button("Cancle") {
                            searchText = ""
                            withAnimation {
                                searching = false
                                UIApplication.shared.dismissKeyboard()
                            }
                        }
                    }
                }
                //底部添加事项View
                BottomAddView(mainData: mainData)
            }
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView(mainData: MainData())
    }
}

