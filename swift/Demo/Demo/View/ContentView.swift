//
//  ContentView.swift
//  Demo
//
//  Created by 李永杰 on 2022/7/4.
//

import SwiftUI

struct ContentView: View {
    
    @StateObject var viewModel = AppViewModel.shared
    
    var body: some View {
        
        if viewModel.appInfos.count == 0 {
            ProgressView()
                .onAppear {
                    requestMore()
                }
        } else {
            NavigationView {
                List {
                    ForEach(viewModel.appInfos) { appInfo in
                        RowView(info: appInfo)
                            .listRowSeparator(.hidden)
                            .listRowBackground(EmptyView())
                    }
                    HStack{
                        Spacer()
                        if viewModel.hasMore {
                            ProgressView()
                                .padding(.trailing, 5)
                        }
                        Text(viewModel.hasMore ? "Loading..." : "No more data")
                            .foregroundColor(Color.secondary)
                            .onAppear(perform: requestMore)
                        Spacer()
                    }
                    .listRowSeparator(.hidden)
                    .listRowBackground(EmptyView())
                }
                .navigationTitle("APP")
                .listStyle(.plain)
                .background(Color(.systemGroupedBackground))
                .refreshable {
                    viewModel.requestAppinfos(true)
                }
            }
        }
    }
    
    func requestMore() {
        if viewModel.hasMore {
            viewModel.requestAppinfos(false)
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
