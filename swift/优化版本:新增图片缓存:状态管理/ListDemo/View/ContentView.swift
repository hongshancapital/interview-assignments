//
//  ContentView.swift
//  ListDemo
//
//  Created by renhe on 2022/6/24.
//

import SwiftUI

struct ContentView: View {
    @ObservedObject var viewModel = ContentViewModel()
    var body: some View {
        NavigationView{
            List{
                ForEach(viewModel.appInfos,id: \.trackId){ info in
                    InfoItemView.init(infoModel: info) .listRowBackground(Color.clear)
                        .listRowSeparator(.hidden)
                        .environmentObject(viewModel)
                }
                // 加载更多
                refreshListener
                
            }.navigationTitle(Text("APP"))
                .listStyle(.plain)
                .background(Color(uiColor: .secondarySystemBackground))
                .overlay {
                    if viewModel.isLoading && viewModel.appInfos.isEmpty {
                        ProgressView()
                            .frame(maxWidth: .infinity, maxHeight: .infinity,alignment: .center)
                    }
                }
                .refreshable {
                    await viewModel.refresh()
                }
            
        }
    }
    
    private var refreshListener: some View {
        Group {
            if viewModel.hasMore && !viewModel.appInfos.isEmpty {
                HStack(spacing: 8) {
                    ProgressView()
                    Text("Loading...").font(Font.system(size: 18)).foregroundColor(Color.gray)
                }
            }
            
            if let error = viewModel.error {
                Text(error.localizedDescription).font(Font.system(size: 18)).foregroundColor(Color.gray)
            }
            
            if !viewModel.hasMore {
                Text("No more data.").font(Font.system(size: 18)).foregroundColor(Color.gray)
            }
        } .listRowBackground(Color.clear)
            .listRowSeparator(.hidden)
            .frame(maxWidth: .infinity, alignment: .center)
        
            .onAppear {
                Task{
                    await viewModel.loadMore()
                }
            }
        
    }
    
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
