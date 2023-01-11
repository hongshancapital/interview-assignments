//
//  ContentView.swift
//  AppList
//
//  Created by wozyao on 2022/10/25.
//

import SwiftUI
import Refresh

struct ContentView: View {
    
    @EnvironmentObject var viewModel: ViewModel
    
    var body: some View {
        NavigationView {
            ZStack {
                Color(.systemGray6).ignoresSafeArea()
                Group {
                    if viewModel.appList.isEmpty {
                        ProgressView()
                            .onAppear {
                                viewModel.loadNewData()
                            }
                    } else {
                        ScrollView {
                            
                            RefreshHeader(refreshing: $viewModel.headerRefreshing) {
                                viewModel.loadNewData()
                            } label: { progress in
                                HStack {
                                    ProgressView()
                                }
                                .padding(.vertical, 30)
                            }

                            ForEach(viewModel.appList) { appModel in
                                AppListRow(appModel: appModel)
                            }
                            RefreshFooter(refreshing: $viewModel.footerRefreshing) {
                                viewModel.loadMoreData()
                            } label: {
                                
                                if viewModel.noMore {
                                    Text("No more data.")
                                } else {
                                    HStack(spacing: 10) {
                                        ProgressView()
                                        Text("Loading...")
                                    }
                                }
                                
                            }
                            .noMore(viewModel.noMore)
                            .preload(offset: 50)

                        }
                        .enableRefresh()
                    }
                }
            }
            .navigationTitle("App")
        }

    }
}

struct AppListRow: View {
    
    @State var appModel: AppModel
    
    var body: some View {
        HStack {
            AsyncImage(url: URL(string: appModel.artworkUrl100)) { image in
                image
                    .resizable()
                    .cornerRadius(8)
                    .overlay {
                        RoundedRectangle(cornerRadius: 8,style: .continuous)
                            .stroke(Color(.systemGray4), lineWidth: 1)
                    }
            } placeholder: {
                ProgressView()
            }
            .frame(width: 60, height: 60)
            
            VStack(alignment: .leading, spacing: 10) {
                Text(appModel.trackName)
                    .font(.system(size: 17))
                    .fontWeight(.bold)
                Text(appModel.description)
                    .font(.system(size: 14))
                    .lineLimit(2)
            }
            Spacer()
            Button(action: {
                appModel.isLike.toggle()
            }) {
                Image(systemName: appModel.isLike ? "heart.fill" : "heart")
                    .foregroundColor(appModel.isLike ? .red:  .gray)
                    .scaleEffect(appModel.isLike ? 1.2 : 1)
                    .animation(.linear(duration: 1), value: appModel.isLike)
            }
        }
        .padding()
        .background(.white)
        .cornerRadius(8)
        .padding(.horizontal)
        
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
