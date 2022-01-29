//
//  ContentView.swift
//  ToDoList
//
//  Created by lilun.ios on 2022/1/26.
//

import SwiftUI

struct ContentView: View {
    @EnvironmentObject var store: DefaultStore<AppState>

    init() {
        UINavigationBar
            .appearance()
            .backgroundColor = UIColor(named: "listbg")
    }
    
    var body: some View {
        NavigationView {
            ZStack {
                VStack {
                    /// make list content not under status bar
                    Spacer()
                        .frame(height: 20)

                    HStack(alignment: .top, spacing: 0) {
                        ToDoList()
                            .padding(.bottom, 55)
                            .clipped()
                            .border(.green)
                    }

                }
                /// set background color under status bar
                .background(Color("listbg"))



                VStack(){
                    Spacer()
                    InputBar(text: "")
                        .frame(idealHeight: 50, maxHeight: 50)
                        .padding(.bottom, 0)
                        .ignoresSafeArea()
                }
            }
             .edgesIgnoringSafeArea(.top)
             .navigationBarItems(
                leading:
                    Text("List")
                        .font(Font.title)
             )
             .navigationBarTitleDisplayMode(.inline)
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
