//
//  AppInfoListView.swift
//  FWDemo
//
//  Created by wei feng on 2022/6/21.
//

import SwiftUI

struct AppInfoListView: View {
    @StateObject var viewModel = AppListViewModel()
    var body: some View {
        NavigationView{
            if viewModel.datalist.count == 0 {
                ProgressView()
                    .onAppear {
                        viewModel.refresh()
                    }
            }else{
                List{
                    ForEach(viewModel.datalist) { appInfo in
                        AppInfoRow(appInfo: appInfo)
                    }
                    .listRowBackground(EmptyView())
                    .listRowSeparator(.hidden)
                    
                    HStack(alignment: .center){
                        Spacer()
                        if !viewModel.isLoadCompletion {
                            ProgressView()
                                .onAppear {
                                    viewModel.loadMore()
                                }
                        }
                        Text(viewModel.isLoadCompletion ? "No more data." : "Loading...")
                            .foregroundColor(Color(uiColor: .lightGray))
                            .font(Font.system(size: 14))
                        Spacer()
                    }
                    .listRowBackground(EmptyView())
                    .listRowSeparator(.hidden)
                }
                .refreshable {
                    viewModel.refresh()
                }
                .navigationTitle("App")
                .listStyle(.plain)
                .background(Color(uiColor: .systemGroupedBackground))

            }
        }
    }
}

struct AppInfoListView_Previews: PreviewProvider {
    static var previews: some View {
        AppInfoListView()
    }
}
