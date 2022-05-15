//
//  ContentView.swift
//  LHAppListDemo
//
//  Created by lzh on 2022/3/26.
//

import SwiftUI

struct ContentView: View {
    @StateObject var viewModel = LHViewModel()
    
    var body: some View {
        NavigationView {
            ZStack {
                if viewModel.loadState == .failed {
                    Text("load data failed")
                        .padding(EdgeInsets(top: 0, leading: 0, bottom: 80, trailing: 0))
                }else{
                    loadingView
                }
                if viewModel.models.count != 0 {
                    listView
                }
            }
        }
        .onAppear {
            viewModel.loadMoreAppInfo()
        }
    }
    
    
    var loadingView : some View {
        ProgressView().padding(EdgeInsets(top: 0, leading: 0, bottom: 80, trailing: 0))
    }
    
    var listView : LHListView {
        LHListView(viewModel: self.viewModel)
    }
}

//struct ContentView_Previews: PreviewProvider {
//    static var previews: some View {
//        ContentView()
//    }
//}
