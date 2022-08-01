//
//  Home.swift
//  InterviewDemo
//
//  Created by 陈凝 on 2022/8/1.
//

import SwiftUI

struct Home: View {
    
    @StateObject var homeViewModelObject : HomeViewModel = .init()
    
    var body: some View {
        NavigationView {
            DetectView()
            .navigationTitle("App")
        }
        .onAppear{
            Task{
                await homeViewModelObject.fetchHomeDataList()
            }
        }
    }
    
    // MARK: - Home Screen main view,when viewModel has data ,hide the progressview
    @ViewBuilder
    func DetectView() -> some View {
        if let data = homeViewModelObject.homeDataList{
            List{
                ForEach(data,id:\.trackId){model in
                    HomeListRowView(model: model)
                        .listRowSeparator(.hidden)
                        .listRowBackground(Color.clear)
                        .listStyle(.inset)
                        .listRowInsets(EdgeInsets())
                        .padding(.bottom,10)
                }
                LoadMoreView()
            }
            .refreshable {
                Task{
                    await self.homeViewModelObject.refresh()
                }
            }
        }
        else{
            ProgressView()
        }
    }
    
    // MARK: - LoadMoreView alawys on the bottom of list,so when it show up means we need load more data to show
    @ViewBuilder
    func LoadMoreView() -> some View {
        HStack(spacing:10){
            // if no more data , hide this
            if self.homeViewModelObject.canLoadMore(){
                ProgressView()
                Text("Loading...")
            }
            else{
                Text("No more data.")
            }
        }
        .listRowSeparator(.hidden)
        .listRowBackground(Color.clear)
        .listStyle(.inset)
        .listRowInsets(EdgeInsets())
        .frame(maxWidth:.infinity,alignment:.center)
        .padding(.bottom,10)
        .onAppear{
            Task{
                if self.homeViewModelObject.canLoadMore(){
                    await self.homeViewModelObject.fetchHomeDataList()
                }
            }
        }
    }
}

struct Home_Previews: PreviewProvider {
    static var previews: some View {
        Home()
    }
}
