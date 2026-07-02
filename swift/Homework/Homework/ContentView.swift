//
//  ContentView.swift
//  Homework
//
//  Created by Andy on 2022/9/2.
//

import SwiftUI

struct ContentView: View {
    
    @EnvironmentObject var viewModel: AppListViewModel
    
    var body: some View {
   
        NavigationView {
            ZStack {
                if viewModel.appInfos.isEmpty {
                    ProgressView()
                }
                if viewModel.appInfos.count > 0 {
                    AppListView()
                        .navigationTitle("App")
                        .background(Color.bgGrayColor)
                    
                }
                if let error = viewModel.error {
                    VStack {
                        Text(error)
                        Button {
                            viewModel.loadData()
                        } label: {
                            Text("retry")
                        }
                    }
                }
            }
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView().environmentObject(AppListViewModel())
    }
}
