//
//  IndexView.swift
//  Assignment
//
//  Created by Yikai Deng on 2022/6/25.
//

import SwiftUI

struct IndexView: View {
    
    enum LoadingState {
        case loading
        case loaded
        case failure
    }
    
    @ObservedObject var viewModel = ViewModel()
    
    @State var loadingState: LoadingState = .loading
    
    var body: some View {
        NavigationView {
            switch viewModel.loadingState {
            case .loading:
                loadingView
            case .loaded:
                listView
            case .idle:
                failureView
            }
        }
        .onAppear {
            viewModel.loadData()
        }
    }
    
    var loadingView: some View {
        ProgressView()
            .frame(width: 64, height: 64, alignment: .center)
            .navigationTitle("App")
            .ignoresSafeArea(.all, edges: .bottom)
    }
    
    var failureView: some View {
        VStack {
            Text("Bad network service")
            Text("Tap to refresh")
        }
        .onTapGesture {
            viewModel.reload()
        }
        .background(Color("background"))
        .navigationTitle("App")
        .ignoresSafeArea(.all, edges: .bottom)
    }
    
    var listView: some View {
        List {
            ForEach(viewModel.itemsData) { item in
                ItemCell(imageURL: item.icon, title: item.name, description: item.description, isFavorite: item.isFavorite, favoriteAction: { success, failure in
                    DataProvider.changeFavorToFile(model: item, success: success, failure: failure)
                })
                .frame(height: 94)
            }
            
            RefreshFooterView(hasMoreData: viewModel.hasMoreData)
                .onAppear {
                    viewModel.loadData()
                }
        }
        .refreshable {
            viewModel.reload()
        }
        .background(Color("background"))
        .navigationTitle("App")
        .ignoresSafeArea(.all, edges: .bottom)
    }
}

struct IndexView_Previews: PreviewProvider {
    static var previews: some View {
        IndexView()
    }
}
