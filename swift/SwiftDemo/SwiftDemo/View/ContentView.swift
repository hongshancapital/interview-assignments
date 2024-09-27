//
//  ContentView.swift
//  SwiftDemo
//
//  Created by xz on 2023/2/3.
//

import SwiftUI

struct ContentView: View {
    @State var models: [Model] = []
    
    @State var loadMoreStatus: LoadStatus = .none
    @State var loadStatus: LoadStatus = .none
    
    private let threholdOffset: Int = 5
    
    var body: some View {
        ZStack {
            List {
                ForEach(models) { model in
                    ModelCell(model: model, favoriteClickAction: {
                        self.models[self.models.firstIndex(where: { $0.id == model.id})!].favorite = !(model.favorite)
                    }).onAppear {
                        self.listItemAppears(model)
                    }
                }
                // load more
                LoadingView(status: $loadMoreStatus) {
                    self.loadMoreModels()
                }
            }
            .listStyle(.plain)
            .background(Color.init(uiColor: UIColor.systemGray6))
            .onAppear {
                self.loading()
            }
            
            // first load 
            LoadingView(status: $loadStatus) {
                self.loading()
            }
        }
    }
}

extension ContentView {
    private func listItemAppears(_ item: Model) {
        if models.isThresholdItem(offset: threholdOffset, item: item) {
            loadMoreModels()
        }
    }
    
    private func loadMoreModels() {
        if  loadMoreStatus == .loading {
            return
        }
        self.loadMoreStatus = .loading
        
        NetworkManager.loadFeeds { feeds in
            if let feeds = feeds {
                models += feeds.results
                self.loadMoreStatus = .none
            } else {
                self.loadMoreStatus = .failed
            }
        }
    }
    
    private func loading() {
        if loadStatus == .loading {
            return
        }
        self.loadStatus = .loading
        
        NetworkManager.loadFeeds { feeds in
            if let feeds = feeds {
                models = feeds.results
                
                self.loadStatus = .none
            } else {
                self.loadStatus = .failed
            }
        }
    }
}


struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
