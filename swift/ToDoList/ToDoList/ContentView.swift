//
//  ContentView.swift
//  ToDoList
//
//  Created by wh on 2022/1/13.
//

import SwiftUI

// 屏幕宽高
let screenSize = UIScreen.main.bounds

// 取消键盘
extension UIApplication {
    func dismissKeyboard() {
        sendAction(#selector(UIResponder.resignFirstResponder), to: nil, from: nil, for: nil)
    }
}

struct ContentView: View {
    @ObservedObject var mainData: MainData = .shared
    @State var searching: Bool = false
    @State var searchText: String = ""

    init(mainData: MainData) {
        self.mainData = mainData
    }

    var body: some View {
        NavigationView {
            ZStack {
                Color("BackgroundColor").edgesIgnoringSafeArea(.all)
                VStack {
                    // 搜索框
                    SearchBar(searchText: $searchText, searching: $searching)
                    // 事项内容View
                    ScrollContentView(mainData: mainData, searchText: $searchText, searching: $searching)
                }
                // 底部添加事项View
                BottomAddView(mainData: mainData)
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
        }
        .gesture(DragGesture().onChanged { _ in
            UIApplication.shared.dismissKeyboard()
        })
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView(mainData: MainData())
    }
}
