//
//  ContentView.swift
//  SwiftHomeWork
//
//  Created by apple on 2022/4/12.
//

import SwiftUI
import Combine

struct ContentView: View {
    
    @EnvironmentObject var viewModel : DataViewModel
    
    var body: some View {
        VStack{
            if let wel = viewModel.welcome {
                List(wel.results){ item in
                    Section.init {
                        ItemUIView.init(item: item)
                    }
                }
                .listStyle(.insetGrouped)
            }else{
                ProgressView()
            }
        }.task {
            try? await viewModel.request(page: 0)
        }.refreshable {
            try? await viewModel.request(page: 0)
        }
        .navigationTitle("App")
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        NavigationView{
            ContentView()
        }.environmentObject(DataViewModel())
    }
}
