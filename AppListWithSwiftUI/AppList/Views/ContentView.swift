//
//  ContentView.swift
//  AppList
//
//  Created by 王宁 on 2022/4/1.
//

import SwiftUI

struct ContentView: View {
    
    @MainActor
    @StateObject private var appViewModel = AppViewModel()
    
    var body: some View {
        NavigationView {
            List {
                ForEach($appViewModel.datas) { model in
                    DataCell(model: model, action: {model in
                        appViewModel.like(model: model)
                    })
                        .listRowInsets(.init(top: 5, leading: 0, bottom: 5, trailing: 0))
                }
                HStack{
                    Spacer()
                    if(appViewModel.noMoreData){
                        Text("No more data.")
                    }else  if(appViewModel.hasMoreData){
                        ProgressView()
                            .padding(.trailing,10)
                        Text("Loading...")
                    }
                    Spacer()
                }
                .foregroundColor(Styles.Colors.textGray)
                .listRowBackground(Color.clear)
                .task{
                    if (appViewModel.hasMoreData){
                        await appViewModel.loadMore()
                    }
                }
            }
            .refreshable {
                await appViewModel.refresh()
            }
            .navigationBarTitle("App")
            .background(Styles.Colors.background)
            .overlay(content: {
                if(appViewModel.datas.count == 0){
                    ProgressView()
                }
            })
            .task {
                await appViewModel.firstLoad()
            }
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        Group {
            ContentView()
.previewInterfaceOrientation(.portraitUpsideDown)
            ContentView()
        }
    }
}
